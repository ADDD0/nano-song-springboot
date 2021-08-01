package org.nano.song.controller;

import org.nano.song.domain.Constant;
import org.nano.song.domain.Enumerate.DELETE_FLAG;
import org.nano.song.domain.repository.CoverSingerRepository;
import org.nano.song.domain.repository.SingerRepository;
import org.nano.song.domain.repository.SongRepository;
import org.nano.song.domain.repository.entity.CoverSinger;
import org.nano.song.domain.repository.entity.Singer;
import org.nano.song.domain.repository.entity.Song;
import org.nano.song.handler.exception.ResourceNotFoundException;
import org.nano.song.info.request.song.QuerySongByPerformanceDateRequest;
import org.nano.song.info.request.song.QuerySongBySongCollectionIdRequest;
import org.nano.song.info.response.song.QuerySongResponse;
import org.nano.song.info.response.song.QuerySongResponseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

@CrossOrigin
@RequestMapping(Constant.URL_QUERY_SONG)
@RestController
@Transactional
public class QuerySongController {

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private SingerRepository singerRepository;

    @Autowired
    private CoverSingerRepository coverSingerRepository;

    @PostMapping(Constant.QUERY_CONTENT_PERFORMANCE_DATE)
    @ResponseBody
    public ResponseEntity<?> query(@RequestBody @Validated @NotEmpty QuerySongByPerformanceDateRequest request)
            throws ResourceNotFoundException {

        String performanceDate = request.getPerformanceDate();

        Date parsePerformanceDate;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
            parsePerformanceDate = simpleDateFormat.parse(performanceDate);
        } catch (ParseException e) {
            // 格式化弹唱日期失败 报400
            return new ResponseEntity<>(Constant.ERR_MSG_DATE_FOMAT_WRONG, HttpStatus.BAD_REQUEST);
        }
        // 查找歌曲
        ArrayList<Song> songArrayList = songRepository
                .findAllByPerformanceDateAndLogicalDeleteFlag(parsePerformanceDate, DELETE_FLAG.UNDELETED.getCode())
                .orElse(new ArrayList<>());

        ArrayList<QuerySongResponseResource> querySongResponseResourceArrayList = new ArrayList<>();
        for (Song song : songArrayList) {
            // 查找歌曲翻唱
            ArrayList<CoverSinger> coverSingerArrayList = coverSingerRepository
                    .findAllBySongId(song.getSongId())
                    .orElse(new ArrayList<>());

            ArrayList<Singer> singerArrayList = new ArrayList<>();
            for (CoverSinger coverSinger : coverSingerArrayList) {
                // 检查歌手是否存在 若不存在 报400
                Singer singer = singerRepository
                        .findBySingerIdAndLogicalDeleteFlag(coverSinger.getSingerId(), DELETE_FLAG.UNDELETED.getCode())
                        .orElseThrow(() -> new ResourceNotFoundException(Constant.SHOW_SINGER, "id=" + coverSinger.getSingerId()));
                singerArrayList.add(singer);
            }

            // 歌手数组排序
            singerArrayList.sort(Comparator.comparing(Singer::getSingerName));
            // 构建歌曲查询结果
            QuerySongResponseResource responseResource = new QuerySongResponseResource();
            responseResource.setSong(song);
            responseResource.setSingerArrayList(singerArrayList);
            // 插入歌曲查询结果
            querySongResponseResourceArrayList.add(responseResource);
        }
        QuerySongResponse response = new QuerySongResponse();
        // 歌曲查询结果按弹唱日期排序
        querySongResponseResourceArrayList.sort(Comparator.comparing(o -> o.getSong().getPerformanceDate()));

        response.setQuerySongResponseResourceArrayList(querySongResponseResourceArrayList);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(Constant.QUERY_CONTENT_SONG_COLLECTION_ID)
    @ResponseBody
    public ResponseEntity<?> query(@RequestBody @Validated @NotEmpty QuerySongBySongCollectionIdRequest request)
            throws ResourceNotFoundException {

        int songCollectionId = request.getSongCollectionId();

        // 查找歌曲
        ArrayList<Song> songArrayList = songRepository
                .findAllBySongCollectionIdAndLogicalDeleteFlag(songCollectionId, DELETE_FLAG.UNDELETED.getCode())
                .orElse(new ArrayList<>());

        ArrayList<QuerySongResponseResource> querySongResponseResourceArrayList = new ArrayList<>();
        for (Song song : songArrayList) {
            // 查找歌曲翻唱
            ArrayList<CoverSinger> coverSingerArrayList = coverSingerRepository.findAllBySongId(song.getSongId())
                    .orElse(new ArrayList<>());

            ArrayList<Singer> singerArrayList = new ArrayList<>();
            for (CoverSinger coverSinger : coverSingerArrayList) {
                // 检查歌手是否存在 若不存在 报400
                Singer singer = singerRepository.
                        findBySingerIdAndLogicalDeleteFlag(coverSinger.getSingerId(), DELETE_FLAG.UNDELETED.getCode())
                        .orElseThrow(() -> new ResourceNotFoundException(Constant.SHOW_SINGER, "id=" + coverSinger.getSingerId()));
                singerArrayList.add(singer);
            }

            // 歌手数组排序
            singerArrayList.sort(Comparator.comparing(Singer::getSingerName));
            // 构建歌曲查询结果
            QuerySongResponseResource responseResource = new QuerySongResponseResource();
            responseResource.setSong(song);
            responseResource.setSingerArrayList(singerArrayList);
            // 插入歌曲查询结果
            querySongResponseResourceArrayList.add(responseResource);
        }
        QuerySongResponse response = new QuerySongResponse();
        // 歌曲查询结果按弹唱日期排序
        querySongResponseResourceArrayList.sort(Comparator.comparing(o -> o.getSong().getPerformanceDate()));

        response.setQuerySongResponseResourceArrayList(querySongResponseResourceArrayList);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
