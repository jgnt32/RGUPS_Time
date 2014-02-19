package ru.rgups.time.model;

import java.util.ArrayList;

import ru.rgups.time.model.entity.Day;
import ru.rgups.time.model.entity.DoubleLine;
import ru.rgups.time.model.entity.Lesson;
import ru.rgups.time.model.entity.LessonInformation;
import ru.rgups.time.model.entity.LessonList;
import ru.rgups.time.model.entity.OverLine;
import ru.rgups.time.model.entity.UnderLine;
import ru.rgups.time.spice.TimeTableRequest;
import ru.rgups.time.utils.PreferenceManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.text.TextUtils;
import android.util.Log;

import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.request.listener.RequestListener;

public class DataManager {
	
	private SQLiteStatement mSaveLessonStatement;
	private SQLiteDatabase mDb;
	
	public DataManager() {
		mDb = HelperManager.getHelper().getWritableDatabase();
		
		mSaveLessonStatement = mDb.compileStatement(
				TextUtils.concat(
				"INSERT OR REPLACE INTO ",LessonTableModel.TABLE_NAME,"(",
				LessonTableModel.DAY,",",			//1
				LessonTableModel.NUMBER,",",		//2
				LessonTableModel.GROUP_ID,",",		//3
				LessonTableModel.WEEK_STATE,",",	//4
				LessonTableModel.LESSON_TITLE,",",	//5
				LessonTableModel.LESSON_TYPE,",",	//6
				LessonTableModel.TEACHER_NAME,", ",	//7
				LessonTableModel.ROOM,
				") VALUES (?,?,?,?,?,?,?,?)"
						).toString());
	}
	
	private SpiceManager mSpiceManager;
	
	private static DataManager mInstance;
	
	public static DataManager getInstance(){
		if(mInstance == null){
			mInstance = new DataManager();
		}
		return mInstance;
	}
	
	

	public SpiceManager getSpiceManager() {
		return mSpiceManager;
	}

	public void setSpiceManager(SpiceManager mSpiceManager) {
		this.mSpiceManager = mSpiceManager;
	}
	
	public void timeTableRequest(RequestListener<LessonList> listener){
		this.getSpiceManager().execute(new TimeTableRequest(PreferenceManager.getInstance().getGroupId().toString()), listener);
	}
	
	public void saveLessons(LessonList lessonList, long groupId){
		Log.e("saveLessons", " lessonList "+lessonList.getDays().size());

		try{
			mDb.beginTransaction();
			for(Day day : lessonList.getDays()){
				for(Lesson lesson : day.getLessons()){
					if(lesson.getDoubleLine() != null){
						for(DoubleLine doubleLine : lesson.getDoubleLine()){
							Log.e("saveLessons", " doubleLine "+doubleLine.getTitle());
							mSaveLessonStatement.clearBindings();
							mSaveLessonStatement.bindLong(1, day.getNumber());
							mSaveLessonStatement.bindLong(2, lesson.getNumber());
							mSaveLessonStatement.bindLong(3, groupId);
							mSaveLessonStatement.bindLong(4, doubleLine.WEEK_STATE);
							
						
							if(doubleLine.getTitle() != null){
								mSaveLessonStatement.bindString(5, doubleLine.getTitle());
							}							
							if(doubleLine.getType() != null){
								mSaveLessonStatement.bindString(6, doubleLine.getType());
							}

							if(doubleLine.getTeacher() != null){
								mSaveLessonStatement.bindString(7, doubleLine.getTeacher());
							}							
							
							if(doubleLine.getRoom() != null){
								mSaveLessonStatement.bindString(8, doubleLine.getRoom());
							}
							
							mSaveLessonStatement.execute();
						}
					}
					
					if(lesson.getUnderLine() != null){

						for(UnderLine underLine : lesson.getUnderLine()){
							Log.e("saveLessons", " underLine "+underLine.getTitle());
	
							mSaveLessonStatement.clearBindings();
							mSaveLessonStatement.bindLong(1, day.getNumber());
							mSaveLessonStatement.bindLong(2, lesson.getNumber());
							mSaveLessonStatement.bindLong(3, groupId);
							mSaveLessonStatement.bindLong(4, underLine.WEEK_STATE);
							if(underLine.getTitle() != null){
								mSaveLessonStatement.bindString(5, underLine.getTitle());
							}							
							if(underLine.getType() != null){
								mSaveLessonStatement.bindString(6, underLine.getType());
							}

							if(underLine.getTeacher() != null){
								mSaveLessonStatement.bindString(7, underLine.getTeacher());
							}	
						
							if(underLine.getRoom() != null){
								mSaveLessonStatement.bindString(8, underLine.getRoom());
							}
							
							
							mSaveLessonStatement.execute();
						}
					}
					
					if(lesson.getOverLine() != null){
						for(OverLine overLine : lesson.getOverLine()){
							Log.e("saveLessons", " overLine "+overLine.getTitle());
							
							mSaveLessonStatement.clearBindings();
							
							mSaveLessonStatement.bindLong(1, day.getNumber());
							mSaveLessonStatement.bindLong(2, lesson.getNumber());
							mSaveLessonStatement.bindLong(3, groupId);
							mSaveLessonStatement.bindLong(4, overLine.WEEK_STATE);
							
							if(overLine.getTitle() != null){
								mSaveLessonStatement.bindString(5, overLine.getTitle());
							}							
							if(overLine.getType() != null){
								mSaveLessonStatement.bindString(6, overLine.getType());
							}

							if(overLine.getTeacher() != null){
								mSaveLessonStatement.bindString(7, overLine.getTeacher());
							}	
						
							if(overLine.getRoom() != null){
								mSaveLessonStatement.bindString(8, overLine.getRoom());
							}
							
							
							mSaveLessonStatement.execute();
						}
					}
				}
			}
			mDb.setTransactionSuccessful();
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			mDb.endTransaction();
		}
		
	}
	
