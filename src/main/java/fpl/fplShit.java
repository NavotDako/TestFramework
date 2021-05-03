package fpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class fplShit {
    public static void main(String[] args) {
        List<Team> teamsList = addAllTeams();
        for (int i = 0; i < teamsList.size(); i++) {
            System.out.println(teamsList.get(i));
        }



    }

    private static List<Team> addAllTeams() {
        List<Team> teamsList = new ArrayList<>();

        boolean a = false;
        String fileNameDefined = "C:\\Users\\DELL\\Desktop\\testP\\testP\\bin\\fpl\\games.csv";
        File file = new File(fileNameDefined);

        try {
            Scanner inputStream = new Scanner(file);
            while (inputStream.hasNext()) {
                String data = inputStream.nextLine();
                String[] split = data.split(",");
                Team team = new Team(split[0], Integer.parseInt(split[1]));
                team.addGames(split);
                teamsList.add(team);
            }
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return teamsList;
    }
}

class Game {
    String opp;
    boolean home;

    public Game(String opp, boolean home) {
        this.opp = opp;
        this.home = home;
    }

    @Override
    public String toString() {
        String s = home ? "(H)" : "(A)";
        return opp + " " + s;
    }
}

class Team {
    String name;
    int rank;
    List<Game> gameList = new ArrayList<>();

    public Team(String name, int rank) {
        this.name = name;
        this.rank = rank;
    }

    public void addGames(String[] games) {

        for (int i = 0; i < 8; i++) {
            boolean home = games[i + 2].contains("(A)") ? false : true;
            gameList.add(new Game(games[i + 2].replace(" (A)", "").replace(" (H)", ""), home));
        }
    }

    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < gameList.size(); i++) {
            s += gameList.get(i).toString() + "->";
        }
        return "Name: " + name + "\t" + "Rank: " + rank + "\t" + s;

    }
}
