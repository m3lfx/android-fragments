package com.example.whereapp;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;


public class TagsFragment extends ListFragment {
    private ActivityComs activityComs;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataManager d = new DataManager(getActivity().getApplicationContext());
        Cursor c = d.getTags();
// Create a new adapter
        SimpleCursorAdapter cursorAdapter =
                new SimpleCursorAdapter(getActivity(),
                        android.R.layout.simple_list_item_1, c,
                        new String[] { DataManager.TABLE_ROW_TAG },
                        new int[] { android.R.id.text1 }, 0);
// Attach the Cursor to the adapter
        setListAdapter(cursorAdapter);
    }

    public void onListItemClick(ListView l, View v,
                                int position, long id) {
// What tag has just been clicked?
        Cursor c = ((SimpleCursorAdapter)l.getAdapter()).getCursor();
        c.moveToPosition(position);
        String clickedTag = c.getString(1);
// 1 is the position of the string
        Log.e("clickedTag = ", " " + clickedTag);
        activityComs.onTagsListItemSelected(clickedTag);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity){
            activityComs = (ActivityComs) context;
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        activityComs = null;
    }
}