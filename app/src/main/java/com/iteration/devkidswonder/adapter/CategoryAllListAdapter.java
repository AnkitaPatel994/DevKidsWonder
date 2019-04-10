package com.iteration.devkidswonder.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iteration.devkidswonder.R;
import com.iteration.devkidswonder.activity.SubCategoryActivity;
import com.iteration.devkidswonder.model.Category;
import com.iteration.devkidswonder.network.RetrofitInstance;
import com.jackandphantom.circularimageview.RoundedImage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryAllListAdapter extends RecyclerView.Adapter<CategoryAllListAdapter.ViewHolder> {

    Context context;
    ArrayList<Category> categoryListArray;
    View v;

    public CategoryAllListAdapter(Context context, ArrayList<Category> categoryListArray) {
        this.context = context;
        this.categoryListArray = categoryListArray;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_all_list, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        final String category_id = categoryListArray.get(position).getCategory_id();
        String category_title = categoryListArray.get(position).getCategory_title();
        String category_img = categoryListArray.get(position).getCategory_img();

        viewHolder.txtAllCatName.setText(category_title);

        if ((position % 2) == 0)
        {
            viewHolder.llCatListBg.setBackground(ContextCompat.getDrawable(context,R.color.colorPEACH));
        }
        else
        {
            viewHolder.llCatListBg.setBackground(ContextCompat.getDrawable(context,R.color.colorllBg));
        }

        Picasso.with(context).load(RetrofitInstance.BASE_URL +category_img).into(viewHolder.ivAllCatImg);


        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, SubCategoryActivity.class);
                i.putExtra("cate_id",category_id);
                i.putExtra("brand_id","*");
                i.putExtra("min_price","*");
                i.putExtra("max_price","*");
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryListArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RoundedImage ivAllCatImg;
        TextView txtAllCatName;
        LinearLayout llCatListBg;

        public ViewHolder(View itemView) {
            super(itemView);

            llCatListBg = (LinearLayout)itemView.findViewById(R.id.llCatListBg);
            ivAllCatImg = (RoundedImage)itemView.findViewById(R.id.ivAllCatImg);
            txtAllCatName = (TextView)itemView.findViewById(R.id.txtAllCatName);
        }
    }
}
