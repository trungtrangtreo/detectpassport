package vn.spaceshare.demo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PassportInfo {

    @Expose
    @SerializedName("date_of_birth")
    String dateOfBirth = "";

    @Expose
    @SerializedName("date_of_expiry")
    String dateOfExpiry = "";

    @Expose
    @SerializedName("date_of_issue")
    String dateOfIssue = "";

    @Expose
    @SerializedName("full_name")
    String fullName = "";

    @Expose
    @SerializedName("nationality")
    String nationality = "";

    @Expose
    @SerializedName("passport_number")
    String passportNumber = "";

    @Expose
    @SerializedName("place_of_birth")
    String placeOfBirth = "";

    @Expose
    @SerializedName("sex")
    String sex = "";

}
