package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
public class WeatherResponse {
    private Location location;
    private Current current;
}
