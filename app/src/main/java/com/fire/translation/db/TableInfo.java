package com.fire.translation.db;

import com.fire.translation.db.entities.Record;
import com.fire.translation.db.entities.RecordSQLiteTypeMapping;
import com.fire.translation.db.entities.TableName;
import com.fire.translation.db.entities.TableNameSQLiteTypeMapping;
import com.fire.translation.db.entities.Word;
import com.fire.translation.db.entities.WordSQLiteTypeMapping;
import com.pushtorefresh.storio3.sqlite.StorIOSQLite;
import com.pushtorefresh.storio3.sqlite.impl.DefaultStorIOSQLite;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by fire on 2018/1/12.
 * Date：2018/1/12
 * Author: fire
 * Description:
 */

public class TableInfo {

    /**
     * 生成StorIOSQLite对象的所有数据库表名
     */
    public static HashSet<String> getAllTableNameSet() {
        return new HashSet<String>(
                Arrays.asList("app_channel_defect","app_consts","app_daoxian","app_defect_category","app_defect_images","app_defect_standard","app_hongwaicewen","app_hongwaicewen_values","app_jiaochakuayue","app_jiedidianzu","app_noumenon_defect","app_task","app_tower_image","app_tree","app_xslog","pms_daoxian","pms_daoxianparams","pms_jaochakuayue","pms_jcjh","pms_tower","pms_user","pms_xsplan","pms_xsqx","pms_xssb","pms_xszywb"));
    }

    /**
     * 生成StorIOSQLite对象时配置TypeMapping
     *
     * @param {[Builder]} Builder builder
     * @return {Builder}
     */
    public static StorIOSQLite buildTypeMapping(DefaultStorIOSQLite.CompleteBuilder builder) {
        builder.addTypeMapping(Record.class,new RecordSQLiteTypeMapping());
        builder.addTypeMapping(TableName.class,new TableNameSQLiteTypeMapping());
        builder.addTypeMapping(Word.class,new WordSQLiteTypeMapping());
        return builder.build();
    }
}
