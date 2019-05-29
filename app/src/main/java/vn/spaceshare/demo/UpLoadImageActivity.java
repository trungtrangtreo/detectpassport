package vn.spaceshare.demo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import vn.spaceshare.demo.util.KeyIntent;

import java.io.File;
import java.io.IOException;

public class UpLoadImageActivity extends AppCompatActivity {

    private String path = "";
    private ImageView ivImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_load_image);

        ivImage = findViewById(R.id.ivImage);
        if (getIntent() != null) {
            path = getIntent().getStringExtra(KeyIntent.KEY_PATH);
            File imgFile = new File(path);
            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            ivImage.setImageBitmap(bitmap);
//            ivImage.animate().rotation(90).start();

        }
    }

    private static int exifToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

}
