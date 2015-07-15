package com.nookdev.githubreader.Models;

public class Repository {

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

    public String name;
    public int forks;
    public int stars;
    public String language;
    public boolean forked;
    public boolean starred;

    public Repository(String _name, String _language, int _forks, int _stars) {
        this.name = _name;
        this.language = _language;
        this.forks = _forks;
        this.stars = _stars;
    }

    public Repository(String _name, String _language, int _forks, int _stars, boolean _forked,boolean _starred) {
        this.name = _name;
        this.language = _language;
        this.forks = _forks;
        this.stars = _stars;
        this.starred = _starred;
        this.forked = _forked;
    }

    public String parcelize(){
        StringBuilder sb = new StringBuilder();
        return sb.append(addSplitters(PARCEL_REPO_START_TAG)).
                append(PARCEL_REPO_NAME_TAG).append(PARCEL_REPO_SPLITTER).append(this.name)
                .append(addSplitters(PARCEL_REPO_LANGUAGE_TAG)).append(this.language)
                .append(addSplitters(PARCEL_REPO_FORK_TAG)).append(Integer.toString(this.forks))
                .append(addSplitters(PARCEL_REPO_STARS_TAG)).append(Integer.toString(this.stars))
                .toString();
    }

    private String addSplitters(String param){

        return PARCEL_REPO_SPLITTER+param+PARCEL_REPO_SPLITTER;
    }

}