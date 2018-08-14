package com.example.a1.shopping;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MakeOrder extends AppCompatActivity {
    ListView cartlistview;
    ArrayList<Product> productArrayList;
    MyCustomAdapter myCustomAdapter;
    ShoppingCart shoppingCart;
    TextView total;
    Integer TotalPrice = 0;
    ECommerceDB eCommerceDB;

Button buybtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_order);
        cartlistview = (ListView) findViewById(R.id.cartlistviewID);
        total = (TextView) findViewById(R.id.TotlaPRicetxtID);
       buybtn=(Button)findViewById(R.id.buybtnID);
        productArrayList = ((ShoppingCart) (getApplication())).getProducts();
        myCustomAdapter = new MyCustomAdapter(productArrayList);
        cartlistview.setAdapter(myCustomAdapter);

        TotalPrice = ((ShoppingCart) (getApplication())).getTotalPrice();
        total.setText(TotalPrice.toString());
        eCommerceDB = new ECommerceDB(this);
if(myCustomAdapter.getCount()<=0){
    buybtn.setEnabled(false);
}
    }

    ProgressDialog progressDialog;
    Context context;

    public void BuyAll(View view) {
        String address="";
        Integer username = ((ShoppingCart) getApplication()).getUserName();
        Date currentTime = Calendar.getInstance().getTime();
        DateFormat df2 = new SimpleDateFormat("dd-MM-yyyy");
        String order_date = df2.format(currentTime);
        progressDialog = new ProgressDialog(this);
    progressDialog.show();
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
progressDialog.setMessage("Getting Your Location...");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (lastLocation==null){
            lastLocation=locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

             address=lastLocation.getLongitude()+","+lastLocation.getLatitude();

        }
       progressDialog.dismiss();

  eCommerceDB.Add_New_Order(order_date,username,address);
        ((ShoppingCart)getApplication()).setTotalPrice(0);
       ShoppingCart.totalPrice=0;
      ShoppingCart.products.clear();
        Toast.makeText(getApplicationContext(),"Thank You For Using Our App",Toast.LENGTH_LONG).show();
        Intent i=new Intent(this,Tabbed.class);
        startActivity(i);
    }

    public class MyCustomAdapter extends BaseAdapter {
        ArrayList<Product> arr = new ArrayList<Product>();

        public MyCustomAdapter(ArrayList<Product> item) {
            this.arr = item;
        }


        @Override
        public int getCount() {
            return arr.size();
        }

        @Override
        public Object getItem(int position) {
            return arr.get(position).getName();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View view1 = inflater.inflate(R.layout.cartlistrow2, null);
            final TextView nametxt = (TextView) view1.findViewById(R.id.proNametxtID);
            final TextView pricetxt = (TextView) view1.findViewById(R.id.proPricetxtID);
            final Spinner QtSpinner=(Spinner)view1.findViewById(R.id.QTSpID);
            final Button Delbtn=(Button)view1.findViewById(R.id.deleteItembtnId);
            nametxt.setText(arr.get(position).getName());
            pricetxt.setText(arr.get(position).getPrice().toString() + " L.E");
          //  TotalPrice+=arr.get(position).getPrice();
            //total.setText("Total : "+TotalPrice.toString()+ " L.E");

            QtSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position1, long id) {
                    TotalPrice+=(arr.get(position).getPrice()*position1);
                  //  TotalPrice-=arr.get(position).getPrice();
                    total.setText("Total : "+TotalPrice.toString()+ " L.E");

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            Delbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
if(myCustomAdapter.getCount()<=1){
    ShoppingCart.totalPrice=0;
    ShoppingCart.products.clear();
    arr.clear();
    TotalPrice=ShoppingCart.totalPrice;
    total.setText("Total : "+TotalPrice.toString()+ " L.E");
    myCustomAdapter.notifyDataSetChanged();
    buybtn.setEnabled(false);
}
else {
    ShoppingCart.totalPrice -= (arr.get(position).Price);
    ShoppingCart.products.remove(position);
    arr.remove(myCustomAdapter.getItem(position));
    TotalPrice = ShoppingCart.totalPrice;
    total.setText("Total : " + TotalPrice.toString() + " L.E");
    myCustomAdapter.notifyDataSetChanged();
}
                }
            });
            Integer QT=Integer.parseInt(arr.get(position).getQuantity().toString());
            ArrayList <Integer>arrayList=new ArrayList();
            for (int i=1;i<=QT;i++)
                arrayList.add(i);
            ArrayAdapter<Integer> arrayAdapter=new ArrayAdapter<Integer>(getApplication(),android.R.layout.simple_spinner_dropdown_item,arrayList);
             QtSpinner.setAdapter(arrayAdapter);

            return view1;
        }

    }
    }
