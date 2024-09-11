package com.fthon.subsclife.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PagedItem<T> {

    private List<T> items;

    // 다음 페이지가 존재하는지 여부
    private Boolean hasNext;

}
