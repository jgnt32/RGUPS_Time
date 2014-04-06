package ru.rgups.time.adapters;

import ru.rgups.time.R;
import ru.rgups.time.model.LessonTableModel;
import ru.rgups.time.model.entity.LessonInformation;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TeacherLessonListAdapter extends CursorAdapter{
	private LayoutInflater mInflater;	
	private String[] mTime;
	private String mHeaderNumber;

	public TeacherLessonListAdapter(Context context, Cursor c,
			boolean autoRequery) {
		super(context, c, autoRequery);
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mTime = context.getResources().getStringArray(R.array.lessons_time_periods);
		mHeaderNumber = context.getResources().getString(R.string.lesson_list_divider);

	}

	

	
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		return  mInflater.inflate(R.layout.lesson_list_element, null);
	}
	@Override
	public void bindView(View view, Context context, Cursor c) {
		final TextView teacher = (TextView) view.findViewById(R.id.lesson_teacher);
		final TextView room = (TextView) view.findViewById(R.id.lesson_room);
		final TextView lessonTitle = (TextView) view.findViewById(R.id.lesson_title);
		
		teacher.setText(c.getString(c.getColumnIndex(LessonInformation.TEACHER_NAME)));
		room.setText(c.getString(c.getColumnIndex(LessonInformation.ROOM)));
		lessonTitle.setText(c.getString(c.getColumnIndex(LessonInformation.LESSON_TITLE)));
		
		final TextView number = (TextView) view.findViewById(R.id.divider_text);
		final TextView time = (TextView) view.findViewById(R.id.lesson_divider_time);
	
		number.setText(mHeaderNumber.replace("#", Integer.toString(c.getInt(c.getColumnIndex(LessonTableModel.NUMBER)))));
		time.setText(mTime[c.getInt(c.getColumnIndex(LessonTableModel.NUMBER))-1]);
	}

	
}
