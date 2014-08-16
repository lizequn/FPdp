package uk.ac.ncl.cs.zequn.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uk.ac.ncl.cs.zequn.entity.AggregationCreationEntity;
import uk.ac.ncl.cs.zequn.service.InstanceService;
import uk.ac.ncl.cs.zequn.service.UrlBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by zequnli on 16/08/2014.
 */
@Service
public class InstanceServiceImpl implements InstanceService {
    private static Logger logger = Logger.getLogger(InstanceServiceImpl.class.getName());

    private static int number =0;
    private static List<AggregationCreationEntity> list = new LinkedList<AggregationCreationEntity>();
    @Override
    public void createNewInstance() {
//        number++;
        String dir = "/Users/zequnli/FPTesting/FPco" + number++;
        String evn = "MAVEN_OPTS=\"-Xmx128m\"";
        String evns[] = new String[1];
        evns[0] = evn;
        String run = "/Library/apache-maven-3.2.1/bin/mvn tomcat7:run";
        try {
//            Runtime rt = Runtime.getRuntime();
//            //rt.exec(evn);
//            Process proc = rt.exec(run, evns, new File(dir));
//            InputStream s = proc.getErrorStream();
//
//            BufferedReader r = new BufferedReader(new InputStreamReader(s));
//            String ss;
//            while ((ss = r.readLine()) != null) {
//                System.out.println(ss);
//            }
            ProcessBuilder processBuilder = new ProcessBuilder("mvn","tomcat7:run");
            processBuilder.directory(new File(dir));
            processBuilder.environment().put("MAVEN_OPTS", "-Xmx256m -Xms256m");
            processBuilder.environment().putAll(System.getenv());
            processBuilder.start();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @Override
    public void storeAggregation(AggregationCreationEntity entity) {
        list.add(entity);
    }

    @Override
    public void resumeAggregation(String url,int count,List<String> mapper) {
        RestTemplate restTemplate = new RestTemplate();
        System.out.println(UrlBuilder.getInitUrl(url)+"/"+count+"/"+0+"/"+0);
        int i = restTemplate.getForObject(UrlBuilder.getInitUrl(url)+"/"+count+"/"+0+"/"+0,Integer.class);
           System.out.println(i);
            //set Mapper
        for(String s:mapper){
            restTemplate.postForObject(UrlBuilder.getMappingUrl(s),mapper,Integer.class);
        }
        for (AggregationCreationEntity entity:list) {
            restTemplate.postForEntity(UrlBuilder.getCreateAggUlr(url), entity, null);

        }
        restTemplate.getForObject(UrlBuilder.getStartUrl(url),Integer.class);
        logger.info(url+"init");
    }

    public static void main(String [] args){
            new InstanceServiceImpl().createNewInstance();
         }
    }


