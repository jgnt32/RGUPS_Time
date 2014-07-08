package ru.rgups.time.interfaces;

public interface AuthListener{
	public void OpenFacultetList();
	public void OpenGroupList(Long facultetId);
	public void setActionbarTitle(int stringRes);
	public void finishAuthActivity();
}