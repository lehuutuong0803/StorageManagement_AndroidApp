package com.example.shop10;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shop10.LopSQL.LoaiSP;

import java.util.HashMap;
import java.util.Map;

import static com.example.shop10.common.ip;

public class activity_sua_loaisp extends AppCompatActivity  {

    EditText tenloaiSP;
    Button btDongY;
    Button btHuy;
    int id=0;
    String url = ip +"/UpdateLoaiSP.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_loaisp);
        Intent intent = getIntent();
        LoaiSP loaiSP = (LoaiSP) intent.getSerializableExtra("dataLoaiSP");

        AnhXa();
        tenloaiSP.setText(loaiSP.getTenLoaiSP());
        id = loaiSP.getIDLoaiSP();

        btDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tenloaiSP.getText().toString().trim().equals(""))
                {
                    Toast.makeText(activity_sua_loaisp.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else {
                    CapNhatLoaiSP(url);
                }
            }
        });

        btHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_sua_loaisp.this,ManHinh_qlLoaisp.class);
                startActivity(intent);
            }
        });
    }

    private  void CapNhatLoaiSP(String url)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("success"))
                        {
                            Toast.makeText(activity_sua_loaisp.this, "Cập Nhập Thành Công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(activity_sua_loaisp.this,ManHinh_qlLoaisp.class);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(activity_sua_loaisp.this, "Lỗi Cập Nhập", Toast.LENGTH_SHORT).show();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activity_sua_loaisp.this, "Xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String > params = new HashMap<>();
                params.put("idloaisp",String.valueOf(id));
                params.put("tenloaiSP",tenloaiSP.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void AnhXa() {
        tenloaiSP = (EditText)findViewById(R.id.etTenLoaiSP);
        btDongY =(Button)findViewById(R.id.btDongY);
        btHuy = (Button)findViewById(R.id.btHuy);
    }
}