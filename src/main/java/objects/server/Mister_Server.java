package objects.server;

import io.javalin.Context;
import io.javalin.Javalin;
import main.Game;
import objects.ArenaObject;
import objects.character.ArenaCharacter;
import org.json.JSONObject;

import java.io.IOException;

public class Mister_Server {

    private Game currentGame;

    public static void main(String[] args) {
        new Mister_Server().run();
    }


    private void run() {
        Javalin server = Javalin
                .create()
                .port(666)
                .enableCorsForAllOrigins()
                .start();

        server.post("/API/StartGame", this::StartGame);
        server.get("/API/NextTurn", this::nextTurn);
        server.post("/API/ObjectInfo", this::objectInfo);
    }


    private void objectInfo(Context context){
        int objectPosition = (new JSONObject(context.body()).getInt("position"));

        System.out.println("Checking for: " + objectPosition);

        if(!(currentGame.getArena().acquireCharacterAtPosition(objectPosition) == null)){
            context.json(currentGame.getArena().acquireCharacterAtPosition(objectPosition));
        }



    }

    private void StartGame(Context context) throws IOException {
        System.out.println("Starting game from server");
        JSONObject gameInfoJSON = new JSONObject(context.body());
        int arenaLength = gameInfoJSON.getInt("length");
        int arenaHeight = gameInfoJSON.getInt("height");

        currentGame = new Game(arenaLength, arenaHeight);

        context.json(currentGame);

    }

    private void nextTurn(Context context){
        System.out.println("Next Turn");
        currentGame.nextTurn();

        context.json(currentGame);

    }



}
