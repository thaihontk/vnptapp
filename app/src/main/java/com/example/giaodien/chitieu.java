package com.example.giaodien;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class chitieu extends AppCompatActivity {
    EditText mact;
    Button btnchitieu;
    RecyclerView recyclerViewchitieu;
    RecyclerViewchitieu mRcvAdapter2;
    private List<String> manv = new ArrayList<>();
    private List<String> tennhanvien = new ArrayList<>();
    private List<String> vmact = new ArrayList<>();
    private List<String> chitieubsc = new ArrayList<>();
    private List<String> chitieu = new ArrayList<>();
    private List<String> dathuchien = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitieu);
        Intent intent = getIntent();
        final String urladd = intent.getStringExtra("urladd");
        final String chukyno = intent.getStringExtra("chukyno");
        bien();
        btnchitieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bangchon(urladd,chukyno);
            }
        });
    }

    private void bien(){
        mact = (EditText)findViewById(R.id.mact);
        btnchitieu = (Button)findViewById(R.id.btnchitieu);
        recyclerViewchitieu = (RecyclerView)findViewById(R.id.recycleviewchitieu);
    }
    private void bangchon(String urladd, final String chukyno){
        String url =urladd+"/chitieu.php";
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
                        manv.add(jsonObject[i].getString("MA_NV"));
                        tennhanvien.add(jsonObject[i].getString("TEN_NV"));
                        vmact.add(jsonObject[i].getString("MA_CT"));
                        chitieubsc.add(jsonObject[i].getString("CHITIEU_BSC"));
                        chitieu.add(jsonObject[i].getString("CHITIEU"));
                        dathuchien.add(jsonObject[i].getString("DATHUCHIEN"));
                    };

                    //Toast.makeText(getApplicationContext(),gio.get(2), Toast.LENGTH_SHORT).show();
                    //xử lý dữ liệu
                    mRcvAdapter2 = new RecyclerViewchitieu(manv,tennhanvien,vmact,chitieubsc,chitieu,dathuchien);

                    LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                    recyclerViewchitieu.setLayoutManager(layoutManager);
                    recyclerViewchitieu.setAdapter(mRcvAdapter2);
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
                params.put("mact",mact.getText().toString());
                params.put("chukyno",chukyno);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
