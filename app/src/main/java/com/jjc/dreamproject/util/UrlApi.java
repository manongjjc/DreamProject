package com.jjc.dreamproject.util;

/**
 * Created by JJC on 2017/12/5.
 */

public class UrlApi {
    public static final String QueryMusic = "http://s.music.qq.com/fcgi-bin/music_search_new_platform?t=0&n=${num}&aggr=1&cr=1&loginUin=0&format=json&inCharset=GB2312&outCharset=utf-8&notice=0&platform=jqminiframe.json&needNewCode=0&p=1&catZhida=0&remoteplace=sizer.newclient.next_song&w=${name}";//条件查询音乐
    public static final String TotalHit ="http://music.qq.com/musicbox/shop/v3/data/hit/hit_all.js";// 总榜
    public static final String NewMusic = "http://music.qq.com/musicbox/shop/v3/data/hit/hit_newsong.js";//新歌榜
    public static final String MusicAdress = "http://ws.stream.qqmusic.qq.com/${id}.m4a?fromtag=46";//获取音乐播放路径
    public static final String MusicPictrue = "http://imgcache.qq.com/music/photo/album_${width}/${image_sum}/${width}_albumpic_${image_id}_0.jpg";//获取音乐图片  width 图片宽度  image_id 图片ID  image_sum 等于image_id%100
    public static final String MusicLyric = "http://music.qq.com/miniportal/static/lyric/${id_sum}/${id}.xml";//查询歌词 ID  歌曲ID  id_sum = id%100
    public static final String MusicPhoto1 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1512981943714&di=25e21cd589498c285cce151b63f12a89&imgtype=0&src=http%3A%2F%2Fpic49.nipic.com%2Ffile%2F20141007%2F1529049_130758410000_2.jpg";
    public static final String MusicPhoto2 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1512982106911&di=f1c59ce6eba3a1dadf67cd3c6ff6bd1d&imgtype=0&src=http%3A%2F%2Fimg1.3lian.com%2Fimg013%2Fv1%2F93%2Fd%2F98.jpg";
    public static final String PicturePhoto1 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1512982203220&di=b91251ce1bbaac0e139aa336034a2411&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F5fdf8db1cb1349549b5cde4a5d4e9258d1094a59.jpg";
    public static final String PicturePhoto2 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1512982285246&di=16ecc25f05a13653a634f16522ae3d2c&imgtype=0&src=http%3A%2F%2Fimg1.3lian.com%2F2015%2Fw6%2F60%2Fd%2F3.jpg";
    public static final String MoviePhoto1 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1512982430130&di=21119bdd470592656a1046ac061b3db5&imgtype=0&src=http%3A%2F%2Fpic14.nipic.com%2F20110520%2F2531170_173830072355_2.jpg";
    public static final String MoviePhoto2 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1512982459005&di=e1f74e38fa87d99408ac3cc29df93b1e&imgtype=0&src=http%3A%2F%2Fpic.qjimage.com%2Fpmedia0061%2Fhigh%2Fpmedia09192146.jpg";

    /**
     * 云音乐搜索API网址
     */
    public static final String CLOUD_MUSIC_API_SEARCH = "http://s.music.163.com/search/get/?";
    /**
     * 歌曲信息API网址
     */
    public static final String CLOUD_MUSIC_API_MUSICINGO = "http://music.163.com/api/song/detail/?";
    /**
     * 获取歌曲的歌词
     */
    public static final String CLOUD_MUSIC_API_MUSICLRC = "http://music.163.com/api/song/lyric?";

    public static String getQueryMusic(int num,String name ){
        return QueryMusic.replaceAll("\\$\\{num\\}", num+"").replaceAll("\\$\\{name\\}",name);
    }

    public static String getTotalHit(){
        return TotalHit;
    }
    public static String getNewMusic(){
        return NewMusic;
    }

    public static String getMusicAdress(int id){
        return MusicAdress.replaceAll("\\$\\{id\\}", id+"");
    }

    public static String getMusicPictrue(int width,int image_id){
        return MusicPictrue.replaceAll("\\$\\{width\\}", width+"").replaceAll("\\$\\{image_id\\}", image_id+"").replaceAll("\\$\\{image_sum\\}", image_id%100+"");
    }

    public static String getMusicLyric(int id){
        return MusicLyric.replaceAll("\\$\\{id\\}", id+"").replaceAll("\\$\\{id_sum\\}", id%100+"");
    }
    /**
     * 网易音乐搜索API
     * http://s.music.163.com/search/get/
     * 获取方式：GET
     * 参数：
     * src: lofter //可为空
     * type: 1
     * filterDj: true|false //可为空
     * s: //关键词
     * limit: 10 //限制返回结果数
     * offset: 0 //偏移
     * callback: //为空时返回json，反之返回jsonp callback
     * @param s
     * @return
     * 注意废数字才用‘’符号，要不不能用，否则出错！！
     */
    public static String SearchMusic(String s,int limit,int type,int offset){
        return CLOUD_MUSIC_API_SEARCH + "type="+type+"&s='" + s + "'&limit="+limit+"&offset="+offset;
    }
    /**
     * 网易云音乐歌曲信息API
     * @param id 歌曲id
     * @param ids 用[]包裹起来的歌曲id 写法%5B %5D
     * @return
     */
    public static String Cloud_Music_MusicInfoAPI(String id,String ids){
        return CLOUD_MUSIC_API_MUSICINGO + "id="+id+"&ids=%5B"+ids+"%5D";
    }
    /**
     * 获取歌曲歌词的API
     *URL：

     GET http://music.163.com/api/song/lyric

     必要参数：

     id：歌曲ID

     lv：值为-1，我猜测应该是判断是否搜索lyric格式

     kv：值为-1，这个值貌似并不影响结果，意义不明

     tv：值为-1，是否搜索tlyric格式
     * @param os
     * @param id
     */
    public static String Cloud_Muisc_getLrcAPI(String os,String id){
        return CLOUD_MUSIC_API_MUSICLRC + "os="+os+"&id="+id+"&lv=-1&kv=-1&tv=-1";
    }
}

