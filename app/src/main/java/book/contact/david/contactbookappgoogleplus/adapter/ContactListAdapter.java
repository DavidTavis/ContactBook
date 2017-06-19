package book.contact.david.contactbookappgoogleplus.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import book.contact.david.contactbookappgoogleplus.R;
import book.contact.david.contactbookappgoogleplus.Utils;
import book.contact.david.contactbookappgoogleplus.activities.EmailActivity;
import book.contact.david.contactbookappgoogleplus.activities.PhoneActivity;
import book.contact.david.contactbookappgoogleplus.model.Contact;

/**
 * Created by TechnoA on 14.06.2017.
 */

public class ContactListAdapter extends ArrayAdapter<Contact> {

    private Context context;
    List<Contact> contacts;
    SharedPreferences.Editor editor;

    public ContactListAdapter(Context context, List<Contact> contacts) {
        super(context, R.layout.list_contact_item, contacts);
        this.context = context;
        this.contacts = contacts;
        Utils.logInfo("Constructor ContactListAdapter");
        editor = getContext().getSharedPreferences("myContactPref",Context.MODE_PRIVATE).edit();
    }

    private class ViewHolder {
        TextView firstNameTxt;
        TextView lastNameTxt;
        ImageButton addPhoneBtn;
        ImageButton addEmailBtn;
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

        Utils.logInfo("position="+ position);
        final Contact contact = (Contact) getItem(position);

        ViewHolder holder = null;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_contact_item, null);

            holder = new ViewHolder();

            holder.firstNameTxt = (TextView) convertView.findViewById(R.id.txt_first_name);
            holder.lastNameTxt = (TextView) convertView.findViewById(R.id.txt_last_name);
            holder.addPhoneBtn = (ImageButton) convertView.findViewById(R.id.button_add_number);
            holder.addEmailBtn = (ImageButton) convertView.findViewById(R.id.button_add_email);

            holder.addPhoneBtn.setTag(position);
            holder.addEmailBtn.setTag(position);

            holder.addPhoneBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    int position = (Integer) v.getTag();
                    Contact currentContact = getItem(position);

                    editor.putInt("contactId",currentContact.getId()).commit();
                    editor.putString("contactFirstName",currentContact.getFirstName()).commit();
                    editor.putString("contactLastName",currentContact.getLastName()).commit();

                    Intent intent = new Intent(context, PhoneActivity.class);
                    context.startActivity(intent);

                }
            });

            holder.addEmailBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    int position = (Integer) v.getTag();
                    Contact currentContact = getItem(position);

                    editor.putInt("contactId",currentContact.getId()).commit();
                    editor.putString("contactFirstName",currentContact.getFirstName()).commit();
                    editor.putString("contactLastName",currentContact.getLastName()).commit();

                    Intent intent = new Intent(context, EmailActivity.class);
                    context.startActivity(intent);

                }
            });

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }




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