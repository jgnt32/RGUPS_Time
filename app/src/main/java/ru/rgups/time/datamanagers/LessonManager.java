package ru.rgups.time.datamanagers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import ru.rgups.time.model.DataManager;
import ru.rgups.time.model.HelperManager;
import ru.rgups.time.model.HomeWork;
import ru.rgups.time.model.LessonListElement;
import ru.rgups.time.model.LessonTableModel;
import ru.rgups.time.model.entity.LessonInformation;
import ru.rgups.time.utils.CalendarManager;
import ru.rgups.time.utils.PreferenceManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.text.TextUtils;

public class LessonManager {
	private SQLiteDatabase mDb;
	private SQLiteStatement mSaveLessonStatement;
	private static LessonManager mInstance;
	
	private GregorianCalendar mCalendar = new GregorianCalendar();
	
	public static LessonManager getInstance() {
		if(mInstance == null){
			mInstance = new LessonManager();
		}
		return mInstance;
	}
	
	public LessonManager() {
		mDb = HelperManager.getHelper().getWritableDatabase();
				
	}
	
	
	
	public Cursor getTeachersLessonsBySemestrDay(Integer dayNumber, String teacherName){
		
		String query = TextUtils.concat(
				"SELECT l.",LessonTableModel.ID," as _id, l.*,i.*",
				
				" FROM ",LessonTableModel.TABLE_NAME," as l  ",
				"INNER JOIN ",LessonInformation.TABLE_NAME," AS i ON ",
				"l.",LessonTableModel.ID," = i.",LessonInformation.LESSON_ID,
				" WHERE l.",
				LessonTableModel.DAY,"=?", " AND i.",
				LessonInformation.TEACHER_NAME,"=? AND ",
				"(l.",LessonTableModel.WEEK_STATE,"= ? OR l." ,
				LessonTableModel.WEEK_STATE,"='2')",
				" GROUP BY l.",LessonTableModel.NUMBER, " ORDER BY l.",LessonTableModel.NUMBER
				).toString();
		Cursor c = mDb.rawQuery(query, new String[]{Integer.toString(CalendarManager.getDayOfWeek(dayNumber)), teacherName,
					Integer.toString(CalendarManager.getWeekState(dayNumber))});
		return c;
	}
	
	

	
	public boolean [][] getStudentLessonMatrix(){
		boolean [][] result = new boolean [7][2];
		
		for(int i = 0; i < 2; i++){
			for(int j = 1; j < 8; j++){
				result[j-1][i] = DataManager.getInstance().dayHasLesson(j, i);
			}
		}
		
		return result;
	}
	
	
	public boolean [][] getTeacherLessonMatrix(String teacheName){
		boolean [][] result = new boolean [7][2];
		
		for(int i = 0; i < 2; i++){
			for(int j = 1; j < 8; j++){
				result[j-1][i] = DataManager.getInstance().dayHasLesson(j, i, teacheName);
			}
		}
		
		return result;
	}

    @Deprecated
	public boolean [] getHomeWorkExistingVector(long timestamp, ArrayList<LessonListElement> lessonList){
		boolean [] result = new boolean [lessonList.size()];
		int i = 0;
		for(LessonListElement lesson : lessonList){
			result[i] = DataManager.getInstance().lessonHasHomeWork(lesson.getId(), timestamp / CalendarManager.MILISECONDS_PER_DAY);
			i++;
			
		}
		
		return result;
	}


	public int [] getHomeWorkVector(){
        int [] result = new int [CalendarManager.getCorrectSemestrDayCount()];
        Cursor c = getHomeWorkCursor();
        try {


            while(c.moveToNext()){
                result[getDayOfSemestr(c)] = c.getInt(c.getColumnIndex("cnt"));
            }
        } catch (Exception e){
            e.printStackTrace();
        }

		return result;
	}


