package com.example.valentin.cartoonfilter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.valentin.cartoonfilter.CustomViews.SlideMenu;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jp.co.cyberagent.android.gpuimage.GPUImageView;

public class MainActivity extends AppCompatActivity {

    Bitmap mBitmap;
    Toast mToast;
    SlideMenu menu;
    GPUImageView mainView;
    File tmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainView = (GPUImageView) findViewById(R.id.mainView);
        menu = new SlideMenu(this, mainView);
        menu.generateBaseMenu();
        takePicture();
    }

    public void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            tmp = createImageFile("tmp");
        } catch (IOException e) {
            e.printStackTrace();
        }
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tmp));
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, 1);
        }
    }

    private File createImageFile(String type) throws IOException {

        String imageFileName;
        if (type != "tmp") {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            imageFileName = "JPEG_" + timeStamp + ".jpg";
        } else
            imageFileName = "tmp.jpg";

        String root = Environment.getExternalStorageDirectory().toString() + "/CameraFilter/";

        File storageDir = new File(root);
        try {
            if (storageDir.mkdir() || storageDir.isDirectory()) {
                storageDir = new File(storageDir, imageFileName);
                if (type != "tmp") {
                    mToast = Toast.makeText(getApplicationContext(), imageFileName + " saved", Toast.LENGTH_LONG);
                    mToast.show();
                }
            } else {
                throw new Exception("Unable to create new file");
            }
        } catch (Exception e) {
            Toast toast = Toast.makeText(getApplicationContext(), "Pas assez d'espace sur votre carte SD", Toast.LENGTH_LONG);
            toast.show();
            e.printStackTrace();
        }
        if (type != "tmp") {
            try {
                FileOutputStream out = new FileOutputStream(storageDir);
                mBitmap = mainView.capture();
                mBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
                out.flush();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return storageDir;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap photo = BitmapFactory.decodeFile(tmp.getPath(), options);
        if (photo != null) {
            mBitmap = photo;
            tmp.delete();
        } else {
            Context context = getApplicationContext();
            CharSequence text = "Record picture failed !";
            int duration = Toast.LENGTH_SHORT;
            mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.not_found);
            mToast = Toast.makeText(context, text, duration);
            mToast.show();
        }
        mainView.setImage(mBitmap);
        mainView.requestRender();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save) {
            try {
                createImageFile(null);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
