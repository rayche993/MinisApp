package rs.ac.uns.pmf.dmi.minisapp.activity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by android on 31.8.16..
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper {
    final static String TABLE_NAME = "journals";
    final static String JOURNAL_ID = "journal_id";
    final static String NUMBER_OF_PAPAER_JOURNALS = "number_of_paper_journals";
    final static String _ID = "_id";
    final static String[] columns = { _ID, JOURNAL_ID, NUMBER_OF_PAPAER_JOURNALS };

    final private static String CREATE_CMD =

            "CREATE TABLE artists (" + _ID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + JOURNAL_ID + " TEXT NOT NULL, "
                    + NUMBER_OF_PAPAER_JOURNALS + " INT NOT NULL)";

    final private static String NAME = "journals_db";
    final private static Integer VERSION = 1;
    final private Context mContext;

    public DatabaseOpenHelper(Context context) {
        super(context, NAME, null, VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CMD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // N/A
    }

    void deleteDatabase() {
        mContext.deleteDatabase(NAME);
    }
}
