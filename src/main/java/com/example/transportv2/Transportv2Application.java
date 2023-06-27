package com.example.transportv2;

import com.example.transportv2.entity.ResultCalcul;
import com.example.transportv2.service.TransportCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import java.math.BigDecimal;

@SpringBootApplication
public class Transportv2Application implements CommandLineRunner {

     private TransportCalculator transportCalculator;


    public Transportv2Application(RepositoryRestConfiguration repositoryRestConfiguration, TransportCalculator transportCalculator) {
        this.transportCalculator = transportCalculator;
        repositoryRestConfiguration.exposeIdsFor(ResultCalcul.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(Transportv2Application.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
        BigDecimal poid = BigDecimal.valueOf(5);
        this.transportCalculator.calculateTransportCost(1, 2, 5, poid, true);
    }
}
