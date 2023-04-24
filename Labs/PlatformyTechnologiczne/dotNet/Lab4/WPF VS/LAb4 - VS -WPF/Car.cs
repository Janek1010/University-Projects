using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml.Serialization;

namespace LAb4___VS__WPF
{
    [XmlType("car")]
    public class Car
    {
        private string model;
        private int year;

        [XmlElement(ElementName = "engine")]
        private Engine motor;

        public Car() { }
        public Car(string model, Engine engine, int year)
        {
            this.model = model;
            this.year = year;
            this.motor = engine;
        }
        public string Model
        {
            get { return model; }
            set { model = value; }
        }

        public int Year
        {
            get { return year; }
            set { year = value; }
        }

        public Engine Engine
        {
            get { return motor; }
            set { motor = value; }
        }
    }
}
