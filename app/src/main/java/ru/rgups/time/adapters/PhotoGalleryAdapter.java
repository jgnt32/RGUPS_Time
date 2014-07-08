package ru.rgups.time.adapters;

import java.util.ArrayList;

import ru.rgups.time.R;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class PhotoGalleryAdapter extends BaseAdapter{
	private View mView;
	private LayoutInflater mInflater;
	private ViewHolder mHolder;
	private ArrayList<Drawable> mImages; 
	
	public PhotoGalleryAdapter(Context context) {
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mImages = new ArrayList<Drawable>();
		mImages.add(context.getResources().getDrawable(R.drawable.ic_content_new_attachment));
		mImages.add(context.getResources().getDrawable(R.drawable.ic_launcher));
		mImages.add(context.getResources().getDrawable(R.drawable.ic_pencil));
		mImages.add(context.getResources().getDrawable(R.drawable.ic_place));
		mImages.add(context.getResources().getDrawable(R.drawable.ic_content_new_attachment));

		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mImages.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		mView = convertView;
		if(mView == null){
			mView = mInflater.inflate(R.layout.photo_galary_element, null);
			mHolder = new ViewHolder(mView);
			mView.setTag(mHolder);
		}else{
			mHolder = (ViewHolder) mView.getTag();
		}
		
		mHolder.image.setImageDrawable(mImages.get(position));
		return mView;
	}

	private class ViewHolder{
		private ImageView image;
		
		public ViewHolder(View v) {
			image = (ImageView) v.findViewById(R.id.galery_element);
		}
	}

}
