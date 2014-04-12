package ru.rgups.time;

import ru.rgups.time.spice.SampleSpiceService;
import android.support.v4.app.DialogFragment;

import com.octo.android.robospice.SpiceManager;

public class BaseDialogFragment extends DialogFragment{
    private SpiceManager spiceManager = new SpiceManager( SampleSpiceService.class );
    
    

    protected SpiceManager getSpiceManager() {
        return spiceManager;
    }



	@Override
	public void onStart() {
		super.onStart();
		spiceManager.start(getActivity());
	}



	@Override
	public void onStop() {
		super.onStop();
		spiceManager.shouldStop();
	}



}
