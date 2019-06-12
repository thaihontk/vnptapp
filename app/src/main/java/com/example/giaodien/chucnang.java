package com.example.giaodien;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class chucnang extends AppCompatActivity {
    Button bieudo1, bieudo2, bieudo3, baocuoc;
    EditText datebieudo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chucnang);
        bien();
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
        intent.putExtra("datebieudo",datebieudo.getText().toString().trim());
        startActivity(intent);
    }
    private void bien(){
        datebieudo = (EditText)findViewById(R.id.datebieudo);
        bieudo1 = (Button)findViewById(R.id.bieudo1);
        bieudo2 = (Button)findViewById(R.id.bieudo2);
        bieudo3 = (Button)findViewById(R.id.bieudo3);
        baocuoc = (Button)findViewById(R.id.baocuoc);
    }
}
