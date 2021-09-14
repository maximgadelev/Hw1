package ru.kpfu.itis.gadelev.net;

import ru.kpfu.itis.gadelev.util.HttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class Api implements HttpClient {
    @Override
    public String get(String url, Map<String, String> headers, Map<String, String> params) {
        StringBuilder content = new StringBuilder();
        String input;
        try {
            URL currentUrl = new URL(makeUrlForGet(url, params));
            HttpURLConnection connection = (HttpURLConnection) currentUrl.openConnection();
            connection.setRequestMethod("GET");

            for (String header : headers.keySet()
            ) {
                connection.setRequestProperty(header, headers.get(header));
            }
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())
            )) {
                while ((input = reader.readLine()) != null) {
                    content.append(input);
                }
                System.out.println(content.toString());
            }
            connection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    @Override
    public String post(String url, Map<String, String> headers, Map<String, String> params){
        StringBuilder content = new StringBuilder();
        String input;
        try {
            URL currentUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) currentUrl.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            for (String header : headers.keySet()
            ) {
                connection.setRequestProperty(header, headers.get(header));
            }
            String inputString=makeParamForPost(params);
            try (OutputStream outputStream =connection.getOutputStream()) {
                byte[] inputStringBytes = inputString.getBytes(StandardCharsets.UTF_8);
                outputStream.write(inputStringBytes, 0, inputStringBytes.length);
            }
            try (BufferedReader reader =
                         new BufferedReader(
                                 new InputStreamReader(connection.getInputStream(),StandardCharsets.UTF_8)
                         )
            ) {
                while ((input = reader.readLine()) != null) {
                    content.append(input.trim());
                }
                System.out.println(content.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content.toString();
    }

    public String makeUrlForGet(String url, Map<String, String> params) {
        url = url + "?";
        StringBuilder builder = new StringBuilder(url);
        for (String param : params.keySet()
        ) {
            builder.append(param).append("=").append(params.get(param)).append("&");
        }
        return builder.toString();
    }
    public String makeParamForPost(Map<String,String> params){
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        for (String key : params.keySet()) {
            builder.append("\"").append(key).append("\":")
                    .append("\"").append(params.get(key)).append("\"").append(", ");
        }
        builder.delete(builder.length()-2,builder.length());
        builder.append("}");
        return builder.toString();
    }
}