#ifndef POINT_H
#define POINT_H

#include <vector>

using namespace std;

class Point
{
public:
    friend ostream& operator<<(ostream& os, Point const& data) {
        return os << "x: " << data.x << ", y: " << data.y;
    }
    int x, y;
    Point(int, int);
};

#endif
