package SipOfRiak;

import java.util.ArrayList;

public class Order
{
    public Order() { Items= new ArrayList<Item>(); }
    public long OrderId;
    public long CustomerId;
    public long SalespersonId;
    public ArrayList<Item> Items;
    public Double Total;
    public String OrderDate;
}
