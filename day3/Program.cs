using System;

class DiagnosticReport
{
    private string _gamma;
    private string _epsilon;

    public string Epsilon
    {
        get { return _epsilon; }
        set { _epsilon = value; }
    }
    public string Gamma
    {
        get { return _gamma; }
        set { _gamma = value; }
    }

    public DiagnosticReport(string gamma, string epsilon)
    {
        this._gamma = gamma;
        this._epsilon = epsilon;
    }
}

// create a list, store key-value pair in each position representing commonness [{1: 56, 0: 10}, ....] then can determine binary number
class Program
{
    public const int LENGTH = 12;

    public static int GetBinaryValue(string value)
    {
        int total = 0;

        for (int i = value.Length - 1; i >= 0; i--)
        {
            if (value[i] == '1')
            {
                total += Convert.ToInt32(Math.Pow(2, (value.Length - 1) - i));
            }

        }
        return total;
    }

    private static void _printList(List<Dictionary<int, int>> list, int len)
    {
        for (int i = 0; i < len; i++)
        {
            Console.Write("0: ");
            Console.Write(list[i][0]);
            Console.Write(", 1: ");
            Console.Write(list[i][1]);

            if (list[i][0] > list[i][1])
            {
                Console.Write(" Winner: 0");
            }
            else
            {
                Console.Write(" Winner: 1");
            }

            Console.WriteLine();
        }
    }

    private static DiagnosticReport _getReport(List<Dictionary<int, int>> list)
    {
        string mostCommon = "";
        string leastCommon = "";

        for (int i = 0; i < LENGTH; i++)
        {
            if (list[i][0] > list[i][1])
            {
                mostCommon += "0";
                leastCommon += "1";
            }
            else
            {
                mostCommon += "1";
                leastCommon += "0";
            }
        }
        return new DiagnosticReport(mostCommon, leastCommon);
    }

    public static void Main()
    {
        List<Dictionary<int, int>> values = new List<Dictionary<int, int>>();

        // populate our list
        for (int i = 0; i < LENGTH; i++)
        {
            Dictionary<int, int> temp = new Dictionary<int, int>();
            temp[0] = 0;
            temp[1] = 0;
            values.Add(temp);
        }

        List<string> numberList = new List<string>();

        foreach (string line in System.IO.File.ReadLines("input"))
        {
            for (int i = 0; i < line.Length; i++)
            {
                int bitValue = line[i] - '0';
                values[i][bitValue]++;
            }
            numberList.Add(line);
        }

        DiagnosticReport report = _getReport(values);
        Console.WriteLine("Part 1: " + GetBinaryValue(report.Gamma) * GetBinaryValue(report.Epsilon));


        List<string> gamList = new List<string>();
        List<string> epsList = new List<string>();

        for (int i = 0; i < numberList.Count; i++)
        {
            if (report.Gamma[0] == numberList[i][0])
            {
                gamList.Add(numberList[i]);
            }
            else
            {
                epsList.Add(numberList[i]);
            }
        }

        int oxygenValue = -1;
        int co2Value = -1;

        int count = 1;


        while (oxygenValue == -1)
        {
            List<Dictionary<int, int>> oxygenValues = new List<Dictionary<int, int>>();
            for (int j = 0; j < gamList.Count; j++)
            {
                for (int i = 0; i < LENGTH; i++)
                {
                    int bitValue = gamList[j][i] - '0';
                    Dictionary<int, int> tempDict = new Dictionary<int, int>();
                    tempDict[0] = 0;
                    tempDict[1] = 0;
                    oxygenValues.Add(tempDict);
                    oxygenValues[i][bitValue]++;
                }
            }

            DiagnosticReport tempReport = _getReport(oxygenValues);

            for (int j = 0; j < gamList.Count; j++)
            {
                if (gamList.Count == 1)
                {
                    oxygenValue = GetBinaryValue(gamList[0]);
                    break;
                }

                if (gamList[j][count] != tempReport.Gamma[count] && gamList.Count > 1)
                {
                    gamList.RemoveAt(j);
                    j--;
                }
            }
            count++;
        }

        count = 1;

        while (co2Value == -1)
        {
            List<Dictionary<int, int>> co2Values = new List<Dictionary<int, int>>();
            for (int j = 0; j < epsList.Count; j++)
            {
                for (int i = 0; i < LENGTH; i++)
                {
                    int bitValue = epsList[j][i] - '0';
                    Dictionary<int, int> tempDict = new Dictionary<int, int>();
                    tempDict[0] = 0;
                    tempDict[1] = 0;
                    co2Values.Add(tempDict);
                    co2Values[i][bitValue]++;
                }
            }

            DiagnosticReport tempReport = _getReport(co2Values);

            for (int j = 0; j < epsList.Count; j++)
            {
                if (epsList.Count == 1)
                {
                    co2Value = GetBinaryValue(epsList[0]);
                    break;
                }

                if (epsList[j][count] != tempReport.Epsilon[count] && epsList.Count > 1)
                {
                    epsList.RemoveAt(j);
                    j--;
                }
            }
            count++;
        }

        Console.WriteLine("Part 2: " + oxygenValue * co2Value);
    }
}
