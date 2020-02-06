import LeaderboardReader.*;

import java.util.Arrays;

public class Runner {
    public static void main(String[] args) {
        //Read data from the leader board
        Player[] players = LeaderBoard.read();
        //Print data
        System.out.println(Arrays.toString(players));
    }
}
