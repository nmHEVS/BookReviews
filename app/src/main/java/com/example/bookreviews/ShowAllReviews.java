package com.example.bookreviews;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ShowAllReviews extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_all_reviews);

        final String[] reviews = getResources().getStringArray(R.array.allreviews_array);
        ListView list;

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.listview_reviews_layout, reviews){
            //Call for every entry in the ArrayAdapter
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                View view;
                //If view doesn't exist create a new view
                if(convertView==null){
                    //Create the Layout
                    LayoutInflater inflater = getLayoutInflater();
                    view = inflater.inflate(R.layout.listview_reviews_layout, parent, false);
                } else{
                    view = convertView;
                }

                //Add Text to the layout
                TextView textView1 = (TextView) view.findViewById(R.id.allreviews_array);
                textView1.setText(reviews[position]);

                return view;
            }
        };

        //ListView
        list = (ListView) findViewById(R.id.allreviews_array);
        list.setAdapter(adapter);

        //ListView handler
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                displayReview(view);
            }
        });

    }


    /** Called when click on the book to display*/
    public void displayReview(View view) {
        Intent intent = new Intent(this, ShowReview.class);
        startActivity(intent);
    }
}
