package com.nookdev.githubreader.Activities;


import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nookdev.githubreader.Adapters.AutoCompleteAdapter;
import com.nookdev.githubreader.Fragments.RetainFragment;
import com.nookdev.githubreader.Models.Profile;
import com.nookdev.githubreader.R;
import com.nookdev.githubreader.Views.DelayAutoCompleteTextView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener,RetainFragment.TaskCallbacks {

    private DelayAutoCompleteTextView searchField;
    private boolean hideActionButtons = true;
    private ProgressDialog progressDialog;

    private static final String TAG_TASK_FRAGMENT = "task_fragment";
    private RetainFragment mRetainFragment;
    private AutoCompleteAdapter autoCompleteAdapter;
    private static final int AUTOCOMPLITE_THRESHOLD = 4;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setting up action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.drawable.ab_icon);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        Button searchButton = (Button)findViewById(R.id.search_btn);
        searchButton.setOnClickListener(this);

        FragmentManager fm = getFragmentManager();
        mRetainFragment = (RetainFragment)fm.findFragmentByTag(TAG_TASK_FRAGMENT);


        if (mRetainFragment == null) {
            mRetainFragment = new RetainFragment();
            fm.beginTransaction().add(mRetainFragment, TAG_TASK_FRAGMENT).commit();
        }
        else if (mRetainFragment !=null)
            if (mRetainFragment.isExecuting())
                showProgressDialog();

        searchField = (DelayAutoCompleteTextView)findViewById(R.id.username);
        searchField.setThreshold(AUTOCOMPLITE_THRESHOLD);
        searchField.setAdapter(new AutoCompleteAdapter(this));
        searchField.setLoadingIndicator((ProgressBar) findViewById(R.id.progress_bar));
        searchField.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String login = (String) adapterView.getItemAtPosition(position);
                searchField.setText(login);
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_btn:{

                //TODO влепить валидацию юзернейма перед передачей в интент

                String username = searchField.getText().toString();
                mRetainFragment.searchForUser(username);
                searchField.stopWhatYoureDoing();

                break;
            }
        }
    }


    //прячем кнопки меню, т.к. они пока не нужны
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        menu.findItem(R.id.action_settings).setVisible(!hideActionButtons);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        dismissProgressDialog();
        searchField.stopWhatYoureDoing();
    }

    public void showProgressDialog(){
        if (progressDialog!=null) {
            progressDialog.show();
            return;
        }

        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage(getString(R.string.progress_dialog_message));
        progressDialog.setTitle(getString(R.string.progress_dialog_wait_message));
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                cancelTask();
            }
        });
        progressDialog.show();
    }

    private void cancelTask(){
        dismissProgressDialog();
        mRetainFragment.cancelTask();
    }

    private void dismissProgressDialog(){
        if (progressDialog==null)
            return;
        if(progressDialog.isShowing())
            progressDialog.dismiss();

    }

    @Override
    public void onPreExecute() {
        showProgressDialog();
    }

    @Override
    public void onProgressUpdate(int value) {

        Toast.makeText(this,Integer.toString(value),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancelled() {
        Toast.makeText(this,"Отмена",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPostExecute(Profile profile) {
        Toast.makeText(this,"Готово",Toast.LENGTH_SHORT).show();
        dismissProgressDialog();

        Intent intent = new Intent(this,DetailsActivity.class);
        intent.putExtra(Profile.PROFILE_TAG,profile);
        startActivity(intent);
    }


}
