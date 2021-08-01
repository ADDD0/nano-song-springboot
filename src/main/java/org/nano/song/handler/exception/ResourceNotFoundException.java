package org.nano.song.handler.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 查找数据不存在
 */
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
