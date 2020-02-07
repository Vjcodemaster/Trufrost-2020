package com.autochip.trufrost.ac;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

import app_utility.Constants;
import app_utility.DatabaseHandler;
import app_utility.DatabaseHelper;
import app_utility.OnFragmentInteractionListener;

import static app_utility.Constants.OPEN_INDIVIDUAL_PRODUCT_FRAGMENT;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link app_utility.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AllProductsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllProductsFragment extends Fragment implements OnFragmentInteractionListener{
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String sSubCategorySecond;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    RecyclerView rvAllProducts;

    DatabaseHandler dbHandler;


    ArrayList<String> alProductNames;
    ArrayList<String> alImagePath;
    ArrayList<String> alTechSpecKey;
    ArrayList<String> alTechSpecValue;
    ArrayList<String> alDescription;

    public AllProductsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AllProductsFragment.
     */
    public static AllProductsFragment newInstance(String param1, String param2) {
        AllProductsFragment fragment = new AllProductsFragment();
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
            sSubCategorySecond = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        dbHandler = new DatabaseHandler(getActivity());
        mListener = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_products, container, false);
        initViews(view);
        updateViews();
        return view;
    }

    private void initViews(View view){
        rvAllProducts = view.findViewById(R.id.rv_all_products);
        LinearLayoutManager mLinearLayoutManager;
        mLinearLayoutManager = new GridLayoutManager(getActivity(), 3);
        mLinearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rvAllProducts.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        rvAllProducts.setHasFixedSize(true);
        rvAllProducts.setLayoutManager(mLinearLayoutManager);
    }


    private void updateViews(){
        ArrayList<DatabaseHelper> alDBHelper = new ArrayList<>(dbHandler.getProductsFromSCTwo(sSubCategorySecond));
        alProductNames = new ArrayList<>();
         alImagePath = new ArrayList<>();
        alTechSpecKey = new ArrayList<>();
        alTechSpecValue = new ArrayList<>();
         alDescription = new ArrayList<>();

        for (int i=0; i<alDBHelper.size(); i++){
            alProductNames.add(alDBHelper.get(i).get_product_name());
            alImagePath.add(alDBHelper.get(i).get_product_image_path());
            alTechSpecKey.add(alDBHelper.get(i).get_product_tech_specs());
            alTechSpecValue.add(alDBHelper.get(i).get_product_tech_specs_value());
            alDescription.add(alDBHelper.get(i).get_product_description());
        }
        /*ArrayList<String> alProductNames = new ArrayList<>();
        ArrayList<String> alImagePath = new ArrayList<>();*/

        /*alProductNames.add("SUPERMARKET REFRIGERATION");
        alProductNames.add("CHEST FREEZERS & COOLERS");
        alProductNames.add("COLD DISPENSERS");
        alProductNames.add("COLD ROOMS");
        alProductNames.add("BAKERY EQUIPMENT");
        alProductNames.add("COMBI STEAMERS");
        alProductNames.add("COLD ROOMS");
        alProductNames.add("BAKERY EQUIPMENT");
        alProductNames.add("COMBI STEAMERS");*/

        AllProductsRVAdapter allProductsRVAdapter = new AllProductsRVAdapter(getActivity(), rvAllProducts,
                alProductNames, alImagePath, alTechSpecKey, alTechSpecValue, alDescription, mListener);
        rvAllProducts.setAdapter(allProductsRVAdapter);
    }

    @Override
    public void onAttach(@NonNull Context context) {
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
            case OPEN_INDIVIDUAL_PRODUCT_FRAGMENT:
                int pos =  alProductNames.indexOf(sResult);
                Bundle bundle = new Bundle();
                bundle.putString("product_name", sResult);
                bundle.putString("image_path", alImagePath.get(pos));
                bundle.putString("tech_spec_key", alTechSpecKey.get(pos));
                bundle.putString("tech_spec_value", alTechSpecValue.get(pos));
                bundle.putString("description", alDescription.get(pos));

                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment individualProductFragment = IndividualProductFragment.newInstance(sResult, "");
                individualProductFragment.setArguments(bundle);
                ft.add(R.id.fl_container, individualProductFragment);
                ft.addToBackStack(null);
                ft.commit();
                break;
        }

    }

    @Override
    public void onActivityCalled(int nCase, String sResult) {

    }
}