	public ArrayList<LessonListElement> getLessonList(final Integer dayNumber, final Integer weekState){
		Log.e("getLessonList","weekState  ="+weekState);
		ArrayList<LessonListElement> result = new ArrayList<LessonListElement>();
		Cursor c = mDb.rawQuery(TextUtils.concat(
				"SELECT * FROM ",LessonTableModel.TABLE_NAME," WHERE ",
				LessonTableModel.GROUP_ID," =? AND ",
				LessonTableModel.DAY,"=?", " AND ",
				"(",LessonTableModel.WEEK_STATE,"=? OR " ,
				LessonTableModel.WEEK_STATE,"='",Integer.toString(DoubleLine.WEEK_STATE),"')",
				" GROUP BY ",LessonTableModel.NUMBER, " ORDER BY ",LessonTableModel.NUMBER
				).toString(), new String[]{
			PreferenceManager.getInstance().getGroupId().toString(),
			dayNumber.toString(), weekState.toString()});
		LessonListElement lesson;
		while(c.moveToNext()){
				lesson = new LessonListElement();
				lesson.setDayNumber(c.getInt(c.getColumnIndex(LessonTableModel.DAY)));
				lesson.setLessonNumber(c.getInt(c.getColumnIndex(LessonTableModel.NUMBER)));	
				lesson.setInformation(getLessonInformation(dayNumber, c.getInt(c.getColumnIndex(LessonTableModel.NUMBER)), weekState));
				result.add(lesson);
				Log.e("getLessonList",c.getInt(c.getColumnIndex(LessonTableModel.NUMBER))+" "+c.getString(c.getColumnIndex(LessonTableModel.LESSON_TITLE))
						+" "+c.getString(c.getColumnIndex(LessonTableModel.WEEK_STATE)));

		}

		return result;
	}
	
	private ArrayList<LessonInformation> getLessonInformation (Integer dayNumber, Integer lessonNumber, Integer weekState){
		ArrayList<LessonInformation> result = new ArrayList<LessonInformation>();
		Cursor c = mDb.rawQuery(TextUtils.concat(
				"SELECT * FROM ",LessonTableModel.TABLE_NAME, " WHERE ",
				LessonTableModel.GROUP_ID," ='",PreferenceManager.getInstance().getGroupId().toString(),"' AND ",
				LessonTableModel.DAY,"=? AND ",LessonTableModel.WEEK_STATE,"=?"//LessonTableModel.NUMBER,"=?" //AND ",
				).toString(), new String[]{
			
			dayNumber.toString(), weekState.toString()});
		LessonInformation inf;
		while(c.moveToNext()){
			inf = new LessonInformation();
			inf.setTitle(c.getString(c.getColumnIndex(LessonTableModel.LESSON_TITLE)));
			inf.setTeacher(c.getString(c.getColumnIndex(LessonTableModel.TEACHER_NAME)));
			inf.setRoom(c.getString(c.getColumnIndex(LessonTableModel.ROOM)));
			inf.setType(c.getString(c.getColumnIndex(LessonTableModel.LESSON_TYPE)));
			result.add(inf);
		}
		
		Log.e("getLessonInformation",""+result.size());
		return result;
	}
	
	public void getAllLessons(){
		Cursor c = mDb.rawQuery("SELECT * FROM "+LessonTableModel.TABLE_NAME, new String[]{});
		while(c.moveToNext()){
			Log.e("getAllLessons", " "+c.getString(0)+" "+c.getString(1)+" "+c.getString(2)+" "+c.getString(3)+" "+c.getString(4)
					+" "+c.getString(5)+" "+c.getString(6)+" "+c.getString(7)+" "+c.getString(8));
		}
	}

}
