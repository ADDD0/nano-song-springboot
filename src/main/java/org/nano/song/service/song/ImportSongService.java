package org.nano.song.service.song;

import org.nano.song.handler.exception.BindRelationExistException;
import org.nano.song.handler.exception.ResourceExistException;
import org.nano.song.handler.exception.ResourceNotFoundException;
import org.nano.song.info.bean.song.ImportSongBean;

import java.io.IOException;
import java.text.ParseException;

public interface ImportSongService {

    /**
     * 导入歌曲
     *
     * @param importSongBean 导入歌曲参数
     * @throws ResourceExistException     查找数据已存在
     * @throws ResourceNotFoundException  查找数据不存在
     * @throws ParseException             格式转换错误
     * @throws IOException                文件IO错误
     * @throws BindRelationExistException 绑定关系已存在
     */
    void importSong(ImportSongBean importSongBean)
            throws ResourceExistException, ResourceNotFoundException, ParseException, IOException, BindRelationExistException;
}
