package com.iterationtechnology.devkidswonder.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.iterationtechnology.devkidswonder.R;
import com.iterationtechnology.devkidswonder.activity.RatingViewActivity;
import com.iterationtechnology.devkidswonder.activity.SubCategoryActivity;
import com.iterationtechnology.devkidswonder.model.Category;
import com.iterationtechnology.devkidswonder.model.Rating;
import com.iterationtechnology.devkidswonder.network.RetrofitInstance;
import com.jackandphantom.circularimageview.RoundedImage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class RatingListAdapter extends RecyclerView.Adapter<RatingListAdapter.ViewHolder> {

    Context context;
    ArrayList<Rating> ratingListArray;

    public RatingListAdapter(Context context, ArrayList<Rating> ratingListArray) {
        this.context = context;
        this.ratingListArray = ratingListArray;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rating_list, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {

        String rating_id = ratingListArray.get(position).getRating_id();
        String customer_first_name = ratingListArray.get(position).getCustomer_name();
        String customer_last_name = ratingListArray.get(position).getCustomer_last_name();
        String rating = ratingListArray.get(position).getRating();
        String review = ratingListArray.get(position).getReview();

        viewHolder.txtRatingName.setText(customer_first_name+" "+customer_last_name);
        viewHolder.txtReviewName.setText(review);
        viewHolder.txtRating.setText(rating);
        viewHolder.rbRRating.setRating(Float.parseFloat(rating));

    }

    @Override
    public int getItemCount() {
        return ratingListArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtRatingName,txtReviewName,txtRating;
        RatingBar rbRRating;

        public ViewHolder(View itemView) {
            super(itemView);

            txtRatingName = (TextView) itemView.findViewById(R.id.txtRatingName);
            txtReviewName = (TextView) itemView.findViewById(R.id.txtReviewName);
            txtRating = (TextView) itemView.findViewById(R.id.txtRating);
            rbRRating = (RatingBar) itemView.findViewById(R.id.rbRRating);

        }
    }
}
