package pl.janek.Rosliny;

import pl.janek.Organizm;
import pl.janek.Punkt;
import pl.janek.Swiat;

import java.awt.*;

public class BarszczSosnowskiego extends Roslina{
    public BarszczSosnowskiego(Punkt kooordynaty, Swiat swiat) {
        super(99, 0, kooordynaty, 'B', swiat, Color.lightGray);
    }

    @Override
    public Organizm wygenerujKopie(int xx, int yy) {
        Punkt punkt = new Punkt(yy, xx);
        return new BarszczSosnowskiego(punkt, this.swiat);
    }

    @Override
    public void kolizja(Organizm Agresor) {
        String text = Agresor.getSymbol() + " Zjadl Wilcze Jagody i zginal";
        swiat.dodajDoGigaStringa(text);
        swiat.dodajDoUsuniecia(Agresor);
        swiat.dodajDoUsuniecia(this);
        return;
    }

    @Override
    public void akcja() {
        int analizowanyX;
        int analizowanyY;
        for (int i = 0; i < 3; i++)
        {
            for (int g = 0; g < 3; g++)
            {
                analizowanyX = kooordynaty.getX() - 1 + i;
                analizowanyY = kooordynaty.getY() - 1 + g;
                if (analizowanyX == kooordynaty.getX() && analizowanyY == kooordynaty.getY()) {
                    continue;
                }
                else
                {
                    if (analizowanyX >= 0 && analizowanyY >= 0 && analizowanyX < swiat.getSzerokosc() && analizowanyY < swiat.getWysokosc()
                            && swiat.CzyDanePoleJestNulem(analizowanyY, analizowanyX) == false &&
                            swiat.getSymbolOrganizmuNaDanymPolu(analizowanyX, analizowanyY) != getSymbol())
                    {
                        swiat.dodajDoUsunieciaPoWspol(analizowanyX, analizowanyY);
                    }
                }
            }
        }
        super.akcja();
    }
}
