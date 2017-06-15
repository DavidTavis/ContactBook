package book.contact.david.contactbookappgoogleplus.fragment.phone;

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
import book.contact.david.contactbookappgoogleplus.adapter.PhoneListAdapter;
import book.contact.david.contactbookappgoogleplus.db.PhoneDAO;
import book.contact.david.contactbookappgoogleplus.model.Phone;

/**
 * Created by TechnoA on 15.06.2017.
 */

public class PhoneListFragment extends Fragment implements OnItemClickListener,
        OnItemLongClickListener {

    public static final String ARG_ITEM_ID = "phone_list";

    Activity activity;
    ListView phoneListView;
    ArrayList<Phone> phones;

    PhoneListAdapter phoneListAdapter;
    PhoneDAO phoneDAO;

    private PhoneListFragment.GetPhoneTask task;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        phoneDAO = new PhoneDAO(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_phone_list, container,false);
        findViewsById(view);

        task = new PhoneListFragment.GetPhoneTask(activity);
        task.execute((Void) null);

        phoneListView.setOnItemClickListener(this);
        phoneListView.setOnItemLongClickListener(this);
        return view;
    }

    private void findViewsById(View view) {
        phoneListView = (ListView) view.findViewById(R.id.list_phones);
    }

    @Override
    public void onItemClick(AdapterView<?> list, View view, int position,
                            long id) {
        Phone phone = (Phone) list.getItemAtPosition(position);

        if (phone != null) {
            Bundle arguments = new Bundle();
            arguments.putParcelable("selectedPhone", phone);
//            CustomContactDialogFragment customContactDialogFragment = new CustomContactDialogFragment();
//            customContactDialogFragment.setArguments(arguments);
//            customContactDialogFragment.show(getFragmentManager(),CustomContactDialogFragment.ARG_ITEM_ID);
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        Phone phone = (Phone)  parent.getItemAtPosition(position);
        // Use AsyncTask to delete from database
        phoneDAO.deletePhone(phone);
        phoneListAdapter.remove(phone);

        return true;
    }

    public class GetPhoneTask extends AsyncTask<Void, Void, ArrayList<Phone>> {

        private final WeakReference<Activity> activityWeakRef;

        public GetPhoneTask(Activity context) {
            this.activityWeakRef = new WeakReference<Activity>(context);
        }

        @Override
        protected ArrayList<Phone> doInBackground(Void... arg0) {
            ArrayList<Phone> phoneList = phoneDAO.getPhones();
            return phoneList;
        }

        @Override
        protected void onPostExecute(ArrayList<Phone> phoneList) {
            if (activityWeakRef.get() != null && !activityWeakRef.get().isFinishing()) {
                phones = phoneList;
                if (phoneList != null) {
                    if (phoneList.size() != 0) {
                        phoneListAdapter = new PhoneListAdapter(activity,phoneList);
                        phoneListView.setAdapter(phoneListAdapter);
                    } else {
                        Toast.makeText(activity, "No Phone Records",Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }

    /*
     * This method is invoked from MainActivity onFinishDialog() method. It is
     * called from CustomPhoneDialogFragment when a phone record is updated.
     * This is used for communicating between fragments.
     */
    public void updateView() {
        task = new GetPhoneTask(activity);
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
