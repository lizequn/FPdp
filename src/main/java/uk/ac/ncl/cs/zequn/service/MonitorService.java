package uk.ac.ncl.cs.zequn.service;

import uk.ac.ncl.cs.zequn.entity.ResultCollection;

/**
 * Created by zequnli on 21/08/2014.
 */
public interface MonitorService {
    void analyse(ResultCollection collection);
    void outputCSV(String fileName);
}
