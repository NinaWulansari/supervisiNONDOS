package com.wahanaartha.supervisionline.Connect;

import android.graphics.Bitmap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wahanaartha.supervisionline.Activities.QuizActivity;
import com.wahanaartha.supervisionline.Model.AddPica;
import com.wahanaartha.supervisionline.Model.Approve;
import com.wahanaartha.supervisionline.Model.DataSupervisi;
import com.wahanaartha.supervisionline.Model.DealerLocal;
import com.wahanaartha.supervisionline.Model.DealerModel;
import com.wahanaartha.supervisionline.Model.GetDetailPica;
import com.wahanaartha.supervisionline.Model.HistoryIndex;
import com.wahanaartha.supervisionline.Model.InsertSupervisi;
import com.wahanaartha.supervisionline.Model.KategoriModel;
import com.wahanaartha.supervisionline.Model.ListApprove;
import com.wahanaartha.supervisionline.Model.ListHistory;
import com.wahanaartha.supervisionline.Model.ListQuestionsModel;
import com.wahanaartha.supervisionline.Model.LoginUser;
import com.wahanaartha.supervisionline.Model.Notif;
import com.wahanaartha.supervisionline.Model.QuestionPica;
import com.wahanaartha.supervisionline.Model.Status;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class API {

    static Retrofit retrofit;
    public static String baseURL = "http://intranet.wahanaartha.com/";
    public static String baseURLPicasso = "http://intranet.wahanaartha.com/webservices_supervisi_online/uploads/";
//    public static String baseURL = "http://192.168.12.189/";
//    public static String baseURLPicasso = "http://192.168.12.189/supervisi_online/uploads/";

    public static Retrofit getInstance() {
        if (retrofit == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .build();

            Gson gson = new GsonBuilder().setLenient().create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseURL + "webservices_supervisi_online/")
//                    .baseUrl(baseURL + "supervisi_online/")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }

        return retrofit;
    }

    private static MultipartBody.Part toTextRequestBody(String value, String name) {
        return MultipartBody.Part.create(okhttp3.Headers.of("Content-Disposition", "form-data; name=\"" + name + "\""),
                RequestBody.create(MediaType.parse("text/plain"), value));
    }

    // This method  converts Bitmap to RequestBody
    private static MultipartBody.Part toImageRequestBody(Bitmap bitmap, String name, String fileName, String type) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();
        RequestBody photo = RequestBody.create(MediaType.parse("multipart/form-data"), bitmapdata);
        return MultipartBody.Part.create(okhttp3.Headers.of("Content-Disposition",
                "form-data; name=\"" + name + "\"; filename=\"" + fileName + "\"\r\nContent-Type: " + type + "\r\n\r\n\r\n"), photo);

    }

    public static Call<LoginUser> login(String username, String password) {
        SupervisiService service = getInstance().create(SupervisiService.class);
        return service.login(username, password);
    }

    public static Call<LoginUser> getUser(String id) {
        SupervisiService service = getInstance().create(SupervisiService.class);
        return service.getUser(id);
    }

    public static Call<ArrayList<DealerModel>> getLovDealer(String id, String group_id) {
        SupervisiService service = getInstance().create(SupervisiService.class);
        return service.getLovDealer(id, group_id);
    }

    public static Call<ArrayList<DealerLocal>> spinnerDealer() {
        SupervisiService service = getInstance().create(SupervisiService.class);
        return service.spinnerDealer();
    }


    public static Call<ArrayList<KategoriModel>> getKategori(String type) {
        SupervisiService service = getInstance().create(SupervisiService.class);
        return service.getKategori(type);
    }

    public static Call<List<ListQuestionsModel>> getQuestions(String parent_id, String type, String type_dealer) {
        SupervisiService service = getInstance().create(SupervisiService.class);
        return service.getQuestions(parent_id, type, type_dealer);
    }

    public static Call<ArrayList<HistoryIndex>> getHistoryIndex(String created_by) {
        SupervisiService service = getInstance().create(SupervisiService.class);
        return service.getHistoryIndex(created_by);
    }

    public static Call<ArrayList<HistoryIndex>> getListHistory(String created_by) {
        SupervisiService service = getInstance().create(SupervisiService.class);
        return service.getListHistory(created_by);
    }

    public static Call<ArrayList<Notif>> notification(String create_by) {
        SupervisiService service = getInstance().create(SupervisiService.class);
        return service.notification(create_by);
    }

    public static Call<InsertSupervisi> addSupervisi(RequestBody pi, RequestBody main_item,
                                                     RequestBody item, RequestBody latitude,
                                                     RequestBody longitude, RequestBody no_dlr,
                                                     RequestBody remark, RequestBody create_by,
                                                     RequestBody status_pica, RequestBody type, RequestBody nama_dlr, MultipartBody.Part pic) {
        SupervisiService service = getInstance().create(SupervisiService.class);
        return service.addSupervisi(pi, main_item, item, latitude, longitude, no_dlr, remark, create_by, status_pica, type, nama_dlr, pic);
    }

    public static Call<InsertSupervisi> insertSupervisi(RequestBody pi, RequestBody main_item,
                                                        RequestBody item, RequestBody latitude,
                                                        RequestBody longitude, RequestBody no_dlr,
                                                        RequestBody remark, RequestBody create_by,
                                                        RequestBody status_pica, RequestBody type, RequestBody nama_dlr, MultipartBody.Part pic) {
        SupervisiService service = getInstance().create(SupervisiService.class);


        List<MultipartBody.Part> requestBody = new ArrayList<>();
        return service.insertSupervisi(pi, main_item, item, latitude, longitude, no_dlr, remark, create_by, status_pica, type, nama_dlr,pic);
    }

    public static Call<ArrayList<DataSupervisi>> getDataSupervisi(String create_by, String tgl_supervisi, String no_dlr, String type) {
        SupervisiService service = getInstance().create(SupervisiService.class);
        return service.getDataSupervisi(create_by, tgl_supervisi, no_dlr, type);
    }

    public static Call<ArrayList<Status>> deleteSupervisi(String create_by, String tgl_supervisi, String no_dlr, String type){
        SupervisiService service = getInstance().create(SupervisiService.class);
        return service.deleteSupervisi(create_by, tgl_supervisi, no_dlr, type);
    }


    //RATRI
    public static Call<GetDetailPica> getPica(String id) {
        SupervisiService service = getInstance().create(SupervisiService.class);
        return service.getPica(id);
    }

    public static Call<ArrayList<QuestionPica>> getListPica(String tgl_survey, String no_dlr) {
        SupervisiService service = getInstance().create(SupervisiService.class);
        return service.getListPica(tgl_survey, no_dlr);
    }

    public static Call<ArrayList<ListHistory>> getIndexHistory(String parent_id, String type, String tgl_supervisi, String no_dlr) {
        SupervisiService service = getInstance().create(SupervisiService.class);
        return service.getIndexHistory(parent_id, type, tgl_supervisi, no_dlr);
    }

    public static Call<ArrayList<KategoriModel>> getKategoriPica(String tgl_supervisi, String no_dlr) {
        SupervisiService service = getInstance().create(SupervisiService.class);
        return service.getKategoriPica(tgl_supervisi, no_dlr);
    }

    public static Call<AddPica> editPica(String id, String status_pica, String notes, String reason, String ca, String deadline, String new_deadline, String modi_by) {
        SupervisiService service = getInstance().create(SupervisiService.class);
        return service.editPica(id, status_pica, notes, reason, ca, deadline, new_deadline, modi_by);
    }

    public static Call<ArrayList<ListApprove>> getListApprove(String id) {
        SupervisiService service = getInstance().create(SupervisiService.class);
        return service.getListApprove(id);
    }

    public static Call<ArrayList<ListApprove>> getListApproved(String id) {
        SupervisiService service = getInstance().create(SupervisiService.class);
        return service.getListApproved(id);
    }

    public static Call<ArrayList<ListApprove>> getListApproveDetail(String created_by, String tgl_supervisi, String no_dlr) {
        SupervisiService service = getInstance().create(SupervisiService.class);
        return service.getListApproveDetail(created_by, tgl_supervisi, no_dlr);
    }

    public static Call<ArrayList<ListApprove>> getListApprovedDetail(String created_by, String tgl_supervisi, String no_dlr) {
        SupervisiService service = getInstance().create(SupervisiService.class);
        return service.getListApprovedDetail(created_by, tgl_supervisi, no_dlr);
}

    public static Call<Approve> getApprove(String id) {
        SupervisiService service = getInstance().create(SupervisiService.class);
        return service.getApprove(id);
    }

    public static Call<AddPica> editApprove(String id, String modi_by) {
        SupervisiService service = getInstance().create(SupervisiService.class);
        return service.editApprove(id, modi_by);
    }

    public static Call<AddPica> editNotApprove(String id, String modi_by) {
        SupervisiService service = getInstance().create(SupervisiService.class);
        return service.editNotApprove(id, modi_by);
    }

    public static Call<InsertSupervisi> addHistory(RequestBody pi, RequestBody main_item,
                                                   RequestBody item, RequestBody latitude,
                                                   RequestBody longitude, RequestBody no_dlr,
                                                   RequestBody remark, RequestBody create_by,
                                                   RequestBody status_pica, RequestBody type,
                                                   RequestBody nama_dlr, RequestBody tgl_supervisi, MultipartBody.Part pic) {
        SupervisiService service = getInstance().create(SupervisiService.class);
        return service.addHistory(pi, main_item, item, latitude, longitude, no_dlr, remark, create_by, status_pica, type, nama_dlr,tgl_supervisi,pic);
    }

    public static Call<InsertSupervisi> editHistory(RequestBody id, RequestBody pi, RequestBody remark, RequestBody modi_by, MultipartBody.Part pic) {
        SupervisiService service = getInstance().create(SupervisiService.class);
        return service.editHistory(id, pi, remark, modi_by, pic);
    }

    public static Call<ArrayList<ListApprove>> getIndexPicaSpv(String id) {
        SupervisiService service = getInstance().create(SupervisiService.class);
        return service.getIndexPicaSpv(id);
    }

    public static Call<ArrayList<ListApprove>> getIndexPicaAdmin(String id) {
        SupervisiService service = getInstance().create(SupervisiService.class);
        return service.getIndexPicaAdmin(id);
    }

    public static Call<ArrayList<ListApprove>> getListPicaSpv(String created_by, String tgl_supervisi, String no_dlr) {
        SupervisiService service = getInstance().create(SupervisiService.class);
        return service.getListPicaSpv(created_by, tgl_supervisi, no_dlr);
    }

    public interface SupervisiService {

        @POST("login2.php")
        Call<LoginUser> login(@Query("username") String username,
                              @Query("password") String password);

        @GET("get_user.php")
        Call <LoginUser> getUser(@Query("id")String id);

        @GET("get_cabang.php")
        Call<ArrayList<DealerModel>> getLovDealer(
                @Query("id") String id,
                @Query("group_id") String group_id);

        @GET("get_dealer.php")
        Call<ArrayList<DealerLocal>> spinnerDealer();

        @GET("get_kategori2.php")
        Call<ArrayList<KategoriModel>> getKategori(
                @Query("type") String type);

        @GET("list_question2.php")
        Call<List<ListQuestionsModel>> getQuestions(
                @Query("parent_id") String parent_id,
                @Query("type") String type,
                @Query("type_dealer") String type_dealer);

        @GET("get_supervisi.php")
        Call<ArrayList<HistoryIndex>> getHistoryIndex(
                @Query("created_by") String created_by);

        @GET("get_supervisi.php")
        Call<ArrayList<HistoryIndex>> getListHistory(
                @Query("created_by") String created_by);

        @Multipart
        @POST("Api.php?apicall=upload")
        Call<InsertSupervisi> addSupervisi(
                @Part("pi") RequestBody pi,
                @Part("main_item") RequestBody main_item,
                @Part("item") RequestBody item,
                @Part("latitude") RequestBody latitude,
                @Part("longitude") RequestBody longitude,
                @Part("no_dlr") RequestBody no_dlr,
                @Part("remark") RequestBody jawaban,
                @Part("create_by") RequestBody create_by,
                @Part("status_pica") RequestBody status_pica,
                @Part("type") RequestBody type,
                @Part("nama_dlr") RequestBody nama_dlr,
                @Part MultipartBody.Part pic);

        @Multipart
        @POST("Api.php?apicall=upload")
        Call<InsertSupervisi> insertSupervisi(
                @Part("pi") RequestBody pi,
                @Part("main_item") RequestBody main_item,
                @Part("item") RequestBody item,
                @Part("latitude") RequestBody latitude,
                @Part("longitude") RequestBody longitude,
                @Part("no_dlr") RequestBody no_dlr,
                @Part("remark") RequestBody jawaban,
                @Part("create_by") RequestBody create_by,
                @Part("status_pica") RequestBody status_pica,
                @Part("type") RequestBody type,
                @Part("nama_dlr") RequestBody nama_dlr,
                @Part MultipartBody.Part pic);


        @Multipart
        @POST("update_supervisi.php")
        Call<InsertSupervisi> updateSupervisi(
                @Part("pi") RequestBody pi,
                @Part("remark") RequestBody jawaban,
                @Part("modi_by") RequestBody modi_by,
                @Part("status_pica") RequestBody status_pica,
                @Part MultipartBody.Part pic);

        @GET("get_supervisiDlr.php")
        Call<ArrayList<DataSupervisi>> getDataSupervisi(
                @Query("create_by") String create_by,
                @Query("tgl_supervisi") String tgl_supervisi,
                @Query("no_dlr") String no_dlr,
                @Query("type") String type);

        @GET("notif_sa.php")
        Call<ArrayList<Notif>> notification(@Query("create_by") String create_by);

        @DELETE("delete_supervisi.php")
        Call<ArrayList<Status>> deleteSupervisi(
                @Query("create_by") String created_by,
                @Query("tgl_supervisi") String tgl_supervisi,
                @Query("no_dlr") String no_dlr,
                @Query("type") String type);

        // RATRI
        @GET("get_detail_pica.php")
        Call<GetDetailPica> getPica(
                @Query("id") String id);

        @GET("get_list_pica.php")
        Call<ArrayList<QuestionPica>> getListPica(
                @Query("tgl_supervisi") String tgl_supervisi,
                @Query("no_dlr") String no_dl);

        @GET("get_history.php")
        Call<ArrayList<ListHistory>> getIndexHistory(
                @Query("parent_id") String parent_id,
                @Query("type") String type,
                @Query("tgl_supervisi") String tgl_supervisi,
                @Query("no_dlr") String no_dl);

        @GET("get_kategori_pica.php")
        Call<ArrayList<KategoriModel>> getKategoriPica(
                @Query("tgl_supervisi") String tgl_supervisi,
                @Query("no_dlr") String no_dlr);

        @POST("insert_pica.php")
        Call<AddPica> editPica(@Query("id") String id,
                               @Query("status_pica") String status_pica,
                               @Query("notes") String notes,
                               @Query("reason") String reason,
                               @Query("ca") String ca,
                               @Query("deadline") String deadline,
                               @Query("new_deadline") String new_deadline,
                               @Query("modi_by") String modi_by);

        @GET("get_list_approve.php")
        Call<ArrayList<ListApprove>> getListApprove(
                @Query("id") String id);

        @GET("get_list_approved.php")
        Call<ArrayList<ListApprove>> getListApproved(
                @Query("id") String id);

        @GET("get_list_detail_approve.php")
        Call<ArrayList<ListApprove>> getListApproveDetail(
                @Query("created_by") String created_by,
                @Query("tgl_supervisi") String tgl_supervisi,
                @Query("no_dlr") String no_dlr);

        @GET("get_list_detail_approved.php")
        Call<ArrayList<ListApprove>> getListApprovedDetail(
                @Query("created_by") String created_by,
                @Query("tgl_supervisi") String tgl_supervisi,
                @Query("no_dlr") String no_dlr);

        @GET("get_detail_approve.php")
        Call<Approve> getApprove(
                @Query("id") String id);

        @POST("approve_pica.php")
        Call<AddPica> editApprove(@Query("id") String id,
                                  @Query("modi_by") String modi_by);

        @POST("not_approve_pica.php")
        Call<AddPica> editNotApprove(@Query("id") String id,
                                     @Query("modi_by") String modi_by);

        @Multipart
        @POST("ApiHistory.php?apicall=upload")
        Call<InsertSupervisi> addHistory(
                @Part("pi") RequestBody pi,
                @Part("main_item") RequestBody main_item,
                @Part("item") RequestBody item,
                @Part("latitude") RequestBody latitude,
                @Part("longitude") RequestBody longitude,
                @Part("no_dlr") RequestBody no_dlr,
                @Part("remark") RequestBody jawaban,
                @Part("create_by") RequestBody create_by,
                @Part("status_pica") RequestBody status_pica,
                @Part("type") RequestBody type,
                @Part("nama_dlr") RequestBody nama_dlr,
                @Part("tgl_supervisi") RequestBody tgl_supervisi,
                @Part MultipartBody.Part pic);

        @Multipart
        @POST("ApiEdit.php?apicall=upload")
        Call<InsertSupervisi> editHistory(
                @Part("id") RequestBody id,
                @Part("pi") RequestBody pi,
                @Part("remark") RequestBody jawaban,
                @Part("modi_by") RequestBody modi_by,
                @Part MultipartBody.Part pic);

        @GET("get_list_pica_spv.php")
        Call<ArrayList<ListApprove>> getIndexPicaSpv(
                @Query("id") String id);

        @GET("get_list_pica_admin.php")
        Call<ArrayList<ListApprove>> getIndexPicaAdmin(
                @Query("id") String id);

        @GET("get_list_pica_spv_detail.php")
        Call<ArrayList<ListApprove>> getListPicaSpv(
                @Query("created_by") String created_by,
                @Query("tgl_supervisi") String tgl_supervisi,
                @Query("no_dlr") String no_dlr);
    }

}