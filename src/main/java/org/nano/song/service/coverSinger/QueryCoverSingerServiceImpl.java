package org.nano.song.service.coverSinger;

import org.nano.song.domain.repository.CoverSingerRepository;
import org.nano.song.domain.repository.entity.CoverSinger;
import org.nano.song.info.bean.coverSinger.QueryCoverSingerBean;
import org.nano.song.info.bean.coverSinger.ReturnCoverSingerBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@Transactional
public class QueryCoverSingerServiceImpl implements QueryCoverSingerService {

    @Autowired
    private CoverSingerRepository coverSingerRepository;

    @Override
    public ReturnCoverSingerBean queryCoverSinger(QueryCoverSingerBean queryCoverSingerBean) {

        Integer songId = queryCoverSingerBean.getSongId();
        Integer singerId = queryCoverSingerBean.getSingerId();

        ReturnCoverSingerBean returnCoverSingerBean = new ReturnCoverSingerBean();
        ArrayList<CoverSinger> coverSingerArrayList = new ArrayList<>();
        // 内部调用 不验证数据有效性
        if (songId != null && singerId == null) {
            coverSingerArrayList = queryCoverSingerBySongId(songId);
        } else if (songId == null && singerId != null) {
            coverSingerArrayList = queryCoverSingerBySingerId(singerId);
        } else {
            coverSingerArrayList.add(queryCoverSingerBySongIdAndSingerId(songId, singerId));
        }
        returnCoverSingerBean.setCoverSingerArrayList(coverSingerArrayList);

        return returnCoverSingerBean;
    }

    private ArrayList<CoverSinger> queryCoverSingerBySongId(Integer songId) {

        // 通过歌曲id查找所有翻唱歌手
        return coverSingerRepository.findAllBySongId(songId).orElse(new ArrayList<>());
    }

    private ArrayList<CoverSinger> queryCoverSingerBySingerId(Integer singerId) {

        // 通过歌手id查找所有翻唱歌手
        return coverSingerRepository.findAllBySingerId(singerId).orElse(new ArrayList<>());
    }

    private CoverSinger queryCoverSingerBySongIdAndSingerId(Integer songId, Integer singerId) {

        // 通过歌曲id和歌手id查找原唱歌手
        return coverSingerRepository.findBySongIdAndSingerId(songId, singerId).orElse(new CoverSinger());
    }
}
