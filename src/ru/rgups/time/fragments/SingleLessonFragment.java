package ru.rgups.time.fragments;

import ru.rgups.time.R;
import ru.rgups.time.interfaces.LessonListener;
import ru.rgups.time.model.DataManager;
import ru.rgups.time.model.HomeWork;
import ru.rgups.time.model.LessonListElement;
import ru.rgups.time.model.entity.LessonInformation;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SingleLessonFragment extends Fragment implements OnClickListener, OnCheckedChangeListener{
	
	public static final String TIMESTAMP = "timestamp";
	public static final String LESSON_ID = "lesson_id";

	private LessonListElement mLesson;
	private LessonListener mLessonListener;
	private LinearLayout mInformationContainer;
	private LinearLayout mHomeWorkContainer;
	private TextView mTitle;
	private TextView mTime;

	private String[] mTimePeriods;
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mLessonListener = (LessonListener) activity;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		mLesson = DataManager.getInstance().getLesson(getArguments().getLong(LESSON_ID));
		mTimePeriods = getActivity().getResources().getStringArray(R.array.lessons_time_periods);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.single_lesson_fragment, null);
		mTime = (TextView) v.findViewById(R.id.single_lesson_time);
		mTitle = (TextView) v.findViewById(R.id.single_lesson_title);
		mInformationContainer = (LinearLayout) v.findViewById(R.id.single_information_container);
		mHomeWorkContainer = (LinearLayout) v.findViewById(R.id.home_work_container);
		for(LessonInformation lesson : mLesson.getInformation()){
			mInformationContainer.addView(getLesson(lesson));
		}

		
		
		mTitle.setText(mLesson.getInformation().get(0).getTitle());
		mTime.setText(mTimePeriods[mLesson.getLessonNumber()-1]);

		return v;
	}
	
	@Override
	public void onStart() {
		super.onStart();
		long timestamp = getArguments().getLong(TIMESTAMP);

		for(HomeWork homeWork : DataManager.getInstance().getHomeWorkList(timestamp, mLesson.getId())){
			mHomeWorkContainer.addView(getHomeWorkListElement(homeWork));
		}
	}
	
	@Override
	public void onStop() {
		super.onStop();
		mHomeWorkContainer.removeAllViews();
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.main, menu);
		menu.findItem(R.id.action_add).setVisible(true);
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()){
		case R.id.action_add:
			mLessonListener.OnAddHomeWorkClick(mLesson.getId(), getArguments().getLong(TIMESTAMP));
			
			return true;
			
		default: return super.onOptionsItemSelected(item);

		}
		
	}
	
	private View getLesson(LessonInformation lesson){
		LayoutInflater inflater  = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.single_lesson_information_element, null);
		final TextView teacher = (TextView) v.findViewById(R.id.single_information_teacher);
		final TextView type = (TextView) v.findViewById(R.id.single_information_type);
		final TextView room = (TextView) v.findViewById(R.id.single_information_room);
		teacher.setText(lesson.getTeacher());
		type.setText(lesson.getType());
		room.setText(lesson.getRoom());
		return v;
	}
	
	private View getHomeWorkListElement(HomeWork homeWork){
		final LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = (View) inflater.inflate(R.layout.home_work_list_element, null);
		TextView message = (TextView) v.findViewById(R.id.home_work_list_element_text);
		CheckBox checkBox = (CheckBox) v.findViewById(R.id.home_work_list_element_check_box);
		message.setText(homeWork.getMessage());
		checkBox.setChecked(homeWork.isComplite());
		v.setTag(homeWork.getId());
		v.setOnClickListener(this);
		checkBox.setTag(homeWork.getId());
		checkBox.setOnCheckedChangeListener(this);
		return v;
	}

	
	
	@Override
	public void onClick(View v) {
		Log.e("v.getTag()",""+(Long) v.getTag());
		mLessonListener.OnHomeWorkListElementClick((Long) v.getTag());
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		
	}
}
