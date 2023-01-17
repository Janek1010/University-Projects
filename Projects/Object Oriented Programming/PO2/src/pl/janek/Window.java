package pl.janek;

import Zwierzeta.Lis;
import Zwierzeta.Wilk;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.awt.geom.*;
import java.util.Scanner;


// TO-DO reszta roslin, zapis.odczyt do pliku, dodawanie z panelu organizmow
public class Window extends JFrame implements ActionListener {

    private JFrame frame;
    private JButton nextRoundBtn;
    private JButton saveBtn, loadBtn;
    private JLabel iloscZwierzat;
    private JTextArea Legenda, stanGry;
    private JRadioButton wilkR, owcaR, lisR, zolwR, antylopaR, trawaR, mleczR,
            guaranaR, jagodyR, barszczR;
    private ButtonGroup radioPanel;
    private JButton btn;
    private Swiat swiat;
    private JPanel board;
    private List<JButton> przyciski = new LinkedList<>();
    private int counter;

    public void actionPerformed(ActionEvent e) {
        JButton caller = (JButton) e.getSource();

        frame.setFocusable(true);
        frame.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        swiat.setDirection(Direction.NORTH);
                        break;
                    case KeyEvent.VK_DOWN:
                        swiat.setDirection(Direction.SOUTH);
                        break;
                    case KeyEvent.VK_RIGHT:
                        swiat.setDirection(Direction.EAST);
                        break;
                    case KeyEvent.VK_LEFT:
                        swiat.setDirection(Direction.WEST);
                        break;
                    case KeyEvent.VK_ENTER:
                        swiat.setAktywacjaMocy(true);
                        break;
                }
            }
        });
        frame.requestFocusInWindow();
        frame.requestFocus();
        if (caller == nextRoundBtn) {
            swiat.nowaTura();
            stanGry.setText(swiat.getGigaString());
            swiat.gigaString = "";
            counter = 0;
            for (int i = 0; i < 20; i++) {
                for (int k = 0; k < 20; k++) {
                    if (swiat.organizmy[i][k] != null) {
                        przyciski.get(i * 20 + k).setBackground(swiat.organizmy[i][k].getKolor());
                        //przyciski.get(i*20 +k).setText(String.valueOf(swiat.organizmy[i][k].getSymbol()));
                        counter++;
                    } else {
                        przyciski.get(i * 20 + k).setBackground(Color.WHITE);
                        //przyciski.get(i*20 +k).setText("");
                    }
                }
            }
            iloscZwierzat.setText("iloscZwierzat:  " + counter);
            System.out.println("Next round");
        } else if (caller == saveBtn) {
            JFileChooser fc = new JFileChooser();
            if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                File plik = fc.getSelectedFile();
                JOptionPane.showMessageDialog(null, "Wybrany plik to " + plik);
                try {
                    PrintWriter pw = new PrintWriter(plik);
                    for (int i = 0; i < 20; i++) {
                        for (int k = 0; k < 20; k++) {
                            if (swiat.organizmy[i][k] != null) {
                                String saveStr = i + " " + k + " " + swiat.organizmy[i][k].getSymbol();
                                pw.println(saveStr);
                                // Y, X, symbol
                            }
                        }
                    }
                    pw.close();
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
            System.out.println("Save");
        } else if (caller == loadBtn) {
            JFileChooser fc = new JFileChooser();
            if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                File plik = fc.getSelectedFile();
                JOptionPane.showMessageDialog(null, "Wybrany plik to: " + plik.getAbsolutePath());
                try {
                    Scanner skaner = new Scanner(plik);
                    swiat.usunGre();
                    while (skaner.hasNext()) {
                        String line = skaner.nextLine();
                        String elements[] = line.split(" ");
                        // 0 element - Y
                        // 1 element - X
                        // 2 element - Symbol
                        swiat.dodajNowyOrganizm(Integer.parseInt(elements[0]), Integer.parseInt(elements[1]), elements[2].charAt(0));
                    }
                    skaner.close();
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            JButton button = (JButton) e.getSource();
            int X = Integer.parseInt(button.getName()) % 20;
            int Y = Integer.parseInt(button.getName()) / 20;
            if (wilkR.isSelected()) {
                swiat.dodajNowyOrganizm(Y, X, 'W');
            } else if (owcaR.isSelected()) {
                swiat.dodajNowyOrganizm(Y, X, 'O');
            } else if (lisR.isSelected()) {
                swiat.dodajNowyOrganizm(Y, X, 'L');
            } else if (zolwR.isSelected()) {
                swiat.dodajNowyOrganizm(Y, X, 'Z');
            } else if (antylopaR.isSelected()) {
                swiat.dodajNowyOrganizm(Y, X, 'A');
            } else if (trawaR.isSelected()) {
                swiat.dodajNowyOrganizm(Y, X, 'T');
            } else if (mleczR.isSelected()) {
                swiat.dodajNowyOrganizm(Y, X, 'M');
            }
            else if (guaranaR.isSelected()) {
                swiat.dodajNowyOrganizm(Y, X, 'G');
            }
            else if (jagodyR.isSelected()) {
                swiat.dodajNowyOrganizm(Y, X, 'J');
            }
            else if (barszczR.isSelected()) {
                swiat.dodajNowyOrganizm(Y, X, 'B');
            }
        }
    }

    public Window(int sizeX, int sizeY, Swiat swiat) {

        frame = new JFrame();
        this.swiat = swiat;
        this.counter = 0;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null); //sprawia że musisz podać wymiary przy dodawaniu nowego objektu swing 
        JPanel board = new JPanel();
        board.setLayout(new GridLayout(sizeY, sizeX));
        board.setBounds(10, 10, 550, 550);

        for (int i = 0; i < sizeY; i++) {
            for (int k = 0; k < sizeX; k++) {
                JButton btn = new JButton();
                if (swiat.organizmy[i][k] != null) {
                    btn.setBackground(swiat.organizmy[i][k].getKolor());
                } else {
                    btn.setBackground(Color.WHITE);
                }
                btn.setFont(new Font("Arial", Font.BOLD, 10));
                //String name = String.valueOf(i) + " "+ String.valueOf(k) ;
                btn.setName(String.valueOf(i * 20 + k));
                btn.addActionListener(this);
                board.add(btn);
                przyciski.add(btn);
            }
        }


        nextRoundBtn = new JButton("Next Round");
        nextRoundBtn.setName("nextRound");
        nextRoundBtn.addActionListener(this);
        nextRoundBtn.setBounds(600, 10, 120, 60);

        saveBtn = new JButton("Save!");
        saveBtn.setName("save");
        saveBtn.addActionListener(this);
        saveBtn.setBounds(600, 150, 120, 60);

        loadBtn = new JButton("Load!");
        loadBtn.setName("load");
        loadBtn.addActionListener(this);
        loadBtn.setBounds(600, 290, 120, 60);

        iloscZwierzat = new JLabel("iloscZwierzat:  ");
        iloscZwierzat.setName("iloscZwierzat");
        iloscZwierzat.setBounds(600, 400, 120, 60);

        Legenda = new JTextArea("*****Legenda***** \n czerwone - Wilk \n czarne - owce \n pomaranczowe - lis \n " +
                "szare - zolw \n rozowe - antylopa \n niebieski - czlowiek \n zielony - trawa \n Magenta - mlecz \n zolty - guarana" +
                " \n czarny szary = Wilcze Jagody \n jasno szary = Barsz Sosnowskiego \n \n Sterowanie: strzalki + enter - moc");
        Legenda.setName("Legenda");
        Legenda.setBounds(770, 10, 300, 400);

        stanGry = new JTextArea(swiat.getGigaString());
        stanGry.setBounds(10, 620, 900, 120);
        stanGry.setName("stanGry");

        radioPanel = new ButtonGroup();

        wilkR = new JRadioButton("Wilk");
        wilkR.setSelected(true);
        wilkR.setBounds(10, 570, 70, 30);
        radioPanel.add(wilkR);

        owcaR = new JRadioButton("Owca");
        owcaR.setBounds(100, 570, 70, 30);
        radioPanel.add(owcaR);

        lisR = new JRadioButton("Lis");
        lisR.setBounds(200, 570, 70, 30);
        radioPanel.add(lisR);

        zolwR = new JRadioButton("Zolw");
        zolwR.setBounds(300, 570, 70, 30);
        radioPanel.add(zolwR);

        antylopaR = new JRadioButton("Antylopa");
        antylopaR.setBounds(400, 570, 70, 30);
        radioPanel.add(antylopaR);

        trawaR = new JRadioButton("Trawa");
        trawaR.setBounds(500, 570, 70, 30);
        radioPanel.add(trawaR);

        mleczR = new JRadioButton("Mlecz");
        mleczR.setBounds(600, 570, 70, 30);
        radioPanel.add(mleczR);

        guaranaR = new JRadioButton("Guarana");
        guaranaR.setBounds(700, 570, 70, 30);
        radioPanel.add(guaranaR);

        jagodyR = new JRadioButton("Jagoda");
        jagodyR.setBounds(800, 570, 70, 30);
        radioPanel.add(jagodyR);

        barszczR = new JRadioButton("Barszcz");
        barszczR.setBounds(900, 570, 70, 30);
        radioPanel.add(barszczR);

        mainPanel.add(wilkR);
        mainPanel.add(owcaR);
        mainPanel.add(lisR);
        mainPanel.add(zolwR);
        mainPanel.add(antylopaR);
        mainPanel.add(mleczR);
        mainPanel.add(trawaR);
        mainPanel.add(guaranaR);
        mainPanel.add(jagodyR);
        mainPanel.add(barszczR);

        mainPanel.add(nextRoundBtn);
        mainPanel.add(saveBtn);
        mainPanel.add(loadBtn);
        mainPanel.add(board);
        mainPanel.add(iloscZwierzat);
        mainPanel.add(Legenda);
        mainPanel.add(stanGry);


        frame.add(mainPanel);
        frame.setContentPane(mainPanel);
        frame.setSize(1000, 800);
        frame.setVisible(true);
    }
}