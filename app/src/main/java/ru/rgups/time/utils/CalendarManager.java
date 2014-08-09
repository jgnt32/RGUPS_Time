package ru.rgups.time.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.text.format.DateFormat;

public class CalendarManager {

	private static final String MONTH_FORMAT = "LLLL";
	
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


	public static String getCalendarListMonthTitle(int shift){
		mSemestrCalendar.set(GregorianCalendar.DAY_OF_YEAR, shift + getDayOffset());
		return DateFormat.format(MONTH_FORMAT, mSemestrCalendar.getTime()).toString();
	}


	public static int getCurrentDayOfTheYear(){
		mSemestrCalendar.setTime(Calendar.getInstance().getTime());
		return mSemestrCalendar.get(GregorianCalendar.DAY_OF_YEAR) - getDayOffset();
	}

    public static int getDayOfTheYear(int dayOfSemestr){

        return dayOfSemestr + getDayOffset();
    }


	public static int getCalendarListMonthNumber(int shift){
		mSemestrCalendar.set(GregorianCalendar.DAY_OF_YEAR, shift + getDayOffset());
		return mSemestrCalendar.get(GregorianCalendar.MONTH);
	}

	
	public static boolean montIsChanged(int shift){
		if(mOldMonth != getCalendarListMonthNumber(shift)){
			mOldMonth = getCalendarListMonthNumber(shift);
			return true;
		} else {
			mOldMonth = getCalendarListMonthNumber(shift);
			return false;
		}
	}
	
	public static SEMESTR spotSemestr(){
		int month = Calendar.getInstance().get(Calendar.MONTH);
		if(month >= Calendar.AUGUST){
			return SEMESTR.FIRST_SEMESTR;
		}else{
			return SEMESTR.SECOND_SEMESTR;
		}
	}

    public static long getDate(int dayOfSemestr){
        mSemestrCalendar.setTimeInMillis(System.currentTimeMillis());
        mSemestrCalendar.setFirstDayOfWeek(GregorianCalendar.MONDAY);
        mSemestrCalendar.setMinimalDaysInFirstWeek(4);
        mSemestrCalendar.set(Calendar.DAY_OF_YEAR, dayOfSemestr + getDayOffset());
        return mSemestrCalendar.getTimeInMillis();
    }

    private static GregorianCalendar getCalendar(int dayOfSemestr){
        mSemestrCalendar.setTimeInMillis(System.currentTimeMillis());
        mSemestrCalendar.setFirstDayOfWeek(GregorianCalendar.MONDAY);
        mSemestrCalendar.setMinimalDaysInFirstWeek(4);
        mSemestrCalendar.set(Calendar.DAY_OF_YEAR, dayOfSemestr + getDayOffset());
        return mSemestrCalendar;
    }

    public static int getDayOfWeek(int dayOfSemestr){

        if(getCalendar(dayOfSemestr).get(GregorianCalendar.DAY_OF_WEEK) == GregorianCalendar.SUNDAY){
            return 7;
        }else{
            return getCalendar(dayOfSemestr).get(GregorianCalendar.DAY_OF_WEEK) - 1;
        }
    }
	
	public static int getDayOffset(){
		if(spotSemestr() == SEMESTR.FIRST_SEMESTR){
			int dayOffset = Calendar.getInstance().getMaximum(Calendar.DAY_OF_YEAR) - getCorrectDayCount();
			return dayOffset;
		}else{
			return 1;  // 1 -- because first position = 0 equel 31 december
		}
	
	}
	
	public static int getCorrectDayCount(){

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


    public static boolean weekIsParity(int weekNumber){
        if(weekNumber % 2 == 0){
            return true;
        }else{
            return false;
        }
    }


    public static boolean isOverLine(int dayOfSemestr){

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


    public static int getWeekNumber(int dayOfSemestr) {
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

    public static boolean isMonday(int dayOfSemestr){
        if(getCalendar(dayOfSemestr).get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY){
            return true;
        } else {
            return false;
        }
    }


    public static boolean isSunday(int dayOfSemestr){
        if(getCalendar(dayOfSemestr).get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
            return true;
        } else {
            return false;
        }

    }

    public static int getWeekState(int dayOfSemestr){
        if(isOverLine(dayOfSemestr)){
            return OVER_LINE;
        } else {
            return UNDER_LINE;
        }
    }

}
