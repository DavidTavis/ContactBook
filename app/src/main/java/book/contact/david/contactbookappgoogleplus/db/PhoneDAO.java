package book.contact.david.contactbookappgoogleplus.db;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;

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


    public PhoneDAO(Context context) {
        super(context);
    }

    public long save(Phone phone) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.PHONE_NUMBER_COLUMN, phone.getNumber());
        values.put(DataBaseHelper.PHONE_CONTACT_ID_COLUMN, phone.getContact().getId());

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

    // METHOD 1
    // Uses rawQuery() to query multiple tables
    public ArrayList<Phone> getPhones() {
        ArrayList<Phone> phones = new ArrayList<Phone>();
//        String query = "SELECT " + EMPLOYEE_ID_WITH_PREFIX + ","
//                + EMPLOYEE_NAME_WITH_PREFIX + "," + DataBaseHelper.EMPLOYEE_DOB
//                + "," + DataBaseHelper.EMPLOYEE_SALARY + ","
//                + DataBaseHelper.EMPLOYEE_DEPARTMENT_ID + ","
//                + DEPT_NAME_WITH_PREFIX + " FROM "
//                + DataBaseHelper.EMPLOYEE_TABLE + " emp, "
//                + DataBaseHelper.DEPARTMENT_TABLE + " dept WHERE emp."
//                + DataBaseHelper.EMPLOYEE_DEPARTMENT_ID + " = dept."
//                + DataBaseHelper.ID_COLUMN;
//
//        // Building query using INNER JOIN keyword
//		/*String query = "SELECT " + EMPLOYEE_ID_WITH_PREFIX + ","
//		+ EMPLOYEE_NAME_WITH_PREFIX + "," + DataBaseHelper.EMPLOYEE_DOB
//		+ "," + DataBaseHelper.EMPLOYEE_SALARY + ","
//		+ DataBaseHelper.EMPLOYEE_DEPARTMENT_ID + ","
//		+ DEPT_NAME_WITH_PREFIX + " FROM "
//		+ DataBaseHelper.EMPLOYEE_TABLE + " emp INNER JOIN "
//		+ DataBaseHelper.DEPARTMENT_TABLE + " dept ON emp."
//		+ DataBaseHelper.EMPLOYEE_DEPARTMENT_ID + " = dept."
//		+ DataBaseHelper.ID_COLUMN;*/
//
//        Log.d("query", query);
//        Cursor cursor = database.rawQuery(query, null);
//        while (cursor.moveToNext()) {
//            Employee employee = new Employee();
//            employee.setId(cursor.getInt(0));
//            employee.setName(cursor.getString(1));
//            try {
//                employee.setDateOfBirth(formatter.parse(cursor.getString(2)));
//            } catch (ParseException e) {
//                employee.setDateOfBirth(null);
//            }
//            employee.setSalary(cursor.getDouble(3));
//
//            Department department = new Department();
//            department.setId(cursor.getInt(4));
//            department.setName(cursor.getString(5));
//
//            employee.setDepartment(department);
//
//            employees.add(employee);
//        }
        return phones;
    }

    // METHOD 2
    // Uses SQLiteQueryBuilder to query multiple tables
	/*public ArrayList<Employee> getEmployees() {
		ArrayList<Employee> employees = new ArrayList<Employee>();
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		queryBuilder
				.setTables(DataBaseHelper.EMPLOYEE_TABLE
						+ " INNER JOIN "
						+ DataBaseHelper.DEPARTMENT_TABLE
						+ " ON "
						+ DataBaseHelper.EMPLOYEE_DEPARTMENT_ID
						+ " = "
						+ (DataBaseHelper.DEPARTMENT_TABLE + "." + DataBaseHelper.ID_COLUMN));

		// Get cursor
		Cursor cursor = queryBuilder.query(database, new String[] {
				EMPLOYEE_ID_WITH_PREFIX,
				DataBaseHelper.EMPLOYEE_TABLE + "."
						+ DataBaseHelper.NAME_COLUMN,
				DataBaseHelper.EMPLOYEE_DOB,
				DataBaseHelper.EMPLOYEE_SALARY,
				DataBaseHelper.EMPLOYEE_DEPARTMENT_ID,
				DataBaseHelper.DEPARTMENT_TABLE + "."
						+ DataBaseHelper.NAME_COLUMN }, null, null, null, null,
				null);

		while (cursor.moveToNext()) {
			Employee employee = new Employee();
			employee.setId(cursor.getInt(0));
			employee.setName(cursor.getString(1));
			try {
				employee.setDateOfBirth(formatter.parse(cursor.getString(2)));
			} catch (ParseException e) {
				employee.setDateOfBirth(null);
			}
			employee.setSalary(cursor.getDouble(3));

			Department department = new Department();
			department.setId(cursor.getInt(4));
			department.setName(cursor.getString(5));

			employee.setDepartment(department);

			employees.add(employee);
		}
		return employees;
	}*/
}