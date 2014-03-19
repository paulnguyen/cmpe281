package springidol;

public class PoeticJuggler extends Juggler
{
   private Poem poem;

   public PoeticJuggler(Poem poem)
   {
      //<co id="co_injectPoem"/>
      super();
      this.poem = poem;
   }

   public PoeticJuggler(int beanBags, Poem poem)
   {
      // <co id="co_injectPoemBeanBags"/>
      super(beanBags);
      this.poem = poem;
   }

   public String perform() throws PerformanceException
   {
      super.perform();
      System.out.println("While reciting...");

      poem.recite();
      return " While reciting... ";
   }
  }