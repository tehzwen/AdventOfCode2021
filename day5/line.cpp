#include <iostream>
#include "line.hpp"

using namespace std;


Line::Line(int xCoord1, int yCoord1, int xCoord2, int yCoord2)
{
    x1 = xCoord1;
    x2 = xCoord2;
    y1 = yCoord1;
    y2 = yCoord2;

    Point *currentPoint = new Point(x1, y1);
    coords.push_back(*new Point(x1, y1));

    diagonal = x1 != x2 && y1 != y2;

    while (currentPoint->x != x2 || currentPoint->y != y2) {
        if (currentPoint->x > x2) {
            currentPoint->x--;
        } else if (currentPoint->x < x2) {
            currentPoint->x++;
        }

        if (currentPoint->y > y2) {
            currentPoint->y--;
        } else if (currentPoint->y < y2) {
            currentPoint->y++;
        }

        coords.push_back(*currentPoint);
    }
}

void Line::TraceLine(vector<vector<int> > *board) {
    // cout << "tracing" << endl;

    for (int i = 0; i < coords.size(); i++) {
        int x = coords[i].x;
        int y = coords[i].y;

        // cout << x << ", " << y << endl;
        (*board)[x][y]++;
    }
}
