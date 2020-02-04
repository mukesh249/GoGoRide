package in.wingstud.gogoride.fragment;


import android.content.Context;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import in.wingstud.gogoride.R;
import in.wingstud.gogoride.activity.Dashboard;
import in.wingstud.gogoride.databinding.FragmentProfileBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFrag extends Fragment {

    private View view;

    private Context mContext;

    private FragmentProfileBinding binding;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        view = binding.getRoot();

        initialize();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
//        ((Dashboard) getContext()).setTitle(mContext.getString(R.string.profile), false);
    }

    private void initialize() {

    }

}
