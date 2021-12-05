#ifndef LINE_H
#define LINE_H

#include <vector>
#include "point.hpp"

using namespace std;

class Line
{
    vector<Point> coords;

public:
    bool diagonal;
    int x1, y1, x2, y2;
    Line(int x1, int y1, int x2, int y2);
    void TraceLine(vector<vector<int> > *board);
    void PrintCoords() {
        cout << "From " << *new Point(x1, y1) << " To " << *new Point(x2, y2) << endl;

        for (int i = 0; i < coords.size(); i++) {
            cout << coords[i] << " || ";
        }

        cout << endl;
    }
};

#endif
