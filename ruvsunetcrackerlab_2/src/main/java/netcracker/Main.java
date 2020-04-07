package netcracker;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Exchanger;

/**
 * класс Main - точка запуска проекта
 */
public class Main {
    /**
     * процедура запуска проекта
     * @param args
     */
    public static void main(String[] args) throws Exception {
        Random rand = new Random();

        Exchanger<Customer> c = new Exchanger<Customer>();
        Exchanger<Boolean> n = new Exchanger<Boolean>();

        CashBox cashbox = new CashBox();

        LinkedList<Customer> q1 = new LinkedList<Customer>();
        LinkedList<Customer> q2 = new LinkedList<Customer>();
        LinkedList<Customer> q3 = new LinkedList<Customer>();

        Manager m1 = new Manager("Мэнэджер 1", cashbox, q1, c, n);
        Manager m2 = new Manager("Мэнэджер 2", cashbox, q2, c, n);
        Manager m3 = new Manager("Мэнэджер 3", cashbox, q3, c, n);

        List<Manager> manList = new ArrayList<Manager>();

        Thread[] managers = new Thread[3];

        manList.add(m1);
        manList.add(m2);
        manList.add(m3);

        Thread t0 = new Thread(new ThreadCust(c, n));
        t0.start();

        managers[0] = new Thread(m1);
        managers[1] = new Thread(m2);
        managers[2] = new Thread(m3);

        for ( int i = 0; i < managers.length; ++i ) {
            Thread.sleep(rand.nextInt(3000) + 100);
            System.out.println(manList.get(i).getName() + " приступает к работе");
            managers[i].start();
        }
        for ( int i = 0; i < managers.length; ++i )
            managers[i].join();


        //managers[0].start();

        /*String[] managNames = { "Мэнеджер Иванов", "Мэнеджер Петров", "Мэнеджер Сидоров", "Мэнеджер Сталин", "Мэнеджер Рузвельт", "Мэнеджер Черчиль" };
        String[] custNames = { "Клиент Иванов", "Клиент Петров", "Клиент Сидоров", "Клиент Сталин", "Клиент Рузвельт", "Клиент Черчиль" };
        CashBox cashbox = new CashBox();
        Random rand = new Random();
        Thread[] managers = new Thread[3];
        LinkedList<Customer> q1 = new LinkedList<>();
        LinkedList<Customer> q2 = new LinkedList<>();
        LinkedList<Customer> q3 = new LinkedList<>();

        Manager m1 = new Manager("Мэнэджер 1", cashbox, q1);
        Manager m2 = new Manager("Мэнэджер 2", cashbox, q2);
        Manager m3 = new Manager("Мэнэджер 3", cashbox, q3);

        ThreadCust thread = new ThreadCust(q1);
        Thread creat = new Thread(thread);
        creat.start();


        Thread.sleep(rand.nextInt(3000) + 100);
        managers[0] = new Thread(m1);
        managers[0].start();

        Thread.sleep(rand.nextInt(3000) + 100);
        managers[1] = new Thread(m2);
        managers[1].start();
        Thread.sleep(rand.nextInt(3000) + 100);
        managers[2] = new Thread(m3);
        managers[2].start();

        for ( int i = 0; i < managers.length; ++i )
            managers[i].join();

        /*for ( int i = 0; i < managers.length; ++i ) {
            Thread.sleep(rand.nextInt(3000) + 100);
            managers[1] = new Thread(m1);
            managers[i].start();
        }*/

        //MainView menu = new MainView();
        //menu.showMatrix();
        //menu.Filter();
    }
}
