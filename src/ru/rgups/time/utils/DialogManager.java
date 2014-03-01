package ru.rgups.time.utils;

import ru.rgups.time.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;

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
	

}
