package SipOfRiak;

public class Item
{
    public Item() { }
    public Item(String itemId, String title, Double price) {
        ItemId = itemId;
        Title = title;
        Price = price;
    }
    public String ItemId;
    public String Title;
    public Double Price;
}
