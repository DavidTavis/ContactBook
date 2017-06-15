package book.contact.david.contactbookappgoogleplus.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import book.contact.david.contactbookappgoogleplus.R;
import book.contact.david.contactbookappgoogleplus.model.Contact;
import book.contact.david.contactbookappgoogleplus.model.Phone;

/**
 * Created by TechnoA on 15.06.2017.
 */

public class PhoneListAdapter extends ArrayAdapter<Phone> {

    private Context context;
    List<Phone> phones;

    public PhoneListAdapter(Context context, List<Phone> phones) {
        super(context, R.layout.list_phone_item, phones);
        this.context = context;
        this.phones = phones;
    }

    private class ViewHolder {
        TextView numberTxt;
    }

    @Override
    public int getCount() {
        return phones.size();
    }

    @Override
    public Phone getItem(int position) {
        return phones.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        PhoneListAdapter.ViewHolder holder = null;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_phone_item, null);

            holder = new PhoneListAdapter.ViewHolder();

            holder.numberTxt = (TextView) convertView.findViewById(R.id.txtNumber);

            convertView.setTag(holder);
        } else {
            holder = (PhoneListAdapter.ViewHolder) convertView.getTag();
        }

        Phone phone = (Phone) getItem(position);

        holder.numberTxt.setText(phone.getNumber());

        return convertView;
    }

    @Override
    public void add(Phone phone) {
        phones.add(phone);
        notifyDataSetChanged();
        super.add(phone);
    }

    @Override
    public void remove(Phone phone) {
        phones.remove(phone);
        notifyDataSetChanged();
        super.remove(phone);
    }
}