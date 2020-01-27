package com.test.tichy.vegkey;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.Toast;

import java.util.List;

public class CustomAdapter extends BaseAdapter  {
    String[] names;
    String[] ckt;
    Context context;
    LayoutInflater inflter;
    String value;

    public CustomAdapter(Context context, String[] names, String[] ckt) {
        this.context = context;
        this.names = names;
        this.ckt = ckt;
        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return names.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        view = inflter.inflate(R.layout.list_item, null);
        final CheckedTextView simpleCheckedTextView = (CheckedTextView) view.findViewById(R.id.simpleCheckedTextView);
        simpleCheckedTextView.setText(names[position]);
//        Toast.makeText(view.getContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
        if (ckt[position].equals("1")) {
            simpleCheckedTextView.setCheckMarkDrawable(R.drawable.checked2);
            simpleCheckedTextView.setChecked(true);
        }
// perform on Click Event Listener on CheckedTextView
        simpleCheckedTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(v.getContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
                if (simpleCheckedTextView.isChecked()) {
// set cheek mark drawable and set checked property to false
                    value = "un-Checked";
                    simpleCheckedTextView.setCheckMarkDrawable(0);
                    simpleCheckedTextView.setChecked(false);
                    ckt[position]="0";
                } else {
// set cheek mark drawable and set checked property to true
                    value = "Checked";
                    simpleCheckedTextView.setCheckMarkDrawable(R.drawable.checked2);
                    simpleCheckedTextView.setChecked(true);
                    ckt[position]="1";
                }
                //Toast.makeText(context, String.valueOf(simpleCheckedTextView.positio()), Toast.LENGTH_SHORT).show();
            }

        });
        return view;
    }
}
