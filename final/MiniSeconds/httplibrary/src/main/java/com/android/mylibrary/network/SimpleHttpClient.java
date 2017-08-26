package com.android.mylibrary.network;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017-07-23.
 */

public class SimpleHttpClient {

    private static final int TIME_OUT = 20;

    private static int doPost(String weburl, String method, Map<String, String> aParams) {

        int result = 0;
        String line = null;
        URL url = null;
        HttpURLConnection httpConn = null;

        try {
            // URL 객체를생성한다.
            url = new URL(weburl);

            // URL을통해HttpURLConnnection객체를
            httpConn = (HttpURLConnection) url.openConnection();

            // 연결TimeOut설정
            httpConn.setConnectTimeout(TIME_OUT * 1000);

            // Read TimeOut설정
            httpConn.setReadTimeout(TIME_OUT * 1000);

            // 요청방식선택 (GET, POST)
            httpConn.setRequestMethod(method);

            // HTTP 요청시에 Url encoded 방식으로 인코딩 후 전송한다.
            httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            // OutputStream으로 이미지 정보를 write 한다.
            OutputStream os = httpConn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(aParams));
            writer.flush();
            writer.close();
            os.close();

            // 서버요청에 대한 응답 코드를 받는다.
            int responseCode = httpConn.getResponseCode();

            // 200 ~ 299는 성공이다. 나머지는 에러를 리턴한다.
            if (responseCode >= 200 && responseCode < 300) {
                // 응답정보를InputStream에서 String 으로변경한다.
                InputStream in = httpConn.getInputStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(in));
                StringBuffer response = new StringBuffer();
                while ((line = rd.readLine()) != null) {
                    response.append(line);
                    response.append('\r');
                }

                rd.close();
                in.close();

                if (null != response && 0 != response.length()) {
                    if (response.toString().trim().equals("1")) {
                        result = 1; // 정보전송성공
                    } else {
                        result = 0; // 정보전송실패
                    }
                }
            } else {
                result = -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = -1;
        } finally {
            httpConn.disconnect();
        }
        return result;
    }

    private static String getPostDataString(Map<String, String> params) {
        boolean isFirst = true;
        StringBuilder result = new StringBuilder();

        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (true == isFirst) {
                    isFirst = false;
                } else {
                    result.append("&");
                }

                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result.toString();
    }
/*
    public static String getJson(String serverUrl,String host,String jsonobject) throws IOException {

        StringBuilder sb = new StringBuilder();

        String http = serverUrl;

        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(http);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");
            urlConnection.setUseCaches(false);
            urlConnection.setConnectTimeout(50000);
            urlConnection.setReadTimeout(50000);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Host", host);
            urlConnection.connect();
            //You Can also Create JSONObject here
            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            out.write(jsonobject);// here i sent the parameter
            out.close();
            int HttpResult = urlConnection.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        urlConnection.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                Log.e("new Test", "" + sb.toString());
                return sb.toString();
            } else {
                Log.e(" ", "" + urlConnection.getResponseMessage());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
        return null;
    }

    public static JSONObject doPostRequest(HashMap<String, String> data, String url) {

        try {
            RequestBody requestBody;
            MultipartBuilder mBuilder = new MultipartBuilder().type(MultipartBuilder.FORM);

            if (data != null) {


                for (String key : data.keySet()) {
                    String value = data.get(key);
                    Utility.printLog("Key Values", key + "-----------------" + value);

                    mBuilder.addFormDataPart(key, value);

                }
            } else {
                mBuilder.addFormDataPart("temp", "temp");
            }
            requestBody = mBuilder.build();


            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .build();

            OkHttpClient client = new OkHttpClient();
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            Utility.printLog("URL", url);
            Utility.printLog("Response", responseBody);
            return new JSONObject(responseBody);

        } catch (UnknownHostException | UnsupportedEncodingException e) {

            JSONObject jsonObject=new JSONObject();

            try {
                jsonObject.put("status","false");
                jsonObject.put("message",e.getLocalizedMessage());
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            Log.e(TAG, "Error: " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
            JSONObject jsonObject=new JSONObject();

            try {
                jsonObject.put("status","false");
                jsonObject.put("message",e.getLocalizedMessage());
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            Log.e(TAG, "Other Error: " + e.getLocalizedMessage());
        }
        return null;
    }
    */
}
