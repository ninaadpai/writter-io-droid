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

    public List<String> execute(String s, ArrayList<String> list) {
        List<String> possibleMatches = new ArrayList<>();

        if(s.length() > 0) {
            for (String string : list) {
                if (string.matches("(?i).*"+s+".*")) {
                    possibleMatches.add(string);
                }
            }
        }
        if(possibleMatches.size() == list.size())
            return null;
        return possibleMatches;
    }
}
