package com.example.amgadrady.moviesapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Amgad Rady on 22/11/2016.
 */

public class Reviews_Adapter  extends BaseAdapter{
Context context;
    ArrayList<String> reviews =new ArrayList<String>();
    public static LayoutInflater  inflater =null;
    public Reviews_Adapter(Context context , ArrayList<String> reviews)

    {

        this.context=context;
        this.reviews=reviews;
        inflater=(LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);


    }

    @Override
    public int getCount() {
        return reviews.size();
    }

    @Override
    public Object getItem(int position) {

        return reviews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if(vi==null)
         vi=inflater.inflate(R.layout.reviews_adapter,null);
        TextView text =(TextView)vi.findViewById(R.id.text_1);
        text.setText(reviews.get(position));

        return  vi;


    }
}
