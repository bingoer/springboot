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
import wang.raye.springboot.model.TradePoint;
import wang.raye.springboot.model.TradePointCriteria;

public interface TradePointMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trade_point
     *
     * @mbg.generated
     */
    @SelectProvider(type=TradePointSqlProvider.class, method="countByExample")
    long countByExample(TradePointCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trade_point
     *
     * @mbg.generated
     */
    @DeleteProvider(type=TradePointSqlProvider.class, method="deleteByExample")
    int deleteByExample(TradePointCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trade_point
     *
     * @mbg.generated
     */
    @Delete({
        "delete from trade_point",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trade_point
     *
     * @mbg.generated
     */
    @Insert({
        "insert into trade_point (id, exchange, ",
        "symbol, base, period, ",
        "quant_type, price, ",
        "status, kline_time, ",
        "time)",
        "values (#{id,jdbcType=INTEGER}, #{exchange,jdbcType=VARCHAR}, ",
        "#{symbol,jdbcType=VARCHAR}, #{base,jdbcType=VARCHAR}, #{period,jdbcType=VARCHAR}, ",
        "#{quantType,jdbcType=VARCHAR}, #{price,jdbcType=DOUBLE}, ",
        "#{status,jdbcType=CHAR}, #{klineTime,jdbcType=VARCHAR}, ",
        "#{time,jdbcType=TIMESTAMP})"
    })
    int insert(TradePoint record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trade_point
     *
     * @mbg.generated
     */
    @InsertProvider(type=TradePointSqlProvider.class, method="insertSelective")
    int insertSelective(TradePoint record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trade_point
     *
     * @mbg.generated
     */
    @SelectProvider(type=TradePointSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="exchange", property="exchange", jdbcType=JdbcType.VARCHAR),
        @Result(column="symbol", property="symbol", jdbcType=JdbcType.VARCHAR),
        @Result(column="base", property="base", jdbcType=JdbcType.VARCHAR),
        @Result(column="period", property="period", jdbcType=JdbcType.VARCHAR),
        @Result(column="quant_type", property="quantType", jdbcType=JdbcType.VARCHAR),
        @Result(column="price", property="price", jdbcType=JdbcType.DOUBLE),
        @Result(column="status", property="status", jdbcType=JdbcType.CHAR),
        @Result(column="kline_time", property="klineTime", jdbcType=JdbcType.VARCHAR),
        @Result(column="time", property="time", jdbcType=JdbcType.TIMESTAMP)
    })
    List<TradePoint> selectByExample(TradePointCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trade_point
     *
     * @mbg.generated
     */
    @Select({
        "select",
        "id, exchange, symbol, base, period, quant_type, price, status, kline_time, time",
        "from trade_point",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="exchange", property="exchange", jdbcType=JdbcType.VARCHAR),
        @Result(column="symbol", property="symbol", jdbcType=JdbcType.VARCHAR),
        @Result(column="base", property="base", jdbcType=JdbcType.VARCHAR),
        @Result(column="period", property="period", jdbcType=JdbcType.VARCHAR),
        @Result(column="quant_type", property="quantType", jdbcType=JdbcType.VARCHAR),
        @Result(column="price", property="price", jdbcType=JdbcType.DOUBLE),
        @Result(column="status", property="status", jdbcType=JdbcType.CHAR),
        @Result(column="kline_time", property="klineTime", jdbcType=JdbcType.VARCHAR),
        @Result(column="time", property="time", jdbcType=JdbcType.TIMESTAMP)
    })
    TradePoint selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trade_point
     *
     * @mbg.generated
     */
    @UpdateProvider(type=TradePointSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") TradePoint record, @Param("example") TradePointCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trade_point
     *
     * @mbg.generated
     */
    @UpdateProvider(type=TradePointSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") TradePoint record, @Param("example") TradePointCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trade_point
     *
     * @mbg.generated
     */
    @UpdateProvider(type=TradePointSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(TradePoint record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trade_point
     *
     * @mbg.generated
     */
    @Update({
        "update trade_point",
        "set exchange = #{exchange,jdbcType=VARCHAR},",
          "symbol = #{symbol,jdbcType=VARCHAR},",
          "base = #{base,jdbcType=VARCHAR},",
          "period = #{period,jdbcType=VARCHAR},",
          "quant_type = #{quantType,jdbcType=VARCHAR},",
          "price = #{price,jdbcType=DOUBLE},",
          "status = #{status,jdbcType=CHAR},",
          "kline_time = #{klineTime,jdbcType=VARCHAR},",
          "time = #{time,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(TradePoint record);
}