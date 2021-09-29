package org.nano.song.controller;

import org.nano.song.domain.Constant;
import org.nano.song.handler.exception.BindRelationExistException;
import org.nano.song.handler.exception.ResourceNotFoundException;
import org.nano.song.info.bean.originalSinger.RelateOriginalSingerBean;
import org.nano.song.info.request.originalSinger.RelateOriginalSingerRequest;
import org.nano.song.service.originalSinger.RelateOriginalSingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;

/**
 * 原唱歌手相关控制器类
 */
@CrossOrigin
@RequestMapping(Constant.URL_ORIGINAL_SINGER)
@RestController
public class OriginalSingerController {

    @Autowired
    private RelateOriginalSingerService relateOriginalSingerService;

    /**
     * 关联原唱操作
     *
     * @param request 关联请求
     * @return 关联成功信息
     * @throws ResourceNotFoundException  资源未找到
     * @throws BindRelationExistException 绑定关系已存在
     */
    @PostMapping(Constant.OPERATION_RELATE)
    @ResponseBody
    public ResponseEntity<?> relateOriginalSinger(@RequestBody @Validated @NotEmpty RelateOriginalSingerRequest request)
            throws ResourceNotFoundException, BindRelationExistException {

        RelateOriginalSingerBean relateOriginalSingerBean = new RelateOriginalSingerBean();
        relateOriginalSingerBean.setSingerName(request.getSingerName());
        relateOriginalSingerBean.setSongTitle(request.getSongTitle());
        // 关联原唱歌手
        relateOriginalSingerService.relateOriginalSinger(relateOriginalSingerBean);

        return new ResponseEntity<>(Constant.MSG_RELATE_SUCCESS, HttpStatus.OK);
    }
}
