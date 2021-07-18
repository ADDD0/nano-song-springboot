package org.nano.song.domain.repository;

import org.nano.song.domain.Constant;
import org.nano.song.domain.repository.entity.Singer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Optional;

@Table(name = Constant.TABLE_SINGER)
@Repository
public interface SingerRepository extends JpaRepository<Singer, Integer> {

    /**
     * 根据 singerId 查找歌手（精确查询）
     *
     * @param singerId          歌手id
     * @param logicalDeleteFlag 逻辑删除标志
     * @return 歌手
     */
    Optional<Singer> findBySingerIdAndLogicalDeleteFlag(Integer singerId, Boolean logicalDeleteFlag);

    /**
     * 根据 singerName 查找歌手（精确查询）
     *
     * @param singerName        歌手姓名
     * @param logicalDeleteFlag 逻辑删除标志
     * @return 歌手
     */
    Optional<Singer> findBySingerNameAndLogicalDeleteFlag(String singerName, Boolean logicalDeleteFlag);

    /**
     * 根据 singerName 查找歌手（模糊查询）
     *
     * @param singerName        歌手姓名
     * @param logicalDeleteFlag 逻辑删除标志
     * @return 歌手列表
     */
    Optional<ArrayList<Singer>> findAllBySingerNameContainingAndLogicalDeleteFlag(String singerName, Boolean logicalDeleteFlag);
}
