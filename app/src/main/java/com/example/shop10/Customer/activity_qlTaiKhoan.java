package com.example.shop10.Customer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.service.controls.templates.ControlButton;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shop10.LopSQL.User;
import com.example.shop10.MainActivity;
import com.example.shop10.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class activity_qlTaiKhoan extends AppCompatActivity {
    String tenTK;
    TextView tvtenNguoiDung, tvSDT, tvDiaChi;
    Button btCapNhapThongTin, btDoiMatKhuau;
//    String urlGetData = "https://tuong123.000webhostapp.com/AndroidWebService/SearchAccount.php";
//    String urlUpdateAccount ="https://tuong123.000webhostapp.com/AndroidWebService/UpdateAccount.php";
//    String urlLogin = "https://tuong123.000webhostapp.com/AndroidWebService/LoginApp.php";
//    String urlChangePassWord = "https://tuong123.000webhostapp.com/AndroidWebService/ChangePassWord.php";
    String urlGetData = "http://192.168.1.44:81/AndroidWebService/SearchAccount.php";
    String urlUpdateAccount ="http://192.168.1.44:81/AndroidWebService/UpdateAccount.php";
    String urlLogin = "http://192.168.1.44:81/AndroidWebService/LoginApp.php";
    String urlChangePassWord = "http://192.168.1.44:81/AndroidWebService/ChangePassWord.php";
    ArrayList<User> userArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ql_tai_khoan);
        Intent intent = getIntent();
        tenTK = (String) intent.getSerializableExtra("TenTaiKhoan");
        userArrayList = new ArrayList<>();

        AnhXa();
        GetDataCustomer(tenTK);
        ControlButton();
    }

    private void GetDataCustomer(String tenTK1) {
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
                    tvtenNguoiDung.setText(user.getTenNguoiDung());
                    tvDiaChi.setText(user.getDiaChi());
                    tvSDT.setText(user.getSDT());
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
                params.put("tentk",tenTK1);
                return params ;
            }
        };
        requestQueue.add(stringRequest);
    }


    private void ControlButton()
    {
        btCapNhapThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(activity_qlTaiKhoan.this);
                dialog.setTitle("");
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.capnhatthongtin);

                //AnhXa
                EditText editTextTenND = (EditText)dialog.findViewById(R.id.editTextTenNguoiDung1);
                EditText editTextSDT = (EditText)dialog.findViewById(R.id.editTextSDT1);
                EditText editTextDiaChi = (EditText)dialog.findViewById(R.id.editTextDiaChi1);
                Button btnDongY = (Button) dialog.findViewById(R.id.btnDongYCapNhatTK);
                Button btnHuy = (Button) dialog.findViewById(R.id.btnHuyCapNhatTK);


                User user = userArrayList.get(0);
                editTextTenND.setText(user.getTenNguoiDung());
                editTextSDT.setText(user.getSDT());
                editTextDiaChi.setText(user.getDiaChi());

                btnDongY.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String tenND,sDTND,diaChiND;
                        tenND = editTextTenND.getText().toString().trim();
                        sDTND = editTextSDT.getText().toString().trim();
                        diaChiND = editTextDiaChi.getText().toString().trim();

                        if(tenND.equals("")||sDTND.equals("")||diaChiND.equals(""))
                        {
                            Toast.makeText(activity_qlTaiKhoan.this, "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            UpdateAccount(tenND,sDTND,diaChiND);
                        }

                    }
                });

                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                dialog.show();

            }
        });

        btDoiMatKhuau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(activity_qlTaiKhoan.this);
                dialog.setTitle("");
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.doimatkhuau);

                //AnhXa
                EditText editTextMKCu = (EditText)dialog.findViewById(R.id.editTextMKCu);
                EditText editTextNhapMKMoi = (EditText)dialog.findViewById(R.id.editTextNhapMKMoi);
                EditText editTextNhapLaiMKMoi = (EditText)dialog.findViewById(R.id.editTextNhapLaiMKMoi);
                Button btnDongY = (Button) dialog.findViewById(R.id.btnDongYCapNhatMK);
                Button btnHuy = (Button) dialog.findViewById(R.id.btnHuyCapNhatMK);


                editTextMKCu.setInputType(InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_VARIATION_PASSWORD);
                editTextNhapMKMoi.setInputType(InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_VARIATION_PASSWORD);
                editTextNhapLaiMKMoi.setInputType(InputType.TYPE_CLASS_TEXT |
                        InputType.TYPE_TEXT_VARIATION_PASSWORD);
                btnDongY.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String MKCu,NhapMKMoi,NhapLaiMKMoi;
                        MKCu = editTextMKCu.getText().toString().trim();
                        NhapMKMoi = editTextNhapMKMoi.getText().toString().trim();
                        NhapLaiMKMoi = editTextNhapLaiMKMoi.getText().toString().trim();
                        if(MKCu.equals("")||NhapMKMoi.equals("")||NhapLaiMKMoi.equals(""))
                        {
                            Toast.makeText(activity_qlTaiKhoan.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            CheckPassWord(MKCu,NhapMKMoi,NhapLaiMKMoi);
                        }
                    }
                });
                btnHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                dialog.show();
            }


        });
    }
    private void CheckPassWord(String mkCu,String MKMoi, String LaiMKMoi) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlLogin, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equals("success"))
                {
                   ChangePassWord(mkCu,MKMoi,LaiMKMoi);
                }
                else
                {
                    Toast.makeText(activity_qlTaiKhoan.this, "Mật khẩu không đúng! Vui lòng nhập lại", Toast.LENGTH_SHORT).show();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activity_qlTaiKhoan.this, "Xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("TaiKhoan",tenTK);
                params.put("MatKhau",mkCu);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void ChangePassWord(String mkCu, String mkMoi, String laiMKMoi) {
        if(mkMoi.equals(laiMKMoi))
        {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, urlChangePassWord, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if(response.trim().equals("success"))
                    {
                        Toast.makeText(activity_qlTaiKhoan.this, "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity_qlTaiKhoan.this,activity_qlTaiKhoan.class);
                        intent.putExtra("TenTaiKhoan",tenTK);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(activity_qlTaiKhoan.this, "Mật khẩu không đúng! Vui lòng nhập lại", Toast.LENGTH_SHORT).show();
                    }

                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(activity_qlTaiKhoan.this, "Xảy ra lỗi!", Toast.LENGTH_SHORT).show();
                        }
                    }
            ){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("TaiKhoan",tenTK);
                    params.put("MatKhau",mkCu);
                    params.put("MatKhauMoi",laiMKMoi);
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }
        else
        {
            Toast.makeText(this, "Mật khẩu mới nhập không khớp! Vui lòng nhập lại", Toast.LENGTH_SHORT).show();
        }
    }

    private void AnhXa() {
        tvtenNguoiDung =(TextView)findViewById(R.id.editTextTenNguoiDung);
        tvDiaChi =(TextView)findViewById(R.id.editTextDiaChi);
        tvSDT =(TextView)findViewById(R.id.editTextSDT);
        btCapNhapThongTin =(Button)findViewById(R.id.btnCapNhatThongTin1);
        btDoiMatKhuau =(Button)findViewById(R.id.btnDoiMatKhuau);
    }
    private void UpdateAccount(String tenND, String sDTND, String diaChiND) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlUpdateAccount, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("success"))
                {
                    Toast.makeText(activity_qlTaiKhoan.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(activity_qlTaiKhoan.this,activity_qlTaiKhoan.class);
                            intent.putExtra("TenTaiKhoan",tenTK);
                            startActivity(intent);
                }
                else
                {
                    Toast.makeText(activity_qlTaiKhoan.this, "Lỗi cập nhật", Toast.LENGTH_SHORT).show();
                }
            }

        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activity_qlTaiKhoan.this, "Lỗi "+error, Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("tentk",tenTK);
                params.put("tennguoidung",tenND);
                params.put("diachi",diaChiND);
                params.put("sdt",sDTND);
                return params;
            }
        };
        requestQueue.add(stringRequest);
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
                Intent intent = new Intent(activity_qlTaiKhoan.this, ManHinh_Customer.class);
                intent.putExtra("TenTaiKhoan",tenTK);
                startActivity(intent);
                break;
            case R.id.menuQuanLyTaiKhoan:
                Intent intent1 = new Intent(activity_qlTaiKhoan.this, activity_qlTaiKhoan.class);
                intent1.putExtra("TenTaiKhoan",tenTK);
                startActivity(intent1);
                Toast.makeText(this,"Bạn chọn Tài Khoản",Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuLichSuDonHang:
                Intent intent2 = new Intent(activity_qlTaiKhoan.this,activity_Lichsudonhang.class);
                intent2.putExtra("TenTaiKhoan",tenTK);
                startActivity(intent2);
                Toast.makeText(this,"Bạn chọn Lịch Sử Đơn Hàng",Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuDonHang:
                Intent intent3 = new Intent(activity_qlTaiKhoan.this,activity_Donhang.class);
                intent3.putExtra("TenTaiKhoan",tenTK);
                startActivity(intent3);
                Toast.makeText(this,"Bạn chọn Đơn Hàng",Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuDangXuatCTM:
                ManHinh_Customer.gioHangArrayList.clear();
                Intent intent4 = new Intent(activity_qlTaiKhoan.this, MainActivity.class);
                startActivity(intent4);
                Toast.makeText(this,"Bạn chọn Đăng Xuất",Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}