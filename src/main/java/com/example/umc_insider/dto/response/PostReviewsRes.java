package com.example.umc_insider.dto.response;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostReviewsRes {
    private String content;
    private Integer point;

}
