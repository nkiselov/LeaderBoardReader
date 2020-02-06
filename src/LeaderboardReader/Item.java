package LeaderboardReader;

public class Item {

    public String name;

    public String info;

    public int amount;

    public Item(String data) {
        int br = 0;
        int infStart = 0;
        boolean infDone = false;
        boolean infExists = false;
        boolean nameDone = false;
        name = "";
        info = "";
        amount = 1;
        for (int i = 0; i < data.length(); i++) {
            if (data.charAt(i) == '(') {
                br++;
                if (br == 1) {
                    int index = i - 1;
                    while (data.charAt(index) == ' ') {
                        index--;
                    }
                    name = data.substring(0, index + 1);
                    nameDone = true;
                    infStart = i + 1;
                }
                infExists = true;
            }
            if (data.charAt(i) == ')') {
                br--;
                if (br == 0) {
                    info = data.substring(infStart, i);
                    infDone = true;
                }
            }
            if ((infDone || !infExists) && data.charAt(i) == 'x' && data.charAt(i - 1) == ' ') {
                if (!nameDone) {
                    int index = i - 1;
                    while (data.charAt(index) == ' ') {
                        index--;
                    }
                    name = data.substring(0, index + 1);
                }
                amount = Integer.parseInt(data.substring(i + 1));
                nameDone = true;
            }
            if (i == data.length() - 1 && !nameDone) {
                name = data;
            }
        }
    }

    @Override
    public String toString() {
        return name + " (" + info + ") x" + amount;
    }
}
