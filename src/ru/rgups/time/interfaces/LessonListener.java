package ru.rgups.time.interfaces;

import java.util.Date;

public interface LessonListener {
	
	public void OnLessonListElementClick(long lessonId);
	public void OnAddHomeWorkClick(long lessonId, Date date);
}
