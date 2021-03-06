package com.iterationtechnology.devkidswonder.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iterationtechnology.devkidswonder.R;
import com.iterationtechnology.devkidswonder.activity.SubCategoryActivity;
import com.iterationtechnology.devkidswonder.model.Category;
import com.iterationtechnology.devkidswonder.network.RetrofitInstance;

import java.util.ArrayList;

public class CategoryInterestedListAdapter extends RecyclerView.Adapter<CategoryInterestedListAdapter.ViewHolder> {

    Context context;
    ArrayList<Category> categoryListArray;

    public CategoryInterestedListAdapter(Context context, ArrayList<Category> categoryListArray) {
        this.context = context;
        this.categoryListArray = categoryListArray;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.int_category_list, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        final String category_id = categoryListArray.get(position).getCategory_id();
        final String category_title = categoryListArray.get(position).getCategory_title();
        String category_img = categoryListArray.get(position).getCategory_img();

        viewHolder.txtcpProductCatName.setText(category_title);

        Glide.with(context).load(RetrofitInstance.BASE_URL +category_img).into(viewHolder.ivcpProductImg);


        viewHolder.llIntCategoryList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, SubCategoryActivity.class);
                i.putExtra("pro_name","*");
                i.putExtra("cate_id",category_id);
                i.putExtra("cate_name",category_title);
                i.putExtra("brand_id","*");
                i.putExtra("brand_name","*");
                i.putExtra("min_price","1");
                i.putExtra("max_price","15000");
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivcpProductImg;
        TextView txtcpProductCatName;
        LinearLayout llIntCategoryList;

        public ViewHolder(View itemView) {
            super(itemView);

            ivcpProductImg = (ImageView) itemView.findViewById(R.id.ivcpProductImg);
            txtcpProductCatName = (TextView)itemView.findViewById(R.id.txtcpProductCatName);
            llIntCategoryList = (LinearLayout) itemView.findViewById(R.id.llIntCategoryList);
        }
    }
}
