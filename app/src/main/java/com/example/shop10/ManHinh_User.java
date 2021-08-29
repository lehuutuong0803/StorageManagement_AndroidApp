package com.example.shop10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.shop10.Adapter.SPCTM_Adapter;
import com.example.shop10.Adapter.SPUser_Adapter;
import com.example.shop10.Customer.ManHinh_Customer;
import com.example.shop10.LopSQL.SP;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ManHinh_User extends AppCompatActivity {
    ArrayList<SP> spArrayList;
    SPUser_Adapter spUser_adapter;
    ListView lvSPUser;
    //String urlGetData = "https://tuong123.000webhostapp.com/AndroidWebService/GetDataProduct.php";
    String urlGetData = "http://192.168.1.44:81//AndroidWebService/GetDataProduct.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh__user);
        AnhXa();

        spArrayList = new ArrayList<>();
        spUser_adapter = new SPUser_Adapter(ManHinh_User.this,R.layout.dong_spctm,spArrayList);
        lvSPUser.setAdapter(spUser_adapter);
        GetData(urlGetData);
    }

    private void AnhXa() {
        lvSPUser = (ListView)findViewById(R.id.lvHienThiSPUser);
    }

    private void GetData(String urlGetData) {

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
                        Toast.makeText(ManHinh_User.this, "Lỗi", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menuTrangChu:
                Intent intent = new Intent(ManHinh_User.this,ManHinh_User.class);
                startActivity(intent);
                break;
            case R.id.menuQuanLyLoaiSP:
                Intent intent1 = new Intent(ManHinh_User.this,ManHinh_qlLoaisp.class);
                startActivity(intent1);
                Toast.makeText(this,"Bạn chọn Quản Lý Loại SP",Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuQuanLySP:
                Intent intent2 = new Intent(ManHinh_User.this,ManHinh_qlSp.class);
                startActivity(intent2);
                Toast.makeText(this,"Bạn chọn Quản Lý SP",Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuHoaDonBan:
                Intent intent3 = new Intent(ManHinh_User.this,ManHinh_qlHoadonban.class);
                startActivity(intent3);
                Toast.makeText(this,"Bạn chọn Quản Lý Hóa Đơn Bán",Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuDangXuat:
                Intent intent4 = new Intent(ManHinh_User.this,MainActivity.class);
                startActivity(intent4);
                Toast.makeText(this,"Bạn chọn Đăng Xuất",Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}