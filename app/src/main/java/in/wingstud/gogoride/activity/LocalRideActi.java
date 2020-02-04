package in.wingstud.gogoride.activity;

import android.app.Dialog;
import android.content.Context;
import androidx.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import in.wingstud.gogoride.R;
import in.wingstud.gogoride.databinding.ActivityLocalRideBinding;
import in.wingstud.gogoride.databinding.OtpPopupLayoutBinding;
import in.wingstud.gogoride.util.Utils;

public class LocalRideActi extends AppCompatActivity {

    private Toolbar toolbar;

    private Context mContext;

    private ActivityLocalRideBinding binding;
    private Dialog alert;

    private GoogleMap googleMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_local_ride);

        binding.mapView.onCreate(savedInstanceState);

        binding.mapView.onResume();

        try {
            MapsInitializer.initialize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mContext = LocalRideActi.this;
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

//        binding.toolbar.activityTitle.setText("Auto, 2min away");

        setMap();

        showOtpPopup();
    }

    private void setMap() {
        binding.mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                googleMap.getUiSettings().setZoomControlsEnabled(false);
                googleMap.getUiSettings().setMyLocationButtonEnabled(false);

                googleMap.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(26.9124,75.7873) , 14.0f) );

                googleMap.getUiSettings().setZoomControlsEnabled(false);
                googleMap.getUiSettings().setMyLocationButtonEnabled(false);
                googleMap.getUiSettings().setCompassEnabled(false);
                googleMap.getUiSettings().setRotateGesturesEnabled(false);
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                googleMap.getUiSettings().setMyLocationButtonEnabled(true);

                View locationButton = ((View) binding.mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
                RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
                // position on right bottom
                rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
                rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                rlp.setMargins(0, 180, 180, 0);


                // For showing a move to my location button
//                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
//                            locationRequestCode);
//                } else {
//                    googleMap.setMyLocationEnabled(true);
//                    if (getActivity() != null)
//                        getCurrentLocation();
//                }
            }
        });
    }

    private void showOtpPopup() {
        Dialog alert = new Dialog(mContext);
        alert.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        OtpPopupLayoutBinding otpPopupLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.otp_popup_layout, null, false);
        alert.setContentView(otpPopupLayoutBinding.getRoot());

        otpPopupLayoutBinding.cvOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.llPaymentType.setVisibility(View.GONE);
                binding.llDriverInfo.setVisibility(View.VISIBLE);
                alert.dismiss();
            }
        });

        alert.setCancelable(false);
        alert.show();
    }

    public void confirmBookingProcess(View view){
        showOtpPopup();
    }

    public void otpProcess(View view){
        binding.txtOtp.setVisibility(View.GONE);
        binding.btnPay1.setVisibility(View.VISIBLE);
        binding.txtDistance.setVisibility(View.VISIBLE);
        binding.txtHeading.setText(getString(R.string.net_payable_fair));
    }

    public void pay1Process(View view){
        binding.rlOtp.setVisibility(View.GONE);
        binding.llAdvertising.setVisibility(View.VISIBLE);

    }

    public void pay2Process(View view){
        Utils.startActivity(mContext, RatingActi.class);
    }
}
