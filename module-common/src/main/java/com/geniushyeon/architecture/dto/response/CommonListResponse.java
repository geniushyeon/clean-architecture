package com.geniushyeon.architecture.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CommonListResponse<T> extends CommonResponse<T> {

    @Builder.Default
    private int total = 0;

    @Builder.Default
    private boolean hasNext = false;

    @Builder.Default
    private List<T> data = new ArrayList<>();

    public CommonListResponse(HttpStatus status) {
        super(status);
    }

    public CommonListResponse<T> add(int total, boolean hasNext, List<T> data) {
        this.total = total;
        this.hasNext = hasNext;
        this.data.addAll(data);

        return this;
    }
}
