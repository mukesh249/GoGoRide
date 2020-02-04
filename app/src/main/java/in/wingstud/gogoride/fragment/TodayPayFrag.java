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
import in.wingstud.gogoride.adapter.TodaysPayAdapter;
import in.wingstud.gogoride.bean.TodayPayBean;
import in.wingstud.gogoride.databinding.FragmentTodayPayBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class TodayPayFrag extends Fragment {

    private Context mContext;

    private View view;

    private FragmentTodayPayBinding binding;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_today_pay, container, false);
        view = binding.getRoot();

        initialize();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

//        ((Dashboard) getContext()).setTitle(mContext.getString(R.string.todays_payment), false);
    }

    private void initialize() {
        binding.rvToday.setLayoutManager(new LinearLayoutManager(mContext));
        binding.rvToday.setItemAnimator(new DefaultItemAnimator());

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

        TodaysPayAdapter mAdapter = new TodaysPayAdapter(mContext, list);
        binding.rvToday.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

}
