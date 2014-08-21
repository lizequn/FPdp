package uk.ac.ncl.cs.zequn.service.impl;

import org.springframework.stereotype.Service;
import uk.ac.ncl.cs.zequn.H2.LogAccess;
import uk.ac.ncl.cs.zequn.entity.Result;
import uk.ac.ncl.cs.zequn.entity.ResultCollection;
import uk.ac.ncl.cs.zequn.service.MonitorService;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

/**
 * Created by zequnli on 21/08/2014.
 */
@Service
public class MonitorServiceImpl implements MonitorService {
    LogAccess access = new LogAccess("log");
    private int count=0;
    public MonitorServiceImpl(){
        try {
            access.init();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void analyse(ResultCollection collection) {
        System.out.println("------------------------------");
        long current = System.currentTimeMillis();
        long computing = 0;
        long transfer = 0;
        for(Result result:collection.getResults()){
            computing+=result.getRealTimeWithoutNet()-result.getExceptedTime();
            transfer+=current-result.getRealTimeWithoutNet();
        }
        computing/=100;
        transfer/=100;
        println("Computing:" + computing);
        println("transfer:"+ transfer);
        access.insertTuple(count++ +"",computing+transfer +"");
    }

    @Override
    public void outputCSV(String fileName) {
        access.output2CSV("/Users/zequnli/FPTesting",fileName);
    }

    private MonitorServiceImpl print(String s){
        System.out.print(s);
        return this;
    }
    private MonitorServiceImpl println(String s){
        System.out.println(s);
        return this;
    }
}
