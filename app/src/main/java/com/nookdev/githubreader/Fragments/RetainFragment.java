package com.nookdev.githubreader.Fragments;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;


import com.nookdev.githubreader.Models.Profile;

import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.AbstractList;
import java.util.ArrayList;



public class RetainFragment extends Fragment{


    public interface TaskCallbacks {
        void onPreExecute();
        void onProgressUpdate(int value);
        void onCancelled();
        void onPostExecute(Profile profile);


    }
    private TaskCallbacks mCallbacks;
    private FetchProfileTask fetchProfileTask;


    public void searchForUser(String username){
        fetchProfileTask = new FetchProfileTask();
        fetchProfileTask.execute(username);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCallbacks = (TaskCallbacks) activity;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        //fetchProfileTask.execute();
    }

    public void cancelTask(){
        if (isExecuting()){
            fetchProfileTask.cancel(true);
        }
    }



    public boolean isExecuting(){
        if (fetchProfileTask==null)
            return false;
        return fetchProfileTask.getStatus().equals(AsyncTask.Status.RUNNING);
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }


    public class FetchProfileTask extends AsyncTask<String,Integer,Profile>{



        @Override
        public void onPreExecute() {
            super.onPreExecute();
            if (mCallbacks != null) {
                mCallbacks.onPreExecute();
            }

        }

        @Override
        protected Profile doInBackground(String... params) {
            String query = params[0];
            try {
                //TODO добавить авторизацию и убрать свой логин

                GitHub gitHub=GitHub.connectUsingPassword("nookieaaa","nookie1");
                //GitHub.connectAnonymously();


                try {
                    GHUser ghUser = gitHub.getUser(query);
                    Profile profile = new Profile(ghUser);
                    return profile;
                }
                catch (FileNotFoundException e){

                    return null;
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private ArrayList<String> makeStringListFromGHUserList(AbstractList<GHUser> ghUserList) {
            ArrayList<String> result= new ArrayList<String>(ghUserList.size());
            for (GHUser user : ghUserList){
                result.add(user.getLogin());
            }
            return result;
        }

        @Override
        public void onProgressUpdate(Integer... values) {
            if (mCallbacks != null) {
                mCallbacks.onProgressUpdate(values[0]);
            }
        }

        @Override
        public void onCancelled() {
            if (mCallbacks != null) {
                mCallbacks.onCancelled();
            }
        }

        @Override
        public void onPostExecute(Profile profile) {
            super.onPostExecute(profile);
            if (mCallbacks != null) {
                mCallbacks.onPostExecute(profile);
            }
        }


        }



    }


