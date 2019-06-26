package com.example.giaodien;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.giaodien.thongtinbaocuoc.loadBitmapFromView;

public class chitietcuocgoi extends AppCompatActivity {
    EditText thuebaoid;
    TextView sdt;
    Button btn;
    Bitmap bitmap;
    LinearLayout linepdf;
    RecyclerView mRecyclerView;
    RecyclerViewAdapter mRcvAdapter;
    private List<String> ngay = new ArrayList<>();
    private List<String> gio = new ArrayList<>();
    private List<String> maynghe = new ArrayList<>();
    private List<String> giay = new ArrayList<>();
    private List<String> tenvung = new ArrayList<>();
    private List<String> tien = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietcuocgoi);
        Intent intent = getIntent();
        final String urladd = intent.getStringExtra("urladd");
        final String chukyno = intent.getStringExtra("chukyno");

        bien();
        btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                bangchon(urladd, chukyno);
                /*bitmap = loadBitmapFromView(linepdf, linepdf.getWidth(), linepdf.getHeight());
                createPdf();*/
            }
        });

    }

    private void bien(){
        thuebaoid = (EditText)findViewById(R.id.thuebaoid);
        sdt = (TextView)findViewById(R.id.sdt);
        btn = (Button)findViewById(R.id.btn);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycleview);
        linepdf = (LinearLayout)findViewById(R.id.linepdf);
    }

    private void bangchon(String urladd, final String chukyno){
        String url =urladd+"/chitietthuebao.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),response.trim(), Toast.LENGTH_LONG).show();

                try {
                    JSONArray jsonArray = new JSONArray(response.trim());
                    JSONObject jsonObject[] = new JSONObject[jsonArray.length()+10];
                    int tongtien=0;
                    for(int i=0;i<jsonArray.length();i++){
                        jsonObject[i] = (JSONObject)jsonArray.get(i);
                        ngay.add(jsonObject[i].getString("NGAY_GOI"));
                        gio.add(jsonObject[i].getString("GIO_GOI"));
                        maynghe.add(jsonObject[i].getString("SO_BIGOI"));
                        giay.add(jsonObject[i].getString("S_GOI"));
                        tenvung.add(jsonObject[i].getString("TEN_VUNG"));
                        tien.add(jsonObject[i].getString("TIEN"));
                        tongtien = tongtien + jsonObject[i].getInt("TIEN");
                    };
                    sdt.setText("CHI TIẾT CUỘC GỌI SỐ ĐIỆN THOẠI: "+thuebaoid.getText().toString()+"    TỔNG TIỀN: "+tongtien);
                    //Toast.makeText(getApplicationContext(),gio.get(2), Toast.LENGTH_SHORT).show();
                    //xử lý dữ liệu
                    mRcvAdapter = new RecyclerViewAdapter(ngay,gio,maynghe,giay,tenvung,tien);

                    LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                    mRecyclerView.setLayoutManager(layoutManager);
                    mRecyclerView.setAdapter(mRcvAdapter);
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
                params.put("thuebaoid",thuebaoid.getText().toString());
                params.put("chukyno",chukyno);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void createPdf() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        //  Display display = wm.getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float hight = displaymetrics.heightPixels ;
        float width = displaymetrics.widthPixels ;

        int convertHighet = (int) hight, convertWidth = (int) width;

        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(convertWidth, convertHighet, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint paint = new Paint();
        canvas.drawPaint(paint);

        bitmap = Bitmap.createScaledBitmap(bitmap, convertWidth, convertHighet, true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 0, 0 , null);
        document.finishPage(page);

        String directory_path = Environment.getExternalStorageDirectory().getPath() + "/mypdf/";
        //Toast.makeText(this, "" + directory_path, Toast.LENGTH_LONG).show();
        File file = new File(directory_path);
        if (!file.exists()) {
            file.mkdirs();
        }
        String targetPdf = directory_path + "chitietcuocgoi.pdf";
        File filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));
            Toast.makeText(this, "Done", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Log.e("main", "error " + e.toString());
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }
        // close the document
        document.close();
    }
}
