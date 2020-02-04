package in.wingstud.gogoride.activity;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import in.wingstud.gogoride.R;
import in.wingstud.gogoride.databinding.ActivityDashboardBinding;
import in.wingstud.gogoride.fragment.HomeFrag;
import in.wingstud.gogoride.fragment.NavMenuFrag;
import in.wingstud.gogoride.util.Constants;
import in.wingstud.gogoride.util.Utils;

public class Dashboard extends AppCompatActivity implements NavMenuFrag.FragmentDrawerListener {

    Toolbar toolbar;

    private Context mContext;

    boolean doubleBackToExitPressedOnce = false;

    private ActivityDashboardBinding binding;

    private NavMenuFrag navMenuFrag;

    private Fragment fragment = new HomeFrag();
    private Fragment fragmentHome = new HomeFrag();

    private android.app.AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);

        mContext = Dashboard.this;
        initialize();
    }

    private void initialize() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navMenuFrag = (NavMenuFrag) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);

        navMenuFrag.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawerLayout), toolbar);
        navMenuFrag.setDrawerListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        binding.toolbar.imvNotification.setVisibility(View.VISIBLE);

        binding.toolbar.imvNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.startActivity(mContext, NotificationActi.class);
            }
        });

        binding.toolbar.local.setOnClickListener(view -> {
            setLocal();
        });
        binding.toolbar.rental.setOnClickListener(view -> {
            setRental();
        });
        binding.toolbar.logistic.setOnClickListener(view -> {
            setLogistic();
        });

        displayView(getResources().getString(R.string.dashboard));
        if(fragment == null)
            fragment = new HomeFrag();
        loadHomeFragment();
    }

//    public void setTitle(String title, boolean isShowSwitch){
//        if (isShowSwitch){
//            binding.toolbar.activityTitle.setVisibility(View.GONE);
//        } else {
//            binding.toolbar.activityTitle.setText(title);
//            binding.toolbar.activityTitle.setVisibility(View.VISIBLE);
//        }
//    }

    private void loadHomeFragment() {
        if(fragment!=null) {
//            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//
//            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
//                    android.R.anim.fade_out);
////            fragmentTransaction.replace(R.id.fmContainer, fragment, CURRENT_TAG);
//            fragmentTransaction.replace(R.id.fmContainer, fragment).addToBackStack(null).commit();
////            fragmentTransaction.commitAllowingStateLoss();

            Utils.replaceFrg(this, fragment, true, Constants.DEFAULT_ID);
        }else{
            Toast.makeText(this, "FRAGMNT", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDrawerItemSelected(View view, String menuNmae) {
        displayView(menuNmae);
    }

    private void displayView(String menuNmae) {
        fragment = null;
        if (menuNmae.equalsIgnoreCase(mContext.getResources().getString(R.string.home))) {
            fragment = new HomeFrag();
            loadHomeFragment();
        }
//        else if (menuNmae.equalsIgnoreCase(mContext.getResources().getString(R.string.profile))) {
//            fragment = new ProfileFrag();
//            loadHomeFragment();
//        }else if (menuNmae.equalsIgnoreCase(mContext.getResources().getString(R.string.commission_wallet))){
//            Utils.startActivity(mContext, WalletActi.class);
//        }else if (menuNmae.equalsIgnoreCase(mContext.getResources().getString(R.string.todays_payment))){
//           fragment = new TodayPayFrag();
//           loadHomeFragment();
//        }else if (menuNmae.equalsIgnoreCase(mContext.getResources().getString(R.string.todays_orders))){
//           fragment = new TodayOrdersFrag();
//           loadHomeFragment();
//        }else if (menuNmae.equalsIgnoreCase(mContext.getResources().getString(R.string.orders_history))){
//            fragment = new HistoryFrag();
//            loadHomeFragment();
//        }
        else if (menuNmae.equalsIgnoreCase(mContext.getResources().getString(R.string.logout))) {

            dialog = Utils.retryAlertDialog(this, getResources().getString(R.string.app_name), getResources().getString(R.string.Are_you_sure_to_logout), getResources().getString(R.string.Cancel), getResources().getString(R.string.Yes), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                        logout(true);
                    Utils.logout(Dashboard.this);
                    dialog.dismiss();
                }
            });


        }
//        if (fragment != null) {
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            fragmentManager.beginTransaction().replace(R.id.fmContainer, fragment).addToBackStack(null).commit();
//        }
    }



    @Override
    public void onBackPressed() {
        backCountToExit();
    }

    private void backCountToExit() {

        FragmentManager fm = this.getSupportFragmentManager();

        if (fm.getBackStackEntryCount() > 1 ){

            fm.popBackStack();


        } else {

            if (doubleBackToExitPressedOnce) {
                finish();
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, getString(R.string.Please_BACK_again_to_exit), Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    public void setLocal(){

        binding.toolbar.local.setTextColor(getResources().getColor(R.color.black));
        binding.toolbar.rental.setTextColor(getResources().getColor(R.color.colorAccent));
        binding.toolbar.logistic.setTextColor(getResources().getColor(R.color.colorAccent));

        binding.toolbar.local.setBackground(getResources().getDrawable(R.drawable.rounded_color_primary_btn_bg));
        binding.toolbar.rental.setBackground(null);
        binding.toolbar.logistic.setBackground(null);
    }

    public void setRental(){

        binding.toolbar.local.setTextColor(getResources().getColor(R.color.colorAccent));
        binding.toolbar.rental.setTextColor(getResources().getColor(R.color.black));
        binding.toolbar.logistic.setTextColor(getResources().getColor(R.color.colorAccent));

        binding.toolbar.local.setBackground(null);
        binding.toolbar.rental.setBackground(getResources().getDrawable(R.drawable.rounded_color_primary_btn_bg));
        binding.toolbar.logistic.setBackground(null);
    }
    public void setLogistic(){

        binding.toolbar.local.setTextColor(getResources().getColor(R.color.colorAccent));
        binding.toolbar.rental.setTextColor(getResources().getColor(R.color.colorAccent));
        binding.toolbar.logistic.setTextColor(getResources().getColor(R.color.black));

        binding.toolbar.local.setBackground(null);
        binding.toolbar.rental.setBackground(null);
        binding.toolbar.logistic.setBackground(getResources().getDrawable(R.drawable.rounded_color_primary_btn_bg));
    }
}
