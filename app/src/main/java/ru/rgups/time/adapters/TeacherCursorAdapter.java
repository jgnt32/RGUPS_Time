package ru.rgups.time.adapters;

import ru.rgups.time.R;
import ru.rgups.time.model.entity.LessonInformation;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TeacherCursorAdapter extends StickyListHeadersCursorAdapter {
	
	private LayoutInflater mInflater;
	
	public TeacherCursorAdapter(Context context, Cursor c, boolean autoRequery) {
		super(context, c, autoRequery);
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public void bindView(View v, Context context, Cursor c) {
		TextView name = (TextView) v.findViewById(R.id.list_element_text);
		name.setText(c.getString(c.getColumnIndex(LessonInformation.TEACHER_NAME)));
	}

	@Override
	public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {
		return mInflater.inflate(R.layout.simple_list_element, null);
	}
	
	public String getTeacherName(int position){
        getCursor().moveToPosition(position);
        return getCursor().getString(getCursor().getColumnIndex(LessonInformation.TEACHER_NAME));
	}

	@Override
	protected View newHeaderView(Context context, Cursor cursor, ViewGroup parent) {
		return mInflater.inflate(R.layout.group_list_divier, null);
	}

	@Override
	protected void bindHeaderView(View v, Context context, Cursor c) {
		final TextView text = (TextView) v.findViewById(R.id.levelTitle);
		text.setText(c.getString(c.getColumnIndex(LessonInformation.TEACHER_NAME)).substring(0, 1));
	}

	@Override
	public long getHeaderId(Cursor c) {
		return c.getString(c.getColumnIndex(LessonInformation.TEACHER_NAME)).substring(0, 1).hashCode();
	}

}
