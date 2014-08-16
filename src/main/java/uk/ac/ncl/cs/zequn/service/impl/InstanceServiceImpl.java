package uk.ac.ncl.cs.zequn.service.impl;

import org.springframework.stereotype.Service;
import uk.ac.ncl.cs.zequn.service.InstanceService;

import java.io.*;

/**
 * Created by zequnli on 16/08/2014.
 */
@Service
public class InstanceServiceImpl implements InstanceService {
    private static int number =0;
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
        public static void main(String [] args){
            new InstanceServiceImpl().createNewInstance();
         }
    }


