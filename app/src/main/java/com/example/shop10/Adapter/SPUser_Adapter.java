package com.example.shop10.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shop10.Customer.ManHinh_Customer;
import com.example.shop10.LopSQL.SP;
import com.example.shop10.MainActivity;
import com.example.shop10.ManHinh_User;
import com.example.shop10.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SPUser_Adapter extends BaseAdapter {
    private ManHinh_User context;
    private int layout;
    private List<SP> spList;

    public SPUser_Adapter(ManHinh_User context, int layout, List<SP> spList) {
        this.context = context;
        this.layout = layout;
        this.spList = spList;
    }

    @Override
    public int getCount() {
        return spList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    private class ViewHodel
    {
        ImageView imageViewAnhSP;
        TextView textViewTenSP, textViewSoLuongCon, textViewGiaBan;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodel hodel;
        if(convertView == null)
        {
            hodel = new ViewHodel();
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);

            hodel.imageViewAnhSP = (ImageView)convertView.findViewById(R.id.imageAnhSPCTM);
            hodel.textViewGiaBan=(TextView)convertView.findViewById(R.id.textViewGiaBanCTM);
            hodel.textViewSoLuongCon=(TextView)convertView.findViewById(R.id.textViewSoLuongCTM);
            hodel.textViewTenSP=(TextView)convertView.findViewById(R.id.textViewTenSPCTM);
            convertView.setTag(hodel);
        }
        else {
            hodel = (ViewHodel) convertView.getTag();
        }

        SP sp = spList.get(position);
        hodel.textViewTenSP.setText("Tên SP: "+sp.getTensp());
        hodel.textViewGiaBan.setText("Giá Bán: "+String.valueOf(sp.getGiaban()));
        hodel.textViewSoLuongCon.setText("Số Lượng Còn: "+String.valueOf(sp.getSoluongsp()));
        Picasso.get().load(sp.getHinhanhsp()).into(hodel.imageViewAnhSP);

        return convertView;
    }
}
