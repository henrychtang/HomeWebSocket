package home.testapp;

import org.eclipse.jetty.server.Server;

public class HelloWorld {
	


	public static void main(String[] args) throws Exception{
		
	        Server server = new Server(8800);
	        server.start();
	        server.dumpStdErr();
	        server.join();
	}

}
