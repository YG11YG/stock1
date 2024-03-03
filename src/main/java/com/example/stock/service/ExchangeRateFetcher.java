package com.example.stock.service;

import java.io.BufferedReader; // 버퍼를 사용해 입력 스트림으로부터 텍스트를 읽기 위한 클래스를 임포트합니다.
import java.io.InputStreamReader; // 바이트 스트림을 문자 스트림으로 변환하기 위한 클래스를 임포트합니다.
import java.net.HttpURLConnection; // HTTP 요청을 보내기 위한 클래스를 임포트합니다.
import java.net.URL; // URL을 다루기 위한 클래스를 임포트합니다.

import org.json.JSONArray;
import org.json.JSONObject; // JSON 객체를 다루기 위한 클래스를 임포트합니다.
import org.springframework.stereotype.Service;

@Service
public class ExchangeRateFetcher {

    // 환율을 가져오는 메서드
    public static double getExchangeRate() throws Exception {
        String url = "https://m.search.naver.com/p/csearch/content/qapirender.nhn?key=calculator&pkid=141&q=환율&where=m&u1=keb&u6=standardUnit&u7=0&u3=USD&u4=KRW&u8=down&u2=1";
        URL obj = new URL(url); // URL 객체를 생성합니다.
        HttpURLConnection con = (HttpURLConnection) obj.openConnection(); // URL에 대한 연결을 엽니다.
        con.setRequestMethod("GET"); // HTTP 요청 방식을 GET으로 설정합니다.

        // 응답 데이터를 읽기 위한 BufferedReader를 생성합니다.
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine; // 읽은 데이터를 저장할 임시 변수를 선언합니다.
        StringBuffer response = new StringBuffer();// 응답 데이터를 저장할 StringBuffer 객체를 생성합니다.

        // 서버로부터 받은 데이터를 한 줄씩 읽어 response에 추가합니다.
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
       // System.out.println(response.toString() + "야호");

        in.close();   // BufferedReader를 닫습니다.

        // 응답 데이터를 문자열로 변환하고 JSON 객체로 파싱합니다.
        JSONObject myResponse = new JSONObject(response.toString());
        // JSON 객체에서 "value" 키에 해당하는 값을 추출합니다.
      //  System.out.println(myResponse.toString() + "야호호");

        JSONArray countryArray = myResponse.getJSONArray("country");
        JSONObject firstCountry = countryArray.getJSONObject(1);
        String value = firstCountry.getString("value");
       // System.out.println(value + "야호호"); // 출력 예시: 1야호호




        //콤마를 없애는 수식
        return Double.parseDouble(value.replace(",", ""));
    }

    // 1원당 몇 달러인지 계산하는 메서드
    public static double calculateUSDPerKRW(double value) {

        return 1 / value;
    }
}
