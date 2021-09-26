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

@Service
@Transactional
public class QueryOriginalSingerServiceImpl implements QueryOriginalSingerService {

    @Autowired
    private OriginalSingerRepository originalSingerRepository;

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

    private List<OriginalSinger> queryOriginalSingerBySongCollectionId(Integer songCollectionId) {

        // 通过歌曲集合id查找所有原唱歌手
        return originalSingerRepository.findAllBySongCollectionId(songCollectionId).orElse(Collections.emptyList());
    }

    private List<OriginalSinger> queryOriginalSingerBySingerId(Integer singerId) {

        // 通过歌手id查找所有原唱歌手
        return originalSingerRepository.findAllBySingerId(singerId).orElse(Collections.emptyList());
    }

    private OriginalSinger queryOriginalSingerBySongCollectionIdAndSingerId(Integer songCollectionId, Integer singerId) {

        // 通过歌曲集合id和歌手id查找原唱歌手
        return originalSingerRepository.findBySongCollectionIdAndSingerId(songCollectionId, singerId).orElse(new OriginalSinger());
    }
}
