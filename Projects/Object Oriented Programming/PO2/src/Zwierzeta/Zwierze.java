package Zwierzeta;

import pl.janek.Organizm;
import pl.janek.Punkt;
import pl.janek.Swiat;

import java.awt.*;
import java.util.Random;

public abstract class Zwierze extends Organizm {

    public Zwierze(int sila, int inicjatywa, Punkt kooordynaty, char symbol, Swiat swiat, Color kolor) {
        super(sila, inicjatywa, kooordynaty, symbol, swiat, kolor);
    }

    @Override
    public void akcja() {
        Random rand = new Random();
        int wylosowana_liczba_x = getKooordynaty().getX() -1 + rand.nextInt(3);
        int wylosowana_liczba_y = getKooordynaty().getY() -1 + rand.nextInt(3);
        int i = 0;
        while (i < 80) {
            if (wylosowana_liczba_x == getKooordynaty().getX() && wylosowana_liczba_y == getKooordynaty().getY()) {
                i++;
                continue;
            }
            if (wylosowana_liczba_x >= 0 && wylosowana_liczba_y > -1 && wylosowana_liczba_x < swiat.getSzerokosc() && wylosowana_liczba_y < swiat.getWysokosc()) {
                swiat.ZmianaPolozenia(wylosowana_liczba_y, wylosowana_liczba_x, this);
                return;

            }
            wylosowana_liczba_x = getKooordynaty().getX() -1 + rand.nextInt(3);
            wylosowana_liczba_y = getKooordynaty().getY() -1 + rand.nextInt(3);
            i++;
        }
    }

    @Override
    public void kolizja(Organizm Agresor) {
            if (Agresor.getSymbol() == symbol)
            {
                String text = Agresor.getSymbol() + " znalazl partnera do reprodukcji i sie rozmnozyl \n ";
                swiat.dodajDoGigaStringa(text);
                setWykonanoAkcje(true);
                Random rand = new Random();
                int wylosowana_liczba_x = getKooordynaty().getX() -1 + rand.nextInt(3);
                int wylosowana_liczba_y = getKooordynaty().getY() -1 + rand.nextInt(3);
                int i = 0;
                while (i < 40)
                {
                    if (wylosowana_liczba_x == getKooordynaty().getX() && wylosowana_liczba_y == getKooordynaty().getY())
                    {
                        i++;
                        continue;
                    }
                    if (wylosowana_liczba_x >= 0 && wylosowana_liczba_y > -1 &&
                            wylosowana_liczba_x < swiat.getSzerokosc() && wylosowana_liczba_y < swiat.getWysokosc()
                            && swiat.CzyDanePoleJestNulem(wylosowana_liczba_y, wylosowana_liczba_x) == true)
                    {
                        Organizm kopia = wygenerujKopie(wylosowana_liczba_x, wylosowana_liczba_y);
                        swiat.zapiszNaPolu(wylosowana_liczba_x, wylosowana_liczba_y, kopia);
                        return;

                    }
                    wylosowana_liczba_x = getKooordynaty().getX() -1 + rand.nextInt(3);
                    wylosowana_liczba_y = getKooordynaty().getY() -1 + rand.nextInt(3);
                    i++;
                }
            }
            else
            {
                if (CzyOdbilAtak(Agresor) == false)
                {
                    String text = getSymbol() + " Zostal zjedzony przez " + Agresor.getSymbol()+ " \n ";
                    swiat.dodajDoGigaStringa(text);
                    int noweY = kooordynaty.getY();
                    int noweX = kooordynaty.getX();
                    swiat.dodajDoUsuniecia(this);
                    swiat.ZmianaPolozenia(noweY, noweX, Agresor);
                    return;
                }
                else
                {
                    String text = Agresor.getSymbol() + " przegral atak na " + symbol + " \n ";
                    swiat.dodajDoGigaStringa(text);
                    swiat.dodajDoUsuniecia(Agresor); // przegrywa atakujacy!
                    return;
                }
            }
    }
}
