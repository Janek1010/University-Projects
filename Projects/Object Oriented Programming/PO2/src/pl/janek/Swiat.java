package pl.janek;

import Zwierzeta.*;
import pl.janek.Rosliny.*;

import javax.print.attribute.standard.PresentationDirection;
import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;
import java.util.List;


public class Swiat {
    public final static int ILOSC_WILKOW = 1;
    public final static int ILOSC_OWIEC = 1;
    public final static int ILOSC_LISOW = 1;
    public final static int ILOSC_ZOLWI = 1;
    public final static int ILOSC_ANTYLOP = 1;
    public final static int ILOSC_TRAWY = 1;
    public final static int ILOSC_LUDZI = 1;
    public final static int ILOSC_MLECZY = 1;
    public final static int ILOSC_GUARAN = 1;
    public final static int ILOSC_JAGOD = 1;
    public final static int ILOSC_BARSZCZU = 1;

    protected Organizm[][] organizmy;
    protected List<Organizm> kolejkaOrganizmow = new LinkedList<>();
    protected int szerokosc;
    protected int wysokosc;
    protected int kolejnoscZycia;
    protected String gigaString;
    protected Direction direction;
    protected boolean aktywacjaMocy;

    public Swiat() {
        kolejnoscZycia = 0;
        this.szerokosc = 20;
        this.aktywacjaMocy = false;
        this.wysokosc = 20;
        this.direction = Direction.NORTH;
        gigaString = "";
        organizmy = new Organizm[wysokosc][szerokosc];
        for (int i = 0; i < organizmy.length; i++) {
            for (int k = 0; k < organizmy[i].length; k++) {
                organizmy[i][k] = null;
            }
        }
    }

    public boolean isAktywacjaMocy() {
        return aktywacjaMocy;
    }

