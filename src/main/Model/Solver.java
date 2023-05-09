package Model;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class Solver {

    private static final JSONParser parser = new JSONParser();

    private static String jsonString(Rectangle[] config){

        StringBuilder json = new StringBuilder();
        json.append("{\"blocks\":[");
        for (int i = 0; i < config.length; i++) {
            json.append("{\"shape\":[")
                    .append(config[i].getHeight() / 100).append(",")
                    .append(config[i].getWidth() / 100).append("],")
                    .append("\"position\":[")
                    .append(config[i].getY() / 100).append(",")
                    .append(config[i].getX() / 100).append("]}");
            if (i < config.length - 1)
                json.append(",");
        }
        json.append("],\"boardSize\":[5,4],\"escapePoint\":[3,1]}");
        return json.toString();

    }

    private static String sendPostRequest(String jsonBody) throws IOException {
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

        if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            return bufferedReader.readLine();
        }
        throw new IOException();
    }

    public static Move nextBestMove(Rectangle[] config) throws Exception {
        String response = sendPostRequest(jsonString(config));
        Rectangle[] positions = new Rectangle[2];

        JSONObject json = (JSONObject) parser.parse(response);

        int blockIdx = ((Long) json.get("blockIdx")).intValue();
        int dirIdx =  ((Long) json.get("dirIdx")).intValue();

        positions[0] = new Rectangle(config[blockIdx]);
        positions[1] = new Rectangle(config[blockIdx]);
        switch (dirIdx) {
            case 0 -> positions[1].translate(0, 100);
            case 1 -> positions[1].translate(100, 0);
            case 2 -> positions[1].translate(0, -100);
            case 3 -> positions[1].translate(-100, 0);
            default -> throw new IOException();
        }

        return new Move(positions[0], positions[1]);
    }
}



