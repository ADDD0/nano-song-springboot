package org.nano.song.domain.repository;

import org.nano.song.domain.Constant;
import org.nano.song.domain.repository.entity.SongCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Optional;

@Table(name = Constant.TABLE_SONG_COLLECTION)
@Repository
public interface SongCollectionRepository extends JpaRepository<SongCollection, Integer> {

    /**
     * 根据 songCollectionId 查找歌曲集合（精确查询）
     *
     * @param songCollectionId  歌曲集合id
     * @param logicalDeleteFlag 逻辑删除标志
     * @return 歌曲集合
     */
    Optional<SongCollection> findBySongCollectionIdAndLogicalDeleteFlag(Integer songCollectionId, Boolean logicalDeleteFlag);

    /**
     * 根据 songTitle 查找歌曲集合（精确查询）
     *
     * @param songTitle         歌曲标题
     * @param logicalDeleteFlag 逻辑删除标志
     * @return 歌曲集合
     */
    Optional<SongCollection> findBySongTitleAndLogicalDeleteFlag(String songTitle, Boolean logicalDeleteFlag);

    /**
     * 根据 songTitle 查找所有歌曲集合（模糊查询）
     *
     * @param songTitle         歌曲标题
     * @param logicalDeleteFlag 逻辑删除标志
     * @return 歌曲集合列表
     */
    Optional<ArrayList<SongCollection>> findAllBySongTitleContainingAndLogicalDeleteFlag(String songTitle, Boolean logicalDeleteFlag);
}
