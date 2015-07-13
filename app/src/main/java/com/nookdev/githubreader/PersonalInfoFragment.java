package com.nookdev.githubreader;

import android.app.Activity;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A placeholder fragment containing a simple view.
 */
public class PersonalInfoFragment extends Fragment {

    PersonalInfoFragmentCallbacks mCallbacks;
    private TextView login;
    private TextView company;
    private TextView followers;
    private TextView followed;
    private TextView avatarUri;

    public interface PersonalInfoFragmentCallbacks{
        void personalInfoFragmentCallbackAction(String action);
    }

    public PersonalInfoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);
        login = (TextView)rootView.findViewById(R.id.pinfo_username);
        company = (TextView)rootView.findViewById(R.id.pinfo_company);
        followed = (TextView)rootView.findViewById(R.id.pinfo_followed);
        followers = (TextView)rootView.findViewById(R.id.pinfo_followers);
        avatarUri = (TextView)rootView.findViewById(R.id.pinfo_avatar);

        Profile profile = getActivity().getIntent().getParcelableExtra(Profile.PROFILE_TAG);

        if (profile!=null) {
            login.setText("login:" + profile.username);
            company.setText("company:"+profile.company);
            followed.setText("following:"+String.valueOf(profile.following));
            followers.setText("followers:"+String.valueOf(profile.followers));
            avatarUri.setText("avatar:"+profile.avatarURI.toString());
        }

        return rootView;
    }

    //interface callback
    public void personalInfoFragmentCallbackAction(String action){
        mCallbacks.personalInfoFragmentCallbackAction("some action");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (mCallbacks!=null) {
            try {
                mCallbacks = (PersonalInfoFragmentCallbacks) activity;
            } catch (ClassCastException e) {
                throw new ClassCastException(activity.toString()
                        + " must implement PersonalInfoFragmentCallbacks");
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }
}
