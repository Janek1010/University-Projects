#include <iostream>
#include <stdio.h>  // e.g.functions: fscanf, fopen, gentline etc.
#include <stdlib.h> //e.g.functions: atoi, srand, qsort etc.
#include <string.h> // (e.g.functions: strcmp, strcpy etc.)
#include <math.h>
#include "Header.h"
using namespace std;

field_t** createFieldTab(int y, int x)
{ // tworzy tablicÃª dwuwymiarowÂ¹ o podanych wymiarach

    field_t** tab = new field_t * [y]; // wsk na poczatek tablicy wskaznikow, y = ilosc wierszy

    for (int z = 0; z < y; z++)
    {

        tab[z] = new field_t[x]; // w tablicy wskaznikow zwracam kazdy wskaznik na nowÂ¹ tablice
    }
    fillTab(tab, y, x);
    return tab;
}
void deleteFieldTab(field_t** field_Tab, int y, int x)
{

    for (int i = 0; i < y; i++)
    {
        delete[] field_Tab[i];
    }
}

int firstCommandState() // sprawdza czym jest pierwsza komenda dla state'a i zwraca wartosc potrzebna do switcha wskazujacego na poprawnego scanfa

{
    char command[COMMAND_MAX];
    cin >> command;
    if (strcmp(command, "PRINT") == 0)
    {
        return PRINT;
    }
    else if (strcmp(command, "SET_FLEET") == 0)
    {
        return FLEET;
    }
    else if (strcmp(command, "NEXT_PLAYER") == 0)
    {
        return NEXT;
    }
    else if (strcmp(command, "[state]") == 0)
    {
        return SECOUND_STATE;
    }
    else if (strcmp(command, "BOARD_SIZE") == 0)
    {
        return BOARD_SZ;
    }
    else if (strcmp(command, "INIT_POSITION") == 0)
    {
        return INIT_POS;
    }
    else if (strcmp(command, "REEF") == 0)
    {
        return REEF_SET;
    }
    else if (strcmp(command, "SHIP") == 0)
    {
        return SHIP_DAMAGED;
    }
    else if (strcmp(command, "EXTENDED_SHIPS") == 0)
    {
        return EXTENDED_SHIPS;
    }
    else
    {
        return BAD_INPUT;
    }
}

int firstCommandPlayerA() // sprawdza czym jest pierwsza komenda dla state'a i zwraca wartosc potrzebna do switcha wskazujacego na poprawnego scanfa
{
    char command[COMMAND_MAX];
    cin >> command;

    int value1 = strcmp(command, "PLACE_SHIP");
    int value2 = strcmp(command, "SHOOT");
    int value3 = strcmp(command, "[playerA]");

    if (value1 == 0)
    {
        return PLACE;
    }
    else if (value2 == 0)
    {
        return SHOOT;
    }
    else if (value3 == 0)
    {
        return SECOUND_PLAYER_A;
    }
    else
    {
        return BAD_INPUT_A;
    }
}
int firstCommandPlayerB() // sprawdza czym jest pierwsza komenda dla state'a i zwraca wartosc potrzebna do switcha wskazujacego na poprawnego scanfa
{
    char command[COMMAND_MAX];
    cin >> command;

    int value1 = strcmp(command, "PLACE_SHIP");
    int value2 = strcmp(command, "SHOOT");
    int value3 = strcmp(command, "[playerB]");

    if (value1 == 0)
    {
        return PLACE;
    }
    else if (value2 == 0)
    {
        return SHOOT;
    }
    else if (value3 == 0)
    {
        return SECOUND_PLAYER_B;
    }
    else
    {
        return BAD_INPUT_B;
    }
}

