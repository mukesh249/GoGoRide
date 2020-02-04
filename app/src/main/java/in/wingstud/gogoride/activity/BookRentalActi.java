package in.wingstud.gogoride.activity;

import android.content.Context;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

import in.wingstud.gogoride.R;
import in.wingstud.gogoride.adapter.VehicleListAdapter;
import in.wingstud.gogoride.bean.CarListBean;
import in.wingstud.gogoride.databinding.ActivityBookRentalBinding;
import in.wingstud.gogoride.util.Constants;
import in.wingstud.gogoride.util.recycler_view_utilities.DividerItemDecorationColorPrimary;
import in.wingstud.gogoride.util.recycler_view_utilities.RecyclerItemClickListener;

public class BookRentalActi extends AppCompatActivity {

    private Toolbar toolbar;

    private Context mContext;

    private ActivityBookRentalBinding binding;

    private boolean isRentalCar;
    private ArrayList<CarListBean> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_book_rental);

        if (getIntent().getExtras() != null)
            isRentalCar = getIntent().getBooleanExtra(Constants.IS_RENTAL_CAR, false);

        mContext = BookRentalActi.this;
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

        binding.rvBook.setLayoutManager(new LinearLayoutManager(mContext));
        binding.rvBook.setItemAnimator(new DefaultItemAnimator());
        binding.rvBook.addItemDecoration(new DividerItemDecorationColorPrimary(mContext));
        binding.rvBook.setNestedScrollingEnabled(false);

        binding.rvBook.addOnItemTouchListener(new RecyclerItemClickListener(mContext, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(mContext, ConfirmActi.class);
                intent.putExtra(Constants.IS_RENTAL_CAR, isRentalCar);
                startActivity(intent);
            }
        }));

//        if (isRentalCar){
//            binding.toolbar.activityTitle.setText(getString(R.string.book_your_rental_car));
//            binding.llRentalCar.setVisibility(View.VISIBLE);
//            binding.llLogistic.setVisibility(View.GONE);
//        } else {
//            binding.toolbar.activityTitle.setText(getString(R.string.book_your_logistic));
//            binding.llRentalCar.setVisibility(View.GONE);
//            binding.llLogistic.setVisibility(View.VISIBLE);
//        }


        setData();
    }

    private void setData() {
        CarListBean bean = new CarListBean("Hatchback(4+1)", "WagonR, Tiago, Beat", "900", R.drawable.ic_hatchback);
        list.add(bean);
        bean = new CarListBean("Sedan(4+1)", "Swift Dzire, Etios, Xcent", "1100", R.drawable.ic_sedan);
        list.add(bean);
        bean = new CarListBean("SUV / PUV", "Scorpio, Tavera, Innova", "1500", R.drawable.ic_suv);
        list.add(bean);
        bean = new CarListBean("Traveller", "", "2500", R.drawable.ic_traveller);
        list.add(bean);

        VehicleListAdapter mAdapter = new VehicleListAdapter(mContext, list, isRentalCar);
        binding.rvBook.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}
