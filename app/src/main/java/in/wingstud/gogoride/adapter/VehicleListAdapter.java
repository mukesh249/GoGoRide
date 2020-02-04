package in.wingstud.gogoride.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import in.wingstud.gogoride.R;
import in.wingstud.gogoride.bean.CarListBean;
import in.wingstud.gogoride.databinding.VehicleListRowBinding;

/**
 * Created by hemant on 19-10-2019.
 */

public class VehicleListAdapter extends RecyclerView.Adapter<VehicleListAdapter.MyViewHolder> {

    private boolean isRentalCar;
    private Context mContext;
    private ArrayList<CarListBean> list;

    public VehicleListAdapter(Context mContext, ArrayList<CarListBean> list, boolean isRentalCar) {
        this.mContext = mContext;
        this.list = list;
        this.isRentalCar = isRentalCar;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VehicleListRowBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.vehicle_list_row, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CarListBean bean = list.get(position);
        holder.binding.txtTitle.setText(bean.getTitle());
        holder.binding.txtTypes.setText(bean.getType());
        if (isRentalCar)
            holder.binding.txtCapacity.setVisibility(View.GONE);
        else
            holder.binding.txtCapacity.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private VehicleListRowBinding binding;

        public MyViewHolder(final VehicleListRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
