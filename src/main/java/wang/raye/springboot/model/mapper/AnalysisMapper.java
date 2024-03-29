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
import wang.raye.springboot.model.Analysis;
import wang.raye.springboot.model.AnalysisCriteria;

public interface AnalysisMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table analysis
     *
     * @mbg.generated
     */
    @SelectProvider(type=AnalysisSqlProvider.class, method="countByExample")
    long countByExample(AnalysisCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table analysis
     *
     * @mbg.generated
     */
    @DeleteProvider(type=AnalysisSqlProvider.class, method="deleteByExample")
    int deleteByExample(AnalysisCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table analysis
     *
     * @mbg.generated
     */
    @Delete({
        "delete from analysis",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table analysis
     *
     * @mbg.generated
     */
    @Insert({
        "insert into analysis (id, exchange, ",
        "symbol, status, ",
        "price, period, kline_time, ",
        "create_time)",
        "values (#{id,jdbcType=INTEGER}, #{exchange,jdbcType=VARCHAR}, ",
        "#{symbol,jdbcType=VARCHAR}, #{status,jdbcType=VARCHAR}, ",
        "#{price,jdbcType=DOUBLE}, #{period,jdbcType=VARCHAR}, #{klineTime,jdbcType=VARCHAR}, ",
        "#{createTime,jdbcType=TIMESTAMP})"
    })
    int insert(Analysis record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table analysis
     *
     * @mbg.generated
     */
    @InsertProvider(type=AnalysisSqlProvider.class, method="insertSelective")
    int insertSelective(Analysis record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table analysis
     *
     * @mbg.generated
     */
    @SelectProvider(type=AnalysisSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="exchange", property="exchange", jdbcType=JdbcType.VARCHAR),
        @Result(column="symbol", property="symbol", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.VARCHAR),
        @Result(column="price", property="price", jdbcType=JdbcType.DOUBLE),
        @Result(column="period", property="period", jdbcType=JdbcType.VARCHAR),
        @Result(column="kline_time", property="klineTime", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<Analysis> selectByExample(AnalysisCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table analysis
     *
     * @mbg.generated
     */
    @Select({
        "select",
        "id, exchange, symbol, status, price, period, kline_time, create_time",
        "from analysis",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="exchange", property="exchange", jdbcType=JdbcType.VARCHAR),
        @Result(column="symbol", property="symbol", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.VARCHAR),
        @Result(column="price", property="price", jdbcType=JdbcType.DOUBLE),
        @Result(column="period", property="period", jdbcType=JdbcType.VARCHAR),
        @Result(column="kline_time", property="klineTime", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP)
    })
    Analysis selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table analysis
     *
     * @mbg.generated
     */
    @UpdateProvider(type=AnalysisSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Analysis record, @Param("example") AnalysisCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table analysis
     *
     * @mbg.generated
     */
    @UpdateProvider(type=AnalysisSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Analysis record, @Param("example") AnalysisCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table analysis
     *
     * @mbg.generated
     */
    @UpdateProvider(type=AnalysisSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Analysis record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table analysis
     *
     * @mbg.generated
     */
    @Update({
        "update analysis",
        "set exchange = #{exchange,jdbcType=VARCHAR},",
          "symbol = #{symbol,jdbcType=VARCHAR},",
          "status = #{status,jdbcType=VARCHAR},",
          "price = #{price,jdbcType=DOUBLE},",
          "period = #{period,jdbcType=VARCHAR},",
          "kline_time = #{klineTime,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Analysis record);
}