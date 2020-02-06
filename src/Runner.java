import LeaderboardReader.*;

import java.util.ArrayList;
import java.util.Arrays;

public class Runner {
    public static void main(String[] args) {
        //Read data from the leader board
        Player[] players = LeaderBoard.read();
        //Get all possible names
        ArrayList<String> names = new ArrayList<>();
        for (int p=0; p<players.length; p++){
            Item[] items = players[p].itemsHas;
            if(items==null){
                continue;
            }
            for (int i=0; i<items.length; i++){
                if(!names.contains(items[i].name)){
                    names.add(items[i].name);
                }
            }
        }
        System.out.println(names);
    }
}
