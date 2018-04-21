package SipOfRiak;

public class OrderSummaryItem
{
    public OrderSummaryItem() { }
    public OrderSummaryItem(Order parentOrder) {
        OrderId = parentOrder.OrderId;
        Total = parentOrder.Total;
        OrderDate = parentOrder.OrderDate;
    }

    public long OrderId;
    public Double Total;
    public String OrderDate;
}