int commandKind(char komenda[])
{ // sprawdzam jaki rodzaj komendy zostal wpisany
    int value1 = strcmp(komenda, "[state]");
    int value2 = strcmp(komenda, "[playerA]");
    int value3 = strcmp(komenda, "[playerB]");
    int stan;
    if (value1 == 0)
    {
        stan = state;
    }
    else if (value2 == 0)
    {
        stan = playerA;
    }
    else if (value3 == 0)
    {
        stan = playerB;
    }
    else
    {
        stan = inne;
    }
    return stan;
}
void SET_FLEET(char player_choose, int car_par, int bat_par, int cru_par, int des_par, player_t* player_1, player_t* player_2)
{
    if (player_choose == 'A')
    {
        player_1->des_init_amount = des_par;
        player_1->cru_init_amount = cru_par;
        player_1->bat_init_amount = bat_par;
        player_1->car_init_amount = car_par;
    }
    else if (player_choose == 'B')
    {
        player_2->des_init_amount = des_par;
        player_2->cru_init_amount = cru_par;
        player_2->bat_init_amount = bat_par;
        player_2->car_init_amount = car_par;
    }
}
void fillTab(field_t** tab, int sizey, int sizex) // wypelnia tablice czyms jakas defaultowa wartoscia zeby dziwnych rzeczy nie pokazywalo
{
    for (int y = 0; y < sizey; y++)
    {
        for (int x = 0; x < sizex; x++)
        {
            tab[y][x].shooted = false;
            tab[y][x].part_of_ship = false;
            tab[y][x].reef = false;
            tab[y][x].cannon = false;
            tab[y][x].engine = false;
            tab[y][x].radar = false;
        }
    }
}
void PRINT_state(field_t** tab, int sizey, int sizex, char sign, sizes_tab_t* sizes_tab_actual)
{ // komenda print, wypisuje stan gry
    int A_fragments_remained_counter = 0;
    int B_fragments_remained_counter = 0;
    if (sign == '0')
    {
        for (int y = 0; y < sizey; y++)
        {
            for (int x = 0; x < sizex; x++)
            {
                if (tab[y][x].reef == true)
                {
                    cout << "#";
                }
                else if (tab[y][x].shooted == true && tab[y][x].part_of_ship == true)
                {
                    cout << "x";
                }
                else if (tab[y][x].part_of_ship == true)
                {
                    cout << "+";
                    if (y <= sizes_tab_actual->INIT_POSITION_A_Y2)
                    {
                        A_fragments_remained_counter++;
                    }
                    else
                    {
                        B_fragments_remained_counter++;
                    }
                }
                else
                {
                    cout << " ";
                }
            }
            cout << "\n";
        }
        printf("PARTS REMAINING:: A : %d B : %d\n", A_fragments_remained_counter, B_fragments_remained_counter);
    }
    else if (sign == '1')
    {
        int g = 0;
        cout << "  ";
        for (int x = 0; x < sizex; x++)
        {
            cout << x;
        }
        cout << endl;
        for (int y = 0; y < sizey; y++)
        {  
            if (y < 10)
            {
                cout << g;
            }
            cout << y;
            for (int x = 0; x < sizex; x++)
            {
                if (tab[y][x].reef == true)
                {
                    cout << "#";
                }
                else if (tab[y][x].shooted == true && tab[y][x].part_of_ship == true)
                {
                    cout << "x";
                }
                else if (tab[y][x].engine == true)
                {
                    cout << "%";
                    if (y <= sizes_tab_actual->INIT_POSITION_A_Y2)
                    {
                        A_fragments_remained_counter++;
                    }
                    else
                    {
                        B_fragments_remained_counter++;
                    }
                }
                else if (tab[y][x].radar == true)
                {
                    cout << "@";
                    if (y <= sizes_tab_actual->INIT_POSITION_A_Y2)
                    {
                        A_fragments_remained_counter++;
                    }
                    else
                    {
                        B_fragments_remained_counter++;
                    }
                }
                else if (tab[y][x].cannon == true)
                {
                    cout << "!";
                    if (y <= sizes_tab_actual->INIT_POSITION_A_Y2)
                    {
                        A_fragments_remained_counter++;
                    }
                    else
                    {
                        B_fragments_remained_counter++;
                    }
                }
                else if (tab[y][x].part_of_ship == true)
                {
                    cout << "+";
                    if (y <= sizes_tab_actual->INIT_POSITION_A_Y2)
                    {
                        A_fragments_remained_counter++;
                    }
                    else
                    {
                        B_fragments_remained_counter++;
                    }
                }
                else
                {
                    cout << " ";
                }
            }
            cout << "\n";
        }
        printf("PARTS REMAINING:: A : %d B : %d\n", A_fragments_remained_counter, B_fragments_remained_counter);
    }
    // gdzies tam na koncu napsiac informacje o rozwalonych czesciach
}
void PLACE_SHIP(int place_y, int place_x, char place_direction, int place_ship_number, char ship_kind[], player_t* player)
{ // ustawia podanemu statkowi wspolrzedne dzioba

    directions converted_direction = directionConverter(place_direction); // konwertuje mi kierunek na jedna z wartosci up, down, left, right
    switch (shipKind(ship_kind))
    {
    case DESTROYER:
        player->destroyers[place_ship_number].y = place_y;
        player->destroyers[place_ship_number].x = place_x;
        player->destroyers[place_ship_number].direction = converted_direction;
        break;
    case CRUISER:
        player->cruisers[place_ship_number].y = place_y;
        player->cruisers[place_ship_number].x = place_x;
        player->cruisers[place_ship_number].direction = converted_direction;
        break;
    case BATTLESHIP:
        player->battleships[place_ship_number].y = place_y;
        player->battleships[place_ship_number].x = place_x;
        player->battleships[place_ship_number].direction = converted_direction;
        break;
    case CARRIER:
        player->carriers[place_ship_number].y = place_y;
        player->carriers[place_ship_number].x = place_x;
        player->carriers[place_ship_number].direction = converted_direction;
        break;
    default:
        cout << "blad w placeship, obsluga bledu!";
        break;
    }
}
ships shipKind(char ship[])
{

    int value1 = strcmp(ship, "DES");
    int value2 = strcmp(ship, "CRU");
    int value3 = strcmp(ship, "BAT");
    int value4 = strcmp(ship, "CAR");

    if (value1 == 0)
    {
        return DESTROYER;
    }
    else if (value2 == 0)
    {
        return CRUISER;
    }
    else if (value3 == 0)
    {
        return BATTLESHIP;
    }
    else if (value4 == 0)
    {
        return CARRIER;
    }
    else
    {
        return BAD_INPUT_SHIP;
    }
}
directions directionConverter(char direction)

