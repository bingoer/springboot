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
import wang.raye.springboot.model.MacdCross;
import wang.raye.springboot.model.MacdCrossCriteria;

public interface MacdCrossMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table macd_cross
     *
     * @mbg.generated
     */
    @SelectProvider(type=MacdCrossSqlProvider.class, method="countByExample")
    long countByExample(MacdCrossCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table macd_cross
     *
     * @mbg.generated
     */
    @DeleteProvider(type=MacdCrossSqlProvider.class, method="deleteByExample")
    int deleteByExample(MacdCrossCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table macd_cross
     *
     * @mbg.generated
     */
    @Delete({
        "delete from macd_cross",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table macd_cross
     *
     * @mbg.generated
     */
    @Insert({
        "insert into macd_cross (id, exchange, ",
        "symbol, type, status, ",
        "quota1, quota2, quota3, ",
        "quota4, pre_quota1, ",
        "pre_quota2, pre_quota3, ",
        "pre_quota4, thr_quota1, ",
        "thr_quota2, thr_quota3, ",
        "thr_quota4, price, ",
        "pre_price, thr_price, ",
        "thr_low, high, period, ",
        "kline_time, update_time)",
        "values (#{id,jdbcType=INTEGER}, #{exchange,jdbcType=VARCHAR}, ",
        "#{symbol,jdbcType=VARCHAR}, #{type,jdbcType=VARCHAR}, #{status,jdbcType=CHAR}, ",
        "#{quota1,jdbcType=DOUBLE}, #{quota2,jdbcType=DOUBLE}, #{quota3,jdbcType=DOUBLE}, ",
        "#{quota4,jdbcType=DOUBLE}, #{preQuota1,jdbcType=DOUBLE}, ",
        "#{preQuota2,jdbcType=DOUBLE}, #{preQuota3,jdbcType=DOUBLE}, ",
        "#{preQuota4,jdbcType=DOUBLE}, #{thrQuota1,jdbcType=DOUBLE}, ",
        "#{thrQuota2,jdbcType=DOUBLE}, #{thrQuota3,jdbcType=DOUBLE}, ",
        "#{thrQuota4,jdbcType=DOUBLE}, #{price,jdbcType=DOUBLE}, ",
        "#{prePrice,jdbcType=DOUBLE}, #{thrPrice,jdbcType=DOUBLE}, ",
        "#{thrLow,jdbcType=DOUBLE}, #{high,jdbcType=DOUBLE}, #{period,jdbcType=VARCHAR}, ",
        "#{klineTime,jdbcType=VARCHAR}, #{updateTime,jdbcType=TIMESTAMP})"
    })
    int insert(MacdCross record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table macd_cross
     *
     * @mbg.generated
     */
    @InsertProvider(type=MacdCrossSqlProvider.class, method="insertSelective")
    int insertSelective(MacdCross record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table macd_cross
     *
     * @mbg.generated
     */
    @SelectProvider(type=MacdCrossSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="exchange", property="exchange", jdbcType=JdbcType.VARCHAR),
        @Result(column="symbol", property="symbol", jdbcType=JdbcType.VARCHAR),
        @Result(column="type", property="type", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.CHAR),
        @Result(column="quota1", property="quota1", jdbcType=JdbcType.DOUBLE),
        @Result(column="quota2", property="quota2", jdbcType=JdbcType.DOUBLE),
        @Result(column="quota3", property="quota3", jdbcType=JdbcType.DOUBLE),
        @Result(column="quota4", property="quota4", jdbcType=JdbcType.DOUBLE),
        @Result(column="pre_quota1", property="preQuota1", jdbcType=JdbcType.DOUBLE),
        @Result(column="pre_quota2", property="preQuota2", jdbcType=JdbcType.DOUBLE),
        @Result(column="pre_quota3", property="preQuota3", jdbcType=JdbcType.DOUBLE),
        @Result(column="pre_quota4", property="preQuota4", jdbcType=JdbcType.DOUBLE),
        @Result(column="thr_quota1", property="thrQuota1", jdbcType=JdbcType.DOUBLE),
        @Result(column="thr_quota2", property="thrQuota2", jdbcType=JdbcType.DOUBLE),
        @Result(column="thr_quota3", property="thrQuota3", jdbcType=JdbcType.DOUBLE),
        @Result(column="thr_quota4", property="thrQuota4", jdbcType=JdbcType.DOUBLE),
        @Result(column="price", property="price", jdbcType=JdbcType.DOUBLE),
        @Result(column="pre_price", property="prePrice", jdbcType=JdbcType.DOUBLE),
        @Result(column="thr_price", property="thrPrice", jdbcType=JdbcType.DOUBLE),
        @Result(column="thr_low", property="thrLow", jdbcType=JdbcType.DOUBLE),
        @Result(column="high", property="high", jdbcType=JdbcType.DOUBLE),
        @Result(column="period", property="period", jdbcType=JdbcType.VARCHAR),
        @Result(column="kline_time", property="klineTime", jdbcType=JdbcType.VARCHAR),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    List<MacdCross> selectByExample(MacdCrossCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table macd_cross
     *
     * @mbg.generated
     */
    @Select({
        "select",
        "id, exchange, symbol, type, status, quota1, quota2, quota3, quota4, pre_quota1, ",
        "pre_quota2, pre_quota3, pre_quota4, thr_quota1, thr_quota2, thr_quota3, thr_quota4, ",
        "price, pre_price, thr_price, thr_low, high, period, kline_time, update_time",
        "from macd_cross",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="exchange", property="exchange", jdbcType=JdbcType.VARCHAR),
        @Result(column="symbol", property="symbol", jdbcType=JdbcType.VARCHAR),
        @Result(column="type", property="type", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.CHAR),
        @Result(column="quota1", property="quota1", jdbcType=JdbcType.DOUBLE),
        @Result(column="quota2", property="quota2", jdbcType=JdbcType.DOUBLE),
        @Result(column="quota3", property="quota3", jdbcType=JdbcType.DOUBLE),
        @Result(column="quota4", property="quota4", jdbcType=JdbcType.DOUBLE),
        @Result(column="pre_quota1", property="preQuota1", jdbcType=JdbcType.DOUBLE),
        @Result(column="pre_quota2", property="preQuota2", jdbcType=JdbcType.DOUBLE),
        @Result(column="pre_quota3", property="preQuota3", jdbcType=JdbcType.DOUBLE),
        @Result(column="pre_quota4", property="preQuota4", jdbcType=JdbcType.DOUBLE),
        @Result(column="thr_quota1", property="thrQuota1", jdbcType=JdbcType.DOUBLE),
        @Result(column="thr_quota2", property="thrQuota2", jdbcType=JdbcType.DOUBLE),
        @Result(column="thr_quota3", property="thrQuota3", jdbcType=JdbcType.DOUBLE),
        @Result(column="thr_quota4", property="thrQuota4", jdbcType=JdbcType.DOUBLE),
        @Result(column="price", property="price", jdbcType=JdbcType.DOUBLE),
        @Result(column="pre_price", property="prePrice", jdbcType=JdbcType.DOUBLE),
        @Result(column="thr_price", property="thrPrice", jdbcType=JdbcType.DOUBLE),
        @Result(column="thr_low", property="thrLow", jdbcType=JdbcType.DOUBLE),
        @Result(column="high", property="high", jdbcType=JdbcType.DOUBLE),
        @Result(column="period", property="period", jdbcType=JdbcType.VARCHAR),
        @Result(column="kline_time", property="klineTime", jdbcType=JdbcType.VARCHAR),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP)
    })
    MacdCross selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table macd_cross
     *
     * @mbg.generated
     */
    @UpdateProvider(type=MacdCrossSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") MacdCross record, @Param("example") MacdCrossCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table macd_cross
     *
     * @mbg.generated
     */
    @UpdateProvider(type=MacdCrossSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") MacdCross record, @Param("example") MacdCrossCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table macd_cross
     *
     * @mbg.generated
     */
    @UpdateProvider(type=MacdCrossSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(MacdCross record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table macd_cross
     *
     * @mbg.generated
     */
    @Update({
        "update macd_cross",
        "set exchange = #{exchange,jdbcType=VARCHAR},",
          "symbol = #{symbol,jdbcType=VARCHAR},",
          "type = #{type,jdbcType=VARCHAR},",
          "status = #{status,jdbcType=CHAR},",
          "quota1 = #{quota1,jdbcType=DOUBLE},",
          "quota2 = #{quota2,jdbcType=DOUBLE},",
          "quota3 = #{quota3,jdbcType=DOUBLE},",
          "quota4 = #{quota4,jdbcType=DOUBLE},",
          "pre_quota1 = #{preQuota1,jdbcType=DOUBLE},",
          "pre_quota2 = #{preQuota2,jdbcType=DOUBLE},",
          "pre_quota3 = #{preQuota3,jdbcType=DOUBLE},",
          "pre_quota4 = #{preQuota4,jdbcType=DOUBLE},",
          "thr_quota1 = #{thrQuota1,jdbcType=DOUBLE},",
          "thr_quota2 = #{thrQuota2,jdbcType=DOUBLE},",
          "thr_quota3 = #{thrQuota3,jdbcType=DOUBLE},",
          "thr_quota4 = #{thrQuota4,jdbcType=DOUBLE},",
          "price = #{price,jdbcType=DOUBLE},",
          "pre_price = #{prePrice,jdbcType=DOUBLE},",
          "thr_price = #{thrPrice,jdbcType=DOUBLE},",
          "thr_low = #{thrLow,jdbcType=DOUBLE},",
          "high = #{high,jdbcType=DOUBLE},",
          "period = #{period,jdbcType=VARCHAR},",
          "kline_time = #{klineTime,jdbcType=VARCHAR},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(MacdCross record);
}