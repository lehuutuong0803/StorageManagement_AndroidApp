    package com.example.shop10;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shop10.Adapter.LoaiSP_Adapter;
import com.example.shop10.LopSQL.LoaiSP;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.shop10.common.ip;

    public class ManHinh_qlLoaisp extends AppCompatActivity {

    ListView lvLoaiSP;
    ArrayList<LoaiSP> loaiSPArrayList;
    LoaiSP_Adapter loaiSPAdapter;
    Button btnThem;
    MainActivity mainActivity;
        String url = ip +"/getData.php";
        String urlInsert = ip +"/Insert.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_ql_loaisp);
        AnhXa();
        ControlButton();

        loaiSPArrayList = new ArrayList<>();
        loaiSPAdapter = new LoaiSP_Adapter(this,R.layout.dong_loaisp,loaiSPArrayList);
        lvLoaiSP.setAdapter(loaiSPAdapter);

      //  laysulieu
        GetData(url);


    }

    private void GetData (String url)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                loaiSPArrayList.clear();

                for(int i = 0;i<response.length();i++)
                {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        loaiSPArrayList.add(new LoaiSP(
                                object.getInt("IDLoaiSP"),
                                object.getString("TenLoaiSP")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                loaiSPAdapter.notifyDataSetChanged();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(ManHinh_qlLoaisp.this, "Lỗi", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    //Thêm loại sp
    private void ThemLoaiSP(String urlInsert,String tenloaisp)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlInsert, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equals("success"))
                {
                    Toast.makeText(ManHinh_qlLoaisp.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(ManHinh_qlLoaisp.this,ManHinh_qlLoaisp.class);
                    startActivity(intent1);
                }
                else
                {
                    Toast.makeText(mainActivity, "Lỗi Thêm", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mainActivity, "Xảy ra lỗi", Toast.LENGTH_SHORT).show();

            }
        }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    params.put("tenloaisp1",tenloaisp.toString().trim());

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    //Sự kiện Button
    private void ControlButton() {
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(ManHinh_qlLoaisp.this);
                dialog.setTitle("");
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.nhap_loai_sp);

                //AnhXa
                EditText tenLoaiSP =(EditText)dialog.findViewById(R.id.etTenLoaiSP);
                Button btDongy = (Button)dialog.findViewById(R.id.btDongY);
                Button btHuy = (Button)dialog.findViewById((R.id.btHuy));


                btDongy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(tenLoaiSP.getText().toString().equals(""))
                        {
                            Toast.makeText(ManHinh_qlLoaisp.this, "Điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            String tenloaisp = tenLoaiSP.getText().toString().trim();
                            ThemLoaiSP(urlInsert,tenloaisp);
                        }

                    }
                });
                btHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });
    }
    private void AnhXa() {
        lvLoaiSP =(ListView)findViewById(R.id.lvLoaiSP);
        btnThem = (Button)findViewById(R.id.btnThemLoaiSP);
    }

    public void DeleteLoaiSP(final int  idLoaiSP,String urlDelete){

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlDelete,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("success"))
                        {
                            Toast.makeText(ManHinh_qlLoaisp.this, "Xóa Thành Công", Toast.LENGTH_SHORT).show();
                            GetData(url);
                        }
                        else
                        {
                            Toast.makeText(ManHinh_qlLoaisp.this, "Lỗi Xóa", Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ManHinh_qlLoaisp.this, "Xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                    }
                }
        ){

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("idloaisp",String.valueOf(idLoaiSP));

                return params;
            }
        };
        requestQueue.add(stringRequest);
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
                Intent intent = new Intent(ManHinh_qlLoaisp.this,ManHinh_User.class);
                startActivity(intent);
                break;
            case R.id.menuQuanLyLoaiSP:
                Intent intent1 = new Intent(ManHinh_qlLoaisp.this,ManHinh_qlLoaisp.class);
                startActivity(intent1);
                Toast.makeText(this,"Bạn chọn Quản Lý Loại SP",Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuQuanLySP:
                Intent intent2 = new Intent(ManHinh_qlLoaisp.this,ManHinh_qlSp.class);
                startActivity(intent2);
                Toast.makeText(this,"Bạn chọn Quản Lý SP",Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuHoaDonBan:
                Intent intent3 = new Intent(ManHinh_qlLoaisp.this,ManHinh_qlHoadonban.class);
                startActivity(intent3);
                Toast.makeText(this,"Bạn chọn Quản Lý Hóa Đơn Bán",Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuDangXuat:
                Intent intent4 = new Intent(ManHinh_qlLoaisp.this,MainActivity.class);
                startActivity(intent4);
                Toast.makeText(this,"Bạn chọn Đăng Xuất",Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}