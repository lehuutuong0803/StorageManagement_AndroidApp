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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SpinnerAdapter;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.shop10.LopSQL.SP;
import com.example.shop10.Retrofit.APIUtils;
import com.example.shop10.Retrofit.Dataclient;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class activity_sua_sp extends AppCompatActivity {
    Spinner spinnerIDLoaiSP;
    ArrayList<Integer> arrayListIDLoaiSP = new ArrayList<Integer>();
    ArrayAdapter spinnerAdapter;
//    String urlGetDataIDLoaiSP = "https://tuong123.000webhostapp.com/AndroidWebService/getData.php";
    String urlGetDataIDLoaiSP = "http://192.168.1.44:81/AndroidWebService/getData.php";
    Button btnDongY, btnHuy;
    EditText edtTenSP, edtAnhSP, edtGiaNhap, edtGiaBan, edtSoLuong;
    ImageView imageViewAnhSP;
    ImageButton imageButtonChonAnh;
    TextView textViewIDloaiSP;
    final int REQUEST_CODE_FOLDER = 4655;
    Uri uri;
    String realpath = "";
    int i =0,j=0;
    String id,tenSP,  anhSP, giaNhap, giaBan, soLuong,idLoaiSP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_sp);
        Intent intent =  getIntent();
        SP sp = (SP) intent.getSerializableExtra("dataSP");
        AnhXa();
        id = String.valueOf(sp.getIdsp());
        edtTenSP.setText(sp.getTensp());
        Picasso.get().load(sp.getHinhanhsp()).into(imageViewAnhSP);
        edtGiaNhap.setText(String.valueOf(sp.getGianhap()));
        edtGiaBan.setText(String.valueOf(sp.getGiaban()));
        edtSoLuong.setText(String.valueOf(sp.getSoluongsp()));
        textViewIDloaiSP.setText("ID Loại SP: "+String.valueOf(sp.getIdloaisp()));
        idLoaiSP = String.valueOf(sp.getIdloaisp());
        anhSP = sp.getHinhanhsp();



        spinnerAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayListIDLoaiSP);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinnerIDLoaiSP.setPrompt("Chọn Loại Sản Phẩm Cần Thay Đổi!");
        spinnerIDLoaiSP.setAdapter(
                new NothingSelectedSpinnerAdapter(
                        spinnerAdapter,
                        R.layout.contact_spinner_row_noth_selected,
                        // R.layout.contact_spinner_nothing_selected_dropdown, // Optional
                        this));
        HienThiSpinner(urlGetDataIDLoaiSP);


        spinnerIDLoaiSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               i++;
                       if(i>1)
                       {
                           textViewIDloaiSP.setText("ID Loại SP: "+arrayListIDLoaiSP.get(position-1).toString().trim());
                           Toast.makeText(activity_sua_sp.this, "Position: "+position , Toast.LENGTH_SHORT).show();
                           idLoaiSP = arrayListIDLoaiSP.get(position-1).toString().trim();
                       }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ControlButton();
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

                if(tenSP.equals("")||giaNhap.equals("")||giaBan.equals("")||soLuong.equals(""))
                {
                    Toast.makeText(activity_sua_sp.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(j==0)
                    {
                        Dataclient updateData = APIUtils.getData();
                        retrofit2.Call<String> callBack = updateData.updateData(id,tenSP,anhSP,giaNhap,giaBan,soLuong,idLoaiSP);
                        callBack.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                                if(response.body().equals("success"))
                                {
                                    Toast.makeText(activity_sua_sp.this, "Cập Nhật Thành Công", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {

                                    Toast.makeText(activity_sua_sp.this, "Lỗi cập nhật", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Toast.makeText(activity_sua_sp.this, "Xảy ra lỗi", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else {
                        File file = new File(realpath);
                        String filepath = file.getAbsolutePath();

                        String[] mangtenfile = filepath.split("\\.");
                        filepath = mangtenfile[0] + System.currentTimeMillis() + "." + mangtenfile[1];
                        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                        MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file", filepath, requestBody);
                        Dataclient dataclient = APIUtils.getData();
                        Call<String> callBack = dataclient.UploadPhoto(body);
                        Toast.makeText(activity_sua_sp.this, "Hinh ảnh: " + filepath, Toast.LENGTH_SHORT).show();
                        callBack.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                                if (response != null) {
                                    String messenger = response.body();
                                    if (messenger.length() > 0) {
                                        Dataclient updateData = APIUtils.getData();
                                        retrofit2.Call<String> callBack = updateData.updateData(id, tenSP, APIUtils.Base_Url + "image/" + messenger, giaNhap, giaBan, soLuong, idLoaiSP);
                                        callBack.enqueue(new Callback<String>() {
                                            @Override
                                            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                                                String result = response.body();
                                                Toast.makeText(activity_sua_sp.this, "Thông Báo: " + result, Toast.LENGTH_SHORT).show();
                                                if (response.body().equals("success")) {
                                                    Toast.makeText(activity_sua_sp.this, "Cập Nhật Thành Công", Toast.LENGTH_SHORT).show();
                                                } else {

                                                    Toast.makeText(activity_sua_sp.this, "Lỗi cập nhật", Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<String> call, Throwable t) {
                                                Toast.makeText(activity_sua_sp.this, "Xảy ra lỗi", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {

                            }
                        });
                    }

                }
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity_sua_sp.this, ManHinh_qlSp.class);
                startActivity(intent);
            }
        });
        imageButtonChonAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        activity_sua_sp.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_FOLDER
                );
            }
        });
    }

    private void AnhXa()
    {
        spinnerIDLoaiSP = (Spinner)findViewById(R.id.spinnerIDLoaiSP);
        btnDongY = (Button)findViewById(R.id.btDongY1);
        btnHuy = (Button)findViewById(R.id.btHuy1);
        imageViewAnhSP = (ImageView)findViewById(R.id.imageViewHinhAnh);
        edtGiaBan = (EditText)findViewById(R.id.editTextGiaBan);
        edtGiaNhap = (EditText)findViewById(R.id.editTextGiaNhap);
        edtSoLuong = (EditText)findViewById(R.id.editTextSoLuong);
        edtTenSP = (EditText)findViewById(R.id.editTextTenSP);
        textViewIDloaiSP=(TextView)findViewById(R.id.tvIDLoaiSP);
        imageButtonChonAnh = (ImageButton) findViewById(R.id.imageButtonChonAnh);
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
                        Toast.makeText(activity_sua_sp.this, "Lỗi", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
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
                imageViewAnhSP.setImageBitmap(bitmap);
            }  catch (IOException e) {

            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

}