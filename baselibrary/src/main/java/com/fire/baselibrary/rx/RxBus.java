package com.fire.baselibrary.rx;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;

/**
 *
 * @author fire
 * @date 2018/1/15
 * Description:
 */

public class RxBus {
    private static volatile RxBus defaultInstance;

    private final Relay<Object> bus;

    public RxBus() {
        bus = PublishRelay.create().toSerialized();
    }
    // 单例RxBus
    public static RxBus getDefault() {
        if (defaultInstance == null) {
            synchronized (RxBus.class) {
                if (defaultInstance == null) {
                    defaultInstance = new RxBus();
                }
            }
        }
        return defaultInstance ;
    }
    // 发送一个新的事件
    public void post (Object o) {
        bus.accept(o);
    }
    // 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
    public <T> Observable<T> toObservable (Class<T> eventType) {
        return bus.ofType(eventType);
    }

    /**
     * Helpers
     */
    public void postEventBase(EventBase eventBase) {
        post(eventBase);
    }

    public Observable<EventBase> switchToEventBase(@Nullable final Class<? extends Object> receiver, @Nullable final Class<? extends Object> sender) {
        return bus.ofType(EventBase.class).compose(RxBus.switchEventBase(receiver,sender));
    }

    /**
     * 过滤EventBase,null表示通配符*
     * 如`observable.compose(RxBus.switchEventBase(String.class, null))`表示获取所有接受者为`String
     * .class`的EventBase
     *
     * @param receiver 接受者
     * @param sender 发送者
     */
    @NonNull
    public static ObservableTransformer<EventBase, EventBase> switchEventBase(
            @Nullable final Class<? extends Object> receiver,
            @Nullable final Class<? extends Object> sender) {
        return upstream -> upstream.filter(eventBase -> {
            boolean flag = true;
            if (receiver != null) {
                flag = flag && (receiver == eventBase.getReceiver() || receiver.equals(
                        eventBase.getReceiver()));
            }
            if (sender != null) {
                flag = flag && (sender == eventBase.getSender() || sender.equals(
                        eventBase.getSender()));
            }
            return flag;
        });
    }
}
