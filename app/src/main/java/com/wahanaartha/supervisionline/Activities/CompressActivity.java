package com.wahanaartha.supervisionline.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.wahanaartha.supervisionline.R;

import java.io.Closeable;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CompressActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = getClass().getSimpleName();
    private static final int PICK_CAMERA_IMAGE = 2;
//    private static final int PICK_CAMERA_IMAGE = 2;

    public static final String DATE_FORMAT = "yyyMMdd_HHmmss";
    public static final String IMAGE_DIRECTORY = "ImageScalling";

    public static final String SCHEME_FILE = "file";
    public static final String SCHEME_CONTENT = "content";

    private Button btnCamera;
    private Button btnCompress;

    private ImageView img;
    private ImageView imgCompress;

    private Uri imageCaptureUri;

    private File file;
    private File sourceFile;
    private File destFile;

    private SimpleDateFormat dateFormat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compress);

        file = new File(Environment.getExternalStorageDirectory() + "/" + IMAGE_DIRECTORY);
        if(!file.exists()){
            file.mkdir();
        }

        dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.US);

        initView();
    }

    public void initView(){
        btnCamera = (Button) findViewById(R.id.btn_camera);
        btnCompress = (Button) findViewById(R.id.btn_compress);
        img = (ImageView) findViewById(R.id.activity_main_img);
        imgCompress = (ImageView) findViewById(R.id.activity_main_img_compress);

        btnCamera.setOnClickListener(this);
        btnCompress.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_camera:
                destFile = new File(file, "img_" + dateFormat.format(new Date()).toString() +".png");
                imageCaptureUri = Uri.fromFile(destFile);

                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, imageCaptureUri);
                startActivityForResult(intentCamera, PICK_CAMERA_IMAGE);
                break;
            case R.id.btn_compress:
                Bitmap bmp = decodeFile(destFile);
                imgCompress.setImageBitmap(bmp);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK){
            switch (requestCode){
                case PICK_CAMERA_IMAGE:
                    Log.d(TAG, ".PICK_CAMERA_IMAGE" +imageCaptureUri);
                    img.setImageURI(imageCaptureUri);
                    break;
            }
        }
    }

    private Bitmap  decodeFile(File f){
        Bitmap b = null;
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;

        FileInputStream fis = null;
        try{
            fis = new FileInputStream(f);
            BitmapFactory.decodeStream(fis, null, o);
            fis.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

        int IMAGE_MAX_SIZE = 1024;
        int scale = 1;
        if(o.outHeight > IMAGE_MAX_SIZE || o.outWidth > IMAGE_MAX_SIZE){
            scale = (int) Math.pow(2, (int) Math.ceil(Math.log(IMAGE_MAX_SIZE / (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
        }

        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        try{
            fis = new FileInputStream(f);
            b = BitmapFactory.decodeStream(fis, null, o2);
            fis.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

        Log.d(TAG, "Width :" + b.getWidth() + "Height :" + b.getHeight());
        destFile = new File(file, "img_" +dateFormat.format(new Date()).toString() + ".png");
        try{
            FileOutputStream out = new FileOutputStream(destFile);
            b.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e){
            e.printStackTrace();
        }

        return  b;
    }

    public String getPathFromUri(Uri uriPhoto){
        if(uriPhoto == null)
            return null;

        if(SCHEME_FILE.equals(uriPhoto.getScheme())){
            return uriPhoto.getPath();
        }else if(SCHEME_CONTENT.equals(uriPhoto.getScheme())){
            final String[] filePathColumn = {MediaStore.MediaColumns.DATA,MediaStore.MediaColumns.DISPLAY_NAME};
            Cursor cursor = null;
            try{
                cursor = getContentResolver().query(uriPhoto, filePathColumn, null, null, null, null);
                if(cursor != null && cursor.moveToFirst()){
                    final int columnIndex = (uriPhoto.toString().startsWith("content://com.google.android.gallery3d")) ? cursor.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME): cursor.getColumnIndex(MediaStore.MediaColumns.DATA);

                    if(columnIndex != -1){
                        String filePath = cursor.getString(columnIndex);
                        if(!TextUtils.isEmpty(filePath)){
                            return filePath;
                        }
                    }
                }
            }catch(IllegalArgumentException e){
                Log.d(TAG, "IllegalArgumentException");
                e.printStackTrace();
            } catch (SecurityException ignored){
                Log.d(TAG, "SecurityException");

                ignored.printStackTrace();
            } finally {
                if(cursor != null)
                    cursor.close();
            }
        }
        return null;
    }

    public String getPathfromGooglePhotosUri(Uri uriPhoto){
        if(uriPhoto == null)
            return null;

        FileInputStream input = null;
        FileOutputStream output = null;
        try{
            ParcelFileDescriptor pfd = getContentResolver().openFileDescriptor(uriPhoto, "r");
            FileDescriptor fd = pfd.getFileDescriptor();
            input = new FileInputStream(fd);

            String tempFileName = getTempFileName(this);
            output = new FileOutputStream(tempFileName);

            int read;
            byte[] bytes = new byte[4096];
            while ((read = input.read(bytes)) != -1){
                output.write(bytes, 0, read);
            }
            return tempFileName;
        } catch (IOException ignored){

        } finally {
            closeSilently(input);
            closeSilently(output);
        }
        return null;
    }

    public static void closeSilently(Closeable c){
        if(c == null)
            return;
        try{
            c.close();
        } catch (Throwable t){

        }
    }

    private static String getTempFileName(Context context) throws IOException{
        File outputDir = context.getCacheDir();
        File outputFile = File.createTempFile("image", "tmp", outputDir);
        return outputFile.getAbsolutePath();
    }

    private void copyFile(File sourceFile, File destFile) throws IOException{
        if(!sourceFile.exists()){
            return;
        }

        FileChannel source = null;
        FileChannel destination = null;
        source = new FileInputStream(sourceFile).getChannel();
        destination = new FileOutputStream(destFile).getChannel();

        if(destination != null && source != null){
            destination.transferFrom(source, 0, source.size());
        }

        if(source != null){
            source.close();
        }

        if(destination != null){
            destination.close();
        }
    }
}

