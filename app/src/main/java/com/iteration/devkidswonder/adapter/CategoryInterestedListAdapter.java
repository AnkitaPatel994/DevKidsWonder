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
import com.iteration.devkidswonder.activity.SubCategoryActivity;
import com.iteration.devkidswonder.model.Category;
import com.iteration.devkidswonder.network.RetrofitInstance;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryInterestedListAdapter extends RecyclerView.Adapter<CategoryInterestedListAdapter.ViewHolder> {

    Context context;
    ArrayList<Category> categoryListArray;
    View v;

    public CategoryInterestedListAdapter(Context context, ArrayList<Category> categoryListArray) {
        this.context = context;
        this.categoryListArray = categoryListArray;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.int_category_list, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        final String category_id = categoryListArray.get(position).getCategory_id();
        String category_title = categoryListArray.get(position).getCategory_title();
        String category_img = categoryListArray.get(position).getCategory_img();

        viewHolder.txtcpProductCatName.setText(category_title);

        Picasso.with(context).load(RetrofitInstance.BASE_URL +category_img).into(viewHolder.ivcpProductImg);


        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, SubCategoryActivity.class);
                i.putExtra("category_id",category_id);
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

        public ViewHolder(View itemView) {
            super(itemView);

            ivcpProductImg = (ImageView) itemView.findViewById(R.id.ivcpProductImg);
            txtcpProductCatName = (TextView)itemView.findViewById(R.id.txtcpProductCatName);
        }
    }
}
