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
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class bieudo3 extends Activity implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {
    private GestureDetectorCompat mDetector;
    Button homeButton;
    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bieudo3);

        bien();
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(bieudo3.this, chucnang.class);
                startActivity(intent);
            }
        });
        Intent intent = getIntent();
        String a = intent.getStringExtra("datebieudo");
        dulieu(a);

        this.mDetector = new GestureDetectorCompat(this,this);
        mDetector.setOnDoubleTapListener(this);
    }
    private void bien(){
        pieChart = (PieChart)findViewById(R.id.pieChart);
        homeButton = (Button)findViewById(R.id.homeButton);
    }
    private void dulieu(final String a){
        //connect
        String url ="http://10.97.47.23:8080/vidu1.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response.trim());
                    JSONObject jsonObject = (JSONObject)jsonArray.get(1);
                    String ten = jsonObject.getString("SUBSTR(TEN_DONVI_TTVT,26,3)");
                    //int brcd1 = jsonObject.getInt("BRCD_TB_PTM");

                    JSONObject jsonObjectA[] = new JSONObject[20];
                    final String[] donvi = new String[20];
                    final String[] madonvi = new String[20];
                    //int brcd[] = new int[20];
                    int mytv[] = new int[20];
                    for(int i=0;i<11;i++){
                        jsonObjectA[i]= (JSONObject)jsonArray.get(i);
                    }

                    for(int i=0;i<11;i++){
                        donvi[i] = jsonObjectA[i].getString("SUBSTR(TEN_DONVI_TTVT,26,3)");
                        //brcd[i] = jsonObjectA[i].getInt("BRCD_TB_PTM");
                        mytv[i] = jsonObjectA[i].getInt("MYTV_TB_PTM");
                        madonvi[i] = jsonObjectA[i].getString("MA_DONVI_TTVT");
                    }

                    //bieu do
                    pieChart.setUsePercentValues(true);
                    pieChart.getDescription().setEnabled(false);
                    pieChart.setExtraOffsets(5, 10, 5, 5);

                    pieChart.setDragDecelerationFrictionCoef(2f); // thay đổi độ dày

                    pieChart.setDrawHoleEnabled(true); //thay đổi cấu trúc biểu đồ
                    pieChart.setHoleColor(Color.WHITE);
                    pieChart.setTransparentCircleRadius(61f); // độ dày trục

                    ArrayList<PieEntry> yValues = new ArrayList<>();

                    for(int i=0;i<11;i++){
                        yValues.add(new PieEntry(mytv[i],donvi[i]));
                    }

                    Description description = new Description();
                    description.setText("MYTV");
                    description.setTextSize(20);
                    pieChart.setDescription(description);

                    pieChart.animateX(900); // tạo hiệu ứng

                    PieDataSet dataSet= new PieDataSet( yValues,"Đơn vị");
                    dataSet.setSliceSpace(8f); // thay đổi khoảng cách phần trăm
                    dataSet.setSelectionShift(10f);
                    dataSet.setColors(ColorTemplate.JOYFUL_COLORS);


                    PieData data = new PieData((dataSet));
                    data.setValueTextSize(10f);
                    data.setValueTextColor(Color.WHITE);


                    pieChart.setData(data);

                    pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                        @Override
                        public void onValueSelected(Entry e, final Highlight h) {
                            float thutu1 = h.getX();

                            int thutu = Math.round(thutu1);


                            put(a,madonvi[thutu]);
                            Toast.makeText(getApplicationContext(),""+donvi[thutu] , Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onNothingSelected() {

                        }
                    });
                    //het bieu do
                }catch(JSONException e){
                    Toast.makeText(getApplicationContext(),"Lỗi: nhập sai chu kì nợ", Toast.LENGTH_LONG).show();
                    resetChart();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("datebieudo",a);

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    public void resetChart(){
        pieChart.notifyDataSetChanged();
        pieChart.setData(null);
        pieChart.clear();
        pieChart.invalidate();
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
        X = Math.abs(X);
        Y = Math.abs(Y);
        Toast.makeText(getApplicationContext(),"X là: "+X+"Y là: "+Y, Toast.LENGTH_LONG).show();

        boolean check = true;
        Intent intent = getIntent();
        String datebieudo = intent.getStringExtra("datebieudo");
        if(X>Y){
            Intent intent1 = new Intent(this,bieudo1.class);
            intent1.putExtra("datebieudo",datebieudo);
            startActivity(intent1);
        }if(X<Y) {
            Intent intent1 = new Intent(this,bieudo2.class);
            intent1.putExtra("datebieudo",datebieudo);
            startActivity(intent1);
        }else  check = false;
        return check;
    }
}
