package netcracker;
import java.util.Random;
import java.util.concurrent.Exchanger;

public class ThreadCust implements Runnable {
    private Exchanger<Customer> exchanger;
    private Exchanger<Boolean> exchangerN;
    private boolean toCreate;
    private Customer customer;
    private Random rand = new Random();
    private int i = 0;

    ThreadCust(Exchanger<Customer> ex, Exchanger<Boolean> exN){
        this.exchanger=ex;
        this.exchangerN=exN;
    }

    @Override
    public void run() {
        toCreate = false;
        customer = new Customer("Клиент" + i, rand.nextInt(3000) + 50, 30, "getCash");
        System.out.println(customer.getName() + " пришел");
        while(i < 10){
            try{
                Thread.sleep(rand.nextInt(3000) + 20);
                toCreate = exchangerN.exchange(toCreate);
                if(toCreate){
                    customer = new Customer("Клиент" + ++i, rand.nextInt(3000) + 40, 30, "putCash");
                    System.out.println(customer.getName() + " пришел");
                }
                exchanger.exchange(customer);
            }
            catch(InterruptedException ex){
                System.out.println(ex.getMessage());
            }
        }

    }
}
