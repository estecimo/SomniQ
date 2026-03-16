package com.example.calidadsueno.infrastructure.weka;

import com.example.calidadsueno.interfaces.rest.ExplicacionRequestDTO;
import com.example.calidadsueno.infrastructure.weka.dto.GeminiResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClientException;

import java.util.List;
import java.util.Map;

@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private final String API_URL_TEMPLATE = "https://generativelanguage.googleapis.com/v1beta/models/gemini-flash-latest:generateContent?key=%s";
    private final RestTemplate restTemplate;

    public GeminiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getExplanation(ExplicacionRequestDTO request, String resultadoWeka) {
        String url = String.format(API_URL_TEMPLATE, apiKey);

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
            "Utiliza términos comunes y fáciles de entender por cualquier persona. Si no sabes cómo responder, dímelo. " +
            "No emitas recomendaciones de ningún tipo, solo analiza la relación de los factores con el resultado '%s'.",
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

        Map<String, Object> body = Map.of(
            "contents", List.of(
                Map.of("parts", List.of(
                    Map.of("text", promptText)
                ))
            )
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Parametrizamos el HttpEntity
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        try {
            // postForObject mapea el JSON directamente a tu GeminiResponseDTO
            GeminiResponseDTO response = restTemplate.postForObject(url, entity, GeminiResponseDTO.class);

            if (response != null && response.getCandidates() != null && !response.getCandidates().isEmpty()) {
                return response.getCandidates().get(0)
                               .getContent()
                               .getParts().get(0)
                               .getText();
            }
            
            return "No se pudo obtener la explicación de la IA.";

        } catch (RestClientException e) {
            return "Error de comunicación con el servicio de IA.";
        } catch (Exception e) {
            return "Ocurrió un error inesperado al procesar la respuesta.";
        }
    }
}