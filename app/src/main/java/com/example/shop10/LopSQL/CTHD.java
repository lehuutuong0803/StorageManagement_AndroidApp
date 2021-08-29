package com.example.shop10.LopSQL;

import java.io.Serializable;

public class CTHD implements Serializable {
    public int IDHoaDonBan1,IDSanPham,SoLuong,DonGia,ThanhTien;


    public CTHD(int IDHoaDonBan1, int IDSanPham, int soLuong, int donGia, int thanhTien) {
        this.IDHoaDonBan1 = IDHoaDonBan1;
        this.IDSanPham = IDSanPham;
        SoLuong = soLuong;
        DonGia = donGia;
        ThanhTien = thanhTien;
    }

    public int getIDHoaDonBan1() {
        return IDHoaDonBan1;
    }

    public void setIDHoaDonBan1(int IDHoaDonBan1) {
        this.IDHoaDonBan1 = IDHoaDonBan1;
    }

    public int getIDSanPham() {
        return IDSanPham;
    }

    public void setIDSanPham(int IDSanPham) {
        this.IDSanPham = IDSanPham;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }

    public int getDonGia() {
        return DonGia;
    }

    public void setDonGia(int donGia) {
        DonGia = donGia;
    }

    public float getThanhTien() {
        return ThanhTien;
    }

    public void setThanhTien(int thanhTien) {
        ThanhTien = thanhTien;
    }
}
