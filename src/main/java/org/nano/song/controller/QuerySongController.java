package org.nano.song.controller;

import org.nano.song.domain.Constant;
import org.nano.song.handler.exception.ResourceNotFoundException;
import org.nano.song.info.bean.song.QuerySongBean;
import org.nano.song.info.request.song.QuerySongByPerformanceDateRequest;
import org.nano.song.info.request.song.QuerySongBySongCollectionIdRequest;
import org.nano.song.info.response.song.QuerySongApiResponse;
import org.nano.song.service.song.QuerySongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.text.ParseException;

/**
 * 查询歌曲相关控制器类
 */
@CrossOrigin
@RequestMapping(Constant.URL_QUERY_SONG)
@RestController
public class QuerySongController {

    @Autowired
    private QuerySongService querySongService;

    /**
     * 通过弹唱日期查询歌曲及相关信息
     *
     * @param request 查询请求
     * @return 歌曲及相关信息
     * @throws ResourceNotFoundException 资源未找到
     * @throws ParseException            格式转换错误
     */
    @PostMapping(Constant.QUERY_CONTENT_PERFORMANCE_DATE)
    @ResponseBody
    public ResponseEntity<QuerySongApiResponse> query(@RequestBody @Validated @NotEmpty QuerySongByPerformanceDateRequest request)
            throws ResourceNotFoundException, ParseException {

        QuerySongBean querySongBean = new QuerySongBean();
        querySongBean.setPerformanceDate(request.getPerformanceDate());
        querySongBean.setSongCollectionId(null);
        // 返回查询歌曲
        return new ResponseEntity<>(querySongService.querySongByPerformanceDate(querySongBean), HttpStatus.OK);
    }

    /**
     * 通过歌曲集合id查询歌曲及相关信息
     *
     * @param request 查询请求
     * @return 歌曲及相关信息
     * @throws ResourceNotFoundException 资源未找到
     */
    @PostMapping(Constant.QUERY_CONTENT_SONG_COLLECTION_ID)
    @ResponseBody
    public ResponseEntity<QuerySongApiResponse> query(@RequestBody @Validated @NotEmpty QuerySongBySongCollectionIdRequest request)
            throws ResourceNotFoundException {

        QuerySongBean querySongBean = new QuerySongBean();
        querySongBean.setPerformanceDate(null);
        querySongBean.setSongCollectionId(request.getSongCollectionId());
        // 返回查询歌曲
        return new ResponseEntity<>(querySongService.querySongBySongCollectionId(querySongBean), HttpStatus.OK);
    }
}
