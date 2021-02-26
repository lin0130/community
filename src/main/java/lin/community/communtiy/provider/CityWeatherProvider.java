package lin.community.communtiy.provider;

import com.alibaba.fastjson.JSON;
import lin.community.communtiy.dto.CityWeatherDTO;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class CityWeatherProvider {
    @Value("${gaode.key}")//如果类被new了实例而没有Autowired的话value是注入不进来的
    private String gaode_key;

    public CityWeatherDTO getCityWeahter(String cityAdecode)
    {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://restapi.amap.com/v3/weather/weatherInfo?city="+cityAdecode+"&key="+gaode_key)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            CityWeatherDTO cityWeatherDTO = JSON.parseObject(string, CityWeatherDTO.class);
            return cityWeatherDTO;
        } catch (IOException e) {
            e.printStackTrace();
            return  null;
        }
    }
}
