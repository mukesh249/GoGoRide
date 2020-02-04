package in.wingstud.gogoride.activity;

import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.internal.Constants;

import in.wingstud.gogoride.R;
import in.wingstud.gogoride.api.Constrants;
import in.wingstud.gogoride.api.SharedPrefManager;
import in.wingstud.gogoride.util.SharedPref;
import in.wingstud.gogoride.util.Utils;

public class SplashActi extends AppCompatActivity {

    private Context mContext;
    SharedPrefManager sharedPrefManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sharedPrefManager = new SharedPrefManager(this);
        thread();

    }

    private void thread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    Utils.setDeviceSreenH(SplashActi.this);
                    if (SharedPrefManager.isLogin(Constrants.IsLogin)) {

//                        if (SharedPref.isLogin(SplashActi.this)) {

                            Utils.startActivityWithFinish(SplashActi.this, Dashboard.class);
//                        }
//
                    } else {
                        Utils.startActivityWithFinish(SplashActi.this, LoginActi.class);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

}
