package helloworld;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.Invocation.Builder;

public class GreetingController implements Controller
{

   @Override
   public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
   {
      String userName = request.getParameter("user");
      String result = "";
      if (userName != null)
      {
         Client client = ClientBuilder.newClient();
         WebTarget target = client.target("http://pnguyen-hello-jersey.aws.af.cm").path("document");
         Invocation.Builder invocationBuilder = target.request( MediaType.APPLICATION_JSON ) ;
         result = target.request("application/json").get(String.class);
      }

      ModelAndView view = new ModelAndView("hello_view");
      view.addObject("greeting","Hello " + userName + "<br/><br/>" + result );
      return view;
   }
}
