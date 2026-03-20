package com.example.calidadsueno.infrastructure.weka;

import com.example.calidadsueno.domain.CasoCalidadSueno;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;

import java.io.InputStream;

@Service
public class WekaModeloCalidadSueno {

    private Classifier classifier;
    private Instances header;

    @PostConstruct
    public void init() throws Exception {
        try (InputStream is = getClass().getResourceAsStream("/Ciencias exactas_Unpruned model.model")) {
            if (is == null) {
                throw new IllegalStateException("No se encontró el archivo del modelo en resources");
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

        if (this.header != null) {
            Attribute classAttr = this.header.attribute("Calidad de sueño");
            if (classAttr != null) {
                this.header.setClass(classAttr);
            }
        }
    }

    public String evaluar(CasoCalidadSueno caso) {
        if (classifier == null || header == null) {
            throw new IllegalStateException("El modelo o el header no se cargaron correctamente.");
        }

        Instance instance = new DenseInstance(header.numAttributes());
        instance.setDataset(header);

        // --- MAPEO DE ATRIBUTOS CLAVE --- 
        assignValue(instance, "¿Cómo percibe su calidad de sueño?", caso.getPercepcion());
        assignValue(instance, "Frecuencia de consumo de medicamentos para dormir", caso.getFrecuenciaMedicacion());
        assignValue(instance, "Duración total de sueño", caso.getDuracionSueno());
        assignValue(instance, "Somnolencia diurna", caso.getSomnolenciaDiurna());
        assignValue(instance, "Latencia de sueño", caso.getLatencia());
        assignValue(instance, "¿Tiene adicción al Internet?", caso.getAdiccionInternet());
        assignValue(instance, "Nivel de adicción al Internet", caso.getNivelAdiccion());
        assignValue(instance, "¿Usa Internet para realizar compras?", caso.getComprasOnline());
        assignValue(instance, "¿Usa Internet para realizar ventas?", caso.getVentaOnline());
        assignValue(instance, "Sexo", caso.getSexo());

        try {
            double prediction = classifier.classifyInstance(instance);
            return header.classAttribute().value((int) prediction); // Retorna "Buena" o "Mala" 
        } catch (Exception e) {
            throw new RuntimeException("Error en la clasificación del modelo J48", e);
        }
    }

    private void assignValue(Instance inst, String attrName, String value) {
        Attribute attr = header.attribute(attrName);
        if (attr != null && value != null && !value.trim().isEmpty()) {
            // Es vital que 'value' coincida exactamente con las etiquetas del modelo 
            inst.setValue(attr, value);
        }
    }
}