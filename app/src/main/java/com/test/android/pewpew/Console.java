package com.test.android.pewpew;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Console extends AppCompatActivity implements View.OnClickListener{

    Button left;
    Button right;
    Button up;
    Button down;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    String nikname = "io";
    Player myPlayer;
    DatabaseReference playerRef;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.content_console);

        left = (Button) findViewById(R.id.Left_button);
        right = (Button) findViewById(R.id.Right_button);
        up = (Button) findViewById(R.id.Up_button);
        down = (Button) findViewById(R.id.Down_button);

        left.setOnClickListener(this);
        right.setOnClickListener(this);
        up.setOnClickListener(this);
        down.setOnClickListener(this);

        myPlayer = new Player();

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nikname = dataSnapshot.child("Users/"+user.getUid()+"/nikname").getValue(String.class);
                //Toast.makeText(Console.this, nikname, Toast.LENGTH_SHORT).show();

                Map<String,Object> list = new HashMap<String,Object>();
                list.put(nikname,myPlayer);
                rootRef.child("Players").updateChildren(list);
                playerRef = rootRef.child("Players/"+nikname);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id)
        {
            case R.id.Left_button:
                break;
            case R.id.Right_button:
                break;
            case R.id.Up_button:
                break;
            case R.id.Down_button:
                break;
            default:
                break;
        }

    }
}






/*  if(Left.isPressed())
            //sinistra
        if(Right.isPressed())
            //destra
        if(Up.isPressed())
            //SU
        if(Down.isPressed())
            //giù*/