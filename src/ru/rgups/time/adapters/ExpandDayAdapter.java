package ru.rgups.time.adapters;

import java.util.ArrayList;
import java.util.Collection;

import ru.rgups.time.R;
import ru.rgups.time.model.entity.Day;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ExpandDayAdapter extends BaseExpandableListAdapter {

	private ArrayList<Day> days;
	private LayoutInflater mInflater;
	private Context mContext;
	private View mView;
	private View mLessonView;
	private HolderView holder;
	private LessonViewHolder lessonHolder;
	
	public ExpandDayAdapter(Context context,Collection<Day> list){
		this.mContext = context;
		this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);	

		this.days = new ArrayList<Day>(list);
	}
	
	@Override
	public Object getChild(int groupPosition, int childPosition) {

		return days.get(groupPosition).getLessons();
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		View v = mInflater.inflate(R.layout.lesson_list_element, null);
		return v;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return days.get(groupPosition).getLessons().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return days.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return days.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return days.get(groupPosition).getNumber();
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		View v = mInflater.inflate(R.layout.day_list_element, null);
		return v;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}
	
	private class HolderView{
		private LinearLayout linearLayout;
		private TextView dayTitle;
		
		public HolderView(View v){
			linearLayout = (LinearLayout) v.findViewById(R.id.lesson_layout);
			dayTitle = (TextView) v.findViewById(R.id.day_title);
			
		}
	}
	
	private class LessonViewHolder{
		private TextView title;
		private TextView number;
		private TextView room;
		private TextView type;
		private TextView teacher;
		
		public LessonViewHolder(View view){
			this.setTitle((TextView) view.findViewById(R.id.lesson_title));
			this.setNumber((TextView) view.findViewById(R.id.lesson_number));
			this.setRoom((TextView) view.findViewById(R.id.lesson_room));
			this.setType((TextView) view.findViewById(R.id.lesson_type));
			this.setTeacher((TextView) view.findViewById(R.id.lesson_teacher));
		}

		public TextView getTitle() {
			return title;
		}

		public void setTitle(TextView title) {
			this.title = title;
		}

		public TextView getNumber() {
			return number;
		}

		public void setNumber(TextView number) {
			this.number = number;
		}

		public TextView getRoom() {
			return room;
		}

		public void setRoom(TextView room) {
			this.room = room;
		}

		public TextView getType() {
			return type;
		}

		public void setType(TextView type) {
			this.type = type;
		}

		public TextView getTeacher() {
			return teacher;
		}

		public void setTeacher(TextView teacher) {
			this.teacher = teacher;
		}

	}
	
}



