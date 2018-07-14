package com.anchoco.service;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import okhttp3.CipherSuite;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

import org.json.JSONObject;
import java.util.Properties;

/**
 * Servlet implementation class SetEC
 */

//This class Sets up the payment and returns a token for further API calls

@WebServlet("/createPayment")
public class SetEC extends HttpServlet {
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
    private static String PAYMENTREQUEST_0_PAYMENTACTION ="";
    private static String PAYMENTREQUEST_0_CURRENCYCODE = "";
    private static String PAYMENTREQUEST_0_AMT = "";
    private static String PAYMENTREQUEST_0_ITEMAMT = "";
    private static String L_PAYMENTREQUEST_0_NAME0 = "";
    private static String L_PAYMENTREQUEST_0_NUMBER0 = "";
    private static String L_PAYMENTREQUEST_0_QTY0 = "";
    private static String L_PAYMENTREQUEST_0_AMT0 = "";
    private static String PAYMENTREQUEST_0_INSURANCEAMT = "";
    private static String PAYMENTREQUEST_0_TAXAMT = "";
    private static String PAYMENTREQUEST_0_SHIPDISCAMT = "";
    private static String PAYMENTREQUEST_0_HANDLINGAMT = "";
    private static String PAYMENTREQUEST_0_SHIPPINGAMT = "";
    private static String URL ;
    private static JSONObject obj;
    private static String userActionFlag;
    private static String BNCODE = "";
    private static String environment;

    public JSONObject SetEC_Call() throws Exception {
        
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
        
        Properties prop = new Properties();
        InputStream input = null;
        String filename = "config/config.properties";
        
        input = this.getClass().getClassLoader().getResourceAsStream(filename);  
        if(input==null){
            System.out.println("Sorry, unable to find "+filename);
            JSONObject error = new JSONObject();
            return error;
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
                nvpstr = nvpstr + "&PAYMENTREQUEST_0_INSURANCEAMT=" + URLEncoder.encode(PAYMENTREQUEST_0_INSURANCEAMT, "UTF-8");
                nvpstr = nvpstr + "&PAYMENTREQUEST_0_TAXAMT=" + URLEncoder.encode(PAYMENTREQUEST_0_TAXAMT, "UTF-8");
                nvpstr = nvpstr + "&PAYMENTREQUEST_0_SHIPDISCAMT=" + URLEncoder.encode(PAYMENTREQUEST_0_SHIPDISCAMT, "UTF-8");
                nvpstr = nvpstr + "&PAYMENTREQUEST_0_HANDLINGAMT=" + URLEncoder.encode(PAYMENTREQUEST_0_HANDLINGAMT, "UTF-8");
                nvpstr = nvpstr + "&PAYMENTREQUEST_0_SHIPPINGAMT=" + URLEncoder.encode(PAYMENTREQUEST_0_SHIPPINGAMT, "UTF-8");
                nvpstr = nvpstr + "&BUTTONSOURCE=" + URLEncoder.encode(BNCODE, "UTF-8");
                
                

                RequestBody body = RequestBody.create(mediaType, nvpstr);

                Request request = new Request.Builder()
                .url(URL)
                .post(body).addHeader("cache-control", "no-cache")
                .build();

                try {
                    Response response = client.newCall(request).execute();
                    String payload = response.body().string();
                    obj = deformatNVP(payload);

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
        
        System.out.println("From SetEC:\n"+obj);
        return obj;

    }

    public static JSONObject deformatNVP(String payload) {
        HashMap nvp = new HashMap();
        StringTokenizer stTok = new StringTokenizer(payload, "&");
        while (stTok.hasMoreTokens()) {
            StringTokenizer stInternalTokenizer = new StringTokenizer(stTok.nextToken(), "=");
            if (stInternalTokenizer.countTokens() == 2) {
                String key = URLDecoder.decode(stInternalTokenizer.nextToken());
                String value = URLDecoder.decode(stInternalTokenizer.nextToken());
                nvp.put(key.toUpperCase(), value);
            }
        }
        nvp.put("id", nvp.get("TOKEN"));
        JSONObject json = new JSONObject(nvp);
        return json;
    }
    
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
     {
        PrintWriter writer = null;// response.getWriter();
        try {
             writer = response.getWriter();
        PAYMENTREQUEST_0_CURRENCYCODE=request.getParameter("currencycode");
        PAYMENTREQUEST_0_AMT=request.getParameter("amt");
        PAYMENTREQUEST_0_ITEMAMT=request.getParameter("itemamt");
        L_PAYMENTREQUEST_0_NAME0=request.getParameter("name0");
        L_PAYMENTREQUEST_0_NUMBER0=request.getParameter("number0");
        L_PAYMENTREQUEST_0_QTY0=request.getParameter("qty0");
        L_PAYMENTREQUEST_0_AMT0=request.getParameter("amt0");
        PAYMENTREQUEST_0_INSURANCEAMT = request.getParameter("insuranceamt");
        PAYMENTREQUEST_0_TAXAMT = request.getParameter("tax");
        PAYMENTREQUEST_0_SHIPDISCAMT = request.getParameter("shipdiscamt");
        PAYMENTREQUEST_0_HANDLINGAMT = request.getParameter("handamt");
        PAYMENTREQUEST_0_SHIPPINGAMT = request.getParameter("shipamt");
        JSONObject obj1 = SetEC_Call();
        writer.println(obj1);
        }
        catch(Exception e){
            System.out.println(e);
            System.out.println("From e.getMessage():\n"+e.getMessage());
            String error = "status=Failure&Message="+URLEncoder.encode(e.getMessage());
            System.out.println("From json error message:\n"+error);
            JSONObject obj1 = deformatNVP(error);
            writer.println(obj1);
        }
    }
    

    private void setUserActionFlag(String userActionFlag) {
        this.userActionFlag = userActionFlag;
    }


}