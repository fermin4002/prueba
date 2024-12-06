package modelo;

import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.opencsv.bean.CsvDate;

public class AnalizadorCreador {
	
	private JsonNode creador;
	
	public AnalizadorCreador() {
		
		this.creador=null;
		
	}

	public AnalizadorCreador(JsonNode creador) {
		
		this.creador = creador;
	}

	public JsonNode getCreador() {
		return creador;
	}

	public void setCreador(JsonNode creador) {
		this.creador = creador;
	}

	@Override
	public String toString() {
		return "AnalizadorCreador [creador=" + creador + "]";
	}
	
	
	//Metodos Propios
	//Extraer Campos
	
	public int getId() {
		int salida=creador.get("id").asInt();
		return salida;
	}
	
	public String getNombre() {
		String salida=creador.get("nombre").asText();
		return salida;
	}
	
	
	public String getPais() {
		String salida=creador.get("pais").asText();
		return salida;
	}
	
	public String getTematica() {
		String salida=creador.get("tematica").asText();
		return salida;
	}
	
	public int getSeguidores_totales() {
		int salida=creador.get("seguidores_totales").asInt();
		return salida;
	}
	
	public JsonNode getEstadisticas() {
		JsonNode salida=null;
		salida=creador.get("estadisticas");
		
		return salida;
	}

	public int getInteraccionesTotales( ) {
		
		JsonNode nodo=getEstadisticas();
		int salida;
		salida=nodo.get("interacciones_totales").asInt();
		
		return salida;
		
	}
	
	public int getPromedioVistasMensuales( ) {
		
		JsonNode nodo=getEstadisticas();
		int salida;
		salida=nodo.get("promedio_vistas_mensuales").asInt();
		
		return salida;
		
	}

	public int getTasaCrecimientoSeguidores( ) {
	
	JsonNode nodo=getEstadisticas();
	int salida;
	salida=nodo.get("tasa_crecimiento_seguidores").asInt();
	
	return salida;
	
	}
	
	
	public ArrayNode getColaboraciones() {
		ArrayNode colaboracionesSalida=null;
		colaboracionesSalida=(ArrayNode) creador.get("colaboraciones");
		return colaboracionesSalida;
	}
	
	public void conversionColaboraciones(ArrayNode colaboracionesJson, List<Colaboracion> colaboracionesCsv) {
		int id=getId();
		String creador=getNombre();
		String colaborador;
		String fecha;
		int vistas_mensuales=getPromedioVistasMensuales();
		int crecimiento=getTasaCrecimientoSeguidores();
		
		for(JsonNode clave:colaboracionesJson) {
			colaborador=clave.get("colaborador").asText();
			fecha=clave.get("fecha_inicio").asText();
			
			colaboracionesCsv.add(new Colaboracion(id,fecha,creador,colaborador,vistas_mensuales,crecimiento));
		}
		
		
	}
	
	
	public ArrayList<String> extraerRedes(){
		ArrayList<String> redes=new ArrayList<String>();
		
		for(JsonNode clave: creador.get("plataformas")) {
			redes.add(clave.get("nombre").asText());
		}
		
		return redes;
	}
	
	public JsonNode extraerPlataforma(String plataforma) {
		JsonNode plata=null;
		
		for(JsonNode clave: creador.get("plataformas")) {
			if(clave.get("nombre").asText().equalsIgnoreCase(plataforma)) {
				plata=clave;
			}
		}
		
		
		return plata;
	}
	
	public String getUsurioPlataforma(String plataforma) {
		
		JsonNode temp=extraerPlataforma(plataforma);
		
		return temp.get("usuario").asText();
	}
	
	public int getSeguidoresPlataforma(String plataforma) {
		
		JsonNode temp=extraerPlataforma(plataforma);
		
		return temp.get("seguidores").asInt();
	}

	public String getFechaCreacionPlataforma(String plataforma) {
	
		JsonNode temp=extraerPlataforma(plataforma);
	
		return temp.get("fecha_creacion").asText();
	}
	
	public JsonNode extraerHistoricoConcreto(String plataforma,String fecha) {
		
		JsonNode temp=extraerPlataforma(plataforma);
		JsonNode salida=null;
		for(JsonNode clave:temp.get("historico")) {
			if(fecha.equalsIgnoreCase(clave.get("fecha").asText())) {
				salida=clave;
			}
		}
		return salida;
	}
	
	
	public int getNuevosseguidores(String plataforma,String fecha) {
		
		JsonNode temp=extraerHistoricoConcreto(plataforma,fecha);
		
		return temp.get("nuevos_seguidores").asInt();
	}
	
	public int getInteraciones(String plataforma,String fecha) {
		
		JsonNode temp=extraerHistoricoConcreto(plataforma,fecha);
		
		return temp.get("interacciones").asInt();
	}
	
	public ArrayList<String> extraerHistorico(String plataforma){
		ArrayList<String> historico=new ArrayList<String>();
		
		for(JsonNode clave:extraerPlataforma(plataforma).get("historico")) {
			historico.add(clave.get("fecha").asText());
		}
		
		return historico;
	}
	
	public ArrayNode extraerColaboraciones(){
			
		return (ArrayNode) creador.get("colaboraciones");
		
	}
	
}
