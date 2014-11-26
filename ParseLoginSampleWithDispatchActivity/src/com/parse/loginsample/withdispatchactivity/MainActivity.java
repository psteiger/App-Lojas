package com.parse.loginsample.withdispatchactivity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.ListView;

import java.util.List;
//import com.amit.rssfeed.RssReaderActivity;

public class MainActivity   extends Activity
                            implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    CustomDrawerAdapter adapter;

    List<DrawerItem> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /** CUSTOM **
        dataList = new ArrayList<DrawerItem>();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.navigation_drawer);

        dataList.add(new DrawerItem("Message", R.drawable.ic_action_email));
        dataList.add(new DrawerItem("Likes", R.drawable.ic_action_good));

        adapter = new CustomDrawerAdapter(this, R.layout.custom_drawer_item,
                dataList);

        mDrawerList.setAdapter(adapter);
        **/

        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));


    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment fragment = ItemFragment.newInstance(position + 1);

        switch (position) {
            case 0:
                fragment = ItemFragment.newInstance(position + 1);
                break;
            case 1:
                fragment = ProfileFragment.newInstance(position + 1);
                fragmentTransaction.replace( R.id.container, fragment ).addToBackStack("tag").commit();

                break;
            case 2:
                //mTitle = getString(R.string.title_section3);
                break;
        }

        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }


    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                Log.e("onSectionAttached", "entrou 1");
                mTitle = getString(R.string.title_section1);
                //loadPage();
                break;
            case 2:
                Log.e("onSectionAttached", "entrou 2");
                mTitle = getString(R.string.title_section2);
                break;
        }
    }

    /*
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        WebView myWebView = (WebView) findViewById(R.id.webview);

        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (myWebView != null)
                        if (myWebView.canGoBack())
                            myWebView.goBack();
                    else
                        finish();

                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }
    */
    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
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

