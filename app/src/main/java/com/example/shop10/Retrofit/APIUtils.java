package com.example.shop10.Retrofit;

import retrofit2.Retrofit;

import static com.example.shop10.common.ip;

public class APIUtils {
//    public  static final String Base_Url = "https://tuong123.000webhostapp.com/AndroidWebService/";
        public  static final String Base_Url = "http://192.168.1.12:81/AndroidWebService/" ;

    public static Dataclient getData() {
        return RetrofitClient.getClient(Base_Url).create(Dataclient.class);
    }
}
