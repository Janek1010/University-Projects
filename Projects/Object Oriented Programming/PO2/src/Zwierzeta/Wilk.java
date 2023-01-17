package Zwierzeta;

import Zwierzeta.Zwierze;
import pl.janek.Organizm;
import pl.janek.Punkt;
import pl.janek.Swiat;

import java.awt.*;

public class Wilk extends Zwierze {

    public Wilk(Punkt kooordynaty, Swiat swiat) {
        super(9, 5, kooordynaty, 'W', swiat, Color.RED);
    }

    @Override
    public Organizm wygenerujKopie(int xx, int yy) {
        Punkt punkt = new Punkt(yy, xx);
        return new Wilk(punkt, this.swiat);
    }
}
