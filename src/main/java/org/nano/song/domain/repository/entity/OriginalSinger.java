package org.nano.song.domain.repository.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class OriginalSinger extends AuditEntity {

    private static final long serialVersionUID = 1L;
    // 原唱歌手关系id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer originalSingerRelationId;
    // 歌曲集合id
    private Integer songCollectionId;
    // 歌手id
    private Integer singerId;
}
