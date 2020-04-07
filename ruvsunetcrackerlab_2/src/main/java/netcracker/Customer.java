package netcracker;

class Customer {
    private String name;
    private long dealtime;
    private int cash;
    private String deal;


    Customer(String n, long dt, int cb, String d) {
        name = n;
        dealtime = dt;
        cash = cb;
        deal=d;
    }
    public String getDeal(){return deal;}

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCash(){
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }

    public long getDealtime() {
        return dealtime;
    }

    public void setDealtime(long dealtime) {
        this.dealtime = dealtime;
    }
}
