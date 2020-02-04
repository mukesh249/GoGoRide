package in.wingstud.gogoride.adapter;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import in.wingstud.gogoride.R;
import in.wingstud.gogoride.databinding.NavMenuItemBinding;


/**
 * Created by Nss Solutions on 17-11-2016.
 */

public class NavMenuAdapter extends RecyclerView.Adapter<NavMenuAdapter.DrawerViewHolder> {

    private String[] drawerMenuList;
    private Context mContext;

    public NavMenuAdapter(Context mContext, String[] drawerMenuList) {
        this.mContext = mContext;
        this.drawerMenuList = drawerMenuList;
    }

    @Override
    public DrawerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        NavMenuItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.nav_menu_item, parent, false);
        return new DrawerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(DrawerViewHolder holder, int position) {
        String menuNmae = drawerMenuList[position];
        holder.binding.txtNavItemTitle.setText(menuNmae);
        if (menuNmae.equalsIgnoreCase(mContext.getResources().getString(R.string.home)))
            holder.binding.imvNavItemIcon.setImageResource(R.drawable.ic_home);
        else if (menuNmae.equalsIgnoreCase(mContext.getResources().getString(R.string.book_your_ride)))
            holder.binding.imvNavItemIcon.setImageResource(R.drawable.ic_car);
        else if (menuNmae.equalsIgnoreCase(mContext.getResources().getString(R.string.rides_history)))
            holder.binding.imvNavItemIcon.setImageResource(R.drawable.ic_ride_history);
        else if (menuNmae.equalsIgnoreCase(mContext.getResources().getString(R.string.wallet)))
            holder.binding.imvNavItemIcon.setImageResource(R.drawable.ic_wallet);
        else if (menuNmae.equalsIgnoreCase(mContext.getResources().getString(R.string.support)))
            holder.binding.imvNavItemIcon.setImageResource(R.drawable.ic_support);
        else if (menuNmae.equalsIgnoreCase(mContext.getResources().getString(R.string.about_us)))
            holder.binding.imvNavItemIcon.setImageResource(R.drawable.ic_about_us);
        else if (menuNmae.equalsIgnoreCase(mContext.getResources().getString(R.string.terms)))
            holder.binding.imvNavItemIcon.setImageResource(R.drawable.ic_terms_condition);
        else if (menuNmae.equalsIgnoreCase(mContext.getResources().getString(R.string.privacy_policy)))
            holder.binding.imvNavItemIcon.setImageResource(R.drawable.ic_privacy);

    }

    @Override
    public int getItemCount() {
        return drawerMenuList.length;
    }

    class DrawerViewHolder extends RecyclerView.ViewHolder {

        private NavMenuItemBinding binding;

        public DrawerViewHolder(final NavMenuItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
