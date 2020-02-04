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
import in.wingstud.gogoride.adapter.WalletAdapter;
import in.wingstud.gogoride.bean.WalletBean;
import in.wingstud.gogoride.databinding.ActivityWalletBinding;

public class WalletActi extends AppCompatActivity {

    private Toolbar toolbar;

    private Context mContext;

    private ActivityWalletBinding binding;

    private WalletAdapter mAdapter;
    private ArrayList<WalletBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_wallet);

        mContext = WalletActi.this;
        initialize();
    }

    private void initialize() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.rvWallet.setLayoutManager(new LinearLayoutManager(mContext));
        binding.rvWallet.setItemAnimator(new DefaultItemAnimator());

        binding.toolbar.imvNotification.setVisibility(View.VISIBLE);

        setCommissionData();
    }

    private void setCommissionData() {
        WalletBean bean = new WalletBean("CRN000012");
        list.add(bean);
        bean = new WalletBean("CRN000013");
        list.add(bean);
        bean = new WalletBean("CRN000014");
        list.add(bean);
        bean = new WalletBean("CRN000020");
        list.add(bean);
        bean = new WalletBean("CRN000088");
        list.add(bean);
        bean = new WalletBean("CRN000132");
        list.add(bean);

        mAdapter = new WalletAdapter(mContext, list);
        binding.rvWallet.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}
