package cz.cvut.fit.palicand.akos;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * User: palicka
 * Date: 14/01/13
 * Time: 18:49
 * To change this template use File | Settings | File Templates.
 */
public class AuthenticatorService {
    private static boolean authenticated = false;

    public static boolean authenticate(Context context) {
        if(!authenticated) {
            AccountManager accountManager = AccountManager.get(context);
            Account[] accounts = accountManager.getAccountsByType("com.google");
            for(Account account : accounts) {
                if(account.name.contains("cvut.cz")) {
                    authenticated = true;
                    break;
                }
            }
            if(!authenticated) {

            }
        }
        return authenticated;
    }

}
