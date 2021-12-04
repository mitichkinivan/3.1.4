package test.rest;

import test.rest.entity.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class App 
{
    static final String URL = "http://91.241.64.178:7081/api/users";

    public static void main( String[] args ) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        String result;

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(URL, String.class);
        System.out.println(responseEntity);

        //Достаем cookie
        String sessionId = responseEntity.getHeaders().getFirst("Set-Cookie");

        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("Cookie", sessionId);

        //Первая часть кода
        User user = new User(3L, "James", "Brown", (byte) 17);

        ResponseEntity<String> firstPart = restTemplate.exchange(URL, HttpMethod.POST, new HttpEntity<>(user, httpHeaders), String.class);
        result = firstPart.getBody();
        System.out.println(result);

        //Вторая часть кода
        User updUser = new User(3L, "Thomas", "Shelby", (byte) 17);

        ResponseEntity<String> secondPart = restTemplate.exchange(URL, HttpMethod.PUT, new HttpEntity<>(updUser, httpHeaders), String.class);
        result += secondPart.getBody();
        System.out.println(result);

        //Третья часть кода
        ResponseEntity<String> thirdPart = restTemplate.exchange(URL + "/3", HttpMethod.DELETE, new HttpEntity<String>(httpHeaders), String.class);
        result += thirdPart.getBody();
        System.out.println(result);
    }

}
