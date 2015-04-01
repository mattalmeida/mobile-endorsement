package com.example.jacobvigeveno.mobileendorsementsforjive;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class MainPageActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        Intent intent = getIntent();
        String community_url = intent.getStringExtra(LoginActivity.COMMUNITY_URL_KEY);
        String accToken = intent.getStringExtra(LoginActivity.ACCTOKEN);
        String refToken = intent.getStringExtra(LoginActivity.REFTOKEN);

        SharedPreferences connectionPrefs = getPreferences(0);
        SharedPreferences.Editor editor = connectionPrefs.edit();
        editor.putString("commURL", community_url);
        editor.putString("accessToken", accToken);
        editor.putString("refreshToken", refToken);
        editor.commit();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

public class PeopleSearchAsyncTask extends AsyncTask<Void, Void, PeopleSearchResult> {

    @Override
    protected PeopleSearchResult doInBackground(
            Void... params) {
        EditText userQuery = (EditText) findViewById(R.id.selectUser);

        PeopleSearchResult searchResults = new PeopleSearchResult();

        SharedPreferences settings = getPreferences(MODE_PRIVATE);
        String community_url = settings.getString("commURL", "http://lt-a9-121149.jiveland.com:8080");
        String accToken = settings.getString("accessToken", "");

        if ("".equals(community_url)) {
            Log.e("PeopleSearchAsyncTask", "Community URL is empty");
            community_url = getIntent().getStringExtra(LoginActivity.COMMUNITY_URL_KEY);
        }
        if ("".equals(accToken)) {
            Log.e("PeopleSearchAsyncTask", "No access token!");
            accToken = getIntent().getStringExtra(LoginActivity.ACCTOKEN);
        }

        HttpClient httpClient = new DefaultHttpClient();
        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
        nameValuePairs.add(new BasicNameValuePair("filter", "(" + userQuery + ")"));
        String queryParams = URLEncodedUtils.format(nameValuePairs, "utf-8");
        HttpGet httpGet = new HttpGet(community_url + "/api/core/v3/search/people?" + queryParams);

        httpGet.addHeader("Content-Type", "application/json");
        httpGet.addHeader("Authorization", "Bearer " + accToken);

        HttpResponse response = null;
        InputStream inputStream = null;
        try {
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();

            inputStream = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
            StringBuilder sb = new StringBuilder();

            String line = null;
            String result;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            result = sb.toString();

            JSONObject jObject = new JSONObject(result.replace("/throw.*;/,", "").trim());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return searchResults;
    }

    @Override
    protected void onPostExecute(PeopleSearchResult result) {
        mSearchTask = null;

//            Intent intent = new Intent(this, ShowPeopleResults.class);
        Bundle bundle = new Bundle();
//            bundle.putParcelable("data", searchResults);
//            intent.putExtras(bundle);
//            startActivity(intent);
    }
}

public class PeopleSearchResult {

}
