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
    Button bieudo1, bieudo2, bieudo3, baocuoc;
    TextView datebieudo;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chucnang);
        bien();
        bangchon();
        Intent intent = getIntent();
        int nhomnd_id;
        nhomnd_id = intent.getIntExtra("nhomnd_id",0);

        bieudo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                put(bieudo1.class);
            }
        });
        bieudo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                put(bieudo2.class);
            }
        });
        bieudo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                put(bieudo3.class);
            }
        });
        baocuoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                put(baocuoc.class);
            }
        });
    }
    private void put(Class a){
        Intent intent = new Intent(this,a);
        intent.putExtra("datebieudo",spinner.getSelectedItem().toString());
        startActivity(intent);
    }
    private void bien(){
        datebieudo = (TextView)findViewById(R.id.datebieudo);
        bieudo1 = (Button)findViewById(R.id.bieudo1);
        bieudo2 = (Button)findViewById(R.id.bieudo2);
        bieudo3 = (Button)findViewById(R.id.bieudo3);
        baocuoc = (Button)findViewById(R.id.baocuoc);
        spinner = (Spinner)findViewById(R.id.spinner);

    }
    private void bangchon(){
        final List<String> arr = new ArrayList<>();
        String url ="http://10.97.47.116:8080/ckno.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),response.trim(), Toast.LENGTH_LONG).show();

                try {
                    JSONArray jsonArray = new JSONArray(response.trim());
                    JSONObject jsonObject[] = new JSONObject[20];

                    for(int i=0;i<7;i++){
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
            //arg2 là phần tử được chọn trong data source
            //Toast.makeText(getApplicationContext(),spinner.getSelectedItem().toString() , Toast.LENGTH_SHORT).show();
            //datebieudo.setText(spinner.getSelectedItem().toString());
        }
        //Nếu không chọn gì cả
        public void onNothingSelected(AdapterView<?> arg0) {

        }
    }
}
