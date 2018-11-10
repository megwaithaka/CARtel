package android.example.com.cartel;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;


public class FetchData {

    private static final String BASE_URL = "http://192.168.0.36:8000/";

    private static final String API_TOKEN = "wNPSt1dN3oTNvID1xRB2FXM5VSJBLPppZxbdcBrd4AfwWgEOATN2mIKj5TKV";

    public FetchData() { }

    public static String getAllSportGames(URL serverurl , HashMap<String,String> params ,String method){

        int paramLength = params.size();

        String content  = "";
        String urlQuery = "";

        StringBuilder returnedString = new StringBuilder();

        if(!params.isEmpty()){
            int counter = 0;
            for(HashMap.Entry<String , String> entry : params.entrySet()){

                if(counter > 0 && counter != paramLength ){
                    urlQuery += "&";
                }
                urlQuery += entry.getKey() +"="+entry.getValue();

                counter++;
            }
        }

        byte[] postData       = new byte[0];
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            postData = urlQuery.getBytes( StandardCharsets.UTF_8 );
        }
        int    postDataLength = postData.length;

        try{

            URL url   = new URL(serverurl.toString());

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("accept", "application/json");
            urlConnection.setRequestProperty("api_token", API_TOKEN);
            urlConnection.setRequestProperty("X-Requested-With", "Curl");

            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setUseCaches(false);
            urlConnection.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
            urlConnection.setRequestMethod(method);

            if(method.equals("POST")) {
                DataOutputStream os = new DataOutputStream(urlConnection.getOutputStream());
                os.write(postData);
            }

            BufferedReader inputStreamReader = new BufferedReader(
                    new InputStreamReader(urlConnection.getInputStream())
            );

            while((content = inputStreamReader.readLine()) != null ){
                returnedString.append(content).append("\n");
            }
            inputStreamReader.close();

        }catch (IOException e){
            System.out.println("GetDataOnline 1 -> The Error was: " + e.getMessage());
        }catch(Exception e){
            System.out.println("GetDataOnline 2 -> The Error was: " + e.getMessage());
        }

        return returnedString.toString();
    }
}
