package com.android.mylibrary;

import com.android.mylibrary.model.ModelPerson;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;


public class TestConvertJsonToModel {

    //  Convert JSON to Java object : fromJson
    @Test
    public void test_convert_json_to_model() throws IOException {

        Gson gson = new Gson();

        // 1. JSON to Java object, read it from a file.
        ModelPerson staff1 = gson.fromJson(new FileReader("D:\\file.json"), ModelPerson.class);

        // 2. JSON to Java object, read it from a Json String.
        String jsonInString = "{'name' : 'mkyong'}";
        ModelPerson staff2 = gson.fromJson(jsonInString, ModelPerson.class);

        // JSON to JsonElement, convert to String later.
        JsonElement json = gson.fromJson(new FileReader("D:\\file.json"), JsonElement.class);
        String result = gson.toJson(json);
    }

    // Convert Java object to JSON : toJson
    @Test
    public void test_convert_model_to_json() throws IOException {

        Gson gson = new Gson();
        ModelPerson obj = new ModelPerson("valid", "valpw", "valname", "valemail");

        // 1. Java object to JSON, and save into a file
        gson.toJson(obj, new FileWriter("D:\\file.json"));

        // 2. Java object to JSON, and assign to a String
        String jsonInString = new Gson().toJson(obj);
    }

    // read a JSON from a file
    @Test
    public void test_read_json_from_file() throws IOException {

        Gson gson = new Gson();

        Reader reader = new FileReader("D:\\file.json");

        // Convert JSON to Java Object
        ModelPerson staff = gson.fromJson(reader, ModelPerson.class);
        System.out.println(staff);

        // Convert JSON to JsonElement, and later to String
        /*JsonElement json = gson.fromJson(reader, JsonElement.class);
        String jsonInString = gson.toJson(json);
        System.out.println(jsonInString);*/
    }

    // Convert a JSON Array to a List, using TypeToken
    @Test
    public void test_convert_jsonarray_from_list() throws IOException {

        String json = "[{'name':'mkyong'}, {'name':'laplap'}]";
        List<ModelPerson> list = new Gson().fromJson(json, new TypeToken<List<ModelPerson>>(){}.getType());

        assertEquals( "mkyong", list.get(0).getName() );
        assertEquals( "laplap", list.get(1).getName() );
    }

    // Convert a JSON to a Map
    @Test
    public void test_convert_json_from_map() throws IOException {

        Gson gson = new Gson();

        String json = "{'name':'mkyong', 'age':33}";
        Map<String, Object> map = gson.fromJson(json, new TypeToken<Map<String, Object>>(){}.getType());


        assertEquals( "mkyong", map.get("name") );
        assertEquals( 33.0    , map.get("age") );
    }
}