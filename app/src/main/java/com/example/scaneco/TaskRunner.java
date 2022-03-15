package com.example.scaneco;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TaskRunner {
    private final Executor executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());

    public interface Callback<R>{
        void onComplete(R result);
    }

    public <R> void executeAsync(Callable<R> callable, Callback<R> callback){
        executor.execute(()->{
            R result = null;
            try {
                result = callable.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
            R finalResult = result;
            handler.post(()-> callback.onComplete(finalResult));
        });
    }
}