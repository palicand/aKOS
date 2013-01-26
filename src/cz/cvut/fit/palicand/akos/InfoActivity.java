package cz.cvut.fit.palicand.akos;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Collection;
import java.util.HashSet;

public class InfoActivity extends FragmentActivity {

    public String getUsername() {
        return username;
    }

    private String username;
    private boolean authenticated;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
	    //activity was not resumed, we have to load initial data
		loadPreferences();
        if(!authenticated)
            authenticate();
        loadFragment(new StudentInfoFragment());
	}

    private void authenticate() {
        authenticated = AuthenticatorService.authenticate(this);
        if(authenticated) {
            Toast.makeText(this, getText(R.string.toast_authenticated), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getText(R.string.toast_not_authenticated), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        prefs.edit().putString(getString(R.string.pref_key_authenticated), username).commit();
    }

    /**
	 * loads preferences like login credentials. assigns null to the class variable
	 * representing the preference if none are found
	 */
	private void loadPreferences() {
		SharedPreferences prefs = getPreferences(MODE_PRIVATE);
		String tempUsername = prefs.getString(getString(R.string.pref_key_username), null);
        if(tempUsername == null) {
            username = extractUsername();
        } else {
            username = tempUsername;
        }
	}

    private String extractUsername() {
        AccountManager accountManager = AccountManager.get(this);
        Account[] accounts = accountManager.getAccountsByType("com.google");
        for(Account account : accounts) {
            if(account.name.contains(".cvut.cz")) {
                int length = account.name.indexOf("@");
                return account.name.substring(0, length);
            }
        }
        return null;
    }


    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_info, menu);
		return true;
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
        outState.putBoolean(getString(R.string.pref_key_authenticated), authenticated);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_timetable:
			return true;
		case R.id.menu_info:
			loadFragment(new StudentInfoFragment());
			return true;
		case R.id.menu_courses:
			loadFragment(new CoursesFragment());
			return true;
		case R.id.menu_exams:
			return true;
		default:
			return false;
		}
		
	}

    /**
	 * loads specified fragment, replacing the current fragment
	 * @param fragment fragment to be loaded
	 */
	private void loadFragment(Fragment fragment) {
		if(authenticated) {
			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction fa = fm.beginTransaction();
			fa.replace(R.id.info_fragment, fragment).addToBackStack(null).commit();
		} else {
			Toast.makeText(this, "You must be authenticated", Toast.LENGTH_LONG).show();
		}
	}
}
