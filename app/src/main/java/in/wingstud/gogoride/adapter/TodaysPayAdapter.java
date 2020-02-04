package in.wingstud.gogoride.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import in.wingstud.gogoride.R;
import in.wingstud.gogoride.bean.TodayPayBean;
import in.wingstud.gogoride.databinding.TodaysPayLayoutBinding;

/**
 * Created by hemant on 24-08-2019.
 */

public class TodaysPayAdapter extends RecyclerView.Adapter<TodaysPayAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<TodayPayBean> list;

    public TodaysPayAdapter(Context mContext, ArrayList<TodayPayBean> list){
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TodaysPayLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.todays_pay_layout, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TodayPayBean bean = list.get(position);

        holder.binding.txtOrderId.setText(mContext.getString(R.string.order_id) + ": " + bean.getOrderId());
        holder.binding.txtMobile.setText("+91 " + bean.getMobileNo());
        holder.binding.txtPrice.setText(mContext.getString(R.string.Rs) + bean.getPrice());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TodaysPayLayoutBinding binding;

        public MyViewHolder(final TodaysPayLayoutBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }
}
