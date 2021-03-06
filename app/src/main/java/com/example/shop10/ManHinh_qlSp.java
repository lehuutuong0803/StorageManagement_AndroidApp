package com.example.shop10;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shop10.Adapter.SP_Adapter;
import com.example.shop10.LopSQL.LoaiSP;
import com.example.shop10.LopSQL.SP;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.shop10.common.ip;

public class ManHinh_qlSp extends AppCompatActivity {

    ListView lvSP;
    ArrayList<SP> spArrayList;
    SP_Adapter adapter;
    Button btnThem,btnTimKiem;
    String urlGetData = ip +"/GetDataProduct.php";
    String urlSearchIDProduct = ip +"/SearchIDProduct.php";
    TextView tensp,giasp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_ql_sp);
        AnhXa();


        spArrayList = new ArrayList<>();
        adapter = new SP_Adapter(this,R.layout.dong_sp,spArrayList);
        lvSP.setAdapter(adapter);

        //  laysulieu
        GetData(urlGetData);
        ControlButton();
    }
    private void ControlButton()
    {
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManHinh_qlSp.this,activity_them_sp.class);
                startActivity(intent);
            }
        });
        btnTimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(ManHinh_qlSp.this);
                dialog.setTitle("");
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.timkiem_sp);

                EditText edtIDTimKiem = (EditText)dialog.findViewById(R.id.editTextTimKiemID);
                Button btnTimKiemID = (Button)dialog.findViewById(R.id.btnTimKiemIDSP);
                Button btnHuyTimKiemID = (Button)dialog.findViewById(R.id.btnHuyTimKiemIDSP);

                btnTimKiemID.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String idtk = edtIDTimKiem.getText().toString().trim();
                        if(idtk.equals(""))
                        {
                            Toast.makeText(ManHinh_qlSp.this, "Vui l??ng nh???p ?????y ????? th??ng tin!", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            SearchIDSP(idtk,urlSearchIDProduct);
                            dialog.cancel();
                        }
                    }
                });
                btnHuyTimKiemID.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });

    }
    public void SearchIDSP(String idSP, String urlSearchID)
    {

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlSearchIDProduct,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        spArrayList.clear();
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++)
                            {
                                try {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    spArrayList.add(new SP(
                                            object.getInt("ID"),
                                            object.getString("TenSP"),
                                            object.getString("HinhAnh"),
                                            object.getInt("GiaNhap"),
                                            object.getInt("GiaBan"),
                                            object.getInt("SoLuong"),
                                            object.getInt("IDLoaiSP"),
                                            object.getInt("Status")));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            if(spArrayList.isEmpty())
                            {
                                Toast.makeText(ManHinh_qlSp.this, "Kh??ng c?? s???n ph???m c???n t??m ki???m", Toast.LENGTH_SHORT).show();
                                GetData(urlGetData);
                            }
                            else
                            {
                                Toast.makeText(ManHinh_qlSp.this, "C?? s???n ph???m c???n t??m ki???m", Toast.LENGTH_SHORT).show();
                                adapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ManHinh_qlSp.this, "X???y ra l???i!", Toast.LENGTH_SHORT).show();
                    }
                }
        ){

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("idSPTK",String.valueOf(idSP));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void DeleteSP(final int  idSP,String urlDelete){

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlDelete,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("success"))
                        {
                            Toast.makeText(ManHinh_qlSp.this, "X??a Th??nh C??ng", Toast.LENGTH_SHORT).show();
                            GetData(urlGetData);
                        }
                        else
                        {
                            Toast.makeText(ManHinh_qlSp.this, "L???i X??a", Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ManHinh_qlSp.this, "X???y ra l???i!", Toast.LENGTH_SHORT).show();
                    }
                }
        ){

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("idsp",String.valueOf(idSP));
                return params;
            }
        };
        requestQueue.add(stringRequest);
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
                        JSONObject  object  =response.getJSONObject(i);
                        spArrayList.add(new SP(
                                object.getInt("ID"),
                                object.getString("TenSP"),
                                object.getString("HinhAnh"),
                                object.getInt("GiaNhap"),
                                object.getInt("GiaBan"),
                                object.getInt("SoLuong"),
                                object.getInt("IDLoaiSP"),
                                object.getInt("Status")));
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ManHinh_qlSp.this, "L???i", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }
    private void AnhXa()
    {
        btnThem = (Button)findViewById(R.id.btnThemSP);
        lvSP = (ListView)findViewById(R.id.lvSP);
        btnTimKiem = (Button)findViewById(R.id.btnTimKiemSP);
        tensp = (TextView)findViewById(R.id.SP_TenSP);
        giasp = (TextView)findViewById(R.id.SP_GiaSP);
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
                Intent intent = new Intent(ManHinh_qlSp.this,ManHinh_User.class);
                startActivity(intent);
                break;
            case R.id.menuQuanLyLoaiSP:
                Intent intent1 = new Intent(ManHinh_qlSp.this,ManHinh_qlLoaisp.class);
                startActivity(intent1);
                Toast.makeText(this,"B???n ch???n Qu???n L?? Lo???i SP",Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuQuanLySP:
                Intent intent2 = new Intent(ManHinh_qlSp.this,ManHinh_qlSp.class);
                startActivity(intent2);
                Toast.makeText(this,"B???n ch???n Qu???n L?? SP",Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuHoaDonBan:
                Intent intent3 = new Intent(ManHinh_qlSp.this,ManHinh_qlHoadonban.class);
                startActivity(intent3);
                Toast.makeText(this,"B???n ch???n Qu???n L?? H??a ????n B??n",Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuDangXuat:
                Intent intent4 = new Intent(ManHinh_qlSp.this,MainActivity.class);
                startActivity(intent4);
                Toast.makeText(this,"B???n ch???n ????ng Xu???t",Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}