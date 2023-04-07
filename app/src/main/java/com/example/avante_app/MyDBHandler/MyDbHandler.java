package com.example.avante_app.MyDBHandler;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.avante_app.DataModels.Datum;
import com.example.avante_app.Params.Params;

import java.util.ArrayList;
import java.util.List;


public class MyDbHandler extends SQLiteOpenHelper {

    public MyDbHandler(Context context){
        super(context, Params.DB_NAME,null, Integer.parseInt(Params.DB_VERSION));
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create= "CREATE TABLE " +Params.TABLE_NAME+"("
                +Params.KEY_ID+" INTEGER PRIMARY KEY,"+Params.KEY_EMAIL+
                " TEXT,"+Params.KEY_FIRSTNAME+" TEXT,"+Params.KEY_LASTNAME+" TEXT,"+Params.KEY_AVATAR+" TEXT"+")";
        sqLiteDatabase.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addContact(Datum datum){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(Params.KEY_ID,datum.getId());
        values.put(Params.KEY_LASTNAME,datum.getLastName());
        values.put(Params.KEY_FIRSTNAME,datum.getFirstName());
        values.put(Params.KEY_EMAIL,datum.getEmail());
        values.put(Params.KEY_AVATAR,datum.getAvatar());

        db.insert(Params.TABLE_NAME,null,values);
        db.close();
    }


    public ArrayList<Datum> getAllContacts() {
        ArrayList<Datum> dataList = new ArrayList<>();
        SQLiteDatabase db =this.getReadableDatabase();

        String select="SELECT * FROM " + Params.TABLE_NAME ;
        Cursor cursor=db.rawQuery(select,null);

        if(cursor.moveToFirst()){
            do{
                Datum datum= new Datum();
                datum.setId(cursor.getInt(0));
                datum.setEmail(cursor.getString(1));
                datum.setFirstName(cursor.getString(2));
                datum.setLastName(cursor.getString(3));
                datum.setAvatar(cursor.getString(4));
                dataList.add(datum);
            }while (cursor.moveToFirst());
        }
        return  dataList;
    }


    public void allDataDelete(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(Params.TABLE_NAME ,null,null);
        db.close();
    }
}