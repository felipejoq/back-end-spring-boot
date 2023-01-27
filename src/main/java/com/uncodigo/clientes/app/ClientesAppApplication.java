package com.uncodigo.clientes.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Locale;

@SpringBootApplication
public class ClientesAppApplication implements CommandLineRunner {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);
        SpringApplication.run(ClientesAppApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String pw = "123123";
        for (int i = 0; i < 3; i++) System.out.println(passwordEncoder.encode(pw));
    }
}
