package pl.janek;

import java.awt.*;

public abstract class Organizm {
    protected int sila;
    protected int inicjatywa;
    protected Punkt kooordynaty;
    protected char symbol; // na razie zostawei ten symbol ale bedzie trzeba to zmienic
    protected Swiat swiat;
    protected boolean wykonanoAkcje;
    protected int wiek;
    protected boolean czyZyje;
    protected Color kolor;

    public Organizm(int sila, int inicjatywa, Punkt kooordynaty, char symbol, Swiat swiat, Color kolor) {
        this.sila = sila;
        this.inicjatywa = inicjatywa;
        this.kooordynaty = kooordynaty;
        this.symbol = symbol;
        this.kolor = kolor;
        this.swiat = swiat;
        this.wykonanoAkcje = true;
        this.wiek = swiat.getKolejnoscZycia();
        swiat.setKolejnoscZycia((swiat.getKolejnoscZycia() + 1));
        this.czyZyje = true;
    }

    public Color getKolor() {
        return kolor;
    }

    public Punkt getKooordynaty() {
        return kooordynaty;
    }

    public int getInicjatywa() {
        return inicjatywa;
    }

    public void setKooordynaty(int y, int x) {
        this.kooordynaty.setY(y);
        this.kooordynaty.setX(x);
    }

    public int getWiek() {
        return wiek;
    }

    public int getSila() {
        return sila;
    }

    public void akcja() {
    } // akcja juz byla robiona, zwalidowac

    public void kolizja(Organizm Agresor) {
    } // kolizja nie byla robiona! pamietac

    // jeszcze dodac metode czy odbil atak!!!
    public void rysowanie() {
        System.out.print(getSymbol());
    }

    public char getSymbol() {
        return symbol;
    }

    public void setWykonanoAkcje(boolean wykonanoAkcje) {
        this.wykonanoAkcje = wykonanoAkcje;
    }

    public boolean isWykonanoAkcje() {
        return wykonanoAkcje;
    }

    public boolean isCzyZyje() {
        return czyZyje;
    }

    public boolean CzyOdbilAtak(Organizm atakujacy) {
        if (atakujacy.getSila() >= sila) {
            return false;
        } else {
            return true;
        }
    }

    public void zwiekszSile(int boostsily) {
        this.sila = sila + boostsily;
    }

    public void setCzyZyje(boolean czyZyje) {
        this.czyZyje = czyZyje;
    }

    public Organizm wygenerujKopie(int xx, int yy) {
        return this;
    }
}