{ // konwertuje kierunek w inpucie na enuma directions

    if (direction == 'N')
    {
        return UP;
    }
    else if (direction == 'S')
    {
        return DOWN;
    }
    else if (direction == 'W')
    {
        return LEFT;
    }
    else if (direction == 'E')
    {
        return RIGHT;
    }
    else
    {
        cout << "blad w funkcji konwersji kierunku" << endl;
        return BAD_DIRECTION;
    }
}
void leftShips(int place_y, int place_x, char place_direction, int place_ship_number, char shipkind[], player_t* player, bool* ships_placed_cond)
{ // sprawdza walidacje dla zuzycia wszytskich statkow i postawienia tego samego statku przypadkiem

    switch (shipKind(shipkind))
    {
    case DESTROYER:
        player->des_init_amount--;
        if (player->destroyers_used[place_ship_number] == true)
        {
            printf("INVALID OPERATION \"PLACE_SHIP %d %d %c %d %s\": SHIP ALREADY PRESENT", place_y, place_x, place_direction, place_ship_number, shipkind);
            exit(1);
        }
        if (player->des_init_amount < 0)
        {
            printf("INVALID OPERATION \"PLACE_SHIP %d %d %c %d %s\": ALL SHIPS OF THE CLASS ALREADY SET", place_y, place_x, place_direction, place_ship_number, shipkind);
            exit(1);
        }
        player->destroyers_used[place_ship_number] = true;
        break;
    case CRUISER:
        player->cru_init_amount--;
        if (player->cruisers_used[place_ship_number] == true)
        {
            printf("INVALID OPERATION \"PLACE_SHIP %d %d %c %d %s\": SHIP ALREADY PRESENT", place_y, place_x, place_direction, place_ship_number, shipkind);
            exit(1);
        }
        if (player->cru_init_amount < 0)
        {
            printf("INVALID OPERATION \"PLACE_SHIP %d %d %c %d %s\": ALL SHIPS OF THE CLASS ALREADY SET", place_y, place_x, place_direction, place_ship_number, shipkind);
            exit(1);
        }

        player->cruisers_used[place_ship_number] = true;
        break;
    case BATTLESHIP:
        player->bat_init_amount--;
        if (player->battleships_used[place_ship_number] == true)
        {
            printf("INVALID OPERATION \"PLACE_SHIP %d %d %c %d %s\": SHIP ALREADY PRESENT", place_y, place_x, place_direction, place_ship_number, shipkind);
            exit(1);
        }
        if (player->bat_init_amount < 0)
        {
            printf("INVALID OPERATION \"PLACE_SHIP %d %d %c %d %s\": ALL SHIPS OF THE CLASS ALREADY SET", place_y, place_x, place_direction, place_ship_number, shipkind);
            exit(1);
        }

        player->battleships_used[place_ship_number] = true;
        break;
    case CARRIER:
        player->car_init_amount--;
        if (player->carriers_used[place_ship_number] == true)
        {
            printf("INVALID OPERATION \"PLACE_SHIP %d %d %c %d %s\": SHIP ALREADY PRESENT", place_y, place_x, place_direction, place_ship_number, shipkind);
            exit(1);
        }
        if (player->car_init_amount < 0)
        {
            printf("INVALID OPERATION \"PLACE_SHIP %d %d %c %d %s\": ALL SHIPS OF THE CLASS ALREADY SET", place_y, place_x, place_direction, place_ship_number, shipkind);
            exit(1);
        }
        player->carriers_used[place_ship_number] = true;
        break;
    default:
        cout << "blad w leftShips A, obsluga bledu!";
        break;
    }

    if (player->des_init_amount == 0 && player->cru_init_amount == 0 && player->bat_init_amount == 0 && player->car_init_amount == 0)
    {
        *ships_placed_cond = true;
    }
}
void cannonCheck(int place_ship_number, char ship_kind[], int shoot_y, int shoot_x, player_t* player, int* cannon_y, int* cannon_x)
{
    int type = shipKind(ship_kind);

    if (type == DESTROYER)
    {
        switch (player->destroyers[place_ship_number].direction)
        {
        case UP:
            *cannon_y = player->destroyers[place_ship_number].y + (type - 2);
            *cannon_x = player->destroyers[place_ship_number].x;
            break;
        case DOWN:
            *cannon_y = player->destroyers[place_ship_number].y - (type - 2);
            *cannon_x = player->destroyers[place_ship_number].x;
            break;
        case LEFT:
            *cannon_y = player->destroyers[place_ship_number].y;
            *cannon_x = player->destroyers[place_ship_number].x + (type - 2);
            break;
        case RIGHT:
            *cannon_y = player->destroyers[place_ship_number].y;
            *cannon_x = player->destroyers[place_ship_number].x - (type - 2);
            break;
        default:
            break;
        }
    }
    else if (type == CRUISER)
    {
        switch (player->cruisers[place_ship_number].direction)
        {
        case UP:
            *cannon_y = player->cruisers[place_ship_number].y + (type - 2);
            *cannon_x = player->cruisers[place_ship_number].x;
            break;
        case DOWN:
            *cannon_y = player->cruisers[place_ship_number].y - (type - 2);
            *cannon_x = player->cruisers[place_ship_number].x;
            break;
        case LEFT:
            *cannon_y = player->cruisers[place_ship_number].y;
            *cannon_x = player->cruisers[place_ship_number].x + (type - 2);
            break;
        case RIGHT:
            *cannon_y = player->cruisers[place_ship_number].y;
            *cannon_x = player->cruisers[place_ship_number].x - (type - 2);
            break;
        default:
            break;
        }
    }
    if (type == BATTLESHIP)
    {
        switch (player->battleships[place_ship_number].direction)
        {
        case UP:
            *cannon_y = player->battleships[place_ship_number].y + (type - 2);
            *cannon_x = player->battleships[place_ship_number].x;
            break;
        case DOWN:
            *cannon_y = player->battleships[place_ship_number].y - (type - 2);
            *cannon_x = player->battleships[place_ship_number].x;
            break;
        case LEFT:
            *cannon_y = player->battleships[place_ship_number].y;
            *cannon_x = player->battleships[place_ship_number].x + (type - 2);
            break;
        case RIGHT:
            *cannon_y = player->battleships[place_ship_number].y;
            *cannon_x = player->battleships[place_ship_number].x - (type - 2);
            break;
        default:
            break;
        }
    }
    if (type == CARRIER)
    {
        switch (player->carriers[place_ship_number].direction)
        {
        case UP:
            *cannon_y = player->carriers[place_ship_number].y + (type - 2);
            *cannon_x = player->carriers[place_ship_number].x;
            break;
        case DOWN:
            *cannon_y = player->carriers[place_ship_number].y - (type - 2);
            *cannon_x = player->carriers[place_ship_number].x;
            break;
        case LEFT:
            *cannon_y = player->carriers[place_ship_number].y;
            *cannon_x = player->carriers[place_ship_number].x + (type - 2);
            break;
        case RIGHT:
            *cannon_y = player->carriers[place_ship_number].y;
            *cannon_x = player->carriers[place_ship_number].x - (type - 2);
            break;
        default:
            break;
        }
    }
}
void SHOOT_BASIC(int shoot_y, int shoot_x, field_t** struct_tab, bool ships_placed, sizes_tab_t* sizes_tab_actual)
{
    if (ships_placed == false)
    {
        printf("INVALID OPERATION \"SHOOT %d %d\": NOT ALL SHIPS PLACED", shoot_y, shoot_x);
        exit(1);
    }
    else if (shoot_y < 0 || shoot_y > sizes_tab_actual->INIT_POSITION_B_Y2 || shoot_x < 0 || shoot_x > sizes_tab_actual->INIT_POSITION_B_X2) // tu tez warunki nie elastyczne
    {
        printf("INVALID OPERATION \"SHOOT %d %d\": FIELD DOES NOT EXIST", shoot_y, shoot_x);
        exit(1);
    }
    else
    {
        struct_tab[shoot_y][shoot_x].shooted = true;
    }
}
void SHOOT_EXTENDED(int place_ship_number, char ship_kind[], int shoot_y, int shoot_x, field_t** struct_tab, sizes_tab_t* sizes_tab_actual,
    int cannon_y, int cannon_x, player_t* player)
{ // wyluskac wspolrzedne cannona
    int type = shipKind(ship_kind);
    //dist < type
    float y_diff = (cannon_y - shoot_y);
    float x_diff = (cannon_x - shoot_x);
    float value_y = abs(y_diff);
    float value_x = abs(x_diff);
    float value_y_square = (value_y * value_y);
    float value_x_square = (value_x * value_x);
    float dist = sqrt((value_x_square)+(value_y_square));
    if (type != CARRIER && (dist > type + 1))
    {
        printf("INVALID OPERATION \"SHOOT %d %s %d %d\": SHOOTING TOO FAR", place_ship_number, ship_kind, shoot_y, shoot_x);
        exit(1);
    }
    else
    {
        struct_tab[shoot_y][shoot_x].shooted = true;
    }
    // the ship is not shooting too many shoots (TOO MANY SHOOTS),
}
char playerMove(char player) //zwraca gracza ktorego teraz jest ruch, jesli ustawimy w parametrze A lub B to ten gracz bedzie nastepny
{                            //NEXTPLAYER, and sequence
    static int counter = 2;
    if (player == 'A')
    {
        counter = 2;
        return 'A';
    }
    else if (player == 'B')
    {
        counter = 3;
        return 'B';
    }
    else
    {
        if ((counter % 2) == 0)
        {
            counter++;
            return 'B';
        }
        else
        {
            counter++;
            return 'A';
        }
    }
}
int initFragmTab(int place_y, int place_x, char place_direction, char ship_kind[], field_t** struct_tab, int place_ship_number,
    commandsMain player, sizes_tab_t* sizes_tab_actual, char destroyed_parts[], commandsState FLEET_OR_SHIP_DAMAGED, char playercomm) // switch okresla rodzaj sattku i wywoluje odpowiednia funkcje
{
    int shooted_counter = 0;
    for (int i = 0; i < DESTROYEDTAB; i++)
    {
        if (destroyed_parts[i] == '0')
        {
            shooted_counter++;
        }
    }
    int fragments_counter = 0;
    switch (shipKind(ship_kind))
    {
    case DESTROYER:
        place_part_of_ship(place_y, place_x, place_direction, DESTROYER, struct_tab, place_ship_number, ship_kind, player, sizes_tab_actual, destroyed_parts);
        shipsAroundCheck(place_y, place_x, place_direction, DESTROYER, struct_tab, place_ship_number, ship_kind, destroyed_parts, FLEET_OR_SHIP_DAMAGED, playercomm, sizes_tab_actual);
        fragments_counter += 2;
        break;
    case CRUISER:
        place_part_of_ship(place_y, place_x, place_direction, CRUISER, struct_tab, place_ship_number, ship_kind, player, sizes_tab_actual, destroyed_parts);
        shipsAroundCheck(place_y, place_x, place_direction, CRUISER, struct_tab, place_ship_number, ship_kind, destroyed_parts, FLEET_OR_SHIP_DAMAGED, playercomm, sizes_tab_actual);
        fragments_counter += 3;
        break;
    case BATTLESHIP:
        place_part_of_ship(place_y, place_x, place_direction, BATTLESHIP, struct_tab, place_ship_number, ship_kind, player, sizes_tab_actual, destroyed_parts);
        shipsAroundCheck(place_y, place_x, place_direction, BATTLESHIP, struct_tab, place_ship_number, ship_kind, destroyed_parts, FLEET_OR_SHIP_DAMAGED, playercomm, sizes_tab_actual);
        fragments_counter += 4;
        break;
    case CARRIER:
        place_part_of_ship(place_y, place_x, place_direction, CARRIER, struct_tab, place_ship_number, ship_kind, player, sizes_tab_actual, destroyed_parts);
        shipsAroundCheck(place_y, place_x, place_direction, CARRIER, struct_tab, place_ship_number, ship_kind, destroyed_parts, FLEET_OR_SHIP_DAMAGED, playercomm, sizes_tab_actual);
        fragments_counter += 5;
        break;
    default:
        cout << " w init fragmentTab blad" << endl;
        break;
    }
    return (fragments_counter - shooted_counter);
}
void shipsAroundCheck(int place_y, int place_x, char place_direction, ships type, field_t** struct_tab, int place_ship_number,
    char ship_kind[], char destroyed_parts[], commandsState FLEET_OR_SHIP_DAMAGED, char player, sizes_tab_t* sizes_tab_actual)
{ // sprawdza czy statek styka sie z jakimis innymi
    switch (directionConverter(place_direction))
    {
    case UP:
        for (int i = 0; i < type; i++)
        {
            if (place_y + i < place_y_MAX_B)
            {
                if ((place_x - i > place_x_MIN_B && struct_tab[place_y + i][place_x - 1].part_of_ship == true) || (place_x + 1 < place_x_MAX_B && struct_tab[place_y + i][place_x + 1].part_of_ship == true))
                {
                    if (FLEET_OR_SHIP_DAMAGED == FLEET)
                    {
                        printf("INVALID OPERATION \"PLACE_SHIP %d %d %c %d %s\": PLACING SHIP TOO CLOSE TO OTHER SHIP", place_y, place_x, place_direction,
                            place_ship_number, ship_kind);
                        exit(1);
                    }
                    else if (FLEET_OR_SHIP_DAMAGED == SHIP_DAMAGED)
                    {
                        printf("INVALID OPERATION \"SHIP %c %d %d %c %d %s %s\": PLACING SHIP TOO CLOSE TO OTHER SHIP", player, place_y, place_x,
                            place_direction, place_ship_number, ship_kind, destroyed_parts);
                        exit(1);
                    }
                }
            }
        }
        for (int i = 0; i < 3; i++)
        {
            if (place_x - 1 + i > place_x_MIN_B)
            {
                if ((place_y - i > place_y_MIN_A && struct_tab[place_y - 1][place_x - 1 + i].part_of_ship == true) || (place_y + type > place_x_MAX_B && struct_tab[place_y + type][place_x - 1 + i].part_of_ship == true))
                {
                    if (FLEET_OR_SHIP_DAMAGED == FLEET)
                    {
                        printf("INVALID OPERATION \"PLACE_SHIP %d %d %c %d %s\": PLACING SHIP TOO CLOSE TO OTHER SHIP", place_y, place_x, place_direction,
                            place_ship_number, ship_kind);
                        exit(1);
                    }
                    else if (FLEET_OR_SHIP_DAMAGED == SHIP_DAMAGED)
                    {
                        printf("INVALID OPERATION \"SHIP %c %d %d %c %d %s %s\": PLACING SHIP TOO CLOSE TO OTHER SHIP", player, place_y, place_x,
                            place_direction, place_ship_number, ship_kind, destroyed_parts);
                        exit(1);
                    }
                }
            }
        }
        break;
    case DOWN:
        for (int i = 0; i < type; i++)
        {
            if (place_y - i > place_y_MIN_A)
            {
                if ((place_x - i > place_x_MIN_B && struct_tab[place_y - i][place_x - 1].part_of_ship == true) || (place_x + 1 < place_x_MAX_B && struct_tab[place_y - i][place_x + 1].part_of_ship == true))
                {
                    if (FLEET_OR_SHIP_DAMAGED == FLEET)
                    {
                        printf("INVALID OPERATION \"PLACE_SHIP %d %d %c %d %s\": PLACING SHIP TOO CLOSE TO OTHER SHIP", place_y, place_x, place_direction,
                            place_ship_number, ship_kind);
                        exit(1);
                    }
                    else if (FLEET_OR_SHIP_DAMAGED == SHIP_DAMAGED)
                    {
                        printf("INVALID OPERATION \"SHIP %c %d %d %c %d %s %s\": PLACING SHIP TOO CLOSE TO OTHER SHIP", player, place_y, place_x,
                            place_direction, place_ship_number, ship_kind, destroyed_parts);
                        exit(1);
                    }
                }
            }
        }
        for (int i = 0; i < 3; i++)
        {
            if (place_x - 1 > place_x_MIN_B)
            {
                if ((place_y + i < place_y_MAX_A && struct_tab[place_y + 1][place_x - 1 + i].part_of_ship == true) || (place_y - type > place_y_MIN_A && struct_tab[place_y - type][place_x - 1 + i].part_of_ship == true))
                {
                    if (FLEET_OR_SHIP_DAMAGED == FLEET)
                    {
                        printf("INVALID OPERATION \"PLACE_SHIP %d %d %c %d %s\": PLACING SHIP TOO CLOSE TO OTHER SHIP", place_y, place_x, place_direction,
                            place_ship_number, ship_kind);
                        exit(1);
                    }
                    else if (FLEET_OR_SHIP_DAMAGED == SHIP_DAMAGED)
                    {
                        printf("INVALID OPERATION \"SHIP %c %d %d %c %d %s %s\": PLACING SHIP TOO CLOSE TO OTHER SHIP", player, place_y, place_x,
                            place_direction, place_ship_number, ship_kind, destroyed_parts);
                        exit(1);
                    }
                }
            }
        }
        break;
    case LEFT:
        for (int i = 0; i < type; i++)
        {
            if (place_x + i < place_x_MAX_B)
            {
                if ((place_y - i > place_y_MIN_A && struct_tab[place_y - 1][place_x + i].part_of_ship == true) || ((place_y + 1) < place_y_MAX_B && struct_tab[place_y + 1][place_x + i].part_of_ship == true))
                {
                    if (FLEET_OR_SHIP_DAMAGED == FLEET)
                    {
                        printf("INVALID OPERATION \"PLACE_SHIP %d %d %c %d %s\": PLACING SHIP TOO CLOSE TO OTHER SHIP", place_y, place_x, place_direction,
                            place_ship_number, ship_kind);
                        exit(1);
                    }
                    else if (FLEET_OR_SHIP_DAMAGED == SHIP_DAMAGED)
                    {
                        printf("INVALID OPERATION \"SHIP %c %d %d %c %d %s %s\": PLACING SHIP TOO CLOSE TO OTHER SHIP", player, place_y, place_x,
                            place_direction, place_ship_number, ship_kind, destroyed_parts);
                        exit(1);
                    }
                }
            }
        }
        for (int i = 0; i < 3; i++)
        {
            if (place_y - 1 + i > place_y_MIN_A && place_y - 1 + i < place_y_MAX_B)
            {
                if (((place_x - 1) >= place_x_MIN_B && struct_tab[place_y - 1 + i][place_x - 1].part_of_ship == true) || ((place_x + type) < place_x_MAX_B && struct_tab[place_y - 1 + i][place_x + type].part_of_ship == true))
                {
                    if (FLEET_OR_SHIP_DAMAGED == FLEET)
                    {
                        printf("INVALID OPERATION \"PLACE_SHIP %d %d %c %d %s\": PLACING SHIP TOO CLOSE TO OTHER SHIP", place_y, place_x, place_direction,
                            place_ship_number, ship_kind);
                        exit(1);
                    }
                    else if (FLEET_OR_SHIP_DAMAGED == SHIP_DAMAGED)
                    {
                        printf("INVALID OPERATION \"SHIP %c %d %d %c %d %s %s\": PLACING SHIP TOO CLOSE TO OTHER SHIP", player, place_y, place_x,
                            place_direction, place_ship_number, ship_kind, destroyed_parts);
                        exit(1);
                    }
                }
            }
        }
        break;
    case RIGHT:
        for (int i = 0; i < type; i++)
        {
            if (place_x - i > place_x_MIN_B)
            {
                if ((place_y - 1 > place_y_MIN_A && struct_tab[place_y - 1][place_x - i].part_of_ship == true) || (place_y + 1 < place_y_MAX_B && struct_tab[place_y + 1][place_x - i].part_of_ship == true))
                {
                    if (FLEET_OR_SHIP_DAMAGED == FLEET)
                    {
                        printf("INVALID OPERATION \"PLACE_SHIP %d %d %c %d %s\": PLACING SHIP TOO CLOSE TO OTHER SHIP", place_y, place_x, place_direction,
                            place_ship_number, ship_kind);
                        exit(1);
                    }
                    else if (FLEET_OR_SHIP_DAMAGED == SHIP_DAMAGED)
                    {
                        printf("INVALID OPERATION \"SHIP %c %d %d %c %d %s %s\": PLACING SHIP TOO CLOSE TO OTHER SHIP", player, place_y, place_x,
                            place_direction, place_ship_number, ship_kind, destroyed_parts);
                        exit(1);
                    }
                }
            }
        }
        for (int i = 0; i < 3; i++)
        {
            if (place_y - 1 > place_y_MIN_A)
            {
                if ((place_x + 1 < place_x_MAX_B && struct_tab[place_y - 1 + i][place_x + 1].part_of_ship == true) || (place_y - type > place_x_MIN_B && struct_tab[place_y - 1 + i][place_x - type].part_of_ship == true))
                {
                    if (FLEET_OR_SHIP_DAMAGED == FLEET)
                    {
                        printf("INVALID OPERATION \"PLACE_SHIP %d %d %c %d %s\": PLACING SHIP TOO CLOSE TO OTHER SHIP", place_y, place_x, place_direction,
                            place_ship_number, ship_kind);
                        exit(1);
                    }
                    else if (FLEET_OR_SHIP_DAMAGED == SHIP_DAMAGED)
                    {
                        printf("INVALID OPERATION \"SHIP %c %d %d %c %d %s %s\": PLACING SHIP TOO CLOSE TO OTHER SHIP", player, place_y, place_x,
                            place_direction, place_ship_number, ship_kind, destroyed_parts);
                        exit(1);
                    }
                }
            }
        }
        break;
    default:
        break;
    }
}

