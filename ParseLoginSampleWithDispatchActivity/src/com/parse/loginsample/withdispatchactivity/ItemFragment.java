package com.parse.loginsample.withdispatchactivity;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View.OnClickListener;



import com.parse.ParseObject;
import com.parse.ParseQueryAdapter;





import com.parse.loginsample.withdispatchactivity.dummy.DummyContent;

/**
 * A fragment representing a list of Items.
 * <p />
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p />
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class ItemFragment extends Fragment { //implements AbsListView.OnItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SECTION_NUMBER = "section_number";


    //private OnFragmentInteractionListener mListener;






    private ParseQueryAdapter<ParseObject> mainAdapter;
    private CustomAdapter urgentTodosAdapter;
    private ListView listView;










    // TODO: Rename and change types of parameters
    public static ItemFragment newInstance(int sectionNumber) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
/*
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
*/
        View rootView = inflater.inflate(R.layout.activity_first, container, false);

        mainAdapter = new ParseQueryAdapter<ParseObject>(getActivity(), "Loja");
        mainAdapter.setTextKey("nome");
        mainAdapter.setImageKey("icon");

        // Initialize the subclass of ParseQueryAdapter
        //urgentTodosAdapter = new CustomAdapter(getActivity());

        // Initialize ListView and set initial view to mainAdapter
        listView = (ListView) rootView.findViewById(R.id.list); // getView().findViewById(R.id.listView1);
        listView.setAdapter(mainAdapter);
        mainAdapter.loadObjects();

        // Initialize toggle button
        Button toggleButton = (Button) rootView.findViewById(R.id.toggleButton);
        toggleButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (listView.getAdapter() == mainAdapter) {
                    listView.setAdapter(urgentTodosAdapter);
                    urgentTodosAdapter.loadObjects();
                } else {
                    listView.setAdapter(mainAdapter);
                    mainAdapter.loadObjects();
                }
            }
        });

        return rootView;
    }

    /*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        return view;
    }
*/
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));

    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }


/*
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
        }
    }
*/
    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.

    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyText instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }
     */
    /**
    * This interface must be implemented by activities that contain this
    * fragment to allow an interaction in this fragment to be communicated
    * to the activity and potentially other fragments contained in that
    * activity.
    * <p>
    * See the Android Training lesson <a href=
    * "http://developer.android.com/training/basics/fragments/communicating.html"
    * >Communicating with Other Fragments</a> for more information.
    */

    /*
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }
    */

}
