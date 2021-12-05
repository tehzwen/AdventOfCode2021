package com.test;

import java.io.*;
import java.util.*;

class Position {
    public String value;
    public boolean called;

    public String toString() { 
        return "Value: '" + this.value + "', Called: '" + this.called + "'";
    } 

    public Position(String value) {
        this.value = value;
        this.called = false;
    }
}


class Board {
    public String name;
    public boolean won;

    private ArrayList<List<Position>> _board;
    private int _rowSize;

    public int Count() { 
        return _board.size();
    }

    public Board(int size) {
        _board = new ArrayList();
        _rowSize = size;
        won = false;
    }

    public void unCall() {
        for (int i = 0; i < _rowSize; i++) {
            for (int j = 0; j < _rowSize; j++) {
                _board.get(i).get(j).called = false; 
            }
        }
    }

    public String toString() {
        String total = "Board: " + this.name + ":\n";

        for (int i = 0; i < _rowSize; i++) {
            total += _board.get(i).toString(); 
            total += "\n";   
        }

        return total;
    }

    public int unmarkedSum() {
        int total = 0;

        for (int i = 0; i < _rowSize; i++) {
            boolean rowWins = true;

            for (int j = 0; j < _rowSize; j++) {
                if (!this._board.get(i).get(j).called) {
                    total += Integer.parseInt(this._board.get(i).get(j).value);
                }
            }
        }

        return total;
    }

    public void checkNumber(String number) {
        for (int i = 0; i < _rowSize; i++) {
            for (int j = 0; j < this._board.get(i).size(); j++) {
                if (this._board.get(i).get(j).value.compareTo(number) == 0) {
                    this._board.get(i).get(j).called = true;
                }
            }
        }
    }

    public boolean isWinner() {
        // check rows
        for (int i = 0; i < _rowSize; i++) {
            boolean rowWins = true;

            for (int j = 0; j < _rowSize; j++) {
                if (!this._board.get(i).get(j).called) {
                    rowWins = false;
                }
            }

            if (rowWins) {
                return rowWins;
            }
        }

        // check columns
        for (int i = 0; i < this._rowSize; i++) {
            boolean columnWin = true;
            for (int j = 0; j < this._rowSize; j++) {
                if (!this._board.get(j).get(i).called) {
                    columnWin = false;
                }
            }

            if (columnWin) {
                return columnWin;
            }
        }

        return false;
    }

    public void addRow(String row) {
        List temp = new ArrayList();
        String tempString = "";

        for (int i = 0; i < row.length(); i++) {
            if (row.charAt(i) == ' ') {
                if (tempString.compareTo("") != 0) {
                    Position tempPosition = new Position(tempString);
                    temp.add(tempPosition);
                }

                tempString = "";
            }

            if (row.charAt(i) != ' ') {
                tempString += row.charAt(i);
            }
        }

        Position tempPosition = new Position(tempString);
        temp.add(tempPosition);
        this._board.add(temp);
    }
}


class Program {
    public static void main(String[] args) {
        List<String> calledList = new ArrayList();
        List<Board> boards = new ArrayList();

        try {
            // Open the file
            FileInputStream fstream = new FileInputStream("/usr/app/input");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

            String strLine;
            int count = 0;
            Board tempBoard = new Board(5);

            while ((strLine = br.readLine()) != null) {
                if (count == 0) {
                    calledList = Arrays.asList(strLine.split(","));
                } else {
                    if (strLine.compareTo("") != 0) {
                        tempBoard.addRow(strLine);
                    }
                }

                if (tempBoard.Count() == 5) {
                    tempBoard.name = Integer.toString(count);
                    boards.add(tempBoard);
                    tempBoard = new Board(5);
                }

                count++;
            }

            fstream.close();

            int found = -1;
            int winningCall = 0;

            for (int i = 0; i < calledList.size(); i++) {
                for (int j = 0; j < boards.size(); j++) {
                    boards.get(j).checkNumber(calledList.get(i));
                    if (boards.get(j).isWinner()) {
                        winningCall = Integer.parseInt(calledList.get(i));
                        found = j;
                        break;
                    }
                }

                if (found != -1) {
                    break;
                }
            }

            if (found != -1) {
                System.out.println(boards.get(found));
                System.out.println(boards.get(found).unmarkedSum());
                System.out.println(winningCall);
                System.out.println("Part 1: " + winningCall * boards.get(found).unmarkedSum());
            }

            // reset for part 2
            for (int i = 0; i < boards.size(); i++) {
                boards.get(i).unCall();
            }

            List<String> wonBoards = new ArrayList();

            
            for (int i = 0; i < calledList.size(); i++) {
                for (int j = 0; j < boards.size(); j++) {
                    if (!boards.get(j).won) {
                        boards.get(j).checkNumber(calledList.get(i));
                        if (boards.get(j).isWinner()) {
                            boards.get(j).won = true;
    
                            boolean nameInWon = false;
                            // // check if this board name is already in the list
                            for (int k = 0; k < wonBoards.size(); k++) {
                                if (wonBoards.get(k).compareTo(boards.get(j).name) == 0) {
                                    nameInWon = true;
                                }
                            }
    
                            if (!nameInWon) {
                                wonBoards.add(boards.get(j).name);
                                winningCall = Integer.parseInt(calledList.get(i));
                            }
                        }
                    }
                }
            }

            System.out.println(wonBoards);

            for (int j = 0; j < boards.size(); j++) {
                if (boards.get(j).name.compareTo(wonBoards.get(wonBoards.size() - 1)) == 0) {
                    System.out.println(boards.get(j));
                    System.out.println(boards.get(j).unmarkedSum());
                    System.out.println(winningCall);
                    System.out.println("Part 2: " + winningCall * boards.get(j).unmarkedSum());
                    break;
                }
            }


        } catch(Exception e) {
            System.out.println(e.toString());
        }
    }
}
