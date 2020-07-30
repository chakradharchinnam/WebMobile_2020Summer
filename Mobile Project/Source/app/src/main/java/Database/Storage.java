package Database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.chakradhar.weatherapp.FinalValues;

//creates database

public class Storage {
    private static WeatherDBHandler dbHelper;
    private SQLiteDatabase db;

    public Storage(Context context) {
        dbHelper = new WeatherDBHandler(context);
        this.db = dbHelper.getWritableDatabase();
        //this.db = dbHelper.getReadableDatabase();
    }

    public void InsertWeather(WeatherDB w){
       String first =  w.getLatlon();
       String second = w.getJSON();

        ContentValues values = new ContentValues();
        values.put(FinalValues.OORUID, first);
        values.put(FinalValues.MATTER, second);
        db.insert(FinalValues.ABLE_PERU, null, values);
    }

    public int countAllStoredLocations(){
        int total = 0;
        String query = "SELECT * FROM"+FinalValues.ABLE_PERU +";";
        Cursor cursor = this.getDbConnection().rawQuery(query, null);
        if(cursor.moveToFirst()){
            total = cursor.getCount();
        }
        cursor.close();
        return total;
    }
    public String getJSONfromDB(String latlon){
        String query = "SELECT DATA FROM"+FinalValues.ABLE_PERU +" WHERE CITYID="+latlon+";";
        Cursor cursor = this.getDbConnection().rawQuery(query, null);
        String results;
        if(cursor.moveToFirst()){
            do{



                    int id = cursor.getInt(0);
                    System.out.println("Response number " + id);
                    String storedData = cursor.getString(cursor.getColumnIndexOrThrow(FinalValues.MATTER));
                    System.out.println("Response number " + storedData);
                    results=storedData;
            }while (cursor.moveToNext());
            return results;
        }else{
            return null;
        }

    }
    public SQLiteDatabase getDbConnection(){
        return this.db;
    }
    public void closeDbConnection(){
        if(this.db != null){
            this.db.close();
        }
    }


}
