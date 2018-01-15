package com.fire.translation.widget;

import android.support.annotation.Nullable;

/**
 * Created by fire on 2018/1/15.
 * Date：2018/1/15
 * Author: fire
 * Description:
 */

public class EventBase {


    private Class<? extends Object> sender,receiver;
    private String arg2,arg3;
    private int arg0,arg1;

    public EventBase(Class<? extends Object> sender, Class<? extends Object> receiver, int arg0, int arg1, String arg2, String arg3) {
        this.sender = sender;
        this.receiver = receiver;
        this.arg2 = arg2;
        this.arg3 = arg3;
        this.arg0 = arg0;
        this.arg1 = arg1;
    }

    public Class<? extends Object> getSender() {
        return sender;
    }


    public Class<? extends Object> getReceiver() {
        return receiver;
    }


    public String getArg2() {
        return arg2;
    }

    public String getArg3() {
        return arg3;
    }


    public int getArg0() {
        return arg0;
    }


    public int getArg1() {
        return arg1;
    }


    public static Builder builder() {
        return new Builder().arg2("").arg3("").sender(Void.class);
    }

    public static class Builder {

        private Class<? extends Object> sender,receiver;
        private String arg2,arg3;
        private int arg0,arg1;

        public Builder sender(Class<? extends Object> value) {
            this.sender = value;
            return this;
        }

        public Builder receiver(Class<? extends Object> value) {
            this.receiver = value;
            return this;
        }

        public Builder arg0(Integer value) {
            this.arg0 = value;
            return this;
        }

        public Builder arg1(Integer value) {
            this.arg1 = value;
            return this;
        }

        public Builder arg2(String value) {
            this.arg2 = value;
            return this;
        }

        public Builder arg3(String value) {
            this.arg3 = value;
            return this;
        }

        public EventBase build() {
            return new EventBase(sender,receiver,arg0,arg1,arg2,arg3);
        }
    }
}
