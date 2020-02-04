package in.wingstud.gogoride.fragment;


import android.content.Context;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.AbstractList;
import java.util.ArrayList;

import in.wingstud.gogoride.R;
import in.wingstud.gogoride.activity.Dashboard;
import in.wingstud.gogoride.activity.OrderDetailActi;
import in.wingstud.gogoride.adapter.TOrdersAdapter;
import in.wingstud.gogoride.bean.TodayPayBean;
import in.wingstud.gogoride.databinding.FragmentHistoryBinding;
import in.wingstud.gogoride.util.recycler_view_utilities.RecyclerItemClickListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFrag extends Fragment {

    private View view;

    private Context mContext;

    private FragmentHistoryBinding binding;
    private AbstractList<TodayPayBean> orderList;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false);
        view = binding.getRoot();

        initialize();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

//        ((Dashboard) getContext()).setTitle(mContext.getString(R.string.orders_history), false);
    }

    private void initialize() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        binding.rvOrders.setLayoutManager(mLayoutManager);
        binding.rvOrders.setItemAnimator(new DefaultItemAnimator());


        binding.rvOrders.addOnItemTouchListener(new RecyclerItemClickListener(mContext, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(mContext, OrderDetailActi.class);
//                intent.putExtra(Constants.ORDER_ID, orderList.get(position).getOrderId());
                startActivity(intent);
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
        binding.rvOrders.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }


}
