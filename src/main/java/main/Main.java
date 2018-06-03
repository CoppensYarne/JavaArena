package main;

import objects.character.ArenaCharacter;

import java.io.IOException;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws IOException, InterruptedException {

        Boolean isConsole = true;

        Game game = new Game();

        Scanner reader = new Scanner(System.in);

        if (args.length != 0) {
            int arenaLength = Integer.parseInt(args[0]);
            int arenaHeight = Integer.parseInt(args[1]);
            System.out.println(arenaLength * arenaHeight);
            System.out.println(game.getAllCharacters().size() + game.getAllWeapons().size());
            while ((arenaLength * arenaHeight) < (game.getAllCharacters().size() + game.getAllWeapons().size())) {
                System.out.println("Arena too small, give new size:");
                System.out.print("Length: ");
                arenaLength = reader.nextInt();
                System.out.print("Height: ");
                arenaHeight = reader.nextInt();
            }
            game = new Game("CLI", arenaLength, arenaHeight);
            isConsole = false;
        } else {
            game = new Game();
        }


        String possibleChoices = "{1}Character Info {2}Arena Info {13} Next Turn";


        while (!game.isGameOver()) {
            System.out.println(game.toJSON());
            if (!isConsole) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            String events = game.nextTurn();
            System.out.println(game.getArena());
            System.out.println();
            for (ArenaCharacter arenaCharacter : game.getAllCharacters()) {
                System.out.println(arenaCharacter);
            }
            System.out.println(events);
            System.out.println();
            System.out.println(possibleChoices);
            int choice = reader.nextInt();
            while(choice != 13) {
                System.out.println(choice);
                switch (choice) {
                    case 1:
                        for (ArenaCharacter character : game.getAllCharacters()) {
                            System.out.print("{" + game.getAllCharacters().indexOf(character) + "}");
                            System.out.println(character.getName());
                        }
                        int characterChoice = reader.nextInt();
                        if (!isConsole) {
                            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                        }
                        System.out.println(game.getAllCharacters().get(characterChoice).acquireFullInfo());
                        break;
                    case 2:
                        if (!isConsole) {
                            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                        }
                        System.out.println(game.getArena());
                        System.out.println();
                        for (ArenaCharacter arenaCharacter : game.getAllCharacters()) {
                            System.out.println(arenaCharacter);
                        }
                        System.out.println(events);
                        break;
                }

                System.out.println(possibleChoices);
                choice = reader.nextInt();
            }
        }

        System.out.println(game.getAllCharacters().get(0).getName() + " won!");

    }


}
