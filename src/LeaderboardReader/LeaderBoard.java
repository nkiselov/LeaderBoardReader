package LeaderboardReader;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class LeaderBoard {

    private static String[] parseTable = new String[]{
            "<tbody>",
            "</tbody>",
            "<tr>",
            "</tr>",
            "<td>",
            "<tdalign=\"right\">",
            "</td>"
    };

    public static Player[] read() {
        return readItems(readPlayers());
    }

    private static Player[] readPlayers() {
        String text;
        try {
            text = readAchieves("leaderboard");
        } catch (IOException e) {
            return null;
        }
        ArrayList<Player> players = new ArrayList<>();
        boolean body = false;
        boolean readingData = false;
        int property = 0;
        String data = "";
        Player curPlayer = new Player();
        main:
        for (int i = 0; i < text.length(); i++) {
            if (readingData) {
                data += text.charAt(i);
            }
            keys:
            for (int k = 0; k < parseTable.length; k++) {
                String key = parseTable[k];
                int b = 0;
                int bi = 0;
                while (bi < key.length()) {
                    if (text.charAt(i - b) != ' ') {
                        if (text.charAt(i - b) == key.charAt(key.length() - 1 - bi)) {
                            bi++;
                        } else {
                            continue keys;
                        }
                    }
                    b++;
                }
                if (!(k == 0 || body)) {
                    continue main;
                }
                switch (k) {
                    case 0:
                        body = true;
                        break;
                    case 1:
                        break main;
                    case 2:
                        curPlayer = new Player();
                        break;
                    case 3:
                        players.add(curPlayer);
                        property = 0;
                        break;
                    case 4:
                    case 5:
                        readingData = true;
                        break;
                    case 6:
                        readingData = false;
                        curPlayer.setProperty(data.substring(0, data.length() - b), property);
                        property++;
                        data = "";
                        break;
                }
            }
        }
        Player[] playerArray = new Player[players.size()];
        for (int i = 0; i < playerArray.length; i++) {
            playerArray[i] = players.get(i);
        }
        return playerArray;
    }

    private static Player[] readItems(Player[] players) {
        String text;
        try {
            text = readAchieves("itemsowned");
        } catch (IOException e) {
            return null;
        }
        boolean body = false;
        boolean readingData = false;
        int player = 0;
        int property = 0;
        String data = "";
        String q = "";
        main:
        for (int i = 0; i < text.length(); i++) {
            if (readingData) {
                data += text.charAt(i);
            }
            keys:
            for (int k = 0; k < parseTable.length; k++) {
                String key = parseTable[k];
                int b = 0;
                int bi = 0;
                while (bi < key.length()) {
                    if (text.charAt(i - b) != ' ') {
                        if (text.charAt(i - b) == key.charAt(key.length() - 1 - bi)) {
                            bi++;
                        } else {
                            continue keys;
                        }
                    }
                    b++;
                }
                if (!(k == 0 || body)) {
                    continue main;
                }
                switch (k) {
                    case 0:
                        body = true;
                        break;
                    case 1:
                        break main;
                    case 2:
                        player = -1;
                        break;
                    case 3:
                        property = 0;
                        break;
                    case 4:
                    case 5:
                        readingData = true;
                        break;
                    case 6:
                        readingData = false;
                        String propString = data.substring(0, data.length() - b);
                        if (property == 0) {
                            q = propString;
                            for (int p = 0; p < players.length; p++) {
                                if (getNamePart(players[p].name).equals(propString)) {
                                    player = p;
                                    break;
                                }
                            }
                        } else {
                            try {
                                players[player].setProperty(propString, property + 6);
                            } catch (IndexOutOfBoundsException e) {
                                System.out.println("LeaderboardReader.Player: " + q + " has different names");
                            }
                        }
                        property++;
                        data = "";
                        break;
                }
            }
        }
        return players;
    }

    private static String getNamePart(String name) {
        int ocu = 0;
        for (int i = 0; i < name.length(); i++) {
            if (!isSymbol(name.charAt(i)) && name.charAt(i) != ' ') {
                ocu = i;
            }
        }
        return name.substring(0, ocu + 1);
    }

    private static boolean isSymbol(char c) {
        return c == '*' || c == '^' || c == '#';
    }

    private static String readAchieves(String part) throws IOException {
        StringBuilder data = new StringBuilder();
        URL url = new URL("https://www.classachieves.com/" + part);
        URLConnection con = url.openConnection();
        InputStream is = con.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = rd.readLine()) != null) {
            data.append(line).append("\n");
        }
        return data.toString();
    }
}
