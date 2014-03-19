package helloworld;

import org.springframework.context.*;
import org.springframework.context.support.ClassPathXmlApplicationContext ;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import springidol.Performer;
import springidol.Stage;
import springidol.Ticket;
import springidol.Instrumentalist;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GreetingController implements Controller
{

   @Override
   public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
   {
   
   
      ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-idol.xml");
      Performer performer = (Performer) ctx.getBean("duke");
      //Performer performer = (Performer) ctx.getBean("poeticDuke");
      String act = performer.perform();  
      Stage stage = (Stage) ctx.getBean("theStage");      
      Ticket ticket1 = (Ticket) ctx.getBean("ticket");      
      Ticket ticket2 = (Ticket) ctx.getBean("ticket");      
      System.out.println( "Ticket 1: " + ticket1 ) ;   
      System.out.println( "Ticket 2: " + ticket2 ) ;   
      Instrumentalist performer1 = (Instrumentalist) ctx.getBean("kenny") ;
      performer1.perform() ;
      Instrumentalist performer2 = (Instrumentalist) ctx.getBean("carl") ;
      performer2.perform() ;
   
      String userName = request.getParameter("user");
      String result = "";
      if (userName != null)
      {
        result = "Hello, " + userName + "!";
      }

      ModelAndView view = new ModelAndView("hello_view");
      view.addObject("greeting", result);
      view.addObject("message",act) ;
      return view;
   }
}