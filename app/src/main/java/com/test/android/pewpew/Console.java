package com.test.android.pewpew;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;



public class Console extends AppCompatActivity implements  Joystick.JoystickListener{



    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    String nikname = "io";
    Player myPlayer;
    DatabaseReference playerRef;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Joystick joystick = new Joystick(this);
        setContentView(R.layout.activity_joystick);


        myPlayer = new Player();







        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nikname = dataSnapshot.child("Users/"+user.getUid()+"/nikname").getValue(String.class);
                //Toast.makeText(Console.this, nikname, Toast.LENGTH_SHORT).show();
                //TODO: Bug da risolvere: impedire che quando si blocca e si risblocca il cellulare le coordinate vengano riazzerate !!
                Map<String,Object> list = new HashMap<String,Object>();
                list.put(nikname,myPlayer);
                rootRef.child("Players").updateChildren(list);
                playerRef = rootRef.child("Players/"+nikname);
                playerRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        myPlayer.x = dataSnapshot.child("x").getValue(Integer.class);
                        myPlayer.y = dataSnapshot.child("y").getValue(Integer.class);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }




    public void updatePlayer() {
        Map<String, Object> list = new HashMap<String, Object>();
        list.put(nikname, myPlayer);
        rootRef.child("Players").updateChildren(list);
    }


    @Override
    public void onJoystickMoved(float xPercent, float yPercent, int source) {
        Log.d("Main Method", "X percent: " + xPercent+" Y percent: " + yPercent);


        myPlayer.x = xPercent *100;
        myPlayer.y = yPercent *100;
        //Log.d("mole","posizione server:" + xPercent );

        updatePlayer();

    }
}


//TODO: implementare la scrittura dei dati su derver, e lettura per correzzioni, test joystick,



