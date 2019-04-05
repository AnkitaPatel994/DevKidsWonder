package com.iteration.devkidswonder.activity;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.Formatter;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iteration.devkidswonder.R;
import com.iteration.devkidswonder.model.ProductImg;
import com.iteration.devkidswonder.model.ProductImgList;
import com.iteration.devkidswonder.network.GetProductDataService;
import com.iteration.devkidswonder.network.Pager;
import com.iteration.devkidswonder.network.RetrofitInstance;
import com.iteration.devkidswonder.network.SessionManager;

import java.net.CookieHandler;
import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailsActivity extends AppCompatActivity {

    LinearLayout llPDWishlist,llPDPSizeChart,llPDPSize;
    RecyclerView rvPDAllView,rvPDInterestedProductList,rvPDRecentView,rvPDProductSize;
    String cate_id,ipAddress,pro_id,rs,user_id;
    ViewPager vpPagerImgSlider;
    TabLayout tabIndicator;
    TextView txtCuttedPrice,txtProductOffers,txtPDPSize,txtPDStatusId,txtError;
    BottomSheetDialog bottomSheetDialog;
    int size;
    SessionManager session;
    int flag = 0;
    ImageView ivPdWishBlack,ivPdWishRed;
    ArrayList<ProductImg> ProductImgArray = new ArrayList<>();
    ArrayList<String> ProductImgIdArray = new ArrayList<>();
    public static ArrayList<String> ProductImgNameArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        if(getSupportActionBar()!= null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        session = new SessionManager(ProductDetailsActivity.this);
        flag = session.checkLogin();

        HashMap<String,String> user = session.getUserDetails();
        user_id = user.get(SessionManager.user_id);

        @SuppressLint("WifiManagerLeak")
        WifiManager wifiManager = (WifiManager)getSystemService(WIFI_SERVICE);
        ipAddress = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());

        rs = ProductDetailsActivity.this.getResources().getString(R.string.RS);
        String id = getIntent().getExtras().getString("id");
        pro_id = getIntent().getExtras().getString("pro_id");
        cate_id = getIntent().getExtras().getString("cate_id");
        String pro_title = getIntent().getExtras().getString("pro_title");
        String brand_id = getIntent().getExtras().getString("brand_id");
        String brand_name = getIntent().getExtras().getString("brand_name");
        String pro_oprice = getIntent().getExtras().getString("pro_oprice");
        String pro_discount = getIntent().getExtras().getString("pro_discount");
        String pro_price = getIntent().getExtras().getString("pro_price");
        String pro_desc = getIntent().getExtras().getString("pro_desc");
        String pro_quantity = getIntent().getExtras().getString("pro_quantity");
        String pro_date = getIntent().getExtras().getString("pro_date");
        final String statusid = getIntent().getExtras().getString("statusid");
        String rating = getIntent().getExtras().getString("rating");

        vpPagerImgSlider = (ViewPager)findViewById(R.id.vpPagerImgSlider);

        tabIndicator = (TabLayout)findViewById(R.id.tabIndicator);
        tabIndicator.setupWithViewPager(vpPagerImgSlider);

        GetProductDataService productDataService = RetrofitInstance.getRetrofitInstance().create(GetProductDataService.class);

        Call<ProductImgList> ProductImgListCall = productDataService.getProductImgListData(pro_id);
        ProductImgListCall.enqueue(new Callback<ProductImgList>() {
            @Override
            public void onResponse(Call<ProductImgList> call, Response<ProductImgList> response) {
                ProductImgArray = response.body().getProductImgArrayList();
                /*for (int i=0;i<ProductImgArray.size();i++)
                {
                    String ProductImgId = ProductImgArray.get(i).getImg_id();
                    ProductImgIdArray.add(ProductImgId);
                    String ProductImgName = ProductImgArray.get(i).getPro_img_name();
                    ProductImgNameArray.add(ProductImgName);
                }

                Pager adapter = new Pager(getSupportFragmentManager());
                for (int i = 0; i < ProductImgIdArray.size(); i++) {
                    adapter.addFrag(new ImgSliderFragment(), ProductImgIdArray.get(i).trim());
                }
                vpPagerImgSlider.setAdapter(adapter);*/

            }

            @Override
            public void onFailure(Call<ProductImgList> call, Throwable t) {
                Toast.makeText(ProductDetailsActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });

        TextView txtPDBrandName = (TextView)findViewById(R.id.txtPDBrandName);
        TextView txtPDProductName = (TextView)findViewById(R.id.txtPDProductName);
        TextView txtPDProductSubTitle = (TextView)findViewById(R.id.txtPDProductSubTitle);
        txtError = (TextView)findViewById(R.id.txtError);
        txtPDStatusId = (TextView)findViewById(R.id.txtPDStatusId);
        final TextView txtProductPrice = (TextView)findViewById(R.id.txtProductPrice);
        txtCuttedPrice = (TextView)findViewById(R.id.txtCuttedPrice);
        txtProductOffers = (TextView)findViewById(R.id.txtProductOffers);
        txtPDPSize = (TextView)findViewById(R.id.txtPDPSize);
        TextView txtProductRating = (TextView)findViewById(R.id.txtProductRating);
        TextView product_size_chart = (TextView)findViewById(R.id.product_size_chart);
        final TextView txtPDQty = (TextView)findViewById(R.id.txtPDQty);
        EditText txtProductPincode = (EditText)findViewById(R.id.txtProductPincode);
        Button btnProductCheck = (Button)findViewById(R.id.btnProductCheck);
        Button btnPDAddCart = (Button)findViewById(R.id.btnPDAddCart);
        llPDPSizeChart = (LinearLayout)findViewById(R.id.llPDPSizeChart);

        TextView txtProduct_view_all = (TextView)findViewById(R.id.txtProduct_view_all);

        txtPDStatusId.setText(statusid);
        if(statusid.equals("Available"))
        {
            txtPDStatusId.setTextColor(ContextCompat.getColor(ProductDetailsActivity.this,R.color.colorProductRating));
        }
        else
        {
            txtPDStatusId.setTextColor(ContextCompat.getColor(ProductDetailsActivity.this,R.color.colorRed));
        }
        txtPDBrandName.setText(brand_name);
        txtPDProductName.setText(pro_title);
        txtPDProductSubTitle.setText(pro_desc);
        txtCuttedPrice.setText(rs+pro_oprice);
        txtCuttedPrice.setPaintFlags(txtCuttedPrice.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        txtProductPrice.setText(pro_price);
        txtProductOffers.setText(pro_discount);
        txtProductRating.setText(rating);

        product_size_chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

}
