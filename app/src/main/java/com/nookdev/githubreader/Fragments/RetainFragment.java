package com.nookdev.githubreader.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.nookdev.githubreader.Activities.DetailsActivity;
import com.nookdev.githubreader.Activities.MainActivity;
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
        void onPostExecute(Intent intent);
    }

    public interface RepoTaskCallbacks{
        void onPostExecute(ArrayList data);
    }

    private TaskCallbacks mainActivityCallbacks;
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

                //GitHub gitHub = GitHub.connectAnonymously();
                GitHub gitHub = GitHub.connectUsingPassword("nookieaaa","nookie1");

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

        private ArrayList makeStringListFromGHUserList(AbstractList<GHUser> ghUserList) {
            ArrayList<String> result= new ArrayList(ghUserList.size());
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

        }

        @Override
        protected void onPostExecute(Profile profile) {
            super.onPostExecute(profile);

        }

        @Override
        protected void onProgressUpdate(Integer... i) {
            super.onProgressUpdate(i[0]);

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




    }


