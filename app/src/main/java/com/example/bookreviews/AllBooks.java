package com.example.bookreviews;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

//Class for the AllBooks frame
public class AllBooks extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_books_layout);

        final String [] books = getResources().getStringArray(R.array.books_array);
        ListView list;

        ArrayAdapter<String>adapter = new ArrayAdapter<String>(this, R.layout.listview_books_layout, books){

            //Call for every entry in the ArrayAdapter
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                View view;
                if(convertView == null) {
                    //Create the Layout
                    LayoutInflater inflater = getLayoutInflater();
                    view = inflater.inflate(R.layout.listview_books_layout, parent, false);
                }else{
                    view = convertView;
                }

                //Add Text to the layout
                TextView textView1 = (TextView) view.findViewById(R.id.lisview_books);
                textView1.setText(books[position]);
                return view;
            }
        };
    }
}
