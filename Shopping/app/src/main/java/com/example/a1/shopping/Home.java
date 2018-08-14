package com.example.a1.shopping;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class Home extends AppCompatActivity {

    Button btn,btn2;
   ECommerceDB eCommerceDB;
ListView myproducts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    btn=(Button)findViewById(R.id.AddbnID);
        btn2=(Button)findViewById(R.id.ShowbnID);
       myproducts=(ListView)findViewById(R.id.myshoppinglistID);
eCommerceDB=new ECommerceDB(this);
        btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               eCommerceDB.Add_New_Product(1,"C++how to program",25,7,1);
               eCommerceDB.Add_New_Product(2,"Think Fast and Slow By Danial",70,6,1);
               eCommerceDB.Add_New_Product(3,"The one minute manage",65,5,1);

               eCommerceDB.Add_New_Product(4,"Hamza Namera CD ",300,4,2);
               eCommerceDB.Add_New_Product(5,"El Hossary CD",250,5,2);
               eCommerceDB.Add_New_Product(6,"Tafseer El Quraan CD",65,4,2);

               eCommerceDB.Add_New_Product(7,"Iphone 6",6000,7,3);
               eCommerceDB.Add_New_Product(8,"Iphone 7",7000,6,3);
               eCommerceDB.Add_New_Product(9,"Samsung Galaxy star",2000,5,3);
               Toast.makeText(getApplicationContext(),"good",Toast.LENGTH_LONG).show();



           }
       });

    btn2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        List<String> all_category= eCommerceDB.Get_All_Categories();
            for (int i=0;i<all_category.size();i++){
               Toast.makeText(getApplicationContext(),all_category.get(i),Toast.LENGTH_LONG).show();
            }
        }
    });
    }



}
