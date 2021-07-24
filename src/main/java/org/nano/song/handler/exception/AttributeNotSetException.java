package org.nano.song.handler.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AttributeNotSetException extends Exception {

    private String tableName;
    private String tableField;
    private String message;

    public AttributeNotSetException(String tableName, String tableField, String message) {
        this.tableName = tableName;
        this.tableField = tableField;
        this.message = message;
    }
}
