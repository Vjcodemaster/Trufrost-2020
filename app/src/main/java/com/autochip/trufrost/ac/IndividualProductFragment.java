package com.autochip.trufrost.ac;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bogdwellers.pinchtozoom.ImageMatrixTouchHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import app_utility.Constants;
import app_utility.OnFragmentInteractionListener;
import app_utility.SharedPreferencesClass;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link app_utility.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link IndividualProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IndividualProductFragment extends Fragment implements OnFragmentInteractionListener{
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    TextView tvProductName, tvProductDescription;
    RecyclerView recyclerViewImages;
    TableLayout tlTechnicalSpecs;

    ImageView ivMainImage;
    TableRow tableRowHeading, tableRowValue;
    TextView[] tvTechSpecsHeading;
    TextView[] tvTechSpecsValues;

    String sProductName;
    String sImagePath;
    String sTechSpecKey;
    String sTechSpecValue;
    String sDescription;

    ArrayList<String> alTechHeading;
    ArrayList<String> alTechValues;

    Dialog dialogViewPager;
    ImageView ivBlurImage;
    ViewPager mViewPagerSlideShow;
    ImageView ivLeftArrow;
    ImageView ivRightArrow;
    ArrayList<String> alImagesPath = new ArrayList<>();

    int imagePathPosition = 0;
    FrameLayout flParentViewPager;
    private int padding = 20;
    SharedPreferencesClass sharedPreferencesClass;


    public IndividualProductFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IndividualProductFragment.
     */
    public static IndividualProductFragment newInstance(String param1, String param2) {
        IndividualProductFragment fragment = new IndividualProductFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            sProductName = bundle.getString("product_name");
            sImagePath = bundle.getString("image_path");
            sTechSpecKey = bundle.getString("tech_spec_key");
            sTechSpecValue = bundle.getString("tech_spec_value");
            sDescription = bundle.getString("description");
        }
        mListener = this;
        if(sProductName.equals("GN 680 TNM")) {
            sharedPreferencesClass = new SharedPreferencesClass(getActivity());
            alImagesPath = new ArrayList<>(Arrays.asList(sharedPreferencesClass.getImagesPath().split(",")));
            alImagesPath.add(0, sImagePath);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        //if (sProductName.equals("GN 680 TNM"))
            view = inflater.inflate(R.layout.layout_multiple_images, container, false);
        //else
            //view = inflater.inflate(R.layout.fragment_individual_product, container, false);
        initViews(view);
        getData(inflater);
        return view;
    }


    private void initViews(View view) {
        tvProductName = view.findViewById(R.id.tv_product_name);
        tvProductName.setText(sProductName);
        tvProductDescription = view.findViewById(R.id.tv_product_description);
        tvProductDescription.setText(sDescription);
        tlTechnicalSpecs = view.findViewById(R.id.tl_technical_specs);
        ivMainImage = view.findViewById(R.id.iv_main_image);

        ivMainImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initImagePreviewDialog();
                dialogViewPager.show();

                String path = ivMainImage.getTag().toString();

                int imagePathPosition = alImagesPath.indexOf(path);
                mViewPagerSlideShow.setCurrentItem(imagePathPosition);
                handleArrow(imagePathPosition);
            }
        });

        tableRowHeading = new TableRow(getActivity());
        tableRowHeading.setBackgroundColor(getActivity().getResources().getColor(android.R.color.white));
        tableRowValue = new TableRow(getActivity());
        tableRowValue.setBackgroundColor(getActivity().getResources().getColor(android.R.color.white));

        recyclerViewImages = view.findViewById(R.id.rv_individual_product_images);
        LinearLayoutManager mLinearLayoutManager;

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mLinearLayoutManager = new GridLayoutManager(getActivity(), 6);
            mLinearLayoutManager.setOrientation(RecyclerView.VERTICAL);
            // In landscape
        } else {
            mLinearLayoutManager = new GridLayoutManager(getActivity(), 4);
            mLinearLayoutManager.setOrientation(RecyclerView.VERTICAL);
            // In portrait
        }
        /*LinearLayoutManager mLinearLayoutManager = new GridLayoutManager(getActivity(), 4);
        mLinearLayoutManager.setOrientation(RecyclerView.VERTICAL);*/

        recyclerViewImages.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        recyclerViewImages.setHasFixedSize(true);
        recyclerViewImages.setLayoutManager(mLinearLayoutManager);

        IndividualProductRVAdapter individualProductRVAdapter = new IndividualProductRVAdapter(getActivity(), recyclerViewImages, alImagesPath, mListener);
        recyclerViewImages.setAdapter(individualProductRVAdapter);
        //recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL));
        //
        // saImagePath = dbh.getImagePathFromProducts(mParam3).split(",");
        //alImagesPath = new ArrayList<>(Arrays.asList(dbh.getImagePathFromProducts(mParam3).split(",")));
        Uri uri = Uri.fromFile(new File(sImagePath));

        /*BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(new File(uri.getPath()).getAbsolutePath(), options);
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;
        int height;
        int width;
        if (imageHeight > imageWidth) {
            height = (int) (340f);
            width = (int) (280f);
        } else {
            height = (int) (200f);
            width = (int) (340f);
        }
        ViewGroup.LayoutParams params = ivMainImage.getLayoutParams();
        params.height = height;
        params.width = width;

        ivMainImage.setLayoutParams(params);*/

        ivMainImage.setTag(sImagePath);
        ivMainImage.setImageURI(uri);
        /*ArrayList<String> alImagesPath = new ArrayList<>();
        IndividualProductRVAdapter individualProductRVAdapter = new IndividualProductRVAdapter(getActivity(), recyclerViewImages,
                alImagesPath, mListener);
        recyclerViewImages.setAdapter(individualProductRVAdapter);*/
    }


    private void getData(LayoutInflater inflater) {
        //ArrayList<DataBaseHelper> arrayList = new ArrayList<>(dbh.getAllMainProducts());
        //ArrayList<DataBaseHelper> arrayList1 = new ArrayList<>(dbh.getAllProductsData1());
        alTechHeading = new ArrayList<>(Arrays.asList(sTechSpecKey.split(",")));
        alTechValues = new ArrayList<>(Arrays.asList(sTechSpecValue.split(",")));

        tvTechSpecsHeading = new TextView[alTechHeading.size()];
        tvTechSpecsValues = new TextView[alTechValues.size()];

        tvTechSpecsValues = new TextView[alTechValues.size()];

        //trHeading = (TableRow) inflater.inflate(R.layout.table_row, null);
        for (int i = 0; i < alTechHeading.size(); i++) {
            addDynamicTextViewTechSpecs(i, inflater);
        }
    }

    private void addDynamicTextViewTechSpecs(int i, LayoutInflater inflater) {
        TableRow row = (TableRow) inflater.inflate(R.layout.table_row, null);
        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        params.setMargins(1, 2, 1, 2);
        params.gravity = Gravity.START;

        TextView tvKey = row.findViewById(R.id.tv_key);
        tvKey.setText(alTechHeading.get(i));

        TextView tvValue = row.findViewById(R.id.tv_value);
        try {
            if (alTechHeading.get(i) != null) {
                tvValue.setText(alTechValues.get(i));
                tlTechnicalSpecs.addView(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initImagePreviewDialog() {
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_view_pager, null);
        Rect displayRectangle = new Rect();
        Window window = getActivity().getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        layout.setMinimumWidth((int) (displayRectangle.width() * 0.9f));
        layout.setMinimumHeight((int) (displayRectangle.height() * 0.9f));
        dialogViewPager = new Dialog(getActivity());
        dialogViewPager.setContentView(layout);
        dialogViewPager.setCancelable(true);

        //TextView tvHeading = dialogViewPager.findViewById(R.id.tv_readmore_heading);
        TextView tvClosePreview = dialogViewPager.findViewById(R.id.tv_close_dialog);
        ivBlurImage = dialogViewPager.findViewById(R.id.iv_blur_bg);
        ivBlurImage.setOnTouchListener(new ImageMatrixTouchHandler(getContext()));

        flParentViewPager = dialogViewPager.findViewById(R.id.fl_parent_viewpager);

        tvClosePreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePathPosition = mViewPagerSlideShow.getCurrentItem();
                Uri uri = Uri.fromFile(new File(alImagesPath.get(imagePathPosition)));
                ivMainImage.setTag(alImagesPath.get(imagePathPosition));
                ivMainImage.setImageURI(uri);
                dialogViewPager.dismiss();
            }
        });
        mViewPagerSlideShow = dialogViewPager.findViewById(R.id.viewpager_image_dialog);
        mViewPagerSlideShow.setOffscreenPageLimit(3);


        ivLeftArrow = dialogViewPager.findViewById(R.id.iv_dialog_left_arrow);
        ivRightArrow = dialogViewPager.findViewById(R.id.iv_dialog_right_arrow);

        ivLeftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPagerSlideShow.setCurrentItem(mViewPagerSlideShow.getCurrentItem() - 1);
            }
        });

        ivRightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPagerSlideShow.setCurrentItem(mViewPagerSlideShow.getCurrentItem() + 1);
            }
        });

        mViewPagerSlideShow.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                handleArrow(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        /*mViewPagerSlideShow.setPageTransformer(false, new ViewPager.PageTransformer()

        {
            @Override
            public void transformPage(View page, float position) {
                ZoomOutPageTransformer zoomOutPageTransformer = new ZoomOutPageTransformer();
                zoomOutPageTransformer.transformPage(page, position);
            }
        });*/
        if(!sProductName.equals("GN 680 TNM")) {
            alImagesPath = new ArrayList<>();
            alImagesPath.add(sImagePath);
        }
        final DialogImagePagerAdapter dialogImagePagerAdapter = new DialogImagePagerAdapter(getActivity(), alImagesPath);
        mViewPagerSlideShow.setAdapter(dialogImagePagerAdapter);
        /*String path = ivMainImage.getTag().toString();

        int imagePathPosition = alImagesPath.indexOf(path);
        mViewPagerSlideShow.setCurrentItem(imagePathPosition);
        handleArrow(imagePathPosition);*/

        DialogImagePagerAdapter.OnTouchListener onTouchListener = new DialogImagePagerAdapter.OnTouchListener() {
            @Override
            public void onTouch(boolean isZoom) {
                if (isZoom) {
                    setViewpagerSetting(true);
                } else {
                    setViewpagerSetting(false);
                }
            }
        };
        //takeScreenshot();
        /*Typeface lightFace = Typeface.createFromAsset(getResources().getAssets(), "fonts/myriad_pro_light.ttf");
        Typeface regularFace = Typeface.createFromAsset(getResources().getAssets(), "fonts/myriad_pro_regular.ttf");
        tvHeading.setTypeface(regularFace);*/
        //tvSubHeading.setTypeface(lightFace);
        //tvDescription.setTypeface(lightFace);
    }

    private void setViewpagerSetting(boolean isZoomed) {
        if (isZoomed) {
            flParentViewPager.setPadding(0, 0, 0, 0);
        } else {
            flParentViewPager.setPadding(padding, 0, padding, 0);
        }
    }

    private void handleArrow(int position) {
        if (position == 0 && mViewPagerSlideShow.getAdapter().getCount() == 1) {
            ivLeftArrow.setVisibility(View.GONE);
            ivRightArrow.setVisibility(View.GONE);
        } else if (position == 0 && mViewPagerSlideShow.getAdapter().getCount() > 1) {
            ivLeftArrow.setVisibility(View.GONE);
            ivRightArrow.setVisibility(View.VISIBLE);
        } else if (position == alImagesPath.size() - 1) {
            ivRightArrow.setVisibility(View.GONE);
            ivLeftArrow.setVisibility(View.VISIBLE);
        } else {
            ivLeftArrow.setVisibility(View.VISIBLE);
            ivRightArrow.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onFragmentCalled(int nCase, String sResult) {
        Constants constants = Constants.values()[nCase];
        switch (constants) {
            case IMAGE_CLICKED:
                //int pos = alImagesPath.indexOf(sResult);
                Uri uri = Uri.fromFile(new File(sResult));
                ivMainImage.setImageURI(uri);
                ivMainImage.setTag(sResult);
                imagePathPosition = alImagesPath.indexOf(sResult);
                break;
        }
    }

    @Override
    public void onActivityCalled(int nCase, String sResult) {

    }
}
