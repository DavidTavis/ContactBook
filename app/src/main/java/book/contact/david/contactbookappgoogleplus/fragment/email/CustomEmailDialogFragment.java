package book.contact.david.contactbookappgoogleplus.fragment.email;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import book.contact.david.contactbookappgoogleplus.R;
import book.contact.david.contactbookappgoogleplus.activities.EmailActivity;
import book.contact.david.contactbookappgoogleplus.db.EmailDAO;
import book.contact.david.contactbookappgoogleplus.model.Email;

/**
 * Created by TechnoA on 16.06.2017.
 */

public class CustomEmailDialogFragment extends DialogFragment {

    // UI references
    private EditText etEmail;
    private LinearLayout submitLayout;

    private Email email;

    EmailDAO emailDAO;

    public static final String ARG_ITEM_ID = "email_dialog_fragment";

    /*
     * Callback used to communicate with EmailListFragment to notify the list adapter.
     * MainActivity implements this interface and communicates with EmailListFragment.
     */
    public interface CustomEmailDialogFragmentListener {
        void onFinishEmailDialog();
    }

    public CustomEmailDialogFragment() {

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        emailDAO = new EmailDAO(getActivity());

        Bundle bundle = this.getArguments();
        email = bundle.getParcelable("selectedEmail");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View customDialogView = inflater.inflate(R.layout.fragment_add_email, null);

        builder.setView(customDialogView);

        etEmail = (EditText) customDialogView.findViewById(R.id.etxt_email);

        submitLayout = (LinearLayout) customDialogView.findViewById(R.id.layout_submit_email);
        submitLayout.setVisibility(View.GONE);

        setValue();

        builder.setTitle(R.string.update_email);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.update, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                email.setEmail(etEmail.getText().toString());

                String currentContactId = Integer.toString(getContext().getSharedPreferences("myContactPref", Context.MODE_PRIVATE).getInt("contactId",0));
                email.setContactId(currentContactId);

                long result = emailDAO.update(email);
                if (result > 0) {
                    EmailActivity activity = (EmailActivity) getActivity();
                    activity.onFinishEmailDialog();
                } else {
                    Toast.makeText(getActivity(), "Unable to update email", Toast.LENGTH_SHORT).show();
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
        if (email != null) {
            etEmail.setText(email.getEmail());
        }
    }
}
