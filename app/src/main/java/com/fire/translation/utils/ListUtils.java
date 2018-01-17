package com.fire.translation.utils;

import java.util.List;

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

}
