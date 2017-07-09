package com.gelecegiyazankadinlar.easynote;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class AddNoteActivity extends AppCompatActivity {

    private EditText etTitle, etBody, etDate, etAuthor;
    private Button btSave;

    private DatabaseReference mDatabaseReference;

    private String itemKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("notes");
        mDatabaseReference.keepSynced(true);

        etTitle = (EditText) findViewById(R.id.et_title);
        etBody = (EditText) findViewById(R.id.et_body);
        etDate = (EditText) findViewById(R.id.et_date);
        etAuthor = (EditText) findViewById(R.id.et_author);
        btSave = (Button) findViewById(R.id.bt_save);

        itemKey = getIntent().getStringExtra("KEY");


        if(itemKey == null){ // yeni not ekle


        } else {    // not düzenleme

            mDatabaseReference.child(itemKey).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    NoteModel noteModel = dataSnapshot.getValue(NoteModel.class);

                    etTitle.setText(noteModel.getTitle());
                    etBody.setText(noteModel.getBody());
                    etDate.setText(noteModel.getNoteDate());
                    etAuthor.setText(noteModel.getAuthor());

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = etTitle.getText().toString();
                String body = etBody.getText().toString();
                String noteDate = etDate.getText().toString();
                String author = etAuthor.getText().toString();

                if(itemKey == null){

                    itemKey = mDatabaseReference.push().getKey();

                }

                NoteModel noteModel = new NoteModel(itemKey, title, body, noteDate, author);

                mDatabaseReference.child(itemKey).setValue(noteModel);
                finish();

            }
        });

    }

}
