def main():
    file = open('./input', 'r')
    contents = file.readlines()

    total = 0
    values = []

    for i in range(len(contents) - 2):
        temp_total = 0
        for j in range(3):
            if (j + i < len(contents)):
                temp_total += int(contents[i + j].strip("\n"))
        print(values, temp_total)
        values.append(temp_total)

    
    for i in range(len(values)):
        if (i > 0):
            value = values[i]
            past = values[i - 1]

            if (value == past):
                print(str(values[i]) + "(no change)")
            elif (value > past):
                print(str(values[i]) + "(increased)")
                total += 1
            else:
                print(str(values[i]) + "(decreased)")
        else:
            print(str(values[i]) + "(N/A - no previous measurement)")


    print(total)

if __name__ == "__main__":
    main()
