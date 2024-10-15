package com.example.whereapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.cursoradapter.widget.SimpleCursorAdapter;

import androidx.fragment.app.ListFragment;


import android.view.View;

import android.widget.ListView;


public class TitlesFragment extends ListFragment {
    private Cursor cursor;
    private ActivityComs activityComs;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the tag to search for
        String tag = getArguments().getString("Tag");
// Get an instance of DataManager
        DataManager d = new DataManager (getActivity().getApplicationContext());
        if (tag == "_NO_TAG") {
// Get all the titles from the database
            cursor = d.getTitles();
        } else {
// Get all the titles with a specific related tag
            cursor = d.getTitlesWithTag(tag);
        }
        // Create a new adapter
        SimpleCursorAdapter cursorAdapter =
                new SimpleCursorAdapter(getActivity(),
                        android.R.layout.simple_list_item_1, cursor,
                        new String[]{DataManager.TABLE_ROW_TITLE},
                        new int[]{android.R.id.text1}, 0);
// Attach the adapter to the ListView

        setListAdapter(cursorAdapter);
    }

    public void onListItemClick(ListView l, View v,
                                int position, long id) {
// Move the cursor to the clicked item in the list
        cursor.moveToPosition(position);
// What is the database _id of this item?
        int dBID = cursor.getInt(cursor.getColumnIndexOrThrow(DataManager.TABLE_ROW_ID));
// Use the interface to send the clicked _id
        activityComs.onTitlesListItemSelected(dBID);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        activityComs = (ActivityComs) getActivity();
        if (context instanceof MainActivity){
            activityComs = (ActivityComs) context;
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        activityComs = null;
    }

}