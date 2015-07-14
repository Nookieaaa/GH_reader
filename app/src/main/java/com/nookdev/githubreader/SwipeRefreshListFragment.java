package com.nookdev.githubreader;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class SwipeRefreshListFragment extends ListFragment {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    public ArrayList<Profile> CustomListViewValuesArr = new ArrayList<Profile>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View listFragmentView = super.onCreateView(inflater, container, savedInstanceState);

        mSwipeRefreshLayout = new ListFragmentSwipeRefreshLayout(container.getContext());
        mSwipeRefreshLayout.addView(listFragmentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);


        mSwipeRefreshLayout.setLayoutParams(
                new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));


        return mSwipeRefreshLayout;
    }


    public void setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener listener) {
        mSwipeRefreshLayout.setOnRefreshListener(listener);
    }


    public boolean isRefreshing() {
        return mSwipeRefreshLayout.isRefreshing();
    }


    public void setRefreshing(boolean refreshing) {
        mSwipeRefreshLayout.setRefreshing(refreshing);
    }




    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return mSwipeRefreshLayout;
    }

    public void onItemClick(int mPosition) {
        Profile profile = CustomListViewValuesArr.get(mPosition);
    }

    private class ListFragmentSwipeRefreshLayout extends SwipeRefreshLayout {

        public ListFragmentSwipeRefreshLayout(Context context) {
            super(context);
        }
        
        @Override
        public boolean canChildScrollUp() {
            final ListView listView = getListView();
            if (listView.getVisibility() == View.VISIBLE) {
                return canListViewScrollUp(listView);
            } else {
                return false;
            }
        }

        /****** Function to set data in ArrayList *************/
        public void setListData()
        {

            for (int i = 0; i < 11; i++) {

//                final Profile sched = new Profile();
//
//                /******* Firstly take data in model object ******/
//                sched.setCompanyName("Company "+i);
//                sched.setImage("image"+i);
//                sched.setUrl("http:\\www."+i+".com");
//
//                /******** Take Model Object in ArrayList **********/
//                CustomListViewValuesArr.add( sched );
            }

        }

    }


    private static boolean canListViewScrollUp(ListView listView) {
        if (android.os.Build.VERSION.SDK_INT >= 14) {

            return ViewCompat.canScrollVertically(listView, -1);
        } else {

            return listView.getChildCount() > 0 &&
                    (listView.getFirstVisiblePosition() > 0
                            || listView.getChildAt(0).getTop() < listView.getPaddingTop());
        }
    }

}
