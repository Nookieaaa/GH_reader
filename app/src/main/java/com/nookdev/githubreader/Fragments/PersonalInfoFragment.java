package com.nookdev.githubreader.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nookdev.githubreader.Database.Database;
import com.nookdev.githubreader.R;
import com.nookdev.githubreader.Utils.ImageLoader;
import com.nookdev.githubreader.Models.Profile;

import org.kohsuke.github.GitHub;

import java.util.List;


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
        browseButton = (ImageButton)rootView.findViewById(R.id.circle_button_browse);
        shareButton = (ImageButton)rootView.findViewById(R.id.circle_button_share);
        saveButton = (ImageButton)rootView.findViewById(R.id.circle_button_save);
        usernaneCompany = (TextView)rootView.findViewById(R.id.circle_text_username_company);

        profile = getActivity().getIntent().getParcelableExtra(Profile.PROFILE_TAG);
        following.setText(String.format(getString(R.string.details_following),String.valueOf(profile.following)));
        followers.setText(String.format(getString(R.string.details_followers), String.valueOf(profile.followers)));
        usernaneCompany.setText(String.format(getString(R.string.details_username_company), profile.username, profile.company));


        View.OnClickListener buttonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //заплатка вместо работы с api /// восстанавливаю
                PackageManager pm = getActivity().getPackageManager();

                switch (v.getId()){
                    case R.id.circle_button_save:{
                        Database db= new Database(getActivity());
                        int result = db.add(profile);
                        String message;
                        switch (result){
                            case Database.DB_STATUS_ERROR:{
                                message = getString(R.string.db_message_error);
                                break;
                            }
                            case Database.DB_STATUS_ADDED:{
                                message = getString(R.string.db_message_added);
                                break;
                            }
                            case Database.DB_STATUS_UPDATED:{
                                message = getString(R.string.db_message_updated);
                                break;
                            }
                            default:
                                message="";
                        }

                        if(message.length()!=0)
                            Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();

                    }
                    break;
                    case R.id.circle_button_share:{
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        final Resources resources = getResources();
                        builder.setTitle(resources.getString(R.string.share_title));

                        final CharSequence[] items = {
                                resources.getString(R.string.google_plus_share),
                                resources.getString(R.string.facebook_share),
                                resources.getString(R.string.twitter_share)};
                        builder.setItems(items, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {
                                switch(item){
                                    case 0:{
                                        //google

                                        break;
                                    }
                                    case 1:{
                                        //fb
                                        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                                        String shareBody = " Check out this guy: "+profile.html_adress;
                                        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                                        PackageManager pm = getActivity().getPackageManager();
                                        List<ResolveInfo> activityList = pm.queryIntentActivities(sharingIntent, 0);
                                        for(final ResolveInfo app : activityList) {
                                            if("com.facebook.katana.ShareLinkActivity".equals(app.activityInfo.name)) {
                                                startActivity(Intent.createChooser(sharingIntent, "Share idea"));
                                                break;
                                            } else {
                                                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, resources.getString(R.string.app_name));
                                                startActivity(Intent.createChooser(sharingIntent, resources.getString(R.string.share_title)));
                                                break;
                                            }
                                        }
                                        break;
                                    }
                                    case 2:{
                                        //twitter

                                        break;
                                    }
                                }
                            }
                        }).show();

                        break;
                    }
                    case R.id.circle_button_browse:{
                        Intent browseIntent = new Intent(Intent.ACTION_VIEW,
                                profile.html_adress);
                        ComponentName componentName = browseIntent.resolveActivity(pm);
                        if (componentName != null) {
                            getActivity().startActivity(browseIntent);
                        }
                    }
                }
            }
        };

        shareButton.setOnClickListener(buttonListener);
        saveButton.setOnClickListener(buttonListener);
        browseButton.setOnClickListener(buttonListener);

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
