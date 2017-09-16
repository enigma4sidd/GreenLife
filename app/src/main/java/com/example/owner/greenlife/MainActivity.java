package com.example.owner.greenlife;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.owner.greenlife.R.id.overallScore;

public class MainActivity extends AppCompatActivity {

    Intent i;
    CallbackManager callbackManager;
    AccessTokenTracker accessTokenTracker;
    ProfileTracker profileTracker;
    AccessToken accessToken;
    private TextView overallButton, toButton, monthButton, tipBoxButton;
    ImageButton im1, im2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_header);
        FacebookSdk.sdkInitialize(getApplicationContext());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        overallButton = (TextView) findViewById(overallScore);
        ImageButton im1=(ImageButton) findViewById(R.id.im1);
        ImageButton im2=(ImageButton) findViewById(R.id.im2);
        TextView overallScore = (TextView) findViewById(R.id.overallScore);



        im1.setOnClickListener(new View.OnClickListener()   {
            public void onClick(View view)  {
                try {
                    Log.d("logd", "onclick");
                    Intent l = new Intent(MainActivity.this, com.example.owner.greenlife.ocrreader.MainActivity.class);
                    startActivityForResult(l,1);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        im2.setOnClickListener(new View.OnClickListener()   {
            public void onClick(View view)  {
                try {

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

      /*  int score, monthlyScoreInt, overallScoreInt;

        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.pref),
                Context.MODE_PRIVATE);
        if(sharedPref.getInt(getString(R.string.saved_monthly_score), -1) != -1)
        {
            monthlyScoreInt = sharedPref.getInt(getString(R.string.saved_monthly_score), -1);
        } else {
            monthlyScoreInt = 0;
        }
*/

/*

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(getString(R.string.saved_overall_score), overallScoreInt);
        editor.commit();
*/


/*

        overallScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ScoreActivity.class);
                startActivity(i);
            }
        });
*/



        FacebookSdk.sdkInitialize(this.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();



        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {
                // Set the access token using
                // currentAccessToken when it's loaded or set.
                accessToken = currentAccessToken;
            }
        };
        // If the access token is available already assign it.
        accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken == null) {
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
        }

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(
                    Profile oldProfile,
                    Profile currentProfile) {
                // App code
            }
        };
        Bundle param = new Bundle();
        param.putInt("score", 100);

        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/"+AccessToken.getCurrentAccessToken().getUserId()+"/scores",
                param,
                HttpMethod.POST,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
            /* handle the result */
                        JSONObject jsonObj = response.getJSONObject();

                        try {
                            Log.d("postrs", jsonObj.getString("success"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        ).executeAsync();

        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/"+AccessToken.getCurrentAccessToken().getUserId()+"/scores",
                param,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
            /* handle the result */

                        JSONObject jsonObj = response.getJSONObject();
                        JSONArray rs = null;
                        try {
                            rs = jsonObj.getJSONArray("data");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("JSON123", rs.toString());
                        for(int i=0; i<rs.length();i++)
                        {
                            try {
                                    overallButton.setText(rs.getJSONObject(i).getString("score"));
                                Log.d("JSON123", rs.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }
        ).executeAsync();


        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/684678025025822/scores",
                param,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
            /* handle the result */

                        JSONObject jsonObj = response.getJSONObject();
                        JSONArray rs = null;
                        try {
                            rs = jsonObj.getJSONArray("data");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("JSON123", rs.toString());
                        for(int i=0; i<rs.length();i++)
                        {
                            try {
                                overallButton.setText(rs.getJSONObject(i).getString("score"));
                                Log.d("JSO1", rs.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }
        ).executeAsync();



       /* *//* make the API call *//*
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/{friend-list-id}",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
            *//* handle the result *//*
                    }
                }
        ).executeAsync();
*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");
                Log.d("result",result);


                Bundle param = new Bundle();
                try {
                    int score= Integer.parseInt(result);
                    score= ((score-500)/10);
                    param.putInt("score", score);
                    Log.d("score","HI: "+score);
                    overallButton.setText(score);
                }
catch (Exception e)
{

}
                new GraphRequest(
                        AccessToken.getCurrentAccessToken(),
                        "/"+AccessToken.getCurrentAccessToken().getUserId()+"/scores",
                        param,
                        HttpMethod.POST,
                        new GraphRequest.Callback() {
                            public void onCompleted(GraphResponse response) {
            /* handle the result */
                                JSONObject jsonObj = response.getJSONObject();

                                try {
                                    Log.d("postrs", jsonObj.getString("success"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                ).executeAsync();

               }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case action_logout:
                LoginManager.getInstance().logOut();
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/
}
