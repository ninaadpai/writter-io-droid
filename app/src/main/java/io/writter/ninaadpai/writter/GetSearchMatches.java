package io.writter.ninaadpai.writter;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by ninaadpai on 3/26/17.
 */

class GetSearchMatches {
    public GetSearchMatches(DashboardActivity dashboardActivity) {
    }

    public String execute(String s, ArrayList<String> list) {
        String res = null;
        if(s.length() > 0) {
            for (String string : list) {
                if (string.matches("(?i)" + s + ".*")) {
                    res = string;
                }
            }
        }
        return res;
    }
}
