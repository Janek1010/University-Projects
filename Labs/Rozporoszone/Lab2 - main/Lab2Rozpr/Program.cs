using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Threading;
using Rozporszone2;

namespace RozproszoneLab1
{
    public class Program
    {
        static void Main(string[] args)
        {
            MyList<int> list = new MyList<int>();
            int elementAmount = 100;
            int[] A1 = new int[elementAmount];
            int[] A2 = new int[elementAmount];

            Random random = new Random();
            for (int i = 0; i < elementAmount; i++)
            {
                A1[i] = random.Next(90);
                A2[i] = random.Next(90);
            }

            Console.WriteLine(string.Join(" ", A1));
            Console.WriteLine(string.Join(" ", A2));

            Thread thread1 = new Thread(() =>
            {
                Thread.CurrentThread.Name = "Thread 1";
                for (int i = 0; i < elementAmount; i++)
                {
                    list.Add(A1[i]);
                }
            });

            Thread thread2 = new Thread(() =>
            {
                Thread.CurrentThread.Name = "Thread 2";
                for (int i = 0; i < elementAmount; i++)
                {
                    list.Add(A2[i]);
                }
            });

            thread1.Start();
            thread2.Start();

            thread1.Join();
            thread2.Join();

            list.Sort();
            Console.WriteLine(string.Join(" ", list.Select(x => x.ToString())));

        }

    }
}