package com.example.amgadrady.moviesapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

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

public class DetailsActivity extends AppCompatActivity   {
    ArrayList<String> Keys = new ArrayList<String>();
    TextView textView1;
    TextView textView2;
    TextView textView3;
    ImageView imageView;
    Button button;
    Button button1;
    String id;
    int size=0;
    public  void openReviews(View view)
    {
        Intent intent = new Intent(getApplicationContext() , ReviewsActivity.class);
        intent.putExtra("id",id);
        startActivity(intent);


    }
  public void  openTrailer(View view)
    {

        TrailerDownload task=new TrailerDownload();
        task.execute("http://api.themoviedb.org/3/movie/"+id+"/videos?api_key=666d61b5201fa34bad7884124fa6260d");

    }

    public void starttrailer()
    {


           String videopath = "https://www.youtube.com/watch?v=" + Keys.get(0);

           Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(videopath));
           startActivity(webIntent);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            //code for portrait mode



        Intent intent= getIntent();

        String voteaverage = getIntent().getExtras().getString("voteaverage");
        String overview = getIntent().getExtras().getString("overview");
        String releasedate = getIntent().getExtras().getString("releasedate");
        String title = getIntent().getExtras().getString("title");
        String image = getIntent().getExtras().getString("image");
         id = getIntent().getExtras().getString("id");


        this.setTitle(title);


        textView1 = (TextView) findViewById(R.id.activaty_text_view1);
        textView2 = (TextView) findViewById(R.id.activaty_text_view2);
        textView3 = (TextView) findViewById(R.id.activaty_text_view3);
        imageView = (ImageView) findViewById(R.id.activaty_item_image);
         button=(Button)findViewById(R.id.trailer);
        button1 =(Button)findViewById(R.id.reviews);

        textView1.setText("Voteaverage "+voteaverage);
        textView2.setText("Releasedate "+releasedate);
        textView3.setText(overview);

        Picasso.with(this).load(image).into(imageView);
        Log.i("functon","onCreate2");

    }

    public  class TrailerDownload extends AsyncTask<String, Void , String >
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
            JSONObject jsonObject;
            JSONArray trailerArray ;
            try {
               jsonObject= new JSONObject(result);
                 trailerArray = jsonObject.optJSONArray("results");
                Log.i("JsonSize"  ,String.valueOf(jsonObject.length()));

                Log.i("JsonSize"  ,String.valueOf( trailerArray.length()));
                size=trailerArray.length();
                for (int i = 0; i < trailerArray.length(); i++)

                {
                    JSONObject onekey = trailerArray.getJSONObject(i);

                    String key =onekey.optString("key").toString();


                    //    Keys.add(i,key);
                    Keys.add(key);
                }
                if (Keys.size()>0)
                {

                    Log.i("keys", String.valueOf(Keys.get(0)));
                    starttrailer();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "NO Trailer founded" , Toast.LENGTH_LONG ).show();
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("functon","onpause2");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("functon","onRestart2");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("functon","onResume2");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("functon","onstart2");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("functon","onstop2");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);


    }


}
