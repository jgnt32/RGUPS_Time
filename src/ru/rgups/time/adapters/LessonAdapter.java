package ru.rgups.time.adapters;

import java.util.ArrayList;

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

public class LessonAdapter extends BaseAdapter implements StickyListHeadersAdapte{
	private Context mContext;
	private LayoutInflater mInflater;
	private ArrayList<Lesson> mLessonList;
	  
	private ViewHolder mHolder;
	private DividerHolder dividerHolder;
	private View mView;
	private View headerView;
	private String[] days = {"Понедельник","Вторник","Среда","Четверг","Пятница","Суббота","Воскресенье"};
	public LessonAdapter(Context context, ArrayList<Lesson> list){
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);	
		mLessonList = list;
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

		if(!inf.isEmpty()){
			mHolder.getTitle().setText(inf.get(0).getTitle());
			mHolder.getRoom().setText(inf.get(0).getRoom());
			mHolder.getTeacher().setText(inf.get(0).getTeacher());
			mHolder.getType().setText(inf.get(0).getType());
		}else{
			mHolder.getTitle().setText("--");
			mHolder.getRoom().setText("");
			mHolder.getTeacher().setText("");
			mHolder.getType().setText("");
		}
		return mView;
	}

	@Override
	public View getHeaderView(int position, View convertView, ViewGroup parent) {
		headerView = convertView;
		if(headerView == null){
		Log.e("getHeaderView",""+getItem(position).getDayNumber());

			headerView = mInflater.inflate(R.layout.group_list_divier, parent, false);
			TextView dividerText = (TextView) headerView.findViewById(R.id.levelTitle);
			dividerText.setText("wd");
			dividerHolder = new DividerHolder(headerView);
			headerView.setTag(dividerHolder);
		}else{
			dividerHolder = (DividerHolder) convertView.getTag();
		}
		dividerHolder.dividerText.setText(this.days[getItem(position).getDayNumber()-1]);
		return headerView;
	}

	@Override
	public long getHeaderId(int position) {
		Log.e("getHeaderId",""+getItem(position).getDayNumber());
		return getItem(position).getDayNumber();
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
	

	private class DividerHolder{
		
		public DividerHolder(View v){
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

}
