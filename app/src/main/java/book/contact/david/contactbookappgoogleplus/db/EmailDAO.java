package book.contact.david.contactbookappgoogleplus.db;

import android.content.ContentValues;
import android.content.Context;

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


    public EmailDAO(Context context) {
        super(context);
    }

    public long save(Email email) {
        ContentValues values = new ContentValues();
        values.put(DataBaseHelper.EMAIL_EMAIL_COLUMN, email.getEmail());
        values.put(DataBaseHelper.EMAIL_CONTACT_ID_COLUMN, email.getContact().getId());

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

    // METHOD 1
    // Uses rawQuery() to query multiple tables
    public ArrayList<Email> getEmails() {
        ArrayList<Email> emails = new ArrayList<Email>();
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
        return emails;
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
