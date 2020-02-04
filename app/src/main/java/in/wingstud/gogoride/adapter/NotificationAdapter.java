package in.wingstud.gogoride.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import in.wingstud.gogoride.R;
import in.wingstud.gogoride.bean.NotificationBean;
import in.wingstud.gogoride.databinding.NotificationRowBinding;

/**
 * Created by hemant on 25-08-2019.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<NotificationBean> list;

    public NotificationAdapter(Context mContext, ArrayList<NotificationBean> list){
        this.mContext=  mContext;
        this.list = list;
    }
    @NonNull
    @Override
    public NotificationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NotificationRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.notification_row, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.MyViewHolder holder, int position) {
        NotificationBean bean = list.get(position);

        try {
            holder.binding.txtTitle.setText(bean.getTitle());
            holder.binding.txtDesc.setText(bean.getDesc());
            holder.binding.txtTime.setText(bean.getTime());
        } catch (Exception e){

        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private NotificationRowBinding binding;

        public MyViewHolder(final NotificationRowBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }
}
