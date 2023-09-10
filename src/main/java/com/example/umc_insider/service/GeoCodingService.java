package com.example.umc_insider.service;

import com.example.umc_insider.dto.response.GetLatLngRes;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
//     우편주소로 위도 경도
public class GeoCodingService {
    private String googleMapsApiKey;

    @Value("${google.maps.api.key}")
    public void setGoogleMapsApiKey(String googleMapsApiKey) {
        this.googleMapsApiKey = googleMapsApiKey;
    }


    public GetLatLngRes getLatLngByAddress(String address) {
        try {
            String a = "대한민국 " + address;
            GeoApiContext context = new GeoApiContext.Builder()
                    .apiKey(googleMapsApiKey)
                    .build();

            GeocodingResult[] results = GeocodingApi.geocode(context, a)
                    .region("kr")
                    .await();

            if (results.length > 0) {
                GeocodingResult result = results[0];
                double latitude = result.geometry.location.lat;
                double longitude = result.geometry.location.lng;
                return new GetLatLngRes(latitude, longitude);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



}
