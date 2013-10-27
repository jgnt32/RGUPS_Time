package ru.rgups.time.fragments;

import java.util.ArrayList;

import ru.rgups.time.BaseFragment;
import ru.rgups.time.R;
import ru.rgups.time.adapters.LessonAdapter;
import ru.rgups.time.model.entity.Day;
import ru.rgups.time.model.entity.Lesson;
import ru.rgups.time.model.entity.LessonInformation;
import ru.rgups.time.model.entity.LessonList;
import ru.rgups.time.spice.TimeTableRequest;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

public class TimeTableFragment extends BaseFragment{
	private StickyListHeadersListView mList;
	private ArrayList<LessonInformation> mLessons = new ArrayList<LessonInformation>(); 
	private void getGroupList(){	
		this.getSpiceManager().execute(new TimeTableRequest("15144"), new GetTimeListener());
	}
	
	private class GetTimeListener implements RequestListener< LessonList >{

		@Override
		public void onRequestFailure(SpiceException exception) {
		}

		@Override
		public void onRequestSuccess(LessonList list) {
			Log.e("list",""+list.getDays().size());
			ArrayList<Lesson> lessons = new ArrayList<Lesson>();
			for(Day day:list.getDays()){
		//		Log.e("day number",""+day.getNumber());
				for(Lesson lesson:day.getLessons()){
			//		Log.e("lesson number",""+lesson.getNumber());
					ArrayList<LessonInformation> tempList = new ArrayList<LessonInformation>();
					
					if(lesson.getDoubleLine()!=null){
						tempList.addAll(lesson.getDoubleLine());
					}

					if(lesson.getOverLine()!=null){
						tempList.addAll(lesson.getOverLine());
					}
					if(lesson.getUnderLine()!=null){
						tempList.addAll(lesson.getUnderLine());
					}
					
					lesson.getInfromation().addAll(tempList);
			//Ы		Log.e("inf count",""+lesson.getInfromation().size());
					lesson.setDayNumber(day.getNumber());
				}
				lessons.addAll(day.getLessons());
			}
		//	Log.e("all lessons",""+lessons.size());
			
			LessonAdapter adapter = new LessonAdapter(getActivity(),lessons);
			mList.setAdapter(adapter);
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getGroupList();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.timetable_fragment, null);
		mList = (StickyListHeadersListView) v.findViewById(R.id.lessonList);
		return v;
	}
		

}
