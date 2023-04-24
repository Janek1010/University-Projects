using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml.Serialization;

namespace ConsoleApp1
{
    [XmlRoot(ElementName = "engine")]
    public class Engine
    {
        private double displacement;
        private double horsePower;

        [XmlAttribute]
        private string model;
        public Engine() { }
        public Engine(double displacement, double horsePower, string model)
        {
            this.displacement = displacement;
            this.horsePower = horsePower;
            this.model = model;
        }
        public double Displacement
        {
            get { return displacement; }
            set { displacement = value; }
        }

        public double HorsePower
        {
            get { return horsePower; }
            set { horsePower = value; }
        }

        public string Model
        {
            get { return model; }
            set { model = value; }
        }

    }
}
