package com.educandoweb.cursomc.services;

import com.educandoweb.cursomc.domain.Client;
import com.educandoweb.cursomc.repositories.ClientRepository;
import com.educandoweb.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AuthService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private BCryptPasswordEncoder pe;

    @Autowired
    private EmailService emailService;

    private Random rand = new Random();

    public void sendNewPassword(String email){
        Client client = clientRepository.findByEmail(email);
        if (client == null){
            throw new ObjectNotFoundException("E-mail not found!");
        }
        String newPass = newPassword();
        client.setPassword(pe.encode(newPass));
        clientRepository.save(client);

        emailService.sendNewPasswordEmail(client, newPass);
    }

    private String newPassword() {
        char[] vet = new char[10];
        for (int i = 0; i < 10; i++){
            vet[i] = randomChar();
        }
        return new String(vet);
    }

    private char randomChar() {
        int opt = rand.nextInt(3);
        if (opt == 0 ){
            return (char) (rand.nextInt(10) + 48);
        }
        else if (opt == 1){
            return (char) (rand.nextInt(10) + 65);
        }
        else{
            return (char) (rand.nextInt(10) + 97);
        }
    }
}
