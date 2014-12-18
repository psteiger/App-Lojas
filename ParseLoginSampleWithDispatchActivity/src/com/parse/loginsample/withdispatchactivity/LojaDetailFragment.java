package com.parse.loginsample.withdispatchactivity;

import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;
import com.parse.loginsample.withdispatchactivity.dummy.DummyContent;

import java.util.List;

/**
 * A fragment representing a single Loja detail screen.
 * This fragment is either contained in a {@link LojaListActivity}
 * in two-pane mode (on tablets) or a {@link LojaDetailActivity}
 * on handsets.
 */

public class LojaDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;
    private ParseQuery<ParseObject> query = ParseQuery.getQuery("Loja");
    private ParseObject loja;
    private ParseQueryAdapter<ParseObject> mainAdapter;
    private ArrayAdapter<String> adapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public LojaDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e("LojaDetailFragment onCreate", getArguments().getString(ARG_ITEM_ID));
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            //mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            try {
                loja = query.get(getArguments().getString(ARG_ITEM_ID));
                getActivity().setTitle(loja.getString("nome"));
            } catch (ParseException e) {
                Log.d("itemnull", getArguments().getString(ARG_ITEM_ID));
            }

            /*
            query.getFirstInBackground(new GetCallback<ParseObject>() {
                public void done(ParseObject object, ParseException e) {
                    if (e == null) {
                        item = object;
                        Log.d("item name", loja.getString("nome"));
                    } else {
                        Log.d("fudeu", "FUDEU");
                    }
                }
            });*/



        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_loja_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (loja != null) {


            ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Ponto");

            query2.include("userId");
            query2.include("lojaId");

            query2.whereEqualTo("userId", ParseUser.getCurrentUser());
            query2.whereEqualTo("lojaId", loja);

            String pontos = "0";
            try {
                //Log.e("e", query2.getFirst().getObjectId().toString());
                pontos = String.valueOf(query2.count());
            } catch (ParseException e) {
                Log.e("Cagou", pontos);
            }

            /**
             query2.findInBackground(new FindCallback<ParseObject>() {
             public void done(List<ParseObject> objList, ParseException e) {
             TextView pontosView = (TextView) v.findViewById(R.id.pontos);
             pontosView.setText(object.getCreatedAt().toString());
             }
             });
             */

            // Add a reminder of how long this item has been outstanding
            TextView pontosView = (TextView) rootView.findViewById(R.id.loja_detail_pontos);
            ((TextView) rootView.findViewById(R.id.loja_detail)).setText(pontos + " pontos. " + ((Integer.valueOf(pontos) >= 10) ? (Integer.valueOf(pontos) % 10) : 0) + " brindes.");
            pontosView.setText("Lista de cupons: ");

            adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.cupom_item);
            try {
                Log.e("LojaDetailFragment query count", String.valueOf(query2.count()));


                for (ParseObject cupom : query2.find()) {
                    Log.e("LojaDetailFragment Cupom id", cupom.toString());
                    Log.e("Listando cupom criado em: ", cupom.getCreatedAt().toString());
                    adapter.add(cupom.getCreatedAt().toString());
                }
            } catch (ParseException e) {
                Log.e("LojaDetailFragment", "falhou listagem cupom");
            }

            /*
            query2.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> cupons, ParseException e) {
                    if (e == null) {
                        Log.e("LojaDetailFragment cupons size", String.valueOf(cupons.size()));

                        for (ParseObject cupom : cupons)
                            adapter.add(cupom.getDate("createdAt").toString());

                        //    Log.e("LojaDetailFragment Cupom id", cupom.getDate("createdAt").toString());
                    } else {
                        Log.e("erro", "erro");
                    }
                }
            });
            */

            mainAdapter = new ParseQueryAdapter<ParseObject>(this.getActivity(), "Ponto");
            mainAdapter.setTextKey("createdAt");

            ListView listaCuponsView = (ListView) rootView.findViewById(R.id.lista_cupons);
            listaCuponsView.setAdapter(adapter);

        }

        return rootView;
    }
}
