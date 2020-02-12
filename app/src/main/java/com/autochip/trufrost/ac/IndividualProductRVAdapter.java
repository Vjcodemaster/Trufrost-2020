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

import static app_utility.StaticReferenceClass.IMAGE_CLICKED;

public class IndividualProductRVAdapter extends RecyclerView.Adapter<IndividualProductRVAdapter.ProductItemTabHolder> {

    private Context context;
    private RecyclerView recyclerView;
    private OnFragmentInteractionListener mListener;
    //ArrayList<DataBaseHelper> alDb;
    //DatabaseHandler dbh;

    ArrayList<String> alImagesPath;

    public IndividualProductRVAdapter(Context context, RecyclerView recyclerView, ArrayList<String> alImagesPath,
                                      OnFragmentInteractionListener mListener) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.alImagesPath = alImagesPath;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ProductItemTabHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_products_grid_layout, parent, false);

        return new ProductItemTabHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductItemTabHolder holder, final int position) {

        holder.tvProductName.setVisibility(View.GONE);

        Uri uri = Uri.fromFile(new File(alImagesPath.get(position)));


        holder.ivProducts.setImageURI(uri);

        holder.ivProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onFragmentCalled(IMAGE_CLICKED ,alImagesPath.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return alImagesPath.size(); //alBeaconInfo.size()
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ProductItemTabHolder extends RecyclerView.ViewHolder {
        //TextView tvNumber;
        ImageView ivProducts;
        TextView tvProductName;

        ProductItemTabHolder(View itemView) {
            super(itemView);
            ivProducts = itemView.findViewById(R.id.iv_products);
            tvProductName = itemView.findViewById(R.id.tv_product_name);
            //tvNumber = itemView.findViewById(R.id.tv_number);
        }
    }

}