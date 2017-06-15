package book.contact.david.contactbookappgoogleplus.adapter;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import book.contact.david.contactbookappgoogleplus.R;
import book.contact.david.contactbookappgoogleplus.model.Contact;

/**
 * Created by TechnoA on 14.06.2017.
 */

public class ContactListAdapter extends ArrayAdapter<Contact> {

    private Context context;
    List<Contact> contacts;

    public ContactListAdapter(Context context, List<Contact> contacts) {
        super(context, R.layout.list_item, contacts);
        this.context = context;
        this.contacts = contacts;
    }

    private class ViewHolder {
//        TextView idTxt;
        TextView firstNameTxt;
        TextView lastNameTxt;
    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Contact getItem(int position) {
        return contacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null);

            holder = new ViewHolder();

//            holder.idTxt = (TextView) convertView.findViewById(R.id.txt_emp_id);
            holder.firstNameTxt = (TextView) convertView.findViewById(R.id.txt_first_name);
            holder.lastNameTxt = (TextView) convertView.findViewById(R.id.txt_last_name);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Contact contact = (Contact) getItem(position);

//        holder.idTxt.setText(contact.getId() + "");
        holder.firstNameTxt.setText(contact.getFirstName());
        holder.lastNameTxt.setText(contact.getLastName());

        return convertView;
    }

    @Override
    public void add(Contact contact) {
        contacts.add(contact);
        notifyDataSetChanged();
        super.add(contact);
    }

    @Override
    public void remove(Contact contact) {
        contacts.remove(contact);
        notifyDataSetChanged();
        super.remove(contact);
    }
}