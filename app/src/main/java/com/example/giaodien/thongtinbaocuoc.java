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
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
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
import java.util.HashMap;
import java.util.Map;

public class thongtinbaocuoc extends AppCompatActivity {
    LinearLayout linearLayout;
    TextView donvitinh, donvitapdoan, thangbaocuoc, ngaybaocuoc, mst, dvtc, dc, dtlh, htvcskh, tentaikhoan,
                dc2, stk, nganhang, tenkhachhang, diachi, thuebao, makh, tuyenthu, tongkhuyenmai, vat, tongcuocphi, tongthanhtoan, sotien;
    Button in;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtinbaocuoc);
        //String chukyno = "20190501";
        //String matb="3823755";
        Intent intent = getIntent();
        String chukyno = intent.getStringExtra("chukyno");
        String matb = intent.getStringExtra("matb");
        final String urladd = intent.getStringExtra("urladd");
        bien();
        //Toast.makeText(getApplicationContext(),matb, Toast.LENGTH_LONG).show();
        dulieu(chukyno,urladd);
        dulieu2(matb,chukyno,urladd);
        in.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                bitmap = loadBitmapFromView(linearLayout, linearLayout.getWidth(), linearLayout.getHeight());
                createPdf();
            }
        });
    }

    private void bien(){
        linearLayout = (LinearLayout)findViewById(R.id.layout);
        donvitinh = (TextView)findViewById(R.id.donvitinh);
        donvitapdoan = (TextView)findViewById(R.id.donvitapdoan);
        thangbaocuoc = (TextView)findViewById(R.id.thangbaocuoc);
        ngaybaocuoc = (TextView)findViewById(R.id.ngaybaocuoc);
        mst = (TextView)findViewById(R.id.mst);
        dvtc = (TextView)findViewById(R.id.dvtc);
        dc = (TextView)findViewById(R.id.dc1);
        dtlh = (TextView)findViewById(R.id.dtlh);
        htvcskh = (TextView)findViewById(R.id.htvcskh);
        tentaikhoan = (TextView)findViewById(R.id.tentaikhoan);
        dc2 = (TextView)findViewById(R.id.dc2);
        stk = (TextView)findViewById(R.id.stk);
        nganhang = (TextView)findViewById(R.id.nganhang);
        tenkhachhang = (TextView)findViewById(R.id.tenkhachhang);
        diachi = (TextView)findViewById(R.id.diachi);
        thuebao = (TextView)findViewById(R.id.sdt);
        makh = (TextView)findViewById(R.id.makh);
        tuyenthu = (TextView)findViewById(R.id.tuyenthu);
        tongkhuyenmai = (TextView)findViewById(R.id.tongkhuyenmai);
        vat = (TextView)findViewById(R.id.vat);
        tongcuocphi = (TextView)findViewById(R.id.tongcuocphi);
        tongthanhtoan = (TextView)findViewById(R.id.tongthanhtoan);
        in = (Button)findViewById(R.id.btnin);
        sotien = (TextView)findViewById(R.id.sotien);
    }

    private void dulieu(final String chukyno, String urladd){
        String url =urladd+"/dvthu.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),response.trim(), Toast.LENGTH_LONG).show();
                try{
                    JSONArray jsonArray = new JSONArray(response.trim());
                    JSONObject jsonObject = (JSONObject)jsonArray.get(0);

                    String vdonvitapdoan = jsonObject.getString("DONVI_TAPDOAN");
                    int donvitv = jsonObject.getInt("DONVITC_ID");
                    String vdonvitinh = jsonObject.getString("DONVI_TINH");
                    String vdonvithucuoc = jsonObject.getString("DONVI_THUCUOC");
                    String vmst = jsonObject.getString("MST");
                    String vdiachi = jsonObject.getString("DIACHI");
                    String vdtlh = jsonObject.getString("DIENTHOAI_LH");
                    String vdtcskh = jsonObject.getString("DIENTHOAI_CSKH");
                    String vtenktnh = jsonObject.getString("TEN_TKNH");
                    String vdiachi2 = jsonObject.getString("DIACHI_TKNH");
                    String vsotk = jsonObject.getString("SO_TK");
                    String vnganhang = jsonObject.getString("NGANHANG");

                    //////////////
                    if(vsotk=="null"){
                        vsotk="";
                    }
                    if(vnganhang=="null"){
                        vnganhang="";
                    }
                    String thang = chukyno.substring(4,6);
                    String nam = chukyno.substring(0,4);
                    ///////////
                    thangbaocuoc.setText("THÁNG "+thang+" NĂM "+nam);
                    if (thang =="02") {
                        ngaybaocuoc.setText("(Từ ngày:  01-02-"+nam+" đến ngày:  28-02-"+nam+" )");
                    }else if(thang=="01"||thang=="03"||thang=="05"||thang=="07"||thang=="09"||thang=="11"){
                        ngaybaocuoc.setText("(Từ ngày:  01-"+thang+"-"+nam+"  đến ngày:  31-"+thang+"-"+nam+")");
                    }else {
                        ngaybaocuoc.setText("(Từ ngày:  01-"+thang+"-"+nam+"  đến ngày:  30-"+thang+"-"+nam+")");
                    }
                    /////////////

                    donvitapdoan.setText(vdonvitapdoan);
                    donvitinh.setText(vdonvitinh);
                    dvtc.setText(vdonvithucuoc);
                    mst.setText(vmst);
                    dc.setText(vdiachi);
                    dtlh.setText(vdtlh);
                    htvcskh.setText(vdtcskh);
                    tentaikhoan.setText(vtenktnh);
                    dc2.setText(vdiachi2);
                    stk.setText(vsotk);
                    nganhang.setText(vnganhang);

                    ////////////
                    //Toast.makeText(getApplicationContext(),thang+" "+nam, Toast.LENGTH_LONG).show();
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
                params.put("chukyno",chukyno);
                //params.put("pass1word",editpassword.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void dulieu2(final String matb, final String chukyno, final String urladd) {
        String url =urladd+"/dvthu2.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),response.trim(), Toast.LENGTH_LONG).show();
                try{
                    JSONArray jsonArray = new JSONArray(response.trim());
                    JSONObject jsonObject = (JSONObject)jsonArray.get(0);
                    int vdonvitv_id = jsonObject.getInt("DONVITC_ID");
                    int vthanhtoan_id = jsonObject.getInt("THANHTOAN_ID");
                    String vmatt = jsonObject.getString("MA_TT");
                    String vtenkhachhang = jsonObject.getString("TEN_TT");
                    String vdiachi = jsonObject.getString("DIACHI_CT");
                    String vmatb = jsonObject.getString("MATB_DD");
                    String vhinhthuc = jsonObject.getString("HINHTHUC_TT");
                    String vmst = jsonObject.getString("MST");
                    String vcuoc = jsonObject.getString("CUOC");
                    String vvat = jsonObject.getString("VAT");
                    String vkhuyenmai = jsonObject.getString("KHUYENMAI");
                    String vtongtien = jsonObject.getString("TONGTIEN");
                    /////////////


                    /////////////////////
                    tenkhachhang.setText(vtenkhachhang);
                    diachi.setText(vdiachi);
                    thuebao.setText(vmatb);
                    makh.setText(vmatt);
                    tongcuocphi.setText(vcuoc);
                    vat.setText(vvat);
                    tongkhuyenmai.setText(vkhuyenmai);
                    tongthanhtoan.setText(vtongtien);
                    doichu(vtongtien,urladd);
                    /////////////

                    //Toast.makeText(getApplicationContext(),doichu(vtongtien).toString()+" set text ", Toast.LENGTH_LONG).show();
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
                params.put("matb",matb);
                params.put("chukyno",chukyno);
                //params.put("pass1word",editpassword.getText().toString().trim());
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
        String targetPdf = directory_path + "baocuoc.pdf";
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
    private void doichu(final String sotien1, String urladd){
        final String[] tien = {new String("")};
        String url =urladd+"/doichu.php";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(getApplicationContext(),response.trim(), Toast.LENGTH_LONG).show();
                try{
                    JSONArray jsonArray = new JSONArray(response.trim());
                    JSONObject jsonObject = (JSONObject)jsonArray.get(0);
                    tien[0] = jsonObject.getString("KQ");
                    sotien.setText("Bằng chữ: "+tien[0]);
                    //Toast.makeText(getApplicationContext(),tien[0]+" ", Toast.LENGTH_LONG).show();
                }catch (JSONException e){
                    Toast.makeText(getApplicationContext(),e.toString()+" lỗi excep", Toast.LENGTH_LONG).show();
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
                params.put("sotien",sotien1);
                //params.put("pass1word",editpassword.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);

    }

    public static Bitmap loadBitmapFromView(View v, int width, int height) {
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);
        return b;
    }

}
