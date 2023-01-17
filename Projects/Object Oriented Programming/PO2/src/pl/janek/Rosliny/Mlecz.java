package pl.janek.Rosliny;

import pl.janek.Organizm;
import pl.janek.Punkt;
import pl.janek.Swiat;

import java.awt.*;

public class Mlecz extends Roslina{
    public Mlecz(Punkt kooordynaty, Swiat swiat) {
        super(0, 0, kooordynaty, 'M', swiat, Color.MAGENTA);
    }

    @Override
    public Organizm wygenerujKopie(int xx, int yy) {
        Punkt punkt = new Punkt(yy, xx);
        return new Mlecz(punkt, this.swiat);
    }

    @Override
    public void akcja() {
        for (int i = 0; i < 3; i++){
            super.akcja();
        }
    }
}
