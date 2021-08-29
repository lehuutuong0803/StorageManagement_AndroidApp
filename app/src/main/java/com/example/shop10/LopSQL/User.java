package com.example.shop10.LopSQL;

public class User {
    public int ID;
    public String TenTK;
    public String MatKhauTK;
    public String TenNguoiDung;
    public String SDT;
    public String DiaChi;


    public User() {
    }

    public User(int ID, String tenTK, String matKhauTK, String tenNguoiDung, String SDT, String diaChi) {
        this.ID = ID;
        TenTK = tenTK;
        MatKhauTK = matKhauTK;
        TenNguoiDung = tenNguoiDung;
        this.SDT = SDT;
        DiaChi = diaChi;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTenTK() {
        return TenTK;
    }

    public void setTenTK(String tenTK) {
        TenTK = tenTK;
    }

    public String getMatKhauTK() {
        return MatKhauTK;
    }

    public void setMatKhauTK(String matKhauTK) {
        MatKhauTK = matKhauTK;
    }

    public String getTenNguoiDung() {
        return TenNguoiDung;
    }

    public void setTenNguoiDung(String tenNguoiDung) {
        TenNguoiDung = tenNguoiDung;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }

}
