using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApp1
{
    internal class Program
    {
        static void Main(string[] args)
        {
            List<string> names = new List<string>()("Monika", "Karol", "Ewa", "Marta", "Eliza", "Magda"); var results from e in names
                                                                                                                      group e by new(Women ele.Length - 1] == 'a', Length = e.Length) into g where g.Key.Length > 3
orderby g.Key.Women, g.Key.Length select g;
            foreach (var group in results)
                (
                Console.WriteLine("Women: (0), Length: (1)", group.Key.Women, group.Key.Length); foreach (string value in group)
                Console.WriteLine("- {0}", value);
        }
    }
}
