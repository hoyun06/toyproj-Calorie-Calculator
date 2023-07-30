package toyproj.CalorieCalculator.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import toyproj.CalorieCalculator.dto.FoodDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Getter @Setter
public class ApiService {

    private List<FoodDto> foodList = new ArrayList<>();

    public Page<FoodDto> foodPage(Pageable pageable) {

        // 요청한 페이지 번호
        int currentPage = pageable.getPageNumber();

        // 한 페이지에 몇 개의 엔트리씩 포함시킬 것인지
        int pageSize = pageable.getPageSize();

        // 요청한 페이지 번호와 한 페이지에 몇 개의 엔트리씩이 포함되는지를 바탕으로 foodList 의 몇 번째 index 부터 읽을 것인지 계산
        int index = currentPage * pageSize;

        List<FoodDto> subList;

        // 만약 계산한 index 가 foodList 의 크기보다 크다면 빈 리스트 반환
        // 그렇지 않은 경우 index + pageSize > foodList.size() - 1 조건의 결과에 따라서 가장 마지막 index 를 정한 뒤에
        // foodList 의 sublist 를 반환
        if(index >= foodList.size()) {
            subList = Collections.emptyList();
        }else {
            if(index + pageSize > foodList.size() - 1) {
                subList = foodList.subList(index, foodList.size());
            } else {
                subList = foodList.subList(index, index + pageSize);
            }
        }

        // Page 는 인터페이스이므로 PageImpl 형식으로 반환.
        // 첫 번째 인자는 content 로서 페이징 처리를 한 페이지에 표시할 내용을 의미
        // 두 번째 인자는 pageable 으로서 paging 처리를 함에 있어 필요한 정보를 의미
        // 마지막 인자는 total 으로서 총 데이터 개수(여기서는 foodList 안에 들어있는 데이터의 개수)를 의미
        return new PageImpl<FoodDto>(subList, PageRequest.of(currentPage, pageSize), foodList.size());
    }
}
