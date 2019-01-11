package ams.android_base;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import it.sephiroth.android.library.imagezoom.ImageViewTouch;

public abstract class ZoomImageActivity extends AppCompatActivity {

    protected ImageViewTouch imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        renderImageView();
    }

    private void initView() {
        setContentView(R.layout.activity_zoom_image);
        if(getSupportActionBar() != null) getSupportActionBar().hide();
        // Remove activity title
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //noinspection deprecation,RedundantCast
        imageView = (ImageViewTouch) findViewById(R.id.imageView);
    }

    protected abstract void renderImageView ();

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }


    // This is how to send image to view/zoom image page
    // Bitmap will be cached for 10 seconds only
//    public void sendImageToViewPage (Bitmap bitmap) {
//        new CacheManger(this).cacheBitmapTemporary(GlobalKeys.TEMP_BITMAP, bitmap, 10);
//        Intent intent = new Intent(this, ZoomImageActivity.class);
//        startActivity(intent);
//    }

}
