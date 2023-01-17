package pl.janek;

import java.util.Random;

public class Punkt {
    private int y;
    private int x;
    private Swiat swiat;

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public Punkt(int y, int x) {
        this.y = y;
        this.x = x;
    }
    public Punkt(Swiat swiat){
        Random rand = new Random();
        int x = rand.nextInt(swiat.szerokosc);
        int y = rand.nextInt(swiat.wysokosc);
        while (true){
            x = rand.nextInt(swiat.szerokosc);
            y = rand.nextInt(swiat.wysokosc);
            if (swiat.organizmy[y][x] == null){
                break;
            }
        }
        this.x = x;
        this.y = y;
    }
}
