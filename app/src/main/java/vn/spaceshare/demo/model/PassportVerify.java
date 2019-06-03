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
    @SerializedName("info_passport")
    PassportInfo infoPassportV2;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getFaceMatching() {
        return faceMatching;
    }

    public void setFaceMatching(Boolean faceMatching) {
        this.faceMatching = faceMatching;
    }

    public PassportInfo getInfoPassportV2() {
        return infoPassportV2;
    }

    public void setInfoPassportV2(PassportInfo infoPassportV2) {
        this.infoPassportV2 = infoPassportV2;
    }
}
