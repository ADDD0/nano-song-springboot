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
public class SongCollection extends AuditEntity {

    private static final long serialVersionUID = 1L;
    // 歌曲集合id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer songCollectionId;
    // 歌曲标题
    private String songTitle;
    // 中文标题
    private String chineseTitle;
    // 英文标题
    private String englishTitle;
    // 逻辑删除标志
    private Boolean logicalDeleteFlag;
}
