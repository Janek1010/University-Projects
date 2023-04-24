using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ConsoleApp1
{
    internal class Program
    {
        const int N = 7;
        const int K = 3;

        public delegate int meterDele();
        public delegate int denominatorDele();

        static void Main(string[] args)
        {
            Console.WriteLine("Results for N = {0} and K = {1} is:\n \n", N, K);

            // 1A
            var meter = Task.Run(() => meterCalculation());
            var denominator = Task.Run(() => denominatorCalculation());

            Task.WaitAll(meter, denominator);
            Console.WriteLine("TASKS RESULT***:  {0}  \n\n", denominator.Result / meter.Result);

            // 1B
            var delMeter = new meterDele(meterCalculation);
            var denominatorDele = new denominatorDele(denominatorCalculation);

            Console.WriteLine("DELEGATES RESULT***:  {0}  \n\n", denominatorDele.Invoke() / delMeter.Invoke());
            // 1C
            Asynchronus();
            

        }

        static int meterCalculation()
        {
            int meterValue = 1;
            for (int i = 1; i <= K; i++)
            {
                Console.WriteLine("**** METER ****\n");
                meterValue *= i;
            }

            return meterValue;
        }
        static int denominatorCalculation()
        {
            int denominator = 1;
            for (int i = N - K + 1; i <= N; i++)
            {

                Console.WriteLine("**** DENOMINATOR ****\n");
                denominator *= i;
            }
            return denominator;

        }

        private static async void Asynchronus()
        {
            var deno = Task.Run(denominatorCalculation);
            var mete = Task.Run(meterCalculation);

            await Task.WhenAll(deno, mete);

            Console.WriteLine("ASYNC RESULT***:  {0}  \n\n", deno.Result / mete.Result);
        }
    }
}
