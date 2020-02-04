package in.wingstud.gogoride.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import in.wingstud.gogoride.R;
import in.wingstud.gogoride.databinding.ActivityRegisterBinding;
import in.wingstud.gogoride.presenter.SignUpPresenter;
import in.wingstud.gogoride.util.Utils;
import in.wingstud.gogoride.viewmodel.SignUpViewModel;

public class RegisterActi extends AppCompatActivity {

    private Toolbar toolbar;

    private Context mContext;

    private ActivityRegisterBinding binding;
    private SignUpViewModel signUpViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);

        mContext = RegisterActi.this;
        initialize();
    }

    private void initialize() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        signUpViewModel = new SignUpViewModel(this);
        binding.setRegisterViewModel(signUpViewModel);
        binding.setRegisterPersenter(new SignUpPresenter() {
            @Override
            public void SignupData() {
                if (binding.etFirstName.getText().toString().isEmpty()) {
                    binding.etFirstName.setError("Please Enter Your First Name");
                    binding.etFirstName.requestFocus();
                } else if (binding.etLastName.getText().toString().isEmpty()) {
                    binding.etLastName.setError("Please Enter Your Last Name");
                    binding.etLastName.requestFocus();
                } else if (binding.etEmail.getText().toString().isEmpty()) {
                    binding.etEmail.setError("Please Enter Your Email");
                    binding.etEmail.requestFocus();
                } else if (binding.etMobileNo.getText().toString().isEmpty()) {
                    binding.etMobileNo.setError("Please Enter Mobile Number");
                    binding.etMobileNo.requestFocus();
                } else {
                    signUpViewModel.SignUpRequest();
                }
//                final String f_name = binding.etFirstName.getText().toString();
//                final String l_name = binding.etLastName.getText().toString();
//                final String email = binding.etEmail.getText().toString();
//                final String mobile = binding.etMobileNo.getText().toString();
//                final String pasword = binding.etPassword.getText().toString();

            }
        });

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    public void signUpProcess(View view) {
        Utils.startActivity(mContext, Dashboard.class);
    }

    public void goToLoginScreen(View view) {
        finish();
    }
}
