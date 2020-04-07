package netcracker;

import java.util.LinkedList;
import java.util.concurrent.Exchanger;

public class Manager implements Runnable {
    private Exchanger<Customer> exchanger;
    private Customer customer;
    private boolean f = false;
    private String name;
    private CashBox cashbox;
    private LinkedList<Customer> customers;

    Manager(String n, CashBox cb, LinkedList<Customer> custs, Exchanger<Customer> ex) {
        name = n;
        cashbox = cb;
        customers = custs;
        this.exchanger=ex;
    }

    public String getName(){
        return name;
    }

    private void addInQueue(Customer c){
        customers.addLast(c);
    }

    public LinkedList<Customer> getCustomers(){
        return customers;
    }

    public void getExchange() throws InterruptedException {
        customer=exchanger.exchange(customer);
        if(customer != null){
            f = true;
            addInQueue(customer);
            System.out.println(customer.getName() + " добавлен в очередь к " + name);
        }

    }

    public void serveClients() throws InterruptedException {
        //for (Customer c: customers) {
            Customer c = customers.getFirst();
            System.out.println(name + " Обслуживает " + c.getName());
            System.out.println(name + " Обращается к кассе...");
            cashbox.accept();
            try {
                if(c.getDeal().equals("putCash")) {
                    cashbox.money += c.getCash();
                }
                else{
                    cashbox.money -= c.getCash();
                }

                Thread.sleep(c.getDealtime());
            }
            catch ( InterruptedException ie ) {
                System.out.println("Общение с кассой прервано!");
            }
            System.out.println(name + " закончила работу с кассой.");
            System.out.println("В кассе " + cashbox.money);
            System.out.println(name + " закончила работу с " + c.getName());

            cashbox.done();
            customers.removeFirst();
        //}
    }

    @Override
    public void run() {
        try{
            getExchange();
            while(f){
                serveClients();
                getExchange();
            }
        }

        catch(InterruptedException ex){
            System.out.println(ex.getMessage());
        }

    }
}