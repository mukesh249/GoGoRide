package in.wingstud.gogoride.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.databinding.ObservableField;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Observable;

import in.wingstud.gogoride.activity.Dashboard;
import in.wingstud.gogoride.api.Constrants;
import in.wingstud.gogoride.api.JsonDeserializer;
import in.wingstud.gogoride.api.RequestCode;
import in.wingstud.gogoride.api.SharedPrefManager;
import in.wingstud.gogoride.api.WebCompleteTask;
import in.wingstud.gogoride.api.WebTask;
import in.wingstud.gogoride.api.WebUrls;
import in.wingstud.gogoride.model.LoginModelResponcse;
import in.wingstud.gogoride.util.Utils;

public class LoginViewModel extends Observable implements WebCompleteTask {

    private Context context;
    public final ObservableField<String> mobileno = new ObservableField<>("");
    public final ObservableField<String> password = new ObservableField<>("");

    public LoginViewModel(Context context) {
        this.context = context;
    }

    public void LoginRequest(){
        HashMap objectNew = new HashMap();
        objectNew.put("mobile",mobileno.get());
        objectNew.put("password",password.get());
        Log.i("Login_obj",objectNew+"");
        new WebTask(context, WebUrls.BASE_URL+WebUrls.LoginApi
                , objectNew,LoginViewModel.this, RequestCode.CODE_Login,1);
    }

    @Override
    public void onComplete(String response, int taskcode) {
        if (RequestCode.CODE_Login == taskcode) {
            Log.i("Login_res", response);

            LoginModelResponcse modelResponcse = JsonDeserializer.deserializeJson(response,LoginModelResponcse.class);
            if (modelResponcse.statusCode == 1) {
                SharedPrefManager.setLogin(Constrants.IsLogin,true);
                SharedPrefManager.setUserName(Constrants.UserName, modelResponcse.data.username);
                SharedPrefManager.setUserID(Constrants.UserId, modelResponcse.data.id.toString());
                SharedPrefManager.setUserEmail(Constrants.UserEmail,modelResponcse.data.email);
                SharedPrefManager.setUserImageLink(Constrants.UserPicPath,modelResponcse.userImagePath);
//                                SharedPrefManager.setUserEmail(Constrants.UserEmail,dataArray.getJSONObject(0).optString("email" ));
                SharedPrefManager.setUserMobile(Constrants.UserMobile,modelResponcse.data.mobile);
                SharedPrefManager.setUserPic(Constrants.UserPic, modelResponcse.data.userKyc.profileImage);
                SharedPrefManager.setUserFirstName(Constrants.UserFirstName, modelResponcse.data.fName);
                SharedPrefManager.setUserLastName(Constrants.UserLastName, modelResponcse.data.lName);
                context.startActivity(new Intent(context,Dashboard.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK));

//                context.finish();
//                context.startActivity(new Intent(context, Dashboard.class)
//                        .putExtra("activity","login")
//                        .putExtra("response",response));
            } else {
                Utils.Toast(context,modelResponcse.errorMessage);
            }
        }
    }
}
