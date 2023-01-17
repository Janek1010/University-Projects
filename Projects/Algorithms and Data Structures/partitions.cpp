#include <iostream>
using namespace std;
#define ROZMIARTAB 15

constexpr int ILOSC_LPIERWSZYCH = 25;
constexpr int lpierwsze[ILOSC_LPIERWSZYCH] = { 2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97 }; // oczywiscie nie wszystkie 

int szukajIndeksu(const int tab[], int rozmiar, int k);
void rekSzukanie(const int tab[], int indeks, int suma, int maxindeks, int sumaDocelowa, int kombinacja[]);
bool czyjestlpierw( int liczba);
void zerowanie(int tab[]);
void pushBack(int tab[], int wartosc);
void popBack(int tab[]);
void wyswietl(const int tab[], int maxindeks, int kombinacja[]);

int main()
{
        int liczba_linii = 0;
        int liczba_naturalna = 0;
        int najwiekszy_dzielnik = 0;
        int reszta_bez_1pierw = 0;
        int ktory_indeks = 0;
        int tab[ROZMIARTAB] = {};
        cin >> liczba_linii;
        for (int i = 0; i < liczba_linii; i++)
        {
                cin >> liczba_naturalna;
                cin >> najwiekszy_dzielnik;
                reszta_bez_1pierw = liczba_naturalna - najwiekszy_dzielnik;
                if (reszta_bez_1pierw < 0)
                {
                        continue;
                }
                else if (czyjestlpierw(najwiekszy_dzielnik) == false) {
                        continue;
                }
                else if (reszta_bez_1pierw == 0)
                {
                        cout << najwiekszy_dzielnik << endl;
                }
                else
                {
                        ktory_indeks = szukajIndeksu(lpierwsze, ILOSC_LPIERWSZYCH, najwiekszy_dzielnik);
                        if (ktory_indeks != -1)
                        {
                                zerowanie(tab);
                                rekSzukanie(lpierwsze, ktory_indeks + 1, 0, ktory_indeks, reszta_bez_1pierw, tab);
                        }
                }
        }
}
int szukajIndeksu(const int tab[], int rozmiar, int k) {
        for (int i = 0; i < rozmiar; i++)
        {
                if (tab[i] == k)
                {
                        return i;
                }
        }
        return -1;
}
bool czyjestlpierw( int liczba) {
        for (int i = 0; i < ILOSC_LPIERWSZYCH; i++)
        {
                if (lpierwsze[i] == liczba)
                {
                        return true;
                }
        }
        return false;
}
void zerowanie(int tab[]) {
        for (int i = 0; i < ROZMIARTAB; i++)
        {
                tab[i] = 0;
        }
}
void rekSzukanie(const int tab[], int indeks, int suma, int maxindeks, int sumaDocelowa, int kombinacja[]) {
        if (suma == sumaDocelowa)
        {
                wyswietl(tab, maxindeks, kombinacja);
                return;
        }
        for (int i = 0; i < indeks; i++)
        {

                if (suma + tab[i] <= sumaDocelowa)
                {

                        pushBack(kombinacja, tab[i]);
                        rekSzukanie(tab, i + 1, suma + tab[i], maxindeks, sumaDocelowa, kombinacja);
                        popBack(kombinacja);
                }
                else
                {
                        return;
                }
        }
}
void pushBack(int tab[], int wartosc) {
        for (int i = 1; i < ROZMIARTAB; i++)
        {
                if (tab[i] == 0)
                {
                        tab[i] = wartosc;
                        tab[0]++;
                        return;
                }
        }
}
void popBack(int tab[]) {
        for (int i = ROZMIARTAB-1; i > 0; i--)
        {
                if (tab[i] != 0)
                {
                        tab[i] = 0;
                        tab[0]--;
                        return;
                }
        }
}
void wyswietl(const int tab[], int maxindeks, int kombinacja[]) {
        cout << tab[maxindeks] << "+";
        for (int i = 1; i < kombinacja[0] + 1; i++)
        {
                cout << kombinacja[i];
                if (i == kombinacja[0])
                {
                        break;
                }
                cout << "+";
        }
        cout << endl;
        return;
}