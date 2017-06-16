package book.contact.david.contactbookappgoogleplus.db;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;

import book.contact.david.contactbookappgoogleplus.Utils;
import book.contact.david.contactbookappgoogleplus.model.Phone;

/**
 * Created by TechnoA on 14.06.2017.
 */

public class PhoneDAO extends ContactDBDAO{

    public static final String EMPLOYEE_ID_WITH_PREFIX = "phone.id";
    public static final String EMPLOYEE_NAME_WITH_PREFIX = "phone.name";
    public static final String DEPT_NAME_WITH_PREFIX = "cont.first_name";

    private static final String WHERE_ID_EQUALS = DataBaseHelper.PHONE_ID_COLUMN + " =?";
    private static final String WHERE_CONTACT_ID_EQUALS = DataBaseHelper.PHONE_CONTACT_ID_COLUMN + " =?";

    SharedPreferences pref;

    public PhoneDAO(Context context) {
        super(context);
        pref = context.getSharedPreferences("myContactPref", Context.MODE_PRIVATE);
    }

    public long save(Phone phone) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.PHONE_NUMBER_COLUMN, phone.getNumber());
        String contactId = Integer.toString(pref.getInt("contactId",0));
        values.put(DataBaseHelper.PHONE_CONTACT_ID_COLUMN, contactId);

        return database.insert(DataBaseHelper.PHONE_TABLE, null, values);
    }

    public long update(Phone phone) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.PHONE_NUMBER_COLUMN, phone.getNumber());
        values.put(DataBaseHelper.PHONE_CONTACT_ID_COLUMN, phone.getContact().getId());

        long result = database.update(DataBaseHelper.PHONE_TABLE, values,
                WHERE_ID_EQUALS,
                new String[] { String.valueOf(phone.getId()) });
        Utils.logInfo("Update Result: " + result);
        return result;
    }

    public int deletePhone(Phone phone) {
        return database.delete(DataBaseHelper.PHONE_TABLE, WHERE_ID_EQUALS,
                new String[] { phone.getId() + "" });
    }

    public int deletePhone(String contactId){
        return database.delete(DataBaseHelper.PHONE_TABLE, WHERE_CONTACT_ID_EQUALS,
                new String[] { contactId });
    }

    public ArrayList<Phone> getPhones() {

        String contactId = Integer.toString(pref.getInt("contactId",0));
        ArrayList<Phone> phones = new ArrayList<Phone>();

        Cursor cursor = database.query(DataBaseHelper.PHONE_TABLE,
                new String[] { DataBaseHelper.PHONE_ID_COLUMN,
                        DataBaseHelper.PHONE_NUMBER_COLUMN,
                        DataBaseHelper.PHONE_CONTACT_ID_COLUMN}, DataBaseHelper.PHONE_CONTACT_ID_COLUMN + " = ?", new String[]{contactId}, null, null,
                null);

        while (cursor.moveToNext()) {

            Phone phone = new Phone();
            phone.setId(cursor.getInt(0));
            phone.setNumber(cursor.getString(1));
            phone.setContactId(cursor.getString(2));
            phones.add(phone);

        }

        return phones;
    }


}