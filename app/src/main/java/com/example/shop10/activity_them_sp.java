package com.example.shop10;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shop10.LopSQL.LoaiSP;
import com.example.shop10.LopSQL.SP;
import com.example.shop10.Retrofit.APIUtils;
import com.example.shop10.Retrofit.Dataclient;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

import static com.example.shop10.common.ip;

public class activity_them_sp extends AppCompatActivity {
    Spinner spinnerIDLoaiSP;
    ArrayList<Integer> arrayListIDLoaiSP = new ArrayList<Integer>();
    ArrayAdapter spinnerAdapter;
    String urlGetDataIDLoaiSP = ip + "/getData.php";
    String IDLoaiSP;
    Button btnDongY, btnHuy;
    EditText edtTenSP, edtGiaNhap, edtGiaBan, edtSoLuong;
    ImageView imgvAnh;
    ImageButton imgbtnChonAnh;
    final int REQUEST_CODE_FOLDER = 4655;
    private int PICK_IMAGE_RESULT=1;
    String realpath = "";
    Uri uri;
    Bitmap bitmap;
    String tenSP,  anhSP, giaNhap, giaBan, soLuong;
    int j=0, status = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_sp);
        AnhXa();
        spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayListIDLoaiSP);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinnerIDLoaiSP.setAdapter(spinnerAdapter);
        HienThiSpinner(urlGetDataIDLoaiSP);
        spinnerIDLoaiSP.setPrompt("Chọn Loại Sản Phẩm!");
        ControlButton();

        spinnerIDLoaiSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                IDLoaiSP = arrayListIDLoaiSP.get(position).toString().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                IDLoaiSP =arrayListIDLoaiSP.get(0).toString().trim();
            }
        });
    }

    private void AnhXa()
    {
        spinnerIDLoaiSP = (Spinner)findViewById(R.id.spinnerIDLoaiSP);
        btnDongY = (Button)findViewById(R.id.btDongY1);
        btnHuy = (Button)findViewById(R.id.btHuy1);
        edtGiaBan = (EditText)findViewById(R.id.editTextGiaBan);
        edtGiaNhap = (EditText)findViewById(R.id.editTextGiaNhap);
        edtSoLuong = (EditText)findViewById(R.id.editTextSoLuong);
        edtTenSP = (EditText)findViewById(R.id.editTextTenSP);
        imgvAnh = (ImageView)findViewById(R.id.imageViewHinhAnh);
        imgbtnChonAnh = (ImageButton)findViewById(R.id.imageButtonChonAnh);

    }
    private void HienThiSpinner(String urlGetDataIDLoaiSP)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, urlGetDataIDLoaiSP, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i<response.length();i++)
                {
                    try{
                        JSONObject object  =response.getJSONObject(i);
                        arrayListIDLoaiSP.add(object.getInt("IDLoaiSP"));
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
                spinnerAdapter.notifyDataSetChanged();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(activity_them_sp.this, "Lỗi", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }
    private void ControlButton()
    {

        btnDongY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tenSP = edtTenSP.getText().toString().trim();
                giaNhap = edtGiaNhap.getText().toString().trim();
                giaBan = edtGiaBan.getText().toString().trim();
                soLuong = edtSoLuong.getText().toString().trim();


                if(tenSP.equals("")||giaNhap.equals("")||giaBan.equals("")||soLuong.equals("")||j==0)
                {
                    Toast.makeText(activity_them_sp.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    File file  = new File(realpath);
                    String filepath  = file.getAbsolutePath();
                    //Xử lý hoog trùng tên ảnh

                    String[] mangtenfile = filepath.split("\\.");
                    //Đường dẫn file
                    filepath = mangtenfile[0] +System.currentTimeMillis() + "."+mangtenfile[1];
                    //Xác định Kiểu dữ liệu file
                    RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    //Gửi dữ liệu lên server
                    MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file",filepath,requestBody);
                    Dataclient dataclient = APIUtils.getData();
                    //hứng dữ liệu
                    Call<String> callBack = dataclient.UploadPhoto(body);
                    callBack.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                            if(response != null)
                            {
                                String messenger = response.body();
                                if(messenger.length()>0)
                                {
                                    Dataclient insertData = APIUtils.getData();
                                    retrofit2.Call<String> callBack = insertData.insertData(tenSP,APIUtils.Base_Url +"image/"+messenger,giaNhap,giaBan,soLuong,IDLoaiSP,String.valueOf(status));
                                    callBack.enqueue(new Callback<String>() {
                                        @Override
                                        public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                                            String result  =response.body();
                                            if(response.body().equals("success"))
                                            {
                                                Toast.makeText(activity_them_sp.this, "Thêm Thành Công", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(activity_them_sp.this,ManHinh_qlSp.class);
                                                startActivity(intent);
                                            }
                                            else
                                            {
                                                Toast.makeText(activity_them_sp.this, "Lỗi Thêm", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        @Override
                                        public void onFailure(Call<String> call, Throwable t) {
                                            Toast.makeText(activity_them_sp.this, "Xảy ra lỗi", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Log.d("BBB2", t.getMessage());
                        }
                    });
                }
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_them_sp.this,ManHinh_qlSp.class);
                startActivity(intent);
            }
        });

        imgbtnChonAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ActivityCompat.requestPermissions(
                        activity_them_sp.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_FOLDER
                );
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == REQUEST_CODE_FOLDER)
        {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_FOLDER);
            }
            else
            {
                Toast.makeText(this, "Bạn không cho phép vào thư mục ảnh", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        //Hiện thị ảnh
        if (requestCode == REQUEST_CODE_FOLDER && resultCode == RESULT_OK && data!= null)
        {
            j++;
            uri = data.getData();
            realpath  = getRealPathFromURI(uri);
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgvAnh.setImageBitmap(bitmap);
            }  catch (IOException e) {

            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
    //lấy giá trị thực
    public String getRealPathFromURI (Uri contentUri) {
        String path = null;
        String[] proj = { MediaStore.MediaColumns.DATA };
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }


}