#include <iostream>
#include <string.h>
#include <stdio.h>
using namespace std;

void generateBoard(int**& tab);
void deleteBoard(int** tab);
void showBoard(int** tab);
void possibleConfigurations(int**& tab, int iloscWystapien, int player);
bool winCheck(int** tab, int player);
bool possibleConfigurationsToFirstOccur(int**& tab, int iloscWystapien, int player);
bool isMovesLeft(int**& b);
int evaluate(int**& b, int maximizer, int minimizer);
int minimax(int**& b, int depth, bool isMax, int maximizer, int minimizer);
void findResult(int**& board, bool isMax, int maximizer, int minimizer);

int N, M, K;
int main()
{
        int player1 = 1;
        int     player2 = 2;

        char command[40];
        int activePlayer;
        int** board = nullptr;
        generateBoard(board);
        while (scanf("%s", command) > 0)
        {
                if (feof(stdin) != 0)
                {
                        break;
                }
                if (strcmp(command, "GEN_ALL_POS_MOV") == 0)
                {
                        scanf("%d", &N);
                        scanf("%d", &M);
                        scanf("%d", &K);
                        scanf("%d", &activePlayer);

                        generateBoard(board);
                        if (winCheck(board,player1) == true || winCheck(board,player2) == true)
                        {
                                printf("%d\n", 0);
                        }
                        else
                        {
                                possibleConfigurations(board, 0, activePlayer);
                        }
                        deleteBoard(board);
                }
                else if (strcmp(command, "GEN_ALL_POS_MOV_CUT_IF_GAME_OVER") == 0)
                {
                        scanf("%d", &N);
                        scanf("%d", &M);
                        scanf("%d", &K);
                        scanf("%d", &activePlayer);

                        generateBoard(board);
                        if (winCheck(board, player1) == true || winCheck(board, player2) == true)
                        {
                                printf("%d\n", 0);
                        }
                        else
                        {
                                if (possibleConfigurationsToFirstOccur(board, 0, activePlayer) == false)
                                {
                                        possibleConfigurations(board, 0, activePlayer);
                                }
                        }
                        deleteBoard(board);
                }
                else if (strcmp(command, "SOLVE_GAME_STATE") == 0)
                {
                        scanf("%d", &N);
                        scanf("%d", &M);
                        scanf("%d", &K);
                        scanf("%d", &activePlayer);

                        generateBoard(board);

                        if (activePlayer == 1)
                        {
                                findResult(board, true, 1, 2);
                        }
                        else
                        {
                                findResult(board, true, 2, 1);
                        }

                        deleteBoard(board);
                }
                else
                {
                        printf("Zla komenda");
                }
        }
}
void generateBoard(int**& tab) { // M  rzedy , N kolumny
        tab = new int* [N];
        for (int i = 0; i < N; i++)
        {
                tab[i] = new int[M];
        }
        for (int i = 0; i < N; i++)
        {
                for (int k = 0; k < M; k++)
                {
                        scanf("%d", &tab[i][k]);
                }
        }
}
void deleteBoard(int** tab) {
        for (int i = 0; i < N; i++)
        {
                delete[] tab[i];
        }
        delete[] tab;
        tab = NULL;
}
void showBoard(int** tab) {
        for (int i = 0; i < N; i++)
        {
                for (int k = 0; k < M; k++)
                {
                        printf("%d ", tab[i][k]);
                }
                printf("\n");
        }
}
void possibleConfigurations(int**& tab, int iloscWystapien, int player) {
        for (int i = 0; i < N; i++)
        {
                for (int k = 0; k < M; k++)
                {
                        if (tab[i][k] == 0)
                        {
                                iloscWystapien++;
                        }
                }
        }
        if (iloscWystapien == 0)
        {
                printf("%d\n", 0);
                return;
        }
        printf("%d\n", iloscWystapien);
        for (int i = 0; i < N; i++)
        {
                for (int k = 0; k < M; k++)
                {
                        if (tab[i][k] == 0)
                        {
                                if (player == 1)
                                {
                                        tab[i][k] = 1;
                                        showBoard(tab);
                                        tab[i][k] = 0;
                                }
                                else if (player == 2)
                                {
                                        tab[i][k] = 2;
                                        showBoard(tab);
                                        tab[i][k] = 0;
                                }
                        }
                }
                //printf("\n");
        }
        //printf("\n");
}
bool possibleConfigurationsToFirstOccur(int**& tab, int iloscWystapien, int player) {
        for (int i = 0; i < N; i++)
        {
                for (int k = 0; k < M; k++)
                {
                        if (tab[i][k] == 0)
                        {
                                iloscWystapien++;
                        }
                }
        }
        if (iloscWystapien == 0)
        {
                printf("%d\n", 0);
                return true;
        }
        for (int i = 0; i < N; i++)
        {
                for (int k = 0; k < M; k++)
                {
                        if (tab[i][k] == 0)
                        {
                                if (player == 1)
                                {
                                        tab[i][k] = 1;
                                        if (winCheck(tab,player) == true)
                                        {
                                                printf("%d\n", 1);
                                                showBoard(tab);
                                                return true;
                                        }
                                        tab[i][k] = 0;
                                }
                                else if (player == 2)
                                {
                                        tab[i][k] = 2;
                                        if (winCheck(tab,  player) == true)
                                        {
                                                printf("%d\n", 1);
                                                showBoard(tab);
                                                return true;
                                        }
                                        tab[i][k] = 0;
                                }
                        }
                }
        }
        return false;
}
bool winCheck(int** tab, int player) {
        int rows = 0;
        int columns = 0;
        int bias = 0;
        for (int i = 0; i < N; i++)
        {
                for (int k = 0; k < M; k++)
                {
                        if (tab[i][k] == player)
                        {
                                rows++;
                                if (rows == K)
                                {
                                        return true;
                                }
                        }
                        else
                        {
                                rows = 0;
                        }
                }
                rows = 0;
        }
        for (int i = 0; i < M; i++)
        {
                for (int k = 0; k < N; k++)
                {
                        if (tab[k][i] == player)
                        {
                                columns++;
                                if (columns == K)
                                {
                                        return true;
                                }
                        }
                        else
                        {
                                columns = 0;
                        }
                }
                columns = 0;
        }
        for (int i = 0; i < N; i++)
        {
                for (int k = 0; k < M; k++)
                {
                        if (tab[i][k] == player)
                        {
                                bias = 1;
                                int g = i + 1;
                                int o = k + 1;
                                while ((g < N && o < M) && tab[g][o] == player)
                                {
                                        bias++;
                                        if (bias == K)
                                        {
                                                return true;
                                        }
                                        g = g + 1;
                                        o = o + 1;
                                }
                                bias = 1;
                                int c = i + 1;
                                int b = k - 1;
                                while ((c < N && b >= 0) && tab[c][b] == player)
                                {
                                        bias++;
                                        if (bias == K)
                                        {
                                                return true;
                                        }
                                        c = c + 1;
                                        b = b - 1;
                                }
                                bias = 0;
                        }
                }
        }
        return false;
}
bool isMovesLeft(int**& b)
{
        for (int i = 0; i < N; i++)
                for (int j = 0; j < M; j++)
                        if (b[i][j] == 0)
                                return true;
        return false;
}
int evaluate(int**& b, int maximizer, int minimizer)
{
        int rowsMax = 0;
        int rowsMin = 0;
        for (int row = 0; row < N; row++)
        {
                for (int col = 0; col < M; col++)
                {
                        if (b[row][col] == maximizer)
                        {
                                rowsMin = 0;
                                rowsMax++;
                                if (rowsMax == K)
                                {
                                        return +100;
                                }
                        }
                        else if (b[row][col] == minimizer)
                        {
                                rowsMax = 0;
                                rowsMin++;
                                if (rowsMin == K)
                                {
                                        return -100;
                                }
                        }
                        else
                        {
                                rowsMax = 0;
                                rowsMin = 0;
                        }
                }
                rowsMax = 0;
                rowsMin = 0;
        }

        int columnsMax = 0;
        int columnsMin = 0;
        for (int col = 0; col < M; col++)
        {
                for (int row = 0; row < N; row++)
                {
                        if (b[row][col] == maximizer)
                        {
                                columnsMin = 0;
                                columnsMax++;
                                if (columnsMax == K)
                                {
                                        return +100;
                                }
                        }
                        else if (b[row][col] == minimizer)
                        {
                                columnsMax = 0;
                                columnsMin++;
                                if (columnsMin == K)
                                {
                                        return -100;
                                }
                        }
                        else
                        {
                                columnsMax = 0;
                                columnsMin = 0;
                        }
                }
                columnsMax = 0;
                columnsMin = 0;
        }
        int bias = 0;
        for (int i = 0; i < N; i++)
        {
                for (int k = 0; k < M; k++)
                {
                        if (b[i][k] != 0)
                        {
                                bias = 1;
                                int g = i + 1;
                                int o = k + 1;
                                while ((g < N && o < M) && b[g][o] == b[i][k])
                                {
                                        bias++;
                                        if (bias == K)
                                        {
                                                if (b[i][k] == maximizer)
                                                {
                                                        return +100;
                                                }
                                                else if (b[i][k] == minimizer) {
                                                        return -100;
                                                }
                                        }
                                        g = g + 1;
                                        o = o + 1;
                                }
                                bias = 1;
                                int c = i + 1;
                                int f = k - 1;
                                while ((c < N && f >= 0) && b[c][f] == b[i][k])
                                {
                                        bias++;
                                        if (bias == K)
                                        {
                                                if (b[i][k] == maximizer)
                                                {
                                                        return +100;
                                                }
                                                else if (b[i][k] == minimizer) {
                                                        return -100;
                                                }
                                        }
                                        c = c + 1;
                                        f = f - 1;
                                }
                                bias = 0;
                        }
                }
        }
        return 0;
}
int minimax(int**& b, int depth, bool isMax, int maximizer, int minimizer)
{
        int score = evaluate(b, maximizer, minimizer);
        if (score == 100)
        {
                return score - depth;
        }
        if (score == -100) {
                return score + depth;
        }
        if (isMovesLeft(b) == false)
        {
                return 0;
        }

        if (isMax == true)
        {
                int best = -10000;

                for (int i = 0; i < N; i++)
                {
                        for (int j = 0; j < M; j++)
                        {
                                if (b[i][j] == 0)
                                {
                                        b[i][j] = maximizer;
                                        int value = minimax(b, depth + 1, !isMax, maximizer, minimizer);
                                        if (value > best)
                                        {
                                                best = value;
                                        }
                                        if (best == 100)
                                        {
                                                return best;
                                        }
                                        b[i][j] = 0;
                                }
                        }
                }
                return best;
        }
        else
        {
                int best = 10000;
                for (int i = 0; i < N; i++)
                {
                        for (int j = 0; j < M; j++)
                        {
                                if (b[i][j] == 0)
                                {
                                        b[i][j] = minimizer;
                                        int value = minimax(b, depth + 1, !isMax, maximizer, minimizer);
                                        if (value < best)
                                        {
                                                best = value;
                                        }
                                        if (best == -100)
                                        {
                                                return best;
                                        }
                                        b[i][j] = 0;
                                }
                        }
                }
                return best;
        }
}
void findResult(int**& board, bool isMax, int maximizer, int minimizer) {
        int result = minimax(board, 0, isMax, maximizer, minimizer);
        if (result == 0)
        {
                printf("BOTH_PLAYERS_TIE\n");
                return;
        }
        else if (result > 0)
        {
                if (maximizer == 1)
                {
                        printf("FIRST_PLAYER_WINS\n");
                        return;
                }
                else
                {
                        printf("SECOND_PLAYER_WINS\n");
                        return;
                }
        }
        else if (result < 0)
        {
                if (minimizer == 1)
                {
                        printf("FIRST_PLAYER_WINS\n");
                        return;
                }
                else
                {
                        printf("SECOND_PLAYER_WINS\n");
                        return;
                }
        }
}