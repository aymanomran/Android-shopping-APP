package com.example.a1.shopping;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by 1 on 12/11/2017.
 */

public class ECommerceDB extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ECommerce.db";

    // Table Names
    private static final String TABLE_Customers= "Customers";
    private static final String TABLE_Orders = "Orders";
    private static final String TABLE_Order_details = "Order_details";
    private static final String TABLE_Products = "Products";
    private static final String TABLE_Categories = "Categories";

    // Table Create Statements
    // Customers table create statement
    private static final String CREATE_TABLE_Customers = "CREATE TABLE "
            + TABLE_Customers + "(" + "CustID" + " INTEGER PRIMARY KEY AUTOINCREMENT," + "CustName"
            + " TEXT," + "UserName" + " TEXT," + "PassWord" + " TEXT,"+"Question" + " TEXT,"+ "Gender" + " TEXT," + "Job" + " TEXT,"  + "BirthDate"
            + " DATETIME" + ")";

    // Table Create Statements
    // Orders table create statement
    private static final String CREATE_TABLE_Orders = "CREATE TABLE "
            + TABLE_Orders + "(" + "OrderID" + " INTEGER PRIMARY KEY AUTOINCREMENT," + "OrderDate"
            + " TEXT," + "CustID " + " INTEGER," + "Address"
            + " text" +",FOREIGN KEY (CustID) REFERENCES Customers(CustID)"+ ")";

    // OrderDetails table create statement
    private static final String CREATE_TABLE_Order_details = "CREATE TABLE "
            + TABLE_Order_details + "(" + "OrderID" + " NOT NULL,"
            + "ProID" + " NOT NULL," + "Quantity" + " INTEGER,"
            + "PRIMARY KEY(OrderID,ProID)" + ")";

    // CAtegory table create statement
    private static final String CREATE_TABLE_Categories = "CREATE TABLE " + TABLE_Categories
            + "(" + "CatID" + " INTEGER PRIMARY KEY," + "CatName" + " TEXT" + ")";

    // Products table create statement
    private static final String CREATE_TABLE_Products = "CREATE TABLE " + TABLE_Products
            + "(" + "ProID" + " INTEGER PRIMARY KEY," + "ProName" + " TEXT,"
            + "Price" + " INTEGER," + "Quantity" + " INTEGER,"+ "CatID" + " INTEGER,"+"FOREIGN KEY (CatID) REFERENCES Categories(CatID)"+ ")";

    public ECommerceDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
// creating required tables
        db.execSQL(CREATE_TABLE_Customers);
        db.execSQL(CREATE_TABLE_Orders);
        db.execSQL(CREATE_TABLE_Order_details);
        db.execSQL(CREATE_TABLE_Products);
        db.execSQL(CREATE_TABLE_Categories);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Customers);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Orders);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Order_details);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Categories);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Products);
        onCreate(db);

    }
public void Update_Category_Names(Integer ID,String Name){
try {

    SQLiteDatabase db = this.getWritableDatabase();
    db.execSQL("update Categories set CatName='" + Name + "' where CatID=" + ID.toString());
}catch (Exception ex){
    Log.d("Error :",ex.getMessage().toString());
}
}
public  String Get_Category_Name(Integer ID){
    Cursor cursor=null;
    try {


        SQLiteDatabase db = this.getReadableDatabase();
        List<String> productds = new ArrayList<>();
        cursor = db.rawQuery("select CatName from Categories where CatID=" + ID.toString(), null);
        cursor.moveToFirst();
    }catch (Exception ex){  Log.d("Error :",ex.getMessage().toString());}
        return cursor.getString(0).toString();
}
public ArrayList<Product> Get_Category_Products(Integer ID){
    SQLiteDatabase db=this.getReadableDatabase();
    ArrayList<Product> productds=new ArrayList<>();
    Cursor cursor = db.rawQuery("select * from Products where CatID="+ID.toString(), null);
    cursor.moveToFirst();
    while (cursor.isAfterLast()==false){
        productds.add(new Product(cursor.getString(cursor.getColumnIndex("ProName")), Integer.parseInt(cursor.getString(cursor.getColumnIndex("Price"))),Integer.parseInt(cursor.getString(cursor.getColumnIndex("Quantity")))));
        cursor.moveToNext();
    }
    return productds;
}

