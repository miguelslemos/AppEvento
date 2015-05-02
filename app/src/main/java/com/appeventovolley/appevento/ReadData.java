package com.appeventovolley.appevento;

/**
 * Created by Miguel on 30/04/2015.
 */

import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

public class ReadData extends ListActivity {


    String url = "http://ec2-52-24-88-207.us-west-2.compute.amazonaws.com/php/read_allorder.php";
    ArrayList<HashMap<String, String>> evento_list;
    ProgressDialog PD;
    ListAdapter adapter;

    // JSON Node names
    public static final String id = "id";
    public static final String nome = "nome";
    public static final String local = "local";
    public static final String valor = "valor";
    public static final String categoria = "categoria";
    public static final String descricao = "descricao";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        evento_list = new ArrayList<HashMap<String, String>>();

        PD = new ProgressDialog(this);
        PD.setMessage("Loading.....");
        PD.setCancelable(false);

        getListView().setOnItemClickListener(new ListitemClickListener());

        ReadDataFromDB();
    }

    private void ReadDataFromDB() {
        PD.show();
        JsonObjectRequest jreq = new JsonObjectRequest(Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int success = response.getInt("success");

                            if (success == 1) {
                                JSONArray ja = response.getJSONArray("db_evento");

                                for (int i = 0; i < ja.length(); i++) {

                                    JSONObject jobj = ja.getJSONObject(i);
                                    HashMap<String, String> item = new HashMap<String, String>();

                                     item.put(id,jobj.getString(id));
                                    item.put(nome, jobj.getString(nome));
                                 item.put(local,jobj.getString(local));
                                    item.put(valor, "R$: "+ jobj.getString(valor));

                                    item.put(categoria,jobj.getString(categoria));
                                    item.put(descricao, jobj.getString(descricao));

                                    evento_list.add(item);

                                } // for loop ends

                                String[] from = {categoria, nome,valor,local,descricao};
                                int[] to = { R.id.local, R.id.nome,R.id.valor };

                                adapter = new SimpleAdapter(
                                        getApplicationContext(), evento_list,
                                        R.layout.list_items, from, to);

                                setListAdapter(adapter);

                                PD.dismiss();

                            } // if ends

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                PD.dismiss();
            }
        });

        // Adding request to request queue
        MyApplication.getInstance().addToReqQueue(jreq);

    }



    //On List Item Click move to UpdateDelete Activity
    class ListitemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {

            Intent modify_intent = new Intent(ReadData.this,
                    UpdateDeleteData.class);

            modify_intent.putExtra("item", evento_list.get(position));

            startActivity(modify_intent);

        }

    }
}