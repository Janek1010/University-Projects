using System;
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
using System.Windows.Forms;
using System.IO;

namespace Lab2
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();
        }
        private void OpenFile(object sender, RoutedEventArgs e)
        {
            var dlg = new FolderBrowserDialog() { Description = "Select directory to open" };
            dlg.ShowDialog();
            var path = dlg.SelectedPath;
            
            if (path == null)
            {
                return;
            }

            GetTree(MyTree, path);
        }
        private  void GetTree(System.Windows.Controls.TreeView tree, string path)
        {
            tree.Items.Clear();
            DirectoryInfo d = new DirectoryInfo(path);
            tree.Items.Add(MakeTree(d));

        }
        private  TreeViewItem MakeTree(DirectoryInfo d)
        {
            DirectoryInfo[] directiories = d.GetDirectories();
            var root = new TreeViewItem
            {
                Header = d.Name,
                Tag = d.FullName,
                ContextMenu = (ContextMenu)this.FindResource("directMenu")
            }; // create/delete
            root.Selected += new RoutedEventHandler(UpdateBar);

            foreach (DirectoryInfo dir in directiories)
            {
                root.Items.Add(MakeTree(dir));
            }

            FileInfo[] Files = d.GetFiles(); //Getting Text files

            foreach (FileInfo file in Files)
            {
                var fil = new TreeViewItem
                {
                    Header = file.Name,
                    Tag = file.FullName,
                    ContextMenu = (ContextMenu)this.FindResource("filMenu")
                };
                fil.Selected += new RoutedEventHandler(UpdateBar);
                root.Items.Add(fil);
            }

            return root;
        }

        private void ExitOnClick(object sender, RoutedEventArgs e)
        {
            System.Environment.Exit(0);
        }
        private void ClickedShowView(object sender, RoutedEventArgs e)
        {
            var it = (TreeViewItem)MyTree.SelectedItem;
            var content = File.ReadAllText((string)it.Tag);
            scrollViewer.Content = new TextBlock() { Text = content };
        }
        private void ClickedDeleteFile(object sender, RoutedEventArgs e)
        {
            var plik  = (TreeViewItem)MyTree.SelectedItem;
            var path = (string)plik.Tag;

            FileAttributes atr = File.GetAttributes(path);
            File.SetAttributes(path, atr & ~FileAttributes.ReadOnly);

            File.Delete(path);

            if ((TreeViewItem)MyTree.Items[0] != plik)
            {
                var par = (TreeViewItem)plik.Parent;
                par.Items.Remove(plik);
            }
            else
            {
                MyTree.Items.Clear();
            }
        }

        private void ClickedDeleteDirectory(object sender, RoutedEventArgs e)
        {
            var badanyFolder = (TreeViewItem)MyTree.SelectedItem;
            var path = (string)badanyFolder.Tag;

            FileAttributes atr = File.GetAttributes(path);
            File.SetAttributes(path, atr & ~FileAttributes.ReadOnly);

            RekurencjaDoUsuwaniaFolderow(path);

            if ((TreeViewItem)MyTree.Items[0] != badanyFolder)
            {
                var par = (TreeViewItem)badanyFolder.Parent;
                par.Items.Remove(badanyFolder);
            }
            else
            {
                MyTree.Items.Clear();
            }

        }
        private void RekurencjaDoUsuwaniaFolderow(string path)
        {
            DirectoryInfo d = new DirectoryInfo(path);
            foreach (DirectoryInfo dir in d.GetDirectories())
            {
                RekurencjaDoUsuwaniaFolderow(dir.FullName);
            }

            foreach (FileInfo fi in d.GetFiles())
            {
                File.Delete(fi.FullName);
            }
            Directory.Delete(path);
        }
        private void Create_OnClick(object sender, RoutedEventArgs e)
        {
            var it = (TreeViewItem)MyTree.SelectedItem;
            var path = (string)it.Tag;
            dial dial = new dial(path);

            dial.ShowDialog();
            if (dial.isDoneCreating())
            {
                if (File.Exists(dial.getPath()))
                {
                    var fl = new FileInfo(dial.getPath());
                    it.Items.Add(CreateFile(fl));
                }
                else if (Directory.Exists(dial.getPath()))
                {
                    var fl = new DirectoryInfo(dial.getPath());
                    it.Items.Add(CreateDirectory(fl));
                }
            }
        }
        private TreeViewItem CreateFile(FileInfo file)
        {
            var fil = new TreeViewItem
            {
                Header = file.Name,
                Tag = file.FullName,
                ContextMenu = (ContextMenu)this.FindResource("filMenu")
            };
            fil.Selected += new RoutedEventHandler(UpdateBar);
            return fil;
        }
        private TreeViewItem CreateDirectory(DirectoryInfo file)
        {
            var fil = new TreeViewItem
            {
                Header = file.Name,
                Tag = file.FullName,
                ContextMenu = (ContextMenu)this.FindResource("directMenu")
            };
            fil.Selected += new RoutedEventHandler(UpdateBar);
            return fil;
        }
        
        public void UpdateBar(object obj, RoutedEventArgs a)
        {
            var item = (TreeViewItem)MyTree.SelectedItem;
            FileAttributes atr = File.GetAttributes((string)item.Tag);
            RAHS.Text = "";
            if ((atr & FileAttributes.ReadOnly) == FileAttributes.ReadOnly)
            {
                RAHS.Text = RAHS.Text + 'r';
            }
            else
            {
                RAHS.Text = RAHS.Text + '-';
            }
            if ((atr & FileAttributes.Archive) == FileAttributes.Archive)
            {
                RAHS.Text += 'a';
            }
            else
            {
                RAHS.Text += '-';
            }

            if ((atr & FileAttributes.Hidden) == FileAttributes.Hidden)
            {
                RAHS.Text += 'h';
            }
            else
            {
                RAHS.Text += '-';
            }

            if ((atr & FileAttributes.System) == FileAttributes.System)
            {
                RAHS.Text += 's';
            }
            else
            {
                RAHS.Text += '-';
            }

        }
    }
}
