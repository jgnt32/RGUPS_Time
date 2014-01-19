package ru.rgups.time.adapters;

import java.util.ArrayList;
import java.util.Collection;

import ru.rgups.time.R;
import ru.rgups.time.model.entity.DoubleLine;
import ru.rgups.time.model.entity.Lesson;
import ru.rgups.time.model.entity.OverLine;
import ru.rgups.time.model.entity.UnderLine;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LessonAdapter extends BaseAdapter{
	public static final int OVER_LINE = 0;
	public static final int UNDER_LINE = 1;
	private Context mContext;
	private LayoutInflater mInflater;
	private ArrayList<Lesson> mLessonList;
	private ArrayList<DoubleLine> mDouble;
	private ArrayList<OverLine> mOver;
	private ArrayList<UnderLine> mUnder;
	private ViewHolder mHolder;
	private int mWeekIndicator;
	private View mView;
	private View headerView;
	public LessonAdapter(Context context, Collection<Lesson> list, int weekIndicator){
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);	
		mLessonList = new ArrayList<Lesson>(list);
		mWeekIndicator = weekIndicator;
	}
	

	@Override
	public int getCount() {		
		return mLessonList.size();
	}

	@Override
	public Lesson getItem(int position) {
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
		
		mHolder.getNumber().setText(Integer.toString(this.getItem(position).getNumber()));
		mHolder.getTime().setText(this.getItem(position).getTime());
		
		if(!this.getItem(position).getDoubleLine().isEmpty()){
			mDouble = new ArrayList<DoubleLine>(this.getItem(position).getDoubleLine());
			mHolder.getTitle().setText(mDouble.get(0).getTitle());
			mHolder.getRoom().setText(mDouble.get(0).getRoom());
			mHolder.getTeacher().setText(mDouble.get(0).getTeacher());
			mHolder.getType().setText(mDouble.get(0).getType());
		}else{
			switch(this.mWeekIndicator){
			case OVER_LINE:
				mOver = new ArrayList<OverLine>(this.getItem(position).getOverLine());
				if(!mOver.isEmpty()){
					mHolder.getTitle().setText(mOver.get(0).getTitle());
					mHolder.getRoom().setText(mOver.get(0).getRoom());
					mHolder.getTeacher().setText(mOver.get(0).getTeacher());
					mHolder.getType().setText(mOver.get(0).getType());
				}else{
					mHolder.getTitle().setText("--");
					mHolder.getTitle().setText("");
					mHolder.getRoom().setText("");
					mHolder.getTeacher().setText("");
					mHolder.getType().setText("");
				}
				break;	
			case UNDER_LINE:
				mUnder = new ArrayList<UnderLine>(this.getItem(position).getUnderLine());
				if(!mUnder.isEmpty()){
					mHolder.getTitle().setText(mUnder.get(0).getTitle());
					mHolder.getRoom().setText(mUnder.get(0).getRoom());
					mHolder.getTeacher().setText(mUnder.get(0).getTeacher());
					mHolder.getType().setText(mUnder.get(0).getType());
				}else{
					mHolder.getTitle().setText("--");
					mHolder.getTitle().setText("");
					mHolder.getRoom().setText("");
					mHolder.getTeacher().setText("");
					mHolder.getType().setText("");
				}
				break;
			}
			
		}

		return mView;
	}
	

	private class ViewHolder{
		private TextView title;
		private TextView number;
		private TextView room;
		private TextView type;
		private TextView teacher;
		private TextView time;


		public ViewHolder(View view){
			this.setTitle((TextView) view.findViewById(R.id.lesson_title));
			this.setNumber((TextView) view.findViewById(R.id.lesson_number));
			this.setRoom((TextView) view.findViewById(R.id.lesson_room));
			this.setType((TextView) view.findViewById(R.id.lesson_type));
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
