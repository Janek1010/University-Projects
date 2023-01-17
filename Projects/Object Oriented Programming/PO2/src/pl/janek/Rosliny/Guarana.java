package pl.janek.Rosliny;

import pl.janek.Organizm;
import pl.janek.Punkt;
import pl.janek.Swiat;

import java.awt.*;

public class Guarana extends Roslina{
    public Guarana(Punkt kooordynaty, Swiat swiat) {
        super(0, 0, kooordynaty, 'G', swiat, Color.YELLOW);
    }

    @Override
    public Organizm wygenerujKopie(int xx, int yy) {
        Punkt punkt = new Punkt(yy, xx);
        return new Guarana(punkt, this.swiat);
    }

    @Override
    public void kolizja(Organizm Agresor) {
        if (CzyOdbilAtak(Agresor) == false)
        {
            int noweY = kooordynaty.getY();
            int noweX = kooordynaty.getX();
            Agresor.zwiekszSile(3);
            String text = Agresor.getSymbol() + " Zjadl guarane i zyskal 3 sily!";
            swiat.dodajDoGigaStringa(text);
            // zamiast usuwac do zmieniam wskaznik i dodaje do kolejki usuwania
            swiat.dodajDoUsuniecia(this);
            swiat.ZmianaPolozenia(noweY, noweX, Agresor);
            return;
        }
    }
}
