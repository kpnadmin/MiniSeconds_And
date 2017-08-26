package com.android.mylibrary.network;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import static java.net.HttpURLConnection.HTTP_OK;

/*
    https://stackoverflow.com/questions/4205980/java-sending-http-parameters-via-post-method-easily
    http://www.mkyong.com/java/how-to-automate-login-a-website-java-example/
    https://developer.android.com/reference/java/net/HttpURLConnection.html
    https://stackoverflow.com/questions/2026260/java-how-to-use-urlconnection-to-post-request-with-authorization
    https://stackoverflow.com/questions/32153318/httpclient-wont-import-in-android-studio

 */

public class HttpUtility {

    public static final int METHOD_GET = 0; // METHOD GET
    public static final int METHOD_POST = 1; // METHOD POST

    private static final int TIME_OUT = 20;

    // Callback interface
    public interface Callback {
        // abstract methods
        public void OnSuccess(String response);
        public void OnError(int status_code, String message);
    }
    // static method
    public void request(final String weburl, final int method, final Map < String, String > params, final Callback callback) {
        HttpURLConnection urlConnection = null;
        try {
            String urlstring = weburl;
            // write GET params,append with urlstring
            if (method == METHOD_GET && params != null) {
                for (Map.Entry < String, String > item: params.entrySet()) {
                    String key   = URLEncoder.encode(item.getKey(), "UTF-8");
                    String value = URLEncoder.encode(item.getValue(), "UTF-8");

                    if (!urlstring.contains("?")) {
                        urlstring += "?" + key + "=" + value;
                    } else {
                        urlstring += "&" + key + "=" + value;
                    }
                }
            }
            URL url = new URL(urlstring); // // URL 객체 생성
            urlConnection = (HttpURLConnection) url.openConnection(); // URL을 통해 HttpURLConnnection 열기
            urlConnection.setDoOutput(true); // write POST params
            urlConnection.setUseCaches(false);

            // 연결TimeOut설정
            urlConnection.setConnectTimeout(TIME_OUT * 1000);

            // Read TimeOut설정
            urlConnection.setReadTimeout(TIME_OUT * 1000);

            // HTTP 요청시에 Url encoded 방식으로 인코딩 후 전송한다.
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded"); // handle url encoded form data
            urlConnection.setRequestProperty("charset", "utf-8");

            // 요청방식선택 (GET, POST)
            if (method == METHOD_GET) {
                urlConnection.setRequestMethod("GET");
            }
            else if (method == METHOD_POST) {
                urlConnection.setRequestMethod("POST");
            }
            else {
                return;
            }

            //write POST data :: OutputStream으로 정보를 write 한다.
            if (method == METHOD_POST && params != null) {
                byte[] postDataBytes = getPostDataString(params).getBytes("UTF-8");
                urlConnection.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
                urlConnection.getOutputStream().write(postDataBytes);
            }

            // server response code
            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HTTP_OK && callback != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                    response.append('\r');
                }
                // callback success
                callback.OnSuccess(response.toString());
                reader.close(); // close BufferReader
            } else if (callback != null) {
                // callback error
                callback.OnError(responseCode, urlConnection.getResponseMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (callback != null) {
                // callback error
                callback.OnError(500, e.getLocalizedMessage());
            }
        }
        finally {
            if( urlConnection != null )
                urlConnection.disconnect(); // disconnect connection
        }
    }


    private String getPostDataString(Map<String, String> params) {
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
}