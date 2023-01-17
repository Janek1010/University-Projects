package Zwierzeta;

import Zwierzeta.Zwierze;
import pl.janek.Organizm;
import pl.janek.Punkt;
import pl.janek.Swiat;

import java.awt.*;
import java.util.Random;

public class Zolw extends Zwierze {

    public Zolw(Punkt kooordynaty, Swiat swiat) {
        super(2, 1, kooordynaty, 'Z', swiat, Color.GRAY);
    }

    @Override
    public Organizm wygenerujKopie(int xx, int yy) {
        Punkt punkt = new Punkt(yy, xx);
        return new Zolw(punkt, this.swiat);
    }

    @Override
    public boolean CzyOdbilAtak(Organizm atakujacy) {
        if (atakujacy.getSila() < 5){
            return true;
        }
        if (atakujacy.getSila() >= sila)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    @Override
    public void akcja() {
        Random rand = new Random();
        int CzyZmieniamPolozenie = rand.nextInt(4);
        if (CzyZmieniamPolozenie != 3)
        {
            return;
        }
        super.akcja();
    }
}
