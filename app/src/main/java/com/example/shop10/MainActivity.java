package com.example.shop10;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
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
import com.example.shop10.Customer.ManHinh_Customer;
import com.example.shop10.LopSQL.LoaiSP;
import com.example.shop10.LopSQL.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.shop10.common.ip;

public class MainActivity extends AppCompatActivity {

    EditText editTextTK,editTextMK;
    Button buttonDN, buttonDK;
    SQLiteHeper data;
    ArrayList<User> userArrayList;
    ArrayList<LoaiSP> loaiSPArrayList = new ArrayList<>();
    String urlLogin = ip+ "/LoginApp.php";
    String urlCheck = ip+ "/CheckAccount.php";
    String urlInsert = ip+ "/InsertAccount.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        ControlButton();
        buttonDK.setEnabled(true);
    }
    private void ControlButton() {

        //Đăng Nhập
        buttonDN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextTK.getText().toString().trim().equals("user1")&& editTextMK.getText().toString().trim().equals("123abc"))
                {
                    Toast.makeText(MainActivity.this,"Đăng Nhập Thành Công",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this,ManHinh_User.class);
                    startActivity(intent);
                }
                else
                {
                    Login(urlLogin);
                }

            }
        });

        //Đăng ký TK
        buttonDK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog = new Dialog(MainActivity.this);
                dialog.setTitle("");
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dang_ky);

                //Ánh xạ dialog
                EditText editTextTK1 =(EditText)dialog.findViewById(R.id.etTenDK);
                EditText editTextMK1 =(EditText)dialog.findViewById(R.id.etMKDK);
                EditText editTextTenND = (EditText)dialog.findViewById((R.id.etTenND));
                EditText editTextSDT = (EditText)dialog.findViewById(R.id.etSDT);
                EditText editTextDiaChi = (EditText)dialog.findViewById(R.id.etDiaChi);
                Button btDongy = (Button)dialog.findViewById(R.id.btDongY);
                Button btHuy = (Button)dialog.findViewById((R.id.btHuy));

                editTextMK1.setInputType(InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_VARIATION_PASSWORD);
                btDongy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int i=0;
                        String tk = editTextTK1.getText().toString().trim();
                        String mk = editTextMK1.getText().toString().trim();
                        String tennguoidung = editTextTenND.getText().toString().trim();
                        String sdt = editTextSDT.getText().toString().trim();
                        String diachi= editTextDiaChi.getText().toString().trim();

                        // Kiểm tra các điều kiện
                        if (!tk.trim().equals("")&&!mk.trim().equals("")&&!tennguoidung.trim().equals("")&&!sdt.trim().equals("")&&!diachi.trim().equals("")) {

                            CheckAccount(urlCheck,urlInsert,tk,mk,tennguoidung,sdt,diachi);
                        }

                        else {
                            Toast.makeText(MainActivity.this, "Hãy Nhập đầy đủ thông tin!!", Toast.LENGTH_SHORT).show();
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
        editTextTK = (EditText)findViewById(R.id.etTK);
        editTextMK =(EditText)findViewById(R.id.etMK);
        buttonDN = (Button)findViewById(R.id.btDangNhap);
        buttonDK =(Button)findViewById(R.id.btDangKy);

        //DK
        editTextMK.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }

    //Sự kiến đăng nhập
    private void Login(String urlLogin){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlLogin, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equals("success"))
                {
                        Toast.makeText(MainActivity.this,"Đăng Nhập Thành Công",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, ManHinh_Customer.class);
                        intent.putExtra("TenTaiKhoan",editTextTK.getText().toString().trim());
                        startActivity(intent);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Đăng Nhập Thất Bại! Vui lòng nhập lại", Toast.LENGTH_SHORT).show();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("TaiKhoan",editTextTK.getText().toString().trim());
                params.put("MatKhau",editTextMK.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    //Kiểm tra xem tài khoản đã có chưa
    private void CheckAccount(String urlCheck,String urlInsert,String tenTK,String matkhauTK ,String tennguoiDung, String sdT,String diaChi)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlCheck, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("success"))
                {
                    AddAccount( tenTK, matkhauTK,tennguoiDung,  sdT, diaChi,  urlInsert);

                }
                else
                {
                    Toast.makeText(MainActivity.this, "Tên tài khoản " +tenTK+" đã tồn tại!", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Xảy ra lỗi!", Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("TaiKhoan",tenTK);
                return params;

            }
        };
        requestQueue.add(stringRequest);
    }
    //Thêm tài khoản
    private void AddAccount(String tenTK, String matkhauTK,String tennguoiDung, String sdT,String diaChi,String urlInsert)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlInsert, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("success"))
                {
                    Toast.makeText(MainActivity.this, "Tạo tài khoản "+tenTK+" thành công!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this,MainActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Lỗi Tạo TK", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Xảy ra lỗi!", Toast.LENGTH_SHORT).show();
            }
        }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tenTK1",tenTK);
                params.put("matkhauTK1",matkhauTK);
                params.put("tennguoiDung1",tennguoiDung);
                params.put("sdT1",sdT);
                params.put("diaChi1",diaChi);
                return params;

            }
        };
        requestQueue.add(stringRequest);
    }

}