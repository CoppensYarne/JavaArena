package repositories;

import main.Game;

import java.util.ArrayList;

public class GameRepository {

    private ArrayList<Game> allGames;

    public GameRepository(){
        allGames = new ArrayList<>();
    }

    public ArrayList<Game> getAllGames() {
        return allGames;
    }

    public void addGame(Game game){
        allGames.add(game);
    }

    public Game getGameByOwnerName(String ownerName){
        for(Game game : allGames){
            if(game.getGameOwner().equals(ownerName)){
                return game;
            }
        }
        return null;
    }

}
