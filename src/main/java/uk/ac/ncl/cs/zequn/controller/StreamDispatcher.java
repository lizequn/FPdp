package uk.ac.ncl.cs.zequn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ncl.cs.zequn.entity.StreamTuple;
import uk.ac.ncl.cs.zequn.service.MapperService;
import uk.ac.ncl.cs.zequn.service.Streamer;

/**
 * Created by zequnli on 23/07/2014.
 */
@Controller
public class StreamDispatcher {
    @Autowired
    private MapperService service;
    @Autowired
    private Streamer streamer;
    @RequestMapping(value = "tuple")
    @ResponseBody
    public void tuple(StreamTuple tuple){

    }




}
