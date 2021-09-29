package org.nano.song.domain.repository;

import org.nano.song.domain.Constant;
import org.nano.song.domain.repository.entity.CoverSinger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Table;
import java.util.List;
import java.util.Optional;

/**
 * 翻唱歌手仓储库类
 */
@Table(name = Constant.TABLE_COVER_SINGER)
@Repository
public interface CoverSingerRepository extends JpaRepository<CoverSinger, Integer> {

    /**
     * 根据歌曲id和歌手id查询翻唱歌手（精确查询）
     *
     * @param songId   歌曲id
     * @param singerId 歌手id
     * @return 翻唱歌手
     */
    Optional<CoverSinger> findBySongIdAndSingerId(Integer songId, Integer singerId);

    /**
     * 根据歌曲id查询所有翻唱歌手（精确查询）
     *
     * @param songId 歌曲id
     * @return 翻唱歌手列表
     */
    Optional<List<CoverSinger>> findAllBySongId(Integer songId);

    /**
     * 根据歌手id查询所有翻唱歌手（精确查询）
     *
     * @param singerId 歌手id
     * @return 翻唱歌手列表
     */
    Optional<List<CoverSinger>> findAllBySingerId(Integer singerId);
}
