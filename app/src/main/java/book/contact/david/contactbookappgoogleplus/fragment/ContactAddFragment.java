package book.contact.david.contactbookappgoogleplus.fragment;

import java.lang.ref.WeakReference;
import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import book.contact.david.contactbookappgoogleplus.R;
import book.contact.david.contactbookappgoogleplus.activities.SignInActivity;
import book.contact.david.contactbookappgoogleplus.db.ContactDAO;
import book.contact.david.contactbookappgoogleplus.db.EmailDAO;
import book.contact.david.contactbookappgoogleplus.db.PhoneDAO;
import book.contact.david.contactbookappgoogleplus.model.Contact;
import book.contact.david.contactbookappgoogleplus.model.Email;
import book.contact.david.contactbookappgoogleplus.model.Phone;

/**
 * Created by TechnoA on 14.06.2017.
 */

public class ContactAddFragment extends Fragment implements OnClickListener {

    // UI references
    private EditText firstName;
    private EditText lastName;
    private Button addButton;
    private Button resetButton;


    Contact contact = null;
    Phone phone = null;
    Email email = null;

    private PhoneDAO phoneDAO;
    private EmailDAO emailDAO;
    private ContactDAO contactDAO;
    private AddContactTask addConactTask;

    public static final String ARG_ITEM_ID = "contact_add_fragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        phoneDAO = new PhoneDAO(getActivity());
        emailDAO = new EmailDAO(getActivity());
        contactDAO = new ContactDAO(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_contacts, container,
                false);

        findViewsById(rootView);

        setListeners();

        return rootView;
    }

    private void setListeners() {
        addButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);
    }

    protected void resetAllFields() {
        firstName.setText("");
        lastName.setText("");
    }

    private void setContact() {
        contact = new Contact();
        contact.setFirstName(firstName.getText().toString());
        contact.setLastName(lastName.getText().toString());
        contact.setUserId(SignInActivity.googleId);
    }

    @Override
    public void onResume() {
        getActivity().setTitle(R.string.add_contact);
        android.app.ActionBar actionBar = getActivity().getActionBar();
        if(actionBar!=null) {
            actionBar.setTitle(R.string.add_contact);
        }
        super.onResume();
    }

    private void findViewsById(View rootView) {
        firstName = (EditText) rootView.findViewById(R.id.etxt_first_name);
        lastName = (EditText) rootView.findViewById(R.id.etxt_last_name);
        addButton = (Button) rootView.findViewById(R.id.button_add);
        resetButton = (Button) rootView.findViewById(R.id.button_reset);
    }

    @Override
    public void onClick(View view) {
        if (view == addButton) {
            setContact();
            addConactTask = new AddContactTask(getActivity());
            addConactTask.execute((Void) null);
        } else if (view == resetButton) {
            resetAllFields();
        }
    }

    public class AddContactTask extends AsyncTask<Void, Void, Long> {

        private final WeakReference<Activity> activityWeakRef;

        public AddContactTask(Activity context) {
            this.activityWeakRef = new WeakReference<Activity>(context);
        }

        @Override
        protected Long doInBackground(Void... arg0) {
            long result = contactDAO.save(contact);
            return result;
        }

        @Override
        protected void onPostExecute(Long result) {
            if (activityWeakRef.get() != null
                    && !activityWeakRef.get().isFinishing()) {
                if (result != -1)
                    Toast.makeText(activityWeakRef.get(), "Contact Saved",
                            Toast.LENGTH_LONG).show();
            }
        }
    }
}