package com.example.shop10.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
import com.example.shop10.Customer.ManHinh_Customer;
import com.example.shop10.LopSQL.CTHD;
import com.example.shop10.LopSQL.HoaDonBan;
import com.example.shop10.LopSQL.SP;
import com.example.shop10.ManHinh_qlHoadonban;
import com.example.shop10.ManHinh_qlSp;
import com.example.shop10.R;
import com.example.shop10.activity_xemchitiet_hdban;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.shop10.common.ip;

public class CTHD_Adapter extends BaseAdapter {
    private activity_xemchitiet_hdban context;
    private int layout;
    private ArrayList<SP> spArrayList;
    private List<CTHD> cthdList;
    private List<SP> spList;
    String tenSP,anhSP;
    String urlSearchCTHD = ip +"/SearchIDProduct.php";
    public CTHD_Adapter(activity_xemchitiet_hdban context, int layout, List<CTHD> cthdList,List<SP> spList) {
        this.context = context;
        this.layout = layout;
        this.cthdList = cthdList;
        this.spList = spList;

    }

    @Override
    public int getCount() {
        return cthdList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        TextView tvtenSP, tvgiaBan, tvsoLuong, tvthanhTien;
        ImageView imageViewAnhSP1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CTHD cthd = cthdList.get(position);
        int idsp = cthd.getIDSanPham();
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);
            holder.tvtenSP = (TextView) convertView.findViewById(R.id.textviewTenSPCTHD);
            holder.tvgiaBan = (TextView) convertView.findViewById(R.id.textviewGiaBanSPCTHD);
            holder.tvsoLuong = (TextView) convertView.findViewById(R.id.textviewSoLuongSPCTHD);
            holder.tvthanhTien = (TextView) convertView.findViewById(R.id.textviewThanhTienSPCTHD);
            holder.imageViewAnhSP1 = (ImageView) convertView.findViewById(R.id.imageViewAnhSPCTHD);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvsoLuong.setText("Số Lượng: "+String.valueOf(cthd.getSoLuong()));
        holder.tvgiaBan.setText("Đơn Giá: "+String.valueOf(cthd.getDonGia()));
        holder.tvthanhTien.setText("Thành Tiền: "+String.valueOf(cthd.getThanhTien()));

        for(int i=0;i<spList.size();i++)
        {
            SP sp1 = spList.get(i);
            if(idsp == sp1.getIdsp())
            {
                holder.tvtenSP.setText(sp1.getTensp());
                Picasso.get().load(sp1.getHinhanhsp()).into(holder.imageViewAnhSP1);
            }
        }

        return convertView;
    }

}
