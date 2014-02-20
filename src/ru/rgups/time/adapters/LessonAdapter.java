package ru.rgups.time.adapters;

import java.util.ArrayList;
import java.util.Collection;

import ru.rgups.time.R;
import ru.rgups.time.model.LessonListElement;
import ru.rgups.time.model.entity.DoubleLine;
import ru.rgups.time.model.entity.OverLine;
import ru.rgups.time.model.entity.UnderLine;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LessonAdapter extends BaseAdapter implements StickyListHeadersAdapter{
	public static final int OVER_LINE = 0;
	public static final int UNDER_LINE = 1;
	private Context mContext;
	private LayoutInflater mInflater;
	private ArrayList<LessonListElement> mLessonList;
	private ArrayList<DoubleLine> mDouble;
	private ArrayList<OverLine> mOver;
	private ArrayList<UnderLine> mUnder;
	private ViewHolder mHolder;
	private int mWeekIndicator;
	private View mView;
	private View headerView;
	private String[] timePeriods;
	public LessonAdapter(Context context, Collection<LessonListElement> list){
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);	
		mLessonList = new ArrayList<LessonListElement>(list);
		timePeriods = context.getResources().getStringArray(R.array.lessons_time_periods);
	}
	

	@Override
	public int getCount() {		
		return mLessonList.size();
	}

	@Override
	public LessonListElement getItem(int position) {
		return mLessonList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;//mLessonList.get(position).getLesson_id();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		mView = convertView;
		if (mView == null) {
			mView = mInflater.inflate(R.layout.lesson_list_element, parent, false);
			mHolder = new ViewHolder(mView);
			mView.setTag(mHolder);
		}else{
			mHolder = (ViewHolder) mView.getTag();
		}
		
		mHolder.time.setText(timePeriods[getItem(position).getLessonNumber()-1]);
		if(getItem(position).getInformation().size()>0){
			mHolder.title.setText(getItem(position).getInformation().get(0).getTitle());
			mHolder.room.setText(getItem(position).getInformation().get(0).getRoom());
			mHolder.teacher.setText(getItem(position).getInformation().get(0).getTeacher());
		}else{
			mHolder.getTitle().setText("--");
			mHolder.getRoom().setText("");
			mHolder.getTeacher().setText("");
		//	mHolder.getType().setText("");
		}
		


		return mView;
	}
	

	private class ViewHolder{
		private TextView title;
//		private TextView number;
		private TextView room;
//		private TextView type;
		private TextView teacher;
		private TextView time;


		public ViewHolder(View view){
			this.setTitle((TextView) view.findViewById(R.id.lesson_title));
		//	this.setNumber((TextView) view.findViewById(R.id.lesson_number));
			this.setRoom((TextView) view.findViewById(R.id.lesson_room));
		//	this.setType((TextView) view.findViewById(R.id.lesson_type));
			this.setTeacher((TextView) view.findViewById(R.id.lesson_teacher));
			this.setTime((TextView) view.findViewById(R.id.lesson_time));
		}
		
		
		public TextView getTime() {
			return time;
		}

		public void setTime(TextView time) {
			this.time = time;
		}

		public TextView getTitle() {
			return title;
		}

		public void setTitle(TextView title) {
			this.title = title;
		}

		public TextView getRoom() {
			return room;
		}

		public void setRoom(TextView room) {
			this.room = room;
		}


		public TextView getTeacher() {
			return teacher;
		}

		public void setTeacher(TextView teacher) {
			this.teacher = teacher;
		}

		
	}


	@Override
	public View getHeaderView(int position, View convertView, ViewGroup parent) {
		View v = mInflater.inflate(R.layout.group_list_divier, null);
		TextView text = (TextView) v.findViewById(R.id.levelTitle);
		text.setText(getItem(position).getLessonNumber()+"-я пара");
		return v;
	}


	@Override
	public long getHeaderId(int position) {
		return 	getItem(position).getLessonNumber();
	}
	

}
