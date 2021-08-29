package com.example.shop10.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shop10.Customer.ManHinh_Customer;
import com.example.shop10.Customer.activity_Donhang;
import com.example.shop10.LopSQL.GioHang;
import com.example.shop10.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GioHang_Adapter extends BaseAdapter {
    private activity_Donhang context;
    private int layout;
    private List<GioHang> gioHangList;

    public GioHang_Adapter(activity_Donhang context, int layout, List<GioHang> gioHangList) {
        this.context = context;
        this.layout = layout;
        this.gioHangList = gioHangList;
    }

    @Override
    public int getCount() {
        return gioHangList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    private class Viewholder{
        ImageView imageViewAnhSP;
        TextView tvtenSP,tvgiaTien,tvgiaBan;
        Button btnTruSL, btnCongSL, btnGiaTri;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Viewholder holder;
        if(convertView == null)
        {
            holder = new Viewholder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView =inflater.inflate(layout,null);
            holder.imageViewAnhSP =(ImageView)convertView.findViewById(R.id.imageViewAnhGioHang);
            holder.tvtenSP =(TextView) convertView.findViewById(R.id.textViewTenSPGioHang);
            holder.tvgiaTien =(TextView) convertView.findViewById(R.id.textViewGiaSPGioHang);
            holder.btnCongSL =(Button)convertView.findViewById(R.id.btnCongSoLuong);
            holder.btnGiaTri =(Button)convertView.findViewById(R.id.btnGiaTriSoLuong);
            holder.btnTruSL =(Button)convertView.findViewById(R.id.btnTruSoLuong);
            holder.tvgiaBan =(TextView)convertView.findViewById(R.id.textViewGiaSPBanGioHang);
            convertView.setTag(holder);
        }
        else
        {
            holder = (Viewholder) convertView.getTag();
        }
        GioHang gioHang = gioHangList.get(position);
        Picasso.get().load(gioHang.getHinhSP()).into(holder.imageViewAnhSP);
        holder.tvtenSP.setText("Tên SP:   "+gioHang.getTenSP());
        holder.tvgiaTien.setText("Thành Tiền:   "+gioHang.getGiaSP()+"$");
        holder.btnGiaTri.setText(String.valueOf(gioHang.getSoLuong()));
        holder.tvgiaBan.setText("Giá Bán:   "+gioHang.getGiaSPBan()+"$");

        holder.btnTruSL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ManHinh_Customer.gioHangArrayList.get(position).getSoLuong()>0) {
                    gioHangList.get(position).setSoLuong(gioHangList.get(position).getSoLuong()-1);
                    int thanhTien = gioHang.getGiaSPBan()*gioHangList.get(position).getSoLuong();
                    gioHangList.get(position).setGiaSP(thanhTien);
                    activity_Donhang.ControlButton1();
                    holder.tvgiaTien.setText("Thành Tiền:   "+thanhTien+"$");
                    holder.btnGiaTri.setText(String.valueOf(gioHangList.get(position).getSoLuong()));
                }
                else
                {
                    Toast.makeText(context, "Số lượng Sản Phảm đã về 0!", Toast.LENGTH_SHORT).show();
                }


            }
        });
        holder.btnCongSL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int x = gioHangList.get(position).getSoLuong()+1;
                if(x > gioHangList.get(position).getSoLuongSPCon())
                {
                    Toast.makeText(context, "Số lượng sản phẩm '"+gioHangList.get(position).getTenSP()+"' chỉ còn "+gioHangList.get(position).getSoLuongSPCon()+" SP!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(ManHinh_Customer.gioHangArrayList.get(position).getSoLuong()<10) {
                        gioHangList.get(position).setSoLuong(gioHangList.get(position).getSoLuong()+1);
                        int thanhTien = gioHang.getGiaSPBan()*gioHangList.get(position).getSoLuong();
                        gioHangList.get(position).setGiaSP(thanhTien);
                        activity_Donhang.ControlButton1();
                        holder.tvgiaTien.setText("Thành Tiền:   "+thanhTien+"$");
                        holder.btnGiaTri.setText(String.valueOf(gioHangList.get(position).getSoLuong()));
                    }
                    else
                    {
                        Toast.makeText(context, "Số lượng Sản Phảm đã chạm giới hạn!", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        return convertView;
    }
}
