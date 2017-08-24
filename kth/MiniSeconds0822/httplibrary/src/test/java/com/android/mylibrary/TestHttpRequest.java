package com.android.mylibrary;

import com.android.mylibrary.model.ModelPerson;
import com.android.mylibrary.network.HttpRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;


public class TestHttpRequest {

    // JSONObject 사용법 숙지
    @Test
    public void test_convert_string_to_json() throws Exception {
        JSONObject json = null;

        String  str = "{ 'key1':'value1', 'key2':'value2' }";
        json = new JSONObject( str );
        assertEquals( json.get("key1").toString(), "value1");
    }

    // JSONArray 사용법 숙지
    @Test
    public void test_make_json_object(){

        // { 'name':'student', 'course':['name','course1', {'information':'test', 'id':3} ] }

        String msg = "";

        try {
            // {'information':'test', 'id':3} 만들기
            JSONObject item = new JSONObject();
            item.put("information", "test");
            item.put("id"         ,     3 );

            JSONArray jarray = new JSONArray();
            jarray.put("name");
            jarray.put("course1");
            jarray.put( item );

            JSONObject json = new JSONObject();
            json.put("name", "student");
            json.put("course", jarray );

            msg = json.toString();

            assertEquals(json.get("name").toString(), "student");

            JSONArray test1  = (JSONArray) json.get("course");
            JSONObject test2 = (JSONObject) test1.get(2);
            assertEquals(test2.get("information").toString(), "test");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_get_currentversion(){
        String weburl = "http://192.168.0.238:8080/rest/currentversion";

        HttpRequest request  = null;
        String      response = "";

        try {
            request = new HttpRequest(weburl).addHeader("charset", "utf-8");
            int httpCode = request.get();

            if(HttpURLConnection.HTTP_OK == httpCode ) { // HttpURLConnection.HTTP_OK == 200
                response = request.getStringResponse();  // 서버 값이 리턴된다.
            }
            else {
                // error
            }

            assertTrue( !response.isEmpty() );
            assertNotEquals(response, "1" );
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            request.close();
        }
    }

    @Test
    public void test_get_login_success() {
        String weburl = "http://192.168.0.238:8080/rest/login";

        HttpRequest request = null;
        String response = "";

        try {
            request = new HttpRequest(weburl).addHeader("charset", "utf-8");
            request.addParameter("id","test1id");
            request.addParameter("pw","test1pw");
            int httpCode = request.post();

            if( httpCode == HttpURLConnection.HTTP_OK ){
                response = request.getStringResponse();
            }
            else {
                // error
            }
            assertEquals("1", response);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            request.close();
        }
    }
    
    @Test
    public void test_get_login_fails() {
        String weburl = "http://192.168.25.100:8080/rest/login";
        HttpRequest request = null;
        String response = null;

        int httpCode = 0;
        try {
            request = new HttpRequest(weburl).addHeader("charset"     , "utf-8");
            request.addParameter("id", "test0id");
            request.addParameter("pw", "test0pw");
            httpCode = request.post();

            if (HttpURLConnection.HTTP_OK == httpCode) {
                response = request.getStringResponse();
            } else {
                // log error
            }

            assertEquals(response, "0");
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            request.close();
        }
    }

    @Test
    public void test_get_json_object(){
        String weburl = "http://192.168.0.238:8080/rest/personone";

        HttpRequest request = null;
        JSONObject response = null;
        try {
            request = new HttpRequest(weburl).addHeader("charset","utf-8");
            request.addParameter("name","test3id");

            int httpCode = request.post();

            if (httpCode == HttpURLConnection.HTTP_OK){
                response = request.getJSONObjectResponse();

            }else{
                //error
            }

            assertEquals("valid"   , response.getString("id"   ));
            assertEquals("valname" , response.getString("name" ));
            assertEquals("valemail", response.getString("email"));
            assertEquals("valpw"   , response.getString("pw"   ));

            //json을 model 객체로 변환
            String jsonInString = response.toString();

            Gson gson = new Gson();
            ModelPerson person = gson.fromJson(jsonInString, ModelPerson.class);

            assertEquals("valid"   , person.getId()   );
            assertEquals("valname" , person.getName() );
            assertEquals("valemail", person.getEmail());
            assertEquals("valpw"   , person.getPw()   );

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            request.close();
        }
    }


    @Test
    public void test_personlist() {
        String weburl = "http://192.168.0.238:8080/rest/personlist";
        HttpRequest request = null;
        JSONArray response = null;

        try {
            request = new HttpRequest(weburl).addHeader("charset"     , "utf-8");
            int httpCode = request.post();

            if (HttpURLConnection.HTTP_OK == httpCode) {
                try {
                    response = request.getJSONArrayResponse();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // log error
            }

            assertNotNull( response );
            assertNotEquals(0, response.length() );

            JSONObject json = (JSONObject) response.get(0);
            assertEquals("test1id"   , json.getString("id"   ));
            assertEquals("test1name" , json.getString("name" ));
            assertEquals("test1email", json.getString("email"));
            assertEquals("test1pw"   , json.getString("pw"   ));

            //JSONArray를 List<ModelPerson> 객체로 변환
            String jsonInString = response.toString();
            List<ModelPerson> list = new Gson().fromJson(jsonInString, new TypeToken<List<ModelPerson>>(){}.getType());
            assertEquals("test1id"   , list.get(0).getId()    );
            assertEquals("test1name" , list.get(0).getName()  );
            assertEquals("test1email", list.get(0).getEmail() );
            assertEquals("test1pw"   , list.get(0).getPw()    );

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e1) {
            e1.printStackTrace();
        } finally {
            request.close();
        }
    }

    @Test
    public void test_insert_person() {
        String weburl = "http://192.168.0.238:8080/rest/insertperson";
        HttpRequest request = null;
        String   response = null;

        int httpCode = 0;
        try {
            // ModelPerson을 json으로 변환
            ModelPerson obj = new ModelPerson("valid", "valpw", "valname", "valemail");
            String data = new Gson().toJson(obj); // Java object to JSON

            request = new HttpRequest(weburl).addHeader("charset"     , "utf-8")
                                             .addHeader("Content-Type", "application/json")
                                             .addHeader("Accept"      , "application/json");

            httpCode = request.post(data);

            if (HttpURLConnection.HTTP_OK == httpCode) {
                try {
                    response = request.getStringResponse();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // log error
            }

            assertNotNull( response );
            assertEquals("1", response);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            request.close();
        }
    }



    @Test
    public void test_insert_person_list() {
        String weburl = "http://192.168.0.238:8080/rest/insertpersonlist";
        HttpRequest request = null;
        String   response = null;

        int httpCode = 0;
        try {
            // List를 JSonArray로 변환
            List<ModelPerson> list = new ArrayList<ModelPerson>();
            for( int i=0; i<10; i++){
                String t = new SimpleDateFormat("yyyyMMddHHmmss").format( Calendar.getInstance().getTime() );
                ModelPerson obj = new ModelPerson("valid"+t, "valpw"+t, "valname"+t, "valemail"+t);
                list.add(obj);
            }
            String data = new Gson().toJson(list);

            request = new HttpRequest(weburl).addHeader("charset"     , "utf-8")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept"      , "application/json");

            httpCode = request.post(data);

            if (HttpURLConnection.HTTP_OK == httpCode) {
                try {
                    response = request.getStringResponse();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // log error
            }

            assertNotNull( response );
            assertEquals("10", response);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            request.close();
        }
    }





    @Test
    public void test_personfind(){
        String weburl = "http://192.168.25.100:8080/rest/personfind";

        HttpRequest request = null;
        JSONArray  response = null;

        Gson gson = new Gson();

        try {
            ModelPerson searchvalue = new ModelPerson("valid", "valpw", "valname", "valemail");
            String orderby = "name" ; // Java object to JSON

            // Map을 json으로 변환
            Map<String, Object> map = new HashMap<>();
            map.put("searchvalue", searchvalue );
            map.put("orderby"    , orderby     );

            request = new HttpRequest(weburl).addHeader("charset"     , "utf-8")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept"      , "application/json");

            String data   = new Gson().toJson(map); // Java object to JSON
            int httpCode = request.post( data );

            if (httpCode == HttpURLConnection.HTTP_OK){
                response = request.getJSONArrayResponse();

            }else{
                //error
            }

            JSONObject json = (JSONObject) response.get(0);
            assertEquals("test1id"   , json.getString("id"   ));
            assertEquals("test1name" , json.getString("name" ));
            assertEquals("test1email", json.getString("email"));
            assertEquals("test1pw"   , json.getString("pw"   ));

            //json을 model 객체로 변환
            String jsonInString = response.toString();

            List<ModelPerson> list = gson.fromJson(jsonInString, new TypeToken<List<ModelPerson>>(){}.getType());
            assertEquals("test1id"   , list.get(0).getId   () );
            assertEquals("test1name" , list.get(0).getName () );
            assertEquals("test1email", list.get(0).getEmail() );
            assertEquals("test1pw"   , list.get(0).getPw   () );

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            request.close();
        }
    }

}