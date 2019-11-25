package com.wahanaartha.supervisionline.Connect;

import com.wahanaartha.supervisionline.Model.MyResponse;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface Apii {

    String BASE_URL = "http://192.168.12.189/supervisi_online/";

    //this is our multipart request
    //we have two parameters on is name and other one is description
    @Multipart
    @POST("insert_supervisi.php")
    Call<MyResponse> addSupervisi(
            @Part("image\"; filename=\"myfile.jpg\" ") RequestBody file,
            @Part("latitude") RequestBody latitude,
            @Part("longitude") RequestBody longitude,
            @Part("main_item") RequestBody main_item,
            @Part("item") RequestBody item,
            @Part("remark") RequestBody jawaban,
            @Part("create_by") RequestBody data_create_by,
            @Part("no_dlr") RequestBody no_dlr,
            @Part("pi") RequestBody pi);

    @Multipart
    @POST("/cycles/uploadImage")
    Call<ResponseBody> uploadImage(@Part MultipartBody.Part photo,
                                   @PartMap Map<String, RequestBody> text);
}