package com.example.calidadsueno.infrastructure;

import com.example.calidadsueno.domain.CasoCalidadSueno;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import weka.classifiers.Classifier;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;

import java.io.InputStream;

@Service
public class ServicioEvaluacionJ48 {

    private Classifier classifier;
    private Instances header;

    @PostConstruct
    public void init() throws Exception {
        try (InputStream is = getClass().getResourceAsStream("/cienciasdelasalud.model")) {
            if (is == null) {
                throw new IllegalStateException("Model file not found in resources");
            }
            Object[] modelObj = SerializationHelper.readAll(is);
            for (Object obj : modelObj) {
                if (obj instanceof Instances) {
                    this.header = (Instances) obj;
                } else if (obj instanceof Classifier) {
                    this.classifier = (Classifier) obj;
                }
            }
        }
        if (this.header != null && this.header.classIndex() == -1) {
            weka.core.Attribute classAttribute = this.header.attribute("Calidad de sueño");
            if (classAttribute != null) {
                this.header.setClass(classAttribute);
            } else {
                this.header.setClassIndex(this.header.numAttributes() - 1);
            }
        }
    }

    public String evaluar(CasoCalidadSueno caso) {
        if (classifier == null || header == null) {
            throw new IllegalStateException("El modelo no está cargado.");
        }

        Instance instance = new DenseInstance(header.numAttributes());
        instance.setDataset(header);

        // Map values to Weka representations exactly
        weka.core.Attribute attrPercepcion = header.attribute("¿Cómo percibe su calidad de sueño?");
        if (attrPercepcion != null && isNotNullOrEmpty(caso.getPercepcionCalidad())) {
            instance.setValue(attrPercepcion, mapPercepcion(caso.getPercepcionCalidad()));
        }

        weka.core.Attribute attrDuracion = header.attribute("Clasificación de duración total de sueño");
        if (attrDuracion != null && isNotNullOrEmpty(caso.getDuracionSueno())) {
            instance.setValue(attrDuracion, mapDuracion(caso.getDuracionSueno()));
        }

        weka.core.Attribute attrLatencia = header.attribute("Clasificación de latencia");
        if (attrLatencia != null && isNotNullOrEmpty(caso.getLatencia())) {
            instance.setValue(attrLatencia, mapLatencia(caso.getLatencia()));
        }

        weka.core.Attribute attrCovid = header.attribute("¿Tuvo COVID-19 en los sesis meses previos a la evaluación?");
        if (attrCovid != null && isNotNullOrEmpty(caso.getCovid())) {
            instance.setValue(attrCovid, mapCovid(caso.getCovid()));
        }

        try {
            double prediction = classifier.classifyInstance(instance);
            return header.classAttribute().value((int) prediction);
        } catch (Exception e) {
            throw new RuntimeException("Error al clasificar la instancia", e);
        }
    }

    private boolean isNotNullOrEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    private String mapPercepcion(String value) {
        if ("Buena".equalsIgnoreCase(value)) return "Buena";
        return "Mala";
    }

    private String mapDuracion(String value) {
        if ("Corta".equalsIgnoreCase(value)) return "Corta (< 6 horas)";
        if ("Larga".equalsIgnoreCase(value)) return "Larga (> 9 horas)";
        return "Normal (6-9 horas)";
    }

    private String mapLatencia(String value) {
        if ("Prolongada".equalsIgnoreCase(value)) return "Prolongada (> 15 minutos)";
        if ("Patologica".equalsIgnoreCase(value) || "Patológica".equalsIgnoreCase(value)) return "Patológica (< 5 minutos)";
        return "Normal (5-15 minutos)";
    }

    private String mapCovid(String value) {
        if ("Si".equalsIgnoreCase(value) || "Sí".equalsIgnoreCase(value)) return "Sí";
        return "No";
    }
}
