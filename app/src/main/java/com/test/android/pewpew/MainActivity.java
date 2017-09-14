package com.test.android.pewpew;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {



    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    static FirebaseUser user =  FirebaseAuth.getInstance().getCurrentUser();
    public static DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    static DatabaseReference userFolderRef = rootRef.child("Users");

    static String nikname;

    static User myUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        myUser = new User(user.getDisplayName(),user.getEmail());



        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nikname = dataSnapshot.child("Users/"+user.getUid()+"/nikname").getValue(String.class);
                //Toast.makeText(MainActivity.this, nikname, Toast.LENGTH_SHORT).show();
                myUser.nikname = nikname;
                updateUser();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        

    }

    public static void updateUser()
    {
        Map<String,Object> list = new HashMap<String,Object>();
        list.put(user.getUid(),myUser);
        userFolderRef.updateChildren(list);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView;
            switch(getArguments().getInt(ARG_SECTION_NUMBER))
            {
                case 0:
                    rootView = inflater.inflate(R.layout.player_layout, container, false);
                    Button start = (Button) rootView.findViewById(R.id.Start1v1);
                    start.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getContext(),Console.class);
                            startActivity(intent);
                        }
                    });
                    break;
                case 1:
                    rootView = inflater.inflate(R.layout.cpu_layout, container, false);
                    break;
                case 2:
                    rootView = inflater.inflate(R.layout.settings_layout, container, false);
                    final EditText textEdit = (EditText) rootView.findViewById(R.id.edit_nikname);
                    textEdit.setText(nikname);
                    Button button = (Button)rootView.findViewById(R.id.change_nikname);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                          String newNikname = textEdit.getText().toString();
                            myUser.nikname = newNikname;
                            updateUser();
                            nikname = newNikname;
                            textEdit.setText(nikname);
                        }
                    });
                    break;
                case 3:
                    rootView = inflater.inflate(R.layout.logout_layout, container, false);
                    break;
                default:
                    rootView = inflater.inflate(R.layout.loading_layout, container, false);
                    break;
            }

            return rootView;
        }





    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            // Numero delle pagine
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Nome delle pagine
            switch (position) {
                case 0:
                    return "1 Vs 1";
                case 1:
                    return "1 Vs CPU";
                case 2:
                    return "SETTINGS";
                case 3:
                    return "LOG OUT";
            }
            return null;
        }
    }
}
