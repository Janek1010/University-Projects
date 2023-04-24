#include <iostream>
#include <math.h>
#include <bitset>

using namespace std;

const unsigned long long int M = pow(2, 31);
const unsigned long long int N = 100000;

//1 zadanie 2.2 ld aliniowego o patrzeniu wstecz
const unsigned long long int Xo = 15;
const unsigned long long int a = 69069;
const unsigned long long int c = 1;

unsigned long long int resultZad1[N];
unsigned long long int resultZad2[N];
unsigned long int amount[10] = {0,0,0,0,0,0,0,0,0,0 };
unsigned long int amount2[10] = {0,0,0,0,0,0,0,0,0,0 };

// zadanie 2 2.6
const unsigned long long int p = 7;
const unsigned long long int q = 3;
bitset<31> startingBit = 0b1001001000000000000000000000000;
// musimy dioliczyc 24 bity
// przesuniecie o 31 -p

long long int generateXn(long long int index);
void makeNewNumber();

int main()
{
	resultZad1[0] = 15;
	cout << "ZAD1: \n";
	for (int i = 0; i < N; i++)
	{
		resultZad1[i] = generateXn(i);
		amount[10 * resultZad1[i] / M]++;
	}
	
	for (int i = 0; i < 10; i++)
	{
		cout << "Przedzial " << i << " " << amount[i]<<"\n";
	}
	cout << "*********************************\n";

	cout << "ZAD2: \n";
		
	for (int i = 0; i < N; i++)
	{
		makeNewNumber();
		resultZad2[i] = startingBit.to_ullong();
		amount2[10 * resultZad2[i] / M]++;
		startingBit = (startingBit << 24);
	}

	for (int i = 0; i < 10; i++)
	{
		cout << "Przedzial " << i << " " << amount2[i] << "\n";
	}
	cout << "*********************************\n";
}
long long int generateXn(long long int index) {
    return (resultZad1[index - 1] * a + c) % M;
}
void makeNewNumber() {
	for (int i = 23; i >= 0; i--)
	{
		startingBit[i] = startingBit[i + p] xor startingBit[i + q];
	}
}


