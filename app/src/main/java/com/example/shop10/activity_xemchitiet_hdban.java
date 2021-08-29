package com.example.shop10;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shop10.Adapter.CTHD_Adapter;
import com.example.shop10.LopSQL.CTHD;
import com.example.shop10.LopSQL.HoaDonBan;
import com.example.shop10.LopSQL.SP;
import com.example.shop10.LopSQL.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class activity_xemchitiet_hdban extends AppCompatActivity {

    TextView tvIDHDBan, tvTenKH, tvDiaChi, tvSDT, tvNgayBan, tvTongTien, tvGhiChu;
    ListView listViewCTHD;
    ArrayList<User> userArrayList;
    ArrayList<CTHD> cthdArrayList;
    ArrayList<SP> spArrayList;
    CTHD_Adapter cthd_adapter;

//    String urlSearchUser = "https://tuong123.000webhostapp.com/AndroidWebService/SearchUser.php";
//    String urlGetDataCTHD = "https://tuong123.000webhostapp.com/AndroidWebService/GetDataCTHD.php";
//    String urlGetDataProduct = "https://tuong123.000webhostapp.com/AndroidWebService/GetDataProduct.php";
    String urlGetDataProduct = "http://192.168.1.44:81/AndroidWebService/GetDataProduct.php";
    String urlSearchUser = "http://192.168.1.44:81/AndroidWebService/SearchUser.php";
    String urlGetDataCTHD = "http://192.168.1.44:81/AndroidWebService/GetDataCTHD.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xemchitiet_hdban);

        Intent intent = getIntent();
        HoaDonBan hoaDonBan = (HoaDonBan) intent.getSerializableExtra("dataHDBan");
        userArrayList = new ArrayList<>();
        AnhXa();



        tvIDHDBan.setText("ID Hóa Đơn: "+hoaDonBan.getIDHoaDonBan());
        tvTongTien.setText("Tổng Tiền: "+String.valueOf(hoaDonBan.getTongTien()));
        tvGhiChu.setText("Ghi Chú: "+hoaDonBan.getGhiChu());
        tvNgayBan.setText("Ngày Bán: "+hoaDonBan.getNgayBan());
        GetDataUser(hoaDonBan.getIDKhachHang());


        spArrayList = new ArrayList<>();
        cthdArrayList = new ArrayList<>();
        cthd_adapter = new CTHD_Adapter(this,R.layout.dong_cthd,cthdArrayList,spArrayList);
        listViewCTHD.setAdapter(cthd_adapter);

        GetDataProduct(urlGetDataProduct);
        GetData(hoaDonBan.getIDHoaDonBan());

    }

    private void GetDataProduct (String urlGetData)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, urlGetData, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                spArrayList.clear();
                for (int i = 0; i<response.length();i++)
                {
                    try{
                        JSONObject  object  =response.getJSONObject(i);
                        spArrayList.add(new SP(
                                object.getInt("ID"),
                                object.getString("TenSP"),
                                object.getString("HinhAnh"),
                                object.getInt("GiaNhap"),
                                object.getInt("GiaBan"),
                                object.getInt("SoLuong"),
                                object.getInt("IDLoaiSP")));
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activity_xemchitiet_hdban.this, "Lỗi", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    private void GetData(int idhd) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlGetDataCTHD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        cthdArrayList.clear();
                        Toast.makeText(activity_xemchitiet_hdban.this, "Chi Tiết Hóa Đơn: "+response, Toast.LENGTH_SHORT).show();
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++)
                            {
                                try {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    cthdArrayList.add(new CTHD(
                                            object.getInt("ID"),
                                            object.getInt("IDSP"),
                                            object.getInt("SoLuong"),
                                            object.getInt("DonGia"),
                                            object.getInt("ThanhTien")));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

//                            cthd_adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activity_xemchitiet_hdban.this, "Xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                    }
                }
        ){

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("idcthd",String.valueOf(idhd));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void GetDataUser(int idKhachHang) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlSearchUser,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                                try {
                                    JSONObject object = jsonArray.getJSONObject(0);
                                    userArrayList.add(new User(
                                            object.getInt("ID"),
                                            object.getString("TenTK"),
                                            object.getString("MatKhauTK"),
                                            object.getString("TenNguoiDung"),
                                            object.getString("SDT"),
                                            object.getString("DiaChi")));

                                    User user = userArrayList.get(0);
                                    tvTenKH.setText("Tên KH: "+user.getTenNguoiDung());
                                    tvDiaChi.setText("Địa Chỉ: "+user.getDiaChi());
                                    tvSDT.setText("SĐT: "+user.getSDT());

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activity_xemchitiet_hdban.this, "Xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                    }
                }
        ){

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("idKHTK",String.valueOf(idKhachHang));
                return params;
            }
        };
        requestQueue.add(stringRequest);

    }

    private void AnhXa() {
        tvIDHDBan = (TextView)findViewById(R.id.textviewIDHDBan1);
        tvTenKH = (TextView)findViewById(R.id.textviewTenKH);
        tvDiaChi = (TextView)findViewById(R.id.textviewDiaChi);
        tvSDT = (TextView)findViewById(R.id.textviewSDT);
        tvNgayBan = (TextView)findViewById(R.id.textviewNgayBan);
        tvTongTien = (TextView)findViewById(R.id.textviewTongTien1);
        tvGhiChu = (TextView)findViewById(R.id.textviewGhiChu1);
        listViewCTHD = (ListView)findViewById(R.id.lvCTHD);
    }
}