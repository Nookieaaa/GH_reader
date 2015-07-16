package com.nookdev.githubreader.Fragments;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.nookdev.githubreader.Adapters.RepoListAdapter;
import com.nookdev.githubreader.Models.Profile;
import com.nookdev.githubreader.Models.Repository;
import com.nookdev.githubreader.R;

import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class RepoListFragment extends SwipeRefreshListFragment {
    View mheaderView;
    private Profile profile;
    private ArrayList<Repository> data;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        profile = getActivity().getIntent().getParcelableExtra(Profile.PROFILE_TAG);
        if (profile!=null){
            data = (ArrayList)profile.repositories;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mheaderView = inflater.inflate(R.layout.repolist_header,null);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView lv = getListView();
        lv.addHeaderView(mheaderView);
        lv.setDivider(getResources().getDrawable(R.drawable.list_divider));
        lv.setPadding(20,0,20,0);
        lv.setDividerHeight(10);
        //lv.setBackgroundDrawable(getResources().getDrawable(R.drawable.listview_borders));

        setListAdapter(new RepoListAdapter(getActivity(),data));

        setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initiateRefresh();
            }
        });

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //inflater.inflate(R.menu.main_menu, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initiateRefresh() {

        new RepoUpdater().execute(profile.username);
    }

    private void onRefreshComplete(List<Repository> result) {

        data.clear();
        data.addAll(result);

        RepoListAdapter adapter = (RepoListAdapter)getListAdapter();
        adapter.notifyDataSetChanged();

        setRefreshing(false);
    }



    public class RepoUpdater extends AsyncTask<String,Void,ArrayList<Repository>>{

        @Override
        protected ArrayList<Repository> doInBackground(String... params) {
            String query = params[0];

            try {
                GitHub gitHub = GitHub.connectAnonymously();
                GHUser user = gitHub.getUser(query);
                ArrayList<Repository> result = Repository.getRepositoriesArray(user.getRepositories());
                return result;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Repository> repositories) {
            super.onPostExecute(repositories);
            onRefreshComplete(repositories);
        }
    }
    }

    //adapter
