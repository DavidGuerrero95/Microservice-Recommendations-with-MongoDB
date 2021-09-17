package com.appcity.app.recomendaciones.services;

import java.util.List;

public interface IRecomendacionMath {

	public Double distanciaCoord(List<Double> pos1, List<Double> pos2);
	
	public List<Double> distanciaMedia(List<Double> pos1, List<Double> pos2);
}
