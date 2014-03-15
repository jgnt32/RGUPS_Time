package ru.rgups.time.adapters;

import ru.rgups.time.R;
import ru.rgups.time.model.LessonTableModel;
import ru.rgups.time.model.entity.LessonInformation;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TeacherLessonListAdapter extends CursorAdapter implements StickyListHeadersAdapter{

	private LayoutInflater mInflater;
	
	public TeacherLessonListAdapter(Context context, Cursor c, boolean autoRequery) {
		super(context, c, autoRequery);
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public void bindView(View v, Context context, Cursor c) {
		final TextView lessonTitle = (TextView) v.findViewById(R.id.teachers_lesson_title);
		final TextView room = (TextView) v.findViewById(R.id.teachers_lesson_room);
		
		lessonTitle.setText(c.getString(c.getColumnIndex(LessonInformation.LESSON_TITLE)));
		room.setText(c.getString(c.getColumnIndex(LessonInformation.ROOM)));

	}

	@Override
	public View newView(Context context, Cursor c, ViewGroup arg2) {
		return mInflater.inflate(R.layout.teacher_lesson_list_element, null);
	}

	@Override
	public View getHeaderView(int position, View convertView, ViewGroup parent) {
		return mInflater.inflate(R.layout.lesson_list_divier, null);
	}

	@Override
	public long getHeaderId(int position) {
		return getCursor().getInt(getCursor().getColumnIndex(LessonTableModel.DAY));
	}

}
