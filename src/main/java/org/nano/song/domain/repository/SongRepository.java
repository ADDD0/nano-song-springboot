package org.nano.song.domain.repository;

import org.nano.song.domain.Constant;
import org.nano.song.domain.repository.entity.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Table;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 歌曲仓储库类
 */
@Table(name = Constant.TABLE_SONG)
@Repository
public interface SongRepository extends JpaRepository<Song, Integer> {

    /**
     * 根据歌曲id查询歌曲（精确查询）
     *
     * @param songId            歌曲id
     * @param logicalDeleteFlag 逻辑删除标志
     * @return 歌曲
     */
    Optional<Song> findBySongIdAndLogicalDeleteFlag(Integer songId, Boolean logicalDeleteFlag);

    /**
     * 根据弹唱日期查询所有歌曲（精确查询）
     *
     * @param performanceDate   弹唱日期
     * @param logicalDeleteFlag 逻辑删除标志
     * @return 歌曲列表
     */
    Optional<List<Song>> findAllByPerformanceDateAndLogicalDeleteFlag(Date performanceDate, Boolean logicalDeleteFlag);

    /**
     * 根据歌曲集合id查询所有歌曲（精确查询）
     *
     * @param songCollectionId  歌曲集合id
     * @param logicalDeleteFlag 逻辑删除标志
     * @return 歌曲列表
     */
    Optional<List<Song>> findAllBySongCollectionIdAndLogicalDeleteFlag(Integer songCollectionId, Boolean logicalDeleteFlag);
}
