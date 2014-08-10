package ru.rgups.time.loaders;

import android.content.Context;
import android.content.Loader;
import android.database.ContentObserver;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;

import ru.rgups.time.datamanagers.LessonManager;
import ru.rgups.time.model.DataManager;
import ru.rgups.time.model.LessonListElement;

/**
 * Created by timewaistinguru on 09.08.2014.
 */
public class StudentsLessonLoader extends AsyncTaskLoader<ArrayList<LessonListElement>> {

    private int mDayNumber;


    private ContentObserver mObserver = new ForceLoadContentObserver();

    public StudentsLessonLoader(Context context, int dayNumber) {
        super(context);
        mDayNumber = dayNumber;
        DataManager.getInstance().registerObserver(mObserver);
    }

    @Override
    public ArrayList<LessonListElement> loadInBackground() {
        return LessonManager.getInstance().getLessonsBySemestrDayNumber(mDayNumber);
    }



}
