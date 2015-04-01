package com.example.jacobvigeveno.mobileendorsementsforjive;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class CommunityChooser extends ActionBarActivity {

    public final static String COMMUNITY_URL_KEY = "com.example.jacobvigeveno.mobileendorsementsforjive.COMMUNITYURL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_chooser);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_community_chooser, menu);
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

    // Called when clicking the "choose community" button
    public void chooseCommunity(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        EditText editText = (EditText) findViewById(R.id.choose_community);
        String communityurl = editText.getText().toString();
        intent.putExtra(COMMUNITY_URL_KEY, communityurl);
        startActivity(intent);
    }
}
