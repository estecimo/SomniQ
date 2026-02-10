package com.example.credito.infrastructure.weka;

import com.example.credito.domain.CreditoCaso;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import weka.classifiers.Classifier;
import weka.core.*;

import java.io.InputStream;
import java.util.List;
import java.util.ArrayList;


@Service
public class WekaModeloCreditoService {

    private final Classifier classifier;
    private final Instances estructura;

    public WekaModeloCreditoService() throws Exception {

        InputStream is = new ClassPathResource("modelo_credito.model").getInputStream();
        classifier = (Classifier) SerializationHelper.read(is);

        ArrayList<Attribute> atributos = new ArrayList<>();
        atributos.add(new Attribute("edad"));
        atributos.add(new Attribute("ingresos"));
        atributos.add(new Attribute("antiguedadLaboral"));
        atributos.add(new Attribute("tieneDeudas", List.of("false", "true")));
        atributos.add(new Attribute("historial", List.of("malo", "regular", "bueno")));
        atributos.add(new Attribute("clase", List.of("rechazado", "aprobado")));

        estructura = new Instances("Credito", atributos, 1);
        estructura.setClassIndex(atributos.size() - 1);
    }

    public String clasificar(CreditoCaso caso) throws Exception {

        DenseInstance instancia = new DenseInstance(estructura.numAttributes());
        instancia.setValue(estructura.attribute("edad"), caso.getEdad());
        instancia.setValue(estructura.attribute("ingresos"), caso.getIngresos());
        instancia.setValue(estructura.attribute("antiguedadLaboral"), caso.getAntiguedadLaboral());
        instancia.setValue(estructura.attribute("tieneDeudas"), String.valueOf(caso.isTieneDeudas()));
        instancia.setValue(estructura.attribute("historial"), caso.getHistorial());

        estructura.add(instancia);

        double resultado = classifier.classifyInstance(estructura.firstInstance());
        return estructura.classAttribute().value((int) resultado);
    }
}
