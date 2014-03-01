package ru.rgups.time.interfaces;

import java.util.Date;

public interface LessonListener {
	
	public void OnLessonListElementClick(long lessonId, Long date);
	public void OnAddHomeWorkClick(long lessonId, Long date);
}
