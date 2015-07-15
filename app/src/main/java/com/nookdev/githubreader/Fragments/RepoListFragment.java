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

import com.nookdev.githubreader.Adapters.RepoListAdapter;
import com.nookdev.githubreader.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class RepoListFragment extends SwipeRefreshListFragment {
    View mheaderView;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
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

        setListAdapter(new RepoListAdapter(getActivity(),null));

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

        new DummyBackgroundTask().execute();
    }

    private void onRefreshComplete(List<String> result) {

        /*ArrayAdapter<String> adapter = (ArrayAdapter<String>) getListAdapter();
        adapter.clear();
        for (String cheese : result) {
            adapter.add(cheese);
        }*/


        setRefreshing(false);
    }



    private class DummyBackgroundTask extends AsyncTask<Void, Void, List<String>> {

        static final int TASK_DURATION = 3 * 1000; // 3 seconds

        @Override
        protected List<String> doInBackground(Void... params) {
            try {
                Thread.sleep(TASK_DURATION);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String[] s = {"1","2","3","4","5","6","7","8","9","10"};

            return Arrays.asList(s);
        }

        @Override
        protected void onPostExecute(List<String> result) {
            super.onPostExecute(result);

            onRefreshComplete(result);
        }

    }

    //adapter




}
