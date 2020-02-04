package in.wingstud.gogoride.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetEstimatedFairResponse {

    public class Data {

        @SerializedName("totalEstimateFair")
        @Expose
        public Integer totalEstimateFair;
        @SerializedName("estimatedKm")
        @Expose
        public Integer estimatedKm;
        @SerializedName("per_km_charge")
        @Expose
        public Integer perKmCharge;

    }
        @SerializedName("status_code")
        @Expose
        public Integer statusCode;
        @SerializedName("message")
        @Expose
        public String message;
        @SerializedName("data")
        @Expose
        public Data data;

}
