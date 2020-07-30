package Database;

//extends sqlite
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.chakradhar.weatherapp.FinalValues;

public class WeatherDBHandler extends SQLiteOpenHelper {


    public WeatherDBHandler(Context context) {
        super(context, FinalValues.DB_PERU, null, FinalValues.DB_VERS);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        String query = "CREATE TABLE " + FinalValues.ABLE_PERU + " (" + FinalValues.OORUID +
                 " TEXT," + FinalValues.MATTER + " TEXT)";
        db.execSQL(query);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int i,int il){
        String sql = "DROP TABLE "+FinalValues.ABLE_PERU +";";
        db.execSQL(sql);
        onCreate(db);
    }



}
