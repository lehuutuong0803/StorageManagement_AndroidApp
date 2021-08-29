package com.example.shop10.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.shop10.Customer.activity_Lichsudonhang;
import com.example.shop10.LopSQL.HoaDonBan;
import com.example.shop10.R;
import com.example.shop10.activity_xemchitiet_hdban;

import java.util.List;

public class LichSuDonHang_Adapter extends BaseAdapter {
    private activity_Lichsudonhang context;
    private int layout;
    private List<HoaDonBan> hoaDonBanList;

    public LichSuDonHang_Adapter(activity_Lichsudonhang context, int layout, List<HoaDonBan> hoaDonBanList) {
        this.context = context;
        this.layout = layout;
        this.hoaDonBanList = hoaDonBanList;
    }

    @Override
    public int getCount() {
        return hoaDonBanList.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
          ViewHolder holder ;
        if(convertView == null)
        {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView =inflater.inflate(layout,null);
            holder.iDHDBan = (TextView)convertView.findViewById(R.id.textviewIDHDBanLSDH);
            holder.iDKH =(TextView)convertView.findViewById(R.id.textviewIDKHLSDH);
            holder.ngayDat =(TextView)convertView.findViewById(R.id.textviewNgayDatLSDH);
            holder.tongTien =(TextView)convertView.findViewById(R.id.textviewTongTienLSDH);
            holder.ghiChu =(TextView)convertView.findViewById(R.id.textviewGhiChuLSDH);
            holder.btnXemChiTiet =(Button) convertView.findViewById(R.id.btXemChiTietLSDH);
            holder.btnHoanThanh =(Button) convertView.findViewById(R.id.btHoanThanhLSDH);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        HoaDonBan hoaDonBan = hoaDonBanList.get(position);
        holder.iDHDBan.setText("ID Hóa Đơn: "+hoaDonBan.getIDHoaDonBan());
        holder.iDKH.setText("ID Khách Hàng: "+hoaDonBan.getIDKhachHang());
        holder.ngayDat.setText("Ngày Đặt: "+hoaDonBan.getNgayBan());
        holder.ghiChu.setText("Ghi Chú: "+hoaDonBan.getGhiChu());
        holder.tongTien.setText("Tổng Tiền: "+hoaDonBan.getTongTien());

        if(hoaDonBan.getTinhTrang()!=0)
        {
            holder.btnHoanThanh.setText("Đã Hoàn Thành");
        }
        else
        {
            holder.btnHoanThanh.setText("Đơn Đang HT");
        }
        holder.btnXemChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, activity_xemchitiet_hdban.class);
                intent.putExtra("dataHDBan",hoaDonBan);
                context.startActivity(intent);
            }
        });
        return convertView;
    }
}
