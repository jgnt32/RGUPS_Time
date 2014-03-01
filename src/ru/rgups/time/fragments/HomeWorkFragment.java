package ru.rgups.time.fragments;

import java.util.Date;

import ru.rgups.time.R;
import ru.rgups.time.adapters.PhotoGalleryAdapter;
import ru.rgups.time.interfaces.HomeWorkListener;
import ru.rgups.time.model.DataManager;
import ru.rgups.time.model.HomeWork;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.EditText;
import android.widget.GridView;

public class HomeWorkFragment extends Fragment implements MultiChoiceModeListener{
	
	public static final String LESSON_ID = "lesson_id";
	public static final String DATE = "date";
	
	private GridView mPhotoGridView;
	private PhotoGalleryAdapter mAdapter;
	private EditText mText;
	private HomeWork mHomeWork;
	private HomeWorkListener mHomeWorkListener;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mHomeWorkListener = (HomeWorkListener) activity;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		Log.d("qwerrqeq",""+getArguments().getLong(LESSON_ID)+" tm "+ getArguments().getLong(DATE));

	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.homework_fragment, null);
		mPhotoGridView = (GridView) v.findViewById(R.id.home_work_grid_view);
		mPhotoGridView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL); 
		mPhotoGridView.setMultiChoiceModeListener(this);
		mAdapter = new PhotoGalleryAdapter(getActivity());
		mPhotoGridView.setAdapter(mAdapter);
		mText = (EditText) v.findViewById(R.id.home_work_text);
		return v;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.main, menu);
		menu.findItem(R.id.action_save).setVisible(true);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		
		case R.id.action_save: 
			saveHomeWork();
			mHomeWorkListener.finisActivity();
			return true;
			
			
		default : return super.onOptionsItemSelected(item);

		}

	}

	
	private void saveHomeWork(){
		if(!mText.getText().toString().isEmpty()){
			Log.e("tm ="+getArguments().getLong(DATE),"lessonid = "+getArguments().getLong(LESSON_ID));
			mHomeWork = new HomeWork();
			mHomeWork.setDate(new Date(getArguments().getLong(DATE)));
			mHomeWork.setLessonId(getArguments().getLong(LESSON_ID));
			mHomeWork.setMessage(mText.getText().toString());
			DataManager.getInstance().saveHomeWork(mHomeWork);			
		}
	}

	@Override
	public boolean onActionItemClicked(ActionMode arg0, MenuItem item) {

		return false;
	}


	@Override
	public boolean onCreateActionMode(ActionMode arg0, Menu arg1) {
		return true;
	}


	@Override
	public void onDestroyActionMode(ActionMode arg0) {
		
	}


	@Override
	public boolean onPrepareActionMode(ActionMode arg0, Menu arg1) {
		return true;
	}


	@Override
	public void onItemCheckedStateChanged(ActionMode arg0, int position, long id, boolean checked) {
		Log.e("onItemCheckedStateChanged","position = "+position+"; count = "+mPhotoGridView.getCheckedItemCount());
		
	}
}
