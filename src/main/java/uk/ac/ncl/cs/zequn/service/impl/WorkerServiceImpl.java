package uk.ac.ncl.cs.zequn.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.ncl.cs.zequn.controller.WorkerListener;
import uk.ac.ncl.cs.zequn.service.InstanceService;
import uk.ac.ncl.cs.zequn.service.WorkerService;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zequnli on 30/07/2014.
 */
@Service
public class WorkerServiceImpl implements WorkerService {
    private Timer timer;
    private final int workingTime = 10;
    private int timeCounter;
    @Autowired
    private InstanceService service;
    private WorkerListener listener;
    private static List<Double> statusList = new ArrayList<Double>();
//
//    class myTimerTask extends TimerTask{
//
//        @Override
//        public void run() {
//            timeCounter--;
//            if(0==timeCounter){
//                try {
//                    listener.changeActive();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                timeCounter = workingTime;
//            }
//        }
//    }
    @Override
    public void start(WorkerListener listener) {
        this.listener = listener;

      //  timer.scheduleAtFixedRate(new myTimerTask(),0,1000);
    }

    private void analyse(int id, double status) throws InterruptedException {
        if(status<50.0){
            double max = 0;
            int pos = -1;
            for(int i =0 ;i<statusList.size();i++){
                if(statusList.get(i)>max){
                    max = statusList.get(i);
                    pos = i;
                }
            }
            if(max<50.0){
                service.createNewInstance();
            }else {
                listener.changeActive(pos);
            }
        }
    }

    @Override
    public void updateStatus(int i, double status) {
        statusList.set(i,status);
//        try {
//            analyse(i,status);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void newInstanceStatus(int id) {
        if(statusList.size() != id){
            throw new IllegalStateException();
        }
        statusList.add(0.0);
    }


    @Override
    public void stop() {
        timer.cancel();
    }
}
