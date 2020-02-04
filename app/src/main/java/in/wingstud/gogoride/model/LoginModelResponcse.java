package in.wingstud.gogoride.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginModelResponcse {


    public class Data {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("partner_id")
        @Expose
        public String partnerId;
        @SerializedName("username")
        @Expose
        public String username;
        @SerializedName("reg_no")
        @Expose
        public String regNo;
        @SerializedName("f_name")
        @Expose
        public String fName;
        @SerializedName("l_name")
        @Expose
        public String lName;
        @SerializedName("email")
        @Expose
        public String email;
        @SerializedName("password")
        @Expose
        public String password;
        @SerializedName("simple_pass")
        @Expose
        public String simplePass;
        @SerializedName("otp")
        @Expose
        public String otp;
        @SerializedName("mobile")
        @Expose
        public String mobile;
        @SerializedName("contact_details")
        @Expose
        public String contactDetails;
        @SerializedName("is_email_varifried")
        @Expose
        public String isEmailVarifried;
        @SerializedName("is_otp_varified")
        @Expose
        public Integer isOtpVarified;
        @SerializedName("role_id")
        @Expose
        public Integer roleId;
        @SerializedName("activated")
        @Expose
        public Integer activated;
        @SerializedName("banned")
        @Expose
        public Integer banned;
        @SerializedName("driver_status")
        @Expose
        public String driverStatus;
        @SerializedName("verify_status")
        @Expose
        public String verifyStatus;
        @SerializedName("new_password_key")
        @Expose
        public String newPasswordKey;
        @SerializedName("reset_password_token")
        @Expose
        public String resetPasswordToken;
        @SerializedName("new_email")
        @Expose
        public String newEmail;
        @SerializedName("new_email_key")
        @Expose
        public String newEmailKey;
        @SerializedName("device_token")
        @Expose
        public String deviceToken;
        @SerializedName("device_type")
        @Expose
        public String deviceType;
        @SerializedName("device_id")
        @Expose
        public String deviceId;
        @SerializedName("ip_address")
        @Expose
        public String ipAddress;
        @SerializedName("last_login")
        @Expose
        public String lastLogin;
        @SerializedName("last_ip")
        @Expose
        public String lastIp;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;
        @SerializedName("is_deleted")
        @Expose
        public Integer isDeleted;
        @SerializedName("new_password_requested")
        @Expose
        public String newPasswordRequested;
        @SerializedName("is_login")
        @Expose
        public String isLogin;
        @SerializedName("is_login_first")
        @Expose
        public Integer isLoginFirst;
        @SerializedName("user_kyc")
        @Expose
        public UserKyc userKyc;

    }
        @SerializedName("status_code")
        @Expose
        public Integer statusCode;
        @SerializedName("message")
        @Expose
        public String message;
        @SerializedName("error_message")
        @Expose
        public String errorMessage;
        @SerializedName("user_image_path")
        @Expose
        public String userImagePath;
        @SerializedName("data")
        @Expose
        public Data data;

    public class UserKyc {

        @SerializedName("id")
        @Expose
        public Integer id;
        @SerializedName("user_id")
        @Expose
        public Integer userId;
        @SerializedName("state_id")
        @Expose
        public String stateId;
        @SerializedName("country_id")
        @Expose
        public String countryId;
        @SerializedName("city_id")
        @Expose
        public String cityId;
        @SerializedName("address")
        @Expose
        public String address;
        @SerializedName("pincode")
        @Expose
        public String pincode;
        @SerializedName("gender")
        @Expose
        public String gender;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;
        @SerializedName("is_delete")
        @Expose
        public String isDelete;
        @SerializedName("profile_image")
        @Expose
        public String profileImage;
        @SerializedName("pan_card_img")
        @Expose
        public String panCardImg;
        @SerializedName("aadhar_card_img")
        @Expose
        public String aadharCardImg;
        @SerializedName("check_img")
        @Expose
        public String checkImg;
        @SerializedName("tc")
        @Expose
        public Integer tc;
        @SerializedName("dob")
        @Expose
        public String dob;
        @SerializedName("payment_method")
        @Expose
        public String paymentMethod;
        @SerializedName("aadhar")
        @Expose
        public String aadhar;
        @SerializedName("dl_no")
        @Expose
        public String dlNo;
        @SerializedName("dl_image")
        @Expose
        public String dlImage;
        @SerializedName("dl_valid_up_to")
        @Expose
        public String dlValidUpTo;
        @SerializedName("lc_class")
        @Expose
        public String lcClass;
        @SerializedName("pan_no")
        @Expose
        public String panNo;
        @SerializedName("ac_no")
        @Expose
        public String acNo;
        @SerializedName("ifsc_code")
        @Expose
        public String ifscCode;
        @SerializedName("bank_name")
        @Expose
        public String bankName;
        @SerializedName("ah_name")
        @Expose
        public String ahName;

    }
}
