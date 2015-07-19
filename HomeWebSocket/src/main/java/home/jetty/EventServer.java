package org.eclipse.jetty.demo;

import javax.websocket.server.ServerContainer;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;

public class EventServer
{
    public static void main(String[] args)
    {
    	System.out.println("Define sever port 8080 and context /");
        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8080);
        server.addConnector(connector);

        // Setup the basic application "context" for this application at "/"
        // This is also known as the handler tree (in jetty speak)
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
        
        
        

        try
        {
        	System.out.println("Define ServerContainer");
        	// Initialize javax.websocket layer
            ServerContainer wscontainer = WebSocketServerContainerInitializer.configureContext(context);
            wscontainer.setAsyncSendTimeout(0);
            wscontainer.setDefaultMaxSessionIdleTimeout(0);
            // Add WebSocket endpoint to javax.websocket layer
            wscontainer.addEndpoint(EventSocket.class);
            wscontainer.addEndpoint(home.websocket.DeviceWebSocketServer.class);
            
            server.start();
            server.dump(System.err);
            server.join();
        }
        catch (Throwable t)
        {
            t.printStackTrace(System.err);
        }
    }
}
