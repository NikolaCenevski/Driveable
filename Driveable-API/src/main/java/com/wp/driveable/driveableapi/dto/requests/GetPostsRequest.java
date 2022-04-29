package com.wp.driveable.driveableapi.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetPostsRequest {
    List<String> carTypes;
    Boolean isNew;
    String model;
    String manufacturer;
    Integer priceFrom;
    Integer priceTo;
    Integer yearFrom;
    Integer yearTo;
    String sortBy;
}
