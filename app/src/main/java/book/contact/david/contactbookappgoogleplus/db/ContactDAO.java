package book.contact.david.contactbookappgoogleplus.db;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import book.contact.david.contactbookappgoogleplus.Utils;
import book.contact.david.contactbookappgoogleplus.activities.SignInActivity;
import book.contact.david.contactbookappgoogleplus.model.Contact;

/**
 * Created by TechnoA on 14.06.2017.
 */

public class ContactDAO extends ContactDBDAO {

    private static final String WHERE_ID_EQUALS = DataBaseHelper.CONTACT_ID_COLUMN
            + " =?";

    public ContactDAO(Context context) {
        super(context);
    }

    public long save(Contact contact) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.CONTACT_FIRST_NAME_COLUMN, contact.getFirstName());
        values.put(DataBaseHelper.CONTACT_LAST_NAME_COLUMN, contact.getLastName());
        values.put(DataBaseHelper.CONTACT_USER_ID_COLUMN, contact.getUserId());

        return database.insert(DataBaseHelper.CONTACT_TABLE, null, values);
    }

    public long update(Contact contact) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.CONTACT_FIRST_NAME_COLUMN, contact.getFirstName());
        values.put(DataBaseHelper.CONTACT_LAST_NAME_COLUMN, contact.getLastName());
        values.put(DataBaseHelper.CONTACT_USER_ID_COLUMN, contact.getUserId());

        long result = database.update(DataBaseHelper.CONTACT_TABLE, values,
                WHERE_ID_EQUALS,
                new String[] { String.valueOf(contact.getId()) });
        Utils.logInfo("Update Result=" + result);
        return result;

    }

    public int deleteContact(Contact contact) {
        return database.delete(DataBaseHelper.CONTACT_TABLE,
                WHERE_ID_EQUALS, new String[] { contact.getId() + "" });
    }

    public ArrayList<Contact> getContacts() {
        ArrayList<Contact> contacts = new ArrayList<Contact>();
        Cursor cursor = database.query(DataBaseHelper.CONTACT_TABLE,
                new String[] { DataBaseHelper.CONTACT_ID_COLUMN,
                        DataBaseHelper.CONTACT_FIRST_NAME_COLUMN,
                        DataBaseHelper.CONTACT_LAST_NAME_COLUMN,
                        DataBaseHelper.CONTACT_USER_ID_COLUMN}, DataBaseHelper.CONTACT_USER_ID_COLUMN + " = ?", new String[]{SignInActivity.googleId}, null, null,
                null);

        while (cursor.moveToNext()) {
            Contact contact = new Contact();
            contact.setId(cursor.getInt(0));
            contact.setFirstName(cursor.getString(1));
            contact.setLastName(cursor.getString(2));
            contact.setUserId(cursor.getString(3));
            contacts.add(contact);
        }
        return contacts;
    }

}
