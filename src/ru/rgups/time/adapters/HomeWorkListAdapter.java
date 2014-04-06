package ru.rgups.time.adapters;

import java.util.ArrayList;

import ru.rgups.time.R;
import ru.rgups.time.model.HomeWork;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HomeWorkListAdapter extends BaseAdapter implements StickyListHeadersAdapter{
	
	public static final String DIVIDER_DATE_FORMAT = "d MMMM, EEEE";
	
	private LayoutInflater mInflater;
	private ArrayList<HomeWork> mList;
	private View mView;
	private ViewHolder mViewHolder;
	private View mHeaderView;
	private HeaderHolder mHeaderHolder;
	
	public HomeWorkListAdapter(Context context, ArrayList<HomeWork> list) {
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mList = list;
	}
	
	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public HomeWork getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return getItem(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		mView = convertView;
		if(mView == null){
			mView = mInflater.inflate(R.layout.homework_list_elemetn, null);
			mViewHolder = new ViewHolder(mView);
			mView.setTag(mViewHolder);
		}else{
			mViewHolder = (ViewHolder) mView.getTag();
		}
		
		mViewHolder.lessonTitle.setText(""+getItem(position).getLessonTitle());
		mViewHolder.message.setText(getItem(position).getMessage());
//		mViewHolder.photoCount.setText(""+getItem(position).getImages().size());
		return mView;
	}


	@Override
	public View getHeaderView(int position, View convertView, ViewGroup parent) {
		mHeaderView = convertView;
		if(mHeaderView == null){
			mHeaderView = mInflater.inflate(R.layout.home_work_list_divier, null);
			mHeaderHolder = new HeaderHolder(mHeaderView);
			mHeaderView.setTag(mHeaderHolder);
		}else{
			mHeaderHolder = (HeaderHolder) mHeaderView.getTag();
		}
		
		mHeaderHolder.text.setText(DateFormat.format(DIVIDER_DATE_FORMAT, getItem(position).getDate()));
		return mHeaderView;
	}


	@Override
	public long getHeaderId(int position) {
		return getItem(position).getDate().getTime();
	}
	
	private class ViewHolder{
		
		private TextView lessonTitle;
		private TextView message;
		private TextView photoCount;
		
		public ViewHolder(View v) {
			lessonTitle = (TextView) v.findViewById(R.id.homework_list_element_lesson_title);
			message = (TextView) v.findViewById(R.id.homework_list_element_message);
			photoCount = (TextView) v.findViewById(R.id.homework_list_element_photo_count);
		}
	}
	
	private class HeaderHolder{
		private TextView text;
		
		public HeaderHolder(View v) {
			text = (TextView) v.findViewById(R.id.divider_text);
		}
		
	}

}
