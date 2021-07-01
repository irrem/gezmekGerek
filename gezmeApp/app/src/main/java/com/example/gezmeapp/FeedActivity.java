package com.example.gezmeapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class FeedActivity extends AppCompatActivity {

    private FirebaseFirestore firebaseFirestore;
    ArrayList<String> userEmailData,userComment,userImageUrl;
    private FirebaseAuth firebaseAuth;
    RecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore= FirebaseFirestore.getInstance();

        userComment=new ArrayList<>();
        userEmailData=new ArrayList<>();
        userImageUrl=new ArrayList<>();
        getData();
        RecyclerView recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerAdapter= new RecyclerAdapter(userEmailData,userComment,userImageUrl);
        recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.options_menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.upload){
            startActivity(new Intent(FeedActivity.this,UploadActivity.class));
        }else if(item.getItemId()==R.id.signout){
            try{
                firebaseAuth.signOut();
                startActivity( new Intent(FeedActivity.this,MainActivity.class));
                finish();
            }
            catch (Exception e){
                Toast.makeText(FeedActivity.this,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public void getData(){
        CollectionReference collectionReference=firebaseFirestore.collection("Posts");
        collectionReference.orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                try{
                    if(value!=null){
                        for(DocumentSnapshot snapshot: value.getDocuments()){
                            Map<String,Object> data=snapshot.getData();

                            String comment =(String) data.get("comment");
                            String userEmail=(String) data.get("useremail");
                            String downloadUrl=(String) data.get("downloadurl");

                            userComment.add(comment);
                            userEmailData.add(userEmail);
                            userImageUrl.add(downloadUrl);

                            recyclerAdapter.notifyDataSetChanged();

                        }
                    }

                }catch(Exception e){

                }
            }
        });

    }
}