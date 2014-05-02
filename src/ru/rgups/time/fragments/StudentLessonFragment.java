package ru.rgups.time.fragments;

import ru.rgups.time.adapters.LessonAdapter;
import ru.rgups.time.datamanagers.LessonManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class StudentLessonFragment extends LessonListFragment{

	private LessonAdapter mAdapter;
	
	private int mDayNumber;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mDayNumber = getArguments().getInt(DAY_ARGS);
	}
	
	
	@Override
	protected void setAdapter(ListView list) {
		mAdapter = new LessonAdapter(getActivity(), mLessons);	
		mListView.setAdapter(mAdapter);
	}

	@Override
	protected void loadLessonsFromDb() {
		mLessons.clear();
//		mAdapter.setTimestamp(getTimeStamp());
		mLessons.addAll(LessonManager.getInstance().getLessonsBySemestrDayNumber(mDayNumber));
	}

	@Override
	protected void notifyAdtapter() {
		mAdapter.notifyDataSetChanged();			
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		mLessonListener.OnLessonListElementClick(id, LessonManager.getInstance().getTimeStampBySemestrDayNumber(mDayNumber));
	}

}
