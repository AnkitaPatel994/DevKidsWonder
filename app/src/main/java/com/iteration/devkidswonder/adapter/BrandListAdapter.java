package com.iteration.devkidswonder.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.iteration.devkidswonder.R;
import com.iteration.devkidswonder.activity.HomeActivity;
import com.iteration.devkidswonder.activity.SubCategoryActivity;
import com.iteration.devkidswonder.model.Brand;
import com.iteration.devkidswonder.model.Category;
import com.iteration.devkidswonder.network.RetrofitInstance;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class BrandListAdapter extends RecyclerView.Adapter<BrandListAdapter.ViewHolder> {

    Context context;
    ArrayList<Brand> brandListArray;
    View v;

    public BrandListAdapter(Context context, ArrayList<Brand> brandListArray) {
        this.context = context;
        this.brandListArray = brandListArray;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.brand_list, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        final String Brand_id = brandListArray.get(position).getBrand_id();
        final String Brand_name = brandListArray.get(position).getBrand_name();
        String Brand_img = brandListArray.get(position).getBrand_img();

        viewHolder.txtBrandName.setText(Brand_name);

        Picasso.with(context).load(RetrofitInstance.BASE_URL +Brand_img).into(viewHolder.ivBrandImg);


        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, SubCategoryActivity.class);
                i.putExtra("cate_id","*");
                i.putExtra("cate_name","*");
                i.putExtra("brand_id",Brand_id);
                i.putExtra("brand_name",Brand_name);
                i.putExtra("min_price","1");
                i.putExtra("max_price","15000");
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivBrandImg;
        TextView txtBrandName;

        public ViewHolder(View itemView) {
            super(itemView);

            ivBrandImg = (ImageView)itemView.findViewById(R.id.ivBrandImg);
            txtBrandName = (TextView)itemView.findViewById(R.id.txtBrandName);
        }
    }
}
