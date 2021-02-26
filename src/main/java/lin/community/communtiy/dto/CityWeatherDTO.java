package lin.community.communtiy.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CityWeatherDTO {
    private String info;
    private List<Lives> lives = new ArrayList<>();
}
