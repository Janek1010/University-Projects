package Zwierzeta;

import pl.janek.Organizm;
import pl.janek.Punkt;
import pl.janek.Swiat;

import java.awt.*;
import java.util.Random;

public class Czlowiek extends Zwierze {

    private int dodatkowaSila;
    private int IleTurTemuUruchomionaMoc;

    public Czlowiek(Punkt kooordynaty, Swiat swiat) {
        super(5, 4, kooordynaty, 'C', swiat, Color.BLUE);
        this.dodatkowaSila = 0;
        this.IleTurTemuUruchomionaMoc = 0;
    }

    @Override
    public Organizm wygenerujKopie(int xx, int yy) {
        Punkt punkt = new Punkt(yy, xx);
        return new Czlowiek(punkt, this.swiat);
    }

    @Override
    public void akcja() {
        IleTurTemuUruchomionaMoc++;
        if (swiat.isAktywacjaMocy() == true) {
            AktywacjaMocy();
        }
        swiat.setAktywacjaMocy(false);
        if (dodatkowaSila > 0) {
            dodatkowaSila--;
        }

        switch (swiat.getDirection()) {
            case WEST:
                if (kooordynaty.getX() - 1 >= 0 && kooordynaty.getY() > -1 && kooordynaty.getX() - 1 < swiat.getSzerokosc() && kooordynaty.getY() < swiat.getWysokosc()) {
                    swiat.ZmianaPolozenia(kooordynaty.getY(), kooordynaty.getX() - 1, this);
                }
                break;
            case EAST:
                if (kooordynaty.getX() + 1 >= 0 && kooordynaty.getY() > -1 && kooordynaty.getX() + 1 < swiat.getSzerokosc() && kooordynaty.getY() < swiat.getWysokosc()) {
                    swiat.ZmianaPolozenia(kooordynaty.getY(), kooordynaty.getX() + 1, this);
                }
                break;
            case SOUTH:
                if (kooordynaty.getX() >= 0 && kooordynaty.getY() + 1 > -1 && kooordynaty.getX() < swiat.getSzerokosc() && kooordynaty.getY() + 1 < swiat.getWysokosc()) {
                    swiat.ZmianaPolozenia(kooordynaty.getY() + 1, kooordynaty.getX(), this);
                }
                break;
            case NORTH:
                if (kooordynaty.getX() >= 0 && kooordynaty.getY() - 1 > -1 && kooordynaty.getX() < swiat.getSzerokosc() && kooordynaty.getY() - 1 < swiat.getWysokosc()) {
                    swiat.ZmianaPolozenia(kooordynaty.getY() - 1, kooordynaty.getX(), this);
                }
                break;
        }
    }

    public void AktywacjaMocy() {
        if (IleTurTemuUruchomionaMoc >= 5) {
            dodatkowaSila = 10;
            IleTurTemuUruchomionaMoc = 0;
        }
    }

    @Override
    public boolean CzyOdbilAtak(Organizm atakujacy) {
        if (atakujacy.getSila() - dodatkowaSila >= sila) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void kolizja(Organizm Agresor) {

        if (CzyOdbilAtak(Agresor) == false) {
            String text = symbol + " Zostal zjedzony przez " + Agresor.getSymbol();
            swiat.dodajDoGigaStringa(text);
            int noweY = kooordynaty.getY();
            int noweX = kooordynaty.getX();
            swiat.dodajDoUsuniecia(this);
            swiat.ZmianaPolozenia(noweY, noweX, Agresor);
            return;
        } else {
            String text = Agresor.getSymbol() + " przegral atak na " + symbol;
            swiat.dodajDoGigaStringa(text);
            swiat.dodajDoUsuniecia(Agresor); // przegrywa atakujacy!
            return;
        }

    }
}
