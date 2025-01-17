package com.foro.Alura;



import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@SpringBootApplication
public class AluraApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(AluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		generarClaveJWT();
	}

	private void generarClaveJWT() throws NoSuchAlgorithmException {
		KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
		keyGenerator.init(256); // Tamaño de la clave
		SecretKey secretKey = keyGenerator.generateKey();
		String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
		System.out.println("Clave JWT Generada: " + encodedKey);
	}
}



