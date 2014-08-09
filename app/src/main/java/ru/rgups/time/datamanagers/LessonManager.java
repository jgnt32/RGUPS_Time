package ru.rgups.time.datamanagers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.crashlytics.android.internal.s;

import ru.rgups.time.model.DataManager;
import ru.rgups.time.model.HelperManager;
import ru.rgups.time.model.HomeWork;
import ru.rgups.time.model.LessonListElement;
import ru.rgups.time.model.LessonTableModel;
import ru.rgups.time.model.entity.LessonInformation;
import ru.rgups.time.model.entity.OverLine;
import ru.rgups.time.model.entity.UnderLine;
import ru.rgups.time.utils.CalendarManager;
import ru.rgups.time.utils.ConstUtils;
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
	
	
	
	public ArrayList<LessonListElement> getLessonsBySemestrDayNumber(int dayNumber){
		
		return DataManager.getInstance().getLessonList(CalendarManager.getDayOfWeek(dayNumber),
                CalendarManager.getWeekState(dayNumber));
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
	
	public boolean [] getHomeworLessonListFragment(long timestamp, ArrayList<LessonListElement> lessonList){
		boolean [] result = new boolean [lessonList.size()];
		int i = 0;
		for(LessonListElement lesson : lessonList){
			result[i] = DataManager.getInstance().lessonHasHomeWork(lesson.getId(), timestamp);
			i++;
			
		}
		
		return result;
	}
	
	public int [] getHomeWorkVector(){
		int [] result = new int [CalendarManager.getCorrectDayCount()];
		Cursor c = getHomeWorkCursor();
		
		while(c.moveToNext()){
			mCalendar.setTimeInMillis(c.getLong(c.getColumnIndex(HomeWork.DATE)));
			result[mCalendar.get(GregorianCalendar.DAY_OF_YEAR) - CalendarManager.getDayOffset()] = c.getInt(c.getColumnIndex("cnt"));
		}

		return result;
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
	
	
	public long getTimeStampBySemestrDayNumber(int dayNumber){
		mCalendar.setTime(new Date(0));
		mCalendar.set(GregorianCalendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
		mCalendar.set(GregorianCalendar.DAY_OF_YEAR, CalendarManager.getCurrentDayOfTheYear());
		return mCalendar.getTimeInMillis();
	}
	
}
