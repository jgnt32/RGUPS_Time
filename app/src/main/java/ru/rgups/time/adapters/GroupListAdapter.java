package ru.rgups.time.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import ru.rgups.time.R;
import ru.rgups.time.model.entity.Group;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

public class GroupListAdapter extends BaseAdapter implements StickyListHeadersAdapter {
	private ArrayList<Group> mGroupList;
	private Group mGroup;
	private TextView mGroupName;
	private View mView;
	private Context mContext;
	private LayoutInflater mInflater;
	private ViewHolder mHolder;
	private DividerHolder dividerHolder;
	private String mHeaderString;
	
	public GroupListAdapter(Context context, ArrayList<Group> groupList ){
		this.mContext = context;
		this.mGroupList = groupList;
		Collections.sort(mGroupList, new GroupComparator());
		mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);	
		mHeaderString = context.getResources().getString(R.string.group_list_divider);
	}
	
	
	@Override
	public int getCount() {
		return mGroupList.size();
	}

	@Override
	public Group getItem(int position) {
		return mGroupList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return getItem(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		mView = convertView;
		if (mView == null) {
			mView = mInflater.inflate(R.layout.group_list_element, parent, false);
			mHolder = new ViewHolder(mView);
			mView.setTag(mHolder);
		}else{
			mHolder = (ViewHolder) mView.getTag();
		}
		this.mGroup = getItem(position);
		this.mGroupName = mHolder.getmFacultetName();
		this.mGroupName.setText(mGroup.getTitle());
		return mView;
	}
	

	private class ViewHolder{
		private TextView mFacultetName;
		
		public ViewHolder(View view){
			this.setmFacultetName((TextView) view.findViewById(R.id.groupName));
		}


		public TextView getmFacultetName() {
			return mFacultetName;
		}

		public void setmFacultetName(TextView mFacultetName) {
			this.mFacultetName = mFacultetName;
		}
	}


/*	@Override
	public View getHeaderView(int position, View convertView, ViewGroup parent) {
		Log.e("GroupListAdapter","getHeaderView");
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.group_list_divier, parent, false);
			dividerHolder = new DividerHolder(convertView);
			convertView.setTag(dividerHolder);
		}else{
			dividerHolder = (DividerHolder) convertView.getTag();
		}
		dividerHolder.dividerText.setText(mGroupList.get(position).getLevel()+"-й курс");
		return convertView;
	}


	@Override
	public long getHeaderId(int position) {
		Log.e("GroupListAdapter","getHeaderId");
		return mGroupList.get(position).getLevel();
	}
	*/
	
	
	private class DividerHolder{
		
		DividerHolder(View v){
			dividerText = (TextView) v.findViewById(R.id.levelTitle);
		}
		
		private TextView dividerText;

		public TextView getDividerText() {
			return dividerText;
		}

		public void setDividerText(TextView dividerText) {
			this.dividerText = dividerText;
		}
		
	}


	@Override
	public View getHeaderView(int position, View convertView, ViewGroup parent) {
		View v = mInflater.inflate(R.layout.group_list_divier,null);
		TextView text = (TextView) v.findViewById(R.id.levelTitle);
		text.setText(mHeaderString.replace("#", Integer.toString(getItem(position).getLevel())));
		return v;
	}


	@Override
	public long getHeaderId(int position) {
		// TODO Auto-generated method stub
		return getItem(position).getLevel();
	}

	private class GroupComparator implements Comparator<Group>{

		@Override
		public int compare(Group lhs, Group rhs) {
			if(lhs.getLevel()>rhs.getLevel()){
				return 0;
			}else{
				return 1;
			}
			
		}
		
	}
	
}
