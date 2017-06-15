package book.contact.david.contactbookappgoogleplus.fragment.contact;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;

import book.contact.david.contactbookappgoogleplus.R;
import book.contact.david.contactbookappgoogleplus.Utils;
import book.contact.david.contactbookappgoogleplus.adapter.ContactListAdapter;
import book.contact.david.contactbookappgoogleplus.db.ContactDAO;
import book.contact.david.contactbookappgoogleplus.model.Contact;

/**
 * Created by TechnoA on 14.06.2017.
 */

public class ContactListFragment extends Fragment implements OnItemClickListener,
        OnItemLongClickListener {

    public static final String ARG_ITEM_ID = "contact_list";

    Activity activity;
    ListView contactListView;
    ArrayList<Contact> contacts;

    ContactListAdapter contactListAdapter;
    ContactDAO contactDAO;

    private GetContactTask task;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        contactDAO = new ContactDAO(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contact_list, container, false);

        findViewsById(view);

        task = new GetContactTask(activity);
        task.execute((Void) null);

        contactListView.setOnItemClickListener(this);
        contactListView.setOnItemLongClickListener(this);

        return view;

    }

    private void findViewsById(View view) {
        contactListView = (ListView) view.findViewById(R.id.list_contacts);
    }

    @Override
    public void onItemClick(AdapterView<?> list, View view, int position, long id) {

        Contact contact = (Contact) list.getItemAtPosition(position);

        if (contact != null) {

            Bundle arguments = new Bundle();
            arguments.putParcelable("selectedContact", contact);
            CustomContactDialogFragment customContactDialogFragment = new CustomContactDialogFragment();
            customContactDialogFragment.setArguments(arguments);
            customContactDialogFragment.show(getFragmentManager(), CustomContactDialogFragment.ARG_ITEM_ID);

        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Contact contact = (Contact) parent.getItemAtPosition(position);
        // Use AsyncTask to delete from database
        contactDAO.deleteContact(contact);
        contactListAdapter.remove(contact);

        return true;
    }

    public class GetContactTask extends AsyncTask<Void, Void, ArrayList<Contact>> {

        private final WeakReference<Activity> activityWeakRef;

        public GetContactTask(Activity context) {
            this.activityWeakRef = new WeakReference<Activity>(context);
        }

        @Override
        protected ArrayList<Contact> doInBackground(Void... arg0) {
            ArrayList<Contact> contactList = contactDAO.getContacts();
            return contactList;
        }

        @Override
        protected void onPostExecute(ArrayList<Contact> contactList) {
            if (activityWeakRef.get() != null && !activityWeakRef.get().isFinishing()) {
                contacts = contactList;
                if (contactList != null) {
                    if (contactList.size() != 0) {
                        contactListAdapter = new ContactListAdapter(activity,contactList);
                        contactListView.setAdapter(contactListAdapter);
                    } else {
                        Toast.makeText(activity, "No Contact Records",Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }

    /*
     * This method is invoked from MainActivity onFinishDialog() method. It is
     * called from CustomContactDialogFragment when a contact record is updated.
     * This is used for communicating between fragments.
     */
    public void updateView() {
        task = new GetContactTask(activity);
        task.execute((Void) null);
    }

    @Override
    public void onResume() {
        getActivity().setTitle(R.string.app_name);
        android.app.ActionBar actionBar = getActivity().getActionBar();
        if(actionBar!=null) {
            actionBar.setTitle(R.string.app_name);
        }
        super.onResume();
    }
}