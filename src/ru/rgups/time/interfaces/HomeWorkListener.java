package ru.rgups.time.interfaces;

public interface HomeWorkListener {
	
	public void AddHomeWork(long lessonId, Long date);
	
	public void EditHomeWork(Long hwId);
	
	public void finisActivity();

}
