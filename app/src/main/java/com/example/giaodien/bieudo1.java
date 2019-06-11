package com.example.giaodien;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
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
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class bieudo1 extends Activity implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {
    Button homeButton;
    BarChart barChart;
    private GestureDetectorCompat mDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bieudo1);
        bien();
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(bieudo1.this, chucnang.class);
                startActivity(intent);
            }
        });
        Intent intent = getIntent();
        String datebieudo = intent.getStringExtra("datebieudo");
        dulieu(datebieudo);

        this.mDetector = new GestureDetectorCompat(this,this);
        mDetector.setOnDoubleTapListener(this);

    }
    public void bien(){
        barChart = (BarChart)findViewById(R.id.barChart);
        homeButton = (Button)findViewById(R.id.homeButton);
    }
    public void dulieu(final String a){
        String url ="http://10.97.47.116:8080/vidu1.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),response.trim(), Toast.LENGTH_LONG).show();
                //json
                try {

                    JSONArray jsonArray = new JSONArray(response.trim());
                    JSONObject jsonObject = (JSONObject)jsonArray.get(1);
                    String ten = jsonObject.getString("SUBSTR(TEN_DONVI_TTVT,26,3)");
                    int brcd1 = jsonObject.getInt("BRCD_TB_PTM");
                    int mytv1 = jsonObject.getInt("MYTV_TB_PTM");

                    //Toast.makeText(getApplicationContext(), ten + " " + brcd1 + " " + mytv1, Toast.LENGTH_LONG).show();
                    //du lieu tu json
                    JSONObject jsonObjectA[] = new JSONObject[20];
                    final String[] donvi = new String[20];
                    final String[] madonvi = new String[20];
                    int brcd[] = new int[20];
                    int mytv[] = new int[20];

                    for(int i=0;i<11;i++){
                        jsonObjectA[i]= (JSONObject)jsonArray.get(i);
                    }

                    for(int i=0;i<11;i++){
                        donvi[i+1] = jsonObjectA[i].getString("SUBSTR(TEN_DONVI_TTVT,26,3)");
                        madonvi[i+1] = jsonObjectA[i].getString("MA_DONVI_TTVT");
                        brcd[i] = jsonObjectA[i].getInt("BRCD_TB_PTM");
                        mytv[i] = jsonObjectA[i].getInt("MYTV_TB_PTM");
                    }


                    //bieu do

                    //setContentView(R.layout.activity_bieudockn);

                    resetChart();

                    ArrayList<BarEntry> barEntries = new ArrayList<>();
                    ArrayList<BarEntry> barEntries1 = new ArrayList<>();

                    //add brcd, mytv
                    for(int i=0;i<11;i++){
                        barEntries.add(new BarEntry(i,brcd[i]));
                        barEntries1.add(new BarEntry(i,mytv[i]));
                    }

                    /*barEntries.add(new BarEntry(12,1));
                    barEntries1.add(new BarEntry(12,4));*/

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
                    xAxis.setLabelCount(12,true);
                    xAxis.setGridLineWidth(0f);



                    float barSpace = 0.05f;
                    float groupSpace = 0.2f;
                    int groupCount = 11;

                    data.setBarWidth(0.35f);
                    barChart.getAxisLeft().setAxisMinimum(0);
                    barChart.getAxisRight().setAxisMinimum(0);
                    barChart.getXAxis().setAxisMinimum(0);
                    barChart.getXAxis().setAxisMaximum(11);
                    barChart.animateY(900);
                    barChart.groupBars(0f, groupSpace, barSpace);
                    barChart.setDoubleTapToZoomEnabled(true);


                    barChart.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            barChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                                @Override
                                public void onValueSelected(Entry e, Highlight h) {
                                    float thutu1 = h.getX();
                                    int index = h.getDataSetIndex();
                                    int thutu = Math.round(thutu1);
                                    int i;
                                    String dv;
                                    if(index==0){
                                        i = thutu+1;
                                        dv = "BRCD";
                                    }else {
                                        i=thutu;
                                        dv = "MYTV";
                                    }
                                    //Toast.makeText(getApplicationContext(),donvi[i]+" "+dv+" "+a , Toast.LENGTH_LONG).show();
                                    put(a,madonvi[i]);
                                }

                                @Override
                                public void onNothingSelected() {

                                }
                            });
                            return true;
                        }
                    });



                    //barChart.getBarData().getGroupWidth((groupSpace, barSpace)*groupCount);
                    //barChart.getBarData().getGroupWidth(groupSpace,barSpace);

                    //het bieu do

                }catch (JSONException e){
                    Toast.makeText(getApplicationContext(),"Lỗi: nhập sai chu kì nợ", Toast.LENGTH_LONG).show();
                    resetChart();
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
                params.put("datebieudo",a.trim());

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void resetChart(){
        barChart = (BarChart)findViewById(R.id.barChart);
        barChart.fitScreen();
        barChart.setData(null);
        barChart.getXAxis().setValueFormatter(null);
        barChart.notifyDataSetChanged();
        barChart.clear();
        barChart.invalidate();
    }
    private void put(String a, String b){
        Intent intent = new Intent(this,thongtinckn.class);
        intent.putExtra("datebieudo",a);
        intent.putExtra("madonvi",b);
        startActivity(intent);
    }
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        float X = e2.getX() - e1.getX();
        float Y = e2.getY() - e1.getX();
        Toast.makeText(getApplicationContext(),"X là: "+Math.abs(X)+"Y là: "+Math.abs(Y), Toast.LENGTH_LONG).show();
        X = Math.abs(X);
        Y = Math.abs(Y);
        boolean check = true;
        Intent intent = getIntent();
        String datebieudo = intent.getStringExtra("datebieudo");
        if(X>Y){
            Intent intent1 = new Intent(this,bieudo2.class);
            intent1.putExtra("datebieudo",datebieudo);
            startActivity(intent1);
        }if(X<Y) {
            Intent intent1 = new Intent(this,bieudo3.class);
            intent1.putExtra("datebieudo",datebieudo);
            startActivity(intent1);
        }else  check = false;
        return check;
    }
}
