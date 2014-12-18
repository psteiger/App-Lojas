package com.parse.loginsample.withdispatchactivity;


import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;
import com.parse.FindCallback;

import java.util.List;

public class CustomAdapter extends ParseQueryAdapter<ParseObject> {

    public CustomAdapter(Context context, final String str) {
        // Use the QueryFactory to construct a PQA that will only show
        // Todos marked as high-pri
        super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {
            public ParseQuery create() {
                ParseQuery query = new ParseQuery("Loja");

                query.whereContains("nome", str);
                return query;
            }
        });
    }

    /*
    // Customize the layout by overriding getItemView
    @Override
    public View getItemView(ParseObject object, View v, ViewGroup parent) {
        if (v == null) {
            v = View.inflate(getContext(), R.layout.urgent_item, null);
        }

        super.getItemView(object, v, parent);

        // Add and download the image
        ParseImageView todoImage = (ParseImageView) v.findViewById(R.id.icon);
        ParseFile imageFile = object.getParseFile("icon");
        if (imageFile != null) {
            todoImage.setParseFile(imageFile);
            todoImage.loadInBackground();
        }

        // Add the title view
        TextView titleTextView = (TextView) v.findViewById(R.id.text1);
        titleTextView.setText(object.getString("nome"));

        ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Ponto");

        query2.include("userId");
        query2.include("lojaId");

        query2.whereEqualTo("userId", ParseUser.getCurrentUser());

        query2.whereEqualTo("lojaId", object);

        String pontos = "0";
        try {
            Log.d("a", query2.getFirst().getObjectId().toString());

            pontos = query2.getFirst().get("valor").toString();
        } catch (ParseException e) {
            Log.d("Cagou", pontos);
        }

        /**
        query2.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objList, ParseException e) {
                TextView pontosView = (TextView) v.findViewById(R.id.pontos);
                pontosView.setText(object.getCreatedAt().toString());
            }
        });
//*//*

        // Add a reminder of how long this item has been outstanding
        TextView pontosView = (TextView) v.findViewById(R.id.pontos);
        pontosView.setText(pontos);

        // Add a reminder of how long this item has been outstanding
        TextView timestampView = (TextView) v.findViewById(R.id.timestamp);
        timestampView.setText(object.getCreatedAt().toString());
        return v;
    }
*/
}
