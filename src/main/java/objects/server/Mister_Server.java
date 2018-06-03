package objects.server;

import io.javalin.Context;
import io.javalin.Javalin;
import main.Game;
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
    }


    private void StartGame(Context context) throws IOException {
        JSONObject gameInfoJSON = new JSONObject(context.toString());
        int arenaLength = gameInfoJSON.getInt("length");
        int arenaHeight = gameInfoJSON.getInt("height");

        currentGame = new Game(arenaLength, arenaHeight);

        context.json(currentGame);

    }

    private void nextTurn(Context context){

        currentGame.nextTurn();

        context.json(currentGame);

    }



}
