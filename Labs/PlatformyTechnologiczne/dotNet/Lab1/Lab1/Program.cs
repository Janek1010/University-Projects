using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.IO;
using System.Runtime.Serialization.Formatters.Binary;

namespace Lab1
{
    [Serializable]
    public class Porownanie : IComparer<string>
    {
        public int Compare(string x, string y)
        {
            if (x.Length > y.Length)
            {
                return 1;
            }
            if (x.Length < y.Length)
            {
                return -1;
            }
            return x.CompareTo(y);
        }
    }
    static class Program
    {
        public static SortedDictionary<string, long> elements = new SortedDictionary<string, long>(new Porownanie());
        static void Main(string[] args)
        {
            DirectoryInfo root = new DirectoryInfo(args[0]); 
          
            Console.WriteLine("Najstarsza data plików: " + root.FindOldest());
            Console.WriteLine(Rekurencja(root, 0));

            Serializacja();

            foreach (KeyValuePair<string, long> entry in Deserializacja())
            {
                Console.WriteLine(entry.Key + " -> " + entry.Key + "\n");
            }
        }

        public static void Serializacja()
        {
            BinaryFormatter formatter = new BinaryFormatter();

            FileStream stream = new FileStream("elements.bin", FileMode.Create);
            formatter.Serialize(stream, elements);
            stream.Close();

        }
        public static SortedDictionary<string, long> Deserializacja()
        {
            BinaryFormatter formatter = new BinaryFormatter();
            FileStream stream = new FileStream("elements.bin", FileMode.Open);

            SortedDictionary<string, long> toreturn = new SortedDictionary<string, long>(new Porownanie());
            toreturn = (SortedDictionary<string,long>)formatter.Deserialize(stream);
            return toreturn;

        }
        public static void AddToCollectionFile(FileInfo file)
        {

            elements.Add(file.Name, file.Length);
            return;
        }
        public static void AddToCollectionDirectory(DirectoryInfo dir)
        {

            elements.Add(dir.Name, dir.GetFiles().Length + dir.GetDirectories().Length);
            return;
        }

        public static string Rekurencja(DirectoryInfo d, int level)
        {
            DirectoryInfo[] Files = d.GetDirectories();
            string str = "";
            for (int i = 0; i < level; i++)
            {
                str = str + "   ";
            }
            str = str + d.Name + " (" + (Files.Length + d.GetFiles().Length) + ") " + d.FindingRahs() + "\n";
            AddToCollectionDirectory(d);

            foreach (DirectoryInfo dir in Files)
            {
                str = str + Rekurencja(dir, level + 1);
            }
            str = str + WypiszPliki(d, level + 1); // + 1 bo pliki maja jakby byc nizej
            return str;
        }
        public static string WypiszPliki(DirectoryInfo d, int level)
        {
            FileInfo[] Files = d.GetFiles(); //Getting Text files
            string str = "";

            foreach (FileInfo file in Files)
            {
                for (int i = 0; i < level; i++)
                {
                    str = str + "   ";
                }
                str = str + GetExtraInformationsForFiles(file);
                if (elements.ContainsKey(file.Name))
                {
                    continue;
                }
                AddToCollectionFile(file);
            }
            return str;
        }
        public static DateTime FindOldest(this DirectoryInfo d)
        {
            DateTime oldest = d.CreationTime;

            DirectoryInfo[] Directories = d.GetDirectories();

            foreach (DirectoryInfo dir in Directories)
            {
                if (dir.CreationTime < oldest)
                {
                    oldest = dir.CreationTime;
                }
                DateTime returned = FindOldest(dir); // po sprawdzeniu daty folderu reku szukamy nizejw  tym folderze
                if (returned < oldest)
                {
                    oldest = returned;
                }
            }
            FileInfo[] Files = d.GetFiles(); //Getting Text files

            foreach (FileInfo file in Files)
            {
                if (file.CreationTime < oldest)
                {
                    oldest = file.CreationTime;
                }
            }
            return oldest;

        }
        public static string GetExtraInformationsForFiles(FileInfo d)
        {
            string str = "" + d.Name + " " + d.Length + " bajtow " + d.FindingRahs() + "\n";
            return str;
        }
        public static string FindingRahs(this FileSystemInfo flINFO)
        {
            string rahs = "";
            FileAttributes attributes = flINFO.Attributes;
            if ((attributes & FileAttributes.ReadOnly) == FileAttributes.ReadOnly)
            {
                rahs = rahs + 'r';
            }
            else
            {
                rahs = rahs + '-';
            }
            if ((attributes & FileAttributes.Archive) == FileAttributes.Archive)
            {
                rahs += 'a';
            }
            else
            {
                rahs += '-';
            }

            if ((attributes & FileAttributes.Hidden) == FileAttributes.Hidden)
            {
                rahs += 'h';
            }
            else
            {
                rahs += '-';
            }

            if ((attributes & FileAttributes.System) == FileAttributes.System)
            {
                rahs += 's';
            }
            else
            {
                rahs += '-';
            }

            return rahs;
        }
    }
}
