let datosFormulario = {};
let resultadoPrediccion = "";

function obtenerDatos() {
    return {
        sexo: document.getElementById("sexo").value,
        percepcion: document.getElementById("percepcion").value,
        frecuenciaMedicacion: document.getElementById("frecuenciaMedicacion").value,
        duracionSueno: document.getElementById("duracionSueno").value,
        somnolenciaDiurna: document.getElementById("somnolenciaDiurna").value,
        latencia: document.getElementById("latencia").value,
        adiccionInternet: document.getElementById("adiccionInternet").value,
        nivelAdiccion: document.getElementById("nivelAdiccion").value,
        ventaOnline: document.getElementById("ventaOnline").value,
        comprasOnline: document.getElementById("comprasOnline").value
    };
}

function resetearEstadoExplicacion() {
    // Asegurar que el contenedor padre sea visible
    const container = document.getElementById("explicacion-container");
    container.style.display = "block"; 

    // Habilitar el botón y limpiar lo demás
    document.getElementById("btn-explicar").style.display = "inline-block";
    document.getElementById("loading-explicacion").style.display = "none";
    document.getElementById("texto-explicacion").style.display = "none";
    document.getElementById("texto-explicacion").innerText = "";
}

async function enviar() {
    const divError = document.getElementById("mensaje-error");
    const selects = document.querySelectorAll('select');
    
    divError.style.display = "none";
    selects.forEach(s => s.style.borderColor = "#ccc");

    try {
        for (let select of selects) {
            if (select.value === "") {
                divError.style.display = "block";
                select.style.borderColor = "#d9534f";
                select.focus();
                return;
            }
        }

        datosFormulario = obtenerDatos();

        const res = await fetch("/api/calidad-sueno/clasificar", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(datosFormulario)
        });

        console.log("Datos enviados al servidor:", datosFormulario);
        console.log("Respuesta del servidor:", res);

        if (!res.ok) throw new Error("Error en el servidor al clasificar.");

        const dto = await res.json();
        const resultadoPrediccion = dto.resultado; // Valor puro ("Buena" o "Mala")

        // Obtener todos los elementos del DOM a modificar
        const resElement = document.getElementById("resultado");
        const imgContainer = document.getElementById("resultado-imagen-container");
        const imgElement = document.getElementById("resultado-imagen");

        // Actualizar el texto del resultado
        resElement.innerText = `La predicción es: ${resultadoPrediccion}`;

        // Evaluar si es "Buena" de forma segura
        const esBuena = resultadoPrediccion.toUpperCase() === "BUENA";

        // Asignar el color y la imagen dinámicamente
        resElement.style.color = esBuena ? "#28a745" : "#d9534f";
        imgElement.src = esBuena ? "images/geminigoodspleep.webp" : "images/geminibadsleep.webp";

        // Hacer visible el contenedor de la imagen
        imgContainer.style.display = "block";

        resetearEstadoExplicacion();

    } catch (error) {
        console.error("Error:", error);
        document.getElementById("resultado").innerText = "Hubo un problema: " + error.message;
        document.getElementById("resultado").style.color = "#d9534f";
    }
}

async function pedirExplicacion() {
    const btn = document.getElementById("btn-explicar");
    const loading = document.getElementById("loading-explicacion");
    const texto = document.getElementById("texto-explicacion");

    btn.style.display = "none";
    loading.style.display = "block";

    try {
        // Combinamos los datos del formulario con el resultado obtenido
        const bodyExplicacion = { ...datosFormulario, resultado: resultadoPrediccion };

        const res = await fetch("/api/calidad-sueno/explicar", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(bodyExplicacion)
        });

        if (!res.ok) throw new Error("No se pudo obtener la explicación.");

        const result = await res.json();
        texto.innerText = result.explicacion;
        texto.style.display = "block";
    } catch (error) {
        console.error("Error en Gemini:", error);
        texto.innerText = "No se pudo generar la explicación.";
        texto.style.display = "block";
        btn.style.display = "inline-block";
    } finally {
        loading.style.display = "none";
    }
}

// Ocultar mensaje de error y restaurar borde al cambiar cualquier select
const todosLosSelects = document.querySelectorAll('select');

todosLosSelects.forEach(select => {
    select.addEventListener('change', () => {
        const divError = document.getElementById("mensaje-error");
        
        if (divError.style.display === "block") {
            divError.style.display = "none";
        }
        
        select.style.borderColor = "#ccc";
    });
});