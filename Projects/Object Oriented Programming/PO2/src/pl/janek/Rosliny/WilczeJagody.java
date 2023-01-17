package pl.janek.Rosliny;

import pl.janek.Organizm;
import pl.janek.Punkt;
import pl.janek.Swiat;

import java.awt.*;

public class WilczeJagody extends Roslina{
    public WilczeJagody(Punkt kooordynaty, Swiat swiat) {
        super(0, 0, kooordynaty, 'J', swiat, Color.DARK_GRAY);
    }

    @Override
    public Organizm wygenerujKopie(int xx, int yy) {
        Punkt punkt = new Punkt(yy, xx);
        return new WilczeJagody(punkt, this.swiat);
    }

    @Override
    public void kolizja(Organizm Agresor) {
        String text = Agresor.getSymbol() + " Zjadl Wilcze Jagody i zginal";
        swiat.dodajDoGigaStringa(text);
        swiat.dodajDoUsuniecia(Agresor);
        swiat.dodajDoUsuniecia(this);
        return;
    }
}
