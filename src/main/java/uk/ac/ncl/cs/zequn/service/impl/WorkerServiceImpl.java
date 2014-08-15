package uk.ac.ncl.cs.zequn.service.impl;

import org.springframework.stereotype.Service;
import uk.ac.ncl.cs.zequn.controller.WorkerListener;
import uk.ac.ncl.cs.zequn.service.WorkerService;

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
    private WorkerListener listener;

    class myTimerTask extends TimerTask{

        @Override
        public void run() {
            timeCounter--;
            if(0==timeCounter){
                listener.changeActive();
                timeCounter = workingTime;
            }
        }
    }
    @Override
    public void start(List<String> map,WorkerListener listener) {
        this.listener = listener;
        timeCounter = workingTime;
        timer = new Timer();
        timer.scheduleAtFixedRate(new myTimerTask(),0,1000);
    }

    @Override
    public void stop() {
        timer.cancel();
    }
}
