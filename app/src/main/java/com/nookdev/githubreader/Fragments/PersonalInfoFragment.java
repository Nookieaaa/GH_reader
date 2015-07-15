package com.nookdev.githubreader.Fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nookdev.githubreader.R;
import com.nookdev.githubreader.Utils.ImageLoader;
import com.nookdev.githubreader.Models.Profile;
import com.nookdev.githubreader.Views.CircleLayout;


public class PersonalInfoFragment extends Fragment {

    private TextView usernaneCompany;
    private TextView followers;
    private TextView following;
    private ImageButton browseButton;
    private ImageButton shareButton;
    private ImageButton saveButton;
    private Profile profile;



    public PersonalInfoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);

        following = (TextView)rootView.findViewById(R.id.circle_text_following);
        followers = (TextView)rootView.findViewById(R.id.circle_text_followers);
        usernaneCompany = (TextView)rootView.findViewById(R.id.circle_text_username_company);


        profile = getActivity().getIntent().getParcelableExtra(Profile.PROFILE_TAG);

        following.setText(String.format(getString(R.string.details_following),String.valueOf(profile.following)));
        followers.setText(String.format(getString(R.string.details_followers),String.valueOf(profile.followers)));
        usernaneCompany.setText(String.format(getString(R.string.details_username_company),profile.username,profile.company));


        int loader = R.drawable.notification_template_icon_bg;

        // Imageview to show
        ImageView image = (ImageView) rootView.findViewById(R.id.avatar);

        // Image url
        String image_url = profile.avatarURI.toString();

        // ImageLoader class instance
        ImageLoader imgLoader = new ImageLoader(getActivity().getApplicationContext());


        imgLoader.DisplayImage(image_url, loader, image);

        return rootView;
    }

    public Profile getProfile(){
        return profile;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
