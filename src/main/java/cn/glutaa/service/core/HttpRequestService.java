package cn.glutaa.service.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@Component
public class HttpRequestService implements IHttpRequestService {

    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;

    public HttpRequestService(RestTemplate restTemplate, ObjectMapper mapper){
        this.restTemplate = restTemplate;
        this.mapper = mapper;
    }


    public <R> R sendGetRequestReturnData(String url, Class<R> dataType) {
        return restTemplate.getForObject(url, dataType);
    }

    public <R> R sendGetRequestReturnData(String url, Object param, Class<R> dataType) throws IllegalAccessException {
        Class<?> aClass = param.getClass();
        List<String> paramsList =new ArrayList<>();
        for (Field declaredField : aClass.getDeclaredFields()) {
            declaredField.setAccessible(Boolean.TRUE);
            Object o = declaredField.get(param);
            paramsList.add(declaredField.getName()+"="+o);
        }
        url = url+"?"+ String.join("&", paramsList);
        //不支持headers设置
        ResponseEntity<R> responseEntity = restTemplate.getForEntity(url, dataType);
        return responseEntity.getBody();
    }

    public <R> ResponseEntity<R> sendGetRequest(String url, Class<R> dataType){
        return restTemplate.getForEntity(url, dataType, (Object) null);
    }

    public <R> ResponseEntity<R> sendGetRequest(String url, Map<String,String> params, Class<R> dataType){
        return restTemplate.getForEntity(url, dataType, params);
    }

    public <R> ResponseEntity<R> sendGetRequest(String url, Object params, Class<R> dataType){
        return restTemplate.getForEntity(url, dataType, params);
    }


    public <T> ResponseEntity<T> sendPostRequest(String url, Class<T> dataType) {
        return restTemplate.postForEntity(url, null, dataType);
    }

    public <R> ResponseEntity<R> sendPostRequest(String url, Object param, Class<R> dataType) {
        return restTemplate.postForEntity(url, param, dataType);
    }

    public <T> T sendPostRequestReturnData(String url, Class<T> dataType) {
        return restTemplate.postForObject(url, null, dataType);
    }

    public <T> T sendPostRequestReturnData(String url, Object param, Class<T> dataType) {
        return restTemplate.postForObject(url, param, dataType);
    }


    public <R> ResponseEntity<R> sendHttpRequest(String url, HttpMethod method, HttpHeaders headers, Class<R> returnType) throws JsonProcessingException, IllegalAccessException {
        return this.sendHttpRequest(url, method, null, null, headers, returnType);
    }

    public <R> ResponseEntity<R> sendGetRequest(String url, Object urlParams, HttpHeaders headers, Class<R> returnType) throws JsonProcessingException, IllegalAccessException {
        return this.sendHttpRequest(url, HttpMethod.GET, urlParams, null, headers, returnType);
    }

    public <R> ResponseEntity<R> sendPostRequest(String url, Object body, HttpHeaders headers, Class<R> returnType) throws JsonProcessingException, IllegalAccessException {
        return this.sendHttpRequest(url, HttpMethod.POST, null, body, headers, returnType);
    }

    public <R> ResponseEntity<R> sendHttpRequest(String url, HttpMethod method, Object urlParams, Object body, HttpHeaders headers, Class<R> returnType) throws JsonProcessingException, IllegalAccessException {

        if(urlParams != null){
            Class<?> aClass = urlParams.getClass();
            List<String> paramsList =new ArrayList<>();
            for (Field declaredField : aClass.getDeclaredFields()) {
                declaredField.setAccessible(Boolean.TRUE);
                Object o = declaredField.get(urlParams);
                paramsList.add(declaredField.getName()+"="+o);
            }
            url = url+"?"+ String.join("&", paramsList);
        }

        if(headers == null)
            headers = new HttpHeaders();
        if(headers.getContentType() == null)
            headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity request;
        if(body != null){
            String obj = mapper.writeValueAsString(body);
            request = new HttpEntity(obj, headers);
        }else
            request = new HttpEntity(headers);
        return restTemplate.exchange(url, method == null ? HttpMethod.GET : method, request, returnType);
    }

}
