package com.example.shop10.Customer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shop10.Adapter.HoaDonBan_Adapter;
import com.example.shop10.Adapter.LichSuDonHang_Adapter;
import com.example.shop10.LopSQL.HoaDonBan;
import com.example.shop10.LopSQL.User;
import com.example.shop10.MainActivity;
import com.example.shop10.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class activity_Lichsudonhang extends AppCompatActivity {

    String tenTK;
    ListView lvLichSuDH;
    TextView textViewThongBao;
    ArrayList<HoaDonBan> hDBanArrayList;
    LichSuDonHang_Adapter lichSuDonHang_adapter;
//    String urlGetDataInvoice ="https://tuong123.000webhostapp.com/AndroidWebService/SearchIDInvoice.php";
//    String urlGetData = "https://tuong123.000webhostapp.com/AndroidWebService/SearchAccount.php";
    String urlGetDataInvoice ="http://192.168.1.44:81/AndroidWebService/SearchIDInvoice.php";
    String urlGetData = "http://192.168.1.44:81/AndroidWebService/SearchAccount.php";
    ArrayList<User> userArrayList;
    String idKH="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__lichsudonhang);
        Intent intent = getIntent();
        tenTK = (String) intent.getSerializableExtra("TenTaiKhoan");

        AnhXa();

        userArrayList = new ArrayList<>();
        hDBanArrayList = new ArrayList<>();
        lichSuDonHang_adapter = new LichSuDonHang_Adapter(this,R.layout.dong_lichsudonhang,hDBanArrayList);
        lvLichSuDH.setAdapter(lichSuDonHang_adapter);

        IDCustomer();
    }

    private void IDCustomer() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlGetData, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray =  new JSONArray(response);

                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    userArrayList.add(new User(
                            jsonObject.getInt("ID"),
                            jsonObject.getString("TenTK"),
                            jsonObject.getString("MatKhauTK"),
                            jsonObject.getString("TenNguoiDung"),
                            jsonObject.getString("SDT"),
                            jsonObject.getString("DiaChi")
                    ));

                    User user = userArrayList.get(0);
                 idKH = String.valueOf(user.getID());
                 GetData(idKH);
                    Toast.makeText(activity_Lichsudonhang.this, "IDKH: "+idKH, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("tentk",tenTK);
                return params ;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void GetData(String idKH1) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlGetDataInvoice, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    Toast.makeText(activity_Lichsudonhang.this, "Danh Sach Mảng: "+response, Toast.LENGTH_SHORT).show();
                    if(jsonArray.length()==0)
                    {
                        Toast.makeText(activity_Lichsudonhang.this, "Bạn chưa có hóa đơn nào!", Toast.LENGTH_SHORT).show();
                        textViewThongBao.setText("Bạn chưa có đơn hàng nào!");
                    }
                    for(int i = 0; i<jsonArray.length();i++)
                    {
                        JSONObject object = jsonArray.getJSONObject(i);
                        hDBanArrayList.add(new HoaDonBan(
                                object.getInt("IDHD"),
                                object.getInt("IDKH"),
                                object.getInt("TinhTrang1"),
                                object.getInt("TongTien1"),
                                object.getString("GhiChu1"),
                                object.getString("NgayBan1")
                        ));
                    }
                    lichSuDonHang_adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activity_Lichsudonhang.this, "Lỗi", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("idkhachhang",idKH1);

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void AnhXa() {
        lvLichSuDH = (ListView)findViewById(R.id.lvLichSuDonHang);
        textViewThongBao = (TextView)findViewById(R.id.tvThongBao);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_customer,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menuTrangChuCTM:
                Intent intent = new Intent(activity_Lichsudonhang.this, ManHinh_Customer.class);
                intent.putExtra("TenTaiKhoan",tenTK);
                startActivity(intent);
                break;
            case R.id.menuQuanLyTaiKhoan:
                Intent intent1 = new Intent(activity_Lichsudonhang.this, activity_qlTaiKhoan.class);
                intent1.putExtra("TenTaiKhoan",tenTK);
                startActivity(intent1);
                Toast.makeText(this,"Bạn chọn Tài Khoản",Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuLichSuDonHang:
                Intent intent2 = new Intent(activity_Lichsudonhang.this,activity_Lichsudonhang.class);
                intent2.putExtra("TenTaiKhoan",tenTK);
                startActivity(intent2);
                Toast.makeText(this,"Bạn chọn Lịch Sử Đơn Hàng",Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuDonHang:
                Intent intent3 = new Intent(activity_Lichsudonhang.this,activity_Donhang.class);
                intent3.putExtra("TenTaiKhoan",tenTK);
                startActivity(intent3);
                Toast.makeText(this,"Bạn chọn Đơn Hàng",Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuDangXuatCTM:
                ManHinh_Customer.gioHangArrayList.clear();
                Intent intent4 = new Intent(activity_Lichsudonhang.this, MainActivity.class);
                startActivity(intent4);
                Toast.makeText(this,"Bạn chọn Đăng Xuất",Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}