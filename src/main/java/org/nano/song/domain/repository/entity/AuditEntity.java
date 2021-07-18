package org.nano.song.domain.repository.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

@Data
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class AuditEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    // 创建时间
    @CreatedDate
    private Date createdDate;
    // 最后修改时间
    @LastModifiedDate
    private Date lastModifiedDate;
}
