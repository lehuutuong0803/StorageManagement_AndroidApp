package com.example.shop10.LopSQL;

import java.io.Serializable;

public class LoaiSP implements Serializable {
   public int IDLoaiSP;
    public String TenLoaiSP;

    public LoaiSP(int IDLoaiSP) {
        this.IDLoaiSP = IDLoaiSP;
    }

    public LoaiSP(int IDLoaiSP, String tenLoaiSP) {
        this.IDLoaiSP = IDLoaiSP;
        TenLoaiSP = tenLoaiSP;
    }

    public int getIDLoaiSP() {
        return IDLoaiSP;
    }

    public void setIDLoaiSP(int IDLoaiSP) {
        this.IDLoaiSP = IDLoaiSP;
    }

    public String getTenLoaiSP() {
        return TenLoaiSP;
    }

    public void setTenLoaiSP(String tenLoaiSP) {
        TenLoaiSP = tenLoaiSP;
    }
}
