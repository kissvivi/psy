package com.kk.api.dto;

import com.kk.api.entity.Question;
import com.kk.api.entity.Tests;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestsDto {

    private Question question;

    private List<Tests> testsList;
}
