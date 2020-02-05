package com.autochip.trufrost.ac;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import app_utility.OnFragmentInteractionListener;

import static app_utility.StaticReferenceClass.OPEN_ALL_PRODUCTS;
import static app_utility.StaticReferenceClass.UPDATE_PRODUCTS_ADAPTER;

public class SubCategorySecondRVAdapter extends RecyclerView.Adapter<SubCategorySecondRVAdapter.MenuItemTabHolder> {

    private Context context;
    private RecyclerView recyclerView;
    private String sMainMenuName;
    ArrayList<String> alSubCategoryNames;
    private OnFragmentInteractionListener mListener;
    TextView tvPrevious;

    public SubCategorySecondRVAdapter(Context context, RecyclerView recyclerView, ArrayList<String> alSubCategoryNames,
                                      OnFragmentInteractionListener mListener) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.alSubCategoryNames = alSubCategoryNames;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public MenuItemTabHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_second_category_layout, parent, false);

        return new MenuItemTabHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MenuItemTabHolder holder, final int position) {

        holder.tvProductName.setText(alSubCategoryNames.get(position));


        holder.tvProductName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.tvProductName.setBackgroundColor(context.getResources().getColor(android.R.color.white));
                holder.tvProductName.setTextColor(context.getResources().getColor(R.color.colorSkyBlue));
                if(tvPrevious!=null && tvPrevious!=holder.tvProductName) {
                    tvPrevious.setBackgroundColor(context.getResources().getColor(R.color.colorSkyBlue));
                    tvPrevious.setTextColor(context.getResources().getColor(android.R.color.white));
                }
                mListener.onActivityCalled(OPEN_ALL_PRODUCTS, holder.tvProductName.getText().toString());
                tvPrevious = holder.tvProductName;
                //mListener.onFragmentCalled(UPDATE_PRODUCTS_ADAPTER, holder.tvProductName.getText().toString());
            }
        });

    }

    @Override
    public int getItemCount() {
        return alSubCategoryNames.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class MenuItemTabHolder extends RecyclerView.ViewHolder {
        //TextView tvNumber;
        //ImageView ivProducts;
        TextView tvProductName;

        MenuItemTabHolder(View itemView) {
            super(itemView);
            //ivProducts = itemView.findViewById(R.id.iv_products);
            tvProductName = itemView.findViewById(R.id.tv_product_name);
            //tvNumber = itemView.findViewById(R.id.tv_number);
        }
    }
}