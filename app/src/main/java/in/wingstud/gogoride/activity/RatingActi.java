package in.wingstud.gogoride.activity;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import in.wingstud.gogoride.R;
import in.wingstud.gogoride.databinding.ActivityRatingBinding;
import in.wingstud.gogoride.util.Utils;

public class RatingActi extends AppCompatActivity {

    private Toolbar toolbar;

    private Context mContext;

    private ActivityRatingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_rating);

        mContext = RatingActi.this;
        initialize();
    }

    private void initialize() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.startActivityWithFinish(mContext, Dashboard.class);
            }
        });
    }

    public void rePlaneProcess(View view){
        Utils.startActivityWithFinish(mContext, Dashboard.class);
    }
}
