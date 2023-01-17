#include <iostream>
#include <string.h>
#include <stdlib.h>
using namespace std;

typedef struct list_t {
        unsigned long long value;
        list_t* next;
        list_t* prev;
} list_t;

void addPrevMenu(list_t* BEGINNING, list_t* ENDING, list_t* iterators[]);
void addPrev(list_t*& position, long long int value);
void printOut(list_t* BEGINNING, list_t* iterators[]);
void addNextMenu(list_t* BEGINNING, list_t* ENDING, list_t* iterators[]);
void addNext(list_t*& position, long long int value);
void removeMenu(list_t* BEGINNING, list_t* ENDING, list_t* iterators[]);
void removeBEGElement(list_t*& position);
void removeENDElement(list_t*& position);
void removeElement(list_t*& position, list_t* iterators[]);
void IteratorInitMenu(list_t* BEGINNING, list_t* ENDING, list_t* iterators[]);
list_t* szukajElementu(list_t* BEGINNING, int indeksSzukany);
void nextIterator(list_t* iterators[]);
void prevIterator(list_t* iterators[]);

int main()
{
        char commandFirstLetter;
        int x;
        list_t* iterators[10] = { 0 };
        list_t BEGINNING;
        list_t ENDING;
        BEGINNING.next = &ENDING;
        BEGINNING.prev = NULL;

        ENDING.next = NULL;
        ENDING.prev = &BEGINNING;

        while (cin >> commandFirstLetter)
        {
                switch (commandFirstLetter)
                {
                case 'I': 
                        scanf(" %i", &x);
                        break;
                case 'i':
                        IteratorInitMenu(&BEGINNING, &ENDING, iterators);
                        break;
                case '+':
                        nextIterator(iterators);
                        break;
                case '-':
                        prevIterator(iterators);
                        break;
                case '.':
                        addPrevMenu(&BEGINNING, &ENDING, iterators);
                        break;
                case 'A':
                        addNextMenu(&BEGINNING, &ENDING, iterators);
                        break;
                case 'R':
                        removeMenu(&BEGINNING, &ENDING, iterators);
                        break;
                case 'P':
                        printOut(&BEGINNING, iterators);
                        break;
                default:
                        break;
                }
        }
}
void addPrevMenu(list_t* BEGINNING, list_t* ENDING, list_t* iterators[]) {
        char commKind[5];
        long long int y;
        scanf("A %s %lld", commKind, &y);
        if (strcmp(commKind, "BEG") == 0)
        {
                addPrev(BEGINNING, y);
        }
        else if (strcmp(commKind, "END") == 0)
        {
                addPrev(ENDING, y);
        }
        else
        {
                int x = atoi(commKind);
                addPrev(iterators[x], y);
        }
}
void addPrev(list_t*& position, long long int value) {
        list_t* nowy_el = new list_t;
        nowy_el->value = value;

        if (position->prev == NULL)             // czyli jesli to BEG
        {
                nowy_el->next = position->next;
                position->next = nowy_el;
                nowy_el->prev = position;
                nowy_el->next->prev = nowy_el;
        }
        else if (position->next == NULL && position->prev->prev != NULL)// czyli jesli to end I LISTA MA ELEMENTY JAKIES
        {
                nowy_el->next = position->prev;
                nowy_el->prev = position->prev->prev;
                position->prev->prev->next = nowy_el;
                position->prev->prev = nowy_el;
        }
        else
        {
                nowy_el->next = position;
                nowy_el->prev = position->prev;
                position->prev = nowy_el;
                nowy_el->prev->next = nowy_el;
        }
}
void printOut(list_t* BEGINNING, list_t* iterators[]) {
        char commKind[5];
        scanf(" %s", commKind);
        list_t* szukany = BEGINNING->next;
        if (strcmp(commKind, "ALL") == 0)
        {

                while (szukany->next != NULL)
                {
                        cout << szukany->value << " ";
                        szukany = szukany->next;
                }
                cout << endl;
        }
        else
        {
                int x = atoi(commKind);
                cout << iterators[x]->value << endl;
        }

}
void addNextMenu(list_t* BEGINNING, list_t* ENDING, list_t* iterators[]) {
        char commKind[5];
        long long int y;
        scanf(". %s %lld", commKind, &y);
        if (strcmp(commKind, "BEG") == 0)
        {
                addNext(BEGINNING, y);
        }
        else if (strcmp(commKind, "END") == 0)
        {
                addNext(ENDING, y);
        }
        else
        {
                int x = atoi(commKind);
                addNext(iterators[x], y);

        }
}
void addNext(list_t*& position, long long int value) {
        list_t* nowy_el = new list_t;
        nowy_el->value = value;

        if (position->next == NULL)     
        {
                nowy_el->next = position;
                nowy_el->prev = position->prev;
                position->prev = nowy_el;
                nowy_el->prev->next = nowy_el;
        }
        else
        {
                nowy_el->next = position->next;
                nowy_el->prev = position;
                position->next = nowy_el;
                nowy_el->next->prev = nowy_el;
        }
}
void removeMenu(list_t* BEGINNING, list_t* ENDING, list_t* iterators[]) {
        char commKind[5];
        scanf(" %s", commKind);
        if (strcmp(commKind, "BEG") == 0)
        {
                removeBEGElement(BEGINNING);
        }
        else if (strcmp(commKind, "END") == 0)
        {
                removeENDElement(ENDING);
        }
        else
        {
                int x = atoi(commKind);
                removeElement(iterators[x], iterators);
        }
}
void removeElement(list_t*& position, list_t* iterators[]) {
        list_t* copy = position;
        list_t* usuwany = position;
        for (int i = 0; i < 10; i++)
        {
                if (iterators[i] == copy)
                {
                        if (copy->prev->prev == NULL && copy->next->next == NULL)
                        {
                                iterators[i] = 0;
                        }
                        else if (copy->next->next == NULL)
                        {
                                iterators[i] = copy->prev;
                        }
                        else
                        {
                                iterators[i] = iterators[i]->next;
                        }
                }
        }
        usuwany->prev->next = usuwany->next;
        usuwany->next->prev = usuwany->prev;

        usuwany->next = NULL;
        usuwany->prev = NULL;

        delete usuwany;

}
void removeENDElement(list_t*& position) {

        list_t* usuwany = position->prev;
        if (usuwany->prev != NULL)
        {
                usuwany->prev->next = position;
                position->prev = usuwany->prev;

                usuwany->next = NULL;
                usuwany->prev = NULL;

                delete usuwany;
        }
}
void removeBEGElement(list_t*& position) {

        list_t* usuwany = position->next;
        if (usuwany->next != NULL)
        {
                usuwany->next->prev = position;
                position->next = usuwany->next;

                usuwany->next = NULL;
                usuwany->prev = NULL;

                delete usuwany;
        }
}
void IteratorInitMenu(list_t* BEGINNING, list_t* ENDING, list_t* iterators[]) {
        char commKind[5];
        int x;
        scanf(" %d %s", &x, commKind); 
        if (strcmp(commKind, "BEG") == 0)
        {
                iterators[x] = BEGINNING->next;
        }
        else if (strcmp(commKind, "END") == 0)
        {
                iterators[x] = ENDING->prev;
        }
        else
        {   
                list_t* wskaznikIndeksu = szukajElementu(BEGINNING, atoi(commKind));
                iterators[x] = wskaznikIndeksu;
        }
}
void nextIterator(list_t* iterators[]) {
        int x;
        scanf(" %d", &x);

        if (iterators[x]->next->next == NULL)
        {
                return;
        }
        iterators[x] = iterators[x]->next;

}
void prevIterator(list_t* iterators[]) {
        int x;
        scanf(" %d", &x);
        if (iterators[x]->prev->prev == NULL)
        {
                return;
        }
        iterators[x] = iterators[x]->prev;

}
list_t* szukajElementu(list_t* BEGINNING, int indeksSzukany) {
        list_t* szukanyWsk = BEGINNING->next; 
        int indeks = 0; 
        while (szukanyWsk->next != NULL)
        {
                if (indeks == indeksSzukany)
                {
                        return szukanyWsk;
                }
                indeks++;
                szukanyWsk = szukanyWsk->next;
        }
        return NULL;
}