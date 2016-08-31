package com.dineplan.rest.listener;

public interface AsyncTaskCompleteListener<T> {
    public void onTaskComplete(T result, int fromCalling);
}
