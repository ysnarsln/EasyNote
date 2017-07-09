package com.gelecegiyazankadinlar.easynote;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class NoteAdapter extends ArrayAdapter<NoteModel>{

    public NoteAdapter(Context context, int resource, List<NoteModel> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = ((Activity) getContext())
                    .getLayoutInflater().inflate(R.layout.item_list_note, parent, false);
        }

        NoteModel noteModel = getItem(position);

        TextView tvTitle = convertView.findViewById(R.id.tv_title);
        TextView tvAuthor = convertView.findViewById(R.id.tv_author);

        tvTitle.setText(noteModel.getTitle());
        tvAuthor.setText(noteModel.getAuthor());

        return convertView;
    }
}