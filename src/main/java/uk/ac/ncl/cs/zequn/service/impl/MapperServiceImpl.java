package uk.ac.ncl.cs.zequn.service.impl;

import org.springframework.stereotype.Service;
import uk.ac.ncl.cs.zequn.controller.WorkerListener;
import uk.ac.ncl.cs.zequn.service.MapperService;

import java.util.*;

/**
 * Created by zequnli on 23/07/2014.
 */
@Service
public class MapperServiceImpl implements MapperService {
    private static WorkerListener listener;
    private static int activeWorker = -1;
    private static ArrayList<String> mapper;
    public MapperServiceImpl(){
        if(mapper == null){
            mapper = new ArrayList<String>();
        }
    }

    @Override
    public int register( String url) {
        mapper.add(url);
        return mapper.size()-1;
    }

    @Override
    public ArrayList< String> getMapper() {
        return mapper;
    }

    @Override
    public void setActiveListener(WorkerListener listener) {
        MapperServiceImpl.listener = listener;
    }

    @Override
    public void setActive(int id) {
        activeWorker = id;
        listener.setActive(mapper.get(id));
    }

    @Override
    public int getActive() {
        return activeWorker;
    }
}
