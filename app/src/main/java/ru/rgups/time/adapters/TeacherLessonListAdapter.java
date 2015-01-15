package ru.rgups.time.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;
import ru.rgups.time.R;
import ru.rgups.time.model.entity.teachers.TeachersLesson;

public class TeacherLessonListAdapter extends RealmBaseAdapter<TeachersLesson>{
	private LayoutInflater mInflater;	
	private String[] mTime;
	private String mHeaderNumber;
    private ViewHolder mHolder;

    public TeacherLessonListAdapter(Context context, RealmResults<TeachersLesson> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mTime = context.getResources().getStringArray(R.array.lessons_time_periods);
        mHeaderNumber = context.getResources().getString(R.string.lesson_list_divider);

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.lesson_list_element, null);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);

        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }

        mHolder.teacher.setText(getItem(position).getTeacherName());
        mHolder.room.setText(getItem(position).getRoom());
        mHolder.lessonTitle.setText(getItem(position).getSubject());

        mHolder.number.setText(""+getItem(position).getNumber());

        /*number.setText(mHeaderNumber.replace("#", Integer.toString(c.getInt(c.getColumnIndex(LessonTableModel.NUMBER)))));
        time.setText(mTime[c.getInt(c.getColumnIndex(LessonTableModel.NUMBER))-1]);*/

        return convertView;
    }

    private class ViewHolder {
        private TextView teacher;
        private TextView room;
        private TextView lessonTitle;
        private TextView number;
        private TextView time;

        private ViewHolder(View view) {
           teacher = (TextView) view.findViewById(R.id.lesson_teacher);
           room = (TextView) view.findViewById(R.id.lesson_room);
           lessonTitle = (TextView) view.findViewById(R.id.lesson_title);
           number = (TextView) view.findViewById(R.id.divider_text);
           time = (TextView) view.findViewById(R.id.lesson_divider_time);        }

        public TextView getTeacher() {
            return teacher;
        }

        public void setTeacher(TextView teacher) {
            this.teacher = teacher;
        }

        public TextView getRoom() {
            return room;
        }

        public void setRoom(TextView room) {
            this.room = room;
        }

        public TextView getLessonTitle() {
            return lessonTitle;
        }

        public void setLessonTitle(TextView lessonTitle) {
            this.lessonTitle = lessonTitle;
        }

        public TextView getNumber() {
            return number;
        }

        public void setNumber(TextView number) {
            this.number = number;
        }

        public TextView getTime() {
            return time;
        }

        public void setTime(TextView time) {
            this.time = time;
        }
    }
}
