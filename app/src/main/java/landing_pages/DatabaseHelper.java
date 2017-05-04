package landing_pages;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by thaso on 4/23/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Books.db";
    public static final String TABLE_NAME = "Book_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "ISBN";
    public static final String COL_3 = "TITLE";
    public static final String COL_4 = "AUTHOR";
    public static final String COL_5 = "STUDENT_NUMBER";
    public static final String COL_6 = "DESCRIPTION";
    public static final String COL_7 = "COUNT";
    public static final String COL_8 = "IMAGE";
    public static final String COL_9 = "EDITION";
    public static final String COL_10 = "DATE_RESERVED";
    public static final String COL_11 = "DATE_TO_RETURN";






    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        //SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,ISBN TEXT,TITLE TEXT,AUTHOR TEXT,STUDENT_NUMBER TEXT,DESCRIPTION TEXT,COUNT INTEGER,IMAGE TEXT,EDITION TEXT,DATE_RESERVED TEXT,DATE_TO_RETURN TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String isbn,String title,String author, String studentNumber,String description,String count, String image, String edition, String reserveDate, String returnDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,isbn);
        contentValues.put(COL_3,title);
        contentValues.put(COL_4,author);
        contentValues.put(COL_5,studentNumber);
        contentValues.put(COL_6,description);
        contentValues.put(COL_7,count);
        contentValues.put(COL_8,image);
        contentValues.put(COL_9,edition);
        contentValues.put(COL_10,reserveDate);
        contentValues.put(COL_11,returnDate);
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }


    public Cursor getAllData(String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        //rawQuery("SELECT id, name FROM people WHERE name = ? AND id = ?", new String[] {"David", "2"});
        Cursor res = db.rawQuery("select * from "+tableName,null);
        return res;
    }
    public Cursor getDataWithParam(String tableName, String param, String value)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        //String areaTyp = "SELECT " +AREA_TYPE + "  FROM "+ AREA_TYPE_TABLE + " where `" + TYPE + "`="+ id;
        String query = "SELECT * FROM "+tableName+" WHERE "+param+" ='"+value +"'";
        Cursor res = db.rawQuery(query,  null);
        return res;
    }
    public boolean updateData(String id, String isbn,String title,String author, String STUDENT_NUMBER,String description,String count, String image,String edition, String reserveDate, String returnDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,isbn);
        contentValues.put(COL_3,title);
        contentValues.put(COL_4,author);
        contentValues.put(COL_5,STUDENT_NUMBER);
        contentValues.put(COL_6,description);
        contentValues.put(COL_7,count);
        contentValues.put(COL_8,image);
        contentValues.put(COL_9,edition);
        contentValues.put(COL_10,reserveDate);
        contentValues.put(COL_11,returnDate);
        db.update(TABLE_NAME, contentValues, "ID = ?",new String[] { id });
        return true;
    }
    //test name
    public Integer deleteData (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ISBN = ?",new String[] {id});
    }
    public Integer deleteRowWithColumnValue(String tableName, String columnName, String value)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(tableName, columnName+" = ?",new String[] {value});
    }
}
