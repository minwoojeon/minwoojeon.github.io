package framework.sql;

import android.content.Context;
/**
 * Created by junminwoo on 2016-06-09.
 */
public class SQLManager {
    private static SQLManager db_instance;
    private SQL_helper db;

    public static SQLManager getDB_instance(){
        if (db_instance ==null) db_instance = new SQLManager();
        return db_instance;
    }
    public void setDB_ite(Context context){
        db = new SQL_helper(context, "rank.db", null, 1);
    }
    public SQL_helper getDB_ite(){
        return db;
    }

}
