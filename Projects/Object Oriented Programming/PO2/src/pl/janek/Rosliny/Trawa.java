package pl.janek.Rosliny;

import Zwierzeta.Zwierze;
import pl.janek.Organizm;
import pl.janek.Punkt;
import pl.janek.Swiat;

import java.awt.*;

public class Trawa extends Roslina {

    public Trawa(Punkt kooordynaty, Swiat swiat) {
        super(0, 0, kooordynaty, 'T', swiat, Color.GREEN);
    }

    @Override
    public Organizm wygenerujKopie(int xx, int yy) {
        Punkt punkt = new Punkt(yy, xx);
        return new Trawa(punkt, this.swiat);
    }
}
