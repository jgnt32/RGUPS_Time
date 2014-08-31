package ru.rgups.time.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import ru.rgups.time.datamanagers.LessonManager;
import ru.rgups.time.fragments.HomeWorkListFragment;
import ru.rgups.time.model.entity.Day;
import ru.rgups.time.model.entity.DoubleLine;
import ru.rgups.time.model.entity.Facultet;
import ru.rgups.time.model.entity.Group;
import ru.rgups.time.model.entity.Lesson;
import ru.rgups.time.model.entity.LessonInformation;
import ru.rgups.time.model.entity.LessonList;
import ru.rgups.time.model.entity.OverLine;
import ru.rgups.time.model.entity.UnderLine;
import ru.rgups.time.utils.CalendarManager;
import ru.rgups.time.utils.NotificationManager;
import ru.rgups.time.utils.PreferenceManager;
import ru.rgups.time.utils.Slipper;
import android.content.Context;
import android.database.ContentObservable;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

public class DataManager extends ContentObservable{
	
	private SQLiteStatement mSaveLessonStatement;
	private SQLiteStatement mSaveInformationStatement;
	private SQLiteStatement mSaveHomeWorkStatement;
	private SQLiteDatabase mDb;
	private SQLiteStatement mDeleteStatement;
	private SQLiteStatement mDeleteInformationStatement;
	private SQLiteStatement mDeleteHomeWorkStatement;
	private SQLiteStatement mUpdateHomeWorkStatement;
	private SQLiteStatement mSetHomeWorkChecked;
	private SQLiteStatement mSaveGroupStatement;
	private SQLiteStatement mSaveFacultetStatement;


	
	public DataManager() {
		mDb = HelperManager.getHelper().getWritableDatabase();
		
		mSaveLessonStatement = mDb.compileStatement(
				TextUtils.concat(
				"INSERT OR REPLACE INTO ",LessonTableModel.TABLE_NAME,"(",
				LessonTableModel.DAY,",",			//1
				LessonTableModel.NUMBER,",",		//2
				LessonTableModel.GROUP_ID,",",		//3
				LessonTableModel.WEEK_STATE,", ",	//4
				LessonTableModel.ID,				//5
				") VALUES (?,?,?,?,?)"
						).toString());
		mDeleteStatement = mDb.compileStatement(TextUtils.concat(
				"DELETE FROM ",LessonTableModel.TABLE_NAME,
				" WHERE ",LessonTableModel.GROUP_ID,"=?").toString());
		
		mSaveInformationStatement = mDb.compileStatement(TextUtils.concat(
				"INSERT OR REPLACE INTO ", LessonInformation.TABLE_NAME, "(",
				LessonInformation.GROUP_ID,", ",	//1
				LessonInformation.LESSON_ID,", ",	//2
				LessonInformation.LESSON_TITLE,", ",//3
				LessonInformation.LESSON_TYPE,", ",	//4
				LessonInformation.TEACHER_NAME,", ",//5
				LessonInformation.ROOM,				//6
				") VALUES (?,?,?,?,?,?)"
				).toString());

		mSaveHomeWorkStatement = mDb.compileStatement(TextUtils.concat(
				"INSERT OR REPLACE INTO ", HomeWork.TABLE_NAME, " (",
				HomeWork.DATE,", ",		//1
				HomeWork.LESSON_ID,", ",//2
				HomeWork.MESSAGE,", ",	//3
				HomeWork.IMAGES,", ",	//4
				HomeWork.COMPLITE,", ",	//5
				HomeWork.GROUP_ID,
				") VALUES (?,?,?,?,?,?)"
				).toString());
		
		mUpdateHomeWorkStatement = mDb.compileStatement(TextUtils.concat(
				"UPDATE OR IGNORE ",HomeWork.TABLE_NAME," SET ",
				HomeWork.COMPLITE, " = ?, ",	//1
				HomeWork.MESSAGE, " = ?, ",		//2
                HomeWork.IMAGES, " = ? ",		//3

                " WHERE ",HomeWork.ID, " = ? "	//4
				).toString());
		
		mSetHomeWorkChecked = mDb.compileStatement(TextUtils.concat(
				"UPDATE OR IGNORE ",HomeWork.TABLE_NAME," SET ",
				HomeWork.COMPLITE, " = ? ",	//1
				" WHERE ",HomeWork.ID, " = ? "	//2
				).toString());
		
		mDeleteInformationStatement = mDb.compileStatement(TextUtils.concat(
				"DELETE FROM ",LessonInformation.TABLE_NAME," WHERE ",LessonInformation.GROUP_ID,"=?"
				).toString());

		mDeleteHomeWorkStatement = mDb.compileStatement(TextUtils.concat(
				"DELETE FROM ", HomeWork.TABLE_NAME, " WHERE ", HomeWork.ID ," = ?"
				).toString());
		
		mSaveGroupStatement = mDb.compileStatement(TextUtils.concat(
				"INSERT OR REPLACE INTO ",Group.TABLE_NAME," (",
				Group.ID,", ",
				Group.GROUP_TITLE,",",
				Group.LEVEL,", ",
				Group.FACULTET_ID,
				") VALUES (?,?,?,?)"
				).toString());
		
		mSaveFacultetStatement = mDb.compileStatement(TextUtils.concat(
				"INSERT OR REPLACE INTO ",Facultet.TABLE_NAME," (",
				Facultet.ID,",",
				Facultet.TITLE,") VALUES (?,?)"
				
				).toString());
	}
	
	
	
	
	private static DataManager mInstance;
	
	public static DataManager getInstance(){
		if(mInstance == null){
			mInstance = new DataManager();
		}
		return mInstance;
	}
	
	public void saveGroup(Collection<Group> list, Long facultetId){
		
		try{
			mDb.beginTransaction();
			for(Group group : list){
				mSaveGroupStatement.clearBindings();
				mSaveGroupStatement.bindLong(1, group.getId());
				mSaveGroupStatement.bindString(2, group.getTitle().trim());
				mSaveGroupStatement.bindLong(3, group.getLevel());
				mSaveGroupStatement.bindLong(4, facultetId);
				mSaveGroupStatement.execute();
			}
			mDb.setTransactionSuccessful();
		}catch (Exception e){
			e.printStackTrace();
		}
		finally{
			mDb.endTransaction();
            notifyObservers();
		}
		

	}

    public void notifyObservers(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            dispatchChange(true, null);
        } else {
            dispatchChange(true);

        }

    }
	
	public void saveFacultet(Collection<Facultet> list){
		
		try{
			mDb.beginTransaction();
			for(Facultet facultet : list){
				mSaveFacultetStatement.clearBindings();
				mSaveFacultetStatement.bindLong(1, facultet.getId());
				mSaveFacultetStatement.bindString(2, facultet.getName().trim());
				mSaveFacultetStatement.execute();
			}
			mDb.setTransactionSuccessful();
		}catch (Exception e){
			e.printStackTrace();
		}
		finally{
			mDb.endTransaction();
            notifyObservers();
		}
		

	}

	
	private void deleteOldLessons(long groupId){
		try{
			mDb.beginTransaction();
			mDeleteStatement.bindLong(1, groupId);
			mDeleteStatement.execute();
			mDeleteInformationStatement.bindLong(1, groupId);
			mDeleteInformationStatement.execute();
			mDb.setTransactionSuccessful();
		} catch (Exception e){			
			e.printStackTrace();
		}
		finally{
			mDb.endTransaction();
		}
	}
	
	public void saveLessons(LessonList lessonList, long groupId){
		deleteOldLessons(groupId);
	
		try{
			mDb.beginTransaction();
			for(Day day : lessonList.getDays()){
				for(Lesson lesson : day.getLessons()){

					if(lesson.getDoubleLine() != null){
						mSaveLessonStatement.bindLong(1, day.getNumber());
						mSaveLessonStatement.bindLong(2, lesson.getNumber());
						mSaveLessonStatement.bindLong(3, groupId);
						mSaveLessonStatement.bindLong(4, DoubleLine.WEEK_STATE);
						mSaveLessonStatement.bindLong(5, buildLessonId(day, lesson, DoubleLine.WEEK_STATE, groupId));
						mSaveLessonStatement.execute();

						for(DoubleLine doubleLine : lesson.getDoubleLine()){
							saveLessonInformation(doubleLine, buildLessonId(day, lesson, DoubleLine.WEEK_STATE, groupId), groupId);
						}
					}else{
				
			
						if(lesson.getUnderLine() != null){
							mSaveLessonStatement.bindLong(1, day.getNumber());
							mSaveLessonStatement.bindLong(2, lesson.getNumber());
							mSaveLessonStatement.bindLong(3, groupId);
							mSaveLessonStatement.bindLong(4, UnderLine.WEEK_STATE);
							mSaveLessonStatement.bindLong(5, buildLessonId(day, lesson, UnderLine.WEEK_STATE, groupId));
							mSaveLessonStatement.execute();

							for(UnderLine underLine : lesson.getUnderLine()){	
								saveLessonInformation(underLine, buildLessonId(day, lesson, UnderLine.WEEK_STATE, groupId), groupId);
							}
						}
						
						if(lesson.getOverLine() != null){
							mSaveLessonStatement.bindLong(1, day.getNumber());
							mSaveLessonStatement.bindLong(2, lesson.getNumber());
							mSaveLessonStatement.bindLong(3, groupId);
							mSaveLessonStatement.bindLong(4, OverLine.WEEK_STATE);
							mSaveLessonStatement.bindLong(5, buildLessonId(day, lesson, OverLine.WEEK_STATE, groupId));
							mSaveLessonStatement.execute();

							for(OverLine overLine : lesson.getOverLine()){
								saveLessonInformation(overLine, buildLessonId(day, lesson, OverLine.WEEK_STATE, groupId), groupId);
							}
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
            notifyObservers();
		}
		
	}
	
	
	public void writeToSD(Context context) {
	    File sd = Environment.getExternalStorageDirectory();

	    if (sd.canWrite()) {
	        String currentDBPath = HelperManager.DB_NAME;
	        String backupDBPath = "backupname.db";
			File dbFile = context.getApplicationContext().getDatabasePath(HelperManager.DB_NAME);
	        File backupDB = new File(sd, backupDBPath);

	        if (dbFile.exists()) {
                FileChannel src = null;
                try {
                    src = new FileInputStream(dbFile).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

	        }
	    }
	    Log.e("write to SD","writeToSD");
	}
	private Long buildLessonId(Day day, Lesson lesson, int weekState, long groupId ){
		long lessonId = Long.parseLong(TextUtils.concat(
				Integer.toString(day.getNumber()),
				Integer.toString(lesson.getNumber()),
				Integer.toString(weekState),
				Long.toString(groupId)
				).toString());
		return lessonId;
	}
	
	private void saveLessonInformation(Object information, long lessonId, long groupId){
		mSaveInformationStatement.clearBindings();

		if(information instanceof OverLine){
			OverLine over = (OverLine) information;
			mSaveInformationStatement.bindLong(1, groupId);
			mSaveInformationStatement.bindLong(2, lessonId);
			if(over.getTitle() != null){
				mSaveInformationStatement.bindString(3, over.getTitle());
			}
			
			if(over.getType() != null){
				mSaveInformationStatement.bindString(4, over.getType());
			}
			
			if(over.getTeacher() != null){
				mSaveInformationStatement.bindString(5, over.getTeacher().trim());
			}
			
			if(over.getRoom() != null){
				mSaveInformationStatement.bindString(6, over.getRoom());
			}
			
			mSaveInformationStatement.execute();
			
		}else{
			if(information instanceof UnderLine){
				UnderLine under = (UnderLine) information;
				
				mSaveInformationStatement.clearBindings();
				mSaveInformationStatement.bindLong(1, groupId);
				mSaveInformationStatement.bindLong(2, lessonId);
				
				if(under.getTitle() != null){
					mSaveInformationStatement.bindString(3, under.getTitle());
				}

				if(under.getType() != null){
					mSaveInformationStatement.bindString(4, under.getType());
				}

				if(under.getTeacher() != null){
					mSaveInformationStatement.bindString(5, under.getTeacher().trim());
				}

				if(under.getRoom() != null){
					mSaveInformationStatement.bindString(6, under.getRoom());
				}
				
				
				mSaveInformationStatement.execute();
			}else{
				if(information instanceof DoubleLine){
					DoubleLine doubleLine = (DoubleLine) information;
					mSaveInformationStatement.clearBindings();
					mSaveInformationStatement.bindLong(1, groupId);
					mSaveInformationStatement.bindLong(2, lessonId);
					
					if(doubleLine.getTitle() != null){
						mSaveInformationStatement.bindString(3, doubleLine.getTitle());
					}
					
					
					if(doubleLine.getType() != null){
						mSaveInformationStatement.bindString(4, doubleLine.getType());
					}
					
					
					if(doubleLine.getTeacher() != null){
						mSaveInformationStatement.bindString(5, doubleLine.getTeacher().trim());
					}
					
					
					if(doubleLine.getRoom() != null){
						mSaveInformationStatement.bindString(6, doubleLine.getRoom());
					}
					
					mSaveInformationStatement.execute();
				}
			}
		}
	}
	
	public ArrayList<LessonListElement> getLessonList(final Integer dayOfSemestr){
		ArrayList<LessonListElement> result = new ArrayList<LessonListElement>();

        int weekState = CalendarManager.getWeekState(dayOfSemestr);
        int dayOfWeek = CalendarManager.getDayOfWeek(dayOfSemestr);
		
		String query = TextUtils.concat(
				"SELECT * FROM ",LessonTableModel.TABLE_NAME," WHERE ",
				LessonTableModel.GROUP_ID," ='",PreferenceManager.getInstance().getGroupId().toString(),"' AND ",
				LessonTableModel.DAY,"=? AND ",
				"(",LessonTableModel.WEEK_STATE,"=? OR " ,
				LessonTableModel.WEEK_STATE,"='2')",
				" GROUP BY ",LessonTableModel.NUMBER, " ORDER BY ",LessonTableModel.NUMBER
				).toString();
		

		Cursor c = mDb.rawQuery(query, new String[]{Integer.toString(dayOfWeek), Integer.toString(weekState)});
		LessonListElement lesson;
        long date = CalendarManager.getDate(dayOfSemestr) / CalendarManager.MILISECONDS_PER_DAY;
		while(c.moveToNext()){
            lesson = getLessonFromCursor(c);
            lesson.setHasHomeWork(DataManager.getInstance().lessonHasHomeWork(lesson.getId(), date));
    		result.add(lesson);
			
		}

		return result;
	}

    private LessonListElement getLessonFromCursor(Cursor c) {
        LessonListElement lesson;
        lesson = new LessonListElement();
        lesson.setId(c.getLong(c.getColumnIndex(LessonTableModel.ID)));

        lesson.setDayNumber(c.getInt(c.getColumnIndex(LessonTableModel.DAY)));
        lesson.setLessonNumber(c.getInt(c.getColumnIndex(LessonTableModel.NUMBER)));
        lesson.setInformation(getLessonInformation(c.getLong(c.getColumnIndex(LessonTableModel.ID))));
        return lesson;
    }

    public ArrayList<LessonListElement> getLessonList(){
		ArrayList<LessonListElement> result = new ArrayList<LessonListElement>();
		
		String query = TextUtils.concat(
				"SELECT * FROM ",LessonTableModel.TABLE_NAME," WHERE ",
				LessonTableModel.GROUP_ID," ='",PreferenceManager.getInstance().getGroupId().toString(),
				"' ORDER BY ",LessonTableModel.NUMBER
				).toString();
		

		Cursor c = mDb.rawQuery(query, new String[]{});
		LessonListElement lesson;
		while(c.moveToNext()){
            lesson = getLessonFromCursor(c);
			result.add(lesson);

		}

		return result;
	}
	
	private ArrayList<LessonInformation> getLessonInformation (Long lessonId){
		ArrayList<LessonInformation> result = new ArrayList<LessonInformation>();
		String query = TextUtils.concat(
				"SELECT * FROM ",LessonInformation.TABLE_NAME, " WHERE ",
				LessonInformation.LESSON_ID,"=? ",
				" GROUP BY ",LessonInformation.TEACHER_NAME).toString();
		
		Cursor c = mDb.rawQuery(query, new String[]{lessonId.toString()});
		LessonInformation inf;
		
		while(c.moveToNext()){
		
			inf = new LessonInformation();
			inf.setTitle(c.getString(c.getColumnIndex(LessonInformation.LESSON_TITLE)));
			inf.setTeacher(c.getString(c.getColumnIndex(LessonInformation.TEACHER_NAME)));
			inf.setRoom(c.getString(c.getColumnIndex(LessonInformation.ROOM)));
			inf.setType(c.getString(c.getColumnIndex(LessonInformation.LESSON_TYPE)));
			result.add(inf);
		}
		
		return result;
	}
	
	/*public void getAllLessons(){
		Cursor c = mDb.rawQuery("SELECT * FROM "+LessonTableModel.TABLE_NAME, new String[]{});
		while(c.moveToNext()){
			Log.e("getAllLessons", " "+c.getString(0)+" "+c.getString(1)+" "+c.getString(2)+" "+c.getString(3)+" "+c.getString(4)
					+" "+c.getString(5)+" "+c.getString(6)+" "+c.getString(7)+" "+c.getString(8));
		}
	}*/
	
	public LessonListElement getLesson(Long lesonId){
		
		LessonListElement result = new LessonListElement();
		Cursor c = mDb.rawQuery(TextUtils.concat(
				"SELECT * FROM ",LessonTableModel.TABLE_NAME, " WHERE ",
				LessonTableModel.ID,"=?").toString(), new String[]{lesonId.toString()});
		
		c.moveToFirst();
		if(c.getCount() != 0){
			result.setId(c.getLong(c.getColumnIndex(LessonTableModel.ID)));
			result.setDayNumber(c.getInt(c.getColumnIndex(LessonTableModel.DAY)));
			result.setLessonNumber(c.getInt(c.getColumnIndex(LessonTableModel.NUMBER)));
			result.setInformation(getLessonInformation(lesonId));

		}
			
		return result;
	}
	
	public void saveHomeWork(HomeWork homeWork){
		
		mDb.beginTransaction();
		try{
			mSaveHomeWorkStatement.clearBindings();
			if(homeWork.getDate() != null){
				mSaveHomeWorkStatement.bindLong(1, homeWork.getDate().getTime() / CalendarManager.MILISECONDS_PER_DAY);
			}
			
			mSaveHomeWorkStatement.bindLong(2, homeWork.getLessonId());
			
			if(homeWork.getMessage() != null){
				mSaveHomeWorkStatement.bindString(3, homeWork.getMessage());
			}
			
			if(homeWork.getImages() != null){
				mSaveHomeWorkStatement.bindBlob(4, Slipper.serializeObject(homeWork.getImages()));
			}
			
	
			if(homeWork.isComplite()){
				mSaveHomeWorkStatement.bindLong(5, 1);				
			}else{
				mSaveHomeWorkStatement.bindLong(5, 0);
			}

			mSaveHomeWorkStatement.bindLong(6, PreferenceManager.getInstance().getGroupId());
			
			homeWork.setId(mSaveHomeWorkStatement.executeInsert());
			mDb.setTransactionSuccessful();
			NotificationManager.getInstance().addNewNotification(homeWork);
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			mDb.endTransaction();
		}
	}
	
	
	
	public HomeWork getHomeWork(Long id){
		HomeWork result = null;
		Cursor c = mDb.rawQuery(TextUtils.concat(
				"SELECT * FROM ",HomeWork.TABLE_NAME," WHERE ",HomeWork.ID,"=?"
				).toString(), 
				new String[]{id.toString()});
		if(c.moveToFirst()){
			result = getHomeWorkFromCursor(c);
		}
		return result;
	}

    private HomeWork getHomeWorkFromCursor(Cursor c){
        HomeWork result =  new HomeWork();
            result.setId(c.getLong(c.getColumnIndex(HomeWork.ID)));
            result.setDate(new Date(c.getLong(c.getColumnIndex(HomeWork.DATE)) * CalendarManager.MILISECONDS_PER_DAY));
            result.setLessonId(c.getLong(c.getColumnIndex(HomeWork.LESSON_ID)));
            result.setMessage(c.getString(c.getColumnIndex(HomeWork.MESSAGE)));
            result.setImages(Slipper.deserializeObjectToString(c.getBlob(c.getColumnIndex(HomeWork.IMAGES))));
            result.setComplite(c.getInt(c.getColumnIndex(HomeWork.COMPLITE))>0);
            result.setGroupId(c.getLong(c.getColumnIndex(HomeWork.GROUP_ID)));


        return result;
    }

	public ArrayList<HomeWork> getHomeWorkList(Long date, Long lessonId){
		ArrayList<HomeWork> result = new ArrayList<HomeWork>();

        String hwDate = Long.toString(date / CalendarManager.MILISECONDS_PER_DAY);
		String query = TextUtils.concat(
				"SELECT * FROM ",HomeWork.TABLE_NAME," WHERE ",
				HomeWork.DATE,"= ? AND ",HomeWork.LESSON_ID,"= ?"
				).toString();
		Cursor c = mDb.rawQuery(query, 
				new String[]{hwDate, lessonId.toString()});
		while(c.moveToNext()){

			result.add(getHomeWorkFromCursor(c));
		}
		return result;
	}
	
	
	public Cursor getHomeWorks(long status){
		Long groupId = PreferenceManager.getInstance().getGroupId();
		String query = TextUtils.concat(
				"SELECT h.",HomeWork.ID," as _id, h.*,l.*,i.* FROM ",HomeWork.TABLE_NAME," as h ",
				"INNER JOIN ",LessonInformation.TABLE_NAME," as i ON ",
				" h.",HomeWork.LESSON_ID," = i.",LessonInformation.LESSON_ID,
				" INNER JOIN ",Lesson.TABLE_NAME," as l ON ",
				" l.",Lesson.ID," = i.",LessonInformation.LESSON_ID,
				" WHERE h.",HomeWork.GROUP_ID,"=? ",
				" %status_condition ",
				" GROUP BY h.",HomeWork.ID,
				" ORDER BY h.",HomeWork.DATE, ", l.", Lesson.NUMBER
				).toString();


		Cursor c = mDb.rawQuery(query.replace("%status_condition", getStatusCondition(status)),
				new String[]{groupId.toString()});
		return c;
	}

    private String getStatusCondition(long status){
        String result = "";
        if(status == HomeWorkListFragment.COMPITED_HOME_WORKS){
            result = " AND "+HomeWork.COMPLITE+"= 1";
        } else if (status == HomeWorkListFragment.UNCOMPLITED_HOMEWORKS){
            result = " AND "+HomeWork.COMPLITE+"= 0";
        }
        return result;
    }


	
	public ArrayList<HomeWork> getAllHomeWorks(){
		Long groupId = PreferenceManager.getInstance().getGroupId();
		ArrayList<HomeWork> result = new ArrayList<HomeWork>();
		String query = TextUtils.concat(
				"SELECT h.*,l.* FROM ",HomeWork.TABLE_NAME," as h ",
				"INNER JOIN ",LessonInformation.TABLE_NAME," as l ON ",
				" h.",HomeWork.LESSON_ID,"=l.",LessonInformation.LESSON_ID,
				" WHERE h.",
				HomeWork.GROUP_ID,"=? AND h.",HomeWork.DATE,">=? ",
				
				" GROUP BY l.",LessonInformation.LESSON_ID,
				" ORDER BY h.",HomeWork.DATE
				).toString();
		Cursor c = mDb.rawQuery(query, 
				new String[]{groupId.toString(), Long.toString(Calendar.getInstance().getTimeInMillis())});
		while(c.moveToNext()){

			HomeWork hw = new HomeWork();
			hw.setId(c.getLong(c.getColumnIndex(HomeWork.ID)));
			hw.setDate(new Date(c.getLong(c.getColumnIndex(HomeWork.DATE))));
			hw.setLessonId(c.getLong(c.getColumnIndex(HomeWork.LESSON_ID)));
			hw.setMessage(c.getString(c.getColumnIndex(HomeWork.MESSAGE)));
			hw.setImages(Slipper.deserializeObjectToString(c.getBlob(c.getColumnIndex(HomeWork.IMAGES))));
			hw.setComplite(c.getInt(c.getColumnIndex(HomeWork.COMPLITE))>0);
			hw.setGroupId(c.getLong(c.getColumnIndex(HomeWork.GROUP_ID)));
			hw.setLessonTitle(c.getString(c.getColumnIndex(LessonInformation.LESSON_TITLE)));
			result.add(hw);
		}
		return result;
	}
	
	
	public ArrayList<Long> getTopicalHomeWorksIds(){
		ArrayList<Long> result = new ArrayList<Long>();
		
		String query = TextUtils.concat(
				"SELECT * FROM ",HomeWork.TABLE_NAME," WHERE ",
				HomeWork.GROUP_ID,"=? AND ",HomeWork.DATE,">=? AND ",HomeWork.COMPLITE,"='0' ORDER BY ",HomeWork.DATE
				).toString();
		
		Cursor c = mDb.rawQuery(query, 
				new String[]{PreferenceManager.getInstance().getGroupId().toString(),
				Long.toString(Calendar.getInstance().getTimeInMillis())});
		
		while(c.moveToNext()){
			result.add(c.getLong(c.getColumnIndex(HomeWork.ID)));
		}
		
		
		return result;
 	}
	
	public void updateHomeWork(HomeWork hw){
		try{
			mDb.beginTransaction();
			if(hw != null){
				
				mUpdateHomeWorkStatement.clearBindings();
				if(hw.isComplite()){
					mUpdateHomeWorkStatement.bindLong(1, 1);
					NotificationManager.getInstance().removeNotification(hw);

				}else{
					mUpdateHomeWorkStatement.bindLong(1, 0);
					NotificationManager.getInstance().addNewNotification(hw);

				}
				
				if(hw.getMessage() != null){
					mUpdateHomeWorkStatement.bindString(2, hw.getMessage());
				}

                if(hw.getMessage() != null){
                    mUpdateHomeWorkStatement.bindBlob(3, Slipper.serializeObject(hw.getImages()));
                }
				
				mUpdateHomeWorkStatement.bindLong(4, hw.getId());
				mUpdateHomeWorkStatement.execute();
			}
			mDb.setTransactionSuccessful();
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			mDb.endTransaction();
		}

	}
	
	public void deleteHomeWork(HomeWork hw){
		try{
			mDb.beginTransaction();
			if(hw != null){
				mDeleteHomeWorkStatement.bindLong(1, hw.getId());
				mDeleteHomeWorkStatement.execute();
			}
			
			mDb.setTransactionSuccessful();
			NotificationManager.getInstance().removeNotification(hw);
		}catch (Exception e){
			e.printStackTrace();
		}
		finally{
			mDb.endTransaction();
		}
	}
	
	public void setHomeWorkChecked(Long homeWorkId, boolean checked){
		try{
			mDb.beginTransaction();
			if(checked){
				mSetHomeWorkChecked.bindLong(1, 1);
			}else{
				mSetHomeWorkChecked.bindLong(1, 0);
			}
			
			mSetHomeWorkChecked.bindLong(2, homeWorkId);
			mSetHomeWorkChecked.execute();
			mDb.setTransactionSuccessful();
		} catch (Exception e){
			e.printStackTrace();
		}
		finally{
			mDb.endTransaction();
            notifyObservers();

        }
	}
	
	public int getHomeWorkCountAtDay(Long timestamp){
		Cursor c = mDb.rawQuery(TextUtils.concat(
				"SELECT * FROM ",HomeWork.TABLE_NAME,
				" WHERE ",
				HomeWork.DATE,"=? AND ",
				HomeWork.GROUP_ID,"=? AND ",HomeWork.COMPLITE,"='0'" 
				).toString(), new String[]{timestamp.toString(), PreferenceManager.getInstance().getGroupId().toString()});
		return c.getCount();
	}
	
	public boolean lessonHasHomeWork(Long lessonId, Long timestamp){
		Cursor c = mDb.rawQuery(TextUtils.concat(
				"SELECT * FROM ",HomeWork.TABLE_NAME,
				" WHERE ",
				HomeWork.LESSON_ID,"=? AND ",
				HomeWork.GROUP_ID,"=? ",
				" AND ",HomeWork.DATE,"=?"
				).toString(), new String[]{lessonId.toString(), 
			PreferenceManager.getInstance().getGroupId().toString(),
			timestamp.toString()});
		return c.getCount()>0;
	}

	
	
	public Cursor getAllTeachersCursor(){
		String query = TextUtils.concat(
				"SELECT ",LessonInformation.ID," as _id,",LessonInformation.TABLE_NAME,".* FROM ",LessonInformation.TABLE_NAME,
				" WHERE ",LessonInformation.TEACHER_NAME,"<>'' AND ",LessonInformation.TEACHER_NAME,"<>'..'",
				
				" GROUP BY ",LessonInformation.TEACHER_NAME,
				" ORDER BY ",LessonInformation.TEACHER_NAME
				).toString();
		return mDb.rawQuery(query, new String[]{});
	}
	
	public Cursor getFiltredTeachersCursor(String name){
		String query = TextUtils.concat(
				"SELECT ",LessonInformation.ID," as _id,",LessonInformation.TABLE_NAME,".* FROM ",LessonInformation.TABLE_NAME,
				" WHERE ",LessonInformation.TEACHER_NAME,"<>'' AND ",LessonInformation.TEACHER_NAME,"<>'..' AND ",
				LessonInformation.TEACHER_NAME," LIKE '%",name.toUpperCase(),"%'",
				" GROUP BY ",LessonInformation.TEACHER_NAME,
				" ORDER BY ",LessonInformation.TEACHER_NAME
				).toString();
		return mDb.rawQuery(query, new String[]{});
	}
	
	
	public Cursor getTeachersLessons(Integer dayNumber, Integer weekState, String teacherName){
		String query = TextUtils.concat(
				"SELECT l.",LessonTableModel.ID," as _id, l.*,i.*",
				
				" FROM ",LessonTableModel.TABLE_NAME," as l  ",
				"INNER JOIN ",LessonInformation.TABLE_NAME," AS i ON ",
				"l.",LessonTableModel.ID," = i.",LessonInformation.LESSON_ID,
				" WHERE ",
				LessonTableModel.DAY,"=?", " AND ",
				LessonInformation.TEACHER_NAME,"=? AND ",
				"(",LessonTableModel.WEEK_STATE,"= ? OR " ,
				LessonTableModel.WEEK_STATE,"='2')",
				" GROUP BY l.",LessonTableModel.NUMBER, " ORDER BY l.",LessonTableModel.NUMBER
				).toString();
		Cursor c = mDb.rawQuery(query, new String[]{dayNumber.toString(),teacherName, weekState.toString()});
		return c;
	}
	
	public boolean dayHasLesson(Integer dayNumber, Integer weekState, String teacherName){
		String query = TextUtils.concat(
				"SELECT l.",LessonTableModel.ID," as _id, l.*,i.*",
				
				" FROM ",LessonTableModel.TABLE_NAME," as l  ",
				"INNER JOIN ",LessonInformation.TABLE_NAME," AS i ON ",
				"l.",LessonTableModel.ID," = i.",LessonInformation.LESSON_ID,
				" WHERE ",
				LessonTableModel.DAY,"=?", " AND ",
				LessonInformation.TEACHER_NAME,"=? AND ",
				"(",LessonTableModel.WEEK_STATE,"= ? OR " ,
				LessonTableModel.WEEK_STATE,"='2')",
				" GROUP BY l.",LessonTableModel.NUMBER, " ORDER BY l.",LessonTableModel.NUMBER
				).toString();
		Cursor c = mDb.rawQuery(query, new String[]{dayNumber.toString(),teacherName, weekState.toString()});
		return c.getCount()>0;
	}
	
	public boolean dayHasLesson(Integer day, Integer weekState){
		String query = TextUtils.concat(
				"SELECT * FROM ",LessonTableModel.TABLE_NAME," WHERE ",
				LessonTableModel.GROUP_ID," =? AND ",
				LessonTableModel.DAY,"=?", " AND ",
				"(",LessonTableModel.WEEK_STATE,"=? OR " ,
				LessonTableModel.WEEK_STATE,"='2')",
				" GROUP BY ",LessonTableModel.NUMBER, " ORDER BY ",LessonTableModel.NUMBER
				).toString();
		

		Cursor c = mDb.rawQuery(query, new String[]{PreferenceManager.getInstance().getGroupId().toString(),
				day.toString(), weekState.toString()});
		return c.getCount()>0;
		
	}
	
	public String getCurrentGroupTitle(){
		Cursor c = mDb.rawQuery(TextUtils.concat(
				"SELECT * FROM ",Group.TABLE_NAME," WHERE ",Group.ID,"=?"
				).toString(), new String[]{PreferenceManager.getInstance().getGroupId().toString()});
		c.moveToFirst();
		if(c.getCount()>0){
			return c.getString(c.getColumnIndex(Group.GROUP_TITLE));
		}else{
			return "";
		}
	}
	
	public String getCurrentFacultetTitle(){
		Cursor c = mDb.rawQuery(TextUtils.concat(
				"SELECT * FROM ",Facultet.TABLE_NAME," WHERE ",Facultet.ID,"=?"
				).toString(), new String[]{PreferenceManager.getInstance().getFacultetId().toString()});
		c.moveToFirst();
		if(c.getCount()>0){
			return c.getString(c.getColumnIndex(Facultet.TITLE));
		}else{
			return "";
		}
	}
	
	public Cursor getFacultetList(){
		
		return mDb.rawQuery(TextUtils.concat(
				"SELECT ",Facultet.ID," as _id,* FROM ",Facultet.TABLE_NAME," WHERE ",
				Facultet.TITLE,"<> 'Лицей' AND ",Facultet.TITLE,"<> 'Техникум РГУПС' ",
				"ORDER BY ",Facultet.TITLE
				).toString(), new String[]{});
	}
	
	public ArrayList<Group> getGroupList(Long facultetId){
		ArrayList<Group> result = new ArrayList<Group>();
		Cursor c = mDb.rawQuery(TextUtils.concat(
				"SELECT * FROM ",Group.TABLE_NAME," WHERE ",Group.FACULTET_ID,"=?",
				" ORDER BY ",Group.LEVEL,", ",Group.GROUP_TITLE
				).toString(), new String[]{facultetId.toString()});
		Group group = null;
		while(c.moveToNext()){
			group = new Group();
			group.setId(c.getLong(c.getColumnIndex(Group.ID)));
			group.setTitle(c.getString(c.getColumnIndex(Group.GROUP_TITLE)));
			group.setLevel(c.getInt(c.getColumnIndex(Group.LEVEL)));
			group.setFacultetId(c.getLong(c.getColumnIndex(Group.FACULTET_ID)));
			result.add(group);
		}
		return result;
	}

    public LessonListElement getCurrentLesson(int weekState, int day, int number){
        LessonListElement result = null;
        Cursor c = getCursorCurrentLesson(weekState, day, number);
        if(c.moveToFirst()){
            result = getLessonFromCursor(c);
        }
        return result;
    }



    public LessonListElement getClosestLesson(int weekState, int day, int number){
        LessonListElement result = null;
        try {
            Cursor c;
            c = getCursorClosestLessonByNumber(weekState, day, number);
            if(c.moveToFirst()){                    //if has lesson today
                result = getLessonFromCursor(c);
            } else {
                c = getCursorClosestLessonByDay(weekState, day);
                if(c.moveToFirst()){                //if has lesson in other days on this week
                    result = getLessonFromCursor(c);
                } else {
                    c = getCursorClosestLessonFromOtherWeek(weekState);
                    if(c.moveToFirst()){            //if has lesson on other week
                        result = getLessonFromCursor(c);
                    }
                }
            }
            c.close();
        } catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

    private Cursor getCursorClosestLessonFromOtherWeek(int weekState) throws Exception {
        String query;
        Cursor c;
        query = TextUtils.concat(
                "SELECT * FROM ", LessonTableModel.TABLE_NAME,
                " WHERE ",
                "(", LessonTableModel.WEEK_STATE, "=? OR ", LessonTableModel.WEEK_STATE, "=2) AND ",
                LessonTableModel.GROUP_ID, "=? ",

                " ORDER BY ", LessonTableModel.DAY, ", ", LessonTableModel.NUMBER
        ).toString();

        c = mDb.rawQuery(query, new String[]{Integer.toString(invertWeekState(weekState)),
                PreferenceManager.getInstance().getGroupId().toString()});
        return c;
    }

    private Cursor getCursorClosestLessonByDay(int weekState, int day) {
        String query;
        Cursor c;
        query = TextUtils.concat(
                "SELECT * FROM ", LessonTableModel.TABLE_NAME,
                " WHERE ",
                "(", LessonTableModel.WEEK_STATE, "=? OR ", LessonTableModel.WEEK_STATE, "=2) AND ",
                LessonTableModel.DAY, ">? AND ",
                LessonTableModel.GROUP_ID, "=? ",

                " ORDER BY ", LessonTableModel.DAY, ", ", LessonTableModel.NUMBER
        ).toString();

        c = mDb.rawQuery(query, new String[]{Integer.toString(weekState), Integer.toString(day),
                PreferenceManager.getInstance().getGroupId().toString()});
        return c;
    }

    private Cursor getCursorClosestLessonByNumber(int weekState, int day, int number) {
        Cursor c;
        String query = TextUtils.concat(
                "SELECT * FROM ", LessonTableModel.TABLE_NAME,
                " WHERE ",
                "(", LessonTableModel.WEEK_STATE, "=? OR ", LessonTableModel.WEEK_STATE, "=2) AND ",
                LessonTableModel.DAY, ">=? AND ",
                LessonTableModel.NUMBER, ">? AND ",
                LessonTableModel.GROUP_ID, "=? ",


                " ORDER BY ", LessonTableModel.DAY, ", ", LessonTableModel.NUMBER

        ).toString();

        c = mDb.rawQuery(query, new String[]{
                Integer.toString(weekState), Integer.toString(day), Integer.toString(number),
                PreferenceManager.getInstance().getGroupId().toString()});
        return c;
    }

    private Cursor getCursorCurrentLesson(int weekState, int day, int number) {
        Cursor c;
        String query = TextUtils.concat(
                "SELECT * FROM ", LessonTableModel.TABLE_NAME,
                " WHERE ",
                "(", LessonTableModel.WEEK_STATE, "=? OR ", LessonTableModel.WEEK_STATE, "=2) AND ",
                LessonTableModel.DAY, "=? AND ",
                LessonTableModel.NUMBER, "=? AND ",
                LessonTableModel.GROUP_ID, "=? ",


                " ORDER BY ", LessonTableModel.DAY, ", ", LessonTableModel.NUMBER

        ).toString();

        c = mDb.rawQuery(query, new String[]{Integer.toString(weekState), Integer.toString(day), Integer.toString(number),
        PreferenceManager.getInstance().getGroupId().toString()});
        return c;
    }


    private int invertWeekState(int weekState) throws Exception {
        int result = -1;
        if(weekState == UnderLine.WEEK_STATE){
            result = OverLine.WEEK_STATE;
        } else {
            result = UnderLine.WEEK_STATE;
        }
        if(result == -1 ){
            throw new Exception ("Ivalid params weeksState. WeekState should be equel '0' or '1'.") ;
        }

        return result;
    }
	
}
