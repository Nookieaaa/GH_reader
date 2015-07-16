package com.nookdev.githubreader.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nookdev.githubreader.Fragments.SwipeRefreshListFragment;
import com.nookdev.githubreader.Models.Profile;
import com.nookdev.githubreader.Models.Repository;
import com.nookdev.githubreader.R;

import java.util.ArrayList;

public class RepoListAdapter extends BaseAdapter implements View.OnClickListener {

    /*********** Declare Used Variables *********/
    public Resources res;

    private FragmentActivity listFragment;
    private ArrayList data;
    private static LayoutInflater inflater;
    private ListView listView;
    Repository repository;
    int i=0;

    /*************  CustomAdapter Constructor *****************/
    public RepoListAdapter(FragmentActivity _context, ArrayList _data) {

        /********** Take passed values **********/
        listFragment = _context;
        if (_data==null) {
            _data = new ArrayList<Repository>();

        }
        data=_data;
        //res = resLocal;

        /***********  Layout inflator to call external xml layout () ***********/
        inflater = ( LayoutInflater )listFragment.getApplicationContext().
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    /******** What is the size of Passed Arraylist Size ************/
    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return data.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    /********* Create a holder Class to contain inflated xml file elements *********/
    public static class ViewHolder{

        public TextView name;
        public TextView language;
        public TextView stars_count;
        public TextView forks_count;
        public ImageView forks_image;
        public ImageView stars_image;

    }

    /****** Depends upon data size called for each row , Create each ListView row *****/
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        ViewHolder holder;

        if(convertView==null){

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            v = inflater.inflate(R.layout.repo_list_item, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.name = (TextView) v.findViewById(R.id.repolist_row_infoblock_name);
            holder.language=(TextView)v.findViewById(R.id.repolist_row_infoblock_language);
            holder.forks_count=(TextView)v.findViewById(R.id.repolist_row_starsection_forks);
            holder.stars_count=(TextView)v.findViewById(R.id.repolist_row_starsection_stars);
            holder.forks_image = (ImageView)v.findViewById(R.id.repolist_row_starsection_forkimage);
            holder.stars_image = (ImageView)v.findViewById(R.id.repolist_row_starsection_starimage);
            //holder.image=(ImageView)v.findViewById(R.id.image);

            /************  Set holder with LayoutInflater ************/

        }
        else
            holder=(ViewHolder)v.getTag();

        if(data.size()<=0)
        {


        }
        else
        {
            /***** Get each Model object from Arraylist ********/
            repository = ( Repository ) data.get( position );

            /************  Set Model values in Holder elements ***********/



            holder.name.setText(repository.name );
            holder.language.setText(String.format(listFragment.getApplicationContext().getString(R.string.repository_list_language), repository.language));
            holder.stars_count.setText(String.valueOf(repository.stars));
            holder.forks_count.setText(String.valueOf(repository.forks));
            holder.stars_image.setOnClickListener(this);
            holder.forks_image.setOnClickListener(this);

        }

        v.setTag( holder );

        return v;
    }

    //dummy actions :(
    @Override
    public void onClick(View v) {
        if (listFragment!=null){
            res = listFragment.getResources();
        }
        View parentView = (View) v.getParent(); //getting section
        ListView listView = (ListView) parentView.getParent().getParent();//getting row
        final int position = listView.getPositionForView(parentView);
        ImageView iv = (ImageView)v;
        switch (v.getId()){
            case R.id.repolist_row_starsection_starimage:{
                TextView stars = (TextView)parentView.findViewById(R.id.repolist_row_starsection_stars);
                int curValue = Integer.parseInt(stars.getText().toString());
                if (iv.getDrawable().getConstantState() == res.getDrawable(android.R.drawable.star_big_off).getConstantState())
                {
                    iv.setImageResource(android.R.drawable.star_big_on);
                    stars.setText(String.valueOf(curValue + 1));
                }
                else{
                    iv.setImageResource(android.R.drawable.star_big_off);
                    stars.setText(String.valueOf(curValue - 1));
                }

                break;
            }
            case R.id.repolist_row_starsection_forkimage:{
                TextView forks = (TextView)parentView.findViewById(R.id.repolist_row_starsection_forks);
                int curValue = Integer.parseInt(forks.getText().toString());
                if (iv.getDrawable().getConstantState() == res.getDrawable(R.drawable.fork).getConstantState())
                {
                    iv.setImageResource(R.drawable.forked);
                    forks.setText(String.valueOf(curValue + 1));
                }
                else{
                    iv.setImageResource(R.drawable.fork);
                    forks.setText(String.valueOf(curValue - 1));
                }
                break;
            }
        }


    }



    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            View parentRow = (View) view.getParent();
            ListView listView = (ListView) parentRow.getParent();
            //final int position = listView.getPositionForView(parentRow);


        }

    };


    /********* Called when Item click in ListView ************//*
    private class OnItemClickListener  implements View.OnClickListener {
        private int mPosition;

        OnItemClickListener(int position){
            mPosition = position;
        }

        @Override
        public void onClick(View arg0) {


            int i=0;

            *//****  Call  onItemClick Method inside CustomListViewAndroidExample Class ( See Below )****//*

           // sct.onItemClick(mPosition);
        }
    }*/
}