package org.nano.song.domain.repository;

import org.nano.song.domain.Constant;
import org.nano.song.domain.repository.entity.SongCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Table;
import java.util.List;
import java.util.Optional;

/**
 * 歌曲集合仓储库类
 */
@Table(name = Constant.TABLE_SONG_COLLECTION)
@Repository
public interface SongCollectionRepository extends JpaRepository<SongCollection, Integer> {

    /**
     * 根据歌曲集合id查询歌曲集合（精确查询）
     *
     * @param songCollectionId  歌曲集合id
     * @param logicalDeleteFlag 逻辑删除标志
     * @return 歌曲集合
     */
    Optional<SongCollection> findBySongCollectionIdAndLogicalDeleteFlag(Integer songCollectionId, Boolean logicalDeleteFlag);

    /**
     * 根据歌曲标题查询歌曲集合（精确查询）
     *
     * @param songTitle         歌曲标题
     * @param logicalDeleteFlag 逻辑删除标志
     * @return 歌曲集合
     */
    Optional<SongCollection> findBySongTitleAndLogicalDeleteFlag(String songTitle, Boolean logicalDeleteFlag);

    /**
     * 根据歌曲标题查询所有歌曲集合（模糊查询）
     *
     * @param songTitle         歌曲标题
     * @param logicalDeleteFlag 逻辑删除标志
     * @return 歌曲集合列表
     */
    Optional<List<SongCollection>> findAllBySongTitleContainingAndLogicalDeleteFlag(String songTitle, Boolean logicalDeleteFlag);
}
