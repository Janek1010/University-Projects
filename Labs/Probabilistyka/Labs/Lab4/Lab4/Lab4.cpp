#include <iostream>
#include <Random>
using namespace std;

int const RANGE = 100000;

random_device rd; // obtain a random number from hardware
mt19937 gen(rd()); // seed the generator
uniform_int_distribution<> distr(1, RANGE); // define the range
uniform_int_distribution<> distr2(1, 100);

int losowanieLiczbyX(int liczba);
int losowanieLiczbyY(int liczba, int przedzial);

int ile[] = { 0,0,0,0 };
int przedzialy[10] = { 0,0,0,0,0,0,0,0,0,0 };
float probs[4][4] = {
		{ 0, 0.1, 0, 0 },
		{ 0.1, 0,0.2, 0 },
		{ 0, 0.2,0,0.15 },
		{ 0, 0, 0.15, 0.1 }
};
float probsdivided[4][4] = {
		{ 0, 0, 0, 0 },
		{ 0, 0, 0, 0 },
		{ 0, 0, 0, 0 },
		{ 0, 0, 0, 0 }
};

float probscount[4][4] = {
		{ 0, 0, 0, 0 },
		{ 0, 0, 0, 0 },
		{ 0, 0, 0, 0 },
		{ 0, 0, 0, 0 }
};

float probsSum[4] = { 0,0,0,0 };


int main()
{

	for (int i = 0; i < 4; i++)
	{
		for (int k = 0; k < 4; k++)
		{
			probsSum[i] += probs[i][k];
		}
	}

	cout << "pradopodobienstwa wierszy\n";
	for (int i = 0; i < 4; i++)
	{
		cout << i + 1 << " - " << probsSum[i] << "\n";
	}

	for (int i = 0; i < 4; i++)
	{
		for (int k = 0; k < 4; k++)
		{
			probsdivided[i][k] = probs[i][k] / probsSum[i];
		}
	}
	cout << "prawdopodobienstwa podzielone dla kazdego punktu\n";
	for (int i = 0; i < 4; i++)
	{
		for (int k = 0; k < 4; k++)
		{
			cout << probsdivided[i][k] << " ";
		}
		cout << endl;
	}

	for (int n = 0; n < RANGE; n++) {
		int x = losowanieLiczbyX(distr(gen));
		int y = losowanieLiczbyY(distr(gen), x);
		// teraz do tego musze wylosowac y
		probscount[x][y]++;
	}
	cout << "ile wynikow\n";
	for (int i = 0; i < 4; i++)
	{
		for (int k = 0; k < 4; k++)
		{
			float proc = ((float)probscount[i][k] / (float)RANGE) * 100;
			if (proc == 0)
			{
				cout << "    ";
			}
			cout << proc << " ";
		}
		cout << endl;
	}

	/*
	for (int n = 0; n < RANGE; n++) {
		ile[losowanieLiczby(distr(gen))]++;
	}
	for (int i = 0; i < 4; i++)
	{
		float proc = ((float)ile[i] / (float)RANGE) * 100;
		cout << i + 1 << " - " << ile[i] << " " << proc << " %\n";
	}
	*/

	/*
	for (int n = 0; n < RANGE; n++) {
		int liczba = distr2(gen) + 50;
		przedzialy[(liczba - 50) % 10]++;
	}
	for (int i = 0; i < 10; i++)
	{
		float proc = ((float)przedzialy[i] / (float)RANGE) * 100;
		cout << 50 + i * 10 << "-" << 50 + (i + 1) * 10 << " - " << przedzialy[i] << " " << proc << " %\n";
	}
	*/
}
int losowanieLiczbyX(int liczba) {
	if (liczba < probsSum[0] * RANGE)
	{
		return 0;
	}
	if (liczba < (probsSum[1]+ probsSum[0]) * RANGE)
	{
		return 1;
	}
	if (liczba < (probsSum[2] + probsSum[1] + probsSum[0]) * RANGE)
	{
		return 2;
	}
	if (liczba <= RANGE)
	{
		return 3;
	}
	return -1;
}
int losowanieLiczbyY(int liczba, int przedzial) {
	if (liczba < probsdivided[przedzial][0] * RANGE)
	{
		return 0;
	}
	if (liczba < (probsdivided[przedzial][1] + probsdivided[przedzial][0]) * RANGE)
	{
		return 1;
	}
	if (liczba < (probsdivided[przedzial][2] + probsdivided[przedzial][1] + probsdivided[przedzial][0]) * RANGE)
	{
		return 2;
	}
	if (liczba <= RANGE)
	{
		return 3;
	}
	return 0;
}
