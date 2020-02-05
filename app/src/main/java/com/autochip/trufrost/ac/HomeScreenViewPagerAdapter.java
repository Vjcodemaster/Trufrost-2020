package com.autochip.trufrost.ac;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.google.android.material.card.MaterialCardView;


public class HomeScreenViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater mLayoutInflater;
    //private ArrayList<String> alImagePath;

    HomeScreenViewPagerAdapter(Context context){
        this.context = context;
        //this.alImagePath = alImagePath;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.home_image_pager_item, container, false);
        ImageView ivSlideShowImage = itemView.findViewById(R.id.iv_slide_show_image);

        /*ivDentImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DentInfoFragment.onAdapterInterface.onAdapterCall(TRANSITION_FRAGMENT, false, 0, null);
            }
        });*/
        //
        // ivDentImage.setImageURI(Uri.fromFile(new File(alImagePath.get(position))));
        switch (position) {
            case 0:
                ivSlideShowImage.setImageResource(R.drawable.slideshow_01);
                break;
            case 1:
                ivSlideShowImage.setImageResource(R.drawable.slideshow_02);
                break;
            case 2:
                ivSlideShowImage.setImageResource(R.drawable.slideshow_03);
                break;
            case 3:
                ivSlideShowImage.setImageResource(R.drawable.slideshow_04);
                break;
            case 4:
                ivSlideShowImage.setImageResource(R.drawable.slideshow_05);
                break;

        }
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //container.removeView((LinearLayout) object);
        container.removeView((MaterialCardView) object);
    }
}
