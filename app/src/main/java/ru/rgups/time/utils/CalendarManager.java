package ru.rgups.time.utils;

import android.os.Build;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class CalendarManager {

	private static final String MONTH_FORMAT = "MMMMM";
	
	private static CalendarManager mInstance;
	
	private static GregorianCalendar mSemestrCalendar = new GregorianCalendar();
	private static int mOldMonth;

	public static final int FIRST_SEMESTR = 0;
	public static final int SECOND_SEMESTR = 1;

    public static final int OVER_LINE = 0;
    public static final int UNDER_LINE = 1;

    public static final int FIRST_SEMESTR_DAY_COUNT = 122;
    public static final int SECOND_SEMESTR_LEAP_DAY_COUNT = 213;
    public static final int SECOND_SEMESTR_UNLEAP_DAY_COUNT = 212;

    public static final int MILISECONDS_PER_DAY = 86400000;

    public static enum WEEK_STATE {
        UNDER_LINE,
        OVER_LINE
    }

    public static enum SEMESTR{
        FIRST_SEMESTR,
        SECOND_SEMESTR

    }

    public final static boolean UPARITY_WEEK_IS_OVERLINE = getPointOfReference();


    public static enum POINT_OF_REFERENCE{
        PARITY_WEEK_IS_OVER_LINE,
        UNPARITY_WEEK_IS_OVER_LINE
    }


	public static synchronized String getCalendarListMonthTitle(int shift){
		mSemestrCalendar.set(GregorianCalendar.DAY_OF_YEAR, shift + getDayOffset());
		return getCalendarHintDateFormat().format(mSemestrCalendar.getTime());
	}


    private static SimpleDateFormat getCalendarHintDateFormat(){
        String fmt = Build.VERSION.SDK_INT <= 8 ? "MMMM" : "LLLL";
        return new SimpleDateFormat(fmt);
    }


	public static synchronized int getCurrentDayOfTheYear(){
		mSemestrCalendar.setTime(Calendar.getInstance().getTime());
		return mSemestrCalendar.get(GregorianCalendar.DAY_OF_YEAR) - getDayOffset();
	}

    public static synchronized int getDayOfTheYear(int dayOfSemestr){

        return dayOfSemestr + getDayOffset();
    }


	public static synchronized int getCalendarListMonthNumber(int shift){
		mSemestrCalendar.set(GregorianCalendar.DAY_OF_YEAR, shift + getDayOffset());
		return mSemestrCalendar.get(GregorianCalendar.MONTH);
	}


	public static synchronized boolean montIsChanged(int shift){
		if(mOldMonth != getCalendarListMonthNumber(shift)){
			mOldMonth = getCalendarListMonthNumber(shift);
			return true;
		} else {
			mOldMonth = getCalendarListMonthNumber(shift);
			return false;
		}
	}

	public static synchronized SEMESTR spotSemestr(){
		int month = Calendar.getInstance().get(Calendar.MONTH);
		if(month >= Calendar.AUGUST){
			return SEMESTR.FIRST_SEMESTR;
		}else{
			return SEMESTR.SECOND_SEMESTR;
		}
	}

    public static synchronized int getSemester(){
        if (spotSemestr() == SEMESTR.FIRST_SEMESTR) {
            return 1;
        } else {
            return 2;
        }
    }

    public static synchronized int getYear(){
        return Calendar.getInstance().get(Calendar.YEAR);
    }


    public static synchronized long getDate(int dayOfSemestr){
        mSemestrCalendar.setFirstDayOfWeek(GregorianCalendar.MONDAY);
        mSemestrCalendar.setMinimalDaysInFirstWeek(4);
        mSemestrCalendar.set(Calendar.DAY_OF_YEAR, dayOfSemestr + getDayOffset());
        return mSemestrCalendar.getTimeInMillis();
    }

    private synchronized static GregorianCalendar getCalendar(int dayOfSemestr){
        mSemestrCalendar.setFirstDayOfWeek(GregorianCalendar.MONDAY);
        mSemestrCalendar.setMinimalDaysInFirstWeek(4);
        mSemestrCalendar.set(Calendar.DAY_OF_YEAR, dayOfSemestr + getDayOffset());
        return mSemestrCalendar;
    }


    public static  synchronized int getDayOfSemestr(long date){

        mSemestrCalendar.setFirstDayOfWeek(GregorianCalendar.MONDAY);
        mSemestrCalendar.setMinimalDaysInFirstWeek(4);
        mSemestrCalendar.setTimeInMillis(date);
        return mSemestrCalendar.get(Calendar.DAY_OF_YEAR) - getDayOffset();
    }

    public static synchronized int getDayOfWeek(int dayOfSemestr){

        if(getCalendar(dayOfSemestr).get(GregorianCalendar.DAY_OF_WEEK) == GregorianCalendar.SUNDAY){
            return 7;
        }else{
            return getCalendar(dayOfSemestr).get(GregorianCalendar.DAY_OF_WEEK) - 1;
        }
    }

	public static synchronized int getDayOffset(){
		if(spotSemestr() == SEMESTR.FIRST_SEMESTR){
			int dayOffset = Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_YEAR) - getCorrectSemestrDayCount();
			return dayOffset + 1;
		}else{
			return 1;  // 1 -- because first position = 0 equel 31 december
		}

	}

	public static synchronized int getCorrectSemestrDayCount(){

		if(spotSemestr() == SEMESTR.FIRST_SEMESTR){
			return FIRST_SEMESTR_DAY_COUNT;						//первый семестр
		}else{
			if(mSemestrCalendar.isLeapYear(mSemestrCalendar.get(Calendar.YEAR))){ //второй семестр
				return SECOND_SEMESTR_LEAP_DAY_COUNT;
			}else{
				return SECOND_SEMESTR_UNLEAP_DAY_COUNT;//високосний год
			}
		}
	}


    public static synchronized boolean weekIsParity(int weekNumber){
        if(weekNumber % 2 == 0){
            return true;
        }else{
            return false;
        }
    }


    public static synchronized boolean isOverLine(int dayOfSemestr){

        int weekOfYear = getWeekNumber(dayOfSemestr);

        if(UPARITY_WEEK_IS_OVERLINE){
            if(weekIsParity(weekOfYear)){
                return false;
            }else{
                return true;
            }
        }else{
            if(weekIsParity(weekOfYear)){
                return true;
            }else{
                return false;
            }
        }
    }


    public static synchronized int getWeekNumber(int dayOfSemestr) {
        mSemestrCalendar.setFirstDayOfWeek(GregorianCalendar.MONDAY);
        mSemestrCalendar.setMinimalDaysInFirstWeek(4);
        mSemestrCalendar.set(Calendar.DAY_OF_YEAR, dayOfSemestr + getDayOffset());
        return mSemestrCalendar.get(Calendar.WEEK_OF_YEAR);
    }


    private static boolean getPointOfReference(){
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(Calendar.getInstance().getTime());
        calendar.setFirstDayOfWeek(GregorianCalendar.MONDAY);
        calendar.setMinimalDaysInFirstWeek(4);
        calendar.set(Calendar.MONTH, Calendar.SEPTEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        int firstWeekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);

        if(calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY &&
                calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY){

            if(weekIsParity(firstWeekOfYear)){
                return false;
            }else{
                return true;
            }

        }else{
            if(weekIsParity(firstWeekOfYear)){
                return true;
            }else{
                return false;
            }
        }
    }

    public static synchronized boolean isMonday(int dayOfSemestr){
        if(getCalendar(dayOfSemestr).get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY){
            return true;
        } else {
            return false;
        }
    }


    public static synchronized boolean isSunday(int dayOfSemestr){
        if(getCalendar(dayOfSemestr).get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
            return true;
        } else {
            return false;
        }

    }

    public static synchronized int getWeekState(int dayOfSemestr){
        if(isOverLine(dayOfSemestr)){
            return OVER_LINE;
        } else {
            return UNDER_LINE;
        }
    }

}
