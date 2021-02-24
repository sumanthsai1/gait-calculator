package com.example.test_giat;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private  MaterialEditText name,age,dob,email,password,phonenummber;
    private Button register;
    private RadioGroup radio;
    private RadioButton male,female;
    private ImageView calender;
    private TextView selected_gender;
    int year;
    int month;
    int day;

    DatabaseReference reference;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth auth;



    private static final int DATE_PICKER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();

        email = findViewById(R.id.email);
        password=findViewById(R.id.password);
        age = findViewById(R.id.age);
        dob = findViewById(R.id.date);
        phonenummber=findViewById(R.id.phoneno);
        calender = findViewById(R.id.calender);
        radio=findViewById(R.id.radio);
        male=findViewById(R.id.male);
        female=findViewById(R.id.female);
        selected_gender=findViewById(R.id.selected_gender);
        register=findViewById(R.id.register);

        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (male.isChecked())
                {
                    selected_gender.setText("Male");
                }
                else
                {
                    selected_gender.setText("Female");
                }
            }
        });
        calender.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                SelectDate();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String txt_name=name.getText().toString();
                String txt_email=email.getText().toString();
                String txt_age=age.getText().toString();
                String txt_dob=dob.getText().toString();
                String txt_gender=selected_gender.getText().toString();
                String txt_mobile=phonenummber.getText().toString();
                String txt_password=password.getText().toString();

                if (TextUtils.isEmpty(txt_name) || TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_age)  || TextUtils.isEmpty(txt_dob)  || TextUtils.isEmpty(txt_gender)  || TextUtils.isEmpty(txt_mobile)  || TextUtils.isEmpty(txt_password)) {
                    Toast.makeText(RegisterActivity.this, "All fileds are required", Toast.LENGTH_SHORT).show();
                }
                else if (txt_password.length() < 8 ) {
                    Toast.makeText(RegisterActivity.this, "password must be at least 8 characters", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    register(txt_name,txt_email,txt_age,txt_password,txt_dob,txt_gender,txt_mobile);
                }



            }
        });


    }
    private void register(final String name, final String email, final String age, final String password, final String dob, final String gender, final String mobile) {




        java.util.Calendar calendar= java.util.Calendar.getInstance();
        SimpleDateFormat format=new SimpleDateFormat("HH:mm:ss");
        final String time=format.format(calendar.getTime());
        final String Date= DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());


        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            assert firebaseUser != null;
                            String userid = firebaseUser.getUid();

                            reference = firebaseDatabase.getInstance().getReference("Users").child(userid);

                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("Id", userid);
                            hashMap.put("Name", name);
                            hashMap.put("Email", email);
                            hashMap.put("Age", age);
                            hashMap.put("DOB", dob);
                            hashMap.put("Gender", gender);
                            hashMap.put("Phone Number", mobile);
                            hashMap.put("Password", password);
                            hashMap.put("Time",time);
                            hashMap.put("Date",Date);


                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(RegisterActivity.this, "You can't register with this email or password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void SelectDate() {
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);


        showDialog(DATE_PICKER_ID);
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_PICKER_ID:

                // open datepicker dialog.
                // set date picker for current date
                // add pickerListener listner to date picker
                return new DatePickerDialog(this, pickerListener, year, month,day);
        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year  = selectedYear;
            month = selectedMonth;
            day   = selectedDay;

            // Show selected date
            dob.setText(new StringBuilder().append(month + 1)
                    .append("-").append(day).append("-").append(year)
                    .append(" "));

        }
    };
}