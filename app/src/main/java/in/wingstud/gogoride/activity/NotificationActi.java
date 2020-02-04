package in.wingstud.gogoride.activity;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

import in.wingstud.gogoride.R;
import in.wingstud.gogoride.adapter.NotificationAdapter;
import in.wingstud.gogoride.bean.NotificationBean;
import in.wingstud.gogoride.databinding.ActivityNotificationBinding;

public class NotificationActi extends AppCompatActivity {

    private Toolbar toolbar;

    private Context mContext;

    private ActivityNotificationBinding binding;

    private ArrayList<NotificationBean> list = new ArrayList<>();
    private NotificationAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notification);

        mContext = NotificationActi.this;
        initialize();
    }

    private void initialize() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


//        binding.toolbar.activityTitle.setText(getString(R.string.notification));


        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.rvNotification.setLayoutManager(new LinearLayoutManager(mContext));
        binding.rvNotification.setItemAnimator(new DefaultItemAnimator());

        binding.toolbar.imvNotification.setVisibility(View.GONE);

        setNotificationData();
    }

    private void setNotificationData() {
        NotificationBean bean = new NotificationBean("Mountain Dew Soft Drink", "Just Now", "Lorem ipsum is simply dummy text of the printing" +
                "and typesetting industry. Lorem ipsum has been the industry's standard dummy text ever since the 1500s");
        list.add(bean);
        bean = new NotificationBean("Mountain Dew Soft Drink", "1 minutes ago", "Lorem ipsum is simply dummy text of the printing" +
                "and typesetting industry. Lorem ipsum has been the industry's standard dummy text ever since the 1500s");
        list.add(bean);
        bean = new NotificationBean("Mountain Dew Soft Drink", "1 hours ago", "Lorem ipsum is simply dummy text of the printing" +
                "and typesetting industry. Lorem ipsum has been the industry's standard dummy text ever since the 1500s");
        list.add(bean);

        mAdapter = new NotificationAdapter(mContext, list);
        binding.rvNotification.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}
