package com.example.amgadrady.moviesapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
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

import static com.example.amgadrady.moviesapp.R.string.pref_key;

public class MainActivity extends AppCompatActivity {
 Button button;
    GridView gridView;
    ProgressBar  mProgressBar ;
    Adapter adapter;
   ArrayList<MovieData> movieData ;
static Bundle save = null;
    String Link;
    String sLink;

public  boolean isConnected()
{

    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo Info = cm.getActiveNetworkInfo();

    return  Info != null &&Info.isConnectedOrConnecting();

}
    public void Download()
    {

        DownloadTask task = new DownloadTask();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        Link = settings.getString(getString(pref_key), getString(R.string.defult_value));
sLink=Link;
        Log.i("value", Link);


        task.execute(Link);

    }
    public void  startFunction()
    {
        gridView = (GridView) findViewById(R.id.gridView);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
       /* SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String sortingMethod = sharedPreferences.getString(getString(R.string.pref_key), getString(R.string.pref_default_value));
Log.i("value",sortingMethod);*/



        // task.execute("http://api.themoviedb.org/3/movie/top_rated?api_key=666d61b5201fa34bad7884124fa6260d");


        mProgressBar.setVisibility(View.VISIBLE);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                MovieData obj;
                obj = movieData.get(position);

                intent.putExtra("voteaverage", obj.getvoteAverage());
                intent.putExtra("overview", obj.getoverview());
                intent.putExtra("releasedate", obj.getreleaseDate());
                intent.putExtra("title", obj.gettitle());
                intent.putExtra("image", obj.getposterPath());
                intent.putExtra("id",obj.getid());

                startActivity(intent);
            }
        });

    }

    public void ReDownload (View view)
    {

        if (isConnected()) {
            button.setVisibility(View.INVISIBLE);
            startFunction();

        }
        else
            Toast.makeText(getApplicationContext(), "NO Internet Connection" , Toast.LENGTH_LONG ).show();

    }
   ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

savedInstanceState=save;
//Link=savedInstanceState.getString("Link");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


if(savedInstanceState == null ) {
    if (isConnected()) {
        startFunction();
        Download();

    } else {

        Toast.makeText(getApplicationContext(), "NO Internet Connection", Toast.LENGTH_LONG).show();

        button = (Button) findViewById(R.id.button);
        button.setVisibility(View.VISIBLE);
        // ReDownload();
        // onCreate(savedInstanceState);
    }
    Log.i("functon", "onCreate1");
}

else
        {

            startFunction();
            movieData=savedInstanceState.getParcelableArrayList("key");
            adapter = new Adapter(MainActivity.this,  movieData  ,getResources() );

            gridView.setAdapter(adapter);



            mProgressBar.setVisibility(View.GONE);
            // adapter = new Adapter(MainActivity.this,  movieData  ,getResources() );


            Log.i("test","else");
        }

    }

    //pref_data_sync




    public class DownloadTask extends AsyncTask<String , Void , String >
    {



        @Override
        protected String doInBackground(String... params) {
            Log.i("testing", "inasynktask");
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
        protected   void  onPostExecute (String result) {
            super.onPostExecute(result);

            Log.i("testing ", "start try onpostexcute");

try {
    JSONObject jsonObject = new JSONObject(result);
     Log.i("JsonSize"  ,String.valueOf(jsonObject.length()));
    JSONArray moviesArray = jsonObject.optJSONArray("results");
    Log.i("JsonSize"  ,String.valueOf(moviesArray.length()));


movieData = new ArrayList<>();
    MovieData data ;
    String path;
int n=0;

    for (int i = 0; i < moviesArray.length(); i++)

    {

        n=i;
        data =  new MovieData();
        JSONObject onemovie = moviesArray.getJSONObject(i);

        String posterPath = onemovie.optString("poster_path").toString() ;
        String overview = onemovie.optString("overview").toString();
         String releaseDate  = onemovie.optString("release_date").toString();
          String id= onemovie.optString("id").toString();
          String title = onemovie.optString("title").toString();
      String  voteAverage= onemovie.optString("vote_average").toString();

        path="http://image.tmdb.org/t/p/w184"+posterPath;

        data.setposterPath(path);
        data.setoverview(overview);
        data.setreleaseDate(releaseDate);
        data.setid(id);
        data.settitle(title);
        data.setvoteAverage(voteAverage);


movieData.add(data);
        Log.i("posterpath", path);
        Log.i("overview", overview);
        Log.i("releasedate",releaseDate);
        Log.i("id", id);
        Log.i("title", title);
      Log.i("voteaverage",  voteAverage);


    }

    Log.i("numberof iterations" ,String.valueOf( n));
    Log.i("testing ", "end try onpostexcute");

    adapter = new Adapter(MainActivity.this,  movieData  ,getResources() );

    gridView.setAdapter(adapter);



    mProgressBar.setVisibility(View.GONE);
    return;
}


catch (JSONException e)
{

    e.printStackTrace();
}

            return ;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putParcelableArrayList("key",movieData);
outState.putString("Link",sLink);
        super.onSaveInstanceState(outState);
        Log.i("test","onSaveInstanceState");
        save=outState;

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

      super.onRestoreInstanceState(savedInstanceState);
        movieData=savedInstanceState.getParcelableArrayList("key");
        Log.i("test","onRestoreInstanceState");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.title_activity_settings) {

            startActivity(new Intent(getApplicationContext() , SettingsActivity.class));

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.i("functon","onpause1");
    }

    @Override
     protected void onRestart() {
        super.onRestart();

        Log.i("functon","onRestart1");
    }

    @Override
    protected void onResume() {
        super.onResume();


        Log.i("functon","onResume1");
    }

    @Override
    protected void onStart() {
        super.onStart();



    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.i("functon","onstop1");
    }

}
