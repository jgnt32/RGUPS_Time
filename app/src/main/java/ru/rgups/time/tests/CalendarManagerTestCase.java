package ru.rgups.time.tests;

import android.test.InstrumentationTestCase;

import java.util.Calendar;
import java.util.GregorianCalendar;

import ru.rgups.time.utils.CalendarManager;

/**
 * Created by timewaistinguru on 17.08.2014.
 */
public class CalendarManagerTestCase extends InstrumentationTestCase {

    public void testGetDayOfSemestr(){
        int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
        int calendarManagerDay = CalendarManager.getDayOfSemestr(Calendar.getInstance().getTimeInMillis());

        assertEquals(currentDay - CalendarManager.getDayOffset(), calendarManagerDay);
    }

    public void testGetDate(){
        int dayOfSemestr = CalendarManager.getCurrentDayOfTheYear();
        long calendarDate = CalendarManager.getDate(dayOfSemestr);
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.set(Calendar.DAY_OF_YEAR, dayOfSemestr + CalendarManager.getDayOffset());
        long expectualDate =  calendar.getTimeInMillis();
        assertEquals(expectualDate, calendarDate);
    }

}
