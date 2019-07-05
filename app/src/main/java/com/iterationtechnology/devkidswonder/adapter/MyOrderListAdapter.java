package com.iterationtechnology.devkidswonder.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iterationtechnology.devkidswonder.R;
import com.iterationtechnology.devkidswonder.activity.OrderDeliveredActivity;
import com.iterationtechnology.devkidswonder.activity.OrderPlacedActivity;
import com.iterationtechnology.devkidswonder.activity.OrderShippedActivity;
import com.iterationtechnology.devkidswonder.model.Order;
import com.iterationtechnology.devkidswonder.network.RetrofitInstance;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyOrderListAdapter extends RecyclerView.Adapter<MyOrderListAdapter.ViewHolder>  {

    Context context;
    ArrayList<Order> myOrderProductListArray;
    String user_id;

    public MyOrderListAdapter(Context context, ArrayList<Order> myOrderProductListArray, String user_id) {
        this.context = context;
        this.user_id = user_id;
        this.myOrderProductListArray = myOrderProductListArray;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.order_pro_list, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        String rs = context.getResources().getString(R.string.RS);
        final String order_id = myOrderProductListArray.get(position).getOrder_id();
        final String order_pro_id = myOrderProductListArray.get(position).getOrder_pro_id();
        String pro_title = myOrderProductListArray.get(position).getPro_title();
        String order_qty = myOrderProductListArray.get(position).getOrder_qty();
        String order_size = myOrderProductListArray.get(position).getOrder_size();
        String pro_oprice = myOrderProductListArray.get(position).getPro_oprice();
        String pro_discount = myOrderProductListArray.get(position).getPro_discount();
        String pro_price = myOrderProductListArray.get(position).getPro_price();
        String product_img = myOrderProductListArray.get(position).getProduct_img();
        final String order_status = myOrderProductListArray.get(position).getOrder_status();

        viewHolder.txtOTitle.setText(pro_title);
        viewHolder.txtOCartQty.setText(order_qty);
        viewHolder.txtOSizeCart.setText(order_size);
        viewHolder.txtOProductPrice.setText(rs+pro_price);
        viewHolder.txtOCuttedPrice.setText(rs+pro_oprice);
        viewHolder.txtOProductOff.setText(pro_discount+"%off");

        viewHolder.txtOProductStatus.setVisibility(View.VISIBLE);
        viewHolder.txtOProductStatus.setText(order_status);

        viewHolder.txtOCuttedPrice.setPaintFlags(viewHolder.txtOCuttedPrice.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);

        if (order_status.equals("Delivered"))
        {
            viewHolder.btnTrackOrder.setVisibility(View.GONE);
        }

        Picasso.with(context).load(RetrofitInstance.BASE_URL+product_img).into(viewHolder.ivProImg);

        viewHolder.btnTrackOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*i.putExtra("item","sinItem");*/
                if (order_status.equals("Pending"))
                {
                    Intent i = new Intent(context, OrderPlacedActivity.class);
                    i.putExtra("order_id",order_id);
                    i.putExtra("item","sinItem");
                    i.putExtra("ordersta","Your Order has been Placed Sucessfully");
                    context.startActivity(i);
                }
                else if (order_status.equals("Confirmed"))
                {
                    Intent i = new Intent(context, OrderPlacedActivity.class);
                    i.putExtra("order_id",order_id);
                    i.putExtra("item","sinItem");
                    i.putExtra("ordersta","Your Order has been Confirmed, if will Ready to Ship");
                    context.startActivity(i);
                }
                else if (order_status.equals("Shipped"))
                {
                    Intent i = new Intent(context, OrderShippedActivity.class);
                    i.putExtra("order_id",order_id);
                    context.startActivity(i);
                }
                else if (order_status.equals("Delivered"))
                {
                    Intent i = new Intent(context, OrderDeliveredActivity.class);
                    i.putExtra("order_id",order_id);
                    context.startActivity(i);
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return myOrderProductListArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProImg;
        Button btnTrackOrder;
        TextView txtOTitle,txtOSizeCart,txtOCartQty,txtOProductPrice,txtOCuttedPrice,txtOProductOff,txtOProductStatus;
        LinearLayout llOrderProList;
        public ViewHolder(View itemView) {
            super(itemView);

            ivProImg = (ImageView)itemView.findViewById(R.id.ivProImg);
            txtOTitle = (TextView) itemView.findViewById(R.id.txtOTitle);
            txtOSizeCart = (TextView) itemView.findViewById(R.id.txtOSizeCart);
            txtOCartQty = (TextView) itemView.findViewById(R.id.txtOCartQty);
            txtOProductPrice = (TextView) itemView.findViewById(R.id.txtOProductPrice);
            txtOCuttedPrice = (TextView) itemView.findViewById(R.id.txtOCuttedPrice);
            txtOProductOff = (TextView) itemView.findViewById(R.id.txtOProductOff);
            txtOProductStatus = (TextView) itemView.findViewById(R.id.txtOProductStatus);
            btnTrackOrder = (Button) itemView.findViewById(R.id.btnTrackOrder);
            llOrderProList = (LinearLayout) itemView.findViewById(R.id.llOrderProList);

        }
    }
}
