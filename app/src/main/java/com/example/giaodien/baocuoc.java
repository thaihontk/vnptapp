package com.example.giaodien;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class baocuoc extends AppCompatActivity {
    Button btntracuu;
    EditText matb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baocuoc);
        bien();
        Intent intent = getIntent();
        final String chukyno = intent.getStringExtra("chukyno");
        final String urladd = intent.getStringExtra("urladd");
        btntracuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(baocuoc.this, thongtinbaocuoc.class);
                intent.putExtra("chukyno", chukyno);
                intent.putExtra("matb", matb.getText().toString());
                intent.putExtra("urladd", urladd);
                startActivity(intent);
            }
        });

    }
    private void bien(){
        btntracuu = (Button)findViewById(R.id.btntracuu);
        matb = (EditText)findViewById(R.id.matb);
    }
    private void dulieu(String urladd){
        String url =urladd+"/baocuoc1.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),response.trim(), Toast.LENGTH_LONG).show();
                try{
                    JSONArray jsonArray = new JSONArray(response.trim());
                    JSONObject jsonObject = (JSONObject)jsonArray.get(0);

                    String ten = jsonObject.getString("DONVI_TAPDOAN");
                    int donvitv = jsonObject.getInt("DONVITC_ID");
                    String donvitinh = jsonObject.getString("DONVI_TINH");
                    String donvithucuoc = jsonObject.getString("DONVI_THUCUOC");
                    String mst = jsonObject.getString("MST");
                    String diachi = jsonObject.getString("DIACHI");
                    String dtlh = jsonObject.getString("DIENTHOAI_LH");
                    String dtcskh = jsonObject.getString("DIENTHOAI_CSKH");
                    String tenktnh = jsonObject.getString("TEN_TKNH");
                    String vdiachi = jsonObject.getString("DIACHI_TKNH");
                    String sotk = jsonObject.getString("SO_TK");
                    String nganhang = jsonObject.getString("NGANHANG");

                    Toast.makeText(getApplicationContext(),ten.toString()+" ", Toast.LENGTH_LONG).show();
                }catch (JSONException e){
                    Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
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
                params.put("chukyno","20190501");
                //params.put("pass1word",editpassword.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void dulieu2(String urladd) {
            String url =urladd+"/baocuoc2.php";
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //Toast.makeText(getApplicationContext(),response.trim(), Toast.LENGTH_LONG).show();
                    try{
                        JSONArray jsonArray = new JSONArray(response.trim());
                        JSONObject jsonObject = (JSONObject)jsonArray.get(0);
                        int donvitv_id = jsonObject.getInt("DONVITC_ID");
                        int thanhtoan_id = jsonObject.getInt("THANHTOAN_ID");
                        String matt = jsonObject.getString("MA_TT");
                        String ten = jsonObject.getString("TEN_TT");
                        String diachi = jsonObject.getString("DIACHI_CT");
                        String matb = jsonObject.getString("MATB_DD");
                        String hinhthuc = jsonObject.getString("HINHTHUC_TT");
                        String mst = jsonObject.getString("MST");
                        int cuoc = jsonObject.getInt("CUOC");
                        int vat = jsonObject.getInt("VAT");
                        int khuyenmai = jsonObject.getInt("KHUYENMAI");
                        int tongtien = jsonObject.getInt("TONGTIEN");

                        Toast.makeText(getApplicationContext(),ten+" ", Toast.LENGTH_LONG).show();
                    }catch (JSONException e){
                        Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_LONG).show();
                    }catch (Exception e1){

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
                    params.put("matb","stgst2428");
                    params.put("chukyno","20190501");
                    //params.put("pass1word",editpassword.getText().toString().trim());
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }

}
