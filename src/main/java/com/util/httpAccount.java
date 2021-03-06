/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import com.jsonEntitys.Account;
import com.jsonEntitys.AccountLight;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author jorge
 */
public class httpAccount {

    public Account getAccountObject(String tel) {
        Account account = new Account();
        String resultado = null;
        resultado = this.getHttpString(tel);

        System.out.println("Convirtiendo el resultado del servidor " + resultado);

        if (resultado != null) {
            String jsonResult = resultado;
            // System.out.println(sb.toString());
            //System.out.println("\n\n--------------------OBTENEMOS OBJETO JSON NATIVO DE LA PAGINA, USAMOS EL ARRAY DATA---------------------------\n\n");
            JSONObject objJason = new JSONObject(jsonResult);
            JSONArray dataJson = new JSONArray();

            String codigo = objJason.optString("code");
            System.out.println("el codigo devuelto del servidor es " + codigo);

            if (codigo.compareTo("200") == 0) {
                
                dataJson = objJason.getJSONArray("data");
                String mensaje = objJason.optString("message");

                //System.out.println("objeto normal 1 "+dataJson.toString());
                //
                //
                // System.out.println("\n\n--------------------CREAMOS UN STRING JSON2 REEMPLAZANDO LOS CORCHETES QUE DAN ERROR---------------------------\n\n");
                String jsonString2 = dataJson.toString();
                String temp = dataJson.toString();
                temp = jsonString2.replace("[", "");
                jsonString2 = temp.replace("]", "");
                // System.out.println("new json string"+jsonString2);

                JSONObject objJson2 = new JSONObject(jsonString2);
                // System.out.println("el objeto simple json es " + objJson2.toString());

                // System.out.println("\n\n--------------------CREAMOS UN OBJETO JSON CON EL ARRAR ACCOUN---------------------------\n\n");
                String account1 = objJson2.optString("account");
                // System.out.println(account1);
                JSONObject objJson3 = new JSONObject(account1);
                //   System.out.println("el ULTIMO OBJETO SIMPLE ES  " + objJson3.toString());
                //   System.out.println("\n\n--------------------EMPEZAMOS A RECIBIR LOS PARAMETROS QUE HAY EN EL OBJETO JSON---------------------------\n\n");
                String firstName = objJson3.getString("first_name");
               // System.out.println(firstName);
               // System.out.println(objJson3.get("language_id"));
                //  System.out.println("\n\n--------------------TRATAMOS DE PASAR TODO EL ACCOUNT A OBJETO JAVA---------------------------\n\n");
                Gson gson = new Gson();
                account = gson.fromJson(objJson3.toString(), Account.class);
                //System.out.println(account.getFirst_name());
                // System.out.println(account.getCreation());
                account.setLanguaje_id(objJson3.get("language_id").toString());
                account.setId(objJson3.get("id").toString());
                account.setBalance(objJson3.get("balance").toString());

            } else {

                                System.out.println("El servidor no responde" + codigo);

               

            }
        } else {
            System.out.println("El servidor respondio nulo");
        }
         System.out.println("el id del account es " + account.getId());
        return account;
    }

    public String getHttpString(String tel) {
        String resultado = null;

        String telefono = tel.replace("-", "");
        System.out.println("OBTENER SOLO UN ARRAY DE CADENA JSON");
        //String myURL = "http://192.168.5.44/app_dev.php/cus/getaccount/50241109321.json";
        String myURL = "http://192.168.5.44/app_dev.php/cus/getaccount/" + telefono + ".json";
        System.out.println("Requested URL:" + myURL);
        StringBuilder sb = new StringBuilder();
        URLConnection urlConn = null;
        InputStreamReader in = null;

        try {

            URL url = new URL(myURL);
            urlConn = url.openConnection();

            if (urlConn != null) {

                urlConn.setReadTimeout(5 * 1000);
                if (urlConn.getInputStream() != null) {

                    in = new InputStreamReader(urlConn.getInputStream(),
                            Charset.defaultCharset());

                    BufferedReader bufferedReader = new BufferedReader(in);
                    if (bufferedReader != null) {
                        int cp;
                        while ((cp = bufferedReader.read()) != -1) {
                            sb.append((char) cp);
                        }
                        bufferedReader.close();
                    }
                } else {
                    System.out.print("no se logor leer en inpustream");
                }
                resultado = sb.toString();

            } else {
                System.out.print("no se pudo conectar con el servidor");

            }

        } catch (Exception e) {
            // e.printStackTrace();
            //System.out.println("SERVIDOR NO RESPONDE");

        }
        System.out.println("se ha devuelto algo del servidor" + resultado);
        return resultado;
    }

    public String setAccountObject(AccountLight account, String user) throws UnsupportedEncodingException, MalformedURLException, IOException {

        String respuesta = null;
        String myUrl = "http://192.168.5.44/app_dev.php/cus/setaccount/" + user + ".json";
        URL url = new URL(myUrl);
        try {

            //abrimos la conexiÃ³n
            URLConnection conn = url.openConnection();
            //especificamos que vamos a escribir
            conn.setDoOutput(true);

            String data = URLEncoder.encode("firstName", "UTF-8") + "=" + URLEncoder.encode(account.getFirstName(), "UTF-8");
            data += "&" + URLEncoder.encode("lastName", "UTF-8") + "=" + URLEncoder.encode(account.getLastName(), "UTF-8");
            data += "&" + URLEncoder.encode("address", "UTF-8") + "=" + URLEncoder.encode(account.getAddress(), "UTF-8");
            data += "&" + URLEncoder.encode("city", "UTF-8") + "=" + URLEncoder.encode(account.getCity(), "UTF-8");
            data += "&" + URLEncoder.encode("postalCode", "UTF-8") + "=" + URLEncoder.encode(account.getPostalCode(), "UTF-8");
            data += "&" + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(account.getEmail(), "UTF-8");
            data += "&" + URLEncoder.encode("languaje", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(account.getLanguaje_id()), "UTF-8");
            data += "&" + URLEncoder.encode("notifyEmail", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(account.isNotifyEmail()), "UTF-8");
            data += "&" + URLEncoder.encode("notifyFlag", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(account.isNotityFlag()), "UTF-8");
            System.out.println("Los datos a enviar son "+data);
//escribimos
            try ( //obtenemos el flujo de escritura
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream())) {
                //escribimos
                wr.write(data);
                wr.flush();

//cerramos la conexiÃ³n
            }

            //obtenemos el flujo de lectura
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String linea;
            //procesamos al salida
            while ((linea = rd.readLine()) != null) {
                respuesta += linea;
            }

        } catch (Exception e) {
            e.printStackTrace();
            respuesta = null;
        }

        System.out.println(respuesta);
        // TODO code application logic here

        return respuesta;
    }

    public String getIdAccount(String telefono) {

        Account account = this.getAccountObject(telefono);
        if (account == null) {
            return null;

        } else {
            System.out.println("Se ha devuelto el idAccount " + account.getId());
            return account.getId();
        }

    }

}
