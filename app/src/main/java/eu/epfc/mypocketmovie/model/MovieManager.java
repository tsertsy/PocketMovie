package eu.epfc.mypocketmovie.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 0309oltserstevens on 15/06/2018.
 */

public class MovieManager {
    protected long extractLongValueFromJsonPlanet(JSONObject jsonPlanet, String name)
    {
        try {
            return jsonPlanet.getLong(name);
        }

        catch(JSONException e)
        {
            return 0;
        }
    }

    protected double extractDoubleValueFromJsonMovie(JSONObject jsonMovie, String name)
    {
        try {
            return jsonMovie.getDouble(name);
        }

        catch(JSONException e)
        {
            String s = e.getMessage();
            Log.d(getTAG(), e.getMessage());
            return 0;
        }
    }
    protected String getTAG(){
        return "MovieManager";
    }
}
