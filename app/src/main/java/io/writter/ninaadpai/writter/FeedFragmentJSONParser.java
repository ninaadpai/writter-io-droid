package io.writter.ninaadpai.writter;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by ninaadpai on 3/28/17.
 */

class FeedFragmentJSONParser {
    static public class PostJSONParser {
        static Post parsePosts(String in) throws JSONException {
                Post p = null;
            JSONObject root = new JSONObject(in);
            String imgUrl = "";
            p.setImgUrl(imgUrl);
            String uploadedBy = root.getString("uploadedBy");
            p.setUploadedBy(uploadedBy);
            String topicString = root.getString("category");
            p.setTopicString(topicString);
            String uploadedTime = root.getString("uploadTime");
            p.setUploadedTime(uploadedTime);
            String postQuestion = root.getString("questionText");
            p.setPostQuestion(postQuestion);
            String postDesc = "";
            p.setPostDesc(postDesc);
            Log.i("Post", String.valueOf(p));
            return p;
            }
        }
    }
