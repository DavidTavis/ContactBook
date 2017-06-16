package book.contact.david.contactbookappgoogleplus.fragment.email;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.ref.WeakReference;

import book.contact.david.contactbookappgoogleplus.R;
import book.contact.david.contactbookappgoogleplus.Utils;
import book.contact.david.contactbookappgoogleplus.db.EmailDAO;
import book.contact.david.contactbookappgoogleplus.model.Email;

/**
 * Created by TechnoA on 16.06.2017.
 */

public class EmailAddFragment extends Fragment implements View.OnClickListener {

    // UI references
    private EditText eTEmail;
    private Button addButton;
    private Button resetButton;


    Email email = null;

    private EmailDAO emailDAO;
    private AddEmailTask addEmailTask;

    public static final String ARG_ITEM_ID = "email_add_fragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        emailDAO = new EmailDAO(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_add_email, container, false);

        findViewsById(rootView);

        setListeners();

        return rootView;

    }

    private void setListeners() {
        addButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);
    }

    protected void resetAllFields() {
        eTEmail.setText("");
    }

    private void setEmail() {

        String currentContactId = Integer.toString(getContext().getSharedPreferences("myContactPref", Context.MODE_PRIVATE).getInt("contactId",0));
        Utils.logInfo("currentContactId = " + currentContactId);

        email = new Email();
        email.setEmail(eTEmail.getText().toString());
        email.setContactId(currentContactId);

    }

    @Override
    public void onResume() {

        getActivity().setTitle(R.string.add_email);

        android.app.ActionBar actionBar = getActivity().getActionBar();

        if(actionBar!=null) {
            actionBar.setTitle(R.string.add_email);
        }

        super.onResume();
    }

    private void findViewsById(View rootView) {
        eTEmail = (EditText) rootView.findViewById(R.id.etxt_email);
        addButton = (Button) rootView.findViewById(R.id.button_add_email);
        resetButton = (Button) rootView.findViewById(R.id.button_reset_email);
    }

    @Override
    public void onClick(View view) {
        if (view == addButton) {
            setEmail();
            addEmailTask = new EmailAddFragment.AddEmailTask(getActivity());
            addEmailTask.execute((Void) null);

            switchContent(new EmailListFragment(), EmailListFragment.ARG_ITEM_ID);

        } else if (view == resetButton) {
            resetAllFields();
        }
    }

    private void switchContent(Fragment fragment, String tag){

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        while (fragmentManager.popBackStackImmediate());

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.content_email_frame, fragment, tag);
        transaction.commit();

    }

    public class AddEmailTask extends AsyncTask<Void, Void, Long> {

        private final WeakReference<Activity> activityWeakRef;

        public AddEmailTask(Activity context) {
            this.activityWeakRef = new WeakReference<Activity>(context);
        }

        @Override
        protected Long doInBackground(Void... arg0) {
            long result = emailDAO.save(email);
            return result;
        }

        @Override
        protected void onPostExecute(Long result) {
            if (activityWeakRef.get() != null && !activityWeakRef.get().isFinishing()) {
                if (result != -1) {
                    Toast.makeText(activityWeakRef.get(), "Email Saved", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}