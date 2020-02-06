package LeaderboardReader;

import java.util.ArrayList;
import java.util.Arrays;

public class Player {

    public int rank;

    public int prestige;

    public String name;

    public int totalPoints;

    public int spendPoints;

    public boolean liquidated;

    public String mermaidBelt;

    public Item[] itemsHas;

    public Item[] itemsUsed;

    public Item[] freeItems;

    public void setProperty(String data, int property) {
        switch (property) {
            case 0:
                rank = Integer.parseInt(data);
                break;
            case 1:
                prestige = Integer.parseInt(data);
                break;
            case 2:
                name = data;
                break;
            case 3:
                totalPoints = Integer.parseInt(data);
                break;
            case 4:
                spendPoints = Integer.parseInt(data);
                break;
            case 5:
                liquidated = !data.toLowerCase().equals("no");
                break;
            case 6:
                mermaidBelt = data;
                break;
            case 7:
                itemsHas = parseItems(data);
                break;
            case 8:
                itemsUsed = parseItems(data);
                break;
            case 9:
                freeItems = parseItems(data);
                break;
        }
    }

    private Item[] parseItems(String data) {
        ArrayList<Item> items = new ArrayList<>();
        int begin = 0;
        int br = 0;
        for (int i = 0; i < data.length(); i++) {
            if (data.charAt(i) == '(') {
                br++;
            }
            if (data.charAt(i) == ')') {
                br--;
            }
            if (data.charAt(i) == ',' && br == 0) {
                items.add(new Item(data.substring(begin, i)));
                i++;
                if (i == data.length()) {
                    break;
                }
                while (data.charAt(i) == ' ') {
                    i++;
                }
                begin = i;
            }
            if (i == data.length() - 1 && begin != i) {
                items.add(new Item(data.substring(begin, i + 1)));
            }
        }
        Item[] itemsArray = new Item[items.size()];
        for (int i = 0; i < itemsArray.length; i++) {
            itemsArray[i] = items.get(i);
        }
        return itemsArray;
    }

    @Override
    public String toString() {
        return "\nRank: " + rank +
                "\nPrestige: " + prestige +
                "\nName: " + name +
                "\nTotal points: " + totalPoints +
                "\nSpend points: " + spendPoints +
                "\nLiquidated: " + liquidated +
                "\nMermaidBelt: " + mermaidBelt +
                "\nItems has: " + Arrays.toString(itemsHas) +
                "\nItems used: " + Arrays.toString(itemsUsed) +
                "\nFree items: " + Arrays.toString(freeItems);
    }
}