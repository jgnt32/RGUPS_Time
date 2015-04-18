package ru.rgups.time.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import ru.rgups.time.R;

public class CalendarListElement extends RelativeLayout{

	private static final int[] LESSON_STATE = {R.attr.lesson_free_state};
	
	private boolean mLessonFree = true;
	
	public CalendarListElement(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
        

	public CalendarListElement(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CalendarListElement(Context context) {
		super(context);
	}

	
	@Override
	protected int[] onCreateDrawableState(int extraSpace) {
	    final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
	    if(mLessonFree){
	        mergeDrawableStates(drawableState, LESSON_STATE);
	    }

		return drawableState;
	}
	
	public void setLessonFree(boolean value){
		this.mLessonFree = value;
	}
	
}