    public void setAktywacjaMocy(boolean aktywacjaMocy) {
        this.aktywacjaMocy = aktywacjaMocy;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getSzerokosc() {
        return szerokosc;
    }

    public boolean CzyDanePoleJestNulem(int y, int x) {
        if (organizmy[y][x] == null) {
            return true;
        }
        return false;
    }

    public int getWysokosc() {
        return wysokosc;
    }

    public void dodajDoUsuniecia(Organizm organizm) {
        organizmy[organizm.kooordynaty.getY()][organizm.kooordynaty.getX()] = null;
        organizm.setCzyZyje(false);
    }

    public String getGigaString() {
        return gigaString;
    }

    public void dodajDoGigaStringa(String text) {
        gigaString = gigaString + text;
    }

    void swiatRogrywkaStandardowa() {
        inicjalizacjaOrganizmow();
        Window window = new Window(20, 20, this);
    }

    void nowaTura() {
        zerowanieAkcji();
        zrobAkcje();
    }

    public void inicjalizacjaOrganizmow() {
        for (int i = 0; i < ILOSC_WILKOW; i++) {
            Punkt punkt = new Punkt(this);
            organizmy[punkt.getY()][punkt.getX()] = new Wilk(punkt, this);
        }
        for (int i = 0; i < ILOSC_OWIEC; i++) {
            Punkt punkt = new Punkt(this);
            organizmy[punkt.getY()][punkt.getX()] = new Owca(punkt, this);
        }
        for (int i = 0; i < ILOSC_LISOW; i++) {
            Punkt punkt = new Punkt(this);
            organizmy[punkt.getY()][punkt.getX()] = new Lis(punkt, this);
        }
        for (int i = 0; i < ILOSC_ZOLWI; i++) {
            Punkt punkt = new Punkt(this);
            organizmy[punkt.getY()][punkt.getX()] = new Zolw(punkt, this);
        }
        for (int i = 0; i < ILOSC_ANTYLOP; i++) {
            Punkt punkt = new Punkt(this);
            organizmy[punkt.getY()][punkt.getX()] = new Antylopa(punkt, this);
        }
        for (int i = 0; i < ILOSC_TRAWY; i++) {
            Punkt punkt = new Punkt(this);
            organizmy[punkt.getY()][punkt.getX()] = new Trawa(punkt, this);
        }
        for (int i = 0; i < ILOSC_LUDZI; i++) {
            Punkt punkt = new Punkt(this);
            organizmy[punkt.getY()][punkt.getX()] = new Czlowiek(punkt, this);
        }
        for (int i = 0; i < ILOSC_MLECZY; i++) {
            Punkt punkt = new Punkt(this);
            organizmy[punkt.getY()][punkt.getX()] = new Mlecz(punkt, this);
        }
        for (int i = 0; i < ILOSC_GUARAN; i++) {
            Punkt punkt = new Punkt(this);
            organizmy[punkt.getY()][punkt.getX()] = new Guarana(punkt, this);
        }
        for (int i = 0; i < ILOSC_JAGOD; i++) {
            Punkt punkt = new Punkt(this);
            organizmy[punkt.getY()][punkt.getX()] = new WilczeJagody(punkt, this);
        }
        for (int i = 0; i < ILOSC_BARSZCZU; i++) {
            Punkt punkt = new Punkt(this);
            organizmy[punkt.getY()][punkt.getX()] = new BarszczSosnowskiego(punkt, this);
        }
    }

    public boolean czyDanePoleJestNullem(int y, int x) {
        if (organizmy[y][x] == null) {
            return true;
        }
        return false;
    }

    public void ZmianaPolozenia(int noweY, int noweX, Organizm organizm) {
        int stareX = organizm.getKooordynaty().getX();
        int stareY = organizm.getKooordynaty().getY();
        if (organizmy[noweY][noweX] == null) {
            organizmy[noweY][noweX] = organizm;
        } else {
            organizmy[noweY][noweX].kolizja(organizm);
            return;
        }
        organizm.setKooordynaty(noweY, noweX);
        organizmy[stareY][stareX] = null;
    }

    public int getKolejnoscZycia() {
        return kolejnoscZycia;
    }

    public void setKolejnoscZycia(int kolejnoscZycia) {
        this.kolejnoscZycia = kolejnoscZycia;
    }

    public void zapiszNaPolu(int x, int y, Organizm organizm) {
        organizmy[y][x] = organizm;
    }

    public char getSymbolOrganizmuNaDanymPolu(int x, int y) {
        return organizmy[y][x].getSymbol();
    }

    public void dodajDoUsunieciaPoWspol(int x, int y) {
        Organizm organizm = organizmy[y][x];
        organizmy[organizm.getKooordynaty().getY()][organizm.getKooordynaty().getX()] = null;
        organizm.setCzyZyje(false);
    }

    public void zerowanieAkcji() {
        for (int i = 0; i < organizmy.length; i++) {
            for (int k = 0; k < organizmy[i].length; k++) {
                if (organizmy[i][k] != null) {
                    organizmy[i][k].setWykonanoAkcje(false);
                }
            }
        }
    }

    public void usunGre() {
        kolejnoscZycia = 0;
        for (int i = 0; i < organizmy.length; ++i) {
            for (int g = 0; g < organizmy[i].length; ++g) {
                organizmy[i][g] = null;
            }
        }
    }

    public void dodajNowyOrganizm(int y, int x, char symbol) {
        Punkt punkt = new Punkt(y, x);
        if (organizmy[y][x] != null){
            return;
        }
        switch (symbol) {
            case 'W':
                organizmy[punkt.getY()][punkt.getX()] = new Wilk(punkt, this);
                break;
            case 'O':
                organizmy[punkt.getY()][punkt.getX()] = new Owca(punkt, this);
                break;
            case 'L':
                organizmy[punkt.getY()][punkt.getX()] = new Lis(punkt, this);
                break;
            case 'Z':
                organizmy[punkt.getY()][punkt.getX()] = new Zolw(punkt, this);
                break;
            case 'C':
                organizmy[punkt.getY()][punkt.getX()] = new Czlowiek(punkt, this);
                break;
            case 'A':
                organizmy[punkt.getY()][punkt.getX()] = new Antylopa(punkt, this);
                break;
            case 'T':
                organizmy[punkt.getY()][punkt.getX()] = new Trawa(punkt, this);
                break;
            case 'M':
                organizmy[punkt.getY()][punkt.getX()] = new Mlecz(punkt, this);
                break;
            case 'G':
                organizmy[punkt.getY()][punkt.getX()] = new Guarana(punkt, this);
                break;
            case 'J':
                organizmy[punkt.getY()][punkt.getX()] = new WilczeJagody(punkt, this);
                break;
            case 'B':
                organizmy[punkt.getY()][punkt.getX()] = new BarszczSosnowskiego(punkt, this);

                break;
        }
    }

    public void zrobAkcje() {
        kolejkaOrganizmow.clear();

        for (int i = 0; i < organizmy.length; ++i) {
            for (int g = 0; g < organizmy[i].length; ++g) {
                if (organizmy[i][g] != null) {
                    kolejkaOrganizmow.add(organizmy[i][g]);
                    // mam tu all organizmy nieposortowane wtedy
                }
            }
        }
        // bubble sortem sortuje kolejke
        int i, j;
        // sortuje po inicjatywie
        for (i = 0; i < kolejkaOrganizmow.size() - 1; i++) {
            for (j = 0; j < kolejkaOrganizmow.size() - i - 1; j++) {
                if (kolejkaOrganizmow.get(j).getInicjatywa() < kolejkaOrganizmow.get(j + 1).getInicjatywa()) {
                    Organizm tempj = kolejkaOrganizmow.get(j);
                    Organizm tempj1 = kolejkaOrganizmow.get(j + 1);
                    kolejkaOrganizmow.set(j, tempj1);
                    kolejkaOrganizmow.set(j + 1, tempj);
                }
            }
        }
        // sortuje po starszenstwie
        for (i = 0; i < kolejkaOrganizmow.size() - 1; i++) {
            for (j = 0; j < kolejkaOrganizmow.size() - i - 1; j++) {
                if (kolejkaOrganizmow.get(j).getInicjatywa() == kolejkaOrganizmow.get(j + 1).getInicjatywa()) {
                    if (kolejkaOrganizmow.get(j).getWiek() > kolejkaOrganizmow.get(j + 1).getWiek()) {
                        Organizm tempj = kolejkaOrganizmow.get(j);
                        Organizm tempj1 = kolejkaOrganizmow.get(j + 1);
                        kolejkaOrganizmow.set(j, tempj1);
                        kolejkaOrganizmow.set(j + 1, tempj);
                    }
                }
            }
        }
        for (i = 0; i < kolejkaOrganizmow.size(); i++) {
            if ((kolejkaOrganizmow.get(i).isWykonanoAkcje() == false) && (kolejkaOrganizmow.get(i).isCzyZyje() == true)) {
                kolejkaOrganizmow.get(i).akcja();
                kolejkaOrganizmow.get(i).setWykonanoAkcje(true);
            }
        }
    }

    public int getSilaOrganizmuNaDanymPolu(int y, int x) {
        if (organizmy[y][x] != null) {
            return organizmy[y][x].getSila();
        }
        return 0;
    }
}
