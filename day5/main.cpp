#include <iostream>
#include <fstream>
#include <string>
#include <vector>
#include <cstring>

#include "line.hpp"

using namespace std;

int getLargest(int a, int b, int c, int d) {
    if (a > b && a > c && a > d) {
        return a;
    } else if (b > a && b > c && b > d) {
        return b;
    } else if (c > a && c > b && c > d) {
        return c;
    }
    return d;
}

int main(int argc, char **argv)
{
    ifstream file("input");
    string str;
    vector<Line *> lines;
    vector<int> values;

    int biggest = 0;

    while (getline(file, str))
    {
        char buffer[100];
        char *ptr;

        strcpy(buffer, str.c_str());
        ptr = strtok(buffer, " -> ");

        char firstHalf[100], secondHalf[100];
        strcpy(firstHalf, ptr);
        ptr = strtok(NULL, " -> ");
        strcpy(secondHalf, ptr);

        char x1[100], y1[100], x2[100], y2[100];

        char *commaPtr;
        commaPtr = strtok(firstHalf, ",");
        strcpy(x1, commaPtr);
        commaPtr = strtok(NULL, ",");
        strcpy(y1, commaPtr);

        commaPtr = strtok(secondHalf, ",");
        strcpy(x2, commaPtr);
        commaPtr = strtok(NULL, ",");
        strcpy(y2, commaPtr);

        int x1Num = atoi(x1);
        int x2Num = atoi(x2);
        int y1Num = atoi(y1);
        int y2Num = atoi(y2);
        int tempBiggest = getLargest(x1Num, y1Num, x2Num, y2Num);

        if (tempBiggest > biggest) {
            biggest = tempBiggest;
        }

        Line *tempLine = new Line(x1Num, y1Num, x2Num, y2Num);
        lines.push_back(tempLine);
    }

    vector<vector<int> > board;

    for (int i = 0; i < biggest + 1; i++)
    {
        vector<int> tempVec;

        for (int j = 0; j < biggest + 1; j++)
        {
            tempVec.push_back(0);
        }
        board.push_back(tempVec);
    }

    // print the board
    // for (int i = 0; i < board.size(); i++) {
    //     for (int j = 0; j < board.size(); j++) {
    //         cout << board[j][i];
    //     }
    //     cout << endl;
    // }

    for (int i = 0; i < lines.size(); i++) {
        if (!lines[i]->diagonal) {
            lines[i]->TraceLine(&board);
        }
    }

    cout << "*******************" << endl;

    int count = 0;

    for (int i = 0; i < board.size(); i++) {
        for (int j = 0; j < board.size(); j++) {
            if (board[j][i] >= 2) {
                count++;
            }
        }
    }

    cout << "Part 1: " << count << endl;

    // zero out the board
    for (int i = 0; i < board.size(); i++) {
        for (int j = 0; j < board.size(); j++) {
            board[j][i] = 0;
        }
    }

    for (int i = 0; i < lines.size(); i++) {
        lines[i]->TraceLine(&board);
    }

    count = 0;

    for (int i = 0; i < board.size(); i++) {
        for (int j = 0; j < board.size(); j++) {
            if (board[j][i] >= 2) {
                count++;
            }
        }
    }

    cout << "Part 2: " << count << endl;
}
