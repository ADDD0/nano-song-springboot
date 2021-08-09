package org.nano.song.service.originalSinger;

import org.nano.song.domain.repository.OriginalSingerRepository;
import org.nano.song.domain.repository.entity.OriginalSinger;
import org.nano.song.info.bean.originalSinger.QueryOriginalSingerBean;
import org.nano.song.info.bean.originalSinger.ReturnOriginalSingerBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

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
        ArrayList<OriginalSinger> originalSingerArrayList = new ArrayList<>();
        // 内部调用 不验证数据有效性
        if (songCollectionId != null && singerId == null) {
            originalSingerArrayList = queryOriginalSingerBySongCollectionId(songCollectionId);
        } else if (songCollectionId == null && singerId != null) {
            originalSingerArrayList = queryOriginalSingerBySingerId(singerId);
        } else {
            originalSingerArrayList.add(queryOriginalSingerBySongCollectionIdAndSingerId(songCollectionId, singerId));
        }
        returnOriginalSingerBean.setOriginalSingerArrayList(originalSingerArrayList);

        return returnOriginalSingerBean;
    }

    private ArrayList<OriginalSinger> queryOriginalSingerBySongCollectionId(Integer songCollectionId) {

        // 通过歌曲集合id查找所有原唱歌手
        return originalSingerRepository.findAllBySongCollectionId(songCollectionId).orElse(new ArrayList<>());
    }

    private ArrayList<OriginalSinger> queryOriginalSingerBySingerId(Integer singerId) {

        // 通过歌手id查找所有原唱歌手
        return originalSingerRepository.findAllBySingerId(singerId).orElse(new ArrayList<>());
    }

    private OriginalSinger queryOriginalSingerBySongCollectionIdAndSingerId(Integer songCollectionId, Integer singerId) {

        // 通过歌曲集合id和歌手id查找原唱歌手
        return originalSingerRepository.findBySongCollectionIdAndSingerId(songCollectionId, singerId).orElse(new OriginalSinger());
    }
}
