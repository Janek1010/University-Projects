package pl.janek.Rosliny;

import pl.janek.Organizm;
import pl.janek.Punkt;
import pl.janek.Swiat;

import java.awt.*;
import java.util.Random;

public abstract class Roslina extends Organizm {

    private final int SZANSA_ZASIANIA = 20; // tym wieksza liczba tym mniejsza szansa!
    public Roslina(int sila, int inicjatywa, Punkt kooordynaty, char symbol, Swiat swiat, Color kolor) {
        super(sila, inicjatywa, kooordynaty, symbol, swiat, kolor);
    }

    @Override
    public void akcja() {
        Random rand = new Random();
        int wylosowana_liczba = rand.nextInt(SZANSA_ZASIANIA);
        if (wylosowana_liczba == 0)
        {

            int wylosowana_liczba_x = rand.nextInt(3) + kooordynaty.getX() - 1;
            int wylosowana_liczba_y = rand.nextInt(3) + kooordynaty.getY() - 1;
            int i = 0;
            while (i < 40)
            {
                if (wylosowana_liczba_x == kooordynaty.getX() && wylosowana_liczba_y == kooordynaty.getY())
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
                    String text = getSymbol() + " sie rozmnozyla \n ";
                    swiat.dodajDoGigaStringa(text);
                    return;

                }
                wylosowana_liczba_x = rand.nextInt(3) + kooordynaty.getX() - 1;
                wylosowana_liczba_y = rand.nextInt(3) + kooordynaty.getY() - 1;
                i++;
            }
        }
        else
        {
            return;
        }
    }

    @Override
    public void kolizja(Organizm Agresor) {
        if (CzyOdbilAtak(Agresor) == false)
        {
            String text = symbol + " Zostal zjedzony przez " + Agresor.getSymbol();
            swiat.dodajDoGigaStringa(text);
            int noweY = kooordynaty.getY();
            int noweX = kooordynaty.getX();
            // zamiast usuwac do zmieniam wskaznik i dodaje do kolejki usuwania
            swiat.dodajDoUsuniecia(this);
            swiat.ZmianaPolozenia(noweY, noweX, Agresor);
            return;
        }
    }
}
