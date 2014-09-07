package ru.rgups.time.fragments;

import java.util.ArrayList;

import ru.rgups.time.R;
import ru.rgups.time.interfaces.LessonListener;
import ru.rgups.time.model.DataManager;
import ru.rgups.time.model.HomeWork;
import ru.rgups.time.model.LessonListElement;
import ru.rgups.time.model.entity.LessonInformation;
import ru.rgups.time.rest.ApigeeManager;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

public class SingleLessonFragment extends Fragment{
	
	public static final String TIMESTAMP = "timestamp";
	public static final String LESSON_ID = "lesson_id";

	private LessonListElement mLesson;
	private LessonListener mLessonListener;
	private TextView mTitle;
	private TextView mTime;
    private TextView mRoom;
    private TextView mType;
    private TextView mTeacher;
    private String[] mTimePeriods;
	private long timestamp;
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
		timestamp = getArguments().getLong(TIMESTAMP);


	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.single_lesson_fragment, null);
		mTime = (TextView) v.findViewById(R.id.single_lesson_time);
		mTitle = (TextView) v.findViewById(R.id.single_lesson_title);

		mTitle.setText(mLesson.getTitle());
		mTime.setText(mTimePeriods[mLesson.getLessonNumber()-1]);
        mRoom = (TextView) v.findViewById(R.id.lesson_room);
        mType = (TextView) v.findViewById(R.id.single_information_type);
        mTeacher = (TextView) v.findViewById(R.id.single_information_teacher);

        mRoom.setText(mLesson.getRooms());
        mType.setText(mLesson.getTypes());
        mTeacher.setText(getTeachers());
        return v;
	}



    public String getRooms(){
        String result = null;
        StringBuffer buffer = new StringBuffer();

        for(LessonInformation inf : mLesson.getInformation()){
            if(inf.getRoom() != null & !inf.getRoom().trim().isEmpty()){
                buffer.append(inf.getRoom()).append(", ");
            }
        }

        if(buffer.length() != 0){
            result = buffer.substring(0, buffer.length() - 2);
        }
        return result;
    }


    public String getTeachers(){
        String result = null;
        StringBuffer buffer = new StringBuffer();

        for(LessonInformation inf : mLesson.getInformation()){
            if(inf.getTeacher() != null & !inf.getTeacher().trim().isEmpty()){
                buffer.append(inf.getTeacher()).append(", ");
            }
        }

        if(buffer.length() != 0){
            result  = buffer.substring(0, buffer.length() - 2);
        }

        return result;

    }


    public String getTypes(){
        String result = null;
        StringBuffer buffer = new StringBuffer();

        for(LessonInformation inf : mLesson.getInformation()){
            if(inf.getType() != null & !inf.getType().trim().isEmpty()
                    & !buffer.toString().contains(inf.getType())){
                buffer.append(inf.getType()).append(", ");
            }
        }
        if(buffer.length() != 0){
            result = buffer.substring(0, buffer.length() - 2);
        }
        return result;
    }
	

}
