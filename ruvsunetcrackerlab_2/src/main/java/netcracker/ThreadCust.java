package netcracker;
import java.util.Random;
import java.util.concurrent.Exchanger;

public class ThreadCust implements Runnable {
    private Exchanger<Customer> exchanger;
    private Customer customer;
    private Random rand = new Random();
    private int i = 0;

    ThreadCust(Exchanger<Customer> ex){
        this.exchanger=ex;
    }

    @Override
    public void run() {
        customer = new Customer("Клиент" + i, rand.nextInt(3000) + 50, 30, "getCash");
        System.out.println(customer.getName() + " пришел");
        while(i < 10){
            try{
                Thread.sleep(rand.nextInt(3000) + 40);
                customer = new Customer("Клиент" + ++i, rand.nextInt(3000) + 40, 30, "putCash");
                System.out.println(customer.getName() + " пришел");

                exchanger.exchange(customer);
            }
            catch(InterruptedException ex){
                System.out.println(ex.getMessage());
            }
        }

    }
}
