package uk.ac.ncl.cs.zequn.service.impl;

import org.springframework.stereotype.Service;
import uk.ac.ncl.cs.zequn.entity.StreamTuple;
import uk.ac.ncl.cs.zequn.service.Streamer;

/**
 * Created by zequnli on 23/07/2014.
 */
@Service
public class StreamerImpl implements Streamer {
    @Override
    public StreamTuple getTuple() {
        StreamTuple streamTuple = new StreamTuple();
        streamTuple.setInfo(100);
        return streamTuple;
    }
}
