package com.example.a1.shopping;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
EditText username,password;
 TextView forgettxt;
    ECommerceDB eCommerceDB;
    CheckBox rememberMe;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static final String PREF_NAME = "prefs";
    private static final String KEY_REMEMBER = "remember";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASS = "password";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    username=(EditText)findViewById(R.id.usernameEtID);
        password=(EditText)findViewById(R.id.passwordEtID);
     forgettxt=(TextView)findViewById(R.id.forgetpasswordtxtID);
        rememberMe=(CheckBox)findViewById(R.id.remembermeChbxbtnID);
       //REmember Me
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if(sharedPreferences.getBoolean(KEY_REMEMBER, false))
            rememberMe.setChecked(true);
        else
            rememberMe.setChecked(false);

        username.setText(sharedPreferences.getString(KEY_USERNAME,""));
        password.setText(sharedPreferences.getString(KEY_PASS,""));


//////////////////////////////////////////////////
        eCommerceDB=new ECommerceDB(this);
        forgettxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),ForgetPassword.class);
                startActivity(i);

            }
        });
    }

    //on SignUp Button Clicked
    public void signupFN(View view) {
        Intent i=new Intent(this,SignUp.class);
        startActivity(i);
    }

    //on LogIN Button Clicked
    public void loginFN(View view) {
//    Intent i=new Intent(this,Home.class);
  //      startActivity(i);


      boolean found=false;
found=eCommerceDB.checkCustomerLogin(username.getText().toString(),password.getText().toString());

    if(found==false)
    {
        Toast.makeText(this,"Wrong UserName OR Password Please try again ...",Toast.LENGTH_LONG).show();
        username.setText("");
        password.setText("");
    }
    else
    {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(username.getWindowToken(), 0);

        ((ShoppingCart)getApplication()).setUserName(Integer.parseInt(username.getText().toString()));
        Toast.makeText(this,"Welcome",Toast.LENGTH_LONG).show();
       managePrefs();

        Intent i=new Intent(this,Tabbed.class);
        startActivity(i);
    }
    }
    private void managePrefs(){
        if(rememberMe.isChecked()){
            editor.putString(KEY_USERNAME, username.getText().toString().trim());
            editor.putString(KEY_PASS, password.getText().toString().trim());
            editor.putBoolean(KEY_REMEMBER, true);
            editor.apply();
        }else{
            editor.putBoolean(KEY_REMEMBER, false);
            editor.remove(KEY_PASS);//editor.putString(KEY_PASS,"");
            editor.remove(KEY_USERNAME);//editor.putString(KEY_USERNAME, "");
            editor.apply();
        }
    }

    @Override
    public void onBackPressed() {
    finish();
    }
}
