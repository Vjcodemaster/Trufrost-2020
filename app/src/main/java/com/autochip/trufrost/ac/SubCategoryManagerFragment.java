package com.autochip.trufrost.ac;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import app_utility.Constants;
import app_utility.OnFragmentInteractionListener;

import static app_utility.StaticReferenceClass.OPEN_INDIVIDUAL_PRODUCT_FRAGMENT;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link app_utility.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SubCategoryManagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SubCategoryManagerFragment extends Fragment implements OnFragmentInteractionListener {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private OnFragmentInteractionListener onSubcategoryManagerListener;

    RecyclerView rvFirstCategory;
    RecyclerView rvSecondCategory;
    RecyclerView rvProducts;
    TextView tvHeading;
    String sSubHeading;
    //LinearLayout llIntro;
    //MaterialCardView mcvSecondRecyclerView;


    public SubCategoryManagerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SubCategoryManagerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SubCategoryManagerFragment newInstance(String param1, String param2) {
        SubCategoryManagerFragment fragment = new SubCategoryManagerFragment();
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
        //mListener = this;
        onSubcategoryManagerListener = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sub_category_manager, container, false);

        initViews(view);
        updateViews();
        //mListener.onActivityCalled(OPEN_FIRST_SUB_FRAGMENT, "");
        return view;
    }

    private void initViews(View view) {
        tvHeading = view.findViewById(R.id.tv_heading);
        rvFirstCategory = view.findViewById(R.id.rv_first_category);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvFirstCategory.setLayoutManager(mLinearLayoutManager);
        rvFirstCategory.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        rvFirstCategory.setHasFixedSize(true);

        rvSecondCategory = view.findViewById(R.id.rv_second_category);

        LinearLayoutManager mLinearLayoutManagerSecond = new GridLayoutManager(getActivity(), 2);
        mLinearLayoutManagerSecond.setOrientation(RecyclerView.VERTICAL);
        rvSecondCategory.setLayoutManager(mLinearLayoutManagerSecond);
        rvSecondCategory.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        rvSecondCategory.setHasFixedSize(true);

        //llIntro = view.findViewById(R.id.ll_intro);
        //mcvSecondRecyclerView = view.findViewById(R.id.mcv_second_category);

        /*rvProducts = view.findViewById(R.id.rv_products);
        LinearLayoutManager mLinearLayoutManagerProducts =new GridLayoutManager(getActivity(), 3);
        mLinearLayoutManagerProducts.setOrientation(RecyclerView.VERTICAL);
        rvSecondCategory.setLayoutManager(mLinearLayoutManagerProducts);
        rvSecondCategory.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        rvSecondCategory.setHasFixedSize(true);*/


        /*int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mLinearLayoutManager = new GridLayoutManager(getActivity(), 4);
            mLinearLayoutManager.setOrientation(RecyclerView.VERTICAL);
            // In landscape
        } else {
            mLinearLayoutManager = new GridLayoutManager(getActivity(), 3);
            mLinearLayoutManager.setOrientation(RecyclerView.VERTICAL);
            // In portrait
        }

        rvFirstCategory.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        rvFirstCategory.setHasFixedSize(true);
        rvFirstCategory.setLayoutManager(mLinearLayoutManager);*/


    }

    private void updateViews() {
        ArrayList<String> alFirstSCNames = new ArrayList<>();
        alFirstSCNames.add("SUPERMARKET REFRIGERATION");
        alFirstSCNames.add("CHEST FREEZERS & COOLERS");
        alFirstSCNames.add("COLD DISPENSERS");
        alFirstSCNames.add("COLD ROOMS");
        alFirstSCNames.add("BAKERY EQUIPMENT");
        alFirstSCNames.add("COMBI STEAMERS");

        SubCategoryFirstRVAdapter subCategoryFirstRVAdapter = new SubCategoryFirstRVAdapter(getActivity(), rvFirstCategory,
                alFirstSCNames, onSubcategoryManagerListener);
        rvFirstCategory.setAdapter(subCategoryFirstRVAdapter);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (HomeScreenActivity) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        onSubcategoryManagerListener = null;
    }

    @Override
    public void onFragmentCalled(int nCase, String sResult) {
        Constants constants = Constants.values()[nCase];
        switch (constants) {
            case UPDATE_SECOND_ADAPTER:
                ArrayList<String> alSecondSCNames = new ArrayList<>();
                alSecondSCNames.add("SUPERMARKET REFRIGERATION");
                alSecondSCNames.add("CHEST FREEZERS & COOLERS");
                alSecondSCNames.add("COLD DISPENSERS");
                alSecondSCNames.add("COLD ROOMS");
                alSecondSCNames.add("BAKERY EQUIPMENT");
                alSecondSCNames.add("COMBI STEAMERS");
                alSecondSCNames.add("COLD ROOMS");
                alSecondSCNames.add("BAKERY EQUIPMENT");
                alSecondSCNames.add("COMBI STEAMERS");

                SubCategorySecondRVAdapter subCategorySecondRVAdapter = new SubCategorySecondRVAdapter(getActivity(), rvSecondCategory,
                        alSecondSCNames, mListener);
                rvSecondCategory.setAdapter(subCategorySecondRVAdapter);

                //llIntro.setVisibility(View.GONE);
                //mcvSecondRecyclerView.setVisibility(View.VISIBLE);
                break;
            case UPDATE_PRODUCTS_ADAPTER:
                mListener.onActivityCalled(OPEN_INDIVIDUAL_PRODUCT_FRAGMENT, tvHeading.getText().toString() + ","
                        + sSubHeading + "," + sResult);
                /*ArrayList<String> alProductNames = new ArrayList<>();
                alProductNames.add("SUPERMARKET REFRIGERATION");
                alProductNames.add("CHEST FREEZERS & COOLERS");
                alProductNames.add("COLD DISPENSERS");
                alProductNames.add("COLD ROOMS");
                alProductNames.add("BAKERY EQUIPMENT");
                alProductNames.add("COMBI STEAMERS");
                alProductNames.add("COLD ROOMS");
                alProductNames.add("BAKERY EQUIPMENT");
                alProductNames.add("COMBI STEAMERS");

                ArrayList<String> alProductImages = new ArrayList<>();
                AllProductsRVAdapter productsRVAdapter = new AllProductsRVAdapter(getActivity(), rvProducts,
                        alProductNames,  alProductImages, mListener);
                rvProducts.setAdapter(productsRVAdapter);*/
                break;
        }
    }

    @Override
    public void onActivityCalled(int nCase, String sResult) {

    }
}
