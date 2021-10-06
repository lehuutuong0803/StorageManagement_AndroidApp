package com.example.shop10.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.shop10.LopSQL.LoaiSP;
import com.example.shop10.ManHinh_User;
import com.example.shop10.ManHinh_qlLoaisp;
import com.example.shop10.R;
import com.example.shop10.activity_sua_loaisp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.shop10.common.ip;

public class LoaiSP_Adapter extends BaseAdapter {
    private ManHinh_qlLoaisp context;
    private int layout;
    private List<LoaiSP> loaiSPList;
    String urlDelete = ip +"/DeleteLoaiSP.php";


    public LoaiSP_Adapter(ManHinh_qlLoaisp context, int layout, List<LoaiSP> loaiSPList) {
        this.context = context;
        this.layout = layout;
        this.loaiSPList = loaiSPList;
    }

    @Override
    public int getCount() {
        return loaiSPList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        TextView tvTenLoaiSP,tvIDSP;
        ImageView imageViewDelete, imageViewEdit;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null)
        {
            holder = new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);
            holder.tvTenLoaiSP = (TextView)convertView.findViewById(R.id.textviewTenLoai);
            holder.tvIDSP = (TextView)convertView.findViewById(R.id.textviewIDLoaiSP);
            holder.imageViewDelete = (ImageView)convertView.findViewById((R.id.delete));
            holder.imageViewEdit = (ImageView)convertView.findViewById(R.id.edit);
            convertView.setTag(holder);
        }
        else
        {
            holder =(ViewHolder) convertView.getTag();
        }
        LoaiSP loaiSP = loaiSPList.get(position);
        holder.tvTenLoaiSP.setText(loaiSP.getTenLoaiSP());
        holder.tvIDSP.setText("ID LoaiSP: "+loaiSP.getIDLoaiSP());

        //bat su kien
        holder.imageViewEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, activity_sua_loaisp.class);
                intent.putExtra("dataLoaiSP",loaiSP);
                context.startActivity(intent);
            }
        });

        holder.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                XacNhanXoa(loaiSP.getTenLoaiSP(),loaiSP.getIDLoaiSP());
            }
        });
        return convertView;
    }

    private void XacNhanXoa(String ten,final int id)
    {
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(context);
        dialogXoa.setMessage("Bạn có muốn xóa loại sản phẩm "+ten+" không?");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.DeleteLoaiSP(id,urlDelete);

            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
dialog.cancel();
            }
        });
        dialogXoa.show();
    }
}
