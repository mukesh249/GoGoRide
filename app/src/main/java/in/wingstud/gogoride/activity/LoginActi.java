package in.wingstud.gogoride.activity;

import android.content.Context;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import in.wingstud.gogoride.R;
import in.wingstud.gogoride.api.WebCompleteTask;
import in.wingstud.gogoride.api.WebTask;
import in.wingstud.gogoride.api.WebUrls;
import in.wingstud.gogoride.databinding.ActivityLoginBinding;
import in.wingstud.gogoride.util.Utils;
import in.wingstud.gogoride.viewmodel.LoginViewModel;

public class LoginActi extends AppCompatActivity {

    private Toolbar toolbar;
    private Context mContext;
    private ActivityLoginBinding binding;
    private String userName;
    LoginViewModel loginViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        mContext = LoginActi.this;
        loginViewModel = new LoginViewModel(this);
        binding.setLoginViewModel(loginViewModel);
        binding.setLoginPresenter(() -> {
            if (binding.etMobileNo.getText().toString().isEmpty()) {
                binding.etMobileNo.setError("Please Enter Mobile Number");
                binding.etMobileNo.requestFocus();
            } else if (binding.etPassword.getText().toString().isEmpty()){
                binding.etPassword.setError("Please Enter Password Number");
                binding.etPassword.requestFocus();
            }else {
                loginViewModel.LoginRequest();
            }
        });

    }


//    public void loginProcess(View view) {
//
//        new WebTask(this, WebUrls.BASE_URL+WebUrls.LoginApi,)
//        userName = binding.etMobileNo.getText().toString();
//
////        if (Utils.isValidMobileNumber(mContext, userName)){
//            Utils.startActivity(mContext, Dashboard.class);
////        }
//    }
//
    public void goToSignUpScreen(View view){
        Utils.startActivity(mContext, RegisterActi.class);
    }

}
