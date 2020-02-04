package in.wingstud.gogoride.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import in.wingstud.gogoride.R;
import in.wingstud.gogoride.api.Constrants;
import in.wingstud.gogoride.api.JsonDeserializer;
import in.wingstud.gogoride.api.RequestCode;
import in.wingstud.gogoride.api.SharedPrefManager;
import in.wingstud.gogoride.api.WebCompleteTask;
import in.wingstud.gogoride.api.WebTask;
import in.wingstud.gogoride.api.WebUrls;
import in.wingstud.gogoride.bean.NotificationBean;
import in.wingstud.gogoride.databinding.GetVehicleTypeBinding;
import in.wingstud.gogoride.databinding.NotificationRowBinding;
import in.wingstud.gogoride.fragment.HomeFrag;
import in.wingstud.gogoride.model.GetEstimatedFairResponse;
import in.wingstud.gogoride.model.GetVehicleTypeResponse;

/**
 * Created by mukku on 25-08-2019.
 */

public class GetVehicleAdapter extends RecyclerView.Adapter<GetVehicleAdapter.MyViewHolder> implements WebCompleteTask {

    private Context mContext;
    private List<GetVehicleTypeResponse.Datum> list;

    public GetVehicleAdapter(Context mContext, List<GetVehicleTypeResponse.Datum> list){
        this.mContext=  mContext;
        this.list = list;
    }
    @NonNull
    @Override
    public GetVehicleAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        GetVehicleTypeBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.get_vehicle_type, parent, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull GetVehicleAdapter.MyViewHolder holder, int position) {
        GetVehicleTypeResponse.Datum bean = list.get(position);

        try {
            holder.binding.nameVehicleTv.setText(bean.name);
//            holder.binding.txtDesc.setText(bean.getDesc());
//            holder.binding.txtTime.setText(bean.getTime());
        } catch (Exception e){

        }

        if (position == 0){
            getLocalRideEstimatedFair(bean.id.toString());
        }
        holder.itemView.setOnClickListener(view -> getLocalRideEstimatedFair(bean.id.toString()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private GetVehicleTypeBinding binding;

        public MyViewHolder(final GetVehicleTypeBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }
    public void getLocalRideEstimatedFair(String id){
        HashMap objectNew = new HashMap();
        objectNew.put("from_lat", SharedPrefManager.getPickUpLat(Constrants.PickUpLat));
        objectNew.put("from_lon",SharedPrefManager.getPickUpLong(Constrants.PickUpLong));
        objectNew.put("to_lat",SharedPrefManager.getDropLat(Constrants.DropLat));
        objectNew.put("to_lon",SharedPrefManager.getDropLong(Constrants.DropLong));
        objectNew.put("city_name","jaipur");
        objectNew.put("vehicle_type_id",id);
        new WebTask(mContext, WebUrls.BASE_URL+WebUrls.GetLocalRideEstimatedFair,objectNew, this, RequestCode.CODE_GetLocalRideEstimatedFair,5);
    }
    @Override
    public void onComplete(String response, int taskcode) {

        if (RequestCode.CODE_GetLocalRideEstimatedFair==taskcode){
            GetEstimatedFairResponse estimatedFairResponse = JsonDeserializer.deserializeJson(response,GetEstimatedFairResponse.class);

            if (estimatedFairResponse.statusCode==1) {
                HomeFrag.getInstance().estimatShow(estimatedFairResponse.data.totalEstimateFair.toString());
            }
        }
    }
}
