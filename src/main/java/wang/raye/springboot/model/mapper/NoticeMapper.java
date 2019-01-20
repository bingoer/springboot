package wang.raye.springboot.model.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;
import wang.raye.springboot.model.Notice;
import wang.raye.springboot.model.NoticeCriteria;

public interface NoticeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notice
     *
     * @mbg.generated
     */
    @SelectProvider(type=NoticeSqlProvider.class, method="countByExample")
    long countByExample(NoticeCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notice
     *
     * @mbg.generated
     */
    @DeleteProvider(type=NoticeSqlProvider.class, method="deleteByExample")
    int deleteByExample(NoticeCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notice
     *
     * @mbg.generated
     */
    @Delete({
        "delete from notice",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notice
     *
     * @mbg.generated
     */
    @Insert({
        "insert into notice (id, notice_id, ",
        "lang, zh_name, from_market, ",
        "timestamp, title)",
        "values (#{id,jdbcType=INTEGER}, #{noticeId,jdbcType=VARCHAR}, ",
        "#{lang,jdbcType=VARCHAR}, #{zhName,jdbcType=VARCHAR}, #{fromMarket,jdbcType=VARCHAR}, ",
        "#{timestamp,jdbcType=VARCHAR}, #{title,jdbcType=VARCHAR})"
    })
    int insert(Notice record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notice
     *
     * @mbg.generated
     */
    @InsertProvider(type=NoticeSqlProvider.class, method="insertSelective")
    int insertSelective(Notice record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notice
     *
     * @mbg.generated
     */
    @SelectProvider(type=NoticeSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="notice_id", property="noticeId", jdbcType=JdbcType.VARCHAR),
        @Result(column="lang", property="lang", jdbcType=JdbcType.VARCHAR),
        @Result(column="zh_name", property="zhName", jdbcType=JdbcType.VARCHAR),
        @Result(column="from_market", property="fromMarket", jdbcType=JdbcType.VARCHAR),
        @Result(column="timestamp", property="timestamp", jdbcType=JdbcType.VARCHAR),
        @Result(column="title", property="title", jdbcType=JdbcType.VARCHAR)
    })
    List<Notice> selectByExample(NoticeCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notice
     *
     * @mbg.generated
     */
    @Select({
        "select",
        "id, notice_id, lang, zh_name, from_market, timestamp, title",
        "from notice",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="notice_id", property="noticeId", jdbcType=JdbcType.VARCHAR),
        @Result(column="lang", property="lang", jdbcType=JdbcType.VARCHAR),
        @Result(column="zh_name", property="zhName", jdbcType=JdbcType.VARCHAR),
        @Result(column="from_market", property="fromMarket", jdbcType=JdbcType.VARCHAR),
        @Result(column="timestamp", property="timestamp", jdbcType=JdbcType.VARCHAR),
        @Result(column="title", property="title", jdbcType=JdbcType.VARCHAR)
    })
    Notice selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notice
     *
     * @mbg.generated
     */
    @UpdateProvider(type=NoticeSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Notice record, @Param("example") NoticeCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notice
     *
     * @mbg.generated
     */
    @UpdateProvider(type=NoticeSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Notice record, @Param("example") NoticeCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notice
     *
     * @mbg.generated
     */
    @UpdateProvider(type=NoticeSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Notice record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table notice
     *
     * @mbg.generated
     */
    @Update({
        "update notice",
        "set notice_id = #{noticeId,jdbcType=VARCHAR},",
          "lang = #{lang,jdbcType=VARCHAR},",
          "zh_name = #{zhName,jdbcType=VARCHAR},",
          "from_market = #{fromMarket,jdbcType=VARCHAR},",
          "timestamp = #{timestamp,jdbcType=VARCHAR},",
          "title = #{title,jdbcType=VARCHAR}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Notice record);
}