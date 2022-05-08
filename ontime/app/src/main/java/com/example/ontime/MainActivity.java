package com.example.ontime;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.dataListener {
    // Deklarasi Variable
    private ImageView gambar;
    private Button btnAddNew;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton fab;
    private TextView logo;
    private SearchView etsearch;


    //Deklarasi Variable Database Referesi dan Arraylist dengan parameter model
    private DatabaseReference reference;
    DatabaseReference getReference; // ini yg jadi dipake
    private ArrayList<data_kegiatan> dataKegiatan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.datalist);
        gambar = findViewById(R.id.gambar);

        // FAB Button
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(MainActivity.this,NewTaskAct.class);
                startActivity(a);
            }
        });
        recyclerView = findViewById(R.id.datalist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        logo = findViewById(R.id.logo);

        // Search view
        etsearch = findViewById(R.id.etsearch);
        etsearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                GetData(query.toString());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                GetData(newText.toString());
                return false;
            }
        });

        MyRecyclerView();
        GetData("");

    }

    // Get data berfungsi mengambil data juga mencari data
    public void GetData(String cari){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        getReference = database.getReference();
        if (cari.isEmpty() == true) {
            MyRecyclerView();
            getReference.child("MyNote").child("Does")
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            dataKegiatan = new ArrayList<>();
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                                data_kegiatan kegiatan = snapshot.getValue(data_kegiatan.class);
                                kegiatan.setKey(snapshot.getKey());
                                dataKegiatan.add(kegiatan);
                            }
                            adapter = new RecyclerViewAdapter(dataKegiatan, MainActivity.this);
                            recyclerView.setAdapter(adapter);

                            int size = dataKegiatan.size();
                            if(size>0){
                                gambar.setVisibility(View.GONE);
                            }else{
                                gambar.setVisibility(View.VISIBLE);
                            }

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(getApplicationContext(), "Task failed to load", Toast.LENGTH_SHORT).show();
                            Log.e("MainActivity",databaseError.getDetails()+" "+databaseError.getMessage());

                        }
                    });
        }else{
            getReference.child("MyNote").child("Does").orderByChild("titledoes").startAt(cari).endAt(cari)
             .addValueEventListener(new ValueEventListener() {
                 @Override
                 public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                     dataKegiatan = new ArrayList<>();
                     for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                         data_kegiatan kegiatan = snapshot.getValue(data_kegiatan.class);
                         kegiatan.setKey(snapshot.getKey());
                         dataKegiatan.add(kegiatan);
                     }
                     adapter = new RecyclerViewAdapter(dataKegiatan, MainActivity.this);
                     recyclerView.setAdapter(adapter);

                     int size = dataKegiatan.size();
                     if(size>0){
                         gambar.setVisibility(View.GONE);
                     }else{
                         gambar.setVisibility(View.VISIBLE);
                     }

                 }

                 @Override
                 public void onCancelled(@NonNull DatabaseError databaseError) {
                     Toast.makeText(getApplicationContext(), "Task failed to load", Toast.LENGTH_SHORT).show();
                     Log.e("MainActivity",databaseError.getDetails()+" "+databaseError.getMessage());

                 }
             });
        }
    }



    private void MyRecyclerView() {
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);


    }
    // metod delete data
    @Override
    public void onDeleteData(data_kegiatan data, int position) {
        /*
         * Kode ini akan diambil ketika method onDeleteData
         * dipanggil dari adapter pada RecyleView melalui Interface
         * kemudian akan menghapus data berdasarkan primary key dari data tersebut
         * jika berhasil, maka akan memunculkan toast
         * */
        if(getReference != null){
            getReference.child("MyNote")
                    .child("Does")
                    .child(data.getKey())
                    .removeValue()
                    .addOnSuccessListener(new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                            Toast.makeText(MainActivity.this,"Task deleted successfully",Toast.LENGTH_SHORT).show();
                        }
                    });

        }
    }
}