package ru.rgups.time.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ru.rgups.time.R;
import ru.rgups.time.model.LessonListElement;
import ru.rgups.time.model.entity.DoubleLine;
import ru.rgups.time.model.entity.LessonInformation;
import ru.rgups.time.model.entity.OverLine;
import ru.rgups.time.model.entity.UnderLine;

public class LessonAdapter extends BaseAdapter{
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
	private Long timestamp = (long) 0;
	private String mHeaderNumber;


	public LessonAdapter(Context context, ArrayList<LessonListElement> list){
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);	
		mLessonList = list;
		timePeriods = context.getResources().getStringArray(R.array.lessons_time_periods);
		mHeaderNumber = context.getResources().getString(R.string.lesson_list_divider);
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
		return getItem(position).getId();//mLessonList.get(position).getLesson_id();
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
		

			if(getItem(position).isHasHomeWork()){
				mHolder.hwIndicator.setVisibility(View.VISIBLE);
			}else{
				mHolder.hwIndicator.setVisibility(View.GONE);
			}
		if(getItem(position).getInformation().size()>0){
			StringBuffer roomBuffer = new StringBuffer();
			StringBuffer teacherBuffer = new StringBuffer();
			for(LessonInformation lesson : getItem(position).getInformation()){
				
				if(lesson.getRoom() != null){
					if(!lesson.getRoom().trim().isEmpty()){
						roomBuffer.append(lesson.getRoom()).append(", ");
					}
				}
			
				
				if(lesson.getTeacher() != null){
					if(!lesson.getTeacher().isEmpty()){
						teacherBuffer.append(lesson.getTeacher()).append(", ");
					}
				}
				
				mHolder.title.setText(getTitle(lesson));
			}
			this.setText(mHolder.roomContainer, mHolder.room, roomBuffer);
			this.setText(mHolder.teacherContainer, mHolder.teacher, teacherBuffer);
			mHolder.number.setText(mHeaderNumber.replace("#", Integer.toString(getItem(position).getLessonNumber())));
			mHolder.time.setText(timePeriods[getItem(position).getLessonNumber()-1]);
			
	//		mHolder.room.setText(roomBuffer.substring(0,roomBuffer.length()-1));
	//		mHolder.teacher.setText(teacherBuffer.substring(0,teacherBuffer.length()-1));

		
		}else{
			mHolder.getTitle().setText("--");
			mHolder.getRoom().setText("");
			mHolder.getTeacher().setText("");
		//	mHolder.getType().setText("");
		}
		


		return mView;
	}

    private String getTitle(LessonInformation lesson) {
        String result = lesson.getTitle();

        if(lesson.getType() != null && !lesson.getType().trim().isEmpty()){
            result = lesson.getTitle() + " ("+ lesson.getType() + ")";
        }
        return  result;
    }


    private class ViewHolder{
		private TextView title;
		private View teacherContainer;
		private View roomContainer;
		private TextView number;
		private TextView time;
		private TextView room;
//		private TextView type;
		private TextView teacher;
		private ImageView hwIndicator;

		public ViewHolder(View view){
			this.setTitle((TextView) view.findViewById(R.id.lesson_title));
			number = (TextView) view.findViewById(R.id.divider_text);
			this.setRoom((TextView) view.findViewById(R.id.lesson_room));
			time = (TextView) view.findViewById(R.id.lesson_divider_time);
			this.setTeacher((TextView) view.findViewById(R.id.lesson_teacher));
			this.roomContainer = view.findViewById(R.id.lesson_room_container);
			this.teacherContainer = view.findViewById(R.id.lesson_teacher_container);
			hwIndicator = (ImageView) view.findViewById(R.id.lesson_list_element_homework_indicator);
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

	
	private void setText(View container, TextView text, StringBuffer buffer){
		String value = buffer.toString();
		if(value != null){
			if(!value.replace("", "").trim().isEmpty()){
				container.setVisibility(View.VISIBLE);
				text.setText(value.substring(0,value.lastIndexOf(",")));
			}else{
				container.setVisibility(View.GONE);
			}
		}else{
			container.setVisibility(View.GONE);			
		}
			
	}


	public Long getTimestamp() {
		return timestamp;
	}


	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
}
