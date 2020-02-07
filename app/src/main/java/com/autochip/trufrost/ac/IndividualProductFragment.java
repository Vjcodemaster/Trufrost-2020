package com.autochip.trufrost.ac;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import app_utility.OnFragmentInteractionListener;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link app_utility.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link IndividualProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IndividualProductFragment extends Fragment {
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
    ArrayList<String> alTechValues ;


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
        if(bundle!=null){
            sProductName  = bundle.getString("product_name");
            sImagePath  = bundle.getString("image_path");
            sTechSpecKey  = bundle.getString("tech_spec_key");
            sTechSpecValue  = bundle.getString("tech_spec_value");
            sDescription  = bundle.getString("description");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_individual_product, container, false);

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
                /*initImagePreviewDialog();
                dialogViewPager.show();*/
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
            mLinearLayoutManager = new GridLayoutManager(getActivity(), 5);
            mLinearLayoutManager.setOrientation(RecyclerView.VERTICAL);
            // In landscape
        } else {
            mLinearLayoutManager = new GridLayoutManager(getActivity(), 3);
            mLinearLayoutManager.setOrientation(RecyclerView.VERTICAL);
            // In portrait
        }
        /*LinearLayoutManager mLinearLayoutManager = new GridLayoutManager(getActivity(), 4);
        mLinearLayoutManager.setOrientation(RecyclerView.VERTICAL);*/

        recyclerViewImages.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        recyclerViewImages.setHasFixedSize(true);
        recyclerViewImages.setLayoutManager(mLinearLayoutManager);
        //recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL));
        //
        // saImagePath = dbh.getImagePathFromProducts(mParam3).split(",");
       //alImagesPath = new ArrayList<>(Arrays.asList(dbh.getImagePathFromProducts(mParam3).split(",")));
        Uri uri = Uri.fromFile(new File(sImagePath));

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(new File(uri.getPath()).getAbsolutePath(), options);
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;
        int height;
        int width;
        if(imageHeight>imageWidth){
            height = (int) (250f);
            width = (int) (210f);
        } else {
            height = (int) (150f);
            width = (int) (250f);
        }
        ViewGroup.LayoutParams params = ivMainImage.getLayoutParams();
        params.height = height;
        params.width = width;

        ivMainImage.setLayoutParams(params);

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
        params.gravity = Gravity.CENTER;

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

}
