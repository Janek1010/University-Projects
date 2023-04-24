using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Xml.Linq;
using System.Xml.Serialization;
using System.Xml.XPath;

namespace ConsoleApp1
{
    public class Program
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
        static void Main(string[] args)
        {
            // zad 1
            var results = myCars.Where(car => car.Model == "A6")
                   .Select(car => new
                   {
                       engineType = car.Engine.Model == "TDI" ? "diesel" : "petrol",
                       hppl = car.Engine.HorsePower / car.Engine.Displacement
                   }).ToList();

            var sortedResults = results.OrderBy(car => car.engineType).ToList();

            // zad 2
            var filePath = Path.Combine(Directory.GetCurrentDirectory(), "CarsCollection.xml");
            
            SerializeXML(filePath);
            var deserialized = DeserializeXML(filePath);

            // zad 3
            Console.WriteLine("Srednia wartosc silnikow nie TDI: "+ AveragePower());
            List<string> modelsUnique = GetModelsUnique();
            foreach (var item in modelsUnique)
            {
                Console.WriteLine(item);
            }

            // zad 4
            Console.WriteLine(createXmlFromLinq());

            // zad 5 
            MakeTable();

            // zad 6
            XMLmodification();

        }
        private static void XMLmodification()
        {
            var xml = XElement.Load("CarsCollection.xml");

            foreach (var car in xml.Elements())
            {
                foreach (var field in car.Elements())
                {
                    if (field.Name == "Engine")
                    {
                        foreach (var item in field.Elements())
                        {
                            if (item.Name == "HorsePower")
                            {
                                item.Name = "hp";
                            }
                        }
                    }
                    else if (field.Name == "Model")
                    {

                        var yr = car.Element("Year");
                        var atr = new XAttribute("year", yr.Value);
                        field.Add(atr);
                        yr.Remove();
                    }
                }
            }

            xml.Save("CarsCollection.xml");
        }
        private static void MakeTable()
        {
            var carsToAdd = GetRows();
            var table = new XElement("table", new XAttribute("style", "border: 2px double black"), carsToAdd);
            var template = XElement.Load("template.html");
            var body = template.Element("{http://www.w3.org/1999/xhtml}body");
            body.Add(table);
            template.Save("templateWithTable.html");
            
        }
        private static IEnumerable<XElement> GetRows()
        {
            return myCars.Select(car =>
                new XElement("tr", new XAttribute("style", "border: 2px solid black"),
                    new XElement("td", new XAttribute("style", "border: 2px double black"), car.Model),
                    new XElement("td", new XAttribute("style", "border: 2px double black"), car.Engine.Model),
                    new XElement("td", new XAttribute("style", "border: 2px double black"), car.Engine.Displacement),
                    new XElement("td", new XAttribute("style", "border: 2px double black"), car.Engine.HorsePower),
                    new XElement("td", new XAttribute("style", "border: 2px double black"), car.Year)));
        }
        private static XElement createXmlFromLinq()
        {
            var nodes = myCars
                .Select(car =>
                    new XElement("car",
                        new XElement("model", car.Model),
                        new XElement("engine",
                            new XAttribute("model", car.Engine.Model),
                            new XElement("displacement", car.Engine.Displacement),
                            new XElement("horsePower", car.Engine.HorsePower)),
                        new XElement("year", car.Year)));

            var rootNode = new XElement("cars", nodes);
            rootNode.Save("CarsCollectionLinq.xml");
            return rootNode;
        }

        public static List<string> GetModelsUnique()
        {
            XElement rootNode = XElement.Load("CarsCollection.xml");
            IEnumerable<XElement> models = rootNode.XPathSelectElements("//car/Model");
            List<string> lst = models.Select(model => (string)model).Distinct().ToList();
            return lst;

        }
        public static void SerializeXML(string filename)
        {
            var ser = new XmlSerializer(typeof(List<Car>),
                new XmlRootAttribute("cars"));
            var writer = new StreamWriter(filename);
            ser.Serialize(writer, myCars);
            writer.Close();
            
        }
        public static List<Car> DeserializeXML(string filePath)
        {
            var lst = new List<Car>();
            var ser = new XmlSerializer(typeof(List<Car>),
                new XmlRootAttribute("cars"));
            var rder = new FileStream(filePath, FileMode.Open);
            lst = (List<Car>)ser.Deserialize(rder);
            rder.Close();
            return lst;
        }
        public static double AveragePower()
        {
            var rootNode = XElement.Load("CarsCollection.xml");
            var countAvarageXPath = "sum(//car[not(Engine/Model='TDI')][Engine]/Engine/HorsePower) div count(//car[not(Engine/Model='TDI')][Engine]/Engine/HorsePower)";
            return (double)rootNode.XPathEvaluate(countAvarageXPath);
        }
    }
}
