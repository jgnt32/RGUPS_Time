package ru.rgups.time.loaders;

import android.content.Context;
import android.database.ContentObserver;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;

import ru.rgups.time.model.DataManager;
import ru.rgups.time.model.HomeWork;

/**
 * Created by timewaistinguru on 13.08.2014.
 */
public class HomeWorkLoader extends AsyncTaskLoader<ArrayList<HomeWork>> {

    private long mDate;

    private long mLessonId;

    private ContentObserver mObserver = new ForceLoadContentObserver();

    public HomeWorkLoader(Context context, long date, long lessonId) {
        super(context);
        mDate = date;
        mLessonId = lessonId;
        DataManager.getInstance().registerObserver(mObserver);
    }

    @Override
    public ArrayList<HomeWork> loadInBackground() {
        return DataManager.getInstance().getHomeWorkList(mDate, mLessonId);
    }
}
