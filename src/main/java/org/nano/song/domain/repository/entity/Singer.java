package org.nano.song.domain.repository.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 歌手实体类
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Singer extends AuditEntity {

    private static final long serialVersionUID = 1L;
    // 歌手id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer singerId;
    // 歌手姓名
    private String singerName;
    // 逻辑删除标志
    private Boolean logicalDeleteFlag;
}
