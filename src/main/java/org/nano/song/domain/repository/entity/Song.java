package org.nano.song.domain.repository.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * 歌曲实体类
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Song extends AuditEntity {

    private static final long serialVersionUID = 1L;
    // 歌曲id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer songId;
    // 歌名
    private String songName;
    // 路径
    private String path;
    // 歌曲集合id
    private Integer songCollectionId;
    // 弹唱日期
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date performanceDate;
    // 逻辑删除标志
    private Boolean logicalDeleteFlag;
}
