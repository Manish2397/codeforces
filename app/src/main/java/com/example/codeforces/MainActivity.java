package com.example.codeforces;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private Button parse;
    private EditText user;
    private TextView details;
    private RequestQueue mQueue;
    private ImageView profileImg;
    Context cont;
    String firstname = "manish ",imageUrl;
    private static final String TAG = "MyActivity";
    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        parse = findViewById(R.id.parse);
        user = findViewById(R.id.username);
        details = findViewById(R.id.details);
        profileImg = findViewById(R.id.profile);

        mQueue = Volley.newRequestQueue(this);
        parse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parseDetails();
            }
        });
    }

    public void parseDetails() {
        String url = "https://codeforces.com/api/user.info?handles=";
        url = url + user.getText();
        JsonObjectRequest request;
        request = new JsonObjectRequest(Request.Method.GET,url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response){

                        JSONArray jsonArray=null ;
                        try {
                            jsonArray = response.getJSONArray("result");
                        } catch (JSONException e) {
                            //Log.i(TAG, "onResponse: Array ");
                            e.printStackTrace();
                        }
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = jsonArray.getJSONObject(i);
                        } catch (JSONException e) {
                            //Log.i(TAG, "onResponse: object ");
                            e.printStackTrace();
                        }

                        try {
                            firstname = jsonObject.getString("firstName");
                            String lastname =jsonObject.getString("lastName");
                            String country =jsonObject.getString("country");
                            //String city = jsonObject.getString("city");
                            int rating = jsonObject.getInt("rating");
                            int maxRating = jsonObject.getInt("maxRating");
                            String rank = jsonObject.getString("rank");
                            String maxRank = jsonObject.getString("maxRank");
                            String organisation = jsonObject.getString("organization");
                            imageUrl = jsonObject.getString("titlePhoto");
                            details.setText("Name : "+firstname+" "+lastname + "\n");
                            details.append("Rating : " + rating + "\n");
                            details.append("Max Rating : " + maxRating + "\n");
                            details.append("Rank : " + rank + "\n");
                            details.append("Max Ranking : " + maxRank + "\n" );
                            details.append("Country : " + country + "\n");

                           // details.append("City : " + city + "\n");

                        } catch (JSONException e) {
                            //Log.i(TAG, "onResponse: Array name ");
                            e.printStackTrace();
                        }



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // Log.i(TAG, "onErrorResponse: error");
                error.printStackTrace();
            }
        });
        //Picasso.with(getApplicationContext()).load(imageUrl).fit().centerCrop().into(profileImg);
        mQueue.add(request);

    }
}
