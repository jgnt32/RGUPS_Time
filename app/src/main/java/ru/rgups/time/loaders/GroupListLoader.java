package ru.rgups.time.loaders;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;

import ru.rgups.time.model.DataManager;
import ru.rgups.time.model.entity.Group;

/**
 * Created by timewaistinguru on 14.08.2014.
 */
public class GroupListLoader extends AsyncTaskLoader<ArrayList<Group>> {

    private long mFacultetId;

    private ContentObserver mObserver = new ForceLoadContentObserver();

    public GroupListLoader(Context context, long faculetId) {
        super(context);
        mFacultetId = faculetId;
        DataManager.getInstance().registerObserver(mObserver);
    }

    @Override
    public ArrayList<Group> loadInBackground() {
        return DataManager.getInstance().getGroupList(mFacultetId);
    }
}
