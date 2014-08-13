package ru.rgups.time.adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import ru.rgups.time.R;
import ru.rgups.time.model.DataManager;
import ru.rgups.time.model.HomeWork;

/**
 * Created by timewaistinguru on 13.08.2014.
 */
public class HomeWorksAdapter extends BaseAdapter implements CompoundButton.OnCheckedChangeListener{

    private ArrayList<HomeWork> mHomeWorks;
    private Context mContext;
    private LayoutInflater mInflater;
    private ViewHolder mHolder;
    private DisplayImageOptions mDisplayImageOptions =  new DisplayImageOptions.Builder().build();

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
        if(getItem(position).getImages() != null && !getItem(position).getImages().isEmpty()){
            mHolder.photoContainer.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(getItem(position).getImages().get(0), mHolder.photo);
            mHolder.photoCount.setText(getItem(position).getImages().size()+" фото");
        } else {
            mHolder.photoContainer.setVisibility(View.GONE);

        }

        mHolder.compliteBox.setChecked(getItem(position).isComplite());

        if(getItem(position).isComplite()){
            mHolder.body.setBackground(mContext.getResources().getDrawable(R.drawable.hw_done_indicator));
        } else {
            mHolder.body.setBackground(null);
        }

        mHolder.compliteBox.setOnCheckedChangeListener(this);
        mHolder.compliteBox.setTag(getItem(position).getId());

        return convertView;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        DataManager.getInstance().setHomeWorkChecked((Long) buttonView.getTag(), isChecked);
    }

    private class ViewHolder{
        private final ToggleButton compliteBox;
        private TextView message;
        private TextView photoCount;
        private ImageView photo;
        private View photoContainer;
        private View body;

        private ViewHolder(View v) {
            message = (TextView) v.findViewById(R.id.home_work_list_element_text);
            photoCount = (TextView) v.findViewById(R.id.home_work_list_element_photo_count);
            photo = (ImageView) v.findViewById(R.id.home_work_image_preview);
            photoContainer = v.findViewById(R.id.home_work_list_element_image_container);
            compliteBox = (ToggleButton) v.findViewById(R.id.home_work_list_element_check_box);
            body = v.findViewById(R.id.home_work_list_element_body);
        }
    }
}
