package ru.rgups.time.fragments;

import ru.rgups.time.R;
import ru.rgups.time.interfaces.LessonListener;
import ru.rgups.time.model.DataManager;
import ru.rgups.time.model.LessonListElement;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SingleLessonFragment extends Fragment{
	
	public static final String LESSON_ID = "lesson_id";

	private LessonListElement mLesson;
	private LessonListener mLessonListener;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mLesson = DataManager.getInstance().getLesson(getArguments().getLong(LESSON_ID));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.single_lesson_fragment, null);
		return v;
	}
}
