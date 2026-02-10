async function enviar() {
  const data = {
    edad: Number(document.getElementById("edad").value),
    ingresos: Number(document.getElementById("ingresos").value),
    antiguedadLaboral: Number(document.getElementById("antiguedad").value),
    tieneDeudas: document.getElementById("deudas").value === "true",
    historial: document.getElementById("historial").value
  };

  const res = await fetch("http://localhost:8080/api/credito/clasificar", {
    method: "POST",
    headers: {"Content-Type": "application/json"},
    body: JSON.stringify(data)
  });

  const json = await res.json();
  document.getElementById("resultado").innerText =
    "Resultado: " + json.resultado.toUpperCase();
}
