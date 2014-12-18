package com.parse.loginsample.withdispatchactivity;

import android.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.SearchView;

import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;


/**
 * An activity representing a list of Lojas. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link LojaDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link LojaListFragment} and the item details
 * (if present) is a {@link LojaDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link LojaListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class LojaListActivity extends Activity
        implements LojaListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private ParseQuery<ParseObject> query = ParseQuery.getQuery("Loja");
    private ParseObject item;
    public static final String SEARCH_STRING = "search_string";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loja_list);
        // Show the Up button in the action bar.
        getActionBar().setDisplayHomeAsUpEnabled(true);

        List<String> subscribedChannels = ParseInstallation.getCurrentInstallation().getList("channels");

        if (subscribedChannels != null) {
            for (String channel : subscribedChannels) {
                ParsePush.unsubscribeInBackground(channel);
            }
        }

        ParsePush.subscribeInBackground(ParseUser.getCurrentUser().getUsername());
        /*
        Fragment f = ((LojaListFragment) getFragmentManager()
                        .findFragmentById(R.id.loja_list));

        if(getIntent().getStringExtra(LojaListActivity.SEARCH_STRING) != null) {
            ((LojaListFragment) getFragmentManager()
                    .findFragmentById(R.id.loja_list))
                    .refreshLojaListWithSearchString(getIntent().getStringExtra(LojaListActivity.SEARCH_STRING));
        }
        */

        if (findViewById(R.id.loja_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((LojaListFragment) getFragmentManager()
                    .findFragmentById(R.id.loja_list))
                    .setActivateOnItemClick(true);
        }


        // TODO: If exposing deep links into your app, handle intents here.
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {


        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Log.e("LojaListActivity handleIntent", query);
            Intent searchIntent = new Intent(this, LojaListActivity.class);
            searchIntent.putExtra(LojaListFragment.SEARCH_STRING, query);
            startActivity(searchIntent);


        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Callback method from {@link LojaListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id) {
        Log.d("LojaListActivity onItemSelected", id);
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(LojaDetailFragment.ARG_ITEM_ID, id);
            LojaDetailFragment fragment = new LojaDetailFragment();
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .replace(R.id.loja_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, LojaDetailActivity.class);
            detailIntent.putExtra(LojaDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }
}
