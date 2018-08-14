package com.example.a1.shopping;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SignUp extends AppCompatActivity {
TextView bithdatetxt;
    EditText name,username,password,job,answer;
    RadioButton male,female;
    String Gender="";
   String birthdate="";
    ECommerceDB eCommerceDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
      bithdatetxt=(TextView) findViewById(R.id.birthdateId);
    male=(RadioButton)findViewById(R.id.maleRbtnId);
        female=(RadioButton)findViewById(R.id.femaleRbtnId);
      name=(EditText)findViewById(R.id.nameEtID);
        username=(EditText)findViewById(R.id.usernameEtID);
        password=(EditText)findViewById(R.id.passwordEtID);
        job=(EditText)findViewById(R.id.JobEtID);
        answer=(EditText)findViewById(R.id.answerEtID);
        eCommerceDB=new ECommerceDB(this);
       // Get The Current Date
        Date currentTime = Calendar.getInstance().getTime();
        DateFormat df2 = new SimpleDateFormat("dd-MM-yyyy");
        String valDate = df2.format(currentTime);
        bithdatetxt.setText(valDate);

        final Calendar c = Calendar.getInstance();
      final   int Year = c.get(Calendar.YEAR);
      final   int Month = c.get(Calendar.MONTH);
        final    int Day = c.get(Calendar.DAY_OF_MONTH);

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                female.setChecked(false);
            Gender="male";
            }
        });
female.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        male.setChecked(false);
        Gender="female";
    }
});
    bithdatetxt.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            DatePickerDialog DateDialoge = new DatePickerDialog(SignUp.this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            Integer _month = month + 1;
                            bithdatetxt.setText(dayOfMonth + "-" + _month + "-" + year);
                       birthdate=(dayOfMonth + "-" + _month + "-" + year).toString();
                        }

                        ;
                    }, Year, Month, Day
            );
            DateDialoge.setTitle("Choose Date");
            DateDialoge.show();

        }
    });

    }


    //Create New Account  OnClick
    public void CreateNewAccountFn(View view) {
        boolean found=false;
       found= eCommerceDB.checkCustomerSignUp(username.getText().toString());
      if(found ==true){
          Toast.makeText(this,"There is another user with the same username.",Toast.LENGTH_LONG).show();
      }
      else {
          eCommerceDB.Add_NEW_Customer(name.getText().toString(), username.getText().toString(), password.getText().toString(), Gender, birthdate, job.getText().toString(),answer.getText().toString());
          //   eCommerceDB.Add_NEW_Customer("ayman","1015","123","male","26-10-1994","developer");
          Toast.makeText(this, "Welcome " + name.getText().toString(), Toast.LENGTH_LONG).show();
          List<String> all = eCommerceDB.getAllCustomers();
          Toast.makeText(this, all.toString(), Toast.LENGTH_LONG).show();
          Intent i = new Intent(this, MainActivity.class);
          startActivity(i);

      }
      }
}
