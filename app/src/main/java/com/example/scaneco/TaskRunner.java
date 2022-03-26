package com.example.scaneco;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TaskRunner {
    private final Executor executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());

    public interface Callback<R>{
        void onComplete(R result) throws IOException;
    }

    public <V> void executeAsync(Callable<V> callable, Callback<V> callback){
        executor.execute(()->{
            V result = null;
            try {
                result = callable.call();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            final V finalResult = result;
            handler.post(()-> {
                try {
                    callback.onComplete(finalResult);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });
    }
}