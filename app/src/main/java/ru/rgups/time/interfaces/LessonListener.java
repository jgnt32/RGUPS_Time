package ru.rgups.time.interfaces;


public interface LessonListener {
	
	public void OnLessonListElementClick(long lessonId, Long date);
	public void OnAddHomeWorkClick(long lessonId, Long date);
	public void OnHomeWorkListElementClick(long hwId);
//	public void onHomeWorkListFragmentClick(long hwId);
	public void onTeacherClick(String teachersName);
}
