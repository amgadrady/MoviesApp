package com.example.amgadrady.moviesapp;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
public class Adapter extends BaseAdapter {

    private Context context;
  private ArrayList<MovieData> data;
    public Resources res;
    private static LayoutInflater inflater = null;
    private MovieData nobj;


    static class Viewholder {

        ImageView imageView;
        //TextView text;


    }

    public Adapter(Context c, ArrayList<MovieData> data, Resources res) {

        this.context = c;
        this.data = data;
        this.res = res;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        if (data.size() <= 0)
            return 1;
        return data.size();


    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        Viewholder holder;


        if (row == null) {


            // LayoutInflater inflater =((Activity)context).getLayoutInflater();

            // row =inflater.inflate(R.layout.adapter_data , null);
            row = inflater.inflate(R.layout.adapter_data, parent, false);

            holder = new Viewholder();

            holder.imageView = (ImageView) row.findViewById(R.id.adapter_item_image);
        //   holder.text = (TextView) row.findViewById(R.id.adapter_item_title);
            row.setTag(holder);

        } else {

            holder = (Viewholder) row.getTag();
        }
        if (data.size() <= 0) {
       //    holder.text.setText("No Data found !!");

        }

        nobj = data.get(position);
        Picasso.with(context).load(nobj.getposterPath()).into(holder.imageView);

        return row;

    }
}


