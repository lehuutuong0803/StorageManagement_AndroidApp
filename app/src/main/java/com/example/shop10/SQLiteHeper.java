package com.example.shop10;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class SQLiteHeper extends SQLiteOpenHelper {
    //Table user
    public static final String TABLE_USER = "user1";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TAIKHOAN = "taikhoan";
    public static final String COLUMN_MATKHUAU = "matkhuau";
    public static final String COLUMN_TENNGUOIDUNG = "tennguoidung";
    public static final String COLUMN_SDT = "sdt";
    public static final String COLUMN_DIACHI= "diachi";
    //Table LoaiSP
    public static final String TABLE_LOAI = "loaisp";
    public static final String COLUMN_IDLOAISP = "idloaisp";
    public static final String COLUMN_TENLOAISP = "tenloaisp";
    //Table SanPham
    public static final String TABLE_SANPHAM = "sanpham";
    public static final String COLUMN_IDSP = "idsp";
    public static final String COLUMN_TENSP = "tensp";
    public static final String COLUMN_GIANHAP = "gianhap";
    public static final String COLUMN_GIABAN = "giaban";
    public static final String COLUMN_SOLUONG = "soluong";
    public static final String COLUMN_HINHANH = "hinhanh";
    public static final String COLUMN_IDLOAISP1 = "idloaisp";
    //Table HDBan
    public static final String TABLE_HDBAN = "hdban";
    public static final String COLUMN_IDHDBAN = "idhdban";
    public static final String COLUMN_IDKHACH = "idkhach";
    public static final String COLUMN_NGAYBAN = "ngayban";
    public static final String COLUMN_TONGTIEN = "tongtien";
    //Table CTHD
    public static final String TABLE_CTHD = "cthd";
    public static final String COLUMN_SOLUONGBAN = "soluongban";
    public static final String COLUMN_THANHTIEN = "thanhtien";
    public static final String COLUMN_DONGIA = "dongia";
    public static final String COLUMN_IDHDBAN1 = "idhban";
    public static final String COLUMN_IDSP1 = "idsp1";

    public static final String DATABASE_NAME ="QLBANHANG.db";
    private static final int  DATABASE_VERSION = 1;

    public   static final String DATABASE_CREATE_USER1 = "  CREATE TABLE If not exists "+TABLE_USER+"("+COLUMN_ID+"INTEGER PRIMARY KEY AUTOINCREMENT,"+COLUMN_TAIKHOAN+" NVARCHAR(200),"+COLUMN_MATKHUAU+" NVARCHAR(200),"+COLUMN_TENNGUOIDUNG+" NVARCHAR(200),"+COLUMN_SDT+" CHAR(11),"+COLUMN_DIACHI+" NVARCHAR(200) )";
    private static final String DATABASE_CREATE_LOAI ="CREATE TABLE If not exists "+TABLE_LOAI+" ( "+COLUMN_IDLOAISP+" INT  PRIMARY KEY AUTOINCREMENT,"+COLUMN_TENLOAISP+" NVARCHAR(200))";
    private static final String DATABASE_CREATE_SANPHAM = "CREATE TABLE If not exists "+TABLE_SANPHAM+" ( "+COLUMN_IDSP+" INT PRIMARY KEY AUTOINCREMENT,"+COLUMN_TENSP+" NVARCHAR(200),"+COLUMN_GIANHAP+" FLOAT ,"+COLUMN_GIABAN+" FLOAT,"+COLUMN_SOLUONG+" FLOAT,"+COLUMN_HINHANH+" NVARCHAR,"+COLUMN_IDLOAISP+" INT,CONSTRAINT fk_IDLOAISP FOREIGN KEY ("+COLUMN_IDLOAISP1+") REFERENCES "+TABLE_LOAI+"("+COLUMN_IDLOAISP+"))";
    private static final String DATABASE_CREATE_HDBAN = "CREATE TABLE If not exists "+TABLE_HDBAN+"("+COLUMN_IDHDBAN+" INT  PRIMARY KEY AUTOINCREMENT,"+COLUMN_IDKHACH+" INT,"+COLUMN_NGAYBAN+" DATETIME,"+COLUMN_TONGTIEN+" FLOAT, CONSTRAINT fk_IDKHACH FOREIGN KEY ("+COLUMN_IDKHACH+") REFERENCES USER1("+COLUMN_ID+"))";
    private static final String DATABASE_CREATE_CTHD = "CREATE TABLE If not exists "+TABLE_CTHD+"("+COLUMN_IDHDBAN1+" INT,"+COLUMN_IDSP1+" INT,"+COLUMN_SOLUONGBAN+" FLOAT,"+COLUMN_DONGIA+" FLOAT,"+COLUMN_THANHTIEN+" FLOAT,CONSTRAINT PK_CTHD1 PRIMARY KEY ("+COLUMN_IDHDBAN1+","+COLUMN_IDSP1+"),CONSTRAINT fk_IDHDBAN FOREIGN KEY ("+COLUMN_IDHDBAN1+") REFERENCES "+TABLE_HDBAN+"("+COLUMN_IDHDBAN+"),CONSTRAINT fk_CTHD2 FOREIGN KEY ("+COLUMN_IDSP1+") REFERENCES "+TABLE_SANPHAM+"("+COLUMN_IDSP+"))";

    public SQLiteHeper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }
    public void TruyVanKhongTraVe(String sql)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
    }
    public Cursor TruyVanTraVe(String sql)
    {
        SQLiteDatabase db = getReadableDatabase();
        return  db.rawQuery(sql,null);
    }
}
