package com.example.shop10;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.shop10.Adapter.HoaDonBan_Adapter;
import com.example.shop10.Adapter.LoaiSP_Adapter;
import com.example.shop10.LopSQL.HoaDonBan;
import com.example.shop10.LopSQL.LoaiSP;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.shop10.common.ip;

public class ManHinh_qlHoadonban extends AppCompatActivity {

    ListView lvHDBan;
    ArrayList<HoaDonBan> hDBanArrayList;
    HoaDonBan_Adapter hDBanAdapter;
   String urlGetDataInvoice = ip + "/GetDataInvoice.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_ql_hoadonban);
        AnhXa();

        hDBanArrayList = new ArrayList<>();
        hDBanAdapter = new HoaDonBan_Adapter(this,R.layout.dong_hdban,hDBanArrayList);
        lvHDBan.setAdapter(hDBanAdapter);

        GetData();
    }


    public void GetData ()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, urlGetDataInvoice, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                hDBanArrayList.clear();

                for(int i = 0;i<response.length();i++)
                {
                    try {
                        JSONObject object = response.getJSONObject(i);
                        hDBanArrayList.add(new HoaDonBan(
                                object.getInt("IDHD"),
                                object.getInt("IDKH"),
                                object.getInt("TinhTrang1"),
                                object.getLong("TongTien1"),
                                object.getString("GhiChu1"),
                                object.getString("NgayBan1")));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                hDBanAdapter.notifyDataSetChanged();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(ManHinh_qlHoadonban.this, "Lỗi", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    private void AnhXa() {
        lvHDBan =(ListView)findViewById(R.id.lvHDBan);
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
                Intent intent = new Intent(ManHinh_qlHoadonban.this,ManHinh_User.class);
                startActivity(intent);
                break;
            case R.id.menuQuanLyLoaiSP:
                Intent intent1 = new Intent(ManHinh_qlHoadonban.this,ManHinh_qlLoaisp.class);
                startActivity(intent1);
                Toast.makeText(this,"Bạn chọn Quản Lý Loại SP",Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuQuanLySP:
                Intent intent2 = new Intent(ManHinh_qlHoadonban.this,ManHinh_qlSp.class);
                startActivity(intent2);
                Toast.makeText(this,"Bạn chọn Quản Lý SP",Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuHoaDonBan:
                Intent intent3 = new Intent(ManHinh_qlHoadonban.this,ManHinh_qlHoadonban.class);
                startActivity(intent3);
                Toast.makeText(this,"Bạn chọn Quản Lý Hóa Đơn Bán",Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuDangXuat:
                Intent intent4 = new Intent(ManHinh_qlHoadonban.this,MainActivity.class);
                startActivity(intent4);
                Toast.makeText(this,"Bạn chọn Đăng Xuất",Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}