package com.example.giaodien;

import android.R.layout;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class chucnang extends AppCompatActivity {
    Button bieudo1, bieudo2, bieudo3, baocuoc, bchitietcuocgoi, chitietthanhtoan, chitieu, phancong;
    TextView datebieudo, tennd;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chucnang);
        Intent intent = getIntent();
        final int nhomnd_id;
        nhomnd_id = intent.getIntExtra("nhomnd_id",0);
        final String manv = intent.getStringExtra("manv");
        final String urladd = intent.getStringExtra("urladd");
        final String vtennd = intent.getStringExtra("tennd");

        //Toast.makeText(getApplicationContext(),vtennd+" mã bên chucnang", Toast.LENGTH_SHORT).show();
        bien();

        tennd.setText("Chào "+vtennd);
        bangchon(urladd);

        bieudo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                put(bieudo1.class,urladd);
            }
        });
        bieudo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                put(bieudo2.class,urladd);
            }
        });
        bieudo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                put(bieudo3.class,urladd);
            }
        });
        baocuoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                put(baocuoc.class,urladd);
            }
        });
        bchitietcuocgoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                put(chitietcuocgoi.class,urladd);
            }
        });
        chitietthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                put(thanhtoan.class,urladd);
            }
        });
        chitieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                put(chitieu.class,urladd);
            }
        });
        phancong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(chucnang.this,phancong.class);
                intent.putExtra("chukyno",spinner.getSelectedItem().toString());
                intent.putExtra("manv",manv);
                intent.putExtra("urladd",urladd);
                startActivity(intent);
            }
        });
        check(urladd,nhomnd_id);
    }
    private void check(final String urladd,final int nhomnd_id){
        String url =urladd+"/nhomndid.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),response.trim(), Toast.LENGTH_LONG).show();
                try{
                    JSONArray jsonArray = new JSONArray(response.trim());
                    int sochucnang = jsonArray.length();
                    JSONObject jsonObject[] = new JSONObject[sochucnang];
                    String id_chucnang[] = new String[sochucnang];
                    for(int i=0;i<sochucnang;i++){
                        jsonObject[i] = (JSONObject)jsonArray.get(i);
                        id_chucnang[i] = jsonObject[i].getString("ID_CHUCNANG");
                        switch (id_chucnang[i]){
                            case "1":
                                bieudo1.setVisibility(View.VISIBLE);
                                break;
                            case "2":
                                bieudo2.setVisibility(View.VISIBLE);
                                bieudo3.setVisibility(View.VISIBLE);
                                break;
                            case "3":
                                baocuoc.setVisibility(View.VISIBLE);
                                break;
                            case "4":
                                bchitietcuocgoi.setVisibility(View.VISIBLE);
                                break;
                            case "5":
                                chitietthanhtoan.setVisibility(View.VISIBLE);
                                break;
                            case "6":
                                chitieu.setVisibility(View.VISIBLE);
                                break;
                            case "7":
                                phancong.setVisibility(View.VISIBLE);
                                break;
                        }
                    };
                    //Toast.makeText(getApplicationContext(), id_chucnang[1], Toast.LENGTH_SHORT).show();
                }catch (JSONException e){
                    Toast.makeText(getApplicationContext(), "Lỗi "+e.toString(), Toast.LENGTH_SHORT).show();
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
                params.put("nhomndid",String.valueOf(nhomnd_id));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void put(Class a, String urladd){
        Intent intent = new Intent(this,a);
        intent.putExtra("chukyno",spinner.getSelectedItem().toString());
        intent.putExtra("urladd",urladd);
        startActivity(intent);
    }
    private void bien(){
        datebieudo = (TextView)findViewById(R.id.datebieudo);
        bieudo1 = (Button)findViewById(R.id.bieudo1);
        bieudo2 = (Button)findViewById(R.id.bieudo2);
        bieudo3 = (Button)findViewById(R.id.bieudo3);
        baocuoc = (Button)findViewById(R.id.baocuoc);
        chitietthanhtoan = (Button)findViewById(R.id.chitietthanhtoan);
        bchitietcuocgoi = (Button)findViewById(R.id.chitietcuocgoi);
        chitieu = (Button)findViewById(R.id.chitieu);
        phancong = (Button)findViewById(R.id.phancong);
        spinner = (Spinner)findViewById(R.id.spinner);
        tennd = (TextView)findViewById(R.id.tennd);

        bieudo1.setVisibility(View.GONE);
        bieudo2.setVisibility(View.GONE);
        bieudo3.setVisibility(View.GONE);
        baocuoc.setVisibility(View.GONE);
        bchitietcuocgoi.setVisibility(View.GONE);
        chitietthanhtoan.setVisibility(View.GONE);
        phancong.setVisibility(View.GONE);
        chitieu.setVisibility(View.GONE);
    }
    private void bangchon(String urladd){
        final List<String> arr = new ArrayList<>();
        String url =urladd+"/ckno.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),response.trim(), Toast.LENGTH_LONG).show();
                try {
                    JSONArray jsonArray = new JSONArray(response.trim());
                    JSONObject jsonObject[] = new JSONObject[jsonArray.length()];

                    for(int i=0;i<jsonArray.length();i++){
                        jsonObject[i] = (JSONObject)jsonArray.get(i);
                        arr.add(jsonObject[i].getString("CHUKYNO"));
                    };
                    //Toast.makeText(getApplicationContext(), arr.get(1), Toast.LENGTH_SHORT).show();
                    danhsach(arr);
                }catch (JSONException e1){
                    Toast.makeText(getApplicationContext(), "Lỗi", Toast.LENGTH_SHORT).show();
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
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void danhsach(List<String> arr){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arr);
        adapter.setDropDownViewResource(layout.simple_list_item_single_choice);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new MyProcessEvent());
    }
    private class MyProcessEvent implements AdapterView.OnItemSelectedListener {
        //khi có chọn lựa thi vào hàm này
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

        }
        //Nếu không chọn gì cả
        public void onNothingSelected(AdapterView<?> arg0) {

        }
    }
}
