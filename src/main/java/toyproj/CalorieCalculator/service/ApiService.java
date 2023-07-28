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
        int currentPage = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        int index = currentPage * pageSize;

        List<FoodDto> subList;

        if(index >= foodList.size()) {
            subList = Collections.emptyList();
        }else {
            if(index + pageSize > foodList.size() - 1) {
                subList = foodList.subList(index, foodList.size());
            } else {
                subList = foodList.subList(index, index + pageSize);
            }
        }

        return new PageImpl<FoodDto>(subList, PageRequest.of(currentPage, pageSize), foodList.size());
    }
}
