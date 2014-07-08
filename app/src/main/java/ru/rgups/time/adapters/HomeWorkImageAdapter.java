package ru.rgups.time.adapters;

import java.util.ArrayList;

import ru.rgups.time.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class HomeWorkImageAdapter extends BaseAdapter{
	
	private LayoutInflater mInflater;
	private ArrayList<Bitmap> mList;
	
	public HomeWorkImageAdapter(Context context, ArrayList<Bitmap> list) {
		mList = list;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
 
	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Bitmap getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = mInflater.inflate(R.layout.home_work_grid_element, null);
		ImageView imageView = (ImageView) v.findViewById(R.id.home_work_grid_element);
		imageView.setImageBitmap(getItem(position));
		return v;
	}
	
	

}
