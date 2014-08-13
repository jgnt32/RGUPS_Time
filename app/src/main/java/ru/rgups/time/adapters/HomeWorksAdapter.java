package ru.rgups.time.adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ru.rgups.time.R;
import ru.rgups.time.model.HomeWork;

/**
 * Created by timewaistinguru on 13.08.2014.
 */
public class HomeWorksAdapter extends BaseAdapter {

    private ArrayList<HomeWork> mHomeWorks;
    private Context mContext;
    private LayoutInflater mInflater;
    private ViewHolder mHolder;

    public HomeWorksAdapter(Context mContext, ArrayList<HomeWork> mHomeWorks) {
        this.mHomeWorks = mHomeWorks;
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mHomeWorks.size();
    }

    @Override
    public HomeWork getItem(int position) {
        return mHomeWorks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.home_work_list_element, null);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
          mHolder = (ViewHolder) convertView.getTag();
        }

        mHolder.message.setText(getItem(position).getMessage());
        return convertView;
    }

    private class ViewHolder{
        private TextView message;
        private TextView photoCount;

        private ViewHolder(View v) {
            message = (TextView) v.findViewById(R.id.home_work_list_element_text);
        }
    }
}
