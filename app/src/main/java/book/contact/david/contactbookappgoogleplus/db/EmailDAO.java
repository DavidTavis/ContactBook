package book.contact.david.contactbookappgoogleplus.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;

import java.util.ArrayList;

import book.contact.david.contactbookappgoogleplus.Utils;
import book.contact.david.contactbookappgoogleplus.model.Email;
import book.contact.david.contactbookappgoogleplus.model.Phone;

/**
 * Created by TechnoA on 14.06.2017.
 */

public class EmailDAO extends ContactDBDAO{

    public static final String EMPLOYEE_ID_WITH_PREFIX = "phone.id";
    public static final String EMPLOYEE_NAME_WITH_PREFIX = "phone.name";
    public static final String DEPT_NAME_WITH_PREFIX = "cont.first_name";

    private static final String WHERE_ID_EQUALS = DataBaseHelper.EMAIL_ID_COLUMN + " =?";
    private static final String WHERE_CONTACT_ID_EQUALS = DataBaseHelper.EMAIL_CONTACT_ID_COLUMN + " =?";

    SharedPreferences pref;

    public EmailDAO(Context context) {
        super(context);
        pref = context.getSharedPreferences("myContactPref", Context.MODE_PRIVATE);
    }

    public long save(Email email) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.EMAIL_EMAIL_COLUMN, email.getEmail());
        String contactId = Integer.toString(pref.getInt("contactId",0));
        values.put(DataBaseHelper.EMAIL_CONTACT_ID_COLUMN, contactId);

        return database.insert(DataBaseHelper.EMAIL_TABLE, null, values);
    }

    public long update(Email email) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.EMAIL_EMAIL_COLUMN, email.getEmail());
        values.put(DataBaseHelper.EMAIL_CONTACT_ID_COLUMN, email.getContact().getId());

        long result = database.update(DataBaseHelper.EMAIL_TABLE, values,
                WHERE_ID_EQUALS,
                new String[] { String.valueOf(email.getId()) });
        Utils.logInfo("Update Result: " + result);
        return result;
    }

    public int deleteEmail(Email email) {
        return database.delete(DataBaseHelper.EMAIL_TABLE, WHERE_ID_EQUALS,
                new String[] { email.getId() + "" });
    }

    public int deleteEmail(String contactId) {
        return database.delete(DataBaseHelper.EMAIL_TABLE, WHERE_CONTACT_ID_EQUALS,
                new String[] { contactId });
    }

    public ArrayList<Email> getEmails() {

        ArrayList<Email> emails = new ArrayList<Email>();

        String contactId = Integer.toString(pref.getInt("contactId",0));

        Cursor cursor = database.query(DataBaseHelper.EMAIL_TABLE,
                new String[] { DataBaseHelper.EMAIL_ID_COLUMN,
                        DataBaseHelper.EMAIL_EMAIL_COLUMN,
                        DataBaseHelper.EMAIL_CONTACT_ID_COLUMN}, DataBaseHelper.EMAIL_CONTACT_ID_COLUMN + " = ?", new String[]{contactId}, null, null,
                null);

        while (cursor.moveToNext()) {

            Email email = new Email();
            email.setId(cursor.getInt(0));
            email.setEmail(cursor.getString(1));
            email.setContactId(cursor.getString(2));
            emails.add(email);

        }

        return emails;
    }

}
