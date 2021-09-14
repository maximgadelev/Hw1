package ru.kpfu.itis.gadelev.net;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        Api api = new Api();
        Map<String, String> params = new HashMap<>();
        Map<String, String> headers = new HashMap<>();
       headers.put("Content-Type", "application/json");
        headers.put("Accept", "application/json");
        headers.put("Authorization", "Bearer " + "a3d70ee6db24230185f738f9b0653a40c2747ae5f52ef65c03520eaca00e8495");
        params.put("name", "Maxim");
        params.put("gender","male");
        params.put("email","maximgadelev223@mail.ru");
        params.put("status","active");
        api.post("https://gorest.co.in/public/v1/users",headers,params);
    }
}
