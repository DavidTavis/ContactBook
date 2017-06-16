package book.contact.david.contactbookappgoogleplus.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import book.contact.david.contactbookappgoogleplus.R;
import book.contact.david.contactbookappgoogleplus.model.Email;

/**
 * Created by TechnoA on 16.06.2017.
 */

public class EmailListAdapter extends ArrayAdapter<Email> {

    private Context context;
    List<Email> emails;

    public EmailListAdapter(Context context, List<Email> emails) {
        super(context, R.layout.list_email_item, emails);
        this.context = context;
        this.emails = emails;
    }

    private class ViewHolder {
        TextView emailTxt;
    }

    @Override
    public int getCount() {
        return emails.size();
    }

    @Override
    public Email getItem(int position) {
        return emails.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        EmailListAdapter.ViewHolder holder = null;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_email_item, null);

            holder = new EmailListAdapter.ViewHolder();

            holder.emailTxt = (TextView) convertView.findViewById(R.id.txtEmail);

            convertView.setTag(holder);
        } else {
            holder = (EmailListAdapter.ViewHolder) convertView.getTag();
        }

        Email email = (Email) getItem(position);

        holder.emailTxt.setText(email.getEmail());

        return convertView;
    }

    @Override
    public void add(Email email) {
        emails.add(email);
        notifyDataSetChanged();
        super.add(email);
    }

    @Override
    public void remove(Email email) {
        emails.remove(email);
        notifyDataSetChanged();
        super.remove(email);
    }
}