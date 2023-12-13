package com.example.stock.openapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

    public class FHKST01010100 {

        public static void main(String[] args) throws IOException {

            // 국내 주식 시세 조회
            String url = "https://openapi.koreainvestment.com:9443/uapi/domestic-stock/v1/quotations/inquire-price";
            String tr_id = "FHKST01010100";
            String data = "{\n" +
                    "    \"fid_cond_mrkt_div_code\": \"FID조건시장분류코드\",\n" +
                    "    \"fid_input_iscd\": \"FID입력종목코드\"\n" +
                    "}";

            httpPostBodyConnection(url,data,tr_id);
        }
        public static void httpPostBodyConnection(String UrlData, String ParamData,String TrId) throws IOException {
            String totalUrl = "";
            totalUrl = UrlData.trim().toString();

            URL url = null;
            HttpURLConnection conn = null;

            String responseData = "";
            BufferedReader br = null;

            StringBuffer sb = new StringBuffer();
            String returnData = "";

            try{
                url = new URL(totalUrl);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("authorization", "");
                conn.setRequestProperty("appKey", "{PSv7KA272Ztvq5laNkJhvtCszZv1gMJyLHLz}");
                conn.setRequestProperty("appSecret", "{h9YuY7ehQFHvR3cuow9wyDvTuJpemccx4lndHY+rvzonoq8T/QyvJ7kJ3aMQP7Q6/iqojTZmncUcp2W3EWR8sNfL54tCWfB5Zd4okPqNkAdsmo6iDO91l5UUo4Wj96mNcLuK5EH7QFWRPFOm5k/IlcI3OwmpxUFscV+3sy153WnprBlxGVc=}");
                conn.setRequestProperty("personalSeckey", "");
                conn.setRequestProperty("tr_id", "FHKST01010100TrId");
                conn.setRequestProperty("tr_cont", " ");
                conn.setRequestProperty("custtype", "P");
                conn.setRequestProperty("seq_no", "");
                conn.setRequestProperty("mac_address", "{F4-6A-DD-7C-3F-5D}");
                conn.setRequestProperty("phone_num", "P01011112222");
                conn.setRequestProperty("ip_addr", "{192.168.219.102}");
                conn.setRequestProperty("hashkey", "{Hash값}");
                conn.setRequestProperty("gt_uid", "{Global UID}");
                conn.setDoOutput(true);

                try (OutputStream os = conn.getOutputStream()) {
                    byte request_data[] = ParamData.getBytes("utf-8");
                    os.write(request_data);
                    os.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                conn.connect();
                System.out.println("http 요청 방식" + "POST BODY JSON");
                System.out.println("http 요청 타입" + "application/json");
                System.out.println("http 요청 주소" + UrlData);
                System.out.println("");

                br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            } catch (IOException e){
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
            } finally {
                try {
                    sb = new StringBuffer();
                    while ((responseData = br.readLine()) != null) {
                        sb.append(responseData);
                    }
                    returnData = sb.toString();
                    String responseCode = String.valueOf(conn.getResponseCode());
                    System.out.println("http 응답 코드 : " + responseCode);
                    System.out.println("http 응답 데이터 : " + returnData);
                    if (br != null){
                        br.close();
                    }
                } catch (IOException e){
                    throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
                }
            }
        }
    }
