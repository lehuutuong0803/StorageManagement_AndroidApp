package com.example.shop10.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shop10.LopSQL.HoaDonBan;
import com.example.shop10.LopSQL.LoaiSP;
import com.example.shop10.ManHinh_qlHoadonban;
import com.example.shop10.ManHinh_qlLoaisp;
import com.example.shop10.ManHinh_qlSp;
import com.example.shop10.R;
import com.example.shop10.activity_xemchitiet_hdban;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.shop10.common.ip;

public class HoaDonBan_Adapter extends BaseAdapter {
    private ManHinh_qlHoadonban context;
    private int layout;
    private List<HoaDonBan> loaiHDBanList;
    private String urlUpdate = ip +"/UpdateInvoice.php";

    public HoaDonBan_Adapter(ManHinh_qlHoadonban context, int layout, List<HoaDonBan> loaiHDBanList) {
        this.context = context;
        this.layout = layout;
        this.loaiHDBanList = loaiHDBanList;
    }

    @Override
    public int getCount() {
        return loaiHDBanList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder
    {
        TextView iDHDBan,iDKH,ngayDat,tongTien,ghiChu;
        Button btnXemChiTiet, btnHoanThanh;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder ;
        if(convertView == null)
        {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView =inflater.inflate(layout,null);
            holder.iDHDBan = (TextView)convertView.findViewById(R.id.textviewIDHDBan);
            holder.iDKH =(TextView)convertView.findViewById(R.id.textviewIDKH);
            holder.ngayDat =(TextView)convertView.findViewById(R.id.textviewNgayDat);
            holder.tongTien =(TextView)convertView.findViewById(R.id.textviewTongTien);
            holder.ghiChu =(TextView)convertView.findViewById(R.id.textviewGhiChu);
            holder.btnXemChiTiet =(Button) convertView.findViewById(R.id.btXemChiTiet);
            holder.btnHoanThanh =(Button)convertView.findViewById(R.id.btHoanThanh);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        HoaDonBan hoaDonBan = loaiHDBanList.get(position);
        holder.iDHDBan.setText("ID Hóa Đơn: "+hoaDonBan.getIDHoaDonBan());
        holder.iDKH.setText("ID Khách Hàng: "+hoaDonBan.getIDKhachHang());
        holder.ngayDat.setText("Ngày Đặt: "+hoaDonBan.getNgayBan());
        holder.ghiChu.setText("Ghi Chú: "+hoaDonBan.getGhiChu());
        holder.tongTien.setText("Tổng Tiền: "+hoaDonBan.getTongTien());

        int x = hoaDonBan.getTinhTrang();
        if(x > 0 )
        {
            holder.btnHoanThanh.setText("Đã Hoàn Thành");
        }
        else
        {
            holder.btnHoanThanh.setText("Đơn Đang HT");
        }
        //Bat su kien
        holder.btnXemChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, activity_xemchitiet_hdban.class);
                intent.putExtra("dataHDBan",hoaDonBan);
                context.startActivity(intent);

            }
        });
        holder.btnHoanThanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hoaDonBan.getTinhTrang()==0)
                {
                    AlertDialog.Builder dialogHTDon = new AlertDialog.Builder(context);
                    dialogHTDon.setMessage("Bạn có chắc Hóa Đơn Bán đã hoàn thành không?");
                    dialogHTDon.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            holder.btnHoanThanh.setText("Đã Hoàn Thành");
                            UpdateCondition(hoaDonBan.getIDHoaDonBan());
                            context.GetData();
                        }
                    });
                    dialogHTDon.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    dialogHTDon.show();
                }
                else
                {
                    Toast.makeText(context, "Hóa Đơn đã hoàn thành!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return convertView;
    }
    private void UpdateCondition(int idHoaDonBan) {
        int i = 1;
        RequestQueue requestQueue  = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlUpdate, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.trim().equals("success"))
                {
                    Toast.makeText(context, "Cập nhập tình trạng đơn thành công!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(context, "Lỗi cập nhật đơn", Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Lỗi", Toast.LENGTH_SHORT).show();
                    }
    }
    ){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<>();
                param.put("idhd",String.valueOf(idHoaDonBan));
                param.put("tinhtrang",String.valueOf(i));
                return param;
            }
        };
        requestQueue.add(stringRequest);
}
}
