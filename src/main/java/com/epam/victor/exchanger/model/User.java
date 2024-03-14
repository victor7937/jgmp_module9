package com.epam.victor.exchanger.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;

    private String name;

    private List<Account> accounts;

}
