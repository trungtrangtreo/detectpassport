package vn.spaceshare.demo.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PassportInfo implements Parcelable {



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

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDateOfExpiry() {
        return dateOfExpiry;
    }

    public void setDateOfExpiry(String dateOfExpiry) {
        this.dateOfExpiry = dateOfExpiry;
    }

    public String getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(String dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.dateOfBirth);
        dest.writeString(this.dateOfExpiry);
        dest.writeString(this.dateOfIssue);
        dest.writeString(this.fullName);
        dest.writeString(this.nationality);
        dest.writeString(this.passportNumber);
        dest.writeString(this.placeOfBirth);
        dest.writeString(this.sex);
    }

    public PassportInfo() {
    }

    protected PassportInfo(Parcel in) {
        this.dateOfBirth = in.readString();
        this.dateOfExpiry = in.readString();
        this.dateOfIssue = in.readString();
        this.fullName = in.readString();
        this.nationality = in.readString();
        this.passportNumber = in.readString();
        this.placeOfBirth = in.readString();
        this.sex = in.readString();
    }

    public static final Parcelable.Creator<PassportInfo> CREATOR = new Parcelable.Creator<PassportInfo>() {
        @Override
        public PassportInfo createFromParcel(Parcel source) {
            return new PassportInfo(source);
        }

        @Override
        public PassportInfo[] newArray(int size) {
            return new PassportInfo[size];
        }
    };
}
