package cn.glutaa.service.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.*;
import java.util.Map;

public interface IHttpRequestService {

    <R> R sendGetRequestReturnData(String url, Class<R> dataType);

     <R> R sendGetRequestReturnData(String url, Object param, Class<R> dataType) throws IllegalAccessException;

     <R> ResponseEntity<R> sendGetRequest(String url, Class<R> dataType);

     <R> ResponseEntity<R> sendGetRequest(String url, Map<String,String> params, Class<R> dataType);

     <R> ResponseEntity<R> sendGetRequest(String url, Object params, Class<R> dataType);


     <T> ResponseEntity<T> sendPostRequest(String url, Class<T> dataType);

     <R> ResponseEntity<R> sendPostRequest(String url, Object param, Class<R> dataType);

     <T> T sendPostRequestReturnData(String url, Class<T> dataType);

     <T> T sendPostRequestReturnData(String url, Object param, Class<T> dataType);


     <R> ResponseEntity<R> sendHttpRequest(String url, HttpMethod method, HttpHeaders headers, Class<R> returnType) throws JsonProcessingException, IllegalAccessException;

     <R> ResponseEntity<R> sendGetRequest(String url, Object urlParams, HttpHeaders headers, Class<R> returnType) throws JsonProcessingException, IllegalAccessException;

     <R> ResponseEntity<R> sendPostRequest(String url, Object body, HttpHeaders headers, Class<R> returnType) throws JsonProcessingException, IllegalAccessException;

     <R> ResponseEntity<R> sendHttpRequest(String url, HttpMethod method, Object urlParams, Object body, HttpHeaders headers, Class<R> returnType) throws JsonProcessingException, IllegalAccessException;


}
