package Zwierzeta;

import Zwierzeta.Zwierze;
import pl.janek.Organizm;
import pl.janek.Punkt;
import pl.janek.Swiat;

import java.awt.*;
import java.util.Random;

public class Lis extends Zwierze {

    public Lis(Punkt kooordynaty, Swiat swiat) {
        super(3, 7, kooordynaty, 'L', swiat, Color.ORANGE);
    }

    @Override
    public Organizm wygenerujKopie(int xx, int yy) {
        Punkt punkt = new Punkt(yy, xx);
        return new Lis(punkt, this.swiat);
    }

    @Override
    public void akcja() {
        Random rand = new Random();
        int wylosowana_liczba_x = getKooordynaty().getX() - 1 + rand.nextInt(3);
        int wylosowana_liczba_y = getKooordynaty().getY() - 1 + rand.nextInt(3);
        int i = 0;
        while (i < 80) {
            if (wylosowana_liczba_x == getKooordynaty().getX() && wylosowana_liczba_y == getKooordynaty().getY()) {
                i++;
                continue;
            }

            if (wylosowana_liczba_x >= 0 && wylosowana_liczba_y > -1 && wylosowana_liczba_x < swiat.getSzerokosc() && wylosowana_liczba_y < swiat.getWysokosc()) {
                if (swiat.getSilaOrganizmuNaDanymPolu(wylosowana_liczba_y, wylosowana_liczba_x) > sila)
                {
                    return;
                }
                swiat.ZmianaPolozenia(wylosowana_liczba_y, wylosowana_liczba_x, this);
                return;
            }
            wylosowana_liczba_x = getKooordynaty().getX() - 1 + rand.nextInt(3);
            wylosowana_liczba_y = getKooordynaty().getY() - 1 + rand.nextInt(3);
            i++;
        }
    }
}
