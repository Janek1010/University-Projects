using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Rozporszone2
{
    public class MyList<T> : List<int>
    {
        public void Add(int element)
        {
            lock (this)
            {
                if (this.Contains(element))
                {
                    Console.WriteLine(Thread.CurrentThread.Name + " Element jest juz w liscie ");
                }
                else
                {
                    Console.WriteLine(Thread.CurrentThread.Name + " Dodawanie do listy: " + element);
                    base.Add(element);
                }
            }
        }
    }
}
