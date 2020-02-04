package in.wingstud.gogoride.activity;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

import in.wingstud.gogoride.R;
import in.wingstud.gogoride.bean.ItemsBean;
import in.wingstud.gogoride.bean.OrderDetailBean;
import in.wingstud.gogoride.databinding.ActivityOrderDetailBinding;
import in.wingstud.gogoride.util.Constants;
import in.wingstud.gogoride.util.Utils;

public class OrderDetailActi extends AppCompatActivity {

    Toolbar toolbar;

    private Context mContext;

    private ActivityOrderDetailBinding binding;

    private ArrayList<ItemsBean> list = new ArrayList<>();

    private String orderId;

    private OrderDetailBean orderDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_detail);

        if (getIntent().getExtras() != null)
            orderId = getIntent().getStringExtra(Constants.ORDER_ID);

        mContext = OrderDetailActi.this;
        initialize();
    }

    private void initialize() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


//        binding.toolbar.activityTitle.setText(getString(R.string.order_details));


        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.toolbar.imvNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.startActivity(mContext, NotificationActi.class);
            }
        });

    }

}
