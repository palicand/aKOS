package cz.cvut.fit.palicand.akos;

import android.app.Application;
import android.os.Bundle;
import cz.cvut.fit.palicand.akos.resources.Student;

import java.util.Collection;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: palicka
 * Date: 26/01/13
 * Time: 11:41
 * To change this template use File | Settings | File Templates.
 */
public class AndroidKOS extends Application {
    private static AndroidKOS instance;
    @Override
    public void onCreate() {
        instance = this;
    }

    public static AndroidKOS getInstance() {
        return instance;
    }

    public HashMap<String, String> getAuthentication() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("password", getString(R.string.authentication_password));
        map.put("username", getString(R.string.authentication_username));
        return map;
    }

}