package org.nano.song.service.coverSinger;

import org.nano.song.domain.repository.CoverSingerRepository;
import org.nano.song.domain.repository.entity.CoverSinger;
import org.nano.song.info.bean.coverSinger.QueryCoverSingerBean;
import org.nano.song.info.bean.coverSinger.ReturnCoverSingerBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 查询翻唱歌手服务接口实现类
 */
@Service
@Transactional
public class QueryCoverSingerServiceImpl implements QueryCoverSingerService {

    @Autowired
    private CoverSingerRepository coverSingerRepository;

    /**
     * 查询翻唱歌手
     *
     * @param queryCoverSingerBean 查询翻唱歌手参数
     * @return 返回翻唱歌手参数
     */
    @Override
    public ReturnCoverSingerBean queryCoverSinger(QueryCoverSingerBean queryCoverSingerBean) {

        Integer songId = queryCoverSingerBean.getSongId();
        Integer singerId = queryCoverSingerBean.getSingerId();

        ReturnCoverSingerBean returnCoverSingerBean = new ReturnCoverSingerBean();
        List<CoverSinger> coverSingerList = new ArrayList<>();
        // 内部调用 不验证数据有效性
        if (songId != null && singerId == null) {
            coverSingerList = queryCoverSingerBySongId(songId);
        } else if (songId == null && singerId != null) {
            coverSingerList = queryCoverSingerBySingerId(singerId);
        } else {
            coverSingerList.add(queryCoverSingerBySongIdAndSingerId(songId, singerId));
        }
        returnCoverSingerBean.setCoverSingerList(coverSingerList);

        return returnCoverSingerBean;
    }

    /**
     * 通过歌曲id查询所有翻唱歌手
     *
     * @param songId 歌曲id
     * @return 翻唱歌手列表
     */
    private List<CoverSinger> queryCoverSingerBySongId(Integer songId) {

        return coverSingerRepository.findAllBySongId(songId).orElse(Collections.emptyList());
    }

    /**
     * 通过歌手id查询所有翻唱歌手
     *
     * @param singerId 歌手id
     * @return 翻唱歌手列表
     */
    private List<CoverSinger> queryCoverSingerBySingerId(Integer singerId) {

        return coverSingerRepository.findAllBySingerId(singerId).orElse(Collections.emptyList());
    }

    /**
     * 通过歌曲id和歌手id查询翻唱歌手
     *
     * @param songId   歌曲id
     * @param singerId 歌手id
     * @return 翻唱歌手
     */
    private CoverSinger queryCoverSingerBySongIdAndSingerId(Integer songId, Integer singerId) {

        return coverSingerRepository.findBySongIdAndSingerId(songId, singerId).orElse(new CoverSinger());
    }
}
