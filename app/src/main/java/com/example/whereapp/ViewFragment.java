package com.example.whereapp;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;


public class ViewFragment extends Fragment {
    private Cursor cursor;
    private ImageView imageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int position = getArguments().getInt("Position");
// Load the appropriate photo from db
        DataManager d = new DataManager
                (getActivity().getApplicationContext());
        cursor = d.getPhoto(position);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view,
                container, false);
        TextView textView = (TextView)
                view.findViewById(R.id.textView);
        Button buttonShowLocation = (Button)
                view.findViewById(R.id.buttonShowLocation);
// Set the text from the tile column of the data.
        textView.setText(cursor.getString(cursor.getColumnIndexOrThrow(DataManager.TABLE_ROW_TITLE)));
        imageView = (ImageView) view.findViewById(R.id.imageView);
// Load the image into the TextView via the URI
        imageView.setImageURI(Uri.parse(cursor.getString(cursor.getColumnIndexOrThrow(DataManager.TABLE_ROW_URI))));

        buttonShowLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double latitude = Double.valueOf(cursor.getString
                        (cursor.getColumnIndexOrThrow(DataManager.
                                TABLE_ROW_LOCATION_LAT)));
                double longitude = Double.valueOf(cursor.getString
                        (cursor.getColumnIndexOrThrow(DataManager.
                                TABLE_ROW_LOCATION_LONG)));
// Create a URI from the latitude and longitude
                String uri = String.format(Locale.ENGLISH,
                        "geo:%f,%f", latitude, longitude);
// Create a Google maps intent
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
// Start the maps activity
                getActivity().startActivity(intent);
            }
        });
        return view;
    }
}