package com.example.shop10.Customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.shop10.Adapter.SPCTM_Adapter;
import com.example.shop10.Adapter.SP_Adapter;
import com.example.shop10.LopSQL.GioHang;
import com.example.shop10.LopSQL.SP;
import com.example.shop10.MainActivity;
import com.example.shop10.ManHinh_User;
import com.example.shop10.ManHinh_qlLoaisp;
import com.example.shop10.ManHinh_qlSp;
import com.example.shop10.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ManHinh_Customer extends AppCompatActivity {
    String tenTK;
    ArrayList<SP> spArrayList;
    SPCTM_Adapter spctm_adapter;
    ListView lvSPCTM;

//    String urlGetData = "https://tuong123.000webhostapp.com/AndroidWebService/GetDataProduct.php";
    String urlGetData = "http://192.168.1.44:81/AndroidWebService/GetDataProduct.php";
    public static ArrayList<GioHang> gioHangArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh__customer);
        Intent intent =getIntent();
         tenTK = (String) intent.getSerializableExtra("TenTaiKhoan");
         AnhXa();

        spArrayList = new ArrayList<>();
        spctm_adapter = new SPCTM_Adapter(this,R.layout.dong_spctm,spArrayList);
        lvSPCTM.setAdapter(spctm_adapter);

        GetData(urlGetData);
        ControlButton();
    }

    private void ControlButton() {
        lvSPCTM.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ManHinh_Customer.this,activity_Ctsp.class);
                intent.putExtra("ThongTinSP",spArrayList.get(position));
                intent.putExtra("TenTaiKhoan",tenTK);
                startActivity(intent);
            }
        });
    }

    private void AnhXa() {
        lvSPCTM = (ListView)findViewById(R.id.lvHienThiSP);
        if(gioHangArrayList != null)
        {

        }
        else {
            gioHangArrayList = new ArrayList<>();
        }
    }

    private void GetData(String urlGetData)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, urlGetData, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                spArrayList.clear();
                for (int i = 0; i<response.length();i++)
                {
                    try{
                        JSONObject object  =response.getJSONObject(i);
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
//                spctm_adapter.notifyDataSetChanged();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ManHinh_Customer.this, "Lỗi", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
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
                Intent intent = new Intent(ManHinh_Customer.this, ManHinh_Customer.class);
                intent.putExtra("TenTaiKhoan",tenTK);
                startActivity(intent);
                break;
            case R.id.menuQuanLyTaiKhoan:
                Intent intent1 = new Intent(ManHinh_Customer.this, activity_qlTaiKhoan.class);
                intent1.putExtra("TenTaiKhoan",tenTK);
                startActivity(intent1);
                Toast.makeText(this,"Bạn chọn Tài Khoản",Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuLichSuDonHang:
                Intent intent2 = new Intent(ManHinh_Customer.this,activity_Lichsudonhang.class);
                intent2.putExtra("TenTaiKhoan",tenTK);
                startActivity(intent2);
                Toast.makeText(this,"Bạn chọn Lịch Sử Đơn Hàng",Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuDonHang:
                Intent intent3 = new Intent(ManHinh_Customer.this,activity_Donhang.class);
                intent3.putExtra("TenTaiKhoan",tenTK);
                startActivity(intent3);
                Toast.makeText(this,"Bạn chọn Đơn Hàng",Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuDangXuatCTM:
                ManHinh_Customer.gioHangArrayList.clear();
                Intent intent4 = new Intent(ManHinh_Customer.this, MainActivity.class);
                startActivity(intent4);
                Toast.makeText(this,"Bạn chọn Đăng Xuất",Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}