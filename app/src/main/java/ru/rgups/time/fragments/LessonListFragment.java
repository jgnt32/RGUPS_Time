package ru.rgups.time.fragments;

import java.util.ArrayList;

import ru.rgups.time.R;
import ru.rgups.time.interfaces.LessonListener;
import ru.rgups.time.model.LessonListElement;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

public abstract class LessonListFragment extends Fragment implements OnItemClickListener{
	
	public static final String DAY_ARGS = "day_args";
	
	protected LessonListener mLessonListener;
	
	protected ListView mListView;

	private int mDayNamber;
	
	private int mWeekState;
	
	protected ArrayList<LessonListElement> mLessons = new ArrayList<LessonListElement>();

    private View mProgress;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mLessonListener = (LessonListener) activity;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.lesson_list_fragment, null);
		mListView = (ListView) v.findViewById(R.id.lesson_list);
		mListView.setEmptyView(v.findViewById(R.id.lesson_list_empty_view));
		mListView.setOnItemClickListener(this);
        mProgress = v.findViewById(R.id.lesson_list_empty_progress_view);
		setAdapter(mListView);
		return v;
	}

    protected void hideProgress(){
        mProgress.setVisibility(View.GONE);
    }
	
	
	protected abstract void setAdapter(ListView list);
	

}
