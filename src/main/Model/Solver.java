package Model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * The Solver class provides functionality for solving a puzzle game by making an API request to an AWS lambda
 */
public class Solver {

    private static final JSONParser parser = new JSONParser();
    private long hashCurrentConf = 0; //The hash value representing the current configuration of the puzzle
    private JSONArray moves; // The JSONArray object storing the list of moves received from the API
    private int index_moves = 0;

    private static final int BOARD_SIZE_WIDTH = 5;
    private static final int BOARD_SIZE_HEIGHT = 4;
    private static final int ESCAPE_POINT_X = 3;
    private static final int ESCAPE_POINT_Y = 1;

    /**
     * Converts the configuration of puzzle blocks to a JSON string
     * @param config The array of Rectangle representing the positions and shapes of the blocks
     * @return The JSON string representation of the puzzle configuration
     */
    private static String jsonString(Rectangle[] config) {
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
        json.append("],\"boardSize\":[").append(BOARD_SIZE_WIDTH).append(",")
                .append(BOARD_SIZE_HEIGHT).append("],\"escapePoint\":[").append(ESCAPE_POINT_X).append(",")
                .append(ESCAPE_POINT_Y).append("]}");
        return json.toString();
    }

    /**
     * Sends a POST request to the solver API with the given JSON payload
     * @param jsonBody The JSON payload to send in the request
     * @return The response received from the API as a string
     * @throws IOException If an I/O error occurs while making the request
     */
    private static String sendPostRequest(String jsonBody) throws IOException {
        byte[] requestBody = jsonBody.getBytes(StandardCharsets.UTF_8);
        URL url = new URL("https://izrpgveiyi.execute-api.eu-north-1.amazonaws.com/solver");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);

        try (OutputStream out = con.getOutputStream()) {
            out.write(requestBody);
        }

        if (con.getResponseCode() != HttpURLConnection.HTTP_OK)
            throw new IOException("Connection error");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
        return bufferedReader.readLine();

    }

    /**
     * Retrieves the next best move to make based on the current configuration of the puzzle
     * @param config The array of Rectangle objects representing the current configuration of the puzzle
     * @return The Move object representing the next best move to make
     * @throws IOException If an I/O error occurs while making the API request
     * @throws ParseException when the received JSON is invalid
     */
    public Move nextBestMove(Rectangle[] config) throws IOException, ParseException {

        if (Arrays.hashCode(config) != hashCurrentConf) {
            String response = sendPostRequest(jsonString(config));
            moves = (JSONArray) parser.parse(response); // parse the responded array of moves to get to the end
            index_moves = 0;
        }

        JSONObject json = (JSONObject) moves.get(index_moves++);
        Rectangle[] positions = new Rectangle[2];

        int blockIdx = ((Long) json.get("blockIdx")).intValue();
        int dirIdx = ((Long) json.get("dirIdx")).intValue();

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

    /**
     * Set the hash code of the current configuration, optionally to be called after the best move, in order to save
     * time and not make a new API request, but retrieve the second move of the array
     */
    public void setConfigurationHash(long hash) {
        hashCurrentConf = hash;
    }

}



