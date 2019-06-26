package com.example.giaodien;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

public class thanhtoan extends AppCompatActivity {
    EditText thanhtoanid;
    Button btnthanhtoan;
    Bitmap bitmap;
    LinearLayout linepdf1;
    RecyclerView mRecyclerView;
    RecyclerViewAdapter2 mRcvAdapter2;
    private List<String> danhthu = new ArrayList<>();
    private List<String> thue = new ArrayList<>();
    private List<String> stb1 = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanhtoan);
        Intent intent = getIntent();
        final String urladd = intent.getStringExtra("urladd");
        final String chukyno = intent.getStringExtra("chukyno");
        bien();
        btnthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bangchon(urladd,chukyno);
            }
        });
    }
    private void bien(){
        thanhtoanid = (EditText)findViewById(R.id.thanhtoanid);
        btnthanhtoan = (Button)findViewById(R.id.btnthanhtoan);
        mRecyclerView = (RecyclerView)findViewById(R.id.recycleviewthanhtoan);
        linepdf1 = (LinearLayout)findViewById(R.id.linepdf1);
    }
    private void bangchon(String urladd, final String chukyno){
        String url =urladd+"/chitietthanhtoan.php";
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
                        danhthu.add(jsonObject[i].getString("CUOC"));
                        thue.add(jsonObject[i].getString("VAT"));
                        stb1.add(jsonObject[i].getString("THUEBAO_ID"));
                    };

                    //Toast.makeText(getApplicationContext(),gio.get(2), Toast.LENGTH_SHORT).show();
                    //xử lý dữ liệu
                    mRcvAdapter2 = new RecyclerViewAdapter2(danhthu,thue,stb1);

                    LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                    mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setAdapter(mRcvAdapter2);
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
                params.put("thanhtoanid",thanhtoanid.getText().toString());
                params.put("chukyno",chukyno);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
