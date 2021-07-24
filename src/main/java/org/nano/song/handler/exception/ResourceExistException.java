package org.nano.song.handler.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ResourceExistException extends Exception {

    private String tableName;
    private String tableField;

    public ResourceExistException(String tableName, String tableField) {
        this.tableName = tableName;
        this.tableField = tableField;
    }
}
