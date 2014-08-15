package uk.ac.ncl.cs.zequn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import uk.ac.ncl.cs.zequn.entity.ActiveEntity;
import uk.ac.ncl.cs.zequn.entity.AggregationCreationEntity;
import uk.ac.ncl.cs.zequn.entity.Index;
import uk.ac.ncl.cs.zequn.entity.Result;
import uk.ac.ncl.cs.zequn.service.MapperService;
import uk.ac.ncl.cs.zequn.service.Streamer;
import uk.ac.ncl.cs.zequn.service.UrlBuilder;
import uk.ac.ncl.cs.zequn.service.WorkerService;
import uk.ac.ncl.cs.zequn.service.impl.MapperServiceImpl;
import uk.ac.ncl.cs.zequn.service.impl.WorkerServiceImpl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by zequnli on 23/07/2014.
 */
@Controller
public class WorkerController implements WorkerListener {
    private static Logger logger = Logger.getLogger(WorkerController.class.getName());
    private static int currentId;
    private static String current = null;
    private static boolean flag =false;
    private static RestTemplate restTemplate = new RestTemplate();
    public WorkerController(){
        new MapperServiceImpl().setActiveListener(this);

    }
    @Autowired
    private MapperService service;
    @Autowired
    private Streamer streamer;
    @Autowired
    private WorkerService workerService;
    @RequestMapping(value = "register/{url}")
    @ResponseBody
    public int register(@PathVariable String url){
        int id = service.register("Http://"+url);
        logger.info(id+"");
        return id;
    }

    @RequestMapping(value = "getMapper")
    @ResponseBody
    public List<String> getMapper(){
        return service.getMapper();
    }

    @RequestMapping(value = "changeActive/{oldId}/{newId}")
    @ResponseBody
    public int changeActive(@PathVariable int oldId,@PathVariable int newId,@RequestBody LinkedList<Index> index){
        if(current == null){
            current = service.getMapper().get(newId);
            currentId = newId;
        }else {
            if(service.getActive() == oldId){
                //active B
                String url = UrlBuilder.getActiveUrl(service.getMapper().get(newId));
                int statusB = restTemplate.postForObject(url, index, Integer.class);
                //set stream
                current = service.getMapper().get(newId);
            }
            else {
                throw new IllegalStateException();
            }
        }
        return 1;
    }

    @RequestMapping(value = "initWorker/{slice}/{range}")
    @ResponseBody
    public int initWorker(@PathVariable int slice,@PathVariable int range){
        logger.info("initWorker");
        List<String> map = service.getMapper();
        //init

        int pre = 0;
        int next = 0;
        int count = 0;
        for(String s:map){
            //init
            pre = count-1;
            next = count+1;
            if(pre<0) pre = map.size()-1;
            if(next>=map.size()) next = 0;
            logger.info(UrlBuilder.getInitUrl(s));
            restTemplate.getForObject(UrlBuilder.getInitUrl(s)+"/"+count+"/"+pre+"/"+next,Integer.class);
            //set Mapper
            restTemplate.postForObject(UrlBuilder.getMappingUrl(s),map,Integer.class);
            //set aggregation
            AggregationCreationEntity entity = new AggregationCreationEntity();
            entity.setId(0);
            entity.setRange(range);
            entity.setSlice(slice);
            entity.setAggStrategy("AVG");
            restTemplate.postForEntity(UrlBuilder.getCreateAggUlr(s),entity,null);
            //start server
            restTemplate.getForObject(UrlBuilder.getStartUrl(s),Integer.class);
            count++;
        }
        return count;
    }

    @RequestMapping(value = "startStream")
    @ResponseBody
    public void startStream(){
        if(flag){
            flag = false;
        }else {
            flag = true;
            current = service.getMapper().get(0);
            currentId = 0;
            List<String> map = service.getMapper();
            LinkedList<Index> index = new LinkedList<Index>();
//            LinkedList<Result> results = new LinkedList<Result>();
            ActiveEntity entity = new ActiveEntity();
            entity.setIndex(index);
            entity.setResult(null);
            restTemplate.postForEntity(UrlBuilder.getActiveUrl(map.get(0)),entity,null);
            workerService.start(service.getMapper(),this);
            logger.info("startStream "+currentId+":"+current);

            new Thread(new Td()).start();
        }
    }
    private void stream(){
        if(flag){
            flag = false;
            logger.info("stop stream");
        }else {
            flag = true;
            logger.info("begin stream "+currentId+":"+current);
            new Thread(new Td()).start();
        }
    }


//    @Override
//    public void setActive(String url) {
//        logger.info("active:"+url);
//        current = url;
//    }

    @Override
    public void changeActive() throws InterruptedException {
        logger.info("beforeChange "+currentId+":"+current);
        String oldUrl = current;
        String newUrl ;
        if(currentId+1 >= service.getMapper().size()){
            newUrl = service.getMapper().get(0);
            currentId = 0;
        }else {
            newUrl = service.getMapper().get(currentId+1);
            currentId = currentId+1;
        }
        String urlA = UrlBuilder.getStopActiveUrl(oldUrl);
        String urlB = UrlBuilder.getActiveUrl(newUrl);
        this.stream();
        ActiveEntity entity = restTemplate.getForObject(urlA,ActiveEntity.class);
        int re = restTemplate.postForObject(urlB,entity,Integer.class);
        current = newUrl;
        logger.info("afterChange "+currentId+":"+current);
        this.stream();
    }

    class Td implements Runnable{

        @Override
        public void run() {
            while (flag) {
                restTemplate.postForEntity(UrlBuilder.getStreamUrl(current), streamer.getTuple(), null);
            }
        }
    }
}
