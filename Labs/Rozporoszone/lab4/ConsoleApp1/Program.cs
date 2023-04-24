using System;
using System.Diagnostics;
using System.Threading;

class Program
{
    static void Main(string[] args)
    {
        if (args.Length == 0)
        {
            string path = Process.GetCurrentProcess().MainModule.FileName;
            ProcessStartInfo process = new ProcessStartInfo();
            Process processStarting = new Process();
            process.FileName = path;
            process.Arguments = "processInitiate";
            process.UseShellExecute = true;
            Process process1 = Process.Start(process);
            Process process2 = Process.Start(process);

            int i = 1;
            while (true)
            {
                Console.WriteLine(i++);
                Thread.Sleep(100);
            }
        }
        else
        {
            int i = 0;
            while (true)
            {
                Console.WriteLine(i++);
                Thread.Sleep(100);
            }
        }

    }
}
