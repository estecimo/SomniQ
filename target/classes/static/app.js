async function enviar() {
  const data = {
    percepcionCalidad: document.getElementById("percepcion").value,
    duracionSueno: document.getElementById("duracion").value,
    latencia: document.getElementById("latencia").value,
    covid: document.getElementById("covid").value
  };

  try {
    const res = await fetch("/api/calidad-sueno/evaluar", {
      method: "POST",
      headers: {"Content-Type": "application/json"},
      body: JSON.stringify(data)
    });

    if (!res.ok) throw new Error("Error en la petición");

    const json = await res.json();
    const resultadoElement = document.getElementById("resultado");
    
    resultadoElement.innerText = "Resultado: " + json.resultado.toUpperCase();
    
    // Simple color coding
    if (json.resultado.toUpperCase() === "BUENA") {
        resultadoElement.style.color = "green";
    } else {
        resultadoElement.style.color = "red";
    }

  } catch (err) {
    console.error(err);
    document.getElementById("resultado").innerText = "Error al evaluar";
  }
}
