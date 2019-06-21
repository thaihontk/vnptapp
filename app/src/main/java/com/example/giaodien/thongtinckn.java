package com.example.giaodien;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class thongtinckn extends AppCompatActivity {
    BarChart barChart;
    Button homeButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtinckn);
        bien();
        Intent intent = getIntent();
        String datebieudo = intent.getStringExtra("datebieudo");
        String madonvi = intent.getStringExtra("madonvi");
        dulieu(datebieudo,madonvi);
    }
    public void bien(){
        barChart = (BarChart)findViewById(R.id.barChart);
        homeButton = (Button)findViewById(R.id.homeButton);
    }
    public void dulieu(final String a, final String b){
        String url ="http://10.97.47.23:8080/test4.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),response.trim(), Toast.LENGTH_LONG).show();
                //json
                try{
                    JSONArray jsonArray = new JSONArray(response.trim());
                    JSONObject jsonObjectA[] = new JSONObject[20];
                    final String[] donvi = new String[20];
                    int brcd[] = new int[20];
                    int mytv[] = new int[20];
                    for(int i=0;i<jsonArray.length();i++){
                        jsonObjectA[i]= (JSONObject)jsonArray.get(i);
                    }

                    for(int i=0;i<jsonArray.length();i++){
                        donvi[i+1] = jsonObjectA[i].getString("TEN_KV");
                        brcd[i] = jsonObjectA[i].getInt("BRCD_TB_PTM");
                        mytv[i] = jsonObjectA[i].getInt("MYTV_TB_PTM");
                    }
                    Toast.makeText(getApplicationContext(),donvi[3]+brcd[2]+mytv[2], Toast.LENGTH_LONG).show();
                    //bieu do
                    ArrayList<BarEntry> barEntries = new ArrayList<>();
                    ArrayList<BarEntry> barEntries1 = new ArrayList<>();
                    for(int i=0;i<11;i++){
                        barEntries.add(new BarEntry(i,brcd[i]));
                        barEntries1.add(new BarEntry(i,mytv[i]));
                    }
                    BarDataSet barDataSet = new BarDataSet(barEntries,"BRCD");
                    barDataSet.setColor(Color.parseColor("#F44336"));
                    BarDataSet barDataSet1 = new BarDataSet(barEntries1,"MYTV");
                    barDataSet1.setColors(Color.parseColor("#9C27B0"));


                    final BarData data = new BarData(barDataSet,barDataSet1);
                    barChart.setData(data);

                    //
                    final ArrayList<String> xLabel = new ArrayList<>();

                    //Toast.makeText(getApplicationContext(),donvi[11], Toast.LENGTH_LONG).show();
                    for(int i=1;i<12;i++){
                        xLabel.add(donvi[i]);
                    }
                    //Toast.makeText(getApplicationContext(),jsonObjectA[5].getString("SUBSTR(TEN_DONVI_TTVT,26,3)") , Toast.LENGTH_LONG).show();

                    XAxis xAxis = barChart.getXAxis();
                    xAxis.setValueFormatter(new IndexAxisValueFormatter(){
                        @Override
                        public String getFormattedValue(float value) {
                            int a = Math.round(value);
                            if(a>=xLabel.size()){
                                a = xLabel.size()-1;
                            }
                            return xLabel.get(a);
                        }

                    });

                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setGranularity(1f);
                    xAxis.setCenterAxisLabels(true);
                    xAxis.setGranularityEnabled(true);
                    xAxis.setLabelCount(10,true);
                    xAxis.setGridLineWidth(0f);



                    float barSpace = 0.005f;
                    float groupSpace = 0.2f;
                    int groupCount = jsonArray.length()+2;

                    data.setBarWidth(0.2f);
                    barChart.getAxisLeft().setAxisMinimum(0);
                    barChart.getAxisRight().setAxisMinimum(0);
                    barChart.getXAxis().setAxisMinimum(0);
                    barChart.getXAxis().setAxisMaximum(5);
                    barChart.animateY(900);
                    barChart.groupBars(0f, groupSpace, barSpace);
                    barChart.setDoubleTapToZoomEnabled(true);
                }catch (JSONException e){
                    Toast.makeText(getApplicationContext(),""+e, Toast.LENGTH_LONG).show();
                }

                //het bieu do

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
                params.put("datebieudo",a.trim());
                params.put("madonvi",b.trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
