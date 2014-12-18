package com.parse.loginsample.withdispatchactivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;


public class UserList extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        setTitle("Lista de Usuarios");

        ParseQuery<ParseObject> queryLoja = ParseQuery.getQuery("Loja");
        queryLoja.include("adminId");
        queryLoja.whereEqualTo("adminId", ParseUser.getCurrentUser());

        ParseQuery<ParseUser> userQuery = ParseUser.getQuery();

        ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Ponto");
        query2.include("lojaId");
        query2.include("userId");

        try {
            query2.whereEqualTo("lojaId", queryLoja.getFirst());
        } catch (ParseException e) {
            Log.e("UserList", "fuuu");
        }

        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, R.layout.cupom_item);

        try {
            //Log.e("LojaDetailFragment query count", String.valueOf(query2.count()));


            for (ParseUser user : userQuery.find()) {
                if (!user.getUsername().equals("admin")) {
                    query2.whereEqualTo("userId", user);
                    adapter.add(user.getUsername() + " - " + query2.count() + " pontos");
                }
            }
        } catch (ParseException e) {
            Log.e("LojaDetailFragment", "falhou listagem cupom");
        }

        ListView listaUserView = (ListView) findViewById(R.id.lista_users);
        listaUserView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_list, menu);
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
