package com.revature.services;

import java.util.List;
import java.util.concurrent.ExecutionException;

import com.revature.beans.User;

public interface RideRecommendationsService {
	public List<User> getRideRecommendations(User user, int numRecommendations);
	public double calculateKilometersBetweenPoints(double lat1, double long1, double lat2, double long2);
	public double roadKilometersBetweenPoints(double lat1, double long1, double lat2, double long2) throws ExecutionException;
}
