package book.contact.david.contactbookappgoogleplus.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import book.contact.david.contactbookappgoogleplus.R;
import book.contact.david.contactbookappgoogleplus.fragment.email.CustomEmailDialogFragment;
import book.contact.david.contactbookappgoogleplus.fragment.email.EmailAddFragment;
import book.contact.david.contactbookappgoogleplus.fragment.email.EmailListFragment;

/**
 * Created by TechnoA on 16.06.2017.
 */

public class EmailActivity extends AppCompatActivity implements
        CustomEmailDialogFragment.CustomEmailDialogFragmentListener {

    private Fragment contentFragment;
    private EmailListFragment emailListFragment;
    private EmailAddFragment emailAddFragment;

    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email_activity);

        pref = getSharedPreferences("myContactPref", Context.MODE_PRIVATE);

        FragmentManager fragmentManager = getSupportFragmentManager();

		/*
		 * This is called when orientation is changed.
		 */
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("content")) {
                String content = savedInstanceState.getString("content");
                if (content.equals(EmailAddFragment.ARG_ITEM_ID)) {
                    if (fragmentManager.findFragmentByTag(EmailAddFragment.ARG_ITEM_ID) != null) {
                        setFragmentTitle();
                        contentFragment = fragmentManager.findFragmentByTag(EmailAddFragment.ARG_ITEM_ID);
                    }
                }
            }
            if (fragmentManager.findFragmentByTag(EmailListFragment.ARG_ITEM_ID) != null) {
                emailListFragment = (EmailListFragment) fragmentManager.findFragmentByTag(EmailListFragment.ARG_ITEM_ID);
                contentFragment = emailListFragment;
            }
        } else {
            emailListFragment = new EmailListFragment();
            setFragmentTitle();
            switchContent(emailListFragment, EmailListFragment.ARG_ITEM_ID);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        menu.findItem(R.id.action_add_contact).setVisible(false);
        menu.findItem(R.id.action_add_phone_number).setVisible(false);
        menu.findItem(R.id.action_alphabetical_sort).setVisible(false);
        menu.findItem(R.id.action_ascending_sort).setVisible(false);
        menu.findItem(R.id.action_descending_sort).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_email:
                setFragmentTitle();
                emailAddFragment = new EmailAddFragment();
                switchContent(emailAddFragment, EmailAddFragment.ARG_ITEM_ID);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (contentFragment instanceof EmailAddFragment) {
            outState.putString("content", EmailAddFragment.ARG_ITEM_ID);
        } else {
            outState.putString("content", EmailListFragment.ARG_ITEM_ID);
        }
        super.onSaveInstanceState(outState);
    }

    /*
     * We consider EmailListFragment as the home fragment and it is not added to
     * the back stack.
     */
    public void switchContent(Fragment fragment, String tag) {

        FragmentManager fragmentManager = getSupportFragmentManager();

        while (fragmentManager.popBackStackImmediate());

        if (fragment != null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.content_email_frame, fragment, tag);
            // Only EmailAddFragment is added to the back stack.
            if (!(fragment instanceof EmailListFragment)) {
                transaction.addToBackStack(tag);
            }
            transaction.commit();
            contentFragment = fragment;
        }
    }

    protected void setFragmentTitle() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            String name = "Email: " + pref.getString("contactFirstName", "Some") + " " + pref.getString("contactLastName", "Name");
            actionBar.setTitle(name);
        }
    }

    /*
     * We call super.onBackPressed(); when the stack entry count is > 0. if it
     * is instanceof EmailListFragment or if the stack entry count is == 0, then
     * we prompt the user whether to quit the app or not by displaying dialog.
     * In other words, from EmailListFragment on back press it quits the app.
     */
    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            super.onBackPressed();
        } else if (contentFragment instanceof EmailListFragment || fm.getBackStackEntryCount() == 0) {
            finish();
            //Shows an alert dialog on quit
//            onShowQuitDialog();
        }
    }

    public void onShowQuitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);

        builder.setMessage("Do You Want To Quit?");
        builder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
        builder.setNegativeButton(android.R.string.no,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder.create().show();
    }

    /*
     * Callback used to communicate with EmailListFragment to notify the list adapter.
     * Communication between fragments goes via their Activity class.
     */
    @Override
    public void onFinishEmailDialog() {
        if(emailListFragment != null){
            emailListFragment.updateView();
        }
    }
}