void place_part_of_ship(int place_y, int place_x, char place_direction, ships type, field_t** struct_tab, int place_ship_number,
    char ship_kind[], commandsMain player, sizes_tab_t* sizes_tab_actual, char destroyed_parts[])
    //w zaleznosci od kierunku stawia fragmenty statku na planszy
{
    if (player == playerA)
    {
        switch (directionConverter(place_direction))
        {
        case UP:
            for (int i = 0; i < type; i++)
            {
                if ((place_y + i) < sizes_tab_actual->INIT_POSITION_A_Y1 || (place_y + i) > sizes_tab_actual->INIT_POSITION_A_Y2 || (place_x) < sizes_tab_actual->INIT_POSITION_A_X1 || (place_x) > sizes_tab_actual->INIT_POSITION_A_X2)
                {
                    printf("INVALID OPERATION \"PLACE_SHIP %d %d %c %d %s\": NOT IN STARTING POSITION", place_y, place_x, place_direction, place_ship_number, ship_kind);
                    exit(1);
                }
                else if (struct_tab[place_y + i][place_x].reef == true)
                {
                    printf("INVALID OPERATION \"PLACE_SHIP %d %d %c %d %s\": PLACING SHIP ON REEF", place_y, place_x, place_direction, place_ship_number, ship_kind);
                    exit(1);
                }
                else
                {
                    struct_tab[place_y + i][place_x].part_of_ship = true;
                }
                if (destroyed_parts[i] == '0')
                {
                    struct_tab[place_y + i][place_x].shooted = true;
                }
                if (i == 1)
                {
                    struct_tab[place_y + i][place_x].cannon = true;
                }
                if (i == 0)
                {
                    struct_tab[place_y + i][place_x].radar = true;
                }
                if (i == (type-1))
                {
                    struct_tab[place_y + i][place_x].engine = true;
                }
            }
            break;
        case DOWN:
            for (int i = 0; i < type; i++)
            {
                if ((place_y - i) < sizes_tab_actual->INIT_POSITION_A_Y1 || (place_y - i) > sizes_tab_actual->INIT_POSITION_A_Y2 || (place_x) < sizes_tab_actual->INIT_POSITION_A_X1 || (place_x) > sizes_tab_actual->INIT_POSITION_A_X2)
                {
                    printf("INVALID OPERATION \"PLACE_SHIP %d %d %c %d %s\": NOT IN STARTING POSITION", place_y, place_x, place_direction, place_ship_number, ship_kind);
                    exit(1);
                }
                else if (struct_tab[place_y - i][place_x].reef == true)
                {
                    printf("INVALID OPERATION \"PLACE_SHIP %d %d %c %d %s\": PLACING SHIP ON REEF", place_y, place_x, place_direction, place_ship_number, ship_kind);
                    exit(1);
                }
                else
                {
                    struct_tab[place_y - i][place_x].part_of_ship = true;
                }
                if (destroyed_parts[i] == '0')
                {
                    struct_tab[place_y - i][place_x].shooted = true;
                }
                if (i == 1)
                {
                    struct_tab[place_y - i][place_x].cannon = true;
                }
                if (i == 0)
                {
                    struct_tab[place_y - i][place_x].radar = true;
                }
                if (i == (type - 1))
                {
                    struct_tab[place_y - i][place_x].engine = true;
                }
            }
            break;
        case LEFT:
            for (int i = 0; i < type; i++)
            {
                if ((place_y) < sizes_tab_actual->INIT_POSITION_A_Y1 || (place_y) > sizes_tab_actual->INIT_POSITION_A_Y2 || (place_x + i) < sizes_tab_actual->INIT_POSITION_A_X1 || (place_x + i) > sizes_tab_actual->INIT_POSITION_A_X2)
                {
                    printf("INVALID OPERATION \"PLACE_SHIP %d %d %c %d %s\": NOT IN STARTING POSITION", place_y, place_x, place_direction, place_ship_number, ship_kind);
                    exit(1);
                }
                else if (struct_tab[place_y][place_x + i].reef == true)
                {
                    printf("INVALID OPERATION \"PLACE_SHIP %d %d %c %d %s\": PLACING SHIP ON REEF", place_y, place_x, place_direction, place_ship_number, ship_kind);
                    exit(1);
                }
                else
                {
                    struct_tab[place_y][place_x + i].part_of_ship = true;
                }
                if (destroyed_parts[i] == '0')
                {
                    struct_tab[place_y][place_x + i].shooted = true;
                }
                if (i == 1)
                {
                    struct_tab[place_y][place_x + i].cannon = true;
                }
                if (i == 0)
                {
                    struct_tab[place_y][place_x + i].radar = true;
                }
                if (i == (type - 1))
                {
                    struct_tab[place_y][place_x + i].engine = true;
                }
            }
            break;
        case RIGHT:
            for (int i = 0; i < type; i++)
            {
                if ((place_y) < sizes_tab_actual->INIT_POSITION_A_Y1 || (place_y) > sizes_tab_actual->INIT_POSITION_A_Y2 || (place_x - i) < sizes_tab_actual->INIT_POSITION_A_X1 || (place_x - i) > sizes_tab_actual->INIT_POSITION_A_X2)
                {
                    printf("INVALID OPERATION \"PLACE_SHIP %d %d %c %d %s\": NOT IN STARTING POSITION", place_y, place_x, place_direction, place_ship_number, ship_kind);
                    exit(1);
                }
                else if (struct_tab[place_y][place_x - i].reef == true)
                {
                    printf("INVALID OPERATION \"PLACE_SHIP %d %d %c %d %s\": PLACING SHIP ON REEF", place_y, place_x, place_direction, place_ship_number, ship_kind);
                    exit(1);
                }
                else
                {
                    struct_tab[place_y][place_x - i].part_of_ship = true;
                }
                if (destroyed_parts[i] == '0')
                {
                    struct_tab[place_y][place_x - i].shooted = true;
                }
                if (i == 1)
                {
                    struct_tab[place_y][place_x - i].cannon = true;
                }
                if (i == 0)
                {
                    struct_tab[place_y][place_x - i].radar = true;
                }
                if (i == (type - 1))
                {
                    struct_tab[place_y][place_x - i].engine = true;
                }
            }
            break;
        default:
            cout << "blad w place part of ship" << endl;
            break;
        }
    }
    else if (player == playerB)
    {
        switch (directionConverter(place_direction))
        {
        case UP:
            for (int i = 0; i < type; i++)
            {
                if ((place_y + i) < sizes_tab_actual->INIT_POSITION_B_Y1 || (place_y + i) > sizes_tab_actual->INIT_POSITION_B_Y2 || (place_x) < sizes_tab_actual->INIT_POSITION_B_X1 || (place_x) > sizes_tab_actual->INIT_POSITION_B_X2)
                {
                    printf("INVALID OPERATION \"PLACE_SHIP %d %d %c %d %s\": NOT IN STARTING POSITION", place_y, place_x, place_direction, place_ship_number, ship_kind);
                    exit(1);
                }
                else if (struct_tab[place_y + i][place_x].reef == true)
                {
                    printf("INVALID OPERATION \"PLACE_SHIP %d %d %c %d %s\": PLACING SHIP ON REEF", place_y, place_x, place_direction, place_ship_number, ship_kind);
                    exit(1);
                }
                else
                {
                    struct_tab[place_y + i][place_x].part_of_ship = true;
                }
                if (destroyed_parts[i] == '0')
                {
                    struct_tab[place_y + i][place_x].shooted = true;
                }
                if (i == 1)
                {
                    struct_tab[place_y + i][place_x].cannon = true;
                }
                if (i == 0)
                {
                    struct_tab[place_y + i][place_x].radar = true;
                }
                if (i == (type - 1))
                {
                    struct_tab[place_y + i][place_x].engine = true;
                }
            }
            break;
        case DOWN:
            for (int i = 0; i < type; i++)
            {
                if ((place_y - i) < sizes_tab_actual->INIT_POSITION_B_Y1 || (place_y - i) > sizes_tab_actual->INIT_POSITION_B_Y2 || (place_x) < sizes_tab_actual->INIT_POSITION_B_X1 || (place_x) > sizes_tab_actual->INIT_POSITION_B_X2)
                {
                    printf("INVALID OPERATION \"PLACE_SHIP %d %d %c %d %s\": NOT IN STARTING POSITION", place_y, place_x, place_direction, place_ship_number, ship_kind);
                    exit(1);
                }
                else if (struct_tab[place_y - i][place_x].reef == true)
                {
                    printf("INVALID OPERATION \"PLACE_SHIP %d %d %c %d %s\": PLACING SHIP ON REEF", place_y, place_x, place_direction, place_ship_number, ship_kind);
                    exit(1);
                }
                else
                {
                    struct_tab[place_y - i][place_x].part_of_ship = true;
                }
                if (destroyed_parts[i] == '0')
                {
                    struct_tab[place_y - i][place_x].shooted = true;
                }
                if (i == 1)
                {
                    struct_tab[place_y - i][place_x].cannon = true;
                }
                if (i == 0)
                {
                    struct_tab[place_y - i][place_x].radar = true;
                }
                if (i == (type - 1))
                {
                    struct_tab[place_y - i][place_x].engine = true;
                }
            }
            break;
        case LEFT:
            for (int i = 0; i < type; i++)
            {
                if ((place_y) < sizes_tab_actual->INIT_POSITION_B_Y1 || (place_y) > sizes_tab_actual->INIT_POSITION_B_Y2 || (place_x + i) < sizes_tab_actual->INIT_POSITION_B_X1 || (place_x + i) > sizes_tab_actual->INIT_POSITION_B_X2)
                {
                    printf("INVALID OPERATION \"PLACE_SHIP %d %d %c %d %s\": NOT IN STARTING POSITION", place_y, place_x, place_direction, place_ship_number, ship_kind);
                    exit(1);
                }
                else if (struct_tab[place_y][place_x + i].reef == true)
                {
                    printf("INVALID OPERATION \"PLACE_SHIP %d %d %c %d %s\": PLACING SHIP ON REEF", place_y, place_x, place_direction, place_ship_number, ship_kind);
                    exit(1);
                }
                else
                {
                    struct_tab[place_y][place_x + i].part_of_ship = true;
                }
                if (destroyed_parts[i] == '0')
                {
                    struct_tab[place_y][place_x + i].shooted = true;
                }
                if (i == 1)
                {
                    struct_tab[place_y][place_x + i].cannon = true;
                }
                if (i == 0)
                {
                    struct_tab[place_y][place_x + i].radar = true;
                }
                if (i == (type - 1))
                {
                    struct_tab[place_y][place_x + i].engine = true;
                }
            }
            break;
        case RIGHT:
            for (int i = 0; i < type; i++)
            {
                if ((place_y) < sizes_tab_actual->INIT_POSITION_B_Y1 || (place_y) > sizes_tab_actual->INIT_POSITION_B_Y2 || (place_x - i) < sizes_tab_actual->INIT_POSITION_B_X1 || (place_x - i) > sizes_tab_actual->INIT_POSITION_B_X2)
                {
                    printf("INVALID OPERATION \"PLACE_SHIP %d %d %c %d %s\": NOT IN STARTING POSITION", place_y, place_x, place_direction, place_ship_number, ship_kind);
                    exit(1);
                }
                else if (struct_tab[place_y][place_x - i].reef == true)
                {
                    printf("INVALID OPERATION \"PLACE_SHIP %d %d %c %d %s\": PLACING SHIP ON REEF", place_y, place_x, place_direction, place_ship_number, ship_kind);
                    exit(1);
                }
                else
                {
                    struct_tab[place_y][place_x - i].part_of_ship = true;
                }
                if (destroyed_parts[i] == '0')
                {
                    struct_tab[place_y][place_x - i].shooted = true;
                }
                if (i == 1)
                {
                    struct_tab[place_y][place_x - i].cannon = true;
                }
                if (i == 0)
                {
                    struct_tab[place_y][place_x - i].radar = true;
                }
                if (i == (type - 1))
                {
                    struct_tab[place_y][place_x - i].engine = true;
                }
            }
            break;
        default:
            cout << "blad w place part of ship" << endl;
            break;
        }
    }
}
int shootCheck(int shoot_y, int shoot_x, field_t** struct_tab, player_t* player)
{ // sprawdza czy strzelone pole bylo czyjegos rgacza, jesli tak to zwraca wartosc, pamietac ze sprawdzam pola rpzeciwnika
    // NIE JESTEM ANI TROCHE DUMNY Z TEJ FUNKCJI!!!!
    if (struct_tab[shoot_y][shoot_x].shooted == true && struct_tab[shoot_y][shoot_x].part_of_ship == true)
    {
        for (int i = 0; i < MAX_SHIPS; i++)
        {
            for (int z = 0; z < DESTROYER; z++)
            {
                switch (player->destroyers[i].direction)
                {
                case UP:
                    if (((player->destroyers[i].y + z) == shoot_y && player->destroyers[i].x == shoot_x))
                    {
                        return 1;
                    }
                    break;
                case DOWN:
                    if (((player->destroyers[i].y - z) == shoot_y && player->destroyers[i].x == shoot_x))
                    {
                        return 1;
                    }
                    break;
                case LEFT:
                    if (((player->destroyers[i].y) == shoot_y && (player->destroyers[i].x + z) == shoot_x))
                    {
                        return 1;
                    }
                    break;
                case RIGHT:
                    if (((player->destroyers[i].y) == shoot_y && (player->destroyers[i].x - z) == shoot_x))
                    {
                        return 1;
                    }
                    break;
                default:
                    break;
                }
            }
            for (int z = 0; z < CRUISER; z++)
            {
                switch (player->cruisers[i].direction)
                {
                case UP:
                    if (((player->cruisers[i].y + z) == shoot_y && player->cruisers[i].x == shoot_x))
                    {
                        return 1;
                    }
                    break;
                case DOWN:
                    if (((player->cruisers[i].y - z) == shoot_y && player->cruisers[i].x == shoot_x))
                    {
                        return 1;
                    }
                    break;
                case LEFT:
                    if (((player->cruisers[i].y) == shoot_y && (player->cruisers[i].x + z) == shoot_x))
                    {
                        return 1;
                    }
                    break;
                case RIGHT:
                    if (((player->cruisers[i].y) == shoot_y && (player->cruisers[i].x - z) == shoot_x))
                    {
                        return 1;
                    }
                    break;
                default:
                    break;
                }
            }
            for (int z = 0; z < BATTLESHIP; z++)
            {
                switch (player->battleships[i].direction)
                {
                case UP:
                    if (((player->battleships[i].y + z) == shoot_y && player->battleships[i].x == shoot_x))
                    {
                        return 1;
                    }
                    break;
                case DOWN:
                    if (((player->battleships[i].y - z) == shoot_y && player->battleships[i].x == shoot_x))
                    {
                        return 1;
                    }
                    break;
                case LEFT:
                    if (((player->battleships[i].y) == shoot_y && (player->battleships[i].x + z) == shoot_x))
                    {
                        return 1;
                    }
                    break;
                case RIGHT:
                    if (((player->battleships[i].y) == shoot_y && (player->battleships[i].x - z) == shoot_x))
                    {
                        return 1;
                    }
                    break;
                default:
                    break;
                }
            }
            for (int z = 0; z < CARRIER; z++)
            {
                switch (player->carriers[i].direction)
                {
                case UP:
                    if (((player->carriers[i].y + z) == shoot_y && player->carriers[i].x == shoot_x))
                    {
                        return 1;
                    }
                    break;
                case DOWN:
                    if (((player->carriers[i].y - z) == shoot_y && player->carriers[i].x == shoot_x))
                    {
                        return 1;
                    }
                    break;
                case LEFT:
                    if (((player->carriers[i].y) == shoot_y && (player->carriers[i].x + z) == shoot_x))
                    {
                        return 1;
                    }
                    break;
                case RIGHT:
                    if (((player->carriers[i].y) == shoot_y && (player->carriers[i].x - z) == shoot_x))
                    {
                        return 1;
                    }
                    break;
                default:
                    break;
                }
            }
        }
    }
    return 0; // jesli nie ma takiego pola u graczy to zwroci wartosc 0
}