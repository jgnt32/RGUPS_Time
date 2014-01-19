package ru.rgups.time.datamanagers;

import ru.rgups.time.model.HelperManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.text.TextUtils;

public class LessonManager {
	private SQLiteDatabase mDb;
	private SQLiteStatement mSaveLessonStatement;
	private static LessonManager mInstance;
	
	public static LessonManager getInstance() {
		if(mInstance == null){
			mInstance = new LessonManager();
		}
		return mInstance;
	}
	
	public LessonManager() {
		mDb = HelperManager.getHelper().getWritableDatabase();
		
		mSaveLessonStatement = mDb.compileStatement(TextUtils.concat(
				"INSERT OR REPLACE "
				).toString());
		
	}
	
	public void saveLessons(){
		
	}

}
