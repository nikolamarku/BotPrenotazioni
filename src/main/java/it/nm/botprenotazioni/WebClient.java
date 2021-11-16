package it.nm.botprenotazioni;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WebClient {

    private final CloseableHttpClient httpClient;

    public WebClient() {
        httpClient = HttpClients.createDefault();
    }


    public String get(String url) throws IOException {
        HttpGet request = new HttpGet(url);
        setHeaders(request);
        return contentAsString(httpClient.execute(request));
    }

    public String post(String url, Map<String, String> params) throws Exception {
        HttpPost request = new HttpPost(url);
        setHeaders(request);
        request.setEntity(new UrlEncodedFormEntity(toNameValuePairList(params)));
        return contentAsString(httpClient.execute(request));
    }

    private List<NameValuePair> toNameValuePairList(Map<String, String> params){
        return params
                .entrySet()
                .stream()
                .map( entry -> new BasicNameValuePair(entry.getKey(),entry.getValue()))
                .collect(Collectors.toList());
    }

    private void setHeaders(HttpRequest request){
        request.addHeader("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:93.0) Gecko/20100101 Firefox/93.0");
    }

    private String contentAsString(HttpResponse response) throws IOException {
        return EntityUtils.toString(response.getEntity());
    }

}
