package com.twk95.remotewizard;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CodeViewAdapter extends ArrayAdapter<String> {
    Context context;
    int layoutResourceId;
    ArrayList<String> nameData, codesData = null;

    public CodeViewAdapter(Context context, int resource, ArrayList<String> name, ArrayList<String> codes) {
        super(context, resource, codes);
        this.layoutResourceId = resource;
        this.context = context;
        this.nameData = name;
        this.codesData = codes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        TextHolder holder;
        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new TextHolder();
            holder.textName = (TextView) row.findViewById(R.id.name_text);
            holder.textCode = (TextView) row.findViewById(R.id.code_text);

            row.setTag(holder);
        } else
            holder = (TextHolder) row.getTag();

        holder.textName.setText(nameData.get(position));
        holder.textCode.setText(codesData.get(position));
        return row;
    }

    static class TextHolder {
        TextView textName, textCode;
    }

}
