package com.parse.loginsample.withdispatchactivity;

import android.app.Activity;
import android.os.Bundle;
import android.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;


import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.loginsample.withdispatchactivity.dummy.DummyContent;

import java.util.ArrayList;
import java.util.List;

/**
 * A list fragment representing a list of Lojas. This fragment
 * also supports tablet devices by allowing list items to be given an
 * 'activated' state upon selection. This helps indicate which item is
 * currently being viewed in a {@link LojaDetailFragment}.
 * <p>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class LojaListFragment extends ListFragment {

    /**
     * The serialization (saved instance state) Bundle key representing the
     * activated item position. Only used on tablets.
     */
    private static final String STATE_ACTIVATED_POSITION = "activated_position";
    public static final String SEARCH_STRING = "search_string";

    /**
     * The fragment's current callback object, which is notified of list item
     * clicks.
     */
    private Callbacks mCallbacks = sDummyCallbacks;

    /**
     * The current activated item position. Only used on tablets.
     */
    private int mActivatedPosition = ListView.INVALID_POSITION;

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */

    private ParseQueryAdapter<ParseObject> mainAdapter;
    ParseObject item;
    private List<Loja> lojas;
    private CustomAdapter searchAdapter;
    private ListView listView;
    private EditText edt;
    public boolean withSearchString = false;

    public interface Callbacks {
        /**
         * Callback for when an item has been selected.
         */
        public void onItemSelected(String id);
    }

    /**
     * A dummy implementation of the {@link Callbacks} interface that does
     * nothing. Used only when this fragment is not attached to an activity.
     */
    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(String id) {
        }
    };

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public LojaListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //edt = (EditText) getActivity().findViewById(R.id.search_text);

        lojas = new ArrayList<Loja>();

        /*
        mainAdapter = new ParseQueryAdapter<ParseObject>(getActivity(), "Loja");
        //mainAdapter = new CustomAdapter(getActivity());

        mainAdapter.setTextKey("nome");
        mainAdapter.setImageKey("icon");
        mainAdapter.loadObjects();
        */
        Bundle arguments = getArguments();

 /*
        if (arguments != null) {
            if (getArguments().containsKey(SEARCH_STRING)) {
                Log.e("LojaListFragment onCreate search string", getArguments().getString(SEARCH_STRING));
                refreshLojaListWithSearchString(getArguments().getString(SEARCH_STRING));
            }

        }*/
        // TODO: replace with a real list adapter.
        //setListAdapter(mainAdapter);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(getActivity() != null) {
            if (getActivity().getIntent().getStringExtra(LojaListFragment.SEARCH_STRING) != null) {
                Log.e("LojaListFragment onCreate search string", getActivity().getIntent().getStringExtra(LojaListFragment.SEARCH_STRING));
                searchAdapter = new CustomAdapter(getActivity(), getActivity().getIntent().getStringExtra(LojaListFragment.SEARCH_STRING));
                searchAdapter.setTextKey("nome");
                searchAdapter.setImageKey("icon");
                searchAdapter.loadObjects();
                setListAdapter(searchAdapter);
                refreshLojaListWithSearchString(getActivity().getIntent().getStringExtra(LojaListFragment.SEARCH_STRING));
            } else {
                Log.e("LojaListFragment SEARCH_STRING is", "null");
                mainAdapter = new ParseQueryAdapter<ParseObject>(getActivity(), "Loja");
                //mainAdapter = new CustomAdapter(getActivity());

                mainAdapter.setTextKey("nome");
                mainAdapter.setImageKey("icon");
                mainAdapter.loadObjects();
                setListAdapter(mainAdapter);

                refreshLojaList();
            }
        }

        // Restore the previously serialized activated item position.
        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Reset the active callbacks interface to the dummy implementation.
        mCallbacks = sDummyCallbacks;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);
        //Loja loja = lojas.get(position);


        // Notify the active callbacks interface (the activity, if the
        // fragment is attached to one) that an item has been selected.
        mCallbacks.onItemSelected(lojas.get(position).getId());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    /**
     * Turns on activate-on-click mode. When this mode is on, list items will be
     * given the 'activated' state when touched.
     */
    public void setActivateOnItemClick(boolean activateOnItemClick) {
        // When setting CHOICE_MODE_SINGLE, ListView will automatically
        // give items the 'activated' state when touched.
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }

    private void refreshLojaList() {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Loja");

        query.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> lojaList, ParseException e) {
                if (e == null) {
                    // If there are results, update the list of posts
                    // and notify the adapter
                    lojas.clear();
                    for (ParseObject loja : lojaList) {
                        lojas.add(new Loja(loja.getObjectId(), loja.getString("nome")));
                    }
                    //((ArrayAdapter<Loja>) getListAdapter()).notifyDataSetChanged();
                } else {
                    Log.d(getClass().getSimpleName(), "Error: " + e.getMessage());
                }
            }
        });
    }

    public void refreshLojaListWithSearchString(final String str) {

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Loja");
        query.whereContains("nome", str);
        Log.e("LojaListFragment", "searchable " + str);
        query.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> lojaList, ParseException e) {
                if (e == null) {
                    // If there are results, update the list of posts
                    // and notify the adapter
                    lojas.clear();
                    for (ParseObject loja : lojaList) {
                        Log.e("LojaListFragment refreshLojaListWithSearchString loja", loja.getString("nome"));
                        lojas.add(new Loja(loja.getObjectId(), loja.getString("nome")));
                    }
                    //((ArrayAdapter<Loja>) getListAdapter()).notifyDataSetChanged();
                } else {
                    Log.e(getClass().getSimpleName(), "Error: " + e.getMessage());
                }
            }
        });
    }
}
