package com.example.calidadsueno.infrastructure;

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

    public String getExplanation(ExplicacionRequestDTO request) {
        String url = String.format(API_URL_TEMPLATE, apiKey);

        // Map values to natural language
        Map<String, String> duracionMap = new HashMap<>();
        duracionMap.put("Normal", "entre 6 y 9 horas");
        duracionMap.put("Corta", "menos de 6 horas");
        duracionMap.put("Larga", "más de 9 horas");

        Map<String, String> latenciaMap = new HashMap<>();
        latenciaMap.put("Normal", "entre 5 y 15 minutos");
        latenciaMap.put("Prolongada", "más de 15 minutos");
        latenciaMap.put("Patologica", "menos de 5 minutos");

        String covidText = "Si".equals(request.covid) ? "he tenido COVID-19 en los últimos 6 meses"
                : "no he tenido COVID-19 en los últimos 6 meses";

        String duracion = duracionMap.getOrDefault(request.duracionSueno, request.duracionSueno);
        String latencia = latenciaMap.getOrDefault(request.latencia, request.latencia);

        String promptText = String.format(
                "Eres un especialista en Medicina del Sueño, experto en la evaluación de la calidad de sueño en adultos jóvenes. "
                        +
                        "Tras completar un formulario basado en un modelo de Inteligencia Artificial entrenado con más de 800 registros de estudiantes de Ciencias de la Salud, "
                        +
                        "obtuve el resultado de %s calidad de sueño. Percibo mi calidad de sueño como %s, durante el último mes he dormido habitualmente %s por noche "
                        +
                        "y por lo general tardo en quedarme dormido %s. Además, %s. " +
                        "Explícame de forma simple por qué obtuve ese resultado. Dame una respuesta no mayor a un párrafo, en prosa y sin imágenes. "
                        +
                        "Utiliza términos comunes y fáciles de entender por cualquier persona. Si no sabes cómo responder, dímelo. No emitas recomendaciones de ningún tipo.",
                request.resultado, request.percepcionCalidad, duracion, latencia, covidText);

        // Construct Request Body
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

            if (response.getBody() == null)
                return "Error: Respuesta vacía de Gemini.";

            // Parse response: candidates[0].content.parts[0].text
            Map<String, Object> responseBody = response.getBody();
            List<Map<String, Object>> candidates = (List<Map<String, Object>>) responseBody.get("candidates");

            if (candidates != null && !candidates.isEmpty()) {
                Map<String, Object> candidate = candidates.get(0);
                Map<String, Object> contentObj = (Map<String, Object>) candidate.get("content");
                List<Map<String, Object>> parts = (List<Map<String, Object>>) contentObj.get("parts");
                if (parts != null && !parts.isEmpty()) {
                    return (String) parts.get(0).get("text");
                }
            }
            return "No se pudo generar una explicación (formato inesperado).";

        } catch (Exception e) {
            e.printStackTrace();
            return "Error al comunicarse con Gemini: " + e.getMessage();
        }
    }
}
