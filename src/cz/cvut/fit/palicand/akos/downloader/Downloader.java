package cz.cvut.fit.palicand.akos.downloader;

import android.util.Base64;
import android.util.Log;
import cz.cvut.fit.palicand.akos.AndroidKOS;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: palicka
 * Date: 17/01/13
 * Time: 00:34
 * To change this template use File | Settings | File Templates.
 */
public class Downloader {
    private String authUsername;
    private String authPassword;
    private  String authString;
    private static final String BASE_URL;
    private static final String FORMAT;

    private static final String VERSION;

    private Map<String, String> parameters;

    static {
        BASE_URL = "https://kosapi.fit.cvut.cz/api/";
        VERSION = "3b";
        FORMAT = ".xml";

    }

    private String url;

    public Downloader(String uri) {
        this.url = BASE_URL + VERSION + "/" + uri + FORMAT;
        parameters = new HashMap<String, String>();
        loadAuthentication();
    }

    private void loadAuthentication() {
        HashMap<String, String> map = AndroidKOS.getInstance().getAuthentication();
        authUsername = map.get("username");
        authPassword = map.get("password");
        authString = "Basic " + Base64.encodeToString((authUsername + ":" + authPassword).getBytes(), Base64.URL_SAFE);
    }

    public InputStream download() {
        InputStream stream = null;
        String parameters = createParameters();
        if(parameters != null)
            this.url += parameters;
        try {
            URL url = new URL(this.url);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestProperty("Authorization", authString);
            connection.setRequestMethod("GET");
            Log.d("androidKOS", connection.getResponseMessage());
            stream = connection.getInputStream();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return stream;
    }

    private String createParameters() {
        if(parameters.isEmpty())
            return null;
        StringBuilder parameterBuilder = new StringBuilder("?");
        for(Map.Entry<String, String> parameter : parameters.entrySet()) {
            parameterBuilder.append(parameter.getKey()).append("=").append(parameter.getValue()).append("&");
        }

        return parameterBuilder.toString();
    }

    public Downloader setParameter(String parameter, String value) {
        parameters.put(parameter, value);
        return this;
    }

    public Downloader unsetParameter(String parameter) {
        parameters.remove(parameter);
        return this;
    }
}
