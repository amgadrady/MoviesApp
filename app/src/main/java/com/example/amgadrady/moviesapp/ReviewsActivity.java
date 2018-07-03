package com.example.amgadrady.moviesapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ReviewsActivity extends AppCompatActivity {
ArrayList<String> reviews =new ArrayList<String>();
Reviews_Adapter reviews_adapter;
    ListView listView;
    public  void setListViewData()
    {
        listView= (ListView) findViewById(R.id.listview);
          reviews_adapter=new Reviews_Adapter(ReviewsActivity.this , reviews);
        listView.setAdapter(reviews_adapter);


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle("Reviews");


String id = getIntent().getExtras().getString("id");
ReviewsDownload task = new ReviewsDownload();
        task.execute("http://api.themoviedb.org/3/movie/"+id+"/reviews?api_key=666d61b5201fa34bad7884124fa6260d");

    }
    public  class ReviewsDownload extends AsyncTask<String, Void , String >
    {


        @Override
        protected String doInBackground(String... params) {
            Log.i("testing", "inTrailerasynktask");
            String result ="";
            URL url;
            HttpURLConnection urlConnection =null;
            Log.i("test1" , "before while");
            try {
                url = new URL(params[0]);

                urlConnection =(HttpURLConnection)url.openConnection();

                InputStream in = urlConnection.getInputStream();

                InputStreamReader reader = new InputStreamReader(in);
                int data =reader.read();
                Log.i("test2" , "before while");
                while (data!=-1)
                {

                    char current = (char) data;
                    result+=current;
                    data=reader.read();

                }

                Log.i("result" , result);

                return result ;
            }

            catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {

                e.printStackTrace();
            }
            Log.i("testing ", "end catch inbackground");
            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            super.onPostExecute(result);
            try {
                JSONObject jsonObject= new JSONObject(result);
                JSONArray reviewsArray = jsonObject.optJSONArray("results");
                Log.i("JsonSize"  ,String.valueOf(jsonObject.length()));

                Log.i("JsonSize"  ,String.valueOf( reviewsArray.length()));
                if (jsonObject.length()==0)
                {
                    Toast.makeText(getApplicationContext(), "NO Reviews Available" , Toast.LENGTH_LONG ).show();
                    return;


                }
                for (int i = 0; i <reviewsArray.length(); i++)

                {
                    JSONObject onekey = reviewsArray.getJSONObject(i);

                    String key =onekey.optString("content").toString();


                    //    Keys.add(i,key);
                    reviews.add(key);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

         //   Log.i("keys", String.valueOf(reviews.get(0)));
if (reviews.size()>0) {
    setListViewData();
}
            else
{

    Toast.makeText(getApplicationContext(), "NO Reviews founded" , Toast.LENGTH_LONG ).show();
}

        }
    }
}
