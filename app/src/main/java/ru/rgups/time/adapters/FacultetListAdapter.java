package ru.rgups.time.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.rgups.time.R;
import ru.rgups.time.model.entity.Facultet;

public class FacultetListAdapter extends CursorAdapter{

	private LayoutInflater mInflater;
	
	public FacultetListAdapter(Context context, Cursor c, boolean autoRequery) {
		super(context, c, autoRequery);
		mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);	
	}
	
	@Override
	public void bindView(View v, Context context, Cursor c) {
		final TextView text = (TextView) v.findViewById(R.id.facultetName);
		text.setText(c.getString(c.getColumnIndex(Facultet.TITLE)).trim());
	}
	
	@Override
	public View newView(Context context, Cursor c, ViewGroup v) {
	
		return mInflater.inflate(R.layout.facultet_list_element, null);
	}
	
	
	

}
