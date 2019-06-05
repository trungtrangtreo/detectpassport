package vn.spaceshare.demo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.steelkiwi.cropiwa.CropIwaView;
import com.steelkiwi.cropiwa.config.CropIwaSaveConfig;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import vn.spaceshare.demo.api.ApiClient;
import vn.spaceshare.demo.api.ApiService;
import vn.spaceshare.demo.model.PassportVerify;
import vn.spaceshare.demo.util.Const;
import vn.spaceshare.demo.util.KeyIntent;

import java.io.File;
import java.io.IOException;

public class UpLoadImageActivity extends AppCompatActivity {

    private String path = "";
    private SubsamplingScaleImageView ivImage;
    private Button btnUpload;
    private ProgressBar progressBar;
    private LinearLayout llProgressbar;
    private Rect rect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_load_image);

        ivImage = findViewById(R.id.ivImage);
        btnUpload = findViewById(R.id.btnUpload);
        progressBar = findViewById(R.id.progressBar);
        llProgressbar = findViewById(R.id.llProgressbar);

        if (getIntent() != null) {
            path = getIntent().getStringExtra(KeyIntent.KEY_PATH);
            rect = getIntent().getParcelableExtra(KeyIntent.KEY_RECT);
            File file = new File(path);

            ivImage.setImage(ImageSource.bitmap(exifToDegrees(path)));

            btnUpload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    verifyPassport("http://27.72.88.246:5000/passport", file);
                }
            });


        }
    }

    private Bitmap exifToDegrees(String photoPath) {
        ExifInterface ei = null;
        try {
            ei = new ExifInterface(photoPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_UNDEFINED);

        Bitmap rotatedBitmap;
        Bitmap bitmap = BitmapFactory.decodeFile(photoPath);

        switch (orientation) {

            case ExifInterface.ORIENTATION_ROTATE_90:
                rotatedBitmap = rotateImage(bitmap, 90);
                break;

            case ExifInterface.ORIENTATION_ROTATE_180:
                rotatedBitmap = rotateImage(bitmap, 180);
                break;

            case ExifInterface.ORIENTATION_ROTATE_270:
                rotatedBitmap = rotateImage(bitmap, 270);
                break;

            case ExifInterface.ORIENTATION_NORMAL:
                rotatedBitmap = rotateImage(bitmap, 90);
                break;

            case ExifInterface.ORIENTATION_UNDEFINED:
                rotatedBitmap = rotateImage(bitmap, 90);
                break;
            default:
                rotatedBitmap = bitmap;
        }
        return rotatedBitmap;
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }


    private void verifyPassport(String url, File passport) {
        btnUpload.setEnabled(false);
        llProgressbar.setVisibility(View.VISIBLE);
        ApiService apiService = ApiClient.getClient(getApplicationContext())
                .create(ApiService.class);

        //Create MultipartBody Passport
        RequestBody fileReqBodyPassport = RequestBody.create(MediaType.parse(Const.FORMAT_IMAGE), passport);
        MultipartBody.Part passportImage = MultipartBody.Part.createFormData("passport", passport.getName(), fileReqBodyPassport);

//       Create MultipartBody Portrait
//
//        RequestBody fileReqBodyPortrait = RequestBody.create(MediaType.parse(Const.FORMAT_IMAGE), portrait);
//        MultipartBody.Part portraitImage = MultipartBody.Part.createFormData("portrait_image", portrait.getName(), fileReqBodyPortrait);
        apiService.verifyPassport(url, passportImage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<PassportVerify>() {
                    @Override
                    public void onSuccess(PassportVerify passportVerify) {
                        if (passportVerify.getInfoPassportV2() != null) {
                            Intent intent = new Intent(getApplicationContext(), PassportInfoActivity.class);
                            intent.putExtra(Const.PASSPORT_INFO, passportVerify.getInfoPassportV2());
                            intent.putExtra(KeyIntent.KEY_PATH, path);
                            startActivity(intent);
                        } else {
                            Toast.makeText(UpLoadImageActivity.this, "Not get passport info. Please try again", Toast.LENGTH_SHORT).show();
                        }
                        llProgressbar.setVisibility(View.GONE);
                        btnUpload.setEnabled(true);

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(UpLoadImageActivity.this, "Not get passport info. Please try again", Toast.LENGTH_SHORT).show();
                        llProgressbar.setVisibility(View.GONE);
                        btnUpload.setEnabled(true);
                    }
                });
    }

    final int PIC_CROP = 1;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PIC_CROP) {
            if (data != null) {
                // get the returned data
                Bundle extras = data.getExtras();
                // get the cropped bitmap
                Bitmap selectedBitmap = extras.getParcelable("data");
            }
        }
    }
}
