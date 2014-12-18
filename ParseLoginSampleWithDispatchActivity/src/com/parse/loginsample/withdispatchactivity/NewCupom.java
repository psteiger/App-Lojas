package com.parse.loginsample.withdispatchactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;


public class NewCupom extends Activity {
    private EditText userId;
    private Button saveButton;
    private Button retirarBrindeButton;
    private ParseObject ponto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_cupom);
        setTitle("Novo Cupom / Retirar Brinde");
        Intent intent = this.getIntent();

        userId = (EditText) findViewById(R.id.user_id);

        saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ponto = new ParseObject("Ponto");
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Loja");
                ParseUser user;


                ParseQuery<ParseUser> userquery = ParseUser.getQuery();
                userquery.whereEqualTo("username", userId.getText().toString());
                try {
                    ponto.put("userId", userquery.getFirst());
                } catch (ParseException e) {
                    Log.e("erro", "user not found");
                }
                /*
                userquery.findInBackground(new FindCallback<ParseUser>() {
                    public void done(List<ParseUser> objects, ParseException e) {
                        if (e == null) {
                            //Log.e("user:", objects.get(0).get);
                            ponto.put("userId", objects.get(0).getObjectId());
                        } else {
                            Log.e("nao achou user", "fail");
                        }
                    }
                });*/

//                Log.e("Aqui", ponto.get("userId").toString());
                query.include("adminId");
                query.whereEqualTo("adminId", ParseUser.getCurrentUser());


                ponto.put("valor", 1);
                try {
                    ponto.put("lojaId", query.getFirst());
                } catch (ParseException e) {
                    Log.e("nao criou ponto", "ponto");
                }
    //                Log.e("ponto", ponto.getObjectId().toString());
                ponto.saveInBackground();
                Toast.makeText(NewCupom.this, "Cupom criado.", Toast.LENGTH_SHORT).show();
                ParsePush push = new ParsePush();
                try {
                    push.setChannel(userquery.getFirst().getUsername());
                    push.setMessage("Voce recebeu um novo cupom na loja: " + query.getFirst().getString("nome"));
                } catch (ParseException e) {
                    Log.e("erro push notification", "user ou loja not found");
                }

                push.sendInBackground();
            }
        });

        retirarBrindeButton = (Button) findViewById(R.id.retirar_brinde_button);
        retirarBrindeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseQuery<ParseObject> queryLoja = ParseQuery.getQuery("Loja");
                queryLoja.include("adminId");
                queryLoja.whereEqualTo("adminId", ParseUser.getCurrentUser());

                ParseQuery<ParseUser> userquery = ParseUser.getQuery();
                userquery.whereEqualTo("username", userId.getText().toString());

                try {
                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Ponto");

                    query.include("userId");
                    query.include("lojaId");

                    query.whereEqualTo("userId", userquery.getFirst());
                    query.whereEqualTo("lojaId", queryLoja.getFirst());
                    query.orderByAscending("createdAt");


                    Log.e("query count", Integer.toString(query.count()));
                    if (query.count() >= 10) {
                        Log.e("query count eh maior que 10", "uhu");

                        for (int i = 0; i < 10; i++) {
                            Log.e("deletando " + i, "uhu");
                            query.getFirst().delete();
                        }

                        Toast.makeText(NewCupom.this, "Brinde retirado (-10 cupons).", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(NewCupom.this, "Cupons insuficientes.", Toast.LENGTH_SHORT).show();
                        Log.e("NewCupom", "nao ha cupons suficientes (10 = 1 brinde)");
                    }
                } catch (ParseException e) {
                    Log.e("NewCupom", "Loja nao existe " + e.toString());
                }

                ParsePush push = new ParsePush();
                try {
                    push.setChannel(userquery.getFirst().getUsername());
                    push.setMessage("Voce retirou um brinde na loja: " + queryLoja.getFirst().getString("nome"));
                } catch (ParseException e) {
                    Log.e("erro push notification", "user ou loja not found");
                }

                push.sendInBackground();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_cupom, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
