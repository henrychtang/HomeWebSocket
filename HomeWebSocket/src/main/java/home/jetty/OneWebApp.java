package home.jetty;

//
//  ========================================================================
//  Copyright (c) 1995-2014 Mort Bay Consulting Pty. Ltd.
//  ------------------------------------------------------------------------
//  All rights reserved. This program and the accompanying materials
//  are made available under the terms of the Eclipse Public License v1.0
//  and Apache License v2.0 which accompanies this distribution.
//
//      The Eclipse Public License is available at
//      http://www.eclipse.org/legal/epl-v10.html
//
//      The Apache License v2.0 is available at
//      http://www.opensource.org/licenses/apache2.0.php
//
//  You may elect to redistribute this code under either of these licenses.
//  ========================================================================
//
 
 
import java.lang.management.ManagementFactory;
 
import org.eclipse.jetty.jmx.MBeanContainer;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
 
public class OneWebApp
{
    public static void main(String[] args) throws Exception
    {
        Server server = new Server(8090);
         
        // Setup JMX
        MBeanContainer mbContainer=new MBeanContainer(ManagementFactory.getPlatformMBeanServer());
        server.addBean(mbContainer);
 
        WebAppContext webapp = new WebAppContext();
        webapp.setContextPath("/");
        //webapp.setWar("../../jetty-distribution/target/distribution/demo-base/webapps/test.war");
        webapp.setResourceBase("src/test/webapp");
        server.setHandler(webapp);
 
        //HashLoginService loginService = new HashLoginService();
        //loginService.setName("Test Realm");
        //loginService.setConfig("src/test/resources/realm.properties");
        //server.addBean(loginService);
 
       server.start();
        server.join();
    }
}