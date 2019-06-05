package vn.spaceshare.demo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FaceInfo {


    private String code;

    @Expose
    @SerializedName("face_matching")
    private Boolean faceMatching = false;

    private String message;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getFaceMatching() {
        return faceMatching;
    }

    public void setFaceMatching(Boolean faceMatching) {
        this.faceMatching = faceMatching;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
