package in.wingstud.gogoride.fragment;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryDataEventListener;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import in.wingstud.gogoride.R;
import in.wingstud.gogoride.activity.BookRentalActi;
import in.wingstud.gogoride.activity.Choose_Location;
import in.wingstud.gogoride.activity.LocalRideActi;
import in.wingstud.gogoride.adapter.GetVehicleAdapter;
import in.wingstud.gogoride.api.Constrants;
import in.wingstud.gogoride.api.JsonDeserializer;
import in.wingstud.gogoride.api.RequestCode;
import in.wingstud.gogoride.api.SharedPrefManager;
import in.wingstud.gogoride.api.WebCompleteTask;
import in.wingstud.gogoride.api.WebTask;
import in.wingstud.gogoride.api.WebUrls;
import in.wingstud.gogoride.databinding.FragmentHomeBinding;
import in.wingstud.gogoride.model.GetVehicleTypeResponse;
import in.wingstud.gogoride.util.Constants;
import in.wingstud.gogoride.util.Utils;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.LOCATION_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFrag extends Fragment implements
        View.OnClickListener, OnMapReadyCallback, WebCompleteTask {

    private View view;
    private Context mContext;
    private FragmentHomeBinding binding;
    private String whichType = Constants.LOCAL_RIDE;
    private GoogleMap googleMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private PlacesClient placesClient;
    private GoogleApiClient mGoogleApiClient;
    private static final String TAG = "Home";
    public static double lat_double_current, log_double_current;
    public static LatLng latLng_current_location;
    public static String add_c;
    private static final float DEFAULT_ZOOM = 15f;
    private View mapView;

    boolean searchadd = false;
    private LatLng center;
    private Geocoder geocoder;
    private List<Address> addresses;
    double latitude, longitude;
    private Location mLastKnowLocation;
    private LocationCallback locationCallback;
    private boolean drop = false, pickUp = false;
    int drop_count = 0, pick_count = 0;
    private GetVehicleAdapter getVehicleAdapter;

    public static HomeFrag mInstance;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        view = binding.getRoot();

//        binding.mapViewHome.onCreate(savedInstanceState);
//        binding.mapViewHome.onResume();
//        Initialize the AutocompleteSupportFragment.
        mInstance = this;
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
            mapView = mapFragment.getView();
        }

        binding.vehicleTypeRecyclerView
                .setLayoutManager(new LinearLayoutManager(getActivity(),
                        LinearLayoutManager.HORIZONTAL, false));
        pickUp = true;
        drop = false;

        binding.llPickup.setOnClickListener(view -> {
                    pickUp = true;
                    drop = false;
                    binding.pickfromTv.setVisibility(View.VISIBLE);
                    binding.dropatTv.setVisibility(View.GONE);
                    drop_count = 0;
                    pick_count++;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        binding.mapPin.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                    }
                    if (pickUp && pick_count == 2) {
                        pick_count = 0;
                        startActivity(new Intent(getActivity(), Choose_Location.class)
                                .putExtra("select", "pickup"));
                    } else if (pickUp && pick_count == 1) {
                        if (SharedPrefManager.getPickUpLat(Constrants.PickUpLat) != null &&
                                !SharedPrefManager.getPickUpLat(Constrants.PickUpLat).isEmpty()) {
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(
                                            Double.parseDouble(SharedPrefManager.getPickUpLat(Constrants.PickUpLat)),
                                            Double.parseDouble(SharedPrefManager.getPickUpLong(Constrants.PickUpLong))),
                                    DEFAULT_ZOOM));
                        }
                    }

                }
        );
        binding.llDrop.setOnClickListener(view -> {
                    pickUp = false;
                    drop = true;
                    pick_count = 0;

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        binding.mapPin.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.md_red_700)));
                    }
                    binding.pickfromTv.setVisibility(View.GONE);
                    binding.dropatTv.setVisibility(View.VISIBLE);
                    drop_count++;
                    if (drop && drop_count == 2) {
                        drop_count = 0;
                        startActivity(new Intent(getActivity(), Choose_Location.class)
                                .putExtra("select", "drop"));
                    } else if (drop && drop_count == 1) {
                        if (SharedPrefManager.getDropLat(Constrants.DropLat) != null &&
                                !SharedPrefManager.getDropLat(Constrants.DropLat).isEmpty()) {
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(
                                            Double.parseDouble(SharedPrefManager.getDropLat(Constrants.DropLat)),
                                            Double.parseDouble(SharedPrefManager.getDropLong(Constrants.DropLong))),
                                    DEFAULT_ZOOM));
                        }
                    }
                }
        );

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        Places.initialize(getActivity(), getResources().getString(R.string.google_key));
        placesClient = Places.createClient(getActivity());

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //        LocationServiceOn_Off();
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        if (locationManager == null) {
            showSettingsAlert();
        } else {
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                Log.i("About GPS", "GPS is Enabled in your devide");
                // Toast.makeText(ctx,"enable",Toast.LENGTH_SHORT).show();
            } else {
                // showAlert
                showSettingsAlert();
                // context.startActivity(new Intent(context, DialogActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        }
        getVehicleMethod();
        initialize();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (drop && SharedPrefManager.getDropLat(Constrants.DropLat) != null &&
                !SharedPrefManager.getDropLat(Constrants.DropLat).isEmpty()) {
            binding.dropAddressTv.setText(SharedPrefManager.getDropAddress(Constrants.DropAddress));

            if (googleMap != null)
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(
                                Double.parseDouble(SharedPrefManager.getDropLat(Constrants.DropLat)),
                                Double.parseDouble(SharedPrefManager.getDropLong(Constrants.DropLong))),
                        DEFAULT_ZOOM));
        }
        if (pickUp && SharedPrefManager.getPickUpLat(Constrants.PickUpLat) != null &&
                !SharedPrefManager.getPickUpLat(Constrants.PickUpLat).isEmpty()) {
            binding.pickUpAdd.setText(SharedPrefManager.getPickUpAddress(Constrants.PickAddress));

            if (googleMap != null)
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(
                                Double.parseDouble(SharedPrefManager.getPickUpLat(Constrants.PickUpLat)),
                                Double.parseDouble(SharedPrefManager.getPickUpLong(Constrants.PickUpLong))),
                        DEFAULT_ZOOM));
        }
    }

    private void initialize() {
        binding.txtGetRide.setOnClickListener(this);
        binding.llRentalCar.setOnClickListener(this);
        binding.llLocalRide.setOnClickListener(this);
        binding.llLogistic.setOnClickListener(this);

        // Get a reference to our posts
//        final FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference ref = database.getReference("online_drivers");

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("online_drivers");
        GeoFire geoFire = new GeoFire(ref);

        // creates a new query around [37.7832, -122.4056] with a radius of 0.6 kilometers
        GeoQuery geoQuery = geoFire.queryAtLocation(new GeoLocation(37.7832, -122.4056), 0.6);
        geoQuery.addGeoQueryDataEventListener(new GeoQueryDataEventListener() {

            @Override
            public void onDataEntered(DataSnapshot dataSnapshot, GeoLocation location) {
                // ...
            }

            @Override
            public void onDataExited(DataSnapshot dataSnapshot) {
                // ...
            }

            @Override
            public void onDataMoved(DataSnapshot dataSnapshot, GeoLocation location) {
                // ...
            }

            @Override
            public void onDataChanged(DataSnapshot dataSnapshot, GeoLocation location) {
                Map<String, Object> objectMap = (HashMap<String, Object>)
                        dataSnapshot.getValue();
//                List<Match> = new ArrayList<Match>();
//                for (Object obj : objectMap.values()) {
//                    if (obj instanceof Map) {
//                        Map<String, Object> mapObj = (Map<String, Object>) obj;
//                        FieldClassification.Match match = new Match();
////                        match.setSport((String) mapObj.get(Constants.SPORT));
////                        match.setPlayingWith((String) mapObj.get(Constants.PLAYER));
////                        list.add(match);
//                    }
//                }
            }

            @Override
            public void onGeoQueryReady() {
                // ...
            }

            @Override
            public void onGeoQueryError(DatabaseError error) {
                // ...
            }

        });
    }

    public static HomeFrag getInstance() {
        return mInstance;
    }

    public void estimatShow(String s) {
        binding.estimateTv.setText("Estimated Fair : ₹" + s);
    }

    private void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        // Setting Dialog Title
        alertDialog.setTitle(R.string.gps_setting);

        // Setting Dialog Message
        alertDialog.setMessage(R.string.gps_setting_menu);

        // On pressing Settings button
        alertDialog.setPositiveButton(R.string.settings,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        getActivity().startActivity(intent);
                    }
                });

        // on pressing cancel button
        alertDialog.setNegativeButton(getString(R.string.cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        // Showing Alert Message
        alertDialog.show();
    }

//    private void centerCamera() {
//        if (!searchadd) {
//            googleMap.setOnCameraIdleListener(() -> {
//                center = googleMap.getCameraPosition().target;
//                googleMap.clear();
//                try {
//                    if (!searchadd) {
//                        new GetLocationAsync(center.latitude, center.longitude).execute();
//                    }
//
//                } catch (Exception e) {
//                }
//            });
//        }
//    }

    @Override
    public void onMapReady(GoogleMap mMap) {
        googleMap = mMap;
//        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        // For showing a move to my location button
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    1);
        } else {
            mMap.setMyLocationEnabled(true);
        }

        if (!searchadd) {
            googleMap.setOnCameraIdleListener(() -> {
                center = googleMap.getCameraPosition().target;
                googleMap.clear();
                try {
                    if (!searchadd) {
                        new GetLocationAsync(center.latitude, center.longitude).execute();
                    }
                } catch (Exception e) {

                }
            });
        }

        if (mapView != null && mapView.findViewById(Integer.parseInt("1")) != null) {
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 0, 40, 180);

            //check if gps is enabled or not then request user to enable it.
            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setInterval(10000);
            locationRequest.setFastestInterval(5000);
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);

            SettingsClient settingsClient = LocationServices.getSettingsClient(getActivity());
            Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(builder.build());


            task.addOnSuccessListener(getActivity(),
                    locationSettingsResponse -> getDeviceLocation());
            task.addOnFailureListener(getActivity(), e -> {
                if (e instanceof ResolvableApiException) {
                    ResolvableApiException resolve = (ResolvableApiException) e;
                    try {
                        resolve.startResolutionForResult(getActivity(), 51);
                    } catch (IntentSender.SendIntentException ex) {
                        ex.printStackTrace();
                    }
                }
            });

        }
    }

    public void getVehicleMethod() {
        HashMap objectNew = new HashMap();
        objectNew.put("type", "local_ride");
        new WebTask(getActivity(), WebUrls.BASE_URL + WebUrls.GetVehicleType, objectNew, HomeFrag.this, RequestCode.CODE_GetVehicleType, 5);
    }

    @Override
    public void onComplete(String response, int taskcode) {

        if (RequestCode.CODE_GetVehicleType == taskcode) {
            GetVehicleTypeResponse vehicleTypeResponse = JsonDeserializer.deserializeJson(response, GetVehicleTypeResponse.class);

            if (vehicleTypeResponse.statusCode == 1) {
                getVehicleAdapter = new GetVehicleAdapter(getActivity(), vehicleTypeResponse.data);
                binding.vehicleTypeRecyclerView.setAdapter(getVehicleAdapter);
                getVehicleAdapter.notifyDataSetChanged();
            }
        }

    }

    private class GetLocationAsync extends AsyncTask<String, Void, String> {
        double x, y;
        StringBuilder str;

        public GetLocationAsync(double latitude, double longitude) {
            x = latitude;
            y = longitude;
        }

        @Override
        protected void onPreExecute() {
            if (pickUp) {
                binding.pickUpAdd.setText(" Getting location... ");
            }
            if (drop) {
                binding.dropAddressTv.setText(" Getting location... ");
            }
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                geocoder = new Geocoder(getActivity(), Locale.ENGLISH);
                addresses = geocoder.getFromLocation(x, y, 1);
                str = new StringBuilder();
                if (Geocoder.isPresent()) {
                    if (addresses.size() > 0) {
                        Address returnAddress = addresses.get(0);
                        String localityString = returnAddress.getLocality();
                        String city = returnAddress.getCountryName();
                        String region_code = returnAddress.getCountryCode();
                        String zipcode = returnAddress.getPostalCode();

                        str.append(localityString + "");
                        str.append(city + "" + region_code + "");
                        str.append(zipcode + "");
                    } else {
                    }

                } else {

                }
            } catch (IOException e) {
                Log.e("tag", e.getMessage());
            }
            return null;

        }

        @Override
        protected void onPostExecute(String result) {
            try {
                if (addresses.size() > 0) {
                    latitude = center.latitude;
                    longitude = center.longitude;

                    if (pickUp) {
                        binding.pickUpAdd.setText(String.format("%s ", addresses.get(0).getAddressLine(0)));
//                        SharedPrefManager.setCity(Constrants.City,addresses.get(0).get);
                        SharedPrefManager.setPickUpAddress(Constrants.PickAddress, addresses.get(0).getAddressLine(0) + "");
                        SharedPrefManager.setPickUpLat(Constrants.PickUpLat, latitude + "");
                        SharedPrefManager.setPickUpLong(Constrants.PickUpLong, longitude + "");
                    }
                    if (drop) {
                        binding.dropAddressTv.setText(String.format("%s ", addresses.get(0).getAddressLine(0)));
                        SharedPrefManager.setDropAddress(Constrants.DropAddress, addresses.get(0).getAddressLine(0) + "");
                        SharedPrefManager.setDropLat(Constrants.DropLat, latitude + "");
                        SharedPrefManager.setDropLong(Constrants.DropLong, longitude + "");
                    }


                    Log.i("", "Place_onCameraMove:  " + center.latitude + "    " + center.longitude);

                } else {

                    binding.pickUpAdd.setText("Location not found");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 51) {
            if (resultCode == RESULT_OK) {
                getDeviceLocation();
            }
        }
//        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
//            Place place =Autocomplete.getPlaceFromIntent(data);
//            String address = place.getName() + ", " + place.getAddress();
//            Log.i("", "Place_search: " +address+"\n"+latitude+"  ,  " +longitude);
////            binding.addressTv.setText(address);
//
//            center = place.getLatLng();
//            if (center!=null) {
//                latitude = center.latitude;
//                longitude = center.longitude;
////                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng( latitude,longitude), DEFAULT_ZOOM));
//                searchadd = false;
//            }
////            mFusedLocationProviderClient.removeLocationUpdates(locationCallback);
//
//        } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
//            // TODO: Handle the error.
//            Status status = Autocomplete.getStatusFromIntent(data);
//            Log.i("", status.getStatusMessage());
//        } else if (resultCode == RESULT_CANCELED) {
//            // The user canceled the operation.
//
//        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {

            case 1: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity(), "Permission granted", Toast.LENGTH_SHORT).show();
                    getDeviceLocation();
                } else {
                    Toast.makeText(getActivity(), "Permission denied", Toast.LENGTH_SHORT).show();
                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                                1);
                    }
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onResume() {
        super.onResume();

//        ((Dashboard) getContext()).setTitle("", true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llRentalCar:
                setRentalCar();
                break;
            case R.id.llLocalRide:
                setLocalRide();
                break;
            case R.id.llLogistic:
                setLogistic();
                break;
            case R.id.txtGetRide:
                goToNextScreen();
                break;
        }
    }

    private void goToNextScreen() {
        if (whichType.equals(Constants.LOCAL_RIDE))
            Utils.startActivity(mContext, LocalRideActi.class);
        else {
            Intent intent = new Intent(mContext, BookRentalActi.class);
            if (whichType.equals(Constants.RENTAL_CAR))
                intent.putExtra(Constants.IS_RENTAL_CAR, true);
            else
                intent.putExtra(Constants.IS_RENTAL_CAR, false);
            startActivity(intent);

        }
    }

    private void setRentalCar() {
        whichType = Constants.RENTAL_CAR;
        binding.txtLocal.setTextColor(mContext.getResources().getColor(R.color.gray));
        binding.imvLocal.setImageResource(R.drawable.ic_rickshaw_grey);

        binding.txtRental.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
        binding.imvRental.setImageResource(R.drawable.ic_sedan);

        binding.txtLogistic.setTextColor(mContext.getResources().getColor(R.color.gray));
        binding.imvLogistic.setImageResource(R.drawable.ic_logistic_grey);
    }

    private void setLocalRide() {
        whichType = Constants.LOCAL_RIDE;
        binding.txtLocal.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
        binding.imvLocal.setImageResource(R.drawable.ic_rickshaw);

        binding.txtRental.setTextColor(mContext.getResources().getColor(R.color.gray));
        binding.imvRental.setImageResource(R.drawable.ic_sedan_black);

        binding.txtLogistic.setTextColor(mContext.getResources().getColor(R.color.gray));
        binding.imvLogistic.setImageResource(R.drawable.ic_logistic_grey);
    }

    private void setLogistic() {
        whichType = Constants.LOGISTIC;
        binding.txtLocal.setTextColor(mContext.getResources().getColor(R.color.gray));
        binding.imvLocal.setImageResource(R.drawable.ic_rickshaw_grey);

        binding.txtRental.setTextColor(mContext.getResources().getColor(R.color.gray));
        binding.imvRental.setImageResource(R.drawable.ic_sedan_black);

        binding.txtLogistic.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
        binding.imvLogistic.setImageResource(R.drawable.ic_logistic);
    }

    /*-------------------------------Get Device Locaiton---------------------------------*/

    public void getDeviceLocation() {
        mFusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {

                if (task.isSuccessful()) {
                    mLastKnowLocation = task.getResult();
                    if (mLastKnowLocation != null) {
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastKnowLocation.getLatitude(), mLastKnowLocation.getLongitude()), DEFAULT_ZOOM));
                    } else {
                        LocationRequest locationRequest = LocationRequest.create();
                        locationRequest.setInterval(10000);
                        locationRequest.setFastestInterval(5000);
                        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                        locationCallback = new LocationCallback() {
                            @Override
                            public void onLocationResult(LocationResult locationResult) {
                                super.onLocationResult(locationResult);
                                if (locationResult == null) {
                                    return;
                                }
                                mLastKnowLocation = locationResult.getLastLocation();
                                latitude = mLastKnowLocation.getLatitude();
                                longitude = mLastKnowLocation.getLongitude();

                                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), DEFAULT_ZOOM));
                                mFusedLocationProviderClient.removeLocationUpdates(locationCallback);
                            }
                        };

                        mFusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
                    }
                } else {
                    Toast.makeText(getActivity(), "unable to get last location", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
