package com.example.shop10.LopSQL;

import java.io.Serializable;
import java.util.Date;

public class HoaDonBan implements Serializable {
    public int IDHoaDonBan,IDKhachHang,TinhTrang;
    public long TongTien;
    public String GhiChu,NgayBan;

    public HoaDonBan(int IDHoaDonBan, int IDKhachHang, int tinhTrang, long tongTien, String ghiChu, String ngayBan) {
        this.IDHoaDonBan = IDHoaDonBan;
        this.IDKhachHang = IDKhachHang;
        TinhTrang = tinhTrang;
        TongTien = tongTien;
        GhiChu = ghiChu;
        NgayBan = ngayBan;
    }

    public int getIDHoaDonBan() {
        return IDHoaDonBan;
    }

    public void setIDHoaDonBan(int IDHoaDonBan) {
        this.IDHoaDonBan = IDHoaDonBan;
    }

    public int getIDKhachHang() {
        return IDKhachHang;
    }

    public void setIDKhachHang(int IDKhachHang) {
        this.IDKhachHang = IDKhachHang;
    }

    public int getTinhTrang() {
        return TinhTrang;
    }

    public void setTinhTrang(int tinhTrang) {
        TinhTrang = tinhTrang;
    }

    public float getTongTien() {
        return TongTien;
    }

    public void setTongTien(long tongTien) {
        TongTien = tongTien;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String ghiChu) {
        GhiChu = ghiChu;
    }

    public String getNgayBan() {
        return NgayBan;
    }

    public void setNgayBan(String ngayBan) {
        NgayBan = ngayBan;
    }
}
