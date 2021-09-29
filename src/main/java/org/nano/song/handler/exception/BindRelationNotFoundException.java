package org.nano.song.handler.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 绑定关系不存在异常
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BindRelationNotFoundException extends Exception {

    private String tableName1;
    private Object tableField1;
    private String tableName2;
    private Object tableField2;

    public BindRelationNotFoundException(String tableName1, Object tableField1, String tableName2, Object tableField2) {
        this.tableName1 = tableName1;
        this.tableField1 = tableField1;
        this.tableName2 = tableName2;
        this.tableField2 = tableField2;
    }
}
