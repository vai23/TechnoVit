package com.ask.vitevents.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ask.vitevents.R;

import java.util.ArrayList;

/**
 * Created by suraj on 27/8/18.
 */

public class DialogList extends ArrayAdapter<String> {

    private final Context context;
    private final ArrayList<String> id;
    private final ArrayList<String> name;
    private final ArrayList<String> size;

    //private final String[] subtitle;
    //private final Integer[] imgid;


    public DialogList(Context context, ArrayList<String> id, ArrayList<String> name, ArrayList<String> size)
    {
        super(context, R.layout.team_list);
        //super(context, R.layout.register_dialog,null);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.id = id;
        this.name = name;
        this.size = size;

        //this.subtitle=subtitle;
        //this.imgid=imgid;

    }
    @Override
    @NonNull
    public View getView(int position, View view, ViewGroup parent)
    {
        LayoutInflater inflater= LayoutInflater.from(context);

        View rowView=inflater.inflate(R.layout.team_list, null,false);

        TextView teamname = (TextView) rowView.findViewById(R.id.teamname_list);
        TextView teamsize = (TextView) rowView.findViewById(R.id.team_size);

        teamname.setText(name.get(position));
        //imageView.setImageResource(imgid[position]);
        teamsize.setText(size.get(position));

        return rowView;

    };
}
