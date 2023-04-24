#include <iostream>
#include <vector>
const int ILOSC_ELEMENTOW = 4;
int arrMain[ILOSC_ELEMENTOW] = { 1,3, 5, 8 };
int arrTemp[ILOSC_ELEMENTOW] = { 1,3, 5, 8 };
int licznik = 0;
using namespace std;

std::vector < int > dane; 
void permutacje(int level);
int obliczSilnie(int max);
void wyswietlPermutacje();

int main()
{

	licznik = 0;
	permutacje(0); 
	printf("PERMUTACJA:\n wynik ktorego sie spodziewalismy: %d i wynik ktory otrzymalismy: %d\n", obliczSilnie(ILOSC_ELEMENTOW), licznik);
}

// N-elementowe wariacje bez powtórzeń n-elementowego zbioru S nazywamy permutacjami. Istnieje n!= n⋅(n-1) ⋅…⋅2⋅1 permutacji Pn zbioru S.

void permutacje(int level) {
	if (level == ILOSC_ELEMENTOW)
	{
		licznik++;
		wyswietlPermutacje();
		return;
	}
	for (int i = 0; i < ILOSC_ELEMENTOW; i++)
	{
		if (arrTemp[i] == -1)
		{
			continue;
		}
		arrTemp[i] = -1;
		dane.push_back(arrMain[i]); // dodaje perm do wypisania
		permutacje(level + 1);
		dane.pop_back();
		arrTemp[i] = arrMain[i];
	}
	
}
void wyswietlPermutacje() {
	for (int i = 0; i < dane.size(); i++)
	{
		printf("%d ", dane[i]);
	}
	cout << "\n";
}
int obliczSilnie(int max) { //1 * 2 * 3 * 4 * 5 = 120
	int silnia = 1;
	for (int i = 2; i <= max; i++)
	{
		silnia = silnia * i;
	}
	return silnia;
}