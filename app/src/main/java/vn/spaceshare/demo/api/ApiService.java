package vn.spaceshare.demo.api;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import retrofit2.http.*;
import vn.spaceshare.demo.model.PassportVerify;

public interface ApiService {


    @Multipart
    @POST
    Single<PassportVerify> verifyPassport(@Url String url, @Part MultipartBody.Part passport_image);

}
