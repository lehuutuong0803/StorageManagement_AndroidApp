package com.example.shop10.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shop10.LopSQL.SP;
import com.example.shop10.ManHinh_qlSp;
import com.example.shop10.R;
import com.example.shop10.activity_sua_loaisp;
import com.example.shop10.activity_sua_sp;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.shop10.common.ip;

public class SP_Adapter extends BaseAdapter {
    private ManHinh_qlSp context;
    private int layout;
    private List<SP> spList;
    String urlDeleteProduct = ip +"/DeleteProduct.php";

    public SP_Adapter(ManHinh_qlSp context, int layout, List<SP> spList) {
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

    private class ViewHolder{
        TextView textViewIDSP, textViewTenSP,textViewGiaBan,textViewGiaNhap,textViewSoLuong,textViewAnh,textViewIDLoaiSP1;
        ImageView imageViewDelete, imageViewEdit,imageViewAnhSP;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null)
        {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);
            holder.textViewIDSP = (TextView)convertView.findViewById(R.id.textviewIDSP);
            holder.textViewTenSP = (TextView)convertView.findViewById(R.id.textviewTenSP);
            holder.textViewGiaBan = (TextView)convertView.findViewById(R.id.textviewGiaBanSP);
            holder.textViewGiaNhap = (TextView)convertView.findViewById(R.id.textviewGiaNhapSP);
            holder.textViewSoLuong = (TextView)convertView.findViewById(R.id.textviewSoLuong);
            holder.textViewIDLoaiSP1 = (TextView)convertView.findViewById(R.id.textviewIDLoaiSP1);
            holder.imageViewAnhSP = (ImageView)convertView.findViewById(R.id.imageViewHinhAnh);
            holder.imageViewDelete = (ImageView)convertView.findViewById((R.id.delete));
            holder.imageViewEdit = (ImageView)convertView.findViewById(R.id.edit);

            convertView.setTag(holder);
        }
        else {
          holder = (ViewHolder) convertView.getTag();
        }

        SP sp = spList.get(position);
        holder.textViewTenSP.setText(sp.getTensp());
        holder.textViewIDSP.setText("ID SP: "+sp.getIdsp());
        holder.textViewGiaNhap.setText("Gi?? Nh??p: "+sp.getGianhap());
        holder.textViewGiaBan.setText("Gi?? B??n: "+sp.getGiaban());
        holder.textViewSoLuong.setText("S??? L?????ng: "+sp.getSoluongsp());
        holder.textViewIDLoaiSP1.setText("ID Lo???i SP: "+sp.getIdloaisp());
        //hi???n th??? ???nh
        Picasso.get().load(sp.getHinhanhsp()).into(holder.imageViewAnhSP);

        //B???t s??? ki???n
        holder.imageViewEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, activity_sua_sp.class);
                intent.putExtra("dataSP",sp);
                context.startActivity(intent);
            }
        });

        holder.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XacNhanXoa(sp.getTensp(),sp.idsp);
            }
        });

        return convertView;
    }

    private  void XacNhanXoa(String ten, final int id)
    {
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(context);
        dialogXoa.setMessage("B???n c?? mu???n x??a S???n Ph???m "+ten+" kh??ng?");
        dialogXoa.setPositiveButton("C??", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.DeleteSP(id,urlDeleteProduct);
            }
        });
        dialogXoa.setNegativeButton("Kh??ng", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialogXoa.show();
    }
}
