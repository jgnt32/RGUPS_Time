package ru.rgups.time.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.view.View;

import ru.rgups.time.R;

public class DialogManager {
	
	private static DialogManager mInstance;
	private Context mContext;
	
	public static void initInstatnce(Context context){
		mInstance = new DialogManager(context);
	}
	

	public DialogManager (Context context){
		mContext = context;
	}
	
	public static DialogManager getInstance(){
		return mInstance;
	}
	
	
	public static void showNeutralDialog(Context context, int messageRes){
		AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(context);
		mAlertDialog.setTitle(context.getString(R.string.app_name));
		mAlertDialog.setMessage(context.getString(messageRes));
		mAlertDialog.setNeutralButton(context.getString(R.string.ok_caption), null);		
		mAlertDialog.show();
	}
	
	
	public static void showNeutralDialog(Context context, String message){
		AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(context);
		mAlertDialog.setTitle(context.getString(R.string.app_name));
		mAlertDialog.setMessage(message);
		mAlertDialog.setNeutralButton(context.getString(R.string.ok_caption), null);		
		mAlertDialog.show();
	}
	
	public static void showNeutralCustomDialog(Context context, View v){
		AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(context);
		mAlertDialog.setTitle(context.getString(R.string.app_name));
		mAlertDialog.setNeutralButton(context.getString(R.string.ok_caption), null);	
		mAlertDialog.setView(v);
		mAlertDialog.show();
	}
	
	
	
	public static void showNeutralDialog(Context context,int messageRes, OnClickListener onClick){
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
		alertDialog.setTitle(context.getString(R.string.app_name));
		alertDialog.setMessage(context.getString(messageRes));
		alertDialog.setNeutralButton(context.getString(R.string.ok_caption), onClick);		
		alertDialog.setCancelable(false);
		alertDialog.show();
	}
	
	public static void showPositiveDialog(Context context, int messageRes, OnClickListener onClick){
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
		alertDialog.setTitle(context.getString(R.string.app_name));
		alertDialog.setMessage(context.getString(messageRes));
		alertDialog.setNegativeButton(context.getString(R.string.negative_caption), null);		
		alertDialog.setPositiveButton(R.string.positive_caption, onClick);
		alertDialog.setCancelable(true);
		alertDialog.show();
	}
	
	public static ProgressDialog getNewProgressDialog(Context context, int messageRes){
		ProgressDialog result = new ProgressDialog(context);
		result.setMessage(context.getResources().getString(messageRes));
		result.setCancelable(false);
		return result;
	}
	
	public static ProgressDialog getNewProgressDialog(Context context, String message){
		ProgressDialog result = new ProgressDialog(context);
		result.setMessage(message);
		result.setCancelable(false);
		return result;
	}
	
	

}
