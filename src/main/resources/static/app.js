async function enviar() {
  try {
    // Captura de datos sincronizada con los IDs del HTML
    const data = {
      percepcionSubjetiva: document.getElementById("percepcion").value,
      frecuenciaMedicacion: document.getElementById("frecuenciaMedicacion").value,
      duracionSueno: document.getElementById("duracionSueno").value,
      somnolenciaDiurna: document.getElementById("somnolencia").value,
      adiccionInternet: document.getElementById("adiccionInternet").value,
      ventaOnline: document.getElementById("ventaOnline").value,
      comprasOnline: document.getElementById("comprasOnline").value,
      sexo: document.getElementById("sexo").value,
      nivelAdicInternet: document.getElementById("nivelAdiccion").value, 
      latenciaSueno: document.getElementById("latencia").value           
    };

    console.log("Enviando datos:", data);

    const res = await fetch("http://localhost:8080/api/calidad-sueno/clasificar", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(data)
    });

    if (!res.ok) throw new Error("Error en la respuesta del servidor");

    const dto = await res.json();
    
    // resultado en el div correspondiente
    document.getElementById("resultado").innerText = "La predicción es: " + dto.resultado;

  } catch (error) {
    console.error("Error:", error);
    document.getElementById("resultado").innerText = "Error: Compruebe la conexión con el backend.";
  }
}