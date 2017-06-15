package book.contact.david.contactbookappgoogleplus.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import book.contact.david.contactbookappgoogleplus.R;
import book.contact.david.contactbookappgoogleplus.fragment.ContactAddFragment;
import book.contact.david.contactbookappgoogleplus.fragment.ContactListFragment;
import book.contact.david.contactbookappgoogleplus.fragment.CustomContactDialogFragment;

/**
 * Created by TechnoA on 14.06.2017.
 */

public class MainActivity extends AppCompatActivity implements
        CustomContactDialogFragment.CustomContactDialogFragmentListener {

    private Fragment contentFragment;
    private ContactListFragment contactListFragment;
    private ContactAddFragment contactAddFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        FragmentManager fragmentManager = getSupportFragmentManager();

		/*
		 * This is called when orientation is changed.
		 */
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("content")) {
                String content = savedInstanceState.getString("content");
                if (content.equals(ContactAddFragment.ARG_ITEM_ID)) {
                    if (fragmentManager.findFragmentByTag(ContactAddFragment.ARG_ITEM_ID) != null) {
                        setFragmentTitle(R.string.add_contact);
                        contentFragment = fragmentManager.findFragmentByTag(ContactAddFragment.ARG_ITEM_ID);
                    }
                }
            }
            if (fragmentManager.findFragmentByTag(ContactListFragment.ARG_ITEM_ID) != null) {
                contactListFragment = (ContactListFragment) fragmentManager.findFragmentByTag(ContactListFragment.ARG_ITEM_ID);
                contentFragment = contactListFragment;
            }
        } else {
            contactListFragment = new ContactListFragment();
            setFragmentTitle(R.string.app_name);
            switchContent(contactListFragment, ContactListFragment.ARG_ITEM_ID);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                setFragmentTitle(R.string.add_contact);
                contactAddFragment = new ContactAddFragment();
                switchContent(contactAddFragment, ContactAddFragment.ARG_ITEM_ID);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (contentFragment instanceof ContactAddFragment) {
            outState.putString("content", ContactAddFragment.ARG_ITEM_ID);
        } else {
            outState.putString("content", ContactListFragment.ARG_ITEM_ID);
        }
        super.onSaveInstanceState(outState);
    }

    /*
     * We consider ContactListFragment as the home fragment and it is not added to
     * the back stack.
     */
    public void switchContent(Fragment fragment, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        while (fragmentManager.popBackStackImmediate())
            ;

        if (fragment != null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.content_frame, fragment, tag);
            // Only EmpAddFragment is added to the back stack.
            if (!(fragment instanceof ContactListFragment)) {
                transaction.addToBackStack(tag);
            }
            transaction.commit();
            contentFragment = fragment;
        }
    }

    protected void setFragmentTitle(int resourseId) {
        setTitle(resourseId);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null)
            actionBar.setTitle(resourseId);
    }

    /*
     * We call super.onBackPressed(); when the stack entry count is > 0. if it
     * is instanceof EmpListFragment or if the stack entry count is == 0, then
     * we prompt the user whether to quit the app or not by displaying dialog.
     * In other words, from ContactListFragment on back press it quits the app.
     */
    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            super.onBackPressed();
        } else if (contentFragment instanceof ContactListFragment || fm.getBackStackEntryCount() == 0) {
            //finish();
            //Shows an alert dialog on quit
            onShowQuitDialog();
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
     * Callback used to communicate with EmpListFragment to notify the list adapter.
     * Communication between fragments goes via their Activity class.
     */
    @Override
    public void onFinishDialog() {
        if (contactListFragment != null) {
            contactListFragment.updateView();
        }
    }
}
