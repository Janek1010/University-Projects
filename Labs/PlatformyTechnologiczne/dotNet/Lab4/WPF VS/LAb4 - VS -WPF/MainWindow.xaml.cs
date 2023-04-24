﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace LAb4___VS__WPF
{
    public partial class MainWindow : Window
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
        public MainWindow()
        {
            InitializeComponent();
            Linq();
        }
        public void Linq()
        {


            var methodBasedSyntaxQuery = myCars.Where(car => car.Model == "A6")
                .Select(car => new
                {
                    engineType = car.Engine.Model == "TDI" ? "diesel" : "petrol",
                    hppl = car.Engine.HorsePower / car.Engine.Displacement
                }).OrderBy(car => car.hppl).Reverse().ToList();



            foreach (var e in methodBasedSyntaxQuery)
            {
                txtBox.Text += e.engineType + ": " + e.hppl + "\n";
            }



        }
    }

}