public  ArrayList<Product>Search_About_Product(String PName){
    SQLiteDatabase db=this.getReadableDatabase();
    ArrayList<Product> productds=new ArrayList<>();
    Cursor cursor = db.rawQuery("select * from Products where ProName like '"+PName+"%'", null);
    cursor.moveToFirst();
    while (cursor.isAfterLast()==false){
        productds.add(new Product(cursor.getString(cursor.getColumnIndex("ProName")), Integer.parseInt(cursor.getString(cursor.getColumnIndex("Price"))),Integer.parseInt(cursor.getString(cursor.getColumnIndex("Quantity")))));
        cursor.moveToNext();
    }
    return productds;


}
public void Add_New_Product(Integer id,String name,Integer price,Integer quantity,Integer catID ){
    SQLiteDatabase db=this.getWritableDatabase();
    ContentValues contentValues=new ContentValues();
    contentValues.put("ProID",id);
    contentValues.put("ProName",name);
    contentValues.put("Price",price);
    contentValues.put("Quantity",quantity);
    contentValues.put("CatID",catID);
    db.insert("Products",null,contentValues);
}
public List<String> Get_All_Products(){
    SQLiteDatabase db=this.getReadableDatabase();
    List<String> productds=new ArrayList<>();
    Cursor cursor = db.rawQuery("select * from Products", null);
    cursor.moveToFirst();
    while (cursor.isAfterLast()==false){
        productds.add(cursor.getString(cursor.getColumnIndex("ProName"))+"\n"+cursor.getString(cursor.getColumnIndex("CatID")));
        cursor.moveToNext();
    }
    return productds;
    }
public void Add_New_Category(Integer id,String name){
 //   Categories
   SQLiteDatabase db=this.getWritableDatabase();
    ContentValues contentValues=new ContentValues();
    contentValues.put("CatID",id);
    contentValues.put("CatName",name);
    db.insert("Categories",null,contentValues);
}
public  List<String> Get_All_Categories (){
    SQLiteDatabase db=this.getReadableDatabase();
   List<String> cats=new ArrayList<>();
    Cursor cursor = db.rawQuery("select * from Categories", null);
    cursor.moveToFirst();
    while (cursor.isAfterLast()==false){
        cats.add(cursor.getString(cursor.getColumnIndex("CatName")));
        cursor.moveToNext();
    }
    return cats;
}
public  void Add_New_Order(String Order_Date,Integer username,String Address){

    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues contentValues = new ContentValues();
    contentValues.put("OrderDate", Order_Date);
    contentValues.put("CustID", username);
    contentValues.put("Address", Address);

    db.insert("Orders", null, contentValues);
}

    public List<String> getAllOrders() {
        List<String> Orders = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Orders", null);
        cursor.moveToFirst();
        while (cursor.isAfterLast()==false){
            Orders.add(cursor.getString(cursor.getColumnIndex("Address")));
            cursor.moveToNext();
        }
        return Orders;
    }

    public void Add_NEW_Customer(String name,String username,String Password,String gender,String birthdate ,String job,String Question){
try {


    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues contentValues = new ContentValues();
    contentValues.put("CustName", name);
    contentValues.put("UserName", username);
    contentValues.put("PassWord", Password);
    contentValues.put("Gender", gender);
    contentValues.put("Job", job);
    contentValues.put("BirthDate", birthdate);
    contentValues.put("Question",Question);
    db.insert("Customers", null, contentValues);
}catch (Exception ex){
    Log.i(TAG, "Add_NEW_Customer: "+ex.getMessage().toString());
}
}
public String get_Answer(Integer UserName ,String answer){

    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery("select Question from Customers where UserName='"+UserName.toString()+"'", null);
    cursor.moveToFirst();
    return cursor.getString(0);

}

public  String GetPAssword(String username){
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery("select PassWord from Customers where UserName='"+username+"'", null);
    cursor.moveToFirst();
   return cursor.getString(0);
}
public boolean checkCustomerLogin(String username,String password){
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.rawQuery("select * from Customers where UserName='"+username+"' and PassWord='"+password+"'", null);
    cursor.moveToFirst();
  int count=cursor.getCount();
    if(count>0)
        return true;
    else
        return false;
   }

    public boolean checkCustomerSignUp(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Customers where UserName='"+username+"'", null);
        cursor.moveToFirst();
        int count=cursor.getCount();
        if(count>0)
            return true;
        else
            return false;
    }
    public List<String> getAllCustomers() {
        List<String> Customers = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Customers", null);
        cursor.moveToFirst();
        while (cursor.isAfterLast()==false){
            Customers.add(cursor.getString(cursor.getColumnIndex("UserName")));
            cursor.moveToNext();
        }
        return Customers;
    }

}
