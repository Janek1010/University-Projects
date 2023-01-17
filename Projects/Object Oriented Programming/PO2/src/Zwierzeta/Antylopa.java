package Zwierzeta;

import Zwierzeta.Zwierze;
import pl.janek.Organizm;
import pl.janek.Punkt;
import pl.janek.Swiat;

import java.awt.*;
import java.util.Random;

public class Antylopa extends Zwierze {

    public Antylopa(Punkt kooordynaty, Swiat swiat) {
        super(4, 4, kooordynaty, 'A', swiat, Color.PINK);
    }

    @Override
    public Organizm wygenerujKopie(int xx, int yy) {
        Punkt punkt = new Punkt(yy, xx);
        return new Antylopa(punkt, this.swiat);
    }

    @Override
    public void akcja() {
        Random rand = new Random();
        int wylosowana_liczba_x = getKooordynaty().getX() -2 + rand.nextInt(5);
        int wylosowana_liczba_y = getKooordynaty().getY() -2 + rand.nextInt(5);
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
            wylosowana_liczba_x = getKooordynaty().getX() -2 + rand.nextInt(5);
            wylosowana_liczba_y = getKooordynaty().getY() -2 + rand.nextInt(5);
            i++;
        }
    }

    @Override
    public void kolizja(Organizm Agresor) {
        Random rand = new Random();
        int szansa = rand.nextInt(2);
        if (szansa == 0){
            for (int i = 0; i < 3; i++){
                for (int k = 0; k < 3; k++){
                    if (i == this.kooordynaty.getY() && k == this.kooordynaty.getX()){
                        continue;
                    }
                    if (this.kooordynaty.getY()-1+i > 0 && this.kooordynaty.getY()-1+i < 20 && this.kooordynaty.getX()-1+k > 0 && this.kooordynaty.getX()-1+k < 20){
                        if (swiat.czyDanePoleJestNullem(this.kooordynaty.getY()-1+i, this.kooordynaty.getY()-1+k)){
                            swiat.ZmianaPolozenia(this.kooordynaty.getY()-1+i, this.kooordynaty.getY()-1+k, this );
                            return;
                        }
                    }
                }
            }
        }
        super.kolizja(Agresor);
    }
}
