package com.example.shop10.Customer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shop10.Adapter.GioHang_Adapter;
import com.example.shop10.LopSQL.GioHang;
import com.example.shop10.LopSQL.User;
import com.example.shop10.MainActivity;
import com.example.shop10.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.shop10.common.ip;

public class activity_Donhang extends AppCompatActivity {
    GioHang_Adapter gioHang_adapter;
    ListView lvGioHang;
    TextView tvThongBao;
    static TextView tvTongTien;
    Button btnDatHang, btnTiepTucMuaHang;
    EditText editTextGhiChu;
    String tenTK;
    String urlCreateInvoice = ip +"/CreateInvoice.php";
    String urlGetData = ip +"/SearchAccount.php";
    String urlInsertDetailsInvoice = ip +"/InsertDetailsInvoice.php";
    String urlUpdateQuantity = ip +"/UpdateQuantity.php";
    ArrayList<User> userArrayList;
    String idHD;
    static long tongTien;
    String idKH ="";
    Date currentTime = Calendar.getInstance().getTime();
    int gtri = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__donhang);
        Intent intent = getIntent();
        tenTK = (String) intent.getSerializableExtra("TenTaiKhoan");

        Toast.makeText(this, "Ten KH111: "+tenTK, Toast.LENGTH_SHORT).show();
        userArrayList = new ArrayList<>();
        AnhXa();

        if(ManHinh_Customer.gioHangArrayList.isEmpty())
        {
            tvThongBao.setText("Bạn không có SP nào trong giỏ hàng!");
        }
        else {

            gioHang_adapter = new GioHang_Adapter(this,R.layout.dong_giohang, ManHinh_Customer.gioHangArrayList);
            lvGioHang.setAdapter(gioHang_adapter);
            ControlButton1();
        }
        ControlButton();
    }

    private void ControlButton() {
        btnDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue requestQueue = Volley.newRequestQueue(activity_Donhang.this);
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
                            CreateInvoice(idKH);
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
        });
        btnTiepTucMuaHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_Donhang.this,ManHinh_Customer.class);
                intent.putExtra("TenTaiKhoan",tenTK);
                startActivity(intent);
            }
        });
        lvGioHang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder dialogXoa = new AlertDialog.Builder(activity_Donhang.this);
                dialogXoa.setMessage("Bạn có muốn xóa Sản Phẩm  không?");
                dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ManHinh_Customer.gioHangArrayList.remove(position);
                        gioHang_adapter.notifyDataSetChanged();
                    }
                });
                dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                dialogXoa.show();
                return false;
            }
        });
    }

    private void CreateInvoice(String idKH1) {
        RequestQueue requestQueue = Volley.newRequestQueue(activity_Donhang.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlCreateInvoice, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                idHD = response;
                InsertDetailsInvoice(idHD);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("idkh", idKH1);
                params.put("ngayban", String.valueOf(currentTime));
                params.put("tongtien", String.valueOf(tongTien));
                params.put("ghichu",editTextGhiChu.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void InsertDetailsInvoice(String idHD) {
       for (int i = 0;i<ManHinh_Customer.gioHangArrayList.size();i++)
       {
           GioHang gioHang = ManHinh_Customer.gioHangArrayList.get(i);
           RequestQueue requestQueue = Volley.newRequestQueue(this);
           int finalI = i;
           StringRequest stringRequest = new StringRequest(Request.Method.POST, urlInsertDetailsInvoice, new Response.Listener<String>() {
                   @Override
                   public void onResponse(String response) {
                       if (response.equals("success"))
                       {
                           UpdateQuantity(ManHinh_Customer.gioHangArrayList.get(finalI));
                       }
                       else
                       {
                           Toast.makeText(activity_Donhang.this, "Lỗi tạo đơn hàng!", Toast.LENGTH_SHORT).show();
                       }
                   }
               }, new Response.ErrorListener() {
                   @Override
                   public void onErrorResponse(VolleyError error) {
                       Toast.makeText(activity_Donhang.this, "Lỗi", Toast.LENGTH_SHORT).show();

                   }
               }){
                   @Nullable
                   @Override
                   protected Map<String, String> getParams() throws AuthFailureError {
                       Map<String,String> params = new HashMap<>();
                       params.put("idhdb",idHD);
                       params.put("idsp", String.valueOf(gioHang.getIdSP()));
                       params.put("soluongban", String.valueOf(gioHang.getSoLuong()));
                       params.put("dongiaban", String.valueOf(gioHang.getGiaSPBan()));
                       params.put("thanhtien", String.valueOf(gioHang.getGiaSP()));
                       return params;
                   }
               };
               requestQueue.add(stringRequest);
       }
    }

    private void UpdateQuantity(GioHang gioHang) {
        int slUpdate = gioHang.getSoLuongSPCon() - gioHang.getSoLuong();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlUpdateQuantity, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("success"))
                {
                    Toast.makeText(activity_Donhang.this, "Cập nhật số lượng thành công", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(activity_Donhang.this, "Lỗi Cập nhật soluong", Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activity_Donhang.this, "Lỗi", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("idsp",String.valueOf(gioHang.getIdSP()));
                params.put("soluongsp",String.valueOf(slUpdate));
                return params;
            }
        };
        requestQueue.add(stringRequest);
      gtri++;
      Notify(gtri);
    }

    private void Notify(int gtri1) {
        if(gtri1 == ManHinh_Customer.gioHangArrayList.size())
        {
            ManHinh_Customer.gioHangArrayList.clear();
            Toast.makeText(this, "Tạo đơn hàng thành công!", Toast.LENGTH_SHORT).show();
            Intent intent3 = new Intent(activity_Donhang.this,activity_Donhang.class);
            intent3.putExtra("TenTaiKhoan",tenTK);
            startActivity(intent3);
        }
    }

    public static void ControlButton1() {
        tongTien=0;
        for(int i=0;i<ManHinh_Customer.gioHangArrayList.size();i++)
        {
            tongTien = tongTien + ManHinh_Customer.gioHangArrayList.get(i).getGiaSP();
        }
        tvTongTien.setText("Tổng Tiền:   "+tongTien+"$");
    }
    private void AnhXa() {
        lvGioHang = (ListView)findViewById(R.id.lvSPGioHang);
        tvThongBao = (TextView) findViewById(R.id.textviewThongBaoGioHang);
        tvTongTien = (TextView)findViewById(R.id.textViewTongTienDonHang);
        btnDatHang = (Button) findViewById(R.id.btnDatHang);
        btnTiepTucMuaHang = (Button)findViewById(R.id.btnTiepTucMuaHang);
        editTextGhiChu =(EditText)findViewById(R.id.editTextGhiChuDonHang);

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
                Intent intent = new Intent(activity_Donhang.this, ManHinh_Customer.class);
                intent.putExtra("TenTaiKhoan",tenTK);
                startActivity(intent);
                break;
            case R.id.menuQuanLyTaiKhoan:
                Intent intent1 = new Intent(activity_Donhang.this, activity_qlTaiKhoan.class);
                intent1.putExtra("TenTaiKhoan",tenTK);
                startActivity(intent1);
                Toast.makeText(this,"Bạn chọn Tài Khoản",Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuLichSuDonHang:
                Intent intent2 = new Intent(activity_Donhang.this,activity_Lichsudonhang.class);
                intent2.putExtra("TenTaiKhoan",tenTK);
                startActivity(intent2);
                Toast.makeText(this,"Bạn chọn Lịch Sử Đơn Hàng",Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuDonHang:
                Intent intent3 = new Intent(activity_Donhang.this,activity_Donhang.class);
                intent3.putExtra("TenTaiKhoan",tenTK);
                startActivity(intent3);
                Toast.makeText(this,"Bạn chọn Đơn Hàng",Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuDangXuatCTM:
                ManHinh_Customer.gioHangArrayList.clear();
                Intent intent4 = new Intent(activity_Donhang.this, MainActivity.class);
                startActivity(intent4);
                Toast.makeText(this,"Bạn chọn Đăng Xuất",Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}