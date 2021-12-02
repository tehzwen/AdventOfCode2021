package main

import (
	"bufio"
	"log"
	"os"
	"strconv"
	"strings"
)

func main() {
	file, err := os.Open("input")
	if err != nil {
		log.Fatal(err)
	}
	defer file.Close()

	type Coords struct {
		x   int
		y   int
		aim int
	}

	c := Coords{
		0,
		0,
		0,
	}
	scanner := bufio.NewScanner(file)

	for scanner.Scan() {
		line := scanner.Text()
		amount := strings.Split(line, " ")[1]
		amountInt, err := strconv.Atoi(amount)

		if err != nil {
			log.Fatal(err)
		}

		if line[0] == 'f' {
			c.x += amountInt
			c.y += amountInt * c.aim
		} else if line[0] == 'd' {
			c.aim += amountInt
		} else if line[0] == 'u' {
			c.aim -= amountInt
		}
	}
	println(c.x * c.y)

	if err := scanner.Err(); err != nil {
		log.Fatal(err)
	}
}
