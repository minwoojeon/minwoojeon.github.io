package framework.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by junminwoo on 2016-06-08.
 */
public class SQL_helper extends SQLiteOpenHelper{
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table g_rank(name char(20) not null, score integer);");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public SQL_helper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
}
