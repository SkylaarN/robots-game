package za.wethinkcode.RobotWorlds.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Ui {

    public Ui(){}

    public static String colors(String color){
        switch (color.toLowerCase()){
            case "purple":
                return "\u001B[35m";
            case "red":
                return "\u001B[31m";
            case "blue":
                return "[1m\u001B[34m";
            case "bold":
                return "\u001B[1m";
            case "cyan":
                return "\u001B[36m";
            default:
                return "\u001B[0m";
        }
    }

    public static String emoji(String emoji){
        switch (emoji.toLowerCase()){
            case "ok":
                return "\uD83D\uDC4D";
            case "error":
                return "\uD83D\uDC4E";
            case "heart":
                return "\u2764 ";
            case "gun":
                return "\uD83D\uDCEB";
            case "bullet":
                return "\u26AB ";
            case "eyes":
                return "\uD83D\uDC40";
        }
        return "emoji";
    }

    public static String direction(String direction){
        switch (direction.toLowerCase()) {

            case "south":
                return "%";
            case "left":
                return "<---";
            case "right":
                return "-->";
            case "north":
            default:
                return "^" ;
        }
    }

    public static void displayState(JsonNode response) throws JsonProcessingException {
        if (response.get("result").asText().equalsIgnoreCase("ok")){
            if (response.get("data").get("message").asText().equalsIgnoreCase("Done")) {
                System.out.println("Data: -> ");
                System.out.println("    Position: " + "[" + response.get("data").get("position") + "]");
                System.out.print("    Visibility: ");
                singleLine(emoji("eyes"), Integer.parseInt(response.get("data").get("visibility").asText()));
                System.out.println("");
                System.out.println("    Message: ");
                System.out.println("{");
                System.out.println("      " + response.get("data").get("message").asText());
                System.out.println("                     }");
                System.out.println("------------------------------------------");


                System.out.println("State: -> ");
                System.out.print("    Shields: ");
                singleLine(emoji("heart"), Integer.parseInt(response.get("state").get("shields").asText()));
                System.out.println("");
                System.out.print("    Shots: ");
                singleLine(emoji("bullet"), Integer.parseInt(response.get("state").get("shots").asText()));
                System.out.println("");
                System.out.println("    Direction: " + direction(response.get("state").get("direction").asText()));
                System.out.println("    Status: " + response.get("state").get("status").asText());
            }
        }

    }

    public static void state(JsonNode response){
        System.out.println("State: -> ");
        System.out.print("    Shields: ");
        singleLine(emoji("heart"),Integer.parseInt(response.get("state").get("shields").asText()));
        System.out.println("");
        System.out.print("    Shots: ");
        singleLine(emoji("bullet"),Integer.parseInt(response.get("state").get("shots").asText()));
        System.out.println("");
        System.out.println("    Direction: " + direction(response.get("state").get("direction").asText()));
        System.out.println("    Status: " + response.get("state").get("status").asText());
    }

    public static void display(String responseL) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode response = objectMapper.readTree(responseL);

        System.out.println("------------------------------------------");
        System.out.println("Result: " + emoji(response.get("result").asText()));
        System.out.println("------------------------------------------");
        if(responseL.contains("message")){
            displayState(response);
        } else if (!responseL.contains("data")){
            state(response);
        }
    }

    public static void singleLine(String emoji, int value){
        for (int i = 0; i < value; i++) {
            System.out.print(emoji + " ");
        }
    }
}
