package com.spring63.rest.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;


/**
 * Handles requests for the application home page.
 */
@Controller
public class GcmController {

    private static final Logger logger = LoggerFactory.getLogger(GcmController.class);    

    private static final String PATH = "/api.key";

    private static final int MULTICAST_SIZE = 1000;

    private Sender sender;

    private static final Executor threadPool = Executors.newFixedThreadPool(5);
    
    public static class Env {
        public static String api_key = "AIzaSyAybSYtnZDggNTPPkeHkojaVPm5PLjS3og"; 
    }
    

    /**
     * Gets the access key.
     */
    protected String getKey() {
/*      
        InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(PATH);

        if (stream == null) {
            throw new IllegalStateException("Could not find file " + PATH + " on web resources)");
        }

        BufferedReader reader = new BufferedReader( new InputStreamReader(stream));
        
        try {
            String key = reader.readLine();
            
            return key;
        } catch (IOException e) {
            throw new RuntimeException("Could not read file " + PATH, e);
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                //logger.log(Level.WARNING, "Exception closing " + PATH, e);
            }
        }
        */
        
        return Env.api_key ;
    }

    @RequestMapping(value = "/gcm/List", method = RequestMethod.GET)
    public @ResponseBody String List(Locale locale, Model model) {
        logger.info("GcmController.List");

        List<String> list = Datastore.getDevices();
        
        String result = "";

        try {
            JSONObject jsonMain = new JSONObject();
            JSONArray jArray = new JSONArray();
            
            JSONObject jObject = null;

            for(int i = 0 ; i< list.size(); i++) {
                jObject = new JSONObject();
                jObject.put("regId"    , list.get(i) );   
                
                jArray.put(jObject);    
            }

            jsonMain.put("List", jArray);   
            
            result = jsonMain.toString();
        } catch (JSONException e) {
            e.printStackTrace();            
        }
                
        return result ;
    }

    @RequestMapping(value = "/gcm/Register", method = RequestMethod.POST)
    public @ResponseBody boolean Register(@RequestParam(value="regId", required = false, defaultValue = "") String regId
            , Model model) throws Throwable {
        logger.info("GcmController.Register");

        if(!StringUtils.isEmpty(regId)) {
            Datastore.register(regId);
            
            return true;
        }
        else {
            return false;            
        }
    }


    @RequestMapping(value = "/gcm/Unregister", method = RequestMethod.POST)
    public @ResponseBody boolean Unregister(@RequestParam(value="regId", required = false, defaultValue = "") String regId
            , Model model) throws Throwable {
        logger.info("GcmController.Unregister");

        if(!StringUtils.isEmpty(regId)) {
            Datastore.unregister(regId);
            
            return true;
        }
        else {
            return false;            
        }
    }

    @RequestMapping(value = "/gcm/SendAll", method = RequestMethod.GET)
    public String SendAllGet(Locale locale, Model model) {
        logger.info("GcmController.SendAllGet");        
        
        List<String> devices = Datastore.getDevices();
        String status = "";
        
        if (devices.isEmpty()) {
            status = "<h2>No devices registered!</h2>";
        } else {
            status += "<h2>" + devices.size() + " device(s) registered!</h2>";
            status += "<form name='form' method='POST' action='SendAllPost'>";
            status += "<input type='text' name='msg' value='input message' />";
            status += "<input type='submit' value='Send Message' />";
            status += "</form>";
        }

        model.addAttribute("body", status );
                
        return "gcm/SendAll";        
    }
    

    @RequestMapping(value = "/gcm/SendAllPost", method = RequestMethod.POST)
    public String SendAllPost(@RequestParam(value="msg", required = false, defaultValue = "") String msg
            , Model model) throws Throwable {
        logger.info("GcmController.SendAllPost");
        
        String key = getKey();
        sender =  new Sender(key);

        List<String> devices = Datastore.getDevices();
        String status;
        
        if (devices.isEmpty()) {
            status = "Message ignored as there is no device registered!";
        } else {
            // NOTE: check below is for demonstration purposes; a real
            // application
            // could always send a multicast, even for just one recipient
            if (devices.size() == 1) {
                // send a single message using plain post
                String registrationId = devices.get(0);
                Message message = new Message.Builder().build();
                
                try {
                    Result rlt = sender.send(message, registrationId, 5);                    
                    status = "Sent message to one device: " + rlt;
                } catch (IOException e) {
                    e.printStackTrace();                    
                }
            } else {
                // send a multicast message using JSON
                // must split in chunks of 1000 devices (GCM limit)
                int total = devices.size();
                int counter = 0;
                int tasks = 0;

                List<String> partialDevices = new ArrayList<String>(total);
                
                for (String device : devices) {
                    counter++;
                    partialDevices.add(device);
                    int partialSize = partialDevices.size();
                    
                    if (partialSize == MULTICAST_SIZE || counter == total) {
                        asyncSend(partialDevices, msg);
                        partialDevices.clear();
                        tasks++;
                    }
                }
                status = "Asynchronously sending " + tasks + " multicast messages to " + total + " devices";
            }
        }
        
        return "redirect:/gcm/SendAll";
        
    }

    private void asyncSend(List<String> partialDevices, String msg) {
        // make a copy
        final List<String> devices = new ArrayList<String>(partialDevices);
        final String data = new String(msg); 
        
        threadPool.execute(new Runnable() {

            public void run() {

                MulticastResult multicastResult;
                
                try {
                    Random random = new Random(System.currentTimeMillis());                
                    String messageCollapseKey = String.valueOf(Math.abs(random.nextInt()));
                    
    
                    // 푸시 메시지 전송을 위한 메시지 객체 생성 및 환경 설정
                    Message.Builder gcmMessageBuilder = new Message.Builder();
                    gcmMessageBuilder.collapseKey(messageCollapseKey).delayWhileIdle(true).timeToLive(60);
                    gcmMessageBuilder.addData("type","text");
                    gcmMessageBuilder.addData("command", "show");
                    gcmMessageBuilder.addData("data", URLEncoder.encode(data, "UTF-8"));
    
                    Message message = gcmMessageBuilder.build();
                    
                
                    multicastResult = sender.send(message, devices, 5);
                } catch (IOException e) {
                    logger.debug("Error posting messages", e);
                    return;
                }
                
                List<Result> results = multicastResult.getResults();
                
                // analyze the results
                for (int i = 0; i < devices.size(); i++) {
                    String regId = devices.get(i);
                    Result result = results.get(i);
                    String messageId = result.getMessageId();
                    
                    if (messageId != null) {
                        logger.info("Succesfully sent message to device: " + regId + "; messageId = " + messageId);
                        String canonicalRegId = result.getCanonicalRegistrationId();
                        
                        if (canonicalRegId != null) {
                            // same device has more than on registration id:
                            // update it
                            logger.info("canonicalRegId " + canonicalRegId);
                            Datastore.updateRegistration(regId, canonicalRegId);
                        }
                    } else {
                        String error = result.getErrorCodeName();
                        
                        if (error.equals(Constants.ERROR_NOT_REGISTERED)) {
                            // application has been removed from device -
                            // unregister it
                            logger.info("Unregistered device: " + regId);
                            Datastore.unregister(regId);
                        } else {
                            logger.info("Error sending message to " + regId + ": " + error);
                        }
                    }
                }
            }
        });
    }

}
