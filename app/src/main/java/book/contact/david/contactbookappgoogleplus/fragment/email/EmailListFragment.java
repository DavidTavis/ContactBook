package book.contact.david.contactbookappgoogleplus.fragment.email;

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

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import book.contact.david.contactbookappgoogleplus.R;
import book.contact.david.contactbookappgoogleplus.adapter.EmailListAdapter;
import book.contact.david.contactbookappgoogleplus.db.EmailDAO;
import book.contact.david.contactbookappgoogleplus.model.Email;

/**
 * Created by TechnoA on 16.06.2017.
 */

public class EmailListFragment extends Fragment implements OnItemClickListener,
        OnItemLongClickListener {

    public static final String ARG_ITEM_ID = "email_list";

    Activity activity;
    ListView emailListView;
    ArrayList<Email> emails;

    EmailListAdapter emailListAdapter;
    EmailDAO emailDAO;

    private GetEmailTask task;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();
        emailDAO = new EmailDAO(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_email_list, container,false);
        findViewsById(view);

        task = new GetEmailTask(activity);
        task.execute((Void) null);

        emailListView.setOnItemClickListener(this);
        emailListView.setOnItemLongClickListener(this);

        return view;
    }

    private void findViewsById(View view) {
        emailListView = (ListView) view.findViewById(R.id.list_emails);
    }

    @Override
    public void onItemClick(AdapterView<?> list, View view, int position,long id) {

        Email email = (Email) list.getItemAtPosition(position);

        if (email != null) {
            Bundle arguments = new Bundle();
            arguments.putParcelable("selectedEmail", email);
            CustomEmailDialogFragment customEmailDialogFragment = new CustomEmailDialogFragment();
            customEmailDialogFragment.setArguments(arguments);
            customEmailDialogFragment.show(getFragmentManager(),CustomEmailDialogFragment.ARG_ITEM_ID);
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        Email email = (Email) parent.getItemAtPosition(position);
        // Use AsyncTask to delete from database
        emailDAO.deleteEmail(email);
        emailListAdapter.remove(email);

        return true;
    }

    public class GetEmailTask extends AsyncTask<Void, Void, ArrayList<Email>> {

        private final WeakReference<Activity> activityWeakRef;

        public GetEmailTask(Activity context) {
            this.activityWeakRef = new WeakReference<Activity>(context);
        }

        @Override
        protected ArrayList<Email> doInBackground(Void... arg0) {
            ArrayList<Email> emailList = emailDAO.getEmails();
            return emailList;
        }

        @Override
        protected void onPostExecute(ArrayList<Email> emailList) {
            if (activityWeakRef.get() != null && !activityWeakRef.get().isFinishing()) {
                emails = emailList;
                if (emailList != null) {
                    if (emailList.size() != 0) {
                        emailListAdapter = new EmailListAdapter(activity,emailList);
                        emailListView.setAdapter(emailListAdapter);
                    } else {
                        Toast.makeText(activity, "No Email Records",Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }

    /*
     * This method is invoked from EmailActivity onFinishEmailDialog() method. It is
     * called from CustomEmailDialogFragment when a email record is updated.
     * This is used for communicating between fragments.
     */
    public void updateView() {
        task = new EmailListFragment.GetEmailTask(activity);
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
