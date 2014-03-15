package ru.rgups.time.adapters;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.view.ViewGroup;

public class TeacherLessonList extends CursorAdapter implements StickyListHeadersAdapter{

	public TeacherLessonList(Context context, Cursor c, boolean autoRequery) {
		super(context, c, autoRequery);
	}

	@Override
	public View getHeaderView(int position, View convertView, ViewGroup parent) {
		return null;
	}

	@Override
	public long getHeaderId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void bindView(View v, Context context, Cursor c) {
		
	}

	@Override
	public View newView(Context context, Cursor c, ViewGroup arg2) {
		// TODO Auto-generated method stub
		return null;
	}

}
