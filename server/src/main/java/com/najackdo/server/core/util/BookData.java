package com.najackdo.server.core.util;


import jdk.jfr.TransitionFrom;
import lombok.*;

import java.time.LocalDate;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Builder
@AllArgsConstructor(access = PROTECTED)
@NoArgsConstructor
@Setter
public class BookData {
    private long isbn; //
    private String description; //
    private String categoryName; //
    private String title; //
    private String author; //
    private String cover; //
    private LocalDate pubDate; //
    private int priceStandard; //
    private int itemPage; //
    private double starPoint;
    private String publisher; //
    private String adult; //


    @Override
    public String toString() {
        return isbn+"|"+description+"|"+categoryName+"|"+title+"|"+author+"|"+cover+"|"+pubDate+"|"+priceStandard+"|"+itemPage+"|"+starPoint+"|"+publisher+"|"+adult;
    }
}
