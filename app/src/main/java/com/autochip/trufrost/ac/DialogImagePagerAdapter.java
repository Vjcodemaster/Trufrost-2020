package com.autochip.trufrost.ac;

/*
 * Created by Vj on 30-Mar-17.
 */

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.io.File;
import java.util.ArrayList;

import library.TouchImageView;

class DialogImagePagerAdapter extends PagerAdapter {

    private Activity aActivity;
    private LayoutInflater mLayoutInflater;
    private boolean isEnabledPagePadding;
    //private OnFragmentInteractionListener mListener;
    //private String[] saImagePath;
    ArrayList<String> alImagesPath = new ArrayList<>();
    private OnTouchListener onTouchListener;
    //private CircularProgressBar circularProgressBar;

    DialogImagePagerAdapter(Activity aActivity, ArrayList<String> alImagesPath) {
        this.aActivity = aActivity;
        this.alImagesPath = alImagesPath;
        mLayoutInflater = (LayoutInflater) aActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return alImagesPath.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @NonNull
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.dialog_image_pager_item, container, false);

        /*ImageView imageView = itemView.findViewById(R.id.imageView);
        Uri uri = Uri.fromFile(new File(alImagesPath.get(position)));
        imageView.setImageURI(uri);*/


        final TouchImageView touchImageView = itemView.findViewById(R.id.iv_event_pager);
        Uri uri = Uri.fromFile(new File(alImagesPath.get(position)));
        touchImageView.setImageURI(uri);
        container.addView(itemView);
        if (isEnabledPagePadding){
            touchImageView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    if (touchImageView.getCurrentZoom() > 1.0) {
                        onTouchListener.onTouch(true);
                    } else {

                        onTouchListener.onTouch(false);
                    }
                    return false;
                }
            });
        }
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    interface  OnTouchListener{
        void onTouch(boolean isZoom);
    }
}
