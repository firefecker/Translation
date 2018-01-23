package com.fire.baselibrary.utils;

import android.content.Context;
import android.preference.Preference;
import android.support.annotation.ArrayRes;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by fire on 2018/1/17.
 * Date：2018/1/17
 * Author: fire
 * Description:
 */

public class ListUtils {

    public static String listStrToString(List<String> list,String splite) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                stringBuffer.append(list.get(i));
            } else {
                stringBuffer.append(list.get(i) + splite);
            }
        }
        return stringBuffer.toString();
    }

    public  static List<String> setToList(Context context,@ArrayRes int array, @ArrayRes int arrayValue, Set<String> newValue) {
        String[] stringArray = context.getResources().getStringArray(array);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < stringArray.length; i++) {
            for (String s : newValue) {
                if (stringArray[i].equals(s)) {
                    list.add(context.getResources().getStringArray(arrayValue)[i]);
                }
            }
        }
        return list;
    }

    public static String stringToString(Context context,@ArrayRes int array, @ArrayRes int arrayValue, String newValue) {
        String s = "";
        String[] stringArray = context.getResources().getStringArray(array);
        for (int i = 0; i < stringArray.length; i++) {
            if (stringArray[i].equals(newValue)) {
                s = context.getResources().getStringArray(arrayValue)[i];
            }
        }
        return s;
    }

}
