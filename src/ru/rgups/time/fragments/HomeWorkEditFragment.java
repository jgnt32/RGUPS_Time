package ru.rgups.time.fragments;

import ru.rgups.time.R;
import ru.rgups.time.adapters.PhotoGalleryAdapter;
import ru.rgups.time.interfaces.HomeWorkListener;
import ru.rgups.time.model.DataManager;
import ru.rgups.time.model.HomeWork;
import ru.rgups.time.utils.DialogManager;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
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

public class HomeWorkEditFragment extends Fragment implements MultiChoiceModeListener{
	
	public static final String HOMEWORK_ID = "homework_id";
	
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
		mHomeWork = DataManager.getInstance().getHomeWork(getArguments().getLong(HOMEWORK_ID));
		
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
		Log.e("mHomeWork",""+mHomeWork);
		if(mHomeWork != null){
			mText.setText(mHomeWork.getMessage());
		}
		
		return v;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.homework, menu);
		menu.findItem(R.id.action_save).setVisible(true);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		
		case R.id.action_save: 
			saveHomeWork();
			mHomeWorkListener.finisActivity();
			return true;
		case R.id.action_delete:
			deleteHomeWork();
			
			return true;
		
		default : return super.onOptionsItemSelected(item);

		}

	}

	private void deleteHomeWork(){
		DialogManager.showPositiveDialog(getActivity(), R.string.homework_delete_message, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				DataManager.getInstance().deleteHomeWork(mHomeWork);
				mHomeWorkListener.finisActivity();
			}
		});
	}
	
	
	
	private void saveHomeWork(){
		if(!mText.getText().toString().isEmpty() && mHomeWork != null){
			
			mHomeWork.setMessage(mText.getText().toString());
			DataManager.getInstance().updateHomeWork(mHomeWork);			
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
