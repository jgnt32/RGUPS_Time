package ru.rgups.time.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


import java.util.ArrayList;

import ru.rgups.time.R;
import ru.rgups.time.adapters.HomeWorksAdapter;
import ru.rgups.time.adapters.SingleLessonsHWCardCursorAdapter;
import ru.rgups.time.interfaces.LessonListener;
import ru.rgups.time.loaders.HomeWorkLoader;
import ru.rgups.time.model.HomeWork;

/**
 * Created by timewaistinguru on 12.08.2014.
 */
public class LessonsHomeWorksFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<HomeWork>>,
        AdapterView.OnItemClickListener{

    private HomeWorksAdapter mAdapter;
    private ArrayList<HomeWork> mHomeWorks = new ArrayList<HomeWork>();
    private ListView mListView;
    private View mProgress;
    private LessonListener mLessonListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mLessonListener = (LessonListener) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new HomeWorksAdapter(getActivity(), mHomeWorks);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.lessons_homeworks, null);
        mListView = (ListView) v.findViewById(R.id.single_lesson_home_work_list_view);
        mListView.setOnItemClickListener(this);
        mListView.setAdapter(mAdapter);
        mListView.setEmptyView(v.findViewById(R.id.single_lesson_home_work_empty_view));
        mProgress = v.findViewById(R.id.lessons_home_works_progress);
        return v;

    }

    @Override
    public void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(0, null, this);
        getLoaderManager().getLoader(0).forceLoad();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        menu.findItem(R.id.action_add).setVisible(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_add:
                mLessonListener.OnAddHomeWorkClick(getLessonId(), getDate());
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }
    

    @Override
    public Loader<ArrayList<HomeWork>> onCreateLoader(int i, Bundle bundle) {
        return new HomeWorkLoader(getActivity(), getDate(), getLessonId());
    }

    private long getLessonId() {
        return getArguments().getLong(SingleLessonFragment.LESSON_ID);
    }

    private long getDate() {
        return getArguments().getLong(SingleLessonFragment.TIMESTAMP);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<HomeWork>> arrayListLoader, ArrayList<HomeWork> homeWorks) {
        mHomeWorks.clear();
        mHomeWorks.addAll(homeWorks);
        mAdapter.notifyDataSetChanged();
        hideProgress();
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<HomeWork>> arrayListLoader) {

    }

    private void hideProgress(){
        mProgress.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.e("hw = ", ""+id);
    }
}
