package com.example.a1.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.TextView;

public class Tabbed extends AppCompatActivity {
    SectionPageAdapter sectionPAgeAdapter;
ViewPager mviewPager;
ECommerceDB eCommerceDB;
    TextView tell,logouttxt;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);
sectionPAgeAdapter=new SectionPageAdapter(getSupportFragmentManager());
        mviewPager=(ViewPager)findViewById(R.id.container);
        setUpViewPager(mviewPager);
        TabLayout tabLayout=(TabLayout)findViewById(R.id.tabs);
        logouttxt=(TextView)findViewById(R.id.logouttxtId);
tabLayout.setupWithViewPager(mviewPager);
        eCommerceDB=new ECommerceDB(this);

logouttxt.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        Intent i=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(i);
    }
});
    }
public void setUpViewPager(ViewPager viewPager){
    SectionPageAdapter adapter=new SectionPageAdapter(getSupportFragmentManager());
    adapter.Add_Fragment(new booksfragment(),"Books");
    adapter.Add_Fragment(new CDfragment(),"Sound CD's");
    adapter.Add_Fragment(new MobilePhones(),"Mobile Phones");

viewPager.setAdapter(adapter);
}

    @Override
    public void onBackPressed() {

    }
}
