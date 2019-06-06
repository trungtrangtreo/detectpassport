package vn.spaceshare.demo;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import vn.spaceshare.demo.api.ApiClient;
import vn.spaceshare.demo.api.ApiService;
import vn.spaceshare.demo.model.FaceInfo;
import vn.spaceshare.demo.util.Const;
import vn.spaceshare.demo.util.KeyIntent;

import java.io.File;
import java.io.IOException;

public class CompareActivity extends AppCompatActivity {


    private ImageView ivImage;
    private Button btnCompare;
    private ProgressBar progressBar;
    private LinearLayout llProgressbar;
    private String pathPassport, pathFace = "";
    private String url = "http://27.72.88.246:5000/face";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);

        initView();

        if (getIntent() != null) {
            pathPassport = getIntent().getStringExtra(KeyIntent.KEY_PATH);
            pathFace = getIntent().getStringExtra(KeyIntent.KEY_PATH_FACE);
            ivImage.setImageBitmap(exifToDegrees(pathFace));
        }


        btnCompare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pathFace != null && pathPassport != null) {
                    compare(url, new File(pathPassport), new File(pathFace));
                }
            }
        });
    }

    private void initView() {

        ivImage = findViewById(R.id.ivImage);
        btnCompare = findViewById(R.id.btnCompare);
        progressBar = findViewById(R.id.progressBar);
        llProgressbar = findViewById(R.id.llProgressbar);
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

    private void compare(String url, File passport, File face) {

        llProgressbar.setVisibility(View.VISIBLE);
        ApiService apiService = ApiClient.getClient(getApplicationContext())
                .create(ApiService.class);

        //Create MultipartBody Passport
        RequestBody fileReqBodyPassport = RequestBody.create(MediaType.parse(Const.FORMAT_IMAGE), passport);
        MultipartBody.Part passportImage = MultipartBody.Part.createFormData("passport", passport.getName(), fileReqBodyPassport);

//       Create MultipartBody Portrait
//
        RequestBody fileReqBodyPortrait = RequestBody.create(MediaType.parse(Const.FORMAT_IMAGE), face);
        MultipartBody.Part portraitImage = MultipartBody.Part.createFormData("portrait", face.getName(), fileReqBodyPortrait);
        apiService.comparePassportAndFace(url, passportImage, portraitImage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<FaceInfo>() {
                    @Override
                    public void onSuccess(FaceInfo faceInfo) {
                        llProgressbar.setVisibility(View.GONE);
                        if (faceInfo.getCode().equals("200")) {
                            if (faceInfo.getFaceMatching() != null) {
                                if (faceInfo.getFaceMatching()) {
                                    showDialog("Matching passport and face");
                                } else {
                                    showDialog("Not matching passport and face");
                                }

                            } else {
                                showDialog(faceInfo.getMessage());
                            }
                        } else {
                            showDialog(faceInfo.getMessage());
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(CompareActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        llProgressbar.setVisibility(View.GONE);

                    }
                });
    }

    private void showDialog(String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage(message)
                .setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialoginterface, int i) {

                    }
                }).show();
    }
}
