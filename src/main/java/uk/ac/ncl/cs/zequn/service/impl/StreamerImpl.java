package uk.ac.ncl.cs.zequn.service.impl;

import org.springframework.stereotype.Service;
import uk.ac.ncl.cs.zequn.entity.SingleTuple;
import uk.ac.ncl.cs.zequn.entity.StreamTuple;
import uk.ac.ncl.cs.zequn.service.Streamer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by zequnli on 23/07/2014.
 */
@Service
public class StreamerImpl implements Streamer {
    private static Random random = new Random();
    @Override
    public StreamTuple getTuple() {
        StreamTuple streamTuple = new StreamTuple();
        ArrayList<SingleTuple> s = new ArrayList<SingleTuple>();
        for (int i = 0; i < 100; i++) {
            s.add(create(10));
        }
        streamTuple.setTuples(s);
        return streamTuple;
    }
    private static SingleTuple create(int idRange){
        SingleTuple t = new SingleTuple();
        t.setServiceId(random.nextInt(idRange));
        t.setServiceTime(random.nextDouble()*100);
        t.setWaitingTime(random.nextDouble()*100);
        return t;
    }
}
