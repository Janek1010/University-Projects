using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Lab4__VS
{
    internal static class Program
    {
        private static List<Car> myCars = new List<Car>(){
        new Car("E250", new Engine(1.8, 204, "CGI"), 2009),
        new Car("E350", new Engine(3.5, 292, "CGI"), 2009),
        new Car("A6", new Engine(2.5, 187, "FSI"), 2012),
        new Car("A6", new Engine(2.8, 220, "FSI"), 2012),
        new Car("A6", new Engine(3.0, 295, "TFSI"), 2012),
        new Car("A6", new Engine(2.0, 175, "TDI"), 2011),
        new Car("A6", new Engine(3.0, 309, "TDI"), 2011),
        new Car("S6", new Engine(4.0, 414, "TFSI"), 2012),
        new Car("S8", new Engine(4.0, 513, "TFSI"), 2012)
    };
        [STAThread]
        public static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new Form1());

            Linq();
        }
        public static void Linq()
        {


            var methodBasedSyntaxQuery = myCars.Where(car => car.Model == "A6")
                .Select(car => new
                {
                    engineType = car.Engine.Model == "TDI" ? "diesel" : "petrol",
                    hppl = car.Engine.HorsePower / car.Engine.Displacement
                }).ToList();

            var sortedMethodBasedSyntaxQuery = methodBasedSyntaxQuery.OrderBy(car => car.engineType).ToList();


            foreach (var e in sortedMethodBasedSyntaxQuery) Console.WriteLine(e.engineType + ": " + e.hppl);


        }
    }
}
