package in.wingstud.gogoride.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import java.util.List;


import in.wingstud.gogoride.R;
import in.wingstud.gogoride.adapter.PlacesAutoCompleteAdapter;
import in.wingstud.gogoride.api.Constrants;
import in.wingstud.gogoride.api.SharedPrefManager;
import in.wingstud.gogoride.databinding.ActivityChooseLocationBinding;
import in.wingstud.gogoride.model.PlaceInfo;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class Choose_Location extends AppCompatActivity implements PlacesAutoCompleteAdapter.ClickListener {


    private static final String TAG = "Choose Location";
    private static String add="";

    ImageView currentlocation_refressh;
    AutoCompleteTextView mSearch_et;

    private FusedLocationProviderClient mFusedLocationProviderClient;
    TextView textView;
    ProgressDialog progressDialog=null;
    Toolbar toolbar;

    private PlacesAutoCompleteAdapter mAutoCompleteAdapter;
    private RecyclerView recyclerView;
    private String select="";

    private ActivityChooseLocationBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_choose__location);
        //mContext = this;

        mSearch_et = (AutoCompleteTextView) findViewById(R.id.input_search_ch);
        toolbar = (Toolbar)findViewById(R.id.toolbar_choose_l);
        setSupportActionBar(toolbar);


        if (getIntent().getExtras()!=null){
            if (getIntent().getExtras().getString("select","").equals("pickup")){
                select = getIntent().getExtras().getString("select","");
                getSupportActionBar().setTitle("Enter Pick Location");
            }else if(getIntent().getExtras().getString("select","").equals("drop")){
                select = getIntent().getExtras().getString("select","");
                getSupportActionBar().setTitle("Enter Drop Location");
            }
        }

        toolbar.setTitleTextColor(Color.BLACK);

        progressDialog = new ProgressDialog(Choose_Location.this);
        requestPremission();


        currentlocation_refressh = (ImageView)findViewById(R.id.currentlocation_refressh);
        textView = (TextView)findViewById(R.id.current_location_tv);

        mSearch_et = (AutoCompleteTextView)findViewById(R.id.input_search_ch);


        Places.initialize(this, getResources().getString(R.string.google_key));
        PlacesClient placesClient = Places.createClient(this);
        AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();

        binding.inputSearchCh.addTextChangedListener(filterTextWatcher);

        mAutoCompleteAdapter = new PlacesAutoCompleteAdapter(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAutoCompleteAdapter.setClickListener(Choose_Location.this);
        binding.recyclerView.setAdapter(mAutoCompleteAdapter);
        mAutoCompleteAdapter.notifyDataSetChanged();

        currentlocation_refressh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDeviceLocation();
            }
        });
        binding.icClear.setVisibility(View.GONE);
        binding.icClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (binding.inputSearchCh.getText().toString().isEmpty()){
//                    binding.icClear.setVisibility(View.GONE);
//                }else {
                    binding.inputSearchCh.setText("");
                    binding.icClear.setVisibility(View.GONE);
//                }
            }
        });

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

    }
    private TextWatcher filterTextWatcher = new TextWatcher() {
        public void afterTextChanged(Editable s) {
            if (!s.toString().equals("")) {
                binding.icClear.setVisibility(View.VISIBLE);
                mAutoCompleteAdapter.getFilter().filter(s.toString());
                if (binding.recyclerView.getVisibility() == View.GONE) {binding.recyclerView.setVisibility(View.VISIBLE);}
            } else {
                binding.icClear.setVisibility(View.GONE);
                if (binding.recyclerView.getVisibility() == View.VISIBLE) {binding.recyclerView.setVisibility(View.GONE);}
            }
        }
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
        public void onTextChanged(CharSequence s, int start, int before, int count) { }
    };

    @Override
    public void click(Place place) {
        String latitude , longitude,address;
        address = place.getAddress();
        latitude = place.getLatLng().latitude+"";
        longitude = place.getLatLng().longitude+"";
        if (select.equals("pickup")) {
//            SharedPrefManager.setCity(Constrants.City,place.get);
            SharedPrefManager.setPickUpAddress(Constrants.PickAddress, address + "");
            SharedPrefManager.setPickUpLat(Constrants.PickUpLat, latitude + "");
            SharedPrefManager.setPickUpLong(Constrants.PickUpLong, longitude + "");
        }else if (select.equals("drop")){
            SharedPrefManager.setDropAddress(Constrants.DropAddress, address + "");
            SharedPrefManager.setDropLat(Constrants.DropLat, latitude + "");
            SharedPrefManager.setDropLong(Constrants.DropLong, longitude + "");
        }
        finish();
//        Toast.makeText(this, place.getAddress()+", "+place.getLatLng().latitude+place.getLatLng().longitude, Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    public void getDeviceLocation() {
        progressDialog.show();
        Log.d(TAG, "getDeviceLocation: getting the device current location");
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

//            mFusedLocationProviderClient.getLastLocation().addOnSuccessListener(Choose_Location.this, new OnSuccessListener<Location>() {
//                @Override
//                public void onSuccess(Location location) {
//                    if (location != null){
//                        textView.setText(location.toString());
//                    }
//                }
//            });

            final Task<Location> location = mFusedLocationProviderClient.getLastLocation();
            location.addOnCompleteListener(this, new OnCompleteListener<Location>(){
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()){
                        progressDialog.dismiss();
                        Log.d(TAG,"onComplete: Found location");
                        Location currentLocation = (Location)task.getResult();

                        SharedPrefManager.getInstance(Choose_Location.this).storeLatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
                        SharedPreferences sharedPreferencesReg = getSharedPreferences("LatLng", Context.MODE_PRIVATE);

//                        SharedPreferences.Editor editor = sharedPreferencesReg.edit();
//                        editor.putString("LAT", String.valueOf());
//                        editor.putString("LNG", String.valueOf());
//                        editor.commit();
//                        getAddress(Double.valueOf(sharedPreferencesReg.getString("LAT","")), Double.valueOf(sharedPreferencesReg.getString("LNG","")));

                        textView.setText(add);

                    }
                    else {
                        Log.d(TAG,"onComplete:  current is location null");
                        Toast.makeText(Choose_Location.this, R.string.location_not_found, Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }catch (SecurityException e){
            Log.e(TAG,"getDeviceLocation: SecurityException" + e.getMessage());
        }
    }

    private void requestPremission(){
        ActivityCompat.requestPermissions(this,new String[]{ACCESS_FINE_LOCATION},1);
        getDeviceLocation();
//        init();
    }
}
