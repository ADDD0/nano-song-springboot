package org.nano.song.controller.handler.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ResourceNotFoundException extends Exception {

    private String tableName;
    private String tableField;

    public ResourceNotFoundException(String tableName, String tableField) {
        this.tableName = tableName;
        this.tableField = tableField;
    }
}
