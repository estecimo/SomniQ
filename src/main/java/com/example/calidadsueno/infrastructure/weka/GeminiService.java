package com.example.calidadsueno.infrastructure.weka;

import com.example.calidadsueno.interfaces.rest.ExplicacionRequestDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private final String API_URL_TEMPLATE = "https://generativelanguage.googleapis.com/v1beta/models/gemini-flash-latest:generateContent?key=%s";
    private final RestTemplate restTemplate;

    public GeminiService() {
        this.restTemplate = new RestTemplate();
    }

    public String getExplanation(ExplicacionRequestDTO request, String resultadoWeka) {
        String url = String.format(API_URL_TEMPLATE, apiKey);

        // Construcción del Prompt usando tus 10 atributos exactos
        String promptText = String.format(
            "Eres un especialista en Medicina del Sueño, experto en la evaluación de la calidad de sueño en adultos jóvenes." +
            "Tras completar un formulario basado en un modelo de Inteligencia Artificial entrenado con 92 registros de estudiantes de Ciencias Exactas e Ingenierías," +
            "obtuve el resultado de %s calidad de sueño. Mis datos fueron: \n" +
            "1. Percepción propia: %s\n" +
            "2. Medicamentos para dormir: %s\n" +
            "3. Duración del sueño: %s\n" +
            "4. Somnolencia durante el día: %s\n" +
            "5. Adicción al Internet: %s\n" +
            "6. Nivel de adicción: %s\n" +
            "7. Usa internet para vender: %s\n" +
            "8. Usa internet para comprar: %s\n" +
            "9. Sexo: %s\n" +
            "10. Tiempo en quedarse dormido (latencia): %s\n\n" +
            "Instrucciones: Explícame de forma simple por qué obtuve ese resultado. Dame una respuesta no mayor a un párrafo, en prosa y sin imágenes. " +
            "Utiliza términos comunes y fáciles de entender por cualquier persona. Si no sabes cómo responder, dímelo. No emitas recomendaciones de ningún tipo, solo analiza la relación de los factores con el resultado '%s'.",
            resultadoWeka,
            request.percepcion,
            request.frecuenciaMedicacion,
            request.duracionSueno,
            request.somnolenciaDiurna,
            request.adiccionInternet,
            request.nivelAdiccion,
            request.ventaOnline,
            request.comprasOnline,
            request.sexo,
            request.latencia,
            resultadoWeka
        );

        // Estructura del Body para la API de Gemini
        Map<String, Object> part = new HashMap<>();
        part.put("text", promptText);

        Map<String, Object> content = new HashMap<>();
        content.put("parts", List.of(part));

        Map<String, Object> body = new HashMap<>();
        body.put("contents", List.of(content));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);
            Map<String, Object> responseBody = response.getBody();

            // Navegar en la respuesta JSON de Gemini
            List<Map<String, Object>> candidates = (List<Map<String, Object>>) responseBody.get("candidates");
            Map<String, Object> contentObj = (Map<String, Object>) candidates.get(0).get("content");
            List<Map<String, Object>> parts = (List<Map<String, Object>>) contentObj.get("parts");

            return (String) parts.get(0).get("text");

        } catch (Exception e) {
            return "No se pudo generar la explicación técnica. Por favor, consulte con un profesional. (Error: " + e.getMessage() + ")";
        }
    }
}