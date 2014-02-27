package ru.rgups.time.fragments;

import ru.rgups.time.R;
import ru.rgups.time.adapters.PhotoGalleryAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.GridView;

public class HomeWorkFragment extends Fragment implements MultiChoiceModeListener{
	private GridView mPhotoGridView;
	private PhotoGalleryAdapter mAdapter;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.homework_fragment, null);
		mPhotoGridView = (GridView) v.findViewById(R.id.home_work_grid_view);
		mPhotoGridView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL); 
		mPhotoGridView.setMultiChoiceModeListener(this);
		mAdapter = new PhotoGalleryAdapter(getActivity());
		mPhotoGridView.setAdapter(mAdapter);
		return v;
	}


	@Override
	public boolean onActionItemClicked(ActionMode arg0, MenuItem item) {

		return false;
	}


	@Override
	public boolean onCreateActionMode(ActionMode arg0, Menu arg1) {
		
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public void onDestroyActionMode(ActionMode arg0) {
		// TODO Auto-generated method stub
		
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
