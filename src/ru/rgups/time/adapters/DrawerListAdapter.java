package ru.rgups.time.adapters;

import ru.rgups.time.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class DrawerListAdapter extends BaseAdapter{
	public static enum FRAGMENTS {
		TIME_FRAGMENT,
		SETTING_FRAGMENT
	}
	public static final int TIME_FRAGMENT = 0;
	public static final int SETTING_FRAGMENT = 3;

	private String[] mTitles;
	private LayoutInflater mInflater;
	
	public DrawerListAdapter(Context context) {
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mTitles = context.getResources().getStringArray(R.array.drawer_titles);
	}
	
	@Override
	public int getCount() {
		return mTitles.length;
	}

	@Override
	public Object getItem(int position) {
		return mTitles[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = mInflater.inflate(R.layout.drawer_list_element, null);
		TextView text = (TextView) v.findViewById(R.id.drawer_text);
		text.setText(mTitles[position]);
		return v;
	}

}
