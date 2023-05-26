package com.vitordalvi.lucas;

import com.google.gson.Gson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class InverterTextoApplication {

	public static void main(String[] args) {
		SpringApplication.run(InverterTextoApplication.class, args);
	}

	@PostMapping(value = "/inverter", consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> reverseText(@RequestBody ReverseRequest request) {
		if (request.text().length() > 150) {
			String errorMessage = "A mensagem passou do limite de 150 caracteres.";
			return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		String textoInvertido = new StringBuilder(request.text()).reverse().toString();
		ReverseResponse reverseResponse = new ReverseResponse(textoInvertido);
		String respostaJson = new Gson().toJson(reverseResponse);
		return new ResponseEntity<>(respostaJson, HttpStatus.OK);
	}

	public record ReverseRequest(String text) {}

	public record ReverseResponse(String reversedText) {}
}