    private int getDayOfSemestr(Cursor c) {
        long date = c.getLong(c.getColumnIndex(HomeWork.DATE)) * CalendarManager.MILISECONDS_PER_DAY;
        return CalendarManager.getDayOfSemestr(date);
    }


    public Cursor getHomeWorkCursor(){
		Cursor c = mDb.rawQuery(TextUtils.concat(
				"SELECT COUNT(*) cnt, h.* FROM ",HomeWork.TABLE_NAME," as h WHERE h.", HomeWork.GROUP_ID," = ? "
				," AND h.",HomeWork.COMPLITE," = '0' GROUP BY ", HomeWork.DATE 
				).toString(), new String[]{Long.toString(getCurrentGroupId())});
		return c;
	}

	
	public long getCurrentGroupId(){
		return PreferenceManager.getInstance().getGroupId();
	}

    public LessonListElement getClosestLesson(){
        GregorianCalendar calendar = new GregorianCalendar();
        int dayOfSemestr = CalendarManager.getDayOfSemestr(calendar.getTimeInMillis());
        int lessonNumber;

        if(dayOfSemestr < 0){
            dayOfSemestr = 0;
           lessonNumber = 0;
        } else {
            lessonNumber = getCurrentLessonNumber();
        }

        int dayOfWeek = CalendarManager.getDayOfWeek(dayOfSemestr);

        int weekState = CalendarManager.getWeekState(dayOfSemestr);
        LessonListElement result = DataManager.getInstance().getClosestLesson(weekState, dayOfWeek, lessonNumber);
        result.setDate(CalendarManager.getDate(dayOfSemestr));
        return result;
    }

    public LessonListElement getCurrentLesson(){
        LessonListElement result = null;
        GregorianCalendar calendar = new GregorianCalendar();
        int dayOfSemestr = CalendarManager.getDayOfSemestr(calendar.getTimeInMillis());
        int dayOfWeek = CalendarManager.getDayOfWeek(dayOfSemestr);
        int weekState = CalendarManager.getWeekState(dayOfSemestr);
        int lessonNumber = getCurrentLessonNumber();

        if(dayOfSemestr >= 0){
            result = DataManager.getInstance().getCurrentLesson(weekState, dayOfWeek, lessonNumber);
            result.setDate(CalendarManager.getDate(dayOfSemestr));
        }

        return result;
    }

    public int getCurrentLessonNumber(){
        int result = 9;
        GregorianCalendar calendar = new GregorianCalendar();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);

        int mod = hour * 60 + minutes;  // minute of day

        if(mod >= getMinutesOfDay(0, 0) & mod <= getMinutesOfDay(9, 50)){     //00.00 - 9.50
            result = 1;
        } else if(mod >= getMinutesOfDay(9, 50) & mod <= getMinutesOfDay(11, 35)){ //9.50 - 11.35
            result = 2;
        } else if(mod >= getMinutesOfDay(12, 5) & mod <= getMinutesOfDay(13, 35)){ //12.05 - 13.35
            result = 3;
        } else if(mod >= getMinutesOfDay(13, 35) & mod <= getMinutesOfDay(15, 20)) { //13.35 - 15.20
            result = 4;
        } else if(mod >= getMinutesOfDay(15, 20) & mod <= getMinutesOfDay(17, 00)){  //15.20 - 17.00
            result = 5;
        } else if(mod >= getMinutesOfDay(17, 0) & mod <= getMinutesOfDay(18, 40)){  //17.00 - 18.40
            result = 6;
        } else if(mod >= getMinutesOfDay(18, 40) & mod <= getMinutesOfDay(20, 20)){  //18.40 - 20.20
            result = 7;
        } else if(mod >= getMinutesOfDay(20, 20) & mod <= getMinutesOfDay(22, 0)){  //20.20 - 22.00
            result = 8;
        }

        return result;
    }

    /**Return current minute of day**/
    private int getMinutesOfDay(int hour, int minutes){
        return hour * 60 + minutes;
    }
	
}
