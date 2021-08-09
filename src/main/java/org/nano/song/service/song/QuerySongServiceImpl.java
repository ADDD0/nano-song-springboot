package org.nano.song.service.song;

import org.nano.song.domain.Constant;
import org.nano.song.domain.Enumerate.DELETE_FLAG;
import org.nano.song.domain.repository.SingerRepository;
import org.nano.song.domain.repository.SongRepository;
import org.nano.song.domain.repository.entity.CoverSinger;
import org.nano.song.domain.repository.entity.Singer;
import org.nano.song.domain.repository.entity.Song;
import org.nano.song.handler.exception.ResourceNotFoundException;
import org.nano.song.info.bean.coverSinger.QueryCoverSingerBean;
import org.nano.song.info.bean.coverSinger.ReturnCoverSingerBean;
import org.nano.song.info.bean.song.QuerySongBean;
import org.nano.song.info.bean.song.ReturnSongBean;
import org.nano.song.info.response.song.QuerySongResponse;
import org.nano.song.service.coverSinger.QueryCoverSingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

@Service
@Transactional
public class QuerySongServiceImpl implements QuerySongService {

    @Autowired
    private SingerRepository singerRepository;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private QueryCoverSingerService queryCoverSingerService;

    @Override
    public QuerySongResponse querySongBySongCollectionId(QuerySongBean querySongBean) throws ResourceNotFoundException {

        Integer songCollectionId = querySongBean.getSongCollectionId();
        // 通过歌曲集合id查找所有歌曲
        ArrayList<Song> songArrayList = songRepository.findAllBySongCollectionIdAndLogicalDeleteFlag(songCollectionId, DELETE_FLAG.UNDELETED.getCode())
                .orElse(new ArrayList<>());

        ArrayList<ReturnSongBean> returnSongBeanArrayList = new ArrayList<>();
        for (Song song : songArrayList) {
            QueryCoverSingerBean queryCoverSingerBean = new QueryCoverSingerBean();
            queryCoverSingerBean.setSongId(song.getSongId());
            // 查询翻唱歌手
            ReturnCoverSingerBean returnCoverSingerBean = queryCoverSingerService.queryCoverSinger(queryCoverSingerBean);
            ArrayList<CoverSinger> coverSingerArrayList = returnCoverSingerBean.getCoverSingerArrayList();

            ArrayList<Singer> singerArrayList = new ArrayList<>();
            for (CoverSinger coverSinger : coverSingerArrayList) {
                // 通过歌手id查找歌手
                Singer singer = singerRepository.findBySingerIdAndLogicalDeleteFlag(coverSinger.getSingerId(), DELETE_FLAG.UNDELETED.getCode())
                        .orElseThrow(() -> new ResourceNotFoundException(Constant.SHOW_SINGER, "id=" + coverSinger.getSingerId()));
                singerArrayList.add(singer);
            }
            // 翻唱歌手排序
            singerArrayList.sort(Comparator.comparing(Singer::getSingerName));

            ReturnSongBean returnSongBean = new ReturnSongBean();
            returnSongBean.setSong(song);
            returnSongBean.setSingerArrayList(singerArrayList);
            // 插入当前歌曲查询结果
            returnSongBeanArrayList.add(returnSongBean);
        }
        // 歌曲查询结果按弹唱日期排序
        returnSongBeanArrayList.sort(Comparator.comparing(o -> o.getSong().getPerformanceDate()));

        QuerySongResponse querySongResponse = new QuerySongResponse();
        querySongResponse.setReturnSongBeanArrayList(returnSongBeanArrayList);

        return querySongResponse;
    }

    @Override
    public QuerySongResponse querySongByPerformanceDate(QuerySongBean querySongBean) throws ResourceNotFoundException, ParseException {

        String performanceDate = querySongBean.getPerformanceDate();

        Date parsePerformanceDate;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
            parsePerformanceDate = simpleDateFormat.parse(performanceDate);
        } catch (ParseException e) {
            // 格式化弹唱日期失败 报400
            throw new ParseException(performanceDate, e.getErrorOffset());
        }
        // 通过弹唱日期查找所有歌曲
        ArrayList<Song> songArrayList = songRepository.findAllByPerformanceDateAndLogicalDeleteFlag(parsePerformanceDate, DELETE_FLAG.UNDELETED.getCode())
                .orElse(new ArrayList<>());

        ArrayList<ReturnSongBean> returnSongBeanArrayList = new ArrayList<>();
        for (Song song : songArrayList) {
            QueryCoverSingerBean queryCoverSingerBean = new QueryCoverSingerBean();
            queryCoverSingerBean.setSongId(song.getSongId());
            // 查询翻唱歌手
            ReturnCoverSingerBean returnCoverSingerBean = queryCoverSingerService.queryCoverSinger(queryCoverSingerBean);
            ArrayList<CoverSinger> coverSingerArrayList = returnCoverSingerBean.getCoverSingerArrayList();

            ArrayList<Singer> singerArrayList = new ArrayList<>();
            for (CoverSinger coverSinger : coverSingerArrayList) {
                // 通过歌手id查找歌手
                Singer singer = singerRepository.findBySingerIdAndLogicalDeleteFlag(coverSinger.getSingerId(), DELETE_FLAG.UNDELETED.getCode())
                        .orElseThrow(() -> new ResourceNotFoundException(Constant.SHOW_SINGER, "id=" + coverSinger.getSingerId()));
                singerArrayList.add(singer);
            }
            // 翻唱歌手排序
            singerArrayList.sort(Comparator.comparing(Singer::getSingerName));

            ReturnSongBean returnSongBean = new ReturnSongBean();
            returnSongBean.setSong(song);
            returnSongBean.setSingerArrayList(singerArrayList);
            // 插入当前歌曲查询结果
            returnSongBeanArrayList.add(returnSongBean);
        }
        // 歌曲查询结果按弹唱日期排序
        returnSongBeanArrayList.sort(Comparator.comparing(o -> o.getSong().getPerformanceDate()));

        QuerySongResponse querySongResponse = new QuerySongResponse();
        querySongResponse.setReturnSongBeanArrayList(returnSongBeanArrayList);

        return querySongResponse;
    }
}
