package com.nookdev.githubreader.Adapters;

import android.content.Context;
import android.content.ContextWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.nookdev.githubreader.R;

import org.kohsuke.github.GHSearchBuilder;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;


public class AutoCompleteAdapter extends BaseAdapter implements Filterable{

    private static final int MAX_RESULTS = 10;
    private Context mContext;
    private List<String> mResults;
    private GitHub gitHubConnetcion;


    public AutoCompleteAdapter(Context context) {
        mContext = context;
        mResults = new ArrayList<String>();
    }

    @Override
    public int getCount() {
        return mResults.size();
    }

    @Override
    public String getItem(int position) {
        return mResults.get(position);
    }

    public void setContext(Context context){
        mContext = context;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        }
        String login = getItem(position);
        ((TextView) convertView.findViewById(android.R.id.text1)).setText(login);
        //((TextView) convertView.findViewById(R.id.text2)).setText(book.getAuthor());

        return convertView;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                if (constraint != null) {
                    List<String> logins = null;
                    try {
                        logins = findLogin(constraint.toString());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    // Assign the data to the FilterResults
                    if (logins==null) {
                        filterResults.values = new ArrayList<String>();
                        filterResults.count = 0;
                    }
                    else{
                        filterResults.values = logins;
                        filterResults.count = logins.size();
                    }

                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    mResults = (List<String>) results.values;
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }};

        return filter;
    }

    /**
     * Returns a search result for the given book title.
     */
    private List<String> findLogin(String query) throws FileNotFoundException{

        try {
            if (gitHubConnetcion == null) {
                gitHubConnetcion = GitHub.connectAnonymously();
            }
            GHSearchBuilder<GHUser> ghUserGHSearchBuilder = gitHubConnetcion.searchUsers().q(query).in("login");

            if (ghUserGHSearchBuilder.list().getTotalCount()==1 &&
                    ghUserGHSearchBuilder.list().asSet().contains(query)){
                return null;
            }

            AbstractList ghUserList = (AbstractList) ghUserGHSearchBuilder.list()
                    .asList()
                    .subList(
                            0,
                            Math.min(
                                    MAX_RESULTS,
                                    ghUserGHSearchBuilder.list().getTotalCount()));

            return makeStringListFromGHUserList(ghUserList);

        }
         catch (IOException e){
            e.printStackTrace();
            String message = e.getMessage();
        } catch (Error error){
            error.printStackTrace();
            String message = error.getMessage();
            if (message.contains("API rate limit")){
                Toast.makeText(mContext,mContext.getString(R.string.api_rate_limit_error),Toast.LENGTH_SHORT).show();
                //show toast
                //disable search
            }

        }

        return null;
    }

    private List<String> makeStringListFromGHUserList(AbstractList<GHUser> ghUserList) {
        List<String> result= new ArrayList<String>(ghUserList.size());
        for (GHUser user : ghUserList){
            result.add(user.getLogin());
        }
        return result;
    }
}


/*
public class AutoCompleteAdapter extends ArrayAdapter<String> {
    private Filter mFilter;
    private ArrayList<String> mSubData = new ArrayList<String>();
    static int counter =0;

    public AutoCompleteAdapter(final Context context, int resource) {
        super(context, resource);
        mFilter = new Filter() {
            private int c = ++counter;
            private ArrayList<String> mData = new ArrayList<String>();

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                mData.clear();
                FilterResults filterResults = new FilterResults();

                if(constraint != null) {
                    try {

                        Thread t = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    GitHub gitHub = GitHub.connectAnonymously();

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                           */
/* // Here is the method (synchronous) that fetches the data
                            // from the server
                            URL url = new URL("...");
                            URLConnection conn = url.openConnection();
                            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                            String line = "";

                            while ((line = rd.readLine()) != null) {
                                mData.add(new MyObject(line));
                            }*//*


                    }
                    catch(Exception e) {
                        e.printStackTrace();
                    }

                    filterResults.values = mData;
                    filterResults.count = mData.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if(c == counter) {
                    mSubData.clear();
                    if(results != null && results.count > 0) {
                        ArrayList<String> objects = (ArrayList<String>)results.values;
                        for (String v : objects)
                            mSubData.add(v);

                        notifyDataSetChanged();
                    }
                    else {
                        notifyDataSetInvalidated();
                    }
                }
            }
        };
    }

    @Override
    public int getCount() {
        return mSubData.size();
    }

    @Override
    public String getItem(int index) {
        return mSubData.get(index);
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

}*/
