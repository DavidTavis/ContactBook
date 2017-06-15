package book.contact.david.contactbookappgoogleplus.fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import book.contact.david.contactbookappgoogleplus.R;
import book.contact.david.contactbookappgoogleplus.activities.MainActivity;
import book.contact.david.contactbookappgoogleplus.activities.SignInActivity;
import book.contact.david.contactbookappgoogleplus.db.ContactDAO;
import book.contact.david.contactbookappgoogleplus.model.Contact;

/**
 * Created by TechnoA on 14.06.2017.
 */

public class CustomContactDialogFragment extends DialogFragment {

    // UI references
    private EditText firstName;
    private EditText lastName;
    private LinearLayout submitLayout;

    private Contact contact;

    ContactDAO contactDAO;

    public static final String ARG_ITEM_ID = "contact_dialog_fragment";

    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.ENGLISH);

    /*
     * Callback used to communicate with EmpListFragment to notify the list adapter.
     * MainActivity implements this interface and communicates with EmpListFragment.
     */
    public interface CustomContactDialogFragmentListener {
        void onFinishDialog();
    }

    public CustomContactDialogFragment() {

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        contactDAO = new ContactDAO(getActivity());

        Bundle bundle = this.getArguments();
        contact = bundle.getParcelable("selectedContact");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View customDialogView = inflater.inflate(R.layout.fragment_add_contacts, null);

        builder.setView(customDialogView);

        firstName = (EditText) customDialogView.findViewById(R.id.etxt_first_name);
        lastName = (EditText) customDialogView.findViewById(R.id.etxt_last_name);

        submitLayout = (LinearLayout) customDialogView.findViewById(R.id.layout_submit);
        submitLayout.setVisibility(View.GONE);

        setValue();

        builder.setTitle(R.string.update_emp);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.update, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        contact.setFirstName(firstName.getText().toString());
                        contact.setLastName(lastName.getText().toString());
                        contact.setUserId(SignInActivity.googleId);
                        long result = contactDAO.update(contact);
                        if (result > 0) {
                            MainActivity activity = (MainActivity) getActivity();
                            activity.onFinishDialog();
                        } else {
                            Toast.makeText(getActivity(),
                                    "Unable to update employee",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        builder.setNegativeButton(R.string.cancel,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();

                    }
                });

        AlertDialog alertDialog = builder.create();

        return alertDialog;
    }

    private void setValue() {
        if (contact != null) {
            firstName.setText(contact.getFirstName());
            lastName.setText(contact.getLastName());
        }
    }
}
