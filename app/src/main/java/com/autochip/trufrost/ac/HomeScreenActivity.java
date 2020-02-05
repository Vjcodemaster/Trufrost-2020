package com.autochip.trufrost.ac;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;

import app_utility.Constants;
import app_utility.OnFragmentInteractionListener;

import static app_utility.StaticReferenceClass.OPEN_INDIVIDUAL_PRODUCT_FRAGMENT;

public class HomeScreenActivity extends AppCompatActivity implements OnFragmentInteractionListener {

    private RecyclerView rvMainCategory;
    private ViewPager mViewPagerSlideShow;

    HomeScreenViewPagerAdapter homeScreenViewPagerAdapter;

    OnFragmentInteractionListener mListener;

    TextView tvHeading, tvSubHeading;
    LinearLayout llHeadingParent;
    MaterialCardView mcvSubHeading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        initViews();
        mListener = this;
        homeScreenViewPagerAdapter = new HomeScreenViewPagerAdapter(HomeScreenActivity.this);
        mViewPagerSlideShow.setAdapter(homeScreenViewPagerAdapter);
        MainCategoryRVAdapter imageViewRVAdapter = new MainCategoryRVAdapter(rvMainCategory, mListener);
        rvMainCategory.setAdapter(imageViewRVAdapter);
    }

    private void initViews() {
        tvHeading = findViewById(R.id.tv_heading);
        tvSubHeading = findViewById(R.id.tv_sub_heading);
        mcvSubHeading = findViewById(R.id.mcv_sub_heading);
        llHeadingParent = findViewById(R.id.ll_heading);

        rvMainCategory = findViewById(R.id.rv_main_category);
        LinearLayoutManager mLinearLayoutManager;
        mLinearLayoutManager = new GridLayoutManager(HomeScreenActivity.this, 2);
        mLinearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rvMainCategory.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        rvMainCategory.setHasFixedSize(true);
        rvMainCategory.setLayoutManager(mLinearLayoutManager);

        mViewPagerSlideShow = findViewById(R.id.viewpager_slideshow);

        mViewPagerSlideShow.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {


            }
        });

        mViewPagerSlideShow.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //nCount++;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        final Handler handler = new Handler();
        final int delay = 8000; //milliseconds

        handler.postDelayed(new Runnable() {
            public void run() {
                //do something
                if (mViewPagerSlideShow.getCurrentItem() == homeScreenViewPagerAdapter.getCount() - 1) {
                    mViewPagerSlideShow.setCurrentItem(0);
                } else
                    mViewPagerSlideShow.setCurrentItem((mViewPagerSlideShow.getCurrentItem()) + 1);
                handler.postDelayed(this, delay);
            }
        }, delay);

        /*Fragment fragmentSubCategoryFirst = SubCategoryManagerFragment.newInstance("","");
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.ll_container, fragmentSubCategoryFirst);
        ft.addToBackStack(null);
        ft.commit();*/
        /*
        Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {
                // force transform with a 1 pixel nudge
                mViewPagerSlideShow.beginFakeDrag();
                mViewPagerSlideShow.fakeDragBy(0.1f);
                mViewPagerSlideShow.endFakeDrag();
            }
        };
        handler.postDelayed(r, 30);*/
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fl_container);
            //currentFragment.getClass().getName();
            //String fragmentTag = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1).getName();
            if (currentFragment.getClass().getName().equals(AllProductsFragment.class.getName())) {
                mcvSubHeading.setVisibility(View.GONE);
            }
        } else
            llHeadingParent.setVisibility(View.GONE);

        super.onBackPressed();
    }

    @Override
    public void onFragmentCalled(int nCase, String sResult) {
        Constants constants = Constants.values()[nCase];
        switch (constants) {
            case OPEN_FRAGMENT_MANAGER:
                Toast.makeText(HomeScreenActivity.this, "Triggered", Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void onActivityCalled(int nCase, String sResult) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Constants constants = Constants.values()[nCase];
        switch (constants) {
            case OPEN_FRAGMENT_MANAGER:
                //Toast.makeText(HomeScreenActivity.this, "Triggered", Toast.LENGTH_LONG).show();
                Fragment fragmentSubCategoryFirst = SubCategoryManagerFragment.newInstance("", "");
                ft.add(R.id.fl_container, fragmentSubCategoryFirst);
                ft.addToBackStack(null);
                ft.commit();
                tvHeading.setText(sResult.trim());
                llHeadingParent.setVisibility(View.VISIBLE);
                break;
            case OPEN_ALL_PRODUCTS:
                Fragment allProductsFragment = AllProductsFragment.newInstance(sResult, "");
                ft.add(R.id.fl_container, allProductsFragment);
                ft.addToBackStack(null);
                ft.commit();
                tvSubHeading.setText(sResult);
                mcvSubHeading.setVisibility(View.VISIBLE);
                break;
            case OPEN_INDIVIDUAL_PRODUCT_FRAGMENT:
                Fragment individualProductFragment = IndividualProductFragment.newInstance(sResult, "");
                ft.add(R.id.fl_container, individualProductFragment);
                ft.addToBackStack(null);
                ft.commit();
                break;
           /* case MAIN_CATEGORY_UPDATE:
                break;*/
            case SUB_CATEGORY_HEADING_UPDATE:
                tvSubHeading.setText(sResult);
                mcvSubHeading.setVisibility(View.VISIBLE);
                break;
        }
    }
}
