package com.example.giaodien;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class phancong extends AppCompatActivity {
    TextView tennhanvien, manv;
    RecyclerView recyclerViewphancong;
    RecyclerViewphancong mRcvAdapter2;

    private List<String> vmact = new ArrayList<>();
    private List<String> chitieubsc = new ArrayList<>();
    private List<String> chitieu = new ArrayList<>();
    private List<String> dathuchien = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phancong);
        Intent intent = getIntent();
        final String urladd = intent.getStringExtra("urladd");
        final String chukyno = intent.getStringExtra("chukyno");
        final String manv = intent.getStringExtra("manv");
        Toast.makeText(getApplicationContext(),manv+" mã nhân viên  ben phan cpng", Toast.LENGTH_SHORT).show();
        bien();
        bangchon(urladd,chukyno,manv);
    }
    private void bien(){
        tennhanvien = (TextView)findViewById(R.id.tennhanvien);
        manv = (TextView)findViewById(R.id.manv);
        recyclerViewphancong = (RecyclerView)findViewById(R.id.recycleviewphancong);
    }
    private void bangchon(String urladd, final String chukyno, final String vmanv){
        String url =urladd+"/phancong.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),response.trim(), Toast.LENGTH_LONG).show();

                try {
                    JSONArray jsonArray = new JSONArray(response.trim());
                    JSONObject jsonObject[] = new JSONObject[jsonArray.length()+10];

                    for(int i=0;i<jsonArray.length();i++){
                        jsonObject[i] = (JSONObject)jsonArray.get(i);
                        vmact.add(jsonObject[i].getString("MA_CT"));
                        chitieubsc.add(jsonObject[i].getString("CHITIEU_BSC"));
                        chitieu.add(jsonObject[i].getString("CHITIEU"));
                        dathuchien.add(jsonObject[i].getString("DATHUCHIEN"));
                    };
                    tennhanvien.setText("Tên nhân viên: "+jsonObject[1].getString("TEN_NV"));
                    manv.setText("Mã nhân viên: "+jsonObject[1].getString("MA_NV"));
                    //Toast.makeText(getApplicationContext(),gio.get(2), Toast.LENGTH_SHORT).show();
                    //xử lý dữ liệu
                    mRcvAdapter2 = new RecyclerViewphancong(vmact,chitieubsc,chitieu,dathuchien);

                    LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                    recyclerViewphancong.setLayoutManager(layoutManager);
                    recyclerViewphancong.setAdapter(mRcvAdapter2);
                }catch (JSONException e1){
                    Toast.makeText(getApplicationContext(), "Lỗi json", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_LONG).show();
            }
        } ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("manv", vmanv);
                params.put("chukyno",chukyno);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
