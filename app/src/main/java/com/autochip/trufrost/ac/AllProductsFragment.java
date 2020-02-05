package com.autochip.trufrost.ac;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

import app_utility.OnFragmentInteractionListener;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link app_utility.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AllProductsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AllProductsFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    RecyclerView rvAllProducts;

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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
        ArrayList<String> alProductNames = new ArrayList<>();
        ArrayList<String> alImagePath = new ArrayList<>();

        alProductNames.add("SUPERMARKET REFRIGERATION");
        alProductNames.add("CHEST FREEZERS & COOLERS");
        alProductNames.add("COLD DISPENSERS");
        alProductNames.add("COLD ROOMS");
        alProductNames.add("BAKERY EQUIPMENT");
        alProductNames.add("COMBI STEAMERS");
        alProductNames.add("COLD ROOMS");
        alProductNames.add("BAKERY EQUIPMENT");
        alProductNames.add("COMBI STEAMERS");

        AllProductsRVAdapter allProductsRVAdapter = new AllProductsRVAdapter(getActivity(), rvAllProducts,
                alProductNames, alImagePath, mListener);
        rvAllProducts.setAdapter(allProductsRVAdapter);
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
    }
}
