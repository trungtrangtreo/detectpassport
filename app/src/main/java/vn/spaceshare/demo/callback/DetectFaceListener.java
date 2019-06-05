package vn.spaceshare.demo.callback;

import java.io.File;

public interface DetectFaceListener {

    void onSuccess();

    void onTakePassport(File fileFace);
}
