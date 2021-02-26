package lin.community.communtiy.controller;

import lin.community.communtiy.dto.CityWeatherDTO;
import lin.community.communtiy.provider.CityWeatherProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchCityWeatherController {
    @Autowired
    private CityWeatherProvider cityWeatherProvider;

    @GetMapping("/searchWeather")
    public String searchWeather(
            Model model,
            @RequestParam(name = "search", required = false) String search)//required=false不传值 不报错
    {
        CityWeatherDTO cityWeahter = cityWeatherProvider.getCityWeahter(search);
        model.addAttribute("cityWeather",cityWeahter);
        return "cityweather";
    }
}
