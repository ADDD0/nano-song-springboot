package org.nano.song.handler.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BindRelationExistException extends Exception {

    private String tableName1;
    private String tableField1;
    private String tableName2;
    private String tableField2;

    public BindRelationExistException(String tableName1, String tableField1, String tableName2, String tableField2) {
        this.tableName1 = tableName1;
        this.tableField1 = tableField1;
        this.tableName2 = tableName2;
        this.tableField2 = tableField2;
    }
}
