package ru.rgups.time.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;
import ru.rgups.time.R;
import ru.rgups.time.model.entity.teachers.Teacher;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

public class TeacherListAdapter extends RealmBaseAdapter<Teacher> implements StickyListHeadersAdapter {
	
	private LayoutInflater mInflater;
    private RealmResults<Teacher> teachers;

    public TeacherListAdapter(Context context, RealmResults<Teacher> teachers) {
        super(context, teachers, true);
        this.teachers = teachers;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View getHeaderView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = mInflater.inflate(R.layout.group_list_divier, viewGroup, false);
        }
        final TextView text = (TextView) view.findViewById(R.id.levelTitle);
        text.setText(getItem(i).getShortName().substring(0, 1));
        return view;
    }

    @Override
    public long getHeaderId(int i) {
        return getItem(i).getShortName().substring(0, 1).hashCode();
    }

    @Override
    public int getCount() {
        return teachers.size();
    }

    @Override
    public Teacher getItem(int position) {
        return teachers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.simple_list_element, parent, false);
        }
        TextView name = (TextView) convertView.findViewById(R.id.list_element_text);
        name.setText(getItem(position).getShortName());
        return convertView;
    }

    public void swap(RealmResults<Teacher> teachers){
        this.teachers = teachers;
        notifyDataSetChanged();
    }

}
