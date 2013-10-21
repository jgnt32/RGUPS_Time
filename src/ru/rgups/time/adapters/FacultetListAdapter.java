package ru.rgups.time.adapters;

import java.util.ArrayList;

import ru.rgups.time.R;
import ru.rgups.time.model.entity.Facultet;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class FacultetListAdapter extends BaseAdapter{
	private ArrayList<Facultet> mFacultetList;
	private Facultet mFacultet;
	private TextView mFacultetName;
	private TextView mFacultetId;
	private View mView;
	private Context mContext;
	private LayoutInflater mInflater;
	private ViewHolder mHolder;
	
	public FacultetListAdapter(Context context, ArrayList<Facultet> facultetList ){
		this.mContext = context;
		this.mFacultetList = facultetList;
		mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);	
	}
	
	
	@Override
	public int getCount() {
		return mFacultetList.size();
	}

	@Override
	public Facultet getItem(int position) {
		return mFacultetList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return mFacultetList.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		mView = convertView;
		if (mView == null) {
			mView = mInflater.inflate(R.layout.facultet_list_element, parent, false);
			mHolder = new ViewHolder(mView);
			mView.setTag(mHolder);
		}else{
			mHolder = (ViewHolder) mView.getTag();
		}
		this.mFacultet = getItem(position);
		this.mFacultetName = mHolder.getmFacultetName();
		this.mFacultetName.setText(mFacultet.getName());
		return mView;
	}
	

	static class ViewHolder{
		private TextView mFacultetName;
		
		public ViewHolder(View view){
			this.setmFacultetName((TextView) view.findViewById(R.id.facultetName));
		}


		public TextView getmFacultetName() {
			return mFacultetName;
		}

		public void setmFacultetName(TextView mFacultetName) {
			this.mFacultetName = mFacultetName;
		}
	}
	

}
