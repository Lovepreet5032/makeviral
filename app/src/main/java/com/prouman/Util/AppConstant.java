package com.prouman.Util;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;

import com.prouman.R;

import org.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by om on 3/3/2017.
 */
public class AppConstant {
    public static int count=0;
    public static String videoscreentitle="Videos";
    public static final String STRINGLANGUAGE ="Language" ;
    private static final HashMap<String, Integer> emoticons = new HashMap<String, Integer>();
    public static boolean isLanguagechanged=false;
    public static JSONArray jsonArray=null;
    static {
        emoticons.put(":)", R.drawable.smile);}
      //  emoticons.put(":D", R.drawable.j2);}
    public static Spannable getSmiledText(Context context, String s) {
        int index;
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(s);

        for (index = 0; index < builder.length(); index++) {
            for (Map.Entry<String, Integer> entry : emoticons.entrySet()) {
                int length = entry.getKey().length();
                if (index + length > builder.length())
                    continue;
                if (builder.subSequence(index, index + length).toString()
                        .equals(entry.getKey())) {
                    builder.setSpan(new ImageSpan(context, entry.getValue()),
                            index, index + length,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    builder.setSpan(new SpannableStringBuilder("<img src=\"smiley\">"),index, index + length,
//                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    index += length - 1;
                    break;
                }
            }
        }
        return builder;
    }
    public static String fixEncoding(String response) {
        try {
            byte[] u = response.toString().getBytes("ISO-8859-1");
            response = new String(u, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        return response;
    }
    public static void setLanguageForApp(String languageToLoad, AppCompatActivity context){
        Locale locale;
        if(languageToLoad.equals("not-set")){ //use any value for default
            locale = Locale.getDefault();
        }
        else {
            locale = new Locale(languageToLoad);
        }
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getBaseContext().getResources().updateConfiguration(config,
                context.getBaseContext().getResources().getDisplayMetrics());
    }
    public static String encodedResponse(String response)
    {
        try {
            response = URLDecoder.decode(URLEncoder.encode(response, "iso-8859-1"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return response;
    }
    public static String loadCountryJson(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("flags.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
    public static SpannableStringBuilder makeStringBold(String strText,int length)
    {
        SpannableStringBuilder str = new SpannableStringBuilder(strText);
        str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return str;
    }
    public static SpannableStringBuilder makeStringBold(String strText)
    {
        SpannableStringBuilder str = new SpannableStringBuilder(strText);
        str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, strText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        str.setSpan(new ForegroundColorSpan(Color.GREEN), 0, strText.length(),  Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return str;
    }

}
