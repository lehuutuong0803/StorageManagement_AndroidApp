package com.example.shop10.Retrofit;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Dataclient {

    @Multipart
    @POST("UploadImage.php")
    Call<String> UploadPhoto(@Part MultipartBody.Part photo);
    @FormUrlEncoded
    @POST("InsertProduct.php")
    Call<String> insertData(@Field("tensp1") String tenSP
                            ,@Field("hinhanh1") String hinhAnh
                            ,@Field("gianhap1") String giaNhap
                            ,@Field("giaban1") String giaBan
                            ,@Field("soluong1") String soLuong
                            ,@Field("idloaisp2") String idLoaiSP
                            ,@Field("status") String status);
    @FormUrlEncoded
    @POST("UpdateProduct.php")
    Call<String> updateData(@Field("idsp") String idSP
            ,@Field("tensp") String tenSP1
            ,@Field("hasp") String hinhAnh1
            ,@Field("gianhap") String giaNhap1
            ,@Field("giaban") String giaBan1
            ,@Field("soluong") String soLuong1
            ,@Field("idloaisp") String idLoaiSP1);
}
