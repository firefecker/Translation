package com.fire.translation.db;

/**
 * Created by fire on 2018/1/12.
 * Date：2018/1/12
 * Author: fire
 * Description:
 */

public class DBConfig {

    private int version;
    private String dbDir;
    private String dbName;

    public DBConfig(int version, String dbDir, String dbName) {
        this.version = version;
        this.dbDir = dbDir;
        this.dbName = dbName;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getDbDir() {
        return dbDir;
    }

    public void setDbDir(String dbDir) {
        this.dbDir = dbDir;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int version;
        private String dbDir;
        private String dbName;

        public Builder version(int version) {
            this.version = version;
            return this;
        }

        public Builder dbDir(String dbDir) {
            this.dbDir = dbDir;
            return this;
        }

        public Builder dbName(String dbName) {
            this.dbName = dbName;
            return this;
        }

        public DBConfig build() {
            return new DBConfig(version,dbDir,dbName);
        }
    }

}
