package uk.ac.ncl.cs.zequn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ncl.cs.zequn.entity.ResultCollection;
import uk.ac.ncl.cs.zequn.service.MonitorService;

/**
 * Created by zequnli on 21/08/2014.
 */
@Controller
public class MonitorController {
    @Autowired
    private MonitorService service;
    @RequestMapping(value = "result")
    @ResponseBody
    public int result(@RequestBody ResultCollection collection){
        service.analyse(collection);
        return 0;
    }
    @RequestMapping(value = "outputCSV/{fileName}")
    @ResponseBody
    public int outputCSV(@PathVariable String fileName){
        service.outputCSV(fileName);
        return 0;
    }
}
