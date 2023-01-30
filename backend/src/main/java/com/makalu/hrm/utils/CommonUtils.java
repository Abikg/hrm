package com.makalu.hrm.utils;

import com.makalu.hrm.model.PageInfo;
import lombok.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class CommonUtils {

    public static Pageable pageBuilder(Integer page, Integer limit) {
        if (page == null || page < 1) {
            page = 1;
        }

        if (limit == null || limit < 1 || limit > 500) {
            limit = 100;
        }

        page = page - 1;

        return PageRequest.of(page, limit);
    }

    public static PageInfo pageInfoBuilder(Integer page, Integer limit) {
        if (page == null || page < 1) {
            page = 1;
        }

        if (limit == null || limit < 1 || limit > 500) {
            limit = 100;
        }

        page = page - 1;

        return new PageInfo().pageNumber(page).limit(limit);
    }


}
