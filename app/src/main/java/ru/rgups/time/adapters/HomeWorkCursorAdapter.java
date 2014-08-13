package ru.rgups.time.adapters;

import ru.rgups.time.R;
import ru.rgups.time.model.DataManager;
import ru.rgups.time.model.HomeWork;
import ru.rgups.time.model.entity.LessonInformation;
import ru.rgups.time.utils.CalendarManager;
import ru.rgups.time.utils.Slipper;

import android.content.Context;
import android.database.Cursor;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

public class HomeWorkCursorAdapter extends StickyListHeadersCursorAdapter implements CompoundButton.OnCheckedChangeListener{

	public static final String DIVIDER_DATE_FORMAT = "d MMMM, EEEE";

	
	private LayoutInflater mInflater;

	public HomeWorkCursorAdapter(Context context, Cursor c, boolean autoRequery) {
		super(context, c, autoRequery);
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	@Override
	protected View newHeaderView(Context context, Cursor cursor,
			ViewGroup parent) {
		return mInflater.inflate(R.layout.home_work_list_divier, null);
	}

	@Override
	protected void bindHeaderView(View v, Context context, Cursor c) {
		final TextView date = (TextView) v.findViewById(R.id.divider_text);
		date.setText(DateFormat.format(DIVIDER_DATE_FORMAT, getDate(c)));
	}

    private long getDate(Cursor c) {
        return c.getLong(c.getColumnIndex(HomeWork.DATE)) * CalendarManager.MILISECONDS_PER_DAY;
    }

    @Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		return  mInflater.inflate(R.layout.home_work_list_element, parent, false);
	}

	@Override
	public void bindView(View v, Context context, Cursor c) {
		final TextView lessonTitle = (TextView) v.findViewById(R.id.homework_list_element_lesson_title);
		final TextView message = (TextView) v.findViewById(R.id.home_work_list_element_text);
		final TextView photoCount = (TextView) v.findViewById(R.id.home_work_list_element_photo_count);
		final ToggleButton compliteBox = (ToggleButton) v.findViewById(R.id.home_work_list_element_check_box);
        final ImageView photo = (ImageView) v.findViewById(R.id.home_work_image_preview);
        final View photoContainer = v.findViewById(R.id.home_work_list_element_image_container);
        final View body = v.findViewById(R.id.home_work_list_element_body);
        lessonTitle.setVisibility(View.VISIBLE);
		lessonTitle.setText(c.getString(c.getColumnIndex(LessonInformation.LESSON_TITLE)));
		message.setText(c.getString(c.getColumnIndex(HomeWork.MESSAGE)));

        boolean isComplite = c.getInt(c.getColumnIndex(HomeWork.COMPLITE)) > 0;

        compliteBox.setOnCheckedChangeListener(null);
		compliteBox.setChecked(isComplite);
        compliteBox.setOnCheckedChangeListener(this);
        
        compliteBox.setTag(c.getLong(c.getColumnIndex("_id")));

        if(isComplite){
            body.setBackground(context.getResources().getDrawable(R.drawable.hw_done_indicator));
        } else {
            body.setBackground(null);
        }

        ArrayList<String> images = Slipper.deserializeObjectToString(c.getBlob(c.getColumnIndex(HomeWork.IMAGES)));

        if(images != null && !images.isEmpty()){
            photoContainer.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(images.get(0), photo);
            photoCount.setText(images.size()+" фото");
        } else {
            photoContainer.setVisibility(View.GONE);
        }

	}

	@Override
	public long getHeaderId(Cursor c) {
		return c.getLong(c.getColumnIndex(HomeWork.DATE));
	}

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        DataManager.getInstance().setHomeWorkChecked((Long) buttonView.getTag(), isChecked);

    }
}
