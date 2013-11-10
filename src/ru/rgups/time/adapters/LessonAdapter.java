package ru.rgups.time.adapters;

import java.util.ArrayList;
import java.util.Collection;

import ru.rgups.time.R;
import ru.rgups.time.model.entity.Lesson;
import ru.rgups.time.model.entity.LessonInformation;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LessonAdapter extends BaseAdapter{
	private Context mContext;
	private LayoutInflater mInflater;
	private ArrayList<Lesson> mLessonList;
	  
	private ViewHolder mHolder;
	
	private View mView;
	private View headerView;
	public LessonAdapter(Context context, Collection<Lesson> list){
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);	
		mLessonList = new ArrayList<Lesson>(list);
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
		Log.e("day number",""+getItem(position).getDayNumber());
		ArrayList<LessonInformation> inf = new ArrayList<LessonInformation>(getItem(position).getInfromation());
		mHolder.getNumber().setText(Integer.toString(this.getItem(position).getNumber()));

	/*	if(!inf.isEmpty()){
			mHolder.getTitle().setText(inf.get(0).getTitle());
			mHolder.getRoom().setText(inf.get(0).getRoom());
			mHolder.getTeacher().setText(inf.get(0).getTeacher());
			mHolder.getType().setText(inf.get(0).getType());
		}else{
			mHolder.getTitle().setText("--");
			mHolder.getRoom().setText("");
			mHolder.getTeacher().setText("");
			mHolder.getType().setText("");
		}*/
		return mView;
	}
	

	private class ViewHolder{
		private TextView title;
		private TextView number;
		private TextView room;
		private TextView type;
		private TextView teacher;
		
		public ViewHolder(View view){
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
