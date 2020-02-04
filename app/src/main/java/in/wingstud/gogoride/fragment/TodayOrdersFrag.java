package in.wingstud.gogoride.fragment;


import android.content.Context;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import in.wingstud.gogoride.R;
import in.wingstud.gogoride.activity.Dashboard;
import in.wingstud.gogoride.activity.OrderDetailActi;
import in.wingstud.gogoride.adapter.TOrdersAdapter;
import in.wingstud.gogoride.bean.TodayPayBean;
import in.wingstud.gogoride.databinding.FragmentTodayOrdersBinding;
import in.wingstud.gogoride.util.Utils;
import in.wingstud.gogoride.util.recycler_view_utilities.RecyclerItemClickListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class TodayOrdersFrag extends Fragment {

    private Context mContext;

    private View view;

    private FragmentTodayOrdersBinding binding;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_today_orders, container, false);
        view = binding.getRoot();

        initialize();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

//        ((Dashboard) getContext()).setTitle(mContext.getString(R.string.todays_orders), false);
    }

    private void initialize() {
        binding.rvTOrders.setLayoutManager(new LinearLayoutManager(mContext));
        binding.rvTOrders.setItemAnimator(new DefaultItemAnimator());

        binding.rvTOrders.addOnItemTouchListener(new RecyclerItemClickListener(mContext, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Utils.startActivity(mContext, OrderDetailActi.class);
            }
        }));

        setData();
    }

    private void setData() {
        ArrayList<TodayPayBean> list = new ArrayList<>();

        TodayPayBean bean = new TodayPayBean("CRN457845", "4512525252", 150);
        list.add(bean);
        bean = new TodayPayBean("CRN457845", "4512525252", 150);
        list.add(bean);
        bean = new TodayPayBean("CRN457845", "4512525252", 150);
        list.add(bean);
        bean = new TodayPayBean("CRN457845", "4512525252", 150);
        list.add(bean);

        TOrdersAdapter mAdapter = new TOrdersAdapter(mContext, list);
        binding.rvTOrders.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

}
