package objects.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.Context;
import io.javalin.Javalin;
import objects.Game;
import org.json.JSONObject;
import repositories.GameRepository;

import java.io.IOException;

public class Mister_Server {

    private static GameRepository gr;

    public static void main(String[] args) {
        gr = new GameRepository();
        new Mister_Server().run();
    }


    private void run() {
        Javalin server = Javalin
                .create()
                .port(666)
                .enableCorsForAllOrigins()
                .start();

        server.post("/API/StartGame", this::StartGame);
        server.post("/API/NextTurn", this::nextTurn);
        server.post("/API/ObjectInfo", this::objectInfo);
        server.post("/API/ArenaInfo", this::arenaInfo);
    }


    private void arenaInfo(Context context) throws JsonProcessingException {
        String ownerName = new JSONObject(context.body()).getString("owner");
        ObjectMapper mapper = new ObjectMapper();
        String arena = mapper.writeValueAsString(gr.getGameByOwnerName(ownerName).getArena());
        context.json(arena);
    }


    private void objectInfo(Context context){
        int objectPosition = (new JSONObject(context.body()).getInt("position"));
        String ownerName = new JSONObject(context.body()).getString("owner");

        System.out.println("Checking for: " + objectPosition);

        if(!(gr.getGameByOwnerName(ownerName).getArena().acquireObjectAtPosition(objectPosition) == null)){
            context.json(gr.getGameByOwnerName(ownerName).getArena().acquireObjectAtPosition(objectPosition));
        }



    }

    private void StartGame(Context context) throws IOException {
        System.out.println("Starting game from server");
        JSONObject gameInfoJSON = new JSONObject(context.body());
        int arenaLength = gameInfoJSON.getInt("length");
        int arenaHeight = gameInfoJSON.getInt("height");
        String ownerName = new JSONObject(context.body()).getString("owner");
        System.out.println("Owner: " + ownerName);

        gr.addGame(new Game(ownerName, arenaLength, arenaHeight));

        context.json(gr.getGameByOwnerName(ownerName));

    }

    private void nextTurn(Context context){
        System.out.println("Next Turn");
        String ownerName = new JSONObject(context.body()).getString("owner");
        System.out.println("Owner: " + ownerName);
        gr.getGameByOwnerName(ownerName).nextTurn();

        context.json(gr.getGameByOwnerName(ownerName));

    }



}
