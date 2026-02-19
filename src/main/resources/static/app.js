async function enviar() {
  const data = {
    percepcionCalidad: document.getElementById("percepcion").value,
    duracionSueno: document.getElementById("duracion").value,
    latencia: document.getElementById("latencia").value,
    covid: document.getElementById("covid").value
  };

  // Validation
  if (!data.percepcionCalidad || !data.duracionSueno || !data.latencia || !data.covid) {
    alert("Por favor, responde todas las preguntas antes de evaluar.");
    return;
  }

  // Reset UI elements
  document.getElementById("explicacion-container").style.display = "none";
  document.getElementById("texto-explicacion").innerText = "";
  document.getElementById("texto-explicacion").style.display = "none";
  document.getElementById("btn-explicar").style.display = "inline-block";
  document.getElementById("btn-explicar").disabled = false;

  try {
    const res = await fetch("/api/calidad-sueno/evaluar", {
      method: "POST",
      headers: {"Content-Type": "application/json"},
      body: JSON.stringify(data)
    });

    if (!res.ok) throw new Error("Error en la petición");

    const json = await res.json();
    const resultadoElement = document.getElementById("resultado");
    
    // Resultado limpio
    resultadoElement.innerText = "Resultado: " + json.resultado.toUpperCase();
    
    // Simple color coding
    if (json.resultado.toUpperCase() === "BUENA") {
        resultadoElement.style.color = "green";
    } else {
        resultadoElement.style.color = "red";
    }

    // Show explanation button
    document.getElementById("explicacion-container").style.display = "block";

    // Store current data for explanation context
    window.lastEvaluationData = { ...data, resultado: json.resultado };

  } catch (err) {
    console.error(err);
    document.getElementById("resultado").innerText = "Error al evaluar";
  }
}

async function pedirExplicacion() {
  const btn = document.getElementById("btn-explicar");
  const loading = document.getElementById("loading-explicacion");
  const explanationText = document.getElementById("texto-explicacion");
  
  // Disable button to prevent multiple clicks
  btn.disabled = true;

  // Hide button and show loading
  btn.style.display = "none";
  loading.style.display = "block";

  const data = window.lastEvaluationData;
  if (!data) {
    alert("No hay datos de evaluación disponibles.");
    loading.style.display = "none";
    btn.style.display = "inline-block";
    btn.disabled = false;
    return;
  }

  // Construct payload for backend
  const payload = {
    percepcionCalidad: data.percepcionCalidad,
    duracionSueno: data.duracionSueno,
    latencia: data.latencia,
    covid: data.covid,
    resultado: data.resultado
  };

  try {
    const response = await fetch("/api/calidad-sueno/explicacion", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload)
    });

    if (!response.ok) {
        throw new Error("Error en el servidor: " + response.statusText);
    }

    const result = await response.json();
    loading.style.display = "none";

    if (result.explicacion && !result.explicacion.startsWith("Error")) {
        explanationText.innerText = result.explicacion;
        explanationText.style.display = "block";
    } else {
        throw new Error(result.explicacion || "No se pudo obtener la explicación.");
    }
    
  } catch (error) {
    console.error("Error fetching explanation:", error);
    loading.style.display = "none";
    alert("Ocurrió un error al obtener la explicación. Inténtalo de nuevo.");
    // Allow trying again if error
    btn.style.display = "inline-block";
    btn.disabled = false;
  }
}
