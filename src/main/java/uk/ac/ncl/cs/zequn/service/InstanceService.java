package uk.ac.ncl.cs.zequn.service;

import uk.ac.ncl.cs.zequn.entity.AggregationCreationEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zequnli on 16/08/2014.
 */
public interface InstanceService {
    void createNewInstance();
    void storeAggregation(AggregationCreationEntity entity);
    void resumeAggregation(String url,int count,List<String> mapper);
}
