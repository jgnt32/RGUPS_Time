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

public class TeacherCursorAdapter extends CursorAdapter{
	
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

}
