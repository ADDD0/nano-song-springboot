package org.nano.song.domain.repository;

import org.nano.song.domain.Constant;
import org.nano.song.domain.repository.entity.OriginalSinger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Table;
import java.util.List;
import java.util.Optional;

@Table(name = Constant.TABLE_ORIGINAL_SINGER)
@Repository
public interface OriginalSingerRepository extends JpaRepository<OriginalSinger, Integer> {

    /**
     * 根据 songCollectionId 和 singerId 查找原唱歌手（精确查询）
     *
     * @param songCollectionId 歌曲集合id
     * @param singerId         歌手id
     * @return 原唱歌手
     */
    Optional<OriginalSinger> findBySongCollectionIdAndSingerId(Integer songCollectionId, Integer singerId);

    /**
     * 根据 songCollectionId 查找所有原唱歌手（精确查询）
     *
     * @param songCollectionId 歌曲集合id
     * @return 原唱歌手列表
     */
    Optional<List<OriginalSinger>> findAllBySongCollectionId(Integer songCollectionId);

    /**
     * 根据 singerId 查找所有原唱歌手（精确查询）
     *
     * @param singerId 歌手id
     * @return 原唱歌手列表
     */
    Optional<List<OriginalSinger>> findAllBySingerId(Integer singerId);
}
