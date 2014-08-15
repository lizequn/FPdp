package uk.ac.ncl.cs.zequn.controller;

/**
 * Created by zequnli on 23/07/2014.
 */
public interface WorkerListener {
//    void setActive(String url);
    void changeActive() throws InterruptedException;
}
