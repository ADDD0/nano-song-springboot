package org.nano.song.domain.repository.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 翻唱歌手实体类
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class CoverSinger extends AuditEntity {

    private static final long serialVersionUID = 1L;
    // 翻唱歌手关系id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer coverSingerRelationId;
    // 歌曲id
    private Integer songId;
    // 歌手id
    private Integer singerId;
}
