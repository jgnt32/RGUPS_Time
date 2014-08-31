package ru.rgups.time.adapters;

import java.util.ArrayList;

import ru.rgups.time.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class HomeWorkImageAdapter extends BaseAdapter{
	
	private LayoutInflater mInflater;
	private ArrayList<String> mList;
    DisplayImageOptions mImageOptions = new DisplayImageOptions.Builder()
            .cacheInMemory(true)
    //        .cacheOnDisk(true)
            .build();
	
	public HomeWorkImageAdapter(Context context, ArrayList<String> list) {
		mList = list;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
 
	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public String getItem(int position) {
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
        ImageLoader.getInstance().displayImage(getItem(position), imageView, mImageOptions);
		return v;
	}
	
	

}
