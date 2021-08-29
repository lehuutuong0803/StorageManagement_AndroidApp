package com.example.shop10.LopSQL;

import java.io.Serializable;

public class SP implements Serializable {
    public int idsp, idloaisp,gianhap,giaban,soluongsp;
    public String tensp, hinhanhsp;

    public SP(int idsp, String tensp  , String hinhanhsp, int gianhap, int giaban, int soluongsp,int idloaisp) {
        this.idsp = idsp;
        this.idloaisp = idloaisp;
        this.gianhap = gianhap;
        this.giaban = giaban;
        this.soluongsp = soluongsp;
        this.tensp = tensp;
        this.hinhanhsp = hinhanhsp;
    }

    public int getIdsp() {
        return idsp;
    }

    public void setIdsp(int idsp) {
        this.idsp = idsp;
    }

    public int getIdloaisp() {
        return idloaisp;
    }

    public void setIdloaisp(int idloaisp) {
        this.idloaisp = idloaisp;
    }

    public float getGianhap() {
        return gianhap;
    }

    public void setGianhap(int gianhap) {
        this.gianhap = gianhap;
    }

    public float getGiaban() {
        return giaban;
    }

    public void setGiaban(int giaban) {
        this.giaban = giaban;
    }

    public float getSoluongsp() {
        return soluongsp;
    }

    public void setSoluongsp(int soluongsp) {
        this.soluongsp = soluongsp;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public String getHinhanhsp() {
        return hinhanhsp;
    }

    public void setHinhanhsp(String hinhanhsp) {
        this.hinhanhsp = hinhanhsp;
    }
}
