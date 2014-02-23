package ru.rgups.time.fragments;

import ru.rgups.time.R;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HomeWorkFragment extends DialogFragment{

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.getDialog().setTitle("Домашнее задание");

		View v = inflater.inflate(R.layout.homework_fragment, null);
		return v;
	}
}
