package com.nookdev.githubreader.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;


import com.nookdev.githubreader.Activities.DetailsActivity;
import com.nookdev.githubreader.Activities.MainActivity;
import com.nookdev.githubreader.Database.Database;
import com.nookdev.githubreader.Models.Profile;
import com.nookdev.githubreader.Models.Repository;

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
        void onPostExecute(Intent intent);
    }

    public interface RepoTaskCallbacks{
        void onPostExecute(ArrayList data);
    }

    private TaskCallbacks mainActivityCallbacks;
    private TaskCallbacks detailsActivityCallbacks;
    private FetchProfileTask fetchProfileTask;


    public void searchForUser(String username){
        fetchProfileTask = new FetchProfileTask();
        fetchProfileTask.execute(username);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity.getClass() == MainActivity.class) {
            mainActivityCallbacks = (TaskCallbacks) activity;
        }
        else
            detailsActivityCallbacks = (TaskCallbacks) activity;

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
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
        mainActivityCallbacks = null;
        detailsActivityCallbacks = null;
    }


    public class FetchProfileTask extends AsyncTask<String,Integer,Intent>{
        @Override
        public void onPreExecute() {
            super.onPreExecute();
            if (mainActivityCallbacks != null) {
                mainActivityCallbacks.onPreExecute();
            }

        }

        @Override
        protected Intent doInBackground(String... params) {
            String query = params[0];
            try {
                //TODO добавить авторизацию и убрать свой логин

                GitHub gitHub = GitHub.connectAnonymously();


                try {
                    GHUser ghUser = gitHub.getUser(query);
                    Profile profile = new Profile(ghUser);

                    Intent findingIntent = new Intent(getActivity(), DetailsActivity.class);
                    findingIntent.putExtra(Profile.PROFILE_TAG,profile);

                    return findingIntent;
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
            if (mainActivityCallbacks != null) {
                mainActivityCallbacks.onProgressUpdate(values[0]);
            }
        }

        @Override
        public void onCancelled() {
            if (mainActivityCallbacks != null) {
                mainActivityCallbacks.onCancelled();
            }
        }

        @Override
        public void onPostExecute(Intent intent) {
            super.onPostExecute(intent);
            if (mainActivityCallbacks != null) {
                mainActivityCallbacks.onPostExecute(intent);
            }
        }


        }

    public class LoadTask extends AsyncTask<String,Integer,Profile>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (detailsActivityCallbacks != null) {
                detailsActivityCallbacks.onPreExecute();
            }
        }

        @Override
        protected void onPostExecute(Profile profile) {
            super.onPostExecute(profile);
            if (detailsActivityCallbacks != null) {
                //detailsActivityCallbacks.onPostExecute(profile);
            }
        }

        @Override
        protected void onProgressUpdate(Integer... i) {
            super.onProgressUpdate(i[0]);
            if (detailsActivityCallbacks != null) {
                detailsActivityCallbacks.onProgressUpdate(i[0]);
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected Profile doInBackground(String... params) {

            String query = params[0];

            return null;

        }
    }


    public class RepoUpdater extends AsyncTask<String,Void,ArrayList<Repository>>{

        @Override
        protected ArrayList<Repository> doInBackground(String... params) {
            String query = params[0];

            try {
                GitHub gitHub = GitHub.connectAnonymously();
                GHUser user = gitHub.getUser(query);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Repository> repositories) {
            super.onPostExecute(repositories);

        }
    }

    }


