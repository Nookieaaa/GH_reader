package com.nookdev.githubreader.Activities;


import android.media.Image;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.nookdev.githubreader.Fragments.PersonalInfoFragment;
import com.nookdev.githubreader.Fragments.RepoListFragment;
import com.nookdev.githubreader.Models.Profile;
import com.nookdev.githubreader.R;
import com.nookdev.githubreader.Views.CircleLayout;


public class DetailsActivity extends AppCompatActivity implements PersonalInfoFragment.PersonalInfoFragmentCallbacks,CircleLayout.CircleCallbacks{
    private Profile profile;
    private PersonalInfoFragment personalInfoFragment;
    private RepoListFragment repoListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.ab_icon);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);



        personalInfoFragment = new PersonalInfoFragment();
        repoListFragment = new RepoListFragment();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.personalinfo_fragment,personalInfoFragment);
        ft.replace(R.id.repolist_fragment,repoListFragment);
        ft.commit();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
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

    @Override
    public void personalInfoFragmentCallbackAction(String action) {

    }

    @Override
    public void changeAvatarParams(int radius) {
        ImageView iv = (ImageView)findViewById(R.id.avatar);
        iv.getLayoutParams().height = (int)(radius*3.5);
        iv.getLayoutParams().width = (int)(radius*3.5);
        iv.requestLayout();
    }
}
