package br.com.alura.ecommerce;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class HttpEcommerceService {

    public static void main(String[] args) throws Exception {
        var server = new Server(8080);

        var context = new ServletContextHandler();
        context.setContextPath("/");
        context.addServlet(new ServletHolder(new NewOrderServlet()), "/new");
        context.addServlet(new ServletHolder(new GenerateAllReportsServlet()), "/admin/generate-reports");

        server.setHandler(context);

        server.start();
        server.join();
    }
}
