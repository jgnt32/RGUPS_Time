package ru.rgups.time.fragments;

import java.util.ArrayList;

import ru.rgups.time.R;
import ru.rgups.time.model.LessonListElement;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

public abstract class LessonListFragment extends Fragment{
	
	public static final String DAY_ARGS = "day_args";
	

	
	protected ListView mListView;
	private LessonAyncTasck mAyncTasck;
	
	private int mDayNamber;
	
	private int mWeekState;
	
	protected ArrayList<LessonListElement> mLessons = new ArrayList<LessonListElement>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mAyncTasck = new LessonAyncTasck();
		mAyncTasck.execute();
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.lesson_list_fragment, null);
		mListView = (ListView) v.findViewById(R.id.lesson_list);
		setAdapter(mListView);
		return v;
	}
	
	
	protected abstract void setAdapter(ListView list);
	
	protected abstract void loadLessonsFromDb();
	
	protected abstract void notifyAdtapter();
	
	private class LessonAyncTasck extends AsyncTask<Void, Void, Void>{
		
		public LessonAyncTasck() {
			// TODO Auto-generated constructor stub
		}
		

		@Override
		protected Void doInBackground(Void... params) {
			loadLessonsFromDb();
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			notifyAdtapter();
		}
		
	}
	
	

}
