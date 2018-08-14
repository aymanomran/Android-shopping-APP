package com.example.a1.shopping;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ForgetPassword extends AppCompatActivity {
    EditText username,Answertxt;
   Button send;
TextView theuserpasstxt;
    String Password,Email;
    ECommerceDB eCommerceDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        username=(EditText)findViewById(R.id.usernameEtID);
        Answertxt=(EditText)findViewById(R.id.mailEtID);
    send=(Button)findViewById(R.id.sendMailbtnId);
    eCommerceDB=new ECommerceDB(this);
theuserpasstxt=(TextView)findViewById(R.id.theuserpasswordtxtID);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.

                Builder().permitAll().build();



        StrictMode.setThreadPolicy(policy);


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Password = eCommerceDB.GetPAssword(username.getText().toString());
            String Useranswer=Answertxt.getText().toString();
             String SavedAnswer=eCommerceDB.get_Answer(Integer.parseInt(username.getText().toString()),Useranswer);
          if(Useranswer.equals(SavedAnswer)){

              theuserpasstxt.setText("Your PassWord Is : "+eCommerceDB.GetPAssword(username.getText().toString()));
          }
          else
          {
              Toast.makeText(getApplicationContext(),"Wrong Answer! try Again",Toast.LENGTH_LONG).show();
          }
          /*
                   Intent i = new Intent(Intent.ACTION_SEND);
                   i.setType("message/rfc822");
                    i.setType("plain/text");
                    i.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
                    i.putExtra(Intent.EXTRA_EMAIL, new String[]{Answertxt.getText().toString()});
                    i.putExtra(Intent.EXTRA_SUBJECT, "Your Password");
                    i.putExtra(Intent.EXTRA_TEXT, "Your PassWord IS " + Password);
                    try {
                     startActivity(Intent.createChooser(i, "Send mail..."));
                        startActivity(i);
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(getApplicationContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                    }

                    Toast.makeText(getApplicationContext(), "Please Check Your Mail", Toast.LENGTH_LONG).show();
*/
            }
        });
    }

}
