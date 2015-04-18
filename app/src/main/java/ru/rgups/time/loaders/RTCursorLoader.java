package ru.rgups.time.loaders;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;

import ru.rgups.time.model.DataManager;

public class RTCursorLoader extends CursorLoader{

	public RTCursorLoader(Context context) {
		super(context);

	}
	

	@Override
	public Cursor loadInBackground() {
		return DataManager.getInstance().getAllTeachersCursor();
	}
}
