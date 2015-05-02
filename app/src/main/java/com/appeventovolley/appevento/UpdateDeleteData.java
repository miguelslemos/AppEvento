package com.appeventovolley.appevento;

/**
 * Created by Miguel on 30/04/2015.
 */
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

public class UpdateDeleteData extends Activity {

    EditText modificarnome;
    EditText modificarlocal;
    EditText modificarvalor;
    EditText modificarcategoria;
    EditText modificardescricao;

    String nome;
    String local;
    String valor;
    String categoria;
    String descricao;
    String id;

    ProgressDialog PD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_delete);

        PD = new ProgressDialog(this);
        PD.setMessage("please wait.....");
        PD.setCancelable(false);

        modificarnome = (EditText) findViewById(R.id.modificarnome);
        modificarlocal = (EditText) findViewById(R.id.modificarlocal);
        modificarvalor = (EditText) findViewById(R.id.modificarvalor);
        modificarcategoria = (EditText) findViewById(R.id.modificarcategoria);
        modificardescricao = (EditText) findViewById(R.id.modificardescricao);


        Intent i = getIntent();

        HashMap<String, String> item = (HashMap<String, String>) i
                .getSerializableExtra("item");
       id=item.get(ReadData.id);
        nome = item.get(ReadData.nome);
        local = item.get(ReadData.local);
        valor = item.get(ReadData.valor);
        categoria = item.get(ReadData.categoria);
        descricao = item.get(ReadData.descricao);

        modificarnome.setText(nome);
        modificarlocal.setText(local);
        modificarvalor.setText(valor);
        modificarcategoria.setText(categoria);
        modificardescricao.setText(descricao);

    }

    public void update(View view) {
        PD.show();

        nome = modificarnome.getText().toString();
        local = modificarlocal.getText().toString();
        valor = modificarvalor.getText().toString();
        categoria = modificarcategoria.getText().toString();
        descricao = modificardescricao.getText().toString();

         String update_url = "http://ec2-52-24-88-207.us-west-2.compute.amazonaws.com/php/update_item.php?id="
                 + id + "&nome=" + nome + "&local=" + local + "&valor=" + valor + "&categoria=" + categoria+ "&descricao=" + descricao;


        JsonObjectRequest update_request = new JsonObjectRequest(update_url,
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    int success = response.getInt("success");

                    if (success == 1) {
                        PD.dismiss();
                        Toast.makeText(getApplicationContext(),
                                "Atualizado com sucesso",
                                Toast.LENGTH_SHORT).show();
                        // redirect to readdata
                        MoveToReadData();

                    } else {
                        PD.dismiss();
                        Toast.makeText(getApplicationContext(),
                                "falha na atualizacao", Toast.LENGTH_SHORT)
                                .show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        // Adding request to request queue
        MyApplication.getInstance().addToReqQueue(update_request);
    }


    public void delete(View view) {
        PD.show();
        String delete_url = "http://ec2-52-24-88-207.us-west-2.compute.amazonaws.com/php/delete_item.php?id=" + id;

        JsonObjectRequest delete_request = new JsonObjectRequest(delete_url,
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    int success = response.getInt("success");

                    if (success == 1) {
                        PD.dismiss();
                        Toast.makeText(getApplicationContext(),
                                "Deletado com sucesso",
                                Toast.LENGTH_SHORT).show();
                        // redirect to readdata
                        MoveToReadData();
                    } else {
                        PD.dismiss();
                        Toast.makeText(getApplicationContext(),
                                "Falha ao deletar", Toast.LENGTH_SHORT)
                                .show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        // Adding request to request queue
        MyApplication.getInstance().addToReqQueue(delete_request);

    }

    private void MoveToReadData() {
        Intent read_intent = new Intent(UpdateDeleteData.this, ReadData.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(read_intent);

    }
}