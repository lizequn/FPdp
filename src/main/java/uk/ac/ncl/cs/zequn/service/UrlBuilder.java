package uk.ac.ncl.cs.zequn.service;

/**
 * Created by zequnli on 23/07/2014.
 */
public class UrlBuilder {

    public static String getInitUrl(String baseUrl){
        return baseUrl+"/init";
    }
    public static String getMappingUrl(String baseUrl){
        return baseUrl+"/urlMapping";
    }
    public static String getCreateAggUlr(String baseUrl){
        return baseUrl+"/createAggregation";
    }
    public static String getStartUrl(String baseUrl){
        return baseUrl+"/start";
    }
    public static String getActiveUrl(String baseUrl){
        return baseUrl+"/active";
    }
    public static String getStreamUrl(String baseUrl){
        return baseUrl+"/stream";
    }
}
