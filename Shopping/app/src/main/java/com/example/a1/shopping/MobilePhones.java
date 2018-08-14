package com.example.a1.shopping;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;
import static com.example.a1.shopping.ShoppingCart.products;

/**
 * Created by 1 on 12/14/2017.
 */

public class MobilePhones extends android.support.v4.app.Fragment {
    ArrayList<Product> productArrayList;
   MyCustomAdapter myCustomAdapter, myCustomAdapter1;
    ECommerceDB eCommerceDB;
    TextView ShoppingCarttxt;
    AppCompatActivity appCompatActivity;
    ListView booklistview;
    ShoppingCart shoppingCart;
    Button makeOrderbtn, VoiceSearchbtn;
    EditText searchView;
    ArrayList<Product> productArrayList1;
    protected static final int RESULT_SPEECH = 1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.tab3,container,false);
        appCompatActivity = new AppCompatActivity();
        eCommerceDB = new ECommerceDB(getContext());
        ///////////////////////////////////
        booklistview = (ListView) view.findViewById(R.id.booklistviewID);
        ShoppingCarttxt = (TextView) view.findViewById(R.id.telllmequntitytxtID);
        makeOrderbtn = (Button) view.findViewById(R.id.makeordertxtID);
        searchView = (EditText) view.findViewById(R.id.searchviewtxtID);
        VoiceSearchbtn = (Button) view.findViewById(R.id.search_voice_btnID);

///////////////////////////////////////////////////////////////////////////
        ShoppingCarttxt.setText(String.valueOf("Shopping Cart : " + products.size() + " product/s"));
        shoppingCart = new ShoppingCart();

        productArrayList = eCommerceDB.Get_Category_Products(3);
        myCustomAdapter = new MyCustomAdapter(productArrayList);
        booklistview.setAdapter(myCustomAdapter);
        VoiceSearchbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(
                        RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

                try {
                    startActivityForResult(intent, RESULT_SPEECH);
                    searchView.setText("");
                } catch (ActivityNotFoundException a) {
                    Toast t = Toast.makeText(getContext(),
                            "Opps! Your device doesn't support Speech to Text",
                            Toast.LENGTH_SHORT);
                    t.show();
                }
            }
        });

        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                productArrayList1 = eCommerceDB.Search_About_Product(searchView.getText().toString());
                myCustomAdapter1 = new MyCustomAdapter(productArrayList1);
                booklistview.setAdapter(myCustomAdapter1);
                if (searchView.getText().toString().equals("")) {
                    myCustomAdapter = new MyCustomAdapter(productArrayList);
                    booklistview.setAdapter(myCustomAdapter);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        makeOrderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getContext(),MakeOrder.class);
                startActivity(i);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case RESULT_SPEECH: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> text = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    searchView.setText(text.get(0));
                }
                break;
            }

        }
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
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View view1 = inflater.inflate(R.layout.listrow, null);
            final TextView nametxt = (TextView) view1.findViewById(R.id.proNametxtID);
            final TextView pricetxt = (TextView) view1.findViewById(R.id.proPricetxtID);
            final TextView Quntatytxt = (TextView) view1.findViewById(R.id.proQuantitytxtID);
            final ToggleButton togglebtn = (ToggleButton) view1.findViewById(R.id.buybtnID);
            nametxt.setText(arr.get(position).getName());
            pricetxt.setText(arr.get(position).getPrice().toString() + " L.E");
            Quntatytxt.setText(" QT :" + arr.get(position).getQuantity().toString());

            togglebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //first
                    if (togglebtn.isChecked()) {
                        try {

                            products.add(arr.get(position));//new Product(nametxt.getText().toString(),Integer.parseInt(pricetxt.getText().toString()),Integer.parseInt(Quntatytxt.getText().toString())));
                            shoppingCart.totalPrice += arr.get(position).Price;

                            ShoppingCarttxt.setText(String.valueOf("Shopping Cart : " + products.size() + " product/s"));

                            Toast.makeText(getContext(), "Yes", Toast.LENGTH_LONG).show();
                        } catch (Exception ex) {
                            Log.d("Error :", ex.getMessage().toString());
                        }
                    } else {
                        products.remove(arr.get(position));
                        shoppingCart.totalPrice -= arr.get(position).Price;
                        ShoppingCarttxt.setText(String.valueOf("Shopping Cart : " + products.size() + " product/s"));
                        //second
                        Toast.makeText(getContext(), "NO", Toast.LENGTH_LONG).show();

                    }
                }
            });
            return view1;
        }


    }
}
