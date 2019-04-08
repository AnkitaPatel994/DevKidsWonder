package com.iteration.devkidswonder.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.iteration.devkidswonder.R;
import com.iteration.devkidswonder.activity.ProductDetailsActivity;
import com.iteration.devkidswonder.model.InsertRecentViewProp;
import com.iteration.devkidswonder.model.Product;
import com.iteration.devkidswonder.network.GetProductDataService;
import com.iteration.devkidswonder.network.RetrofitInstance;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecentviewListAdapter extends RecyclerView.Adapter<RecentviewListAdapter.ViewHolder> {

    Context context;
    ArrayList<Product> recentviewListArray;
    View v;
    String ipAddress;

    public RecentviewListAdapter(Context context, ArrayList<Product> recentviewListArray, String ipAddress) {
        this.context = context;
        this.recentviewListArray = recentviewListArray;
        this.ipAddress = ipAddress;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_list, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        String rs = context.getResources().getString(R.string.RS);
        final String id = recentviewListArray.get(position).getId();
        final String pro_id = recentviewListArray.get(position).getPro_id();
        final String cate_id = recentviewListArray.get(position).getCate_id();
        final String pro_title = recentviewListArray.get(position).getPro_title();
        final String brand_id = recentviewListArray.get(position).getBrand_id();
        final String brand_name = recentviewListArray.get(position).getBrand_name();
        final String pro_oprice = recentviewListArray.get(position).getPro_oprice();
        final String pro_discount = recentviewListArray.get(position).getPro_discount();
        final String pro_price = recentviewListArray.get(position).getPro_price();
        final String pro_desc = recentviewListArray.get(position).getPro_desc();
        final String pro_quantity = recentviewListArray.get(position).getPro_quantity();
        final String pro_date = recentviewListArray.get(position).getPro_date();
        final String statusid = recentviewListArray.get(position).getStatusid();
        final String rating = recentviewListArray.get(position).getRating();
        String product_img = recentviewListArray.get(position).getProduct_img();

        viewHolder.txtSubProductName.setText(pro_title);
        viewHolder.txtsubrating.setText(rating);
        viewHolder.txtsubprice.setText(rs+pro_price);
        viewHolder.txtsubcuttedprice.setText(rs+pro_oprice);
        viewHolder.txtsubcuttedprice.setPaintFlags(viewHolder.txtsubcuttedprice.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        viewHolder.txtsuboffer.setText(pro_discount);

        Picasso.with(context).load(RetrofitInstance.BASE_URL+product_img).into(viewHolder.ivsubProductImg);


        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GetProductDataService productDataService = RetrofitInstance.getRetrofitInstance().create(GetProductDataService.class);
                Call<InsertRecentViewProp> insertRecentViewPropCall = productDataService.getInsertRecentViewPropData(pro_id,ipAddress);
                insertRecentViewPropCall.enqueue(new Callback<InsertRecentViewProp>() {
                    @Override
                    public void onResponse(Call<InsertRecentViewProp> call, Response<InsertRecentViewProp> response) {
                        String Message = response.body().getMessage();
                        Log.d("Message",Message);
                    }

                    @Override
                    public void onFailure(Call<InsertRecentViewProp> call, Throwable t) {
                        Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                    }
                });

                Intent i = new Intent(context, ProductDetailsActivity.class);
                i.putExtra("id",id);
                i.putExtra("pro_id",pro_id);
                i.putExtra("cate_id",cate_id);
                i.putExtra("pro_title",pro_title);
                i.putExtra("brand_id",brand_id);
                i.putExtra("brand_name",brand_name);
                i.putExtra("pro_oprice",pro_oprice);
                i.putExtra("pro_discount",pro_discount);
                i.putExtra("pro_price",pro_price);
                i.putExtra("pro_desc",pro_desc);
                i.putExtra("pro_quantity",pro_quantity);
                i.putExtra("pro_date",pro_date);
                i.putExtra("statusid",statusid);
                i.putExtra("rating",rating);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivsubProductImg;
        TextView txtSubProductName,txtsubrating,txtsubprice,txtsubcuttedprice,txtsuboffer;

        public ViewHolder(View itemView) {
            super(itemView);

            ivsubProductImg = (ImageView)itemView.findViewById(R.id.ivsubProductImg);
            txtSubProductName = (TextView)itemView.findViewById(R.id.txtSubProductName);
            txtsubrating = (TextView)itemView.findViewById(R.id.txtsubrating);
            txtsubprice = (TextView)itemView.findViewById(R.id.txtsubprice);
            txtsubcuttedprice = (TextView)itemView.findViewById(R.id.txtsubcuttedprice);
            txtsuboffer = (TextView)itemView.findViewById(R.id.txtsuboffer);

        }
    }
}
