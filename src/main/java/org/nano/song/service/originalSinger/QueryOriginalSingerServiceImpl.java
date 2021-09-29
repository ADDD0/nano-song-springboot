package org.nano.song.service.originalSinger;

import org.nano.song.domain.repository.OriginalSingerRepository;
import org.nano.song.domain.repository.entity.OriginalSinger;
import org.nano.song.info.bean.originalSinger.QueryOriginalSingerBean;
import org.nano.song.info.bean.originalSinger.ReturnOriginalSingerBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 查询原唱歌手服务接口实现类
 */
@Service
@Transactional
public class QueryOriginalSingerServiceImpl implements QueryOriginalSingerService {

    @Autowired
    private OriginalSingerRepository originalSingerRepository;

    /**
     * 查询原唱歌手
     *
     * @param queryOriginalSingerBean 查询原唱歌手参数
     * @return 返回原唱歌手参数
     */
    @Override
    public ReturnOriginalSingerBean queryOriginalSinger(QueryOriginalSingerBean queryOriginalSingerBean) {

        Integer songCollectionId = queryOriginalSingerBean.getSongCollectionId();
        Integer singerId = queryOriginalSingerBean.getSingerId();

        ReturnOriginalSingerBean returnOriginalSingerBean = new ReturnOriginalSingerBean();
        List<OriginalSinger> originalSingerList = new ArrayList<>();
        // 内部调用 不验证数据有效性
        if (songCollectionId != null && singerId == null) {
            originalSingerList = queryOriginalSingerBySongCollectionId(songCollectionId);
        } else if (songCollectionId == null && singerId != null) {
            originalSingerList = queryOriginalSingerBySingerId(singerId);
        } else {
            originalSingerList.add(queryOriginalSingerBySongCollectionIdAndSingerId(songCollectionId, singerId));
        }
        returnOriginalSingerBean.setOriginalSingerList(originalSingerList);

        return returnOriginalSingerBean;
    }

    /**
     * 通过歌曲集合id查询所有原唱歌手
     *
     * @param songCollectionId 歌曲集合id
     * @return 原唱歌手列表
     */
    private List<OriginalSinger> queryOriginalSingerBySongCollectionId(Integer songCollectionId) {

        return originalSingerRepository.findAllBySongCollectionId(songCollectionId).orElse(Collections.emptyList());
    }

    /**
     * 通过歌手id查询所有原唱歌手
     *
     * @param singerId 歌手id
     * @return 原唱歌手列表
     */
    private List<OriginalSinger> queryOriginalSingerBySingerId(Integer singerId) {

        return originalSingerRepository.findAllBySingerId(singerId).orElse(Collections.emptyList());
    }

    /**
     * 通过歌曲集合id和歌手id查询原唱歌手
     *
     * @param songCollectionId 歌曲集合id
     * @param singerId         歌手id
     * @return 原唱歌手
     */
    private OriginalSinger queryOriginalSingerBySongCollectionIdAndSingerId(Integer songCollectionId, Integer singerId) {

        return originalSingerRepository.findBySongCollectionIdAndSingerId(songCollectionId, singerId).orElse(new OriginalSinger());
    }
}
