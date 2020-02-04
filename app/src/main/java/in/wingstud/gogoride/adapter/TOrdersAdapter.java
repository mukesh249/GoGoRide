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
import in.wingstud.gogoride.databinding.OrderListRowBinding;

/**
 * Created by hemant on 25-08-2019.
 */

public class TOrdersAdapter extends RecyclerView.Adapter<TOrdersAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<TodayPayBean> list;

    public TOrdersAdapter(Context mContext, ArrayList<TodayPayBean> list){
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        OrderListRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.order_list_row, parent, false);

        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TodayPayBean bean = list.get(position);

        holder.binding.txtOrderId.setText(mContext.getString(R.string.order_id) + ": " + bean.getOrderId());
        holder.binding.txtPrice.setText(mContext.getString(R.string.Rs) + bean.getPrice());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private OrderListRowBinding binding;

        public MyViewHolder(final OrderListRowBinding itemBinding) {
            super(itemBinding.getRoot());
            binding = itemBinding;
        }
    }
}
