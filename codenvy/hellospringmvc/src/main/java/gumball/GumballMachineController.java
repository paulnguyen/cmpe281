package gumball;


import org.springframework.context.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import springidol.Performer;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GumballMachineController implements Controller
{

   @Override
   public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
   {
      
      String insertQtr = request.getParameter("btnInsert");
      String turnCrank = request.getParameter("btnCrank");
      
      ModelAndView view = new ModelAndView("gumball_view");

      if (insertQtr != null)
      {
        view.addObject("message", insertQtr );
      }
      else if (turnCrank != null)
      {
        view.addObject("message", turnCrank );
      }
      else
      {
        view.addObject("message", "");
      }

      return view;
   }
}