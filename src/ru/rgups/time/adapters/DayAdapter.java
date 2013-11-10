package ru.rgups.time.adapters;

import java.util.ArrayList;
import java.util.Collection;

import ru.rgups.time.R;
import ru.rgups.time.model.entity.Day;
import ru.rgups.time.model.entity.Lesson;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DayAdapter extends BaseAdapter{
	private ArrayList<Day> days;
	private LayoutInflater mInflater;
	private Context mContext;
	private View mView;
	private View mLessonView;
	private HolderView holder;
	private LessonViewHolder lessonHolder;
	private String[] dayTitles = {"Понедельник","Вторник","Среда","Четверг","Пятница","Суббота","Воскресенье"};

	public DayAdapter(Context context,Collection<Day> list){
		this.mContext = context;
		this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);	

		this.days = new ArrayList<Day>(list);
	}
	
	@Override
	public int getCount() {

		return days.size();
	}

	@Override
	public Day getItem(int position) {
		return days.get(position);
	}

	@Override
	public long getItemId(int position) {
		return days.get(position).getNumber();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		mView = convertView;
		if (mView == null) {
			mView = mInflater.inflate(R.layout.day_list_element, null);
			holder = new HolderView(mView);
			mView.setTag(holder);
		}else{
			holder = (HolderView) mView.getTag();
		}
		
		holder.dayTitle.setText(dayTitles[this.getItem(position).getNumber()-1]);
		holder.linearLayout.removeAllViews();
		
	
		for(Lesson lesson:this.getItem(position).getLessons()){
			mLessonView = mInflater.inflate(R.layout.lesson_list_element, null);

			/*final TextView number = (TextView) mLessonView.findViewById(R.id.lesson_number);
			number.setText(Integer.toString(lesson.getNumber()));*/
			holder.linearLayout.addView(mLessonView);
			
		}
		return mView;
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
