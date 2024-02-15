package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class Main {

    static ObjectMapper objectMapper = new ObjectMapper();
    static OkHttpClient client = new OkHttpClient();
    public static WeatherResponse getWeatherData(String city) {

        try {
            var urlLink = HttpUrl.parse("http://api.weatherapi.com/v1/current.json")
                    .newBuilder()
                    .addQueryParameter("key", "[Your API key]")
                    .addQueryParameter("q", city)
                    .build()
                    .toString();

            var request = new Request.Builder()
                    .url(urlLink)
                    .build();
            var response = client.newCall(request).execute();

            return objectMapper.readValue(response.body().string(), WeatherResponse.class);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void sendTelegramMessage(String message) {
        String botToken = "[Your bot Token]";
        String chatId = "[Your Chat ID]";

        try {
            String encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8);

            //Eroarea aparea pentru ca spatiile goale din mesaj nu erau inlocuite cu %20
            //Am rezolvat eroarea prin URLEncoder
            String urlString = String.format("https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s", botToken, chatId, encodedMessage);

            var request = new Request.Builder()
                    .url(urlString)
                    .build();

            client.newCall(request).execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        WeatherResponse weatherData = getWeatherData("Bucuresti");
        if(weatherData == null){
            System.out.println("A intervenit o eroare");
            sendTelegramMessage("A intervenit o eroare");
            return;
        }
        String message = String.format("Temperatura astazi in %s o sa fie de %s grade Celsius.",
                weatherData.getLocation().getName(),
                weatherData.getCurrent().getTemp_c());

        sendTelegramMessage(message);
    }
}