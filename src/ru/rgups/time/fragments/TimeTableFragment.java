package ru.rgups.time.fragments;

import java.util.ArrayList;

import ru.rgups.time.BaseFragment;
import ru.rgups.time.model.entity.Day;
import ru.rgups.time.model.entity.DoubleLine;
import ru.rgups.time.model.entity.Lesson;
import ru.rgups.time.model.entity.LessonList;
import ru.rgups.time.spice.TimeTableRequest;
import android.os.Bundle;
import android.util.Log;

import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

public class TimeTableFragment extends BaseFragment{
	
	
	
	
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
			ArrayList<Day> days = new ArrayList<Day>(list.getDays());
			ArrayList<Lesson> lessons = new ArrayList<Lesson>(days.get(0).getLessons());
			for(Lesson lesson:lessons){
				for(DoubleLine dbl:lesson.getDoubleLine()){
					Log.e("days.get(0).getLessons()",""+dbl.getTitle());
				}
			}
			Log.e("days.get(0).getLessons()",""+days.get(0).getLessons().size());
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getGroupList();
	}
		

}
