package uk.ac.ncl.cs.zequn.service;

import uk.ac.ncl.cs.zequn.controller.WorkerController;
import uk.ac.ncl.cs.zequn.controller.WorkerListener;

import java.util.List;
import java.util.Map;

/**
 * Created by zequnli on 23/07/2014.
 */
public interface MapperService {
    int register(String url);
    List<String> getMapper();
    void setActiveListener(WorkerListener listener);
//    void setActive(int id);
    int getActive();
}
