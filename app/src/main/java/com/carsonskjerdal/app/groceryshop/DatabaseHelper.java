package com.carsonskjerdal.app.groceryshop;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Info
    private static final String DATABASE_NAME = "groceryDatabase";
    private static final int DATABASE_VERSION = 18;

    // Table Names
    private static final String TABLE_GROCERIES = "groceries";
    private static final String TABLE_PRODUCTS = "products";
    private static final String TABLE_CART = "cart";

    // Grocery Table Columns
    private static final String KEY_GROCERIES_ID = "id";
    private static final String KEY_GROCERIES_NAME = "groceryName";
    private static final String KEY_GROCERIES_IMAGE = "groceryImage";

    // User Table Columns
    private static final String KEY_PRODUCTS_ID = "id";
    private static final String KEY_PRODUCTS_NAME = "productsName";
    private static final String KEY_PRODUCTS_IMAGE = "productsImage";
    private static final String KEY_PRODUCTS_GROUP = "productsGroup";
    private static final String KEY_PRODUCTS_PRICE = "productsPrice";

    // Cart Table Columns
    private static final String KEY_CART_ID = "id";
    private static final String KEY_CART_NAME = "cartName";
    private static final String KEY_CART_IMAGE = "cartImage";
    private static final String KEY_CART_PRICE = "cartPrice";
    private static final String KEY_CART_QUANTITY = "cartQuantity";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    // Called when the database is created for the FIRST time.
    // If a database already exists on disk with the same DATABASE_NAME, this method will NOT be called.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_POSTS_TABLE = "CREATE TABLE " + TABLE_GROCERIES +
                "(" +
                KEY_GROCERIES_ID + " INTEGER PRIMARY KEY," + // Define a primary key
                KEY_GROCERIES_NAME + " TEXT, " +
                KEY_GROCERIES_IMAGE + " TEXT" +
                ")";

        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_PRODUCTS +
                "(" +
                KEY_PRODUCTS_ID + " INTEGER PRIMARY KEY," +
                KEY_PRODUCTS_NAME + " TEXT," +
                KEY_PRODUCTS_IMAGE + " TEXT," +
                KEY_PRODUCTS_GROUP + " TEXT," +
                KEY_PRODUCTS_PRICE + " TEXT" +
                ")";

        String CREATE_CART_TABLE = "CREATE TABLE " + TABLE_CART +
                "(" +
                KEY_CART_ID + " INTEGER PRIMARY KEY," +
                KEY_CART_NAME + " TEXT," +
                KEY_CART_IMAGE + " TEXT," +
                KEY_CART_PRICE + " TEXT," +
                KEY_CART_QUANTITY + " TEXT" +
                ")";

        db.execSQL(CREATE_POSTS_TABLE);
        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_CART_TABLE);

        addTableData(db);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void addTableData(SQLiteDatabase db) {
        ContentValues values = new ContentValues();

        //add Grocery data to its table

        String[][] data = {{"Cá", ""}, {"Bánh mì", ""}, {"Sữa", ""}, {"Táo", ""},
                {"Cam", ""}, {"Kẹo", ""}, {"Gia vị", ""},
                {"Nước uống", ""}, {"Thịt bò", ""}, {"Rau", ""}};

        for (String[] aData : data) {
            values.put(KEY_GROCERIES_NAME, aData[0]);
            values.put(KEY_GROCERIES_IMAGE, aData[1]);

            db.insert(TABLE_GROCERIES, null, values);
        }

        //add Product data to its table
        ContentValues values2 = new ContentValues();

        String[][] data2 = {{"Cá thu", "0", "11000", "Cá"}, {"Cá basa", "0", "15000", "Cá"}, {"Cá hồi", "", "20000", "Cá"}, {"Bánh mì lạt", "0", "11000", "Bánh mì"}
                , {"Bánh mì que", "0", "6000", "Bánh mì"}, {"Vinamilk", "0", "4000", "Sữa"}, {"Cô gái Hà Lan", "0", "5000", "Sữa"}
                , {"Dalat milk", "0", "7000", "Sữa"}, {"Táo đỏ", "0", "11000", "Táo"}, {"Táo xanh", "0", "12000", "Táo"}, {"Táo Nhập", "0", "15000", "Táo"}
                , {"Cam Việt Nam", "0", "3000", "Cam"}, {"Cam nhập", "0", "7000", "Cam"}, {"Milkita", "0", "2500", "Kẹo"}
                , {"Kẹo cà phê", "0", "2500", "Kẹo"}, {"Kẹo bạc hà", "0", "1500", "Kẹo"}, {"Nước tương", "0", "18000", "Gia vị"}
                , {"Nước mắm", "0", "19000", "Gia vị"}, {"Tiêu", "0", "4000", "Gia vị"}, {"Coca", "0", "7500", "Nước uống"}, {"Pepsi", "0", "8000", "Nước uống"}
                , {"Xá xị", "0", "6000", "Nước uống"}, {"Bò Việt Nam", "0", "25000", "Thịt bò"}, {"Bò Mỹ", "0", "45000", "Thịt bò"}
                , {"Rau cải", "0", "11000", "Rau"}, {"Rau muống", "0", "12000", "Rau"}, {"Mồng tơi", "0", "11500", "Rau"}};

        for (String[] aData : data2) {
            values2.put(KEY_PRODUCTS_NAME, aData[0]);
            values2.put(KEY_PRODUCTS_IMAGE, aData[1]);
            values2.put(KEY_PRODUCTS_PRICE, aData[2]);
            values2.put(KEY_PRODUCTS_GROUP, aData[3]);

            db.insert(TABLE_PRODUCTS, null, values2);
        }
    }
}
