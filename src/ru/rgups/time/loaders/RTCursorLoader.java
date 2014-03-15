package ru.rgups.time.loaders;

import ru.rgups.time.model.DataManager;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;

public class RTCursorLoader extends CursorLoader{

	public RTCursorLoader(Context context) {
		super(context);

	}
	

	@Override
	public Cursor loadInBackground() {
		return DataManager.getInstance().getAllTeachersCursor();
	}
}
