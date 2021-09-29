package org.nano.song.service.song;

import org.nano.song.domain.Constant;
import org.nano.song.domain.Enumerate.DELETE_FLAG;
import org.nano.song.domain.repository.SingerRepository;
import org.nano.song.domain.repository.SongCollectionRepository;
import org.nano.song.domain.repository.SongRepository;
import org.nano.song.domain.repository.entity.Singer;
import org.nano.song.domain.repository.entity.Song;
import org.nano.song.domain.repository.entity.SongCollection;
import org.nano.song.handler.exception.BindRelationExistException;
import org.nano.song.handler.exception.ResourceExistException;
import org.nano.song.handler.exception.ResourceNotFoundException;
import org.nano.song.info.bean.coverSinger.BindCoverSingerBean;
import org.nano.song.info.bean.song.ImportSongBean;
import org.nano.song.service.coverSinger.BindCoverSingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;

/**
 * 导入歌曲服务接口实现类
 */
@Service
@Transactional
public class ImportSongServiceImpl implements ImportSongService {

    @Autowired
    private SingerRepository singerRepository;

    @Autowired
    private SongCollectionRepository songCollectionRepository;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private BindCoverSingerService bindCoverSingerService;

    /**
     * 导入歌曲
     *
     * @param importSongBean 导入歌曲参数
     * @throws ResourceExistException     资源已存在
     * @throws ResourceNotFoundException  资源未找到
     * @throws ParseException             格式转换错误
     * @throws IOException                文件IO错误
     * @throws BindRelationExistException 绑定关系已存在
     */
    @Override
    public void importSong(ImportSongBean importSongBean) throws ResourceExistException, ResourceNotFoundException, ParseException, IOException, BindRelationExistException {

        String coverSingerName = importSongBean.getCoverSingerName();
        String fileName = importSongBean.getFileName();
        String performanceDate = importSongBean.getPerformanceDate();
        String songFileContent = importSongBean.getSongFileContent();
        String songTitle = importSongBean.getSongTitle();
        // 通过歌手姓名查询翻唱歌手 若不存在 报400
        Singer singer = singerRepository.findBySingerNameAndLogicalDeleteFlag(coverSingerName, DELETE_FLAG.UNDELETED.getCode())
                .orElseThrow(() -> new ResourceNotFoundException(Constant.SHOW_SINGER, coverSingerName));
        // 通过歌曲标题查询歌曲集合 若不存在 报400
        SongCollection songCollection = songCollectionRepository.findBySongTitleAndLogicalDeleteFlag(songTitle, DELETE_FLAG.UNDELETED.getCode())
                .orElseThrow(() -> new ResourceNotFoundException(Constant.SHOW_SONG_COLLECTION, songTitle));

        // 设置实体属性
        Song song = new Song();
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
            song.setPerformanceDate(simpleDateFormat.parse(performanceDate));
        } catch (ParseException e) {
            // 格式化弹唱日期失败 报400
            throw new ParseException(performanceDate, e.getErrorOffset());
        }
        song.setPath(performanceDate + "/" + fileName);
        song.setSongCollectionId(songCollection.getSongCollectionId());
        // 去除文件扩展名
        song.setSongName(fileName.substring(0, fileName.length() - 4));
        song.setLogicalDeleteFlag(false);

        // todo 获取base64编码后的字节流 正则实现
        songFileContent = songFileContent.replaceFirst("data:audio/mpeg;base64,", "");
        // 创建新的文件夹路径
        String newDirPath = Constant.RESOURCES_STATIC_ROOT_PATH + performanceDate + Constant.SLASH;
        File fileDir = new File(newDirPath);
        if (!fileDir.exists()) {
            // 文件夹不存在时创建文件夹
            if (!fileDir.mkdirs()) {
                // 新建文件夹失败 报500
                throw new IOException("新建文件夹失败");
            }
        }
        // 创建新的音频文件
        String newSongPath = newDirPath + fileName;
        if (new File(newSongPath).exists()) {
            // 歌曲文件已存在
            throw new ResourceExistException(Constant.SHOW_SONG, fileName);
        }
        try {
            // todo 使用AudioFileWriter创建音频
            // Base64解码
            byte[] buffer = Base64.getDecoder().decode(songFileContent);
            FileOutputStream fileOutputStream = new FileOutputStream(newSongPath);
            fileOutputStream.write(buffer);
            fileOutputStream.close();
        } catch (IOException e) {
            throw new IOException("文件IO错误");
        }

        // 数据持久化
        songRepository.save(song);

        BindCoverSingerBean bindCoverSingerBean = new BindCoverSingerBean();
        bindCoverSingerBean.setSingerId(singer.getSingerId());
        bindCoverSingerBean.setSongId(song.getSongId());
        // 绑定翻唱歌手
        bindCoverSingerService.bindCoverSinger(bindCoverSingerBean);
    }
}
