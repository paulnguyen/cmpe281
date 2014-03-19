package helloworld;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.restlet.resource.ClientResource;
import org.restlet.representation.Representation;

public class GreetingController implements Controller
{

   @Override
   public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
   {
      String userName = request.getParameter("user");
      String result = "";
      if (userName != null)
      {
        result = "Hello, " + userName + "!";
        
        
        ClientResource res = new ClientResource("http://pnguyen-hello-jersey.aws.af.cm/document") ;
        Representation rep = res.get() ;
        result = rep.getText() ;
      }

      ModelAndView view = new ModelAndView("hello_view");
      view.addObject("greeting", result);
      return view;
   }
}
