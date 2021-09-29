package org.nano.song.domain.repository;

import org.nano.song.domain.Constant;
import org.nano.song.domain.repository.entity.OriginalSinger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Table;
import java.util.List;
import java.util.Optional;

/**
 * 原唱歌手仓储库类
 */
@Table(name = Constant.TABLE_ORIGINAL_SINGER)
@Repository
public interface OriginalSingerRepository extends JpaRepository<OriginalSinger, Integer> {

    /**
     * 根据歌曲集合id和歌手id查询原唱歌手（精确查询）
     *
     * @param songCollectionId 歌曲集合id
     * @param singerId         歌手id
     * @return 原唱歌手
     */
    Optional<OriginalSinger> findBySongCollectionIdAndSingerId(Integer songCollectionId, Integer singerId);

    /**
     * 根据歌曲集合id查询所有原唱歌手（精确查询）
     *
     * @param songCollectionId 歌曲集合id
     * @return 原唱歌手列表
     */
    Optional<List<OriginalSinger>> findAllBySongCollectionId(Integer songCollectionId);

    /**
     * 根据歌手id查询所有原唱歌手（精确查询）
     *
     * @param singerId 歌手id
     * @return 原唱歌手列表
     */
    Optional<List<OriginalSinger>> findAllBySingerId(Integer singerId);
}
