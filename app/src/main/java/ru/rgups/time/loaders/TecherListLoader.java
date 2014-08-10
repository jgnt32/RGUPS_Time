package ru.rgups.time.loaders;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;

import ru.rgups.time.model.DataManager;

/**
 * Created by timewaistinguru on 10.08.2014.
 */
public class TecherListLoader extends AsyncTaskLoader<Cursor> {

    public TecherListLoader(Context context) {
        super(context);
    }

    @Override
    public Cursor loadInBackground() {
        return DataManager.getInstance().getAllTeachersCursor();
    }
}
