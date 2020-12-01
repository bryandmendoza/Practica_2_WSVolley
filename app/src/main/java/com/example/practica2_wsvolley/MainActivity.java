package com.example.practica2_wsvolley;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private EditText txtCountry;
    private RequestQueue request;
    private StringRequest stringRq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getCovidData(View view) {
        txtCountry = findViewById(R.id.txtCountry);
        if(txtCountry.getText().toString().length()==0)
        {
            Toast.makeText(this, "No ha ingresado ningún país." , Toast.LENGTH_LONG).show();
        }
        else
        {
            request = Volley.newRequestQueue(MainActivity.this);
            String URL = "https://covid-19-data.p.rapidapi.com/country?name="+txtCountry.getText().toString();
            stringRq = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    JSONArray covidDataGroup = null;
                    try {
                        covidDataGroup = new JSONArray(response);
                        if (covidDataGroup.length() == 0) {
                            Toast.makeText(MainActivity.this, "No se ha encontrado resultados.", Toast.LENGTH_LONG).show();
                            return;
                        }
                        else {
                            JSONObject covidData = covidDataGroup.getJSONObject(0);
                            Intent intent = new Intent(MainActivity.this, Home.class);
                            Bundle b = new Bundle();
                            b.putString("country_selected", "País elegido: "+covidData.getString("country")+"\n");
                            b.putString("last_update", "Última actualización: "+covidData.getString("lastUpdate")+"\n");
                            b.putString("confirmed", "Casos confirmados: "+covidData.getInt("confirmed")+"\n");
                            b.putString("recovered", "Recuperados: "+covidData.getInt("recovered")+"\n");
                            b.putString("deaths", "Muertos: "+covidData.getInt("deaths")+"\n");
                            intent.putExtras(b);
                            startActivity(intent);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, "Sucedió un error en la consulta. Intente nuevamente. \n Detalle del error: "+error.getMessage() , Toast.LENGTH_LONG).show();
                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("x-rapidapi-key", "9f0ce76771msh83b90ea92683a77p1d24b7jsn781dc1e65c4c");
                    params.put("x-rapidapi-host", "covid-19-data.p.rapidapi.com");
                    return params;
                }
            };
            request.add(stringRq);
        }
    }
}