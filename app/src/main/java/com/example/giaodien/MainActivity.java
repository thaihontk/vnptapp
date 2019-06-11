package com.example.giaodien;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText edituser, editpassword;
    Button btndangky, btndangnhap, btnthoat;
    int nhomnd_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();
        thoatButton();
        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ktrDN()){
                    dangnhap();
                }else {
                    Toast.makeText(getApplicationContext(),"Nhập tên đăng nhập và mật khẩu!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void anhXa() {
        edituser= (EditText)findViewById(R.id.edittextuser);
        editpassword= (EditText)findViewById(R.id.edittextpassword);
        btndangnhap=(Button)findViewById(R.id.buttondangnhap);

        btnthoat=(Button)findViewById(R.id.buttonthoat);

    }
    private boolean ktrDN(){
        if(edituser.getText().toString().trim().equals("")){
            return false;
        }if(editpassword.getText().toString().trim().equals("")){
            return false;
        }else return true;
    }
    private void dangnhap(){
        String url ="http://10.97.47.116:8080/test2.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),response.trim(), Toast.LENGTH_LONG).show();
                if (edituser.getText().toString().trim().contains(response.trim())){
                    Toast.makeText(getApplicationContext(),"Sai tài khoản hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
                }else {

                    nhomnd_id = Integer.parseInt(String.valueOf(response.trim().charAt(response.trim().length()-1)));
                    put_dn(nhomnd_id);
                    switch (nhomnd_id){
                        case 1:
                            Toast.makeText(getApplicationContext(),"Đăng nhập thành công với quyền quản trị", Toast.LENGTH_SHORT).show();
                            break;
                        case 2:
                            Toast.makeText(getApplicationContext(),"Đăng nhập thành công với quyền thành viên", Toast.LENGTH_SHORT).show();
                            break;
                        case 3:
                            Toast.makeText(getApplicationContext(),"Đăng nhập thành công với quyền khách", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_LONG).show();
            }
        } ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user1name",edituser.getText().toString().trim());
                params.put("pass1word",editpassword.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);

    }
    private void thoatButton() {
        btnthoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this,android.R.style.Theme_DeviceDefault_Light_Dialog);
                builder.setTitle("Bạn có muốn thoát khỏi app?");
                builder.setIcon(android.R.drawable.ic_dialog_alert);
                builder.setPositiveButton("CÓ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        Intent startMain = new Intent(Intent.ACTION_MAIN);
                        startMain.addCategory(Intent.CATEGORY_HOME);
                        startActivity(startMain);
                        finish();
                    }
                });
                builder.setNegativeButton("KHÔNG", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
        });
    };
    private void put_dn(int nhomnd_id){
        Intent intent = new Intent(this,chucnang.class);
        intent.putExtra("nhomnd_id",nhomnd_id);
        startActivity(intent);
    }
    private void put(Class cl){
        Intent intent = new Intent(this,cl);
        intent.putExtra("datebieudo","20181201");
        startActivity(intent);
    }
}