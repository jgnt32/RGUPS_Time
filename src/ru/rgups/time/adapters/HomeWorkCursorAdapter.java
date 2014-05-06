package ru.rgups.time.adapters;

import ru.rgups.time.R;
import ru.rgups.time.model.HomeWork;
import ru.rgups.time.model.entity.LessonInformation;
import android.content.Context;
import android.database.Cursor;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

public class HomeWorkCursorAdapter extends StickyListHeadersCursorAdapter{

	public static final String DIVIDER_DATE_FORMAT = "d MMMM, EEEE";

	
	private LayoutInflater mInflater;

	public HomeWorkCursorAdapter(Context context, Cursor c, boolean autoRequery) {
		super(context, c, autoRequery);
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	@Override
	protected View newHeaderView(Context context, Cursor cursor,
			ViewGroup parent) {
		return mInflater.inflate(R.layout.home_work_list_divier, null);
	}

	@Override
	protected void bindHeaderView(View v, Context context, Cursor c) {
		final TextView date = (TextView) v.findViewById(R.id.divider_text);
		date.setText(DateFormat.format(DIVIDER_DATE_FORMAT,  c.getLong(c.getColumnIndex(HomeWork.DATE))));
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		return  mInflater.inflate(R.layout.homework_list_elemetn, parent, false);
	}

	@Override
	public void bindView(View v, Context context, Cursor c) {
		final TextView lessonTitle = (TextView) v.findViewById(R.id.homework_list_element_lesson_title);
		final TextView message = (TextView) v.findViewById(R.id.homework_list_element_message);
		final TextView photoCount = (TextView) v.findViewById(R.id.homework_list_element_photo_count);
		final CheckBox compliteBox = (CheckBox) v.findViewById(R.id.lesson_list_element_checkbox);
		
		lessonTitle.setText(c.getString(c.getColumnIndex(LessonInformation.LESSON_TITLE)));
		message.setText(c.getString(c.getColumnIndex(HomeWork.MESSAGE)));
		compliteBox.setChecked(c.getInt(c.getColumnIndex(HomeWork.COMPLITE)) > 0);
	
	}

	@Override
	public long getHeaderId(Cursor c) {
		return c.getLong(c.getColumnIndex(HomeWork.DATE));
	}

}
