package com.Soo_Shinsa.address;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
@RequestMapping("/url")
public class UrlGet {

    @GetMapping("/get")
    public void httpGetMethod(@RequestParam String keyword) throws IOException {

        String requestURL = "https://business.juso.go.kr/addrlink/addrLinkApiJsonp.do?";

        // 승인키 포함 필수 요청 변수 세팅을 위한 StringBuilder 선언
        StringBuilder sb = new StringBuilder();

        // 필수 요청 변수
        sb.append(requestURL);
        sb.append("confmKey=" + "devU01TX0FVVEgyMDI1MDEzMTEwMjAyNjExNTQzNTI="); // 승인키
        sb.append("&currentPage=" + 1);
        sb.append("&countPerPage" + 10);
        sb.append("&keyword=" + keyword);

        // URLConnection api 연결
        URL url = new URL(sb.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setConnectTimeout(5000);
        conn.connect();

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        conn.disconnect();

        System.out.println(response.toString());
    }
}