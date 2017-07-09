package com.gelecegiyazankadinlar.easynote;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fabAddNote;

    private ListView lvNotes;
    private NoteAdapter noteAdapter;
    private List<NoteModel> noteModelList;

    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("notes");

        fabAddNote = (FloatingActionButton) findViewById(R.id.fab_add_note);
        fabAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                startActivity(intent);
            }
        });

        lvNotes = (ListView) findViewById(R.id.lv_notes);
        noteModelList = new ArrayList<>();
        noteAdapter = new NoteAdapter(this, R.layout.item_list_note, noteModelList);
        lvNotes.setAdapter(noteAdapter);

        lvNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                NoteModel noteModel = noteModelList.get(position);

                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                intent.putExtra("KEY", noteModel.getKey());
                startActivity(intent);

            }
        });

        lvNotes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {

                NoteModel noteModel = noteModelList.get(position);

                mDatabaseReference.child(noteModel.getKey()).removeValue();

                return true;
            }
        });

        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                NoteModel noteModel = dataSnapshot.getValue(NoteModel.class);
                noteModelList.add(noteModel);
                noteAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                NoteModel noteModel = dataSnapshot.getValue(NoteModel.class);

                for (NoteModel item : noteModelList) {

                    if(item.getKey().equals(noteModel.getKey())){

                        item.setTitle(noteModel.getTitle());
                        item.setBody(noteModel.getBody());
                        item.setNoteDate(noteModel.getNoteDate());
                        item.setAuthor(noteModel.getAuthor());
                        noteAdapter.notifyDataSetChanged();

                    }

                }

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

                NoteModel noteModel = dataSnapshot.getValue(NoteModel.class);
                NoteModel tempModel = null;

                for (NoteModel item : noteModelList) {

                    if(item.getKey().equals(noteModel.getKey())){
                        tempModel = item;
                    }

                }

                if(tempModel != null){
                    noteModelList.remove(tempModel);
                    noteAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabaseReference.addChildEventListener(mChildEventListener);

        new MyAsync().execute();

    }

    class MyAsync extends AsyncTask{

        AlertDialog mAlertDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mAlertDialog = new AlertDialog.Builder(MainActivity.this).show();
        }

        @Override
        protected Object doInBackground(Object[] objects) {

            for (int i = 0; i < 100000; i++){
                Log.d("TAG", ""+i);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            mAlertDialog.dismiss();
        }
    }

}