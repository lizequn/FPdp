package uk.ac.ncl.cs.zequn.service;

import uk.ac.ncl.cs.zequn.controller.WorkerListener;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by zequnli on 30/07/2014.
 */
public interface WorkerService {
    void start(List<String> map,WorkerListener listener);
    void stop();
}
