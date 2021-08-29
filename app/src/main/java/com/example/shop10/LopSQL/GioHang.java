package com.example.shop10.LopSQL;

import java.io.Serializable;

public class GioHang implements Serializable {
    public int idSP,giaSP,soLuong,giaSPBan,soLuongSPCon;
    public String tenSP,hinhSP;

    public GioHang(int idSP, int giaSP, int soLuong, String tenSP, String hinhSP,int giaSPBan,int soLuongSPCon) {
        this.idSP = idSP;
        this.giaSP = giaSP;
        this.soLuong = soLuong;
        this.tenSP = tenSP;
        this.hinhSP = hinhSP;
        this.giaSPBan = giaSPBan;
        this.soLuongSPCon = soLuongSPCon;
    }

    public int getSoLuongSPCon() {
        return soLuongSPCon;
    }

    public void setSoLuongSPCon(int soLuongSPCon) {
        this.soLuongSPCon = soLuongSPCon;
    }

    public int getGiaSPBan() {
        return giaSPBan;
    }

    public void setGiaSPBan(int giaSPBan) {
        this.giaSPBan = giaSPBan;
    }

    public int getIdSP() {
        return idSP;
    }

    public void setIdSP(int idSP) {
        this.idSP = idSP;
    }

    public int getGiaSP() {
        return giaSP;
    }

    public void setGiaSP(int giaSP) {
        this.giaSP = giaSP;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getHinhSP() {
        return hinhSP;
    }

    public void setHinhSP(String hinhSP) {
        this.hinhSP = hinhSP;
    }
}
