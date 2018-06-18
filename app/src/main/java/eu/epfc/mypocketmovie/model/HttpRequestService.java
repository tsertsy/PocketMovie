package eu.epfc.mypocketmovie.model;


import android.app.IntentService;

import android.content.Intent;
import android.content.Context;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Olivier on 12/05/2018.
 */

public class HttpRequestService extends IntentService {
    private OkHttpClient httpClient = new OkHttpClient();

    private static final String EXTRA_URLSTRING = "MOVIEURLSTRING";
    public HttpRequestService() {
        super("HttpRequestService");
    }
    public static void startActionRequestHttp(Context context, String url) {
        Intent intent = new Intent(context, HttpRequestService.class);
        intent.putExtra(EXTRA_URLSTRING, url);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {

            final String url = intent.getStringExtra(EXTRA_URLSTRING);
            try {
                String responseString = startRequest(url);

                // the request succeeded -> send a brodcase message "httpRequestComplete"
                Intent completeIntent = new Intent("httpRequestComplete");
                completeIntent.putExtra("responseString",responseString);
                sendBroadcast(completeIntent);
            }
            catch (IOException e) {

                // the request failed -> send a brodcase message "httpRequestFailed"
                Intent completeIntent = new Intent("httpRequestFailed");
                sendBroadcast(completeIntent);
            }
        }
    }

    private String startRequest(String url) throws IOException {

        Request request = new Request.Builder().url(url).build();
        Response response = httpClient.newCall(request).execute();
        return   response.body().string();
    }
}
