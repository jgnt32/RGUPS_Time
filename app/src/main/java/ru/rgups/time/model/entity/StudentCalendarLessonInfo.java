package ru.rgups.time.model.entity;

import ru.rgups.time.utils.CalendarManager;

/**
 * Created by timewaistinguru on 10.08.2014.
 */
public class StudentCalendarLessonInfo {

    protected boolean mLessonMatrix[][] = new boolean [7][2];

    protected int[] mHwVector = new int[CalendarManager.getCorrectDayCount()];

    public StudentCalendarLessonInfo() {
    }

    public StudentCalendarLessonInfo(boolean[][] mLessonMatrix, int[] mHwVector) {
        this.mLessonMatrix = mLessonMatrix;
        this.mHwVector = mHwVector;
    }

    public boolean[][] getLessonMatrix() {
        return mLessonMatrix;
    }

    public void setLessonMatrix(boolean[][] mLessonMatrix) {
        this.mLessonMatrix = mLessonMatrix;
    }

    public int[] getHwVector() {
        return mHwVector;
    }

    public void setHwVector(int[] mHwVector) {
        this.mHwVector = mHwVector;
    }
}
