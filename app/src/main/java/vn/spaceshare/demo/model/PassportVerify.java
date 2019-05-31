package vn.spaceshare.demo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PassportVerify {

    @Expose
    @SerializedName("code")
    int code = 0;

    @Expose
    @SerializedName("message")
    String message = "";

    @Expose
    @SerializedName("face_matching")
    Boolean faceMatching = false;

    /*@Expose
    @SerializedName("info_passport")
    val infoPassport: PassportInfo? = null*/

    @Expose
    @SerializedName("info_passport_v2")
    PassportInfo infoPassportV2;

}
