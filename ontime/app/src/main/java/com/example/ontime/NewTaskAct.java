package com.example.ontime;




import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import static android.text.TextUtils.isEmpty;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Random;

public class NewTaskAct extends AppCompatActivity {

    private TextView titlepage, addtitle, adddesc, adddate, addinfo;
    private EditText  descdoes,  infodoes, datedoes;
    private RadioGroup placedoes;
    private Spinner titledoes;
    private LinearLayout barangdoes;
    private CheckBox barang1, barang2, barang3;
    private RadioButton place1, place2;
    private Button btnSaveTask, btnCancel;
    private String getTitledoes,getDatedoes,getDescdoes, getInfodoes, getPlacedoes, getBarangdoes;
    private ImageView ic_cancel;
    DatabaseReference getReference;
    DatePickerDialog.OnDateSetListener setListener;
    Integer doesNum = new Random().nextInt();
    int year,month,day;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        titlepage = findViewById(R.id.titlepage);
        ic_cancel = findViewById(R.id.ic_cancel);

        addtitle = findViewById(R.id.addtitle);
        adddesc = findViewById(R.id.adddesc);
        adddate = findViewById(R.id.adddate);
        addinfo = findViewById(R.id.addinfo);

        infodoes  = findViewById(R.id.infodoes);
        titledoes = findViewById(R.id.titledoes);
        descdoes = findViewById(R.id.descdoes);
        datedoes = findViewById(R.id.datedoes);
        place1 = findViewById(R.id.place1);
        place2 = findViewById(R.id.place2);
        placedoes = findViewById(R.id.placedoes);


        // Check Box
        barang1 = findViewById(R.id.barang1);
        barang2 = findViewById(R.id.barang2);
        barang3 = findViewById(R.id.barang3);
        barangdoes = findViewById(R.id.barangdoes);


        btnSaveTask = findViewById(R.id.btnSaveTask);
        // Calender
        final Calendar calendar = Calendar.getInstance();



        // Calender
        infodoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = calendar.get(Calendar.YEAR);
                 int month = calendar.get(Calendar.MONTH);
                 int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(NewTaskAct.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        String date = day + "/" +month+"/"+ year;
                        infodoes.setText(date);
                    }
                },year, month, day);
                datePickerDialog.show();


            }
        });
        // Tombol Cancel

        ic_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewTaskAct.this,MainActivity.class);
                startActivity(intent);
            }
        });
        // Tombol Save
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        getReference = database.getReference();

        btnSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Logic Radio button
                String m1 = place1.getText().toString();
                String m2 = place2.getText().toString();
                if(place1.isChecked()){
                    getPlacedoes = place1.getText().toString();
                }else{
                    getPlacedoes = place2.getText().toString();
                }
                getTitledoes = titledoes.getSelectedItem().toString();
                getDatedoes = datedoes.getText().toString();
                getDescdoes = descdoes.getText().toString();
                getInfodoes = infodoes.getText().toString();
                // Logic checkbox
                String m3 = barang1.getText().toString();
                String m4 = barang2.getText().toString();
                String m5 = barang3.getText().toString();
                if(barang1.isChecked() && !barang2.isChecked()){
                    getBarangdoes = barang1.getText().toString();

                }else if(barang2.isChecked()  && !barang1.isChecked()){
                    getBarangdoes = barang2.getText().toString();

                }else if(barang2.isChecked() && barang1.isChecked() && !barang3.isChecked()){
                    getBarangdoes = barang3.getText().toString();

                }


                Intent a = new Intent(NewTaskAct.this,MainActivity.class);
                startActivity(a);
                checkUser();
            }
        });

    }

    private void checkUser() {
        if(isEmpty(getTitledoes) && isEmpty(getDatedoes) && isEmpty(getDescdoes)&& isEmpty(getInfodoes)&& isEmpty(getPlacedoes)&& isEmpty(getBarangdoes)){
            Toast.makeText(NewTaskAct.this,"Task cannot be empty",Toast.LENGTH_SHORT).show();
        } else{
            getReference.child("MyNote").child("Does").push()
                    .setValue(new data_kegiatan(getTitledoes, getDatedoes, getDescdoes, getInfodoes, getPlacedoes, getBarangdoes))
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            titledoes.getSelectedItem().toString();
                            datedoes.setText("");
                            descdoes.setText("");
                            infodoes.setText("");
                            // metod radio button
                            String m1 = place1.getText().toString();
                            String m2 = place1.getText().toString();
                            if(place1.isChecked()){
                                getPlacedoes = place1.getText().toString();
                            }else{
                                getPlacedoes = place2.getText().toString();
                            }
                            // Logic checkbox
                            String m3 = barang1.getText().toString();
                            String m4 = barang2.getText().toString();
                            String m5 = barang3.getText().toString();
                            if(barang1.isChecked() && !barang2.isChecked()){
                                getBarangdoes = barang1.getText().toString();

                            }else if(barang2.isChecked() && !barang1.isChecked()){
                                getBarangdoes = barang2.getText().toString();

                            }else if(barang2.isChecked() && barang1.isChecked()  && !barang3.isChecked()){
                                getBarangdoes = barang3.getText().toString();

                            }
                            Toast.makeText(NewTaskAct.this,"Successfully Added Task", Toast.LENGTH_SHORT).show();

                        }
                    });
        }
    }
}