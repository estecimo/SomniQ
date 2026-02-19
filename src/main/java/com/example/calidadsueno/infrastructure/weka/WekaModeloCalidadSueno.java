package com.example.calidadsueno.infrastructure.weka;

import com.example.calidadsueno.domain.CasoCalidadSueno;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import weka.classifiers.Classifier;
import weka.core.*;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class WekaModeloCalidadSueno {
    private final Classifier classifier;
    private final Instances estructura;

    public WekaModeloCalidadSueno() throws Exception {
        // 1. Cargar el modelo
        InputStream is = new ClassPathResource("Ciencias exactas_Unpruned model.model").getInputStream();
        this.classifier = (Classifier) SerializationHelper.read(is);

        // 2. Crear los 55 atributos según el reporte
        ArrayList<Attribute> atributos = new ArrayList<>();
        
        // Atributo 0: Sexo
        atributos.add(new Attribute("Sexo", List.of("Hombre", "Mujer")));
        
        // Atributos 1 al 7: Relleno (Estado civil, IMC, Licenciatura, etc.)
        for (int i = 1; i <= 7; i++) atributos.add(new Attribute("relleno_" + i));

        // Atributo 8: Latencia de sueño
        atributos.add(new Attribute("Latencia de sueño", List.of("Normal (5-15 minutos)", "Prolongada (> 15 minutos)", "Patológica (< 5 minutos)")));
        
        // Atributo 9: Duración total de sueño
        atributos.add(new Attribute("Duración total de sueño", List.of("Corta (< 6 horas)", "Normal (6-9 horas)", "Larga (> 9 horas)")));
        
        // Atributos 10 y 11: Relleno (Eficiencia, Despertares)
        atributos.add(new Attribute("Eficiencia de sueño"));
        atributos.add(new Attribute("Frecuencia de despertares durante la noche o madrugada"));

        // Atributo 12: ¿Cómo percibe su calidad de sueño?
        atributos.add(new Attribute("¿Cómo percibe su calidad de sueño?", List.of("Buena", "Mala")));

        // Atributo 13: Frecuencia de consumo de medicamentos para dormir
        atributos.add(new Attribute("Frecuencia de consumo de medicamentos para dormir", List.of(
                "Ninguna vez en el último mes", "Una o dos veces a la semana", 
                "Tres o más veces a la semana", "Menos de una vez a la semana")));

        // Atributo 14: Calidad de sueño (ESTA ES LA CLASE SEGÚN EL REPORTE)
        atributos.add(new Attribute("Calidad de sueño", List.of("Mala", "Buena")));

        // Atributos 15 al 17: Relleno
        for (int i = 15; i <= 17; i++) atributos.add(new Attribute("relleno_" + i));

        // Atributo 18: Somnolencia diurna
        atributos.add(new Attribute("Somnolencia diurna", List.of("Normal", "Excesiva", "Marginal")));

        // Atributo 19: Nivel de adicción al Internet
        atributos.add(new Attribute("Nivel de adicción al Internet", List.of("Sin adicción", "Moderada", "Leve", "Severa")));

        // Atributo 20: ¿Tiene adicción al Internet?
        atributos.add(new Attribute("¿Tiene adicción al Internet?", List.of("Sí", "No")));

        // Atributos 21 al 27: Relleno (Uso de internet varios)
        for (int i = 21; i <= 27; i++) atributos.add(new Attribute("relleno_" + i));

        // Atributo 28: ¿Usa Internet para realizar compras?
        atributos.add(new Attribute("¿Usa Internet para realizar compras?", List.of("Sí", "No")));

        // Atributo 29: ¿Usa Internet para realizar ventas?
        atributos.add(new Attribute("¿Usa Internet para realizar ventas?", List.of("Sí", "No")));

        // Atributos 30 al 54: Relleno para completar los 55 atributos
        while(atributos.size() < 55) {
            atributos.add(new Attribute("relleno_final_" + atributos.size()));
        }

        // 3. Configurar estructura
        estructura = new Instances("CienciasExactasRel", atributos, 0);
        // Según el reporte, la clase "Calidad de sueño" está en la posición 14 (índice 14)
        estructura.setClassIndex(14);
        
        System.out.println("Estructura sincronizada con 55 atributos. Clase en índice: " + estructura.classIndex());
    }

    public String clasificar(CasoCalidadSueno caso) throws Exception {
        Instance instancia = new DenseInstance(55); // Tamaño exacto 55
        instancia.setDataset(estructura);

        // campos que el árbol usa
        setValueSafe("¿Cómo percibe su calidad de sueño?", caso.getPercepcion(), instancia);
        setValueSafe("Frecuencia de consumo de medicamentos para dormir", caso.getFrecuenciaMedicacion(), instancia);
        setValueSafe("Duración total de sueño", caso.getDuracionSueno(), instancia);
        setValueSafe("Somnolencia diurna", caso.getSomnolenciaDiurna(), instancia);
        setValueSafe("¿Tiene adicción al Internet?", caso.getAdiccionInternet(), instancia);
        setValueSafe("¿Usa Internet para realizar ventas?", caso.getVentaOnline(), instancia);
        setValueSafe("Sexo", caso.getSexo(), instancia);
        setValueSafe("Nivel de adicción al Internet", caso.getNivelAdiccion(), instancia);
        setValueSafe("Latencia de sueño", caso.getLatencia(), instancia);
        setValueSafe("¿Usa Internet para realizar compras?", caso.getComprasOnline(), instancia);

        double resultadoIdx = classifier.classifyInstance(instancia);
        return estructura.classAttribute().value((int) resultadoIdx);
    }

    private void setValueSafe(String nombre, String valor, Instance inst) {
        Attribute attr = estructura.attribute(nombre);
        if (attr != null && valor != null) {
            inst.setValue(attr, valor);
        }
    }
}