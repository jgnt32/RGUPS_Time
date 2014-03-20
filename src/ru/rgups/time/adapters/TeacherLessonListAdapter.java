package ru.rgups.time.adapters;

import ru.rgups.time.R;
import ru.rgups.time.model.LessonTableModel;
import ru.rgups.time.model.entity.LessonInformation;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TeacherLessonListAdapter extends BaseAdapter implements StickyListHeadersAdapter{

	private LayoutInflater mInflater;
	private Cursor mCursor;
	
	public TeacherLessonListAdapter(Context context, Cursor c) {
		mCursor = c;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}


	@Override
	public View getHeaderView(int position, View convertView, ViewGroup parent) {
		mCursor.move(position);

		View v = mInflater.inflate(R.layout.lesson_list_divier, null);
		return v;
	}

	@Override
	public long getHeaderId(int position) {
		mCursor.move(position);

		return mCursor.getLong(mCursor.getColumnIndex(LessonTableModel.NUMBER));
	}

	@Override
	public int getCount() {
		if(mCursor == null){
			return 0;
		}else{
			return mCursor.getCount();
	
		}
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		mCursor.move(position);
		return mCursor.getLong(mCursor.getColumnIndex("_id"));
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		mCursor.move(position);

		View v = mInflater.inflate(R.layout.teacher_lesson_list_element, null);
		final TextView lessonTitle = (TextView) v.findViewById(R.id.teachers_lesson_title);
		final TextView room = (TextView) v.findViewById(R.id.teachers_lesson_room);
		mCursor.move(position);
		lessonTitle.setText(mCursor.getString(mCursor.getColumnIndex(LessonInformation.LESSON_TITLE)));
		room.setText(mCursor.getString(mCursor.getColumnIndex(LessonInformation.ROOM)));		
		return v;
	}

	

	public void changeCursor(Cursor c){
		mCursor = c;
	}
	
	

}
