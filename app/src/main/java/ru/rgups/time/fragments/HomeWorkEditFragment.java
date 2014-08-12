package ru.rgups.time.fragments;

import ru.rgups.time.R;
import ru.rgups.time.activities.PhotoFullScreenActivity;
import ru.rgups.time.adapters.HomeWorkImageAdapter;
import ru.rgups.time.adapters.PhotoGalleryAdapter;
import ru.rgups.time.interfaces.HomeWorkListener;
import ru.rgups.time.interfaces.PickUpImageListener;
import ru.rgups.time.model.DataManager;
import ru.rgups.time.model.HomeWork;
import ru.rgups.time.utils.DialogManager;
import ru.rgups.time.utils.PreferenceManager;

import android.app.Activity;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

public class HomeWorkEditFragment extends Fragment implements MultiChoiceModeListener, AdapterView.OnItemClickListener,
        LoaderManager.LoaderCallbacks<HomeWork>, PickUpImageListener{
    public static final String LESSON_ID = "lesson_id";
    public static final String HOMEWORK_ID = "homework_id";
    public static final String DATE = "date";
    private static final int CAMERA_REQUEST = 1;

    private GridView mPhotoGridView;
	private HomeWorkImageAdapter mAdapter;
	private EditText mText;
	private HomeWork mHomeWork;
    private ArrayList<String> mPhotos = new ArrayList<String>();

	private HomeWorkListener mHomeWorkListener;

    private static final int REQUEST_CODE = 0;


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
        mPhotos.clear();
        if(mHomeWork != null){
            mPhotos.addAll(mHomeWork.getImages());
        }
		mAdapter = initAdapter();
	}

    private HomeWorkImageAdapter initAdapter() {
        return new HomeWorkImageAdapter(getActivity(), mPhotos);
    }


    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.homework_fragment, null);
		mPhotoGridView = (GridView) v.findViewById(R.id.home_work_grid_view);
		mPhotoGridView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE_MODAL); 
		mPhotoGridView.setMultiChoiceModeListener(this);
        mPhotoGridView.setAdapter(mAdapter);
        mPhotoGridView.setOnItemClickListener(this);
		mText = (EditText) v.findViewById(R.id.home_work_text);
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
        switch (item.getItemId()) {
            case R.id.action_save:
                saveHomeWork();
                mHomeWorkListener.finisActivity();
                return true;
            case R.id.action_delete:
                deleteHomeWork();

                return true;

            case R.id.action_add_image:
                pickImage();
                return true;

            default: return super.onOptionsItemSelected(item);
        }
    }


    public void pickImage() {
        PickDialogFragment fragment = new PickDialogFragment();
        fragment.setImageListener(this);
        fragment.show(getChildFragmentManager(), null);
        /*
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE);
        */
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE || requestCode == CAMERA_REQUEST)
            try {
                ClipData list =
                        data.getClipData();

                if(list != null){
                    for (int i = 0; i < list.getItemCount(); i++) {
                        addImage(list.getItemAt(i).getUri().toString());
                    }
                } else{
                    addImage(data.getData().toString());

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
    }


    private void addImage(String uri){
        mPhotos.add(uri);
        mAdapter.notifyDataSetChanged();
    }


	private void deleteHomeWork(){
        if(isReadyToSave()){
            DialogManager.showPositiveDialog(getActivity(), R.string.homework_delete_message, new OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    DataManager.getInstance().deleteHomeWork(mHomeWork);
                    mHomeWorkListener.finisActivity();
                }
            });

        } else {
            close();
        }
	}


    private void close(){
        mHomeWorkListener.finisActivity();
    }


	private void saveHomeWork(){
		if(isReadyToSave()){

            if(mHomeWork == null){
                mHomeWork = new HomeWork();
                mHomeWork.setDate(new Date(getArguments().getLong(DATE)));
                mHomeWork.setLessonId(getArguments().getLong(LESSON_ID));
                mHomeWork.setMessage(mText.getText().toString());
                mHomeWork.setGroupId(PreferenceManager.getInstance().getGroupId());
            }

			mHomeWork.setMessage(mText.getText().toString());
            mHomeWork.setImages(mPhotos);

            if(mHomeWork.getId() == 0){
                DataManager.getInstance().saveHomeWork(mHomeWork);
            } else {
                DataManager.getInstance().updateHomeWork(mHomeWork);
            }
		} else {
            close();
        }
	}

    private boolean isReadyToSave() {
        if(!mPhotos.isEmpty()){
            return true;
        } else {
            return !mText.getText().toString().isEmpty();
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(getActivity(), PhotoFullScreenActivity.class);
        i.putStringArrayListExtra(PhotoFullScreenActivity.PHOTOS, mPhotos);
        startActivity(i);
    }

    @Override
    public Loader<HomeWork> onCreateLoader(int i, Bundle bundle) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<HomeWork> homeWorkLoader, HomeWork homeWork) {

    }

    @Override
    public void onLoaderReset(Loader<HomeWork> homeWorkLoader) {

    }

    @Override
    public void onPickNewPhotoClick() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);

    }

    @Override
    public void onPickPhotoFromGalleryClick() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE);

    }
}
