package book.contact.david.contactbookappgoogleplus.fragment.phone;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.ref.WeakReference;

import book.contact.david.contactbookappgoogleplus.R;
import book.contact.david.contactbookappgoogleplus.Utils;
import book.contact.david.contactbookappgoogleplus.db.PhoneDAO;
import book.contact.david.contactbookappgoogleplus.model.Phone;

/**
 * Created by TechnoA on 15.06.2017.
 */

public class PhoneAddFragment extends Fragment implements View.OnClickListener {

    // UI references
    private EditText phoneNumber;
    private Button addButton;
    private Button resetButton;


    Phone phone = null;

    private PhoneDAO phoneDAO;
    private PhoneAddFragment.AddPhoneTask addPhoneTask;

    public static final String ARG_ITEM_ID = "phone_add_fragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        phoneDAO = new PhoneDAO(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_add_phone, container, false);

        findViewsById(rootView);

        setListeners();

        return rootView;

    }

    private void setListeners() {
        addButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);
    }

    protected void resetAllFields() {
        phoneNumber.setText("");
    }

    private void setPhone() {

        String currentContactId = Integer.toString(getContext().getSharedPreferences("myContactPref", Context.MODE_PRIVATE).getInt("contactId",0));
        Utils.logInfo("currentContactId = " + currentContactId);

        phone = new Phone();
        phone.setNumber(phoneNumber.getText().toString());
        phone.setContactId(currentContactId);

    }

    @Override
    public void onResume() {

        getActivity().setTitle(R.string.add_phone);

        android.app.ActionBar actionBar = getActivity().getActionBar();

        if(actionBar!=null) {
            actionBar.setTitle(R.string.add_phone);
        }

        super.onResume();
    }

    private void findViewsById(View rootView) {
        phoneNumber = (EditText) rootView.findViewById(R.id.etxt_number);
        addButton = (Button) rootView.findViewById(R.id.button_add_number);
        resetButton = (Button) rootView.findViewById(R.id.button_reset_number);
    }

    @Override
    public void onClick(View view) {
        if (view == addButton) {
            setPhone();
            addPhoneTask = new PhoneAddFragment.AddPhoneTask(getActivity());
            addPhoneTask.execute((Void) null);
        } else if (view == resetButton) {
            resetAllFields();
        }
    }

    public class AddPhoneTask extends AsyncTask<Void, Void, Long> {

        private final WeakReference<Activity> activityWeakRef;

        public AddPhoneTask(Activity context) {
            this.activityWeakRef = new WeakReference<Activity>(context);
        }

        @Override
        protected Long doInBackground(Void... arg0) {
            long result = phoneDAO.save(phone);
            return result;
        }

        @Override
        protected void onPostExecute(Long result) {
            if (activityWeakRef.get() != null && !activityWeakRef.get().isFinishing()) {
                if (result != -1) {
                    Toast.makeText(activityWeakRef.get(), "Phone Saved", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}