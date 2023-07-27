package toyproj.CalorieCalculator.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import toyproj.CalorieCalculator.dto.FoodDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api")
public class ApiController {

    @Value("http://openapi.foodsafetykorea.go.kr/api/" + "${API_KEY}" + "/I2790/json/1/10/DESC_KOR=")
    private String url;

    @GetMapping("/food")
    public String foodForm() {
        return "api/foodForm";
    }

    @PostMapping("/food")
    public String searchFood(@RequestParam String foodName, Model model) throws IOException {

        //Api 요청 url. 바뀌지 않는 base url 은 url 이라는 필드에 값을 저장해놓고
        // 사용자로부터 입력받은 음식 이름을 덧붙여 완전한 url 생성
        String completeUrl = url + foodName;
        
        // 실제 Url Object 생성
        URL apiUrl = new URL(completeUrl);

        // HTTP 프로토콜은 TCP 위에서 동작하는데 TCP 는 connection based 프로토콜임
        // 따라서, 통신을 하기 위해선 커넥션이 필요하므로 커넥션 설정.
        // 다만, 여기서 실제로 connection setup 이 되는 것은 아님. 여기서는 그저 URLConnection 인스턴스를
        // 새로 생성할 뿐이고 실제 setup 은 con.connect() 를 호출하거나 입/출력스트림을 가져올 때 암묵적으로 setup 됨
        HttpURLConnection con = (HttpURLConnection) apiUrl.openConnection();

        // HTTP 요청을 보낼때 어떤 method 형식인지 명시
        con.setRequestMethod("GET");

        // 해당 connection 의 input stream 을 반환하고 해당 input stream 을 통해서 데이터를 읽어들이기 위한 세팅
        // InputStreamReader 는 한번에 한 character 만 읽어오고 나머지는 계속해서 stream 에 남아있음
        // BufferedReader 는 한번에 여러 개의 character 를 읽어와 buffer 에 저장하므로 나머지가 stream 에 남아있지 않음
        BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

        StringBuilder sb = new StringBuilder();
        String line = null;

        // api 호출을 통해서 얻어온 데이터를 계속해서 StringBuilder 인스턴스에 이어붙임
        // 끝이나면 sb 에는 json 형식의 데이터가 들어있게 됨
        while((line = reader.readLine()) != null) {
            sb.append(line);
        }

        // Json serializing 라이브러리인 GSON 을 이용해서 String 형식이었던 Json 을
        // 실제 Json Object 로 변환한 뒤 원하는 필드만을 추출하여 JsonArray 형식으로 반환
        JsonObject jsonObject = JsonParser.parseString(sb.toString()).getAsJsonObject();
        JsonArray jsonArray = jsonObject.get("I2790").getAsJsonObject().get("row").getAsJsonArray();

        List<FoodDto> foodList = new ArrayList<>();

        // Json 안에 또 Json 형식의 배열로 들어있던 데이터를 JsonArray에 담고
        // 각 Json 데이터에서 내가 사용하고자 하는 데이터들만 추출하여
        // FoodDto 형식의 인스턴스로 생성후 foodList 에 추가
        for (JsonElement element : jsonArray) {
            JsonObject object = element.getAsJsonObject();
            foodList.add(new FoodDto(object.get("NUTR_CONT1").toString(),
                    object.get("DESC_KOR").toString(), object.get("FOOD_CD").toString(), object.get("SERVING_SIZE").toString()));
        }

        model.addAttribute("foods", foodList);
        return "api/foodList";
    }
}
