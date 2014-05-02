package ru.rgups.time.datamanagers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import ru.rgups.time.model.DataManager;
import ru.rgups.time.model.HelperManager;
import ru.rgups.time.model.HomeWork;
import ru.rgups.time.model.LessonListElement;
import ru.rgups.time.model.entity.OverLine;
import ru.rgups.time.model.entity.UnderLine;
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
	
	public final static int CURRENS_SEMESTR = spotSemestr();//

	public final static int DAY_COUNT = getCorrectDayCount();
	
	public final static int DAY_OFFSET = getDayOffset(); 
	
	public final static boolean UPARITY_WEEK_IS_OVERLINE = getPointOfReference();

	
	public static int getCorrectDayCount(){
		GregorianCalendar calendar = new GregorianCalendar();
		if(CURRENS_SEMESTR == ConstUtils.FIRST_SEMESTR){
			return 122;						//первый семестр
		}else{
			if(calendar.getMaximum(Calendar.DAY_OF_YEAR)==355){ //второй семестр
				return 212;
			}else{
				return 213;//високосний год
			}
		}
		
	}
	
	
	public static int spotSemestr(){
		int month = Calendar.getInstance().get(Calendar.MONTH);
		if(month >= Calendar.SEPTEMBER){
			return ConstUtils.FIRST_SEMESTR;
		} else {
			return ConstUtils.SECOND_SEMESTR;
		}

	}
	
	private static int getDayOffset(){
		if(CURRENS_SEMESTR == ConstUtils.FIRST_SEMESTR){
			GregorianCalendar calendar = new GregorianCalendar();
					
			calendar.setTime(Calendar.getInstance().getTime());
			int dayOffset = calendar.getMaximum(Calendar.DAY_OF_YEAR) - DAY_COUNT;
			return dayOffset;
		}else{
			return 1;  //because first position = 0
		}
	
	}
	
	private static boolean getPointOfReference(){
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(Calendar.getInstance().getTime());
		calendar.setFirstDayOfWeek(GregorianCalendar.MONDAY);
		calendar.setMinimalDaysInFirstWeek(4);
		calendar.set(Calendar.MONTH, Calendar.SEPTEMBER);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		
		int firstWeekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
		
		if(calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY){
			if(weekIsParity(firstWeekOfYear)){
				return false;
			}else{
				return true;
			}
						
		}else{
			if(weekIsParity(firstWeekOfYear)){
				return true;
			}else{
				return false;
			}
		}
		
	}
	
	public static boolean weekIsParity(int weekNumber){
		if(weekNumber % 2 == 0){
			return true;
		} else {
			return false;
		}
	}
	
	private boolean isOverLine(final Date date){
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setFirstDayOfWeek(GregorianCalendar.MONDAY);
		calendar.setMinimalDaysInFirstWeek(4);
		calendar.setTime(date);
		int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);	
		boolean currentWeekIsParity = (weekOfYear % 2) == 0;

		if(UPARITY_WEEK_IS_OVERLINE){
			if(currentWeekIsParity){
				return false;
			}else{
				return true;
			}
		}else{
			if(currentWeekIsParity){
				return true;
			}else{
				return false;
			}
		}
	}
	
	
	public int getWeekState(int dayOfYear){		
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.set(GregorianCalendar.DAY_OF_YEAR, dayOfYear);
		if(this.isOverLine(calendar.getTime())){
			return OverLine.WEEK_STATE;
		}else{
			return UnderLine.WEEK_STATE;
		}
	}
	
	
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
	
	public ArrayList<LessonListElement> getLessonsBySemestrDayNumber(int dayNumber){
		mCalendar.setTime(Calendar.getInstance().getTime());
		mCalendar.set(GregorianCalendar.DAY_OF_YEAR, dayNumber + DAY_OFFSET);
		
		return DataManager.getInstance().getLessonList(getDayOfWeek(dayNumber), getWeekState(dayNumber + DAY_OFFSET));
	}
	
	public int getDayOfWeek(int dayOfSemestr){
		mCalendar.setTime(Calendar.getInstance().getTime());
		mCalendar.set(GregorianCalendar.DAY_OF_YEAR, dayOfSemestr + DAY_OFFSET);
	
		if(mCalendar.get(GregorianCalendar.DAY_OF_WEEK) == GregorianCalendar.SUNDAY){
			return 7;
		}else{
			return mCalendar.get(GregorianCalendar.DAY_OF_WEEK)-1;
		}
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
	
	
	public int [] getHomeWorkVector(){
		int [] result = new int [DAY_COUNT];
		Cursor c = getHomeWorkCursor();
		
		while(c.moveToNext()){
			mCalendar.setTimeInMillis(c.getLong(c.getColumnIndex(HomeWork.DATE)));
			result[mCalendar.get(GregorianCalendar.DAY_OF_YEAR)-DAY_OFFSET] = c.getInt(c.getColumnIndex("cnt"));
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
		mCalendar.set(GregorianCalendar.DAY_OF_YEAR, dayNumber + DAY_OFFSET);
		return mCalendar.getTimeInMillis();
	}
	
}
