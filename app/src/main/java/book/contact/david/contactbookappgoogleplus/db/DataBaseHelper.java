package book.contact.david.contactbookappgoogleplus.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by TechnoA on 14.06.2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "contacts_db";
    private static final int DATABASE_VERSION = 1;

    //table
    public static final String CONTACT_TABLE = "contact";
    public static final String PHONE_TABLE = "phone";
    public static final String EMAIL_TABLE = "email";

    //column CONTACT
    public static final String CONTACT_ID_COLUMN = "id";
    public static final String CONTACT_FIRST_NAME_COLUMN = "first_name";
    public static final String CONTACT_LAST_NAME_COLUMN = "last_name";
    public static final String CONTACT_USER_ID_COLUMN = "user_id";

    //column PHONE
    public static final String PHONE_ID_COLUMN = "id";
    public static final String PHONE_NUMBER_COLUMN = "number";
    public static final String PHONE_CONTACT_ID_COLUMN = "contact_id";

    //column EMAIL
    public static final String EMAIL_ID_COLUMN = "id";
    public static final String EMAIL_EMAIL_COLUMN = "number";
    public static final String EMAIL_CONTACT_ID_COLUMN = "contact_id";

    //create CONTACT table constant
    public static final String CREATE_CONTACT_TABLE = "CREATE TABLE "
            + CONTACT_TABLE + "(" + CONTACT_ID_COLUMN + " INTEGER PRIMARY KEY, "
            + CONTACT_FIRST_NAME_COLUMN + " TEXT, " + CONTACT_LAST_NAME_COLUMN + " TEXT, "
            + CONTACT_USER_ID_COLUMN + " TEXT" + ")";

    //create PHONE table constant
    public static final String CREATE_PHONE_TABLE = "CREATE TABLE "
            + PHONE_TABLE + "(" + PHONE_ID_COLUMN + " INTEGER PRIMARY KEY,"
            + PHONE_NUMBER_COLUMN + " TEXT, "
            + "FOREIGN KEY(" + PHONE_CONTACT_ID_COLUMN + ") REFERENCES "
            + CONTACT_TABLE + "(user_id) " + ")";

    //create EMAIL table constant
    public static final String CREATE_EMAIL_TABLE = "CREATE TABLE "
            + EMAIL_TABLE + "(" + EMAIL_ID_COLUMN + " INTEGER PRIMARY KEY,"
            + EMAIL_EMAIL_COLUMN + " TEXT, "
            + "FOREIGN KEY(" + EMAIL_CONTACT_ID_COLUMN + ") REFERENCES "
            + CONTACT_TABLE + "(user_id) " + ")";

    private static DataBaseHelper instance;

    public static synchronized DataBaseHelper getHelper(Context context) {
        if (instance == null)
            instance = new DataBaseHelper(context);
        return instance;
    }

    private DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CONTACT_TABLE);
//        db.execSQL(CREATE_PHONE_TABLE);
//        db.execSQL(CREATE_EMAIL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}