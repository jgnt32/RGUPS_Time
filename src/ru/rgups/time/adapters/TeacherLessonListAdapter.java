package ru.rgups.time.adapters;

import ru.rgups.time.R;
import ru.rgups.time.model.LessonTableModel;
import ru.rgups.time.model.entity.LessonInformation;
import se.emilsjolander.stickylistheaders.BaseCursorAdapter;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TeacherLessonListAdapter extends BaseCursorAdapter{

	
	private LayoutInflater mInflater;
	private Cursor mCursor;
	
	private String[] mTime;
	
	
	public TeacherLessonListAdapter(Context context, Cursor c,
			boolean autoRequery) {
		super(context, c, autoRequery);
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mTime = context.getResources().getStringArray(R.array.lessons_time_periods);
	}




	@Override
	protected View newHeaderView(Context context, Cursor cursor, ViewGroup parent) {
		return mInflater.inflate(R.layout.lesson_list_divier, null);
	}
	
	@Override
	protected void bindHeaderView(View v, Context context, Cursor c) {
		final TextView number = (TextView) v.findViewById(R.id.divider_text);
		final TextView time = (TextView) v.findViewById(R.id.lesson_divider_time);
	
		number.setText(c.getInt(c.getColumnIndex(LessonTableModel.NUMBER))+"-я пара");
		time.setText(mTime[c.getInt(c.getColumnIndex(LessonTableModel.NUMBER))-1]);
		
		
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
	}
	@Override
	public long getHeaderId(Cursor c) {
		return c.getLong(c.getColumnIndex(LessonTableModel.NUMBER));
	}
	
}
