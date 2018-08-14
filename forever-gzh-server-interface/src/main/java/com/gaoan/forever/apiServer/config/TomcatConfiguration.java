package com.gaoan.forever.apiServer.config;

import org.apache.catalina.connector.Connector;
import org.apache.catalina.valves.RemoteIpValve;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by keymean on 2016/7/22.
 */
@Configuration
public class TomcatConfiguration {

    @Value("${server.tomcat.connector.port:8031}")
    private int port;
    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
        tomcat.addAdditionalTomcatConnectors(createConnector());
        tomcat.addContextValves(createRemoteIpValves());
        tomcat.addConnectorCustomizers(new MyTomcatConnectorCustomizer()); 
        return tomcat;
    }

    private RemoteIpValve createRemoteIpValves(){
        RemoteIpValve remoteIpValve = new RemoteIpValve();
        remoteIpValve.setRemoteIpHeader("x-forwarded-for");
        remoteIpValve.setProtocolHeader("x-forwarded-protocol");
        return remoteIpValve;
    }

    private Connector createConnector() {
        Connector connector = new Connector("AJP/1.3");
        connector.setPort(port);
        return connector;
    }
}
class MyTomcatConnectorCustomizer implements TomcatConnectorCustomizer  
{  
    public void customize(Connector connector)  
    {  
        Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();  
        //设置最大连接数  
        protocol.setMaxConnections(2000);  
        //设置最大线程数  
        protocol.setMaxThreads(2000);  
        protocol.setConnectionTimeout(30000);  
    }  
}  
