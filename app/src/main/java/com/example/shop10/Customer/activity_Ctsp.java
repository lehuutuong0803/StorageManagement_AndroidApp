package com.example.shop10.Customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shop10.LopSQL.GioHang;
import com.example.shop10.LopSQL.SP;
import com.example.shop10.MainActivity;
import com.example.shop10.R;
import com.squareup.picasso.Picasso;

public class activity_Ctsp extends AppCompatActivity {

    SP sp;
    ImageView imageViewAnhSP;
    TextView textViewTenSP, textViewSoLuongCon, textViewGiaBan;
    Button btnThemSPGH;
    Spinner spinner;
    ArrayAdapter spinnerAdapter;
    String tenTK;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__ctsp);
        Intent intent = getIntent();
        sp = (SP) intent.getSerializableExtra("ThongTinSP");
        tenTK = (String) intent.getSerializableExtra("TenTaiKhoan");
        AnhXa();

        Toast.makeText(this, "Tên Tài Khoản: "+tenTK, Toast.LENGTH_SHORT).show();
        textViewTenSP.setText("Tên SP: "+sp.getTensp());
        textViewGiaBan.setText("Giá Bán: "+String.valueOf(sp.getGiaban()));
        textViewSoLuongCon.setText("Số Lượng Còn: "+String.valueOf(sp.getSoluongsp()));
        Picasso.get().load(sp.getHinhanhsp()).into(imageViewAnhSP);

        //spinner
        Integer[] soLuong = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, soLuong);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner.setAdapter(spinnerAdapter);
        Toast.makeText(this, sp.getTensp(), Toast.LENGTH_SHORT).show();
        ControlButton();
    }

    private void ControlButton() {
        btnThemSPGH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sl = Integer.parseInt(spinner.getSelectedItem().toString());
                if(sl > sp.getSoluongsp())
                {
                    Toast.makeText(activity_Ctsp.this, "Số lượng sản phẩm chỉ còn "+sp.getSoluongsp()+" . Vui lòng chọn lại! ", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(ManHinh_Customer.gioHangArrayList.size()>0)
                    {

                        boolean exists = false;
                        int giaban = (int) sp.getGiaban();
                        for(int i = 0;i<ManHinh_Customer.gioHangArrayList.size();i++)
                        {
                            if(ManHinh_Customer.gioHangArrayList.get(i).getIdSP() ==  sp.getIdsp())
                            {
                                int slmoi = ManHinh_Customer.gioHangArrayList.get(i).getSoLuong()+sl;
                                if(slmoi >sp.getSoluongsp() )
                                {
                                    Toast.makeText(activity_Ctsp.this, "Số lượng sản phẩm chỉ còn "+sp.getSoluongsp()+".Bạn đã chọn "+ManHinh_Customer.gioHangArrayList.get(i).getSoLuong()+". Vui lòng chọn lại!ư", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    ManHinh_Customer.gioHangArrayList.get(i).setSoLuong(ManHinh_Customer.gioHangArrayList.get(i).getSoLuong()+sl);
                                    if(ManHinh_Customer.gioHangArrayList.get(i).getSoLuong()>=10)
                                    {
                                        ManHinh_Customer.gioHangArrayList.get(i).setSoLuong(10);
                                    }
                                    ManHinh_Customer.gioHangArrayList.get(i).setGiaSP(ManHinh_Customer.gioHangArrayList.get(i).getSoLuong()*giaban);
                                    Toast.makeText(activity_Ctsp.this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(activity_Ctsp.this, ManHinh_Customer.class);
                                    intent.putExtra("TenTaiKhoan",tenTK);
                                    startActivity(intent);
                                }
                                exists = true;
                            }
                        }
                        if(exists == false)
                        {
                            int sl1 = Integer.parseInt(spinner.getSelectedItem().toString());
                            int giaTien = sl1 * giaban;
                            ManHinh_Customer.gioHangArrayList.add(new GioHang(sp.getIdsp(),giaTien,sl1,sp.getTensp(),sp.hinhanhsp, (int) sp.getGiaban(), (int) sp.getSoluongsp()));
                            Toast.makeText(activity_Ctsp.this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(activity_Ctsp.this, ManHinh_Customer.class);
                            intent.putExtra("TenTaiKhoan",tenTK);
                            startActivity(intent);


                        }
                    }
                    else {
                        int giaban = (int) sp.getGiaban();
                        int giaTien = sl * giaban;
                        ManHinh_Customer.gioHangArrayList.add(new GioHang(sp.getIdsp(),giaTien,sl,sp.getTensp(),sp.hinhanhsp,(int) sp.getGiaban(), (int) sp.getSoluongsp()));
                        Toast.makeText(activity_Ctsp.this, "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity_Ctsp.this, ManHinh_Customer.class);
                        intent.putExtra("TenTaiKhoan",tenTK);
                        startActivity(intent);
                    }
                }


            }
        });
    }

    private void AnhXa() {
        textViewGiaBan =(TextView)findViewById(R.id.textViewCTGiaBanCTM);
        textViewSoLuongCon =(TextView)findViewById(R.id.textViewCTSoLuongCTM);
        textViewTenSP =(TextView)findViewById(R.id.textViewTenCTSPCTM);
        imageViewAnhSP =(ImageView)findViewById(R.id.imageCTAnhSPCTM);
        btnThemSPGH = (Button)findViewById(R.id.btnThemSPVaoGioHang);
        spinner = (Spinner)findViewById(R.id.spinnerSoLuong);
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
                Intent intent = new Intent(activity_Ctsp.this, ManHinh_Customer.class);
                intent.putExtra("TenTaiKhoan",tenTK);
                startActivity(intent);
                break;
            case R.id.menuQuanLyTaiKhoan:
                Intent intent1 = new Intent(activity_Ctsp.this, activity_qlTaiKhoan.class);
                intent1.putExtra("TenTaiKhoan",tenTK);
                startActivity(intent1);
                Toast.makeText(this,"Bạn chọn Tài Khoản",Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuLichSuDonHang:
                Intent intent2 = new Intent(activity_Ctsp.this,activity_Lichsudonhang.class);
                intent2.putExtra("TenTaiKhoan",tenTK);
                startActivity(intent2);
                Toast.makeText(this,"Bạn chọn Lịch Sử Đơn Hàng",Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuDonHang:
                Intent intent3 = new Intent(activity_Ctsp.this,activity_Donhang.class);
                intent3.putExtra("TenTaiKhoan",tenTK);
                startActivity(intent3);
                Toast.makeText(this,"Bạn chọn Đơn Hàng",Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuDangXuatCTM:
                ManHinh_Customer.gioHangArrayList.clear();
                Intent intent4 = new Intent(activity_Ctsp.this, MainActivity.class);
                startActivity(intent4);
                Toast.makeText(this,"Bạn chọn Đăng Xuất",Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}