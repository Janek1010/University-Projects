using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Shapes;

namespace Lab2
{
    public partial class dial : Window
    {
        private string path;
        private bool isDone;
        public dial(string p)
        {
            InitializeComponent();
            this.path = p;
            isDone = false;
        }

        private void OK_Click(object sender, RoutedEventArgs e)
        {
            
            bool isFile = (bool)radioFile.IsChecked;
            bool isDirectory = (bool)radioDirectory.IsChecked;
            // walidacje na nazwe
            if (isFile && !Regex.IsMatch(TextFieldDialog.Text, "^[a-zA-Z0-9_~-]{1,8}\\.(txt|php|html)$"))
            {
                System.Windows.MessageBox.Show("Zla nazwa!", "Uwaga!!!!!", MessageBoxButton.OK, MessageBoxImage.Information);
                return;
            }
            if (isFile == false && isDirectory == false)
            {
                System.Windows.MessageBox.Show("Nie wybrano typu pliku!", "Uwaga!!!!!", MessageBoxButton.OK, MessageBoxImage.Information);
                return;
            }
            var name = TextFieldDialog.Text;
            path = path + "\\" + name;

            FileAttributes attributes = FileAttributes.Normal;

            if ((bool)CB_ReadOnly.IsChecked)
            {
                attributes |= FileAttributes.ReadOnly;
            }
            if ((bool)CB_Archive.IsChecked)
            {
                attributes |= FileAttributes.Archive;
            }
            if ((bool)CB_Hidden.IsChecked)
            {
                attributes |= FileAttributes.Hidden;
            }
            if ((bool)CB_System.IsChecked)
            {
                attributes |= FileAttributes.System;
            }
            if (isFile)
            {
                File.Create(path);
            }
            if (isDirectory)
            {
                Directory.CreateDirectory(path);
            }
            File.SetAttributes(path, attributes);
            isDone = true;
            Close();
        }
        public bool isDoneCreating()
        {
            return isDone;
        }
        public string getPath()
        {
            return path;
        }
        public void CancelDial(object sender, RoutedEventArgs e)
        {
            Close();
        }
    }
}
