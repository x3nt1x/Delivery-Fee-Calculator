package com.x3nt1x.calculator.schedule;

import com.x3nt1x.calculator.entity.Weather;
import com.x3nt1x.calculator.repository.WeatherRepository;
import lombok.AllArgsConstructor;
import org.jdom2.input.SAXBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class RetrieveWeatherData
{
    private final WeatherRepository weatherRepository;

    /**
    * Retrieve weather data every 15 minutes after a full hour
    * Format: <second> <minute> <hour> <day-of-month> <month> <day-of-week> <year>
    */
    @Scheduled(cron = "0 15 * * * *")
    public void run()
    {
        var observe = new ArrayList<>(List.of("Tallinn-Harku", "Tartu-Tõravere", "Pärnu"));
        var url = "https://www.ilmateenistus.ee/ilma_andmed/xml/observations.php";

        try
        {
            var sax = new SAXBuilder();
            var document = sax.build(new URL(url));

            var root = document.getRootElement();
            var stations = root.getChildren("station");

            var timestamp = Long.parseLong(root.getAttributeValue("timestamp"));
            var dateTime = Instant.ofEpochSecond(timestamp).atZone(ZoneId.systemDefault()).toLocalDateTime();
            var time = dateTime.withMinute(0).withSecond(0).withNano(0);

            for (var station : stations)
            {
                var name = station.getChildText("name");

                if (!observe.contains(name))
                    continue;

                var city = name.split("-")[0].toLowerCase();
                var wmo = Integer.parseInt(station.getChildText("wmocode"));
                var wind = Double.parseDouble(station.getChildText("windspeed"));
                var phenomenon = station.getChildText("phenomenon");
                var temperature = Double.parseDouble(station.getChildText("airtemperature"));

                weatherRepository.save(new Weather(name, wmo, city, wind, temperature, phenomenon, time));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}