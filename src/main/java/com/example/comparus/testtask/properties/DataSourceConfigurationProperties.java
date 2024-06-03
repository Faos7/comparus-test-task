package com.example.comparus.testtask.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DataSourceConfigurationProperties {
    private String name;
    private String strategy;
    private String url;
    private String table;
    private String user;
    private String password;
    private ColumnMapping mapping;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class ColumnMapping {
        private String id;
        private String username;
        private String name;
        private String surname;
    }
}