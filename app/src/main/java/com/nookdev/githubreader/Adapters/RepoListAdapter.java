package com.nookdev.githubreader.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nookdev.githubreader.Fragments.SwipeRefreshListFragment;
import com.nookdev.githubreader.Models.Profile;
import com.nookdev.githubreader.Models.Repository;
import com.nookdev.githubreader.R;

import java.util.ArrayList;

public class RepoListAdapter extends BaseAdapter implements View.OnClickListener {

    /*********** Declare Used Variables *********/
    private FragmentActivity listFragment;
    private ArrayList data;
    private static LayoutInflater inflater;
    public Resources res;
    Repository repository;
    int i=0;

    /*************  CustomAdapter Constructor *****************/
    public RepoListAdapter(FragmentActivity _context, ArrayList _data) {

        /********** Take passed values **********/
        listFragment = _context;
        if (_data==null) {
            _data = new ArrayList();
            _data.add(new Repository("repo","Java",0,0));
            _data.add(new Repository("repo1","C++",1,3));
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
        public ImageView image;

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
            //holder.image=(ImageView)v.findViewById(R.id.image);

            /************  Set holder with LayoutInflater ************/
            v.setTag( holder );
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

            holder.name.setText( repository.name );
            holder.language.setText(String.format(listFragment.getApplicationContext().getString(R.string.repository_list_language), repository.language));
            holder.stars_count.setText(String.valueOf(repository.stars));
            holder.forks_count.setText(String.valueOf(repository.forks));



            /*holder.image.setImageResource(
                    res.getIdentifier(
                            "com.androidexample.customlistview:drawable/"+tempValues.getImage()
                            ,null,null));*/

            /******** Set Item Click Listner for LayoutInflater for each row *******/

            v.setOnClickListener(new OnItemClickListener( position ));
        }
        return v;
    }

    @Override
    public void onClick(View v) {
        Log.v("CustomAdapter", "=====Row button clicked=====");
    }

    /********* Called when Item click in ListView ************/
    private class OnItemClickListener  implements View.OnClickListener {
        private int mPosition;

        OnItemClickListener(int position){
            mPosition = position;
        }

        @Override
        public void onClick(View arg0) {


            FragmentActivity sct = listFragment;

            /****  Call  onItemClick Method inside CustomListViewAndroidExample Class ( See Below )****/

           // sct.onItemClick(mPosition);
        }
    }
}