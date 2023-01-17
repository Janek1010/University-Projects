package Zwierzeta;

import Zwierzeta.Zwierze;
import pl.janek.Organizm;
import pl.janek.Punkt;
import pl.janek.Swiat;

import java.awt.*;

public class Owca extends Zwierze {
    public Owca(Punkt kooordynaty, Swiat swiat) {
        super(4, 4, kooordynaty, 'O', swiat, Color.BLACK);
    }
    @Override
    public Organizm wygenerujKopie(int xx, int yy) {
        Punkt punkt = new Punkt(yy, xx);
        return new Owca(punkt, this.swiat);
    }
}
