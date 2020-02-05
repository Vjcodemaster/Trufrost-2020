package com.autochip.trufrost.ac;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import app_utility.OnFragmentInteractionListener;

import static app_utility.StaticReferenceClass.OPEN_FRAGMENT_MANAGER;

public class MainCategoryRVAdapter extends RecyclerView.Adapter<MainCategoryRVAdapter.MenuItemTabHolder> {

    private RecyclerView recyclerView;
    private OnFragmentInteractionListener mListener;
    private String sMainMenuName;

    public MainCategoryRVAdapter(RecyclerView recyclerView, OnFragmentInteractionListener mListener) {
        this.recyclerView = recyclerView;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public MenuItemTabHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_main_category, parent, false);

        return new MenuItemTabHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MenuItemTabHolder holder, final int position) {

        switch (position) {
            case 0:
                sMainMenuName = "Commercial Kitchen";
                holder.tvProductName.setText(sMainMenuName);
                holder.ivProductsImage.setImageResource(R.drawable.commercial_kitchen);
                break;
            case 1:
                sMainMenuName = "Bar & Pubs";
                holder.ivProductsImage.setImageResource(R.drawable.bars_pubs);
                holder.tvProductName.setText(sMainMenuName);
                break;
            case 2:
                sMainMenuName = "Cake & Sweet shops";
                holder.ivProductsImage.setImageResource(R.drawable.cake_sweet);
                holder.tvProductName.setText(sMainMenuName);
                break;
            case 3:
                sMainMenuName = "Food Retail";
                holder.ivProductsImage.setImageResource(R.drawable.food_retail);
                holder.tvProductName.setText(sMainMenuName);
                break;
            case 4:
                sMainMenuName = "Food Preservation";
                holder.ivProductsImage.setImageResource(R.drawable.food_preservation);
                holder.tvProductName.setText(sMainMenuName);
                break;
            case 5:
                sMainMenuName = "Bio Medical";
                holder.ivProductsImage.setImageResource(R.drawable.bio_medical);
                holder.tvProductName.setText(sMainMenuName);
                break;
        }

        holder.ivProductsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position){
                    case 0:
                        sMainMenuName = "Commercial Kitchen";//
                        break;
                    case 1:
                        sMainMenuName = "Bar & Pubs";
                        break;
                    case 2:
                        sMainMenuName = "Cake & Sweet shops";
                        break;
                    case 3:
                        sMainMenuName = "Food Retail";
                        break;
                    case 4:
                        sMainMenuName = "Food Preservation";//
                        break;
                    case 5:
                        sMainMenuName = "Bio Medical";
                        break;
                }
                mListener.onActivityCalled(OPEN_FRAGMENT_MANAGER, sMainMenuName);
                //HomeScreenActivity.onFragmentInteractionListener.onFragmentMessage("OPEN_SUB_CATEGORY_FRAGMENT", position, "", sMainMenuName);
                //HomeScreenActivity.onFragmentInteractionListener.onFragmentMessage("OPEN_FIRST_SUB_CATEGORY_FRAGMENT", position, "", sMainMenuName);

            }
        });

    }


    @Override
    public int getItemCount() {
        return 6; //alBeaconInfo.size()
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class MenuItemTabHolder extends RecyclerView.ViewHolder {
        ImageView ivProductsImage;
        TextView tvProductName;

        MenuItemTabHolder(View itemView) {
            super(itemView);
            ivProductsImage = itemView.findViewById(R.id.iv_product_image);
            tvProductName = itemView.findViewById(R.id.tv_product_name);
        }
    }

}