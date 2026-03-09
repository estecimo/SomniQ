async function enviar() {
  try {
    const selects = document.querySelectorAll('select');
    for (let select of selects) {
      if (select.value === "") {
        alert("Por favor, completa todos los campos del formulario.");
        select.focus();
        return;
      }
    }

    const data = {
      percepcion: document.getElementById("percepcion").value,
      frecuenciaMedicacion: document.getElementById("frecuenciaMedicacion").value,
      duracionSueno: document.getElementById("duracionSueno").value,
      somnolenciaDiurna: document.getElementById("somnolenciaDiurna").value,
      adiccionInternet: document.getElementById("adiccionInternet").value,
      ventaOnline: document.getElementById("ventaOnline").value,
      comprasOnline: document.getElementById("comprasOnline").value,
      sexo: document.getElementById("sexo").value,
      nivelAdiccion: document.getElementById("nivelAdiccion").value, 
      latencia: document.getElementById("latencia").value           
    };

    const res = await fetch("http://localhost:8080/api/calidad-sueno/clasificar", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(data)
    });

    if (!res.ok) throw new Error("Error 500: El modelo no reconoció un valor.");

    const dto = await res.json();
    const resultadoElement = document.getElementById("resultado");
    resultadoElement.innerText = "La predicción es: " + dto.resultado;
    resultadoElement.style.color = dto.resultado === "Buena" ? "#28a745" : "#d9534f";

    document.getElementById("explicacion-container").style.display = "block";

  } catch (error) {
    console.error("Error:", error);
    document.getElementById("resultado").innerText = "Error: " + error.message;
  }
}

async function pedirExplicacion() {
    const btn = document.getElementById("btn-explicar");
    const loading = document.getElementById("loading-explicacion");
    const texto = document.getElementById("texto-explicacion");

    btn.style.display = "none";
    loading.style.display = "block";
    texto.style.display = "none";

    const resultadoTexto = document.getElementById("resultado").innerText.replace("La predicción es: ", "");

    const data = {
        percepcion: document.getElementById("percepcion").value,
        frecuenciaMedicacion: document.getElementById("frecuenciaMedicacion").value,
        duracionSueno: document.getElementById("duracionSueno").value,
        somnolenciaDiurna: document.getElementById("somnolenciaDiurna").value,
        adiccionInternet: document.getElementById("adiccionInternet").value,
        ventaOnline: document.getElementById("ventaOnline").value,
        comprasOnline: document.getElementById("comprasOnline").value,
        sexo: document.getElementById("sexo").value,
        nivelAdiccion: document.getElementById("nivelAdiccion").value, 
        latencia: document.getElementById("latencia").value,
        resultado: resultadoTexto // Importante para Gemini
    };

    try {
        const res = await fetch("http://localhost:8080/api/calidad-sueno/explicar", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data)
        });

        if (!res.ok) throw new Error("No se pudo obtener la explicación.");

        const result = await res.json();
        texto.innerText = result.explicacion;
        texto.style.display = "block";
    } catch (error) {
        console.error("Error en Gemini:", error);
        texto.innerText = "Hubo un problema al generar la explicación. Intente de nuevo.";
        texto.style.display = "block";
        btn.style.display = "inline-block";
    } finally {
        loading.style.display = "none";
    }
}