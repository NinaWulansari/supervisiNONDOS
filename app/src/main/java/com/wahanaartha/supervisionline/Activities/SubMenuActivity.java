package com.wahanaartha.supervisionline.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wahanaartha.supervisionline.Base64;
import com.wahanaartha.supervisionline.Fragment.BerandaFragment;
import com.wahanaartha.supervisionline.Model.ListQuestionsModel;
import com.wahanaartha.supervisionline.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SubMenuActivity extends Activity {


    private String cekCam = null;
    private TextView txtSubmenu, txtHeadmenu;
    private RadioGroup radioGroup;
    private RadioButton optionYa;
    private RadioButton optionTidak;
    private List<ListQuestionsModel> parsedObject;
    private int currentRemarks = 0;
    private int remarkCount;
    private ListQuestionsModel firstRemark;

    Uri imageUri;
    private static final int PICK_IMAGE = 1;
    private static final int PICK_Camera_IMAGE = 2;
    private Bitmap bitmap;
    private ImageView imgView;
    private ProgressDialog dialog;

    private String value, type, typeDlr,nodlr;

    HttpPost httppost;
    StringBuffer buffer;
    HttpResponse response;
    HttpClient httpclient;
    List<NameValuePair> nameValuePairs;
    private RadioButton radioButton;
    InputStream is=null;
    String result=null;
    String line=null;

    private static final String SELECT_SQL = "SELECT no_dlr, latitude, longitude FROM supervisi";
    private SQLiteDatabase db;
    private Cursor c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.submenu_layout);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        prepareInitParameters();
        openDatabase();

        imgView = (ImageView) findViewById(R.id.ImageView);
        txtHeadmenu = (TextView) findViewById(R.id.textHeadmenu);
        txtSubmenu = (TextView) findViewById(R.id.textSubmenu);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
        optionYa = (RadioButton) findViewById(R.id.radioYa);
        optionTidak = (RadioButton) findViewById(R.id.radioTidak);
        Button backButton = (Button) findViewById(R.id.btnBack);
        Button nextButton = (Button) findViewById(R.id.btnNext);
        Button camButton = (Button) findViewById(R.id.btnCamera);

        camButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getCamera();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                currentRemarks=currentRemarks-1;
                firstRemark = parsedObject.get(currentRemarks);
