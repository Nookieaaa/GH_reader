package com.nookdev.githubreader.Models;


import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Profile implements Parcelable{
    public static final String PROFILE_TAG="profile";

    public static final String PARCEL_REPO_NAME_TAG = "nm";
    public static final String PARCEL_REPO_FORK_TAG = "fr";
    public static final String PARCEL_REPO_STARS_TAG = "st";
    public static final String PARCEL_REPO_START_TAG = "strt";
    public static final String PARCEL_REPO_LANGUAGE_TAG = "lng";
    public static final String PARCEL_REPO_SPLITTER = ":";
    public static final String[] TAG_LIST = {PARCEL_REPO_NAME_TAG,
            PARCEL_REPO_FORK_TAG,
            PARCEL_REPO_STARS_TAG,
            PARCEL_REPO_START_TAG,
            PARCEL_REPO_LANGUAGE_TAG
    };


    public String username;
    public String company;
    public int followers;
    public int following;
    public Uri avatarURI;
    public Uri html_adress;
    public List<Repository> repositories;

    public static final Parcelable.Creator<Profile> CREATOR = new Parcelable.Creator<Profile>() {

        @Override
        public Profile createFromParcel(Parcel source) {
            return new Profile(source);  //using parcelable constructor
        }

        @Override
        public Profile[] newArray(int size) {
            return new Profile[size];
        }
    };


    public Profile(GHUser _user) throws IOException {

        this.username = _user.getLogin();
        this.company = _user.getCompany();
        this.following = _user.getFollowingCount();
        this.followers = _user.getFollowersCount();
        this.avatarURI = Uri.parse(_user.getAvatarUrl().toString());
        this.html_adress = Uri.parse(_user.getHtmlUrl().toString());
        repositories = new ArrayList<Repository>();
        Map<String,GHRepository> repositoryMap = _user.getRepositories();
        for (Map.Entry<String,GHRepository> entry : repositoryMap.entrySet()){
            GHRepository data = entry.getValue();
            repositories.add(
                    new Repository(
                            data.getName(),
                            data.getLanguage(),
                            data.getForks(),
                            data.getWatchers()
                    )
            );
        }
    }

    public Profile(String _name, String _company, int _followers, int _following, String _avatar, String _url){
        this.username = _name;
        this.company = _company;
        this.followers = _followers;
        this.following = _following;

        if (_url!=null){
            this.html_adress = Uri.parse(_url);
        }

        if(_avatar!=null){
            avatarURI = Uri.parse(_avatar);
        }
    }

    public Profile(Parcel parcel) {
       String[] data = new String[7];
        parcel.readStringArray(data);

        this.username = data[0];
        this.company = data[1];
        this.following = Integer.parseInt(data[2]);
        this.followers = Integer.parseInt(data[3]);
        this.avatarURI = Uri.parse(data[4]);
        this.html_adress = Uri.parse(data[5]);

        this.repositories = unpackReposFromParcel(data[6]);
    }




    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(
                new String[]{
                        this.username,
                        this.company,
                        String.valueOf(this.following),
                        String.valueOf(this.followers),
                        this.avatarURI.toString(),
                        this.html_adress.toString(),
                        wrapReposToParcel()
                }
        );

    }

    public String wrapReposToParcel(){
        StringBuilder stringBuilder = new StringBuilder(repositories.size());
       for (Repository rep : repositories){
           stringBuilder.append(rep.parcelize());
       }
        return stringBuilder.toString();
    }

    public List<Repository> unpackReposFromParcel(String parcelledRepos){

        ArrayList<Repository> repoList = new ArrayList<Repository>();
        String[] allRepos = parcelledRepos.split(PARCEL_REPO_SPLITTER+
                PARCEL_REPO_START_TAG+
                PARCEL_REPO_SPLITTER);

        for (String repoStr : allRepos){
            if (repoStr.isEmpty())
                continue;
            String[] data = repoStr.split(PARCEL_REPO_SPLITTER);
                Repository newRep = new Repository(data[1], //repository name
                        data[3], // language
                        Integer.parseInt(data[5]), // forks
                        Integer.parseInt(data[7])); //stars
            repoList.add(newRep);
        }


        return repoList;
    }





}
