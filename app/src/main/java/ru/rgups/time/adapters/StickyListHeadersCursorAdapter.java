package ru.rgups.time.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.view.ViewGroup;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

public abstract class StickyListHeadersCursorAdapter extends CursorAdapter implements StickyListHeadersAdapter{

    public StickyListHeadersCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    public StickyListHeadersCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        if (!mDataValid) {
            throw new IllegalStateException("this should only be called when the cursor is valid");
        }
        if (!mCursor.moveToPosition(position)) {
            throw new IllegalStateException("couldn't move cursor to position " + position);
        }
        View v;
        if (convertView == null) {
            v = newView(mContext, mCursor, parent);
        } else {
            v = convertView;
        }
        bindView(v, mContext, mCursor);
        return v;
    }

    protected abstract View newHeaderView(Context context, Cursor cursor, ViewGroup parent);



    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
    	 if (!mDataValid) {
	            throw new IllegalStateException("this should only be called when the cursor is valid");
	        }
	        if (!mCursor.moveToPosition(position)) {
	            throw new IllegalStateException("couldn't move cursor to position " + position);
	        }
	        View v;
	        if (convertView == null) {
	            v = newHeaderView(mContext, mCursor, parent);
	        } else {
	            v = convertView;
	        }
	        bindHeaderView(v, mContext, mCursor);
	        return v;
    }

    protected abstract void bindHeaderView(View v, Context context, Cursor c);





    public abstract View newView(Context context, Cursor cursor, ViewGroup parent);


    public View newDropDownView(Context context, Cursor cursor, ViewGroup parent) {
        return newView(context, cursor, parent);
    }


    public abstract void bindView(View view, Context context, Cursor cursor);


	public abstract long getHeaderId(Cursor c);

	@Override
	public long getHeaderId(int position) {
		return getHeaderId((Cursor) getItem(position));
	}



}