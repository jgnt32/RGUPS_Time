package ru.rgups.time.loaders;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;

import ru.rgups.time.model.DataManager;

/**
 * Created by timewaistinguru on 13.08.2014.
 */
public class HomeWorkCursorLoader extends AsyncTaskLoader<Cursor> {


    private ContentObserver mObserver = new ForceLoadContentObserver();

    public HomeWorkCursorLoader(Context context) {
        super(context);
        DataManager.getInstance().registerObserver(mObserver);
    }

    @Override
    public Cursor loadInBackground() {

        return DataManager.getInstance().getHomeWorks();
    }
}
