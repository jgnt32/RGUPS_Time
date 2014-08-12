package ru.rgups.time.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.rgups.time.R;
import ru.rgups.time.interfaces.PickUpImageListener;

/**
 * Created by timewaistinguru on 11.08.2014.
 */
public class PickDialogFragment extends DialogFragment implements View.OnClickListener{

    private PickUpImageListener mImageListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.image_pick_up_dialog_fragment, null);
        v.findViewById(R.id.pick_up_new_photo).setOnClickListener(this);
        v.findViewById(R.id.pick_up_photo_from_gallery).setOnClickListener(this);
        getDialog().setTitle(R.string.pick_up_image_dialog_title);

        return v;
    }



    public void setImageListener(PickUpImageListener mImageListener) {
        this.mImageListener = mImageListener;
    }

    private void pickNewPhoto(){
        if(mImageListener != null){
            mImageListener.onPickNewPhotoClick();
            this.getDialog().dismiss();
        }
    }

    private void pickPhotoFromGallery(){
        if(mImageListener != null){
            mImageListener.onPickPhotoFromGalleryClick();
            this.getDialog().dismiss();

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.pick_up_new_photo:
                pickNewPhoto();
                break;

            case R.id.pick_up_photo_from_gallery:
                pickPhotoFromGallery();
                break;
        }
    }
}
