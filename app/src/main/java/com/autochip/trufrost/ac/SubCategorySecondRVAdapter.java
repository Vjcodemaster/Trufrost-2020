package com.autochip.trufrost.ac;

import android.animation.Animator;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
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
                showCircularEffect(recyclerView, holder.tvProductName);
                //mListener.onFragmentCalled(UPDATE_PRODUCTS_ADAPTER, holder.tvProductName.getText().toString());
            }
        });

    }

    private void showCircularEffect(final View mParentView, View actualView) {
        // get the center for the clipping circle
        //int cx = (actualView.getLeft() + actualView.getRight()) / 2;
        //int cy = (actualView.getTop() + actualView.getBottom()) / 2;

        int cx = actualView.getWidth() / 2;
        int cy = actualView.getHeight() / 2;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // get the final radius for the clipping circle
            int finalRadius = Math.max(mParentView.getWidth(), mParentView.getHeight());
            float initialRadius = (float) Math.hypot(cx, cy);

            //create the animator for this view (the start radius is zero)
            Animator anim;
            anim = ViewAnimationUtils.createCircularReveal(mParentView, cx, cy,
                    initialRadius, finalRadius);
            anim.setDuration(300);
            mParentView.setVisibility(View.VISIBLE);
            anim.start();
        }
    }

    /*private void showCircularEffect(final View mParentView, View actualView) {
        // get the center for the clipping circle
        //int cx = (actualView.getLeft() + actualView.getRight()) / 2;
        //int cy = (actualView.getTop() + actualView.getBottom()) / 2;

        //int cx = actualView.getWidth() / 2;
        //int cy = actualView.getHeight() / 2;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // get the final radius for the clipping circle
            int cx = actualView.getWidth() / 2;
            int cy = actualView.getHeight() / 2;

            // get the initial radius for the clipping circle
            float initialRadius = (float) Math.hypot(cx, cy);
            int finalRadius = Math.max(mParentView.getWidth(), mParentView.getHeight());

            // create the animation (the final radius is zero)
            Animator anim = ViewAnimationUtils.createCircularReveal(actualView, cx, cy, initialRadius, finalRadius);

            // start the animation
            anim.setDuration(350);
            anim.start();
        }
    }*/

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