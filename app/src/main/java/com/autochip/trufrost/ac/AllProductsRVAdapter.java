package com.autochip.trufrost.ac;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

import app_utility.OnFragmentInteractionListener;

import static app_utility.StaticReferenceClass.OPEN_INDIVIDUAL_PRODUCT_FRAGMENT;

public class AllProductsRVAdapter extends RecyclerView.Adapter<AllProductsRVAdapter.MenuItemTabHolder> {

    private Context context;
    private RecyclerView recyclerView;
    private String sMainMenuName;
    private ArrayList<String> alProductNames;
    private ArrayList<String> alProductImagePath;
    private OnFragmentInteractionListener mListener;

    public AllProductsRVAdapter(Context context, RecyclerView recyclerView, ArrayList<String> alProductNames,
                                ArrayList<String> alProductImagePath, OnFragmentInteractionListener mListener) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.alProductNames = alProductNames;
        this.alProductImagePath = alProductImagePath;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public MenuItemTabHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_all_products_layout, parent, false);

        return new MenuItemTabHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MenuItemTabHolder holder, final int position) {

        holder.tvProductName.setText(alProductNames.get(position));

        if(alProductImagePath.size()>position) {
            Uri imageUri = Uri.fromFile(new File(alProductImagePath.get(position)));
            holder.ivProducts.setImageURI(imageUri);
        }

        holder.ivProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onActivityCalled(OPEN_INDIVIDUAL_PRODUCT_FRAGMENT, alProductNames.get(position));
                //HomeScreenActivity.onFragmentInteractionListener.onFragmentMessage("OPEN_PRODUCTS_FRAGMENT", position, "", holder.tvProductName.getText().toString());
            }
        });

        holder.tvProductName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onActivityCalled(OPEN_INDIVIDUAL_PRODUCT_FRAGMENT, alProductNames.get(position));
                //HomeScreenActivity.onFragmentInteractionListener.onFragmentMessage("OPEN_PRODUCTS_FRAGMENT", position, "", holder.tvProductName.getText().toString());
            }
        });

    }

    @Override
    public int getItemCount() {
        return alProductNames.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class MenuItemTabHolder extends RecyclerView.ViewHolder {
        //TextView tvNumber;
        ImageView ivProducts;
        TextView tvProductName;

        MenuItemTabHolder(View itemView) {
            super(itemView);
            ivProducts = itemView.findViewById(R.id.iv_product_image);
            tvProductName = itemView.findViewById(R.id.tv_product_name);
            //tvNumber = itemView.findViewById(R.id.tv_number);
        }
    }

}