//                txtHeadmenu.setText(firstRemark.getParent());
                txtSubmenu.setText(firstRemark.getTitle());
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(cekCam == null){
                    Toast.makeText(getApplicationContext(), "Gambar belum di pasang.",Toast.LENGTH_LONG).show();
                }else{
                    if (currentRemarks <= remarkCount) {

                        insertToDatabase(currentRemarks);

                        EditText editText = (EditText)findViewById(R.id.editTextDescripion);
                        editText.setText("", TextView.BufferType.EDITABLE);
                        ImageView img= (ImageView) findViewById(R.id.ImageView);
                        img.setImageResource(R.mipmap.logo_new_large);

                        currentRemarks=currentRemarks+1;
                        firstRemark = parsedObject.get(currentRemarks);
//                        txtHeadmenu.setText(firstRemark.getParent());
                        txtSubmenu.setText(firstRemark.getTitle());

                    } else {

                        insertToDatabase(currentRemarks);

                        final Intent intent = new Intent(SubMenuActivity.this, ResultActivity.class);
                        intent.putExtra("IdMenu",value);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
    }


    private void prepareInitParameters(){
        getInitParameters();
        checkInitParameters();
    }

    private void getInitParameters(){
        SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
        String value  = prefs.getString("id_kategori", null);
        String type = prefs.getString("type_supervisi", "");
        String typeDlr = prefs.getString("type_dealer", "");
        this.value = value;
        this.type = type;
        this.typeDlr = typeDlr;
    }

    private void checkInitParameters(){
        if (value==null){
            Toast.makeText(SubMenuActivity.this,"Menu belum dipilih", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SubMenuActivity.this, KategoriActivity.class));
        } else {
            getDetailData(value);
        }
    }

    void getDetailData(final String value2){
        class AsyncJsonObject extends AsyncTask<String, Void, String> {
            private ProgressDialog progressDialog;

            @Override
            protected String doInBackground(String... params) {

                System.out.println(value2);
                SharedPreferences prefs = getSharedPreferences("X", MODE_PRIVATE);
                String value  = prefs.getString("id_kategori", null);
                HttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());
                HttpPost httpPost = new HttpPost("http://192.168.0.79/html2/supervisi_online/showList.php?id="+value);
                String jsonResult = "";
                try {
                    HttpResponse response = httpClient.execute(httpPost);
                    jsonResult = inputStreamToString(response.getEntity().getContent()).toString();
                    System.out.println("Returned Json object " + jsonResult.toString());
                } catch (ClientProtocolException e) {

                    e.printStackTrace();
                } catch (IOException e) {

                    e.printStackTrace();
                }
                return jsonResult;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(SubMenuActivity.this, "Loading Detail", "Please Wait....", true);
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                progressDialog.dismiss();
                System.out.println("Resulted Value: " + result);
                parsedObject = returnParsedJsonObject(result);
                if (parsedObject == null) {
                    return;
                }
                remarkCount = parsedObject.size();
//                if(remarkCount == 0){
//                    Toast toast = Toast.makeText(getApplicationContext(), "Item menu kosong.", Toast.LENGTH_SHORT);
//                    toast.show();
//                    final Intent intent = new Intent(SubMenuActivity.this, KategoriActivity.class);
//                    startActivity(intent);
//                    finish();
//                }else{
                    remarkCount = remarkCount-2;
                    firstRemark = parsedObject.get(0);
//                    txtHeadmenu.setText(firstRemark.getParent());
                    txtSubmenu.setText(firstRemark.getTitle());
                    currentRemarks = 0;
//                }

            }

            private StringBuilder inputStreamToString(InputStream is) {
                String rLine = "";
                StringBuilder answer = new StringBuilder();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                try {
                    while ((rLine = br.readLine()) != null) {
                        answer.append(rLine);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return answer;
            }
        }

        AsyncJsonObject asyncObject = new AsyncJsonObject();
        asyncObject.execute("");
    }

    private List<ListQuestionsModel> returnParsedJsonObject(String result) {
        List<ListQuestionsModel> jsonObject = new ArrayList<ListQuestionsModel>();
        JSONObject resultObject = null;
        JSONArray jsonArray = null;
        ListQuestionsModel newItemObject = null;
        try {
            resultObject = new JSONObject(result);
            System.out.println("Testing the water " + resultObject.toString());
            jsonArray = resultObject.optJSONArray("result");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(jsonArray != null){
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonChildNode = null;
                try {
                    jsonChildNode = jsonArray.getJSONObject(i);
                    String id = jsonChildNode.getString("id");
                    String title = jsonChildNode.getString("title");
                    String parent = jsonChildNode.getString("parent");
                    newItemObject = new ListQuestionsModel(id, title);
                    jsonObject.add(newItemObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return jsonObject;
    }

    private void insertToDatabase(final int idx){

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
        final int selectedId = radioGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(selectedId);
        final String remark = (String) radioButton.getText();
        final String description = ((TextView) findViewById(R.id.editTextDescripion)).getText().toString();

        c = db.rawQuery(SELECT_SQL, null);
        c.moveToFirst();
        final String no_dlr = c.getString(0);
        final String latitude = c.getString(1);
        final String longitude = c.getString(2);

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                InputStream is;
                BitmapFactory.Options bfo;
                Bitmap bitmapOrg;
                ByteArrayOutputStream bao ;

                bfo = new BitmapFactory.Options();
                bfo.inSampleSize = 2;
                //bitmapOrg = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/" + customImage, bfo);

                bao = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bao);
                byte [] ba = bao.toByteArray();
                String ba1 = Base64.encodeBytes(ba);
                System.out.println("BA1 : "+ ba1);

                nameValuePairs = new ArrayList<NameValuePair>(8);
                nameValuePairs.add(new BasicNameValuePair("image",ba1));
                // Always use the same variable name for posting i.e the android side variable name and php side variable name should be similar,
                nameValuePairs.add(new BasicNameValuePair("mainitem",value));
                firstRemark = parsedObject.get(idx);
//                int itemx = firstRemark.getId();
//                nameValuePairs.add(new BasicNameValuePair("item",Integer.toString(itemx)));
                // find the radiobutton by returned id
                nameValuePairs.add(new BasicNameValuePair("remark",remark));
                nameValuePairs.add(new BasicNameValuePair("description",description));

                nameValuePairs.add(new BasicNameValuePair("noDlr",no_dlr));
                nameValuePairs.add(new BasicNameValuePair("latitude",latitude));
                nameValuePairs.add(new BasicNameValuePair("longitude",longitude));
                System.out.println("array post " + nameValuePairs);

                try {
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost("http://intranet.wahanaartha.com/supervisi/insertData.php");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();
                    is = entity.getContent();


                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                }
                return "success";
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            }
        }

        String indexs = String.valueOf(idx);
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(indexs);
    }

    //-- image
    public void getCamera(){
        //define the file-name to save photo taken by Camera activity
        String fileName = "new-photo-name.jpg";
        //create parameters for Intent with filename
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, fileName);
        values.put(MediaStore.Images.Media.DESCRIPTION,"Image captured by camera");
        //imageUri is the current activity attribute, define and save it for later usage (also in onSaveInstanceState)
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        //create new Intent
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        startActivityForResult(intent, PICK_Camera_IMAGE);

        Uri selectedImageUri = null;
        String filePath = null;

        selectedImageUri = imageUri;
        if(selectedImageUri != null){
            try {
                // OI FILE Manager
                String filemanagerstring = selectedImageUri.getPath();


                // MEDIA GALLERY
                String selectedImagePath = getPath(selectedImageUri);
                System.out.println(filemanagerstring + " : " + selectedImagePath);

                if (selectedImagePath != null) {
                    filePath = selectedImagePath;
                } else if (filemanagerstring != null) {
//					filePath = filemanagerstring;
                } else {
                    Toast.makeText(getApplicationContext(), "Unknown path",
                            Toast.LENGTH_LONG).show();
                    Log.e("Bitmap", "Unknown path");
                }

                if (filePath != null) {
                    decodeFile(filePath);
                    cekCam = "oke";
                } else {
                    bitmap = null;
                    System.out.println(2);
                }
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Internal error",
                        Toast.LENGTH_LONG).show();
                Log.e(e.getClass().getName(), e.getMessage(), e);
            }
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_image_gallery, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.camera:
                //define the file-name to save photo taken by Camera activity
                String fileName = "new-photo-name.jpg";
                //create parameters for Intent with filename
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, fileName);
                values.put(MediaStore.Images.Media.DESCRIPTION,"Image captured by camera");
                //imageUri is the current activity attribute, define and save it for later usage (also in onSaveInstanceState)
                imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                //create new Intent
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                startActivityForResult(intent, PICK_Camera_IMAGE);
                return true;
        }
        return false;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri selectedImageUri = null;
        String filePath = null;
        switch (requestCode) {
            case PICK_IMAGE:
                if (resultCode == Activity.RESULT_OK) {
                    selectedImageUri = data.getData();
                }
                break;
            case PICK_Camera_IMAGE:
                if (resultCode == RESULT_OK) {
                    //use imageUri here to access the image
                    selectedImageUri = imageUri;
		 		    	/*Bitmap mPic = (Bitmap) data.getExtras().get("data");
						selectedImageUri = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), mPic, getResources().getString(R.string.app_name), Long.toString(System.currentTimeMillis())));*/
                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "Picture was not taken", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Picture was not taken", Toast.LENGTH_SHORT).show();
                }
                break;
        }

        if(selectedImageUri != null){
            try {
                // OI FILE Manager
                String filemanagerstring = selectedImageUri.getPath();

                // MEDIA GALLERY
                String selectedImagePath = getPath(selectedImageUri);

                if (selectedImagePath != null) {
                    filePath = selectedImagePath;
                } else if (filemanagerstring != null) {
                    filePath = filemanagerstring;
                } else {
                    Toast.makeText(getApplicationContext(), "Unknown path",
                            Toast.LENGTH_LONG).show();
                    Log.e("Bitmap", "Unknown path");
                }

                if (filePath != null) {
                    decodeFile(filePath);
                } else {
                    bitmap = null;
                }
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Internal error",
                        Toast.LENGTH_LONG).show();
                Log.e(e.getClass().getName(), e.getMessage(), e);
            }
        }

    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

    public void decodeFile(String filePath) {
        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, o);

        // The new size we want to scale to
        final int REQUIRED_SIZE = 1024;

        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        bitmap = BitmapFactory.decodeFile(filePath, o2);

        imgView.setImageBitmap(bitmap);

    }

    protected void openDatabase() {
        db = openOrCreateDatabase("supervisiDB", Context.MODE_PRIVATE, null);
    }

    class ImageGalleryTask extends AsyncTask<Void, Void, String> {
        @SuppressWarnings("unused")
        @Override
        protected String doInBackground(Void... unused) {
            InputStream is;
            BitmapFactory.Options bfo;
            Bitmap bitmapOrg;
            ByteArrayOutputStream bao ;

            bfo = new BitmapFactory.Options();
            bfo.inSampleSize = 2;
            //bitmapOrg = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/" + customImage, bfo);

            bao = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bao);
            byte [] ba = bao.toByteArray();
            String ba1 = Base64.encodeBytes(ba);
            System.out.println("BA1 : "+ ba1);
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("image",ba1));
            nameValuePairs.add(new BasicNameValuePair("cmd","image_android"));
            Log.v("log_tag", System.currentTimeMillis()+".jpg");
            try{
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new
                        //  Here you need to put your server file address
                        HttpPost("http://intranet.wahanaartha.com/supervisi/upload_photo.php");
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();
                Log.v("log_tag", "In the try Loop" );
            }catch(Exception e){
                Log.v("log_tag", "Error in http connection "+e.toString());
            }
            return "Success";
            // (null);
        }

        @Override
        protected void onProgressUpdate(Void... unsued) {

        }

        @Override
        protected void onPostExecute(String sResponse) {
            try {
                if (dialog.isShowing())
                    dialog.dismiss();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(),
                        e.getMessage(),
                        Toast.LENGTH_LONG).show();
                Log.e(e.getClass().getName(), e.getMessage(), e);
            }
        }

    }
}
