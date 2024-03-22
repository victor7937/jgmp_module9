package com.epam.victor.exchanger.model;

import com.epam.victor.exchanger.repository.json.deserializer.AccountListDeserializer;
import com.epam.victor.exchanger.repository.json.serializer.AccountListSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private String id;

    private String name;

    @JsonSerialize(using = AccountListSerializer.class)
    @JsonDeserialize(using = AccountListDeserializer.class)
    private List<Account> accounts = new ArrayList<>();

    public User(String name) {
        this.name = name;
    }
}
