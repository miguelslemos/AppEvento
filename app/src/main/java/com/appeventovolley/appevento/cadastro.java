package com.appeventovolley.appevento;

import java.util.HashMap;
import java.util.Map;
import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by Miguel on 30/04/2015.
 */
public class cadastro extends Activity {
    String nome;
    String local;
    String valor;
    String categoria;
    String descricao;
    String url = "http://ec2-52-24-88-207.us-west-2.compute.amazonaws.com/php/take_order.php";

    EditText editnome;
    EditText editlocal;
    EditText editvalor;
    EditText editcategoria;
    EditText editdescricao;

    TextView txtnome;
    TextView txtlocal;
    TextView txtvalor;
    TextView txtcategoria;
    TextView txtdescricao;

    ProgressDialog PD;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro);

        PD = new ProgressDialog(this);
        PD.setMessage("Loading.....");
        PD.setCancelable(false);

        editnome = (EditText) findViewById(R.id.editnome);
        editlocal = (EditText) findViewById(R.id.editlocal);
        editvalor = (EditText) findViewById(R.id.editvalor);
        editcategoria=  (EditText) findViewById(R.id.editcategoria);
        editdescricao = (EditText) findViewById(R.id.editdescricao);
        txtnome= (TextView) findViewById(R.id.txtnome);
        txtlocal= (TextView) findViewById(R.id.txtlocal);
        txtvalor= (TextView) findViewById(R.id.txtvalor);
        txtcategoria= (TextView) findViewById(R.id.txtcategoria);
        txtdescricao= (TextView) findViewById(R.id.txtdescricao);

    }

    public void inserir(View v) {
        PD.show();
        nome = editnome.getText().toString();
        local = editlocal.getText().toString();
        valor = editvalor.getText().toString();
        categoria = editcategoria.getText().toString();
        descricao = editdescricao.getText().toString();


        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        PD.dismiss();
                        editnome.setText("");
                        editlocal.setText("");
                        editvalor.setText("");
                        editcategoria.setText("");
                        editdescricao.setText("");

                        Toast.makeText(getApplicationContext(),
                                "Salvo com sucesso",
                                Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                PD.dismiss();
                Toast.makeText(getApplicationContext(),
                        "Falha ao salvar", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("nome", nome);
                params.put("local", local);
                params.put("valor", valor);
                params.put("categoria", categoria);
                params.put("descricao", descricao);


                return params;
            }
        };

        // Adding request to request queue
        MyApplication.getInstance().addToReqQueue(postRequest);
    }


}