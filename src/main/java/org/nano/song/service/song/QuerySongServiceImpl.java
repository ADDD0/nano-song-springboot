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
import org.nano.song.info.response.song.QuerySongApiResponse;
import org.nano.song.service.coverSinger.QueryCoverSingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 查询歌曲服务接口实现类
 */
@Service
@Transactional
public class QuerySongServiceImpl implements QuerySongService {

    @Autowired
    private SingerRepository singerRepository;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private QueryCoverSingerService queryCoverSingerService;

    /**
     * 通过歌曲集合id查询歌曲
     *
     * @param querySongBean 查询歌曲参数
     * @return 查询歌曲响应
     * @throws ResourceNotFoundException 资源未找到
     */
    @Override
    public QuerySongApiResponse querySongBySongCollectionId(QuerySongBean querySongBean) throws ResourceNotFoundException {

        Integer songCollectionId = querySongBean.getSongCollectionId();
        // 通过歌曲集合id查询所有歌曲
        List<Song> songList = songRepository.findAllBySongCollectionIdAndLogicalDeleteFlag(songCollectionId, DELETE_FLAG.UNDELETED.getCode())
                .orElse(Collections.emptyList());

        List<ReturnSongBean> returnSongBeanList = new ArrayList<>();
        for (Song song : songList) {
            QueryCoverSingerBean queryCoverSingerBean = new QueryCoverSingerBean();
            queryCoverSingerBean.setSongId(song.getSongId());
            // 查询翻唱歌手
            ReturnCoverSingerBean returnCoverSingerBean = queryCoverSingerService.queryCoverSinger(queryCoverSingerBean);
            List<CoverSinger> coverSingerList = returnCoverSingerBean.getCoverSingerList();

            List<Singer> singerList = new ArrayList<>();
            for (CoverSinger coverSinger : coverSingerList) {
                // 通过歌手id查询歌手
                Singer singer = singerRepository.findBySingerIdAndLogicalDeleteFlag(coverSinger.getSingerId(), DELETE_FLAG.UNDELETED.getCode())
                        .orElseThrow(() -> new ResourceNotFoundException(Constant.SHOW_SINGER, "id=" + coverSinger.getSingerId()));
                singerList.add(singer);
            }
            // 翻唱歌手排序
            singerList.sort(Comparator.comparing(Singer::getSingerName));

            ReturnSongBean returnSongBean = new ReturnSongBean();
            returnSongBean.setSong(song);
            returnSongBean.setSingerList(singerList);
            // 插入当前歌曲查询结果
            returnSongBeanList.add(returnSongBean);
        }
        // 歌曲查询结果按弹唱日期排序
        returnSongBeanList.sort(Comparator.comparing(o -> o.getSong().getPerformanceDate()));

        QuerySongApiResponse querySongApiResponse = new QuerySongApiResponse();
        querySongApiResponse.setReturnSongBeanList(returnSongBeanList);

        return querySongApiResponse;
    }

    /**
     * 通过弹唱日期查询歌曲
     *
     * @param querySongBean 查询歌曲参数
     * @return 查询歌曲响应
     * @throws ResourceNotFoundException 资源未找到
     * @throws ParseException            格式转换错误
     */
    @Override
    public QuerySongApiResponse querySongByPerformanceDate(QuerySongBean querySongBean) throws ResourceNotFoundException, ParseException {

        String performanceDate = querySongBean.getPerformanceDate();

        Date parsePerformanceDate;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
            parsePerformanceDate = simpleDateFormat.parse(performanceDate);
        } catch (ParseException e) {
            // 格式化弹唱日期失败 报400
            throw new ParseException(performanceDate, e.getErrorOffset());
        }
        // 通过弹唱日期查询所有歌曲
        List<Song> songList = songRepository.findAllByPerformanceDateAndLogicalDeleteFlag(parsePerformanceDate, DELETE_FLAG.UNDELETED.getCode())
                .orElse(Collections.emptyList());

        List<ReturnSongBean> returnSongBeanList = new ArrayList<>();
        for (Song song : songList) {
            QueryCoverSingerBean queryCoverSingerBean = new QueryCoverSingerBean();
            queryCoverSingerBean.setSongId(song.getSongId());
            // 查询翻唱歌手
            ReturnCoverSingerBean returnCoverSingerBean = queryCoverSingerService.queryCoverSinger(queryCoverSingerBean);
            List<CoverSinger> coverSingerList = returnCoverSingerBean.getCoverSingerList();

            List<Singer> singerList = new ArrayList<>();
            for (CoverSinger coverSinger : coverSingerList) {
                // 通过歌手id查询歌手
                Singer singer = singerRepository.findBySingerIdAndLogicalDeleteFlag(coverSinger.getSingerId(), DELETE_FLAG.UNDELETED.getCode())
                        .orElseThrow(() -> new ResourceNotFoundException(Constant.SHOW_SINGER, "id=" + coverSinger.getSingerId()));
                singerList.add(singer);
            }
            // 翻唱歌手排序
            singerList.sort(Comparator.comparing(Singer::getSingerName));

            ReturnSongBean returnSongBean = new ReturnSongBean();
            returnSongBean.setSong(song);
            returnSongBean.setSingerList(singerList);
            // 插入当前歌曲查询结果
            returnSongBeanList.add(returnSongBean);
        }
        // 歌曲查询结果按弹唱日期排序
        returnSongBeanList.sort(Comparator.comparing(o -> o.getSong().getPerformanceDate()));

        QuerySongApiResponse querySongApiResponse = new QuerySongApiResponse();
        querySongApiResponse.setReturnSongBeanList(returnSongBeanList);

        return querySongApiResponse;
    }
}
