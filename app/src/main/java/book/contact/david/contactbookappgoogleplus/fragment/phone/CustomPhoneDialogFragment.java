package book.contact.david.contactbookappgoogleplus.fragment.phone;

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

import java.text.SimpleDateFormat;
import java.util.Locale;

import book.contact.david.contactbookappgoogleplus.R;
import book.contact.david.contactbookappgoogleplus.activities.PhoneActivity;
import book.contact.david.contactbookappgoogleplus.activities.SignInActivity;
import book.contact.david.contactbookappgoogleplus.db.PhoneDAO;
import book.contact.david.contactbookappgoogleplus.model.Phone;

/**
 * Created by TechnoA on 15.06.2017.
 */

public class CustomPhoneDialogFragment extends DialogFragment {

    // UI references
    private EditText etNumber;
    private LinearLayout submitLayout;

    private Phone phone;

    PhoneDAO phoneDAO;

    public static final String ARG_ITEM_ID = "phone_dialog_fragment";

    /*
     * Callback used to communicate with PhoneListFragment to notify the list adapter.
     * MainActivity implements this interface and communicates with PhoneListFragment.
     */
    public interface CustomPhoneDialogFragmentListener {
        void onFinishPhoneDialog();
    }

    public CustomPhoneDialogFragment() {

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        phoneDAO = new PhoneDAO(getActivity());

        Bundle bundle = this.getArguments();
        phone = bundle.getParcelable("selectedPhone");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View customDialogView = inflater.inflate(R.layout.fragment_add_phone, null);

        builder.setView(customDialogView);

        etNumber = (EditText) customDialogView.findViewById(R.id.etxt_number);

        submitLayout = (LinearLayout) customDialogView.findViewById(R.id.layout_submit_number);
        submitLayout.setVisibility(View.GONE);

        setValue();

        builder.setTitle(R.string.update_phone);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.update, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                phone.setNumber(etNumber.getText().toString());

                String currentContactId = Integer.toString(getContext().getSharedPreferences("myContactPref", Context.MODE_PRIVATE).getInt("contactId",0));
                phone.setContactId(currentContactId);

                long result = phoneDAO.update(phone);

                if (result > 0) {
                    PhoneActivity activity = (PhoneActivity) getActivity();
                    activity.onFinishPhoneDialog();
                } else {
                    Toast.makeText(getActivity(), "Unable to update phone", Toast.LENGTH_SHORT).show();
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
        if (phone != null) {
            etNumber.setText(phone.getNumber());
        }
    }
}
