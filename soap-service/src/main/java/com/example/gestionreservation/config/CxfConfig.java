package com.example.gestionreservation.config;

import com.example.gestionreservation.service.ReservationSoapService;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.Bus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import jakarta.xml.ws.Endpoint;

@Configuration
public class CxfConfig {

    private final ReservationSoapService reservationSoapService;

    public CxfConfig(ReservationSoapService reservationSoapService) {
        this.reservationSoapService = reservationSoapService;
    }

    @Bean
    public Endpoint endpoint(Bus bus) {
        EndpointImpl endpoint = new EndpointImpl(bus, reservationSoapService);
        endpoint.publish("/ReservationService"); // Expose le service à l'URL
        return endpoint;
    }
}
