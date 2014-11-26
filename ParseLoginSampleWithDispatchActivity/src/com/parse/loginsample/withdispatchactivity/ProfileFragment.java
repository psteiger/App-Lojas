/*
 *  Copyright (c) 2014, Facebook, Inc. All rights reserved.
 *
 *  You are hereby granted a non-exclusive, worldwide, royalty-free license to use,
 *  copy, modify, and distribute this software in source code or binary form for use
 *  in connection with the web services and APIs provided by Facebook.
 *
 *  As with any software that integrates with the Facebook platform, your use of
 *  this software is subject to the Facebook Developer Principles and Policies
 *  [http://developers.facebook.com/policy/]. This copyright notice shall be
 *  included in all copies or substantial portions of the software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 *  FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 *  COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 *  IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 *  CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package com.parse.loginsample.withdispatchactivity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.parse.ParseUser;


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



/**
 * Shows the user profile. This simple activity can only function when there is a valid
 * user, so we must protect it with SampleDispatchActivity in AndroidManifest.xml.
 */
public class ProfileFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private TextView titleTextView;
    private TextView emailTextView;
    private TextView nameTextView;

    public static ProfileFragment newInstance(int sectionNumber) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ProfileFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_profile, container, false);

        titleTextView = (TextView) rootView.findViewById(R.id.profile_title);
        emailTextView = (TextView) rootView.findViewById(R.id.profile_email);
        nameTextView = (TextView) rootView.findViewById(R.id.profile_name);
        titleTextView.setText(R.string.profile_title_logged_in);

        rootView.findViewById(R.id.logout_button).setOnClickListener(new OnClickListener() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override

            public void onClick(View v) {
                ParseUser.logOut();

                getActivity().finish();

            }
        });

        return rootView;
    }

          /**
        // FLAG_ACTIVITY_CLEAR_TASK only works on API 11, so if the user
        // logs out on older devices, we'll just exit.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
          Intent intent = new Intent(ProfileFragment.this,
              SampleDispatchActivity.class);
          intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
              | Intent.FLAG_ACTIVITY_NEW_TASK);
          startActivity(intent);
        } else {
          finish();
        }
      }
    });
    }
*/
    @Override
    public void onStart() {
        super.onStart();
        // Set up the profile page based on the current user.
        ParseUser user = ParseUser.getCurrentUser();
        showProfile(user);
    }

    /**
    * Shows the profile of the given user.
    *
    * @param user
    */
    private void showProfile(ParseUser user) {
        if (user != null) {
            emailTextView.setText(user.getEmail());
            String fullName = user.getString("name");
            if (fullName != null) {
                nameTextView.setText(fullName);
            }
        }
    }
}

