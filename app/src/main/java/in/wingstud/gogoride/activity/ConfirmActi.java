package in.wingstud.gogoride.activity;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import in.wingstud.gogoride.R;
import in.wingstud.gogoride.databinding.ActivityConfirmBinding;
import in.wingstud.gogoride.util.Constants;
import in.wingstud.gogoride.util.Utils;

public class ConfirmActi extends AppCompatActivity {

    private Toolbar toolbar;

    private Context mContext;

    private ActivityConfirmBinding binding;

    private boolean isRentalCar;
    private boolean isDetailsShow = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_confirm);

        if (getIntent().getExtras() != null)
            isRentalCar = getIntent().getBooleanExtra(Constants.IS_RENTAL_CAR, false);

        mContext = ConfirmActi.this;
        initialize();
    }

    private void initialize() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        binding.toolbar.activityTitle.setText(getString(R.string.confirm_your_booking));

        if (isRentalCar){
            binding.txtVehicleType.setText("SEDAN");
            binding.txtVehicleDesc.setText("4 Seater - Etios, Sunny, Dzire");
            binding.llOtherDetails.setVisibility(View.VISIBLE);
        } else {
            binding.txtVehicleType.setText("MAHINDRA LOADKING");
            binding.txtVehicleDesc.setText("Size(L x W x H):7 x 4 x 5 ft");
            binding.llOtherDetails.setVisibility(View.GONE);
        }


    }

    public void startRideProcess(View view){
        Utils.startActivity(mContext, LocalRideActi.class);
    }

    public void hideShowDetails(View view){
        if (isDetailsShow){
            binding.llFareDetails.setVisibility(View.GONE);
            binding.imvHideShow.setImageResource(R.drawable.ic_arrow_drop_up);
            isDetailsShow = false;
        } else {
            binding.llFareDetails.setVisibility(View.VISIBLE);
            binding.imvHideShow.setImageResource(R.drawable.ic_arrow_drop_down);
            isDetailsShow = true;
        }
    }
}
