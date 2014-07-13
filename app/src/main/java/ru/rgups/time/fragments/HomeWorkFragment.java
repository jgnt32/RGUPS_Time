package ru.rgups.time.fragments;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

import ru.rgups.time.BaseFragment;
import ru.rgups.time.R;
import ru.rgups.time.adapters.HomeWorkImageAdapter;
import ru.rgups.time.interfaces.HomeWorkListener;
import ru.rgups.time.model.DataManager;
import ru.rgups.time.model.HomeWork;
import ru.rgups.time.rest.ApigeeManager;
import ru.rgups.time.rest.RestManager;
import ru.rgups.time.utils.PreferenceManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;

import com.jakewharton.disklrucache.DiskLruCache;

public class HomeWorkFragment extends BaseFragment {
	
	public static final String LESSON_ID = "lesson_id";
	public static final String DATE = "date";
	private static final int REQUEST_CODE = 0;
	
	private GridView mPhotoGridView;
	private HomeWorkImageAdapter mAdapter;
	private EditText mText;
	private HomeWork mHomeWork;
	private HomeWorkListener mHomeWorkListener;
	private ArrayList<Bitmap> mList = new ArrayList<Bitmap>();
	
	private DiskLruCache mDiskLruCache;
	private final Object mDiskCacheLock = new Object();
	private boolean mDiskCacheStarting = true;
	private static final int DISK_CACHE_SIZE = 1024 * 1024 * 10; // 10MB
	private static final String DISK_CACHE_SUBDIR = "thumbnails";

	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mHomeWorkListener = (HomeWorkListener) activity;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		mAdapter = new HomeWorkImageAdapter(getActivity(), mList);
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.homework_fragment, null);
		mPhotoGridView = (GridView) v.findViewById(R.id.home_work_grid_view);
		mPhotoGridView.setAdapter(mAdapter);

		mText = (EditText) v.findViewById(R.id.home_work_text);
		return v;
	}


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.homework, menu);

	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		
		case R.id.action_save: 
			saveHomeWork();
			mHomeWorkListener.finisActivity();
			return true;
			
		case R.id.action_add_image:
			pickImage();
			return true;
			
			
		default : return super.onOptionsItemSelected(item);

		}

	}

	
	public void pickImage() {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE);
    }
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK)
            try {
             
                InputStream stream = getActivity().getContentResolver().openInputStream(
                        data.getData());
                Bitmap bitmap = BitmapFactory.decodeStream(stream);
                mList.add(bitmap);
                mAdapter.notifyDataSetChanged();
                stream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
	}
	
	private void saveHomeWork(){
		if(!mText.getText().toString().isEmpty()){
			Log.e("tm ="+getArguments().getLong(DATE),"lessonid = "+getArguments().getLong(LESSON_ID));
			mHomeWork = new HomeWork();
			mHomeWork.setDate(new Date(getArguments().getLong(DATE)));
			mHomeWork.setLessonId(getArguments().getLong(LESSON_ID));
			mHomeWork.setMessage(mText.getText().toString());
            mHomeWork.setGroupId(PreferenceManager.getInstance().getGroupId());
			DataManager.getInstance().saveHomeWork(mHomeWork);
            ApigeeManager.getInstance().pushHomeWorkOnServer(mHomeWork);
		}
	}
	

/*	class InitDiskCacheTask extends AsyncTask<File, Void, Void> {
	    @Override
	    protected Void doInBackground(File... params) {
	        synchronized (mDiskCacheLock) {
	            File cacheDir = params[0];
	            mDiskLruCache = DiskLruCache.(directory, appVersion, valueCount, maxSize)pen(cacheDir, DISK_CACHE_SIZE);
	            mDiskCacheStarting = false; // Finished initialization
	            mDiskCacheLock.notifyAll(); // Wake any waiting threads
	        }
	        return null;
	    }
	}

	class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {
	    // Decode image in background.
	    @Override
	    protected Bitmap doInBackground(Integer... params) {
	        final String imageKey = String.valueOf(params[0]);

	        // Check disk cache in background thread
	        Bitmap bitmap = getBitmapFromDiskCache(imageKey);

	        // Add final bitmap to caches
	        addBitmapToCache(imageKey, bitmap);

	        return bitmap;
	    }
	}

	public void addBitmapToCache(String key, Bitmap bitmap) {
	    // Add to memory cache as before
	    if (getBitmapFromMemCache(key) == null) {
	        mMemoryCache.put(key, bitmap);
	    }

	    // Also add to disk cache
	    synchronized (mDiskCacheLock) {
	        if (mDiskLruCache != null && mDiskLruCache.get(key) == null) {
	            mDiskLruCache.put(key, bitmap);
	        }
	    }
	}

	public Bitmap getBitmapFromDiskCache(String key) {
	    synchronized (mDiskCacheLock) {
	        // Wait while disk cache is started from background thread
	        while (mDiskCacheStarting) {
	            try {
	                mDiskCacheLock.wait();
	            } catch (InterruptedException e) {}
	        }
	        if (mDiskLruCache != null) {
	            return mDiskLruCache.get(key);
	        }
	    }
	    return null;
	}

	// Creates a unique subdirectory of the designated app cache directory. Tries to use external
	// but if not mounted, falls back on internal storage.
	public static File getDiskCacheDir(Context context, String uniqueName) {
	    // Check if media is mounted or storage is built-in, if so, try and use external cache dir
	    // otherwise use internal cache dir
	    final String cachePath =
	            Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) ||
	                    !isExternalStorageRemovable() ? getExternalCacheDir(context).getPath() :
	                            context.getCacheDir().getPath();

	    return new File(cachePath + File.separator + uniqueName);
	}*/
	
}
