package com.anchoco.service;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import okhttp3.ConnectionSpec;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.TlsVersion;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;
import org.springframework.stereotype.Service;


/**
 * Servlet implementation class SetEC
 */

/*  This call takes the information from address.html and index.html and sets up the payment
    and returns a token for further API calls   */

@Service
public class SetEC_Mark extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    
    private static String USER;
    private static String PWD;
    private static String SIGNATURE;
    private static String VERSION ;
    private static String METHOD;
    private static String RETURNURL;
    private static String CANCELURL;
    private static String PAYMENTREQUEST_0_PAYMENTACTION;
    private static String PAYMENTREQUEST_0_CURRENCYCODE ;
    private static String PAYMENTREQUEST_0_AMT ;
    private static String PAYMENTREQUEST_0_ITEMAMT ;
    private static String L_PAYMENTREQUEST_0_NAME0 ;
    private static String L_PAYMENTREQUEST_0_NUMBER0 ;
    private static String L_PAYMENTREQUEST_0_QTY0;
    private static String L_PAYMENTREQUEST_0_AMT0 ;
    private static String URL;
    private static String PAYMENTREQUEST_0_SHIPTONAME;
    private static String PAYMENTREQUEST_0_SHIPTOSTREET ;
    private static String PAYMENTREQUEST_0_SHIPTOSTREET2 ;
    private static String PAYMENTREQUEST_0_SHIPTOCITY ;
    private static String PAYMENTREQUEST_0_SHIPTOSTATE ;
    private static String PAYMENTREQUEST_0_SHIPTOZIP ;
    private static String PAYMENTREQUEST_0_SHIPTOCOUNTRYCODE ;
    private static String PAYMENTREQUEST_0_SHIPTOCOUNTRYNAME;
    private static String PAYMENTREQUEST_0_SHIPTOPHONENUM ;
    private static JSONObject obj;
    private static String userActionFlag;
    private static String environment;
    private static String BNCODE = "";
    
    public void markFlow() throws Exception {
    
        ConnectionSpec cs = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS) // Set TLS VERSION 1.2
                .tlsVersions(TlsVersion.TLS_1_2)
                .build();
        List<ConnectionSpec> specs = new ArrayList<>();
            specs.add(cs);
            specs.add(ConnectionSpec.COMPATIBLE_TLS);
            specs.add(ConnectionSpec.CLEARTEXT);
            
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(36000, TimeUnit.SECONDS)
                .writeTimeout(36000, TimeUnit.SECONDS)
                .readTimeout(36000, TimeUnit.SECONDS)
                .connectionSpecs(specs)
                .build();
        MediaType mediaType = MediaType.parse("application/octet-stream");
        System.out.println("Hello from SetEC_Mark.java");
        Properties prop = new Properties();
        InputStream input = null;
        String filename = "application.properties";
        
        input = this.getClass().getClassLoader().getResourceAsStream(filename);
        if(input==null){
            System.out.println("Sorry, unable to find "+filename);
            return;
        }
        
        try {
            prop.load(input);
            this.setUserActionFlag(prop.getProperty("USERACTION_FLAG"));
            String strSandbox="";
            if(prop.getProperty("SANDBOX_FLAG").equals("true")){
                strSandbox="_SANDBOX";
                environment = "sandbox";
                USER = prop.getProperty("USER");
                PWD = prop.getProperty("PWD");
                SIGNATURE = prop.getProperty("SIGNATURE");
                URL = prop.getProperty("SANDBOX_URL");
            }
            else {
                USER = prop.getProperty("LIVE_USER");
                PWD = prop.getProperty("LIVE_PWD");
                SIGNATURE = prop.getProperty("LIVE_SIGNATURE");
                URL = prop.getProperty("LIVE_URL");
            }
            
            BNCODE = prop.getProperty("SBN_CODE");
            VERSION = prop.getProperty("VERSION");
            METHOD = prop.getProperty("SetEC_METHOD");
            RETURNURL = prop.getProperty("RETURNURL");
            CANCELURL = prop.getProperty("CANCELURL");
            PAYMENTREQUEST_0_PAYMENTACTION = prop.getProperty("PAYMENTREQUEST_0_PAYMENTACTION");
            
            try {
                String nvpstr = "USER=" + URLEncoder.encode(USER, "UTF-8");
                nvpstr = nvpstr + "&PWD=" + URLEncoder.encode(PWD, "UTF-8");
                nvpstr = nvpstr + "&SIGNATURE=" + URLEncoder.encode(SIGNATURE, "UTF-8");
                nvpstr = nvpstr + "&VERSION=" + URLEncoder.encode(VERSION, "UTF-8");
                nvpstr = nvpstr + "&METHOD=" + URLEncoder.encode(METHOD, "UTF-8");
                nvpstr = nvpstr + "&RETURNURL=" + URLEncoder.encode(RETURNURL, "UTF-8");
                nvpstr = nvpstr + "&CANCELURL=" + URLEncoder.encode(CANCELURL, "UTF-8");
                nvpstr = nvpstr + "&PAYMENTREQUEST_0_PAYMENTACTION=" + URLEncoder.encode(PAYMENTREQUEST_0_PAYMENTACTION, "UTF-8");
                nvpstr = nvpstr + "&PAYMENTREQUEST_0_CURRENCYCODE=" + URLEncoder.encode(PAYMENTREQUEST_0_CURRENCYCODE, "UTF-8");
                nvpstr = nvpstr + "&PAYMENTREQUEST_0_AMT=" + URLEncoder.encode(PAYMENTREQUEST_0_AMT, "UTF-8");
                nvpstr = nvpstr + "&PAYMENTREQUEST_0_ITEMAMT=" + URLEncoder.encode(PAYMENTREQUEST_0_ITEMAMT, "UTF-8");
                nvpstr = nvpstr + "&L_PAYMENTREQUEST_0_NAME0=" + URLEncoder.encode(L_PAYMENTREQUEST_0_NAME0, "UTF-8");
                nvpstr = nvpstr + "&L_PAYMENTREQUEST_0_NUMBER0=" + URLEncoder.encode(L_PAYMENTREQUEST_0_NUMBER0, "UTF-8");
                nvpstr = nvpstr + "&L_PAYMENTREQUEST_0_QTY0=" + URLEncoder.encode(L_PAYMENTREQUEST_0_QTY0, "UTF-8");
                nvpstr = nvpstr + "&L_PAYMENTREQUEST_0_AMT0=" + URLEncoder.encode(L_PAYMENTREQUEST_0_AMT0, "UTF-8");
                nvpstr = nvpstr + "&PAYMENTREQUEST_0_SHIPTONAME=" + URLEncoder.encode(PAYMENTREQUEST_0_SHIPTONAME, "UTF-8");
                nvpstr = nvpstr + "&PAYMENTREQUEST_0_SHIPTOSTREET=" + URLEncoder.encode(PAYMENTREQUEST_0_SHIPTOSTREET, "UTF-8");
                nvpstr = nvpstr + "&PAYMENTREQUEST_0_SHIPTOSTREET2=" + URLEncoder.encode(PAYMENTREQUEST_0_SHIPTOSTREET2, "UTF-8");
                nvpstr = nvpstr + "&PAYMENTREQUEST_0_SHIPTOCITY=" + URLEncoder.encode(PAYMENTREQUEST_0_SHIPTOCITY, "UTF-8");
                nvpstr = nvpstr + "&PAYMENTREQUEST_0_SHIPTOSTATE=" + URLEncoder.encode(PAYMENTREQUEST_0_SHIPTOSTATE, "UTF-8");
                nvpstr = nvpstr + "&PAYMENTREQUEST_0_SHIPTOZIP=" + URLEncoder.encode(PAYMENTREQUEST_0_SHIPTOZIP, "UTF-8");
                nvpstr = nvpstr + "&PAYMENTREQUEST_0_SHIPTOCOUNTRYCODE=" + URLEncoder.encode(PAYMENTREQUEST_0_SHIPTOCOUNTRYCODE, "UTF-8");
                nvpstr = nvpstr + "&PAYMENTREQUEST_0_SHIPTOCOUNTRYNAME=" + URLEncoder.encode(PAYMENTREQUEST_0_SHIPTOCOUNTRYNAME, "UTF-8");
                nvpstr = nvpstr + "&PAYMENTREQUEST_0_SHIPTOPHONENUM=" + URLEncoder.encode(PAYMENTREQUEST_0_SHIPTOPHONENUM, "UTF-8");
                nvpstr = nvpstr + "&BUTTONSOURCE=" + URLEncoder.encode(BNCODE, "UTF-8");
                

                
                RequestBody body = RequestBody.create(mediaType, nvpstr);
                
                Request request = new Request.Builder()
                .url(URL)
                .post(body)
                .addHeader("cache-control", "no-cache")
                .build();
                
                try {
                    Response response = client.newCall(request).execute();
                    String payload = response.body().string();
                    obj=deformatNVP(payload);               
                    
                    
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new Exception("Client can't execute the call to the server!");
                }
                
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                throw new Exception("URL Encoding Error!");
            }
        } catch (IOException e1) {
            e1.printStackTrace();
            throw new Exception("Error in getting the values for NVP!");
        }
        
    }
    
    public static JSONObject deformatNVP(String payload) {
        HashMap nvp = new HashMap(); 
        StringTokenizer stTok = new StringTokenizer( payload, "&");
        while (stTok.hasMoreTokens())
        {
            StringTokenizer stInternalTokenizer = new StringTokenizer( stTok.nextToken(), "=");
            if (stInternalTokenizer.countTokens() == 2)
            {
                String key = URLDecoder.decode( stInternalTokenizer.nextToken());
                String value = URLDecoder.decode( stInternalTokenizer.nextToken());
                nvp.put( key.toUpperCase(), value );
            }
        }
        nvp.put("id",nvp.get("TOKEN"));
        JSONObject json = new JSONObject(nvp);
        return json;
    }

    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        PrintWriter writer = null;
        try {
            System.out.println("request : " + request );
            System.out.println("response : " + response );
            writer = response.getWriter();
            PAYMENTREQUEST_0_SHIPTONAME = request.getParameter("first_name")+" "+request.getParameter("last_name");
            PAYMENTREQUEST_0_SHIPTOSTREET = request.getParameter("address");
            PAYMENTREQUEST_0_SHIPTOSTREET2 = request.getParameter("address_2");
            PAYMENTREQUEST_0_SHIPTOCITY = request.getParameter("city");
            PAYMENTREQUEST_0_SHIPTOSTATE = request.getParameter("state");
            PAYMENTREQUEST_0_SHIPTOZIP = request.getParameter("zip");
            PAYMENTREQUEST_0_SHIPTOCOUNTRYCODE = request.getParameter("countrycode");
            PAYMENTREQUEST_0_SHIPTOCOUNTRYNAME = request.getParameter("countryname");
            PAYMENTREQUEST_0_SHIPTOPHONENUM = request.getParameter("phone");
            PAYMENTREQUEST_0_CURRENCYCODE = URLDecoder.decode(request.getParameter("currencycode"));
            PAYMENTREQUEST_0_AMT = URLDecoder.decode(request.getParameter("amt"));
            PAYMENTREQUEST_0_ITEMAMT = URLDecoder.decode(request.getParameter("itemamt")); 
            L_PAYMENTREQUEST_0_NAME0 = URLDecoder.decode(request.getParameter("name0"));
            L_PAYMENTREQUEST_0_NUMBER0 = URLDecoder.decode(request.getParameter("number0"));
            L_PAYMENTREQUEST_0_QTY0 = URLDecoder.decode(request.getParameter("qty0"));
            L_PAYMENTREQUEST_0_AMT0 = URLDecoder.decode(request.getParameter("amt0"));
        
            markFlow();
        
            System.out.println("From SetEC_Mark:\n"+obj);
            writer.println(obj);
            }
        
        catch(Exception e) {
            String error = "status=Failure&Message="+URLEncoder.encode(e.getMessage());
            System.out.println("From SetEC_Mark:\n"+error);
            JSONObject obj1 = deformatNVP(error);
            writer.println(obj1);
            }

    }
    
    
    
    private void setUserActionFlag(String userActionFlag) {
        this.userActionFlag = userActionFlag;
    }

}