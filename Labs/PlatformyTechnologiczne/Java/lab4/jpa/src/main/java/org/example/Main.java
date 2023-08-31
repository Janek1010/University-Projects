package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import org.example.model.Mage;
import org.example.model.Tower;

import java.awt.print.Book;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpaLab");
    static EntityManager entityManager = entityManagerFactory.createEntityManager();
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        testDataInit();
        showMenu();
        while (scanner.hasNext()) {
            try {
                int operationNumber = Integer.parseInt(scanner.nextLine());
                showOptions(operationNumber);

            } catch (NumberFormatException e) {
                System.out.println("mozesz wpisywac tylko inty!!");
            }
            showMenu();
        }

        entityManager.close();
        entityManagerFactory.close();
    }

    private static void showOptions(int option) {
        switch (option){
            case 1 -> {
                System.out.println("podaj imie, level i wieze do jakiej ma zostac dodany mag");
                addMage(scanner.next(), Integer.parseInt(scanner.next()), scanner.next());
            }
            case 2 -> {
                System.out.println("podaj nazwe i wysokosc wiezy jaka chcesz dodac");
                addTower(scanner.next(), scanner.next());
            }
            case 3 -> {
                System.out.println("podaj nazwe maga do usuniecia");
                removeMage(scanner.next());
            }
            case 4 -> {
                System.out.println("podaj nazwe wiezy do usuniecia");
                removeTower(scanner.next());
            }
            case 5 -> showMagesAndTowersInDataBase();
            case 6 -> {
                System.out.println("podaj level od ktorego zaczac szukanie magow");
                showMagesWithLevelGreaterThan(scanner.next());
            }
        }
    }

    private static void showMagesWithLevelGreaterThan(String level) {
        System.out.println();
        System.out.println("Magowie z levelem wiekszym niz "+level);
        TypedQuery<Mage> query = entityManager.createQuery("SELECT m FROM Mage m WHERE  m.level > :level ORDER BY m.name", Mage.class);
        query.setParameter("level", level);
        List<Mage> mages = query.getResultList();
        for (Mage mage : mages) {
            System.out.println("    " + mage.getName() + " - Level " + mage.getLevel());
        }
        System.out.println();
    }

    private static void showMenu() {
        System.out.println("Witaj w bazie danych magów i wież");
        System.out.println("aby zarządzać bazą wpisuj poszczególne numery odpowiadające operacji");
        System.out.println("1 -> dodaj maga");
        System.out.println("2 -> dodaj wieże");
        System.out.println("3 -> usuń maga");
        System.out.println("4 -> usuń wieże");
        System.out.println("5 -> pokaż wszystkie rekordy");
        System.out.println("6 -> pokaż  wszystkich magów z poziomem większym niż...");
    }

    private static void showMagesAndTowersInDataBase() {
        System.out.println();
        System.out.println("*SELECT* Widok calej bazy");
        TypedQuery<Tower> query2 = entityManager.createQuery("SELECT t FROM Tower t", Tower.class);
        List<Tower> towers = query2.getResultList();

        for (Tower tower : towers) {
            System.out.println("Magowie nalezacy do wiezy: " + tower.getName());
            TypedQuery<Mage> query = entityManager.createQuery("SELECT m FROM Mage m WHERE m.tower.name = :name", Mage.class);
            query.setParameter("name", tower.getName());
            List<Mage> mages = query.getResultList();
            for (Mage mage : mages) {
                System.out.println("    " + mage.getName() + " - Level " + mage.getLevel());
            }
        }
        System.out.println();
    }

    private static boolean removeMage(String mageName) {
        Mage testMage = entityManager.find(Mage.class, mageName);

        if (testMage == null) {
            LOGGER.log(Level.INFO, "Mag o nazwie: {0} nie istnieje", mageName);
            return false;
        }

        entityManager.getTransaction().begin();

        Tower tower = testMage.getTower();
        tower.getMages().remove(testMage);
        entityManager.remove(testMage);

        entityManager.getTransaction().commit();
        return true;
    }

    private static boolean removeTower(String towerName) {
        Tower testTower = entityManager.find(Tower.class, towerName);

        if (testTower == null) {
            LOGGER.log(Level.INFO, "Mag o nazwie: {0} nie istnieje", towerName);
            return false;
        }

        entityManager.getTransaction().begin();

        System.out.println("*DELETE* Magowie nalezacy do usuwanej wiezy: " + testTower.getName() + " o nazwie " + testTower.getName());
        TypedQuery<Mage> query = entityManager.createQuery("SELECT m FROM Mage m WHERE m.tower.name = :name", Mage.class);
        query.setParameter("name", testTower.getName());
        List<Mage> mages = query.getResultList();
        for (Mage mage : mages) {
            System.out.println("    usuwam " + mage.getName() + " - Level " + mage.getLevel() +  " z bazy danych");
            entityManager.remove(mage);
        }
        entityManager.remove(testTower);
        entityManager.getTransaction().commit();
        return true;
    }


    private static boolean addMage(String name, int level, String towerName) {
        Tower testTower = entityManager.find(Tower.class, towerName);
        if (testTower == null) {
            LOGGER.log(Level.INFO, "Wieża o nazwie: {0} nie istnieje", towerName);
            return false;
        }
        entityManager.getTransaction().begin();
        Mage mage = Mage.builder().name(name).level(level).tower(testTower).build();
        entityManager.persist(mage);
        testTower.getMages().add(mage);
        LOGGER.log(Level.INFO, "dodałem maga: {0}", name+ " o levelu "+ level+ " do wieży "+ towerName);
        entityManager.getTransaction().commit();
        return true;
    }

    private static void addTower(String name, String height) {
        entityManager.getTransaction().begin();
        entityManager.persist(Tower.builder().name(name).height(Integer.parseInt(height)).build());
        LOGGER.log(Level.INFO, "dodałem wieże: {0}", name+ " o wysokosci "+ height);
        entityManager.getTransaction().commit();
    }

    private static void testDataInit() {
        Tower tower = new Tower("Magic-Tower", 10);
        Tower tower2 = new Tower("Bad-Tower", 13);
        entityManager.getTransaction().begin();
        entityManager.persist(tower);
        entityManager.persist(tower2);

        Mage mage = Mage.builder().name("Gandalf").level(23).tower(tower).build();
        entityManager.persist(mage);
        tower.getMages().add(mage);

        Mage mage2 = Mage.builder().name("Artur").level(5).tower(tower2).build();
        entityManager.persist(mage2);
        tower.getMages().add(mage2);

        Mage mage3 = Mage.builder().name("Mirek").level(10).tower(tower).build();
        entityManager.persist(mage3);
        tower.getMages().add(mage3);

        entityManager.getTransaction().commit();
    }
}