package com.appeventovolley.appevento;

import java.util.HashMap;
import java.util.Map;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void read(View v) {
        Intent read_intent = new Intent(MainActivity.this, ReadData.class);
        startActivity(read_intent);
    }

    public void cadastro(View v){
        Intent intent = new Intent(this,cadastro.class);
        startActivity(intent);
    }

}