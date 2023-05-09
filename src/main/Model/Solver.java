package Model;

import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class Solver {

    public final static String b = "\"blockIdx\":";
    public final static String d = ",\"dirIdx\":";

    private static String jsonString(Rectangle[] config){
        String s = "{\"blocks\":[";
        for (int i=0; i<config.length; i++){
            s += "{\"shape\":";
            s += "[" + (int)config[i].getHeight()/100 + "," + (int)config[i].getWidth()/100 + "],";
            s += "\"position\":";
            if(i!=config.length-1)
                s += "[" + (int)config[i].getY()/100 + "," + (int)config[i].getX()/100 + "]},";
            else
                s += "[" + (int)config[i].getY()/100 + "," + (int)config[i].getX()/100 + "]}";
        }
        s += "],\"boardSize\":[5,4],";
        s += "\"escapePoint\":[3,1]}";
        return s;
    }

    private static String sendPostRequest(String jsonBody) throws IOException {
        //String jsonBody = Solver.jsonString(config);
        // Convert the JSON payload to a byte array
        byte[] requestBody = jsonBody.getBytes(StandardCharsets.UTF_8);

        // Set up the HttpURLConnection
        URL url = new URL("https://izrpgveiyi.execute-api.eu-north-1.amazonaws.com/solver");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);

        // Write the JSON payload to the request body
        try (OutputStream out = con.getOutputStream()) {
            out.write(requestBody);
        }

        int statusCode = con.getResponseCode();
        if (statusCode == HttpURLConnection.HTTP_OK) {
            InputStream inputStream = con.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        }
        return "Error response code: " + statusCode;
    }

    public static Rectangle[] nextBestMove(Rectangle[] config) throws IOException {
        String response = sendPostRequest(jsonString(config));
        Rectangle[] move = new Rectangle[2];
        if(response.contains("Error"))
            throw new SolverError();
        else{
            String block = response.substring(response.indexOf(b)+b.length(), response.indexOf(d));
            String dir = response.substring(response.indexOf(d)+d.length(), response.indexOf("}"));
            int blockIdx = Integer.parseInt(block);
            int dirIdx = Integer.parseInt(dir);

            move[0] = new Rectangle(config[blockIdx]);
            Rectangle finalPos = new Rectangle(config[blockIdx]);
            switch (dirIdx) {
                case 0 -> finalPos.translate(0, 100);
                case 1 -> finalPos.translate(100, 0);
                case 2 -> finalPos.translate(0, -100);
                case 3 -> finalPos.translate(-100, 0);
                default -> throw new SolverError();
            }
            move[1] = finalPos;
        }
        return move;
    }

    public static class SolverError extends RuntimeException{}

}



