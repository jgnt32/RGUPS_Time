package ru.rgups.time;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseAsyncFragment extends BaseFragment{

	private BaseAsyncTasck mAsyncTasck;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mAsyncTasck = new BaseAsyncTasck();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mAsyncTasck.execute();
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	
	protected  void preExecute(){}
	
	protected abstract void doInBackgroud();
	
	protected abstract void postExecute();
	
	private class BaseAsyncTasck extends AsyncTask<Void, Void, Void>{

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			preExecute();
		
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			
			doInBackgroud();
			return null;	
		}
		
		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			postExecute();
		}
		
	}
	
	
}
