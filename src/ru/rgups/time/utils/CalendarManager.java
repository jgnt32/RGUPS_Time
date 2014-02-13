package ru.rgups.time.utils;

import java.util.Calendar;
import java.util.GregorianCalendar;

import android.text.format.DateFormat;

public class CalendarManager {

	private final String MONTH_FORMAT = "LLLL";
	
	private static CalendarManager mInstance;
	
	private GregorianCalendar mSemestrCalendar;
	private int mOldMonth;
	public static final int FIRST_SEMESTR = 0;
	public static final int SECOND_SEMESTR = 1;
	
	public static CalendarManager getInstance(){
		if(mInstance == null){
			mInstance = new CalendarManager();			
		}
		return mInstance;
	}
	
	public CalendarManager() {
			
		mSemestrCalendar = new GregorianCalendar();
		mSemestrCalendar.setTime(Calendar.getInstance().getTime());
	}
	
	public String getCalendarListMonthTitle(int shift){
		mSemestrCalendar.set(GregorianCalendar.DAY_OF_YEAR, shift+getDayOffset());
		return DateFormat.format(MONTH_FORMAT, mSemestrCalendar.getTime()).toString();
	
	}
	
	public int getCurrentDatOfTheYear(){
		mSemestrCalendar.setTime(Calendar.getInstance().getTime());
		return mSemestrCalendar.get(GregorianCalendar.DAY_OF_YEAR)-getDayOffset();
	}
	
	public int getCalendarListMonthNumber(int shift){
		mSemestrCalendar.set(GregorianCalendar.DAY_OF_YEAR, shift+getDayOffset());
		return mSemestrCalendar.get(GregorianCalendar.MONTH);
	}
	
	public boolean montIsChanged(int shift){
		if(mOldMonth != getCalendarListMonthNumber(shift)){
			mOldMonth = getCalendarListMonthNumber(shift);
			return true;
		} else {
			mOldMonth = getCalendarListMonthNumber(shift);
			return false;
		}
	}
	
	public static int spotSemestr(){
		int month = Calendar.getInstance().get(Calendar.MONTH);
		if(month>=Calendar.SEPTEMBER){
			return FIRST_SEMESTR;
		}else{
			return SECOND_SEMESTR;
		}

	}
	
	public static int getDayOffset(){
		if(spotSemestr() == FIRST_SEMESTR){
			Calendar calendar = Calendar.getInstance();
			int dayOffset = calendar.getMaximum(Calendar.DAY_OF_YEAR)-getCorrectDayCount();
			return dayOffset;
		}else{
			return 1;  //because first position = 0
		}
	
	}
	
	public static int getCorrectDayCount(){
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(Calendar.getInstance().getTime());
		if(spotSemestr() == ConstUtils.FIRST_SEMESTR){
			return 122;						//первый семестр
		}else{
			if(calendar.getMaximum(Calendar.DAY_OF_YEAR)==355){ //второй семестр
				return 212;
			}else{
				return 213;//високосний год
			}
		}
		
	}
}
