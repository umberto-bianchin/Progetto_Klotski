package Model;

import java.awt.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class Solver {

    public static String jsonString(Rectangle[] config){
        String s = "{\n" + "blocks: [\n";
        for (Rectangle piece: config){
            s += "{ \"shape\": ";
            s += "[" + (int)piece.getHeight()/100 + ", " + (int)piece.getWidth()/100 + "], ";
            s += "\"position\": ";
            s += "[" + (int)piece.getX()/100 + ", " + (int)piece.getY()/100 + "] },\n";
        }
        s += "],\n";
        s += "boardSize: [5, 4],\n";
        s += "escapePoint: [2, 1],\n";
        s += "};";
        return s;
    }

    public static String sendPostRequest(String urlString, String jsonBody) throws Exception {
        //String jsonBody = Solver.jsonString(config);
        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);

        try (OutputStream os = con.getOutputStream()) {
            byte[] input = jsonBody.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        };
        return con.getResponseMessage();
    }

}
