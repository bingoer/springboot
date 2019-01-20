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
import wang.raye.springboot.model.Alert;
import wang.raye.springboot.model.AlertCriteria;

public interface AlertMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table alert
     *
     * @mbg.generated
     */
    @SelectProvider(type=AlertSqlProvider.class, method="countByExample")
    long countByExample(AlertCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table alert
     *
     * @mbg.generated
     */
    @DeleteProvider(type=AlertSqlProvider.class, method="deleteByExample")
    int deleteByExample(AlertCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table alert
     *
     * @mbg.generated
     */
    @Delete({
        "delete from alert",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table alert
     *
     * @mbg.generated
     */
    @Insert({
        "insert into alert (id, exchange, ",
        "symbol, type, status, ",
        "price, period, send, ",
        "count, time)",
        "values (#{id,jdbcType=INTEGER}, #{exchange,jdbcType=VARCHAR}, ",
        "#{symbol,jdbcType=VARCHAR}, #{type,jdbcType=VARCHAR}, #{status,jdbcType=CHAR}, ",
        "#{price,jdbcType=DOUBLE}, #{period,jdbcType=VARCHAR}, #{send,jdbcType=CHAR}, ",
        "#{count,jdbcType=INTEGER}, #{time,jdbcType=TIMESTAMP})"
    })
    int insert(Alert record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table alert
     *
     * @mbg.generated
     */
    @InsertProvider(type=AlertSqlProvider.class, method="insertSelective")
    int insertSelective(Alert record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table alert
     *
     * @mbg.generated
     */
    @SelectProvider(type=AlertSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="exchange", property="exchange", jdbcType=JdbcType.VARCHAR),
        @Result(column="symbol", property="symbol", jdbcType=JdbcType.VARCHAR),
        @Result(column="type", property="type", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.CHAR),
        @Result(column="price", property="price", jdbcType=JdbcType.DOUBLE),
        @Result(column="period", property="period", jdbcType=JdbcType.VARCHAR),
        @Result(column="send", property="send", jdbcType=JdbcType.CHAR),
        @Result(column="count", property="count", jdbcType=JdbcType.INTEGER),
        @Result(column="time", property="time", jdbcType=JdbcType.TIMESTAMP)
    })
    List<Alert> selectByExample(AlertCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table alert
     *
     * @mbg.generated
     */
    @Select({
        "select",
        "id, exchange, symbol, type, status, price, period, send, count, time",
        "from alert",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="exchange", property="exchange", jdbcType=JdbcType.VARCHAR),
        @Result(column="symbol", property="symbol", jdbcType=JdbcType.VARCHAR),
        @Result(column="type", property="type", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.CHAR),
        @Result(column="price", property="price", jdbcType=JdbcType.DOUBLE),
        @Result(column="period", property="period", jdbcType=JdbcType.VARCHAR),
        @Result(column="send", property="send", jdbcType=JdbcType.CHAR),
        @Result(column="count", property="count", jdbcType=JdbcType.INTEGER),
        @Result(column="time", property="time", jdbcType=JdbcType.TIMESTAMP)
    })
    Alert selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table alert
     *
     * @mbg.generated
     */
    @UpdateProvider(type=AlertSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Alert record, @Param("example") AlertCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table alert
     *
     * @mbg.generated
     */
    @UpdateProvider(type=AlertSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Alert record, @Param("example") AlertCriteria example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table alert
     *
     * @mbg.generated
     */
    @UpdateProvider(type=AlertSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Alert record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table alert
     *
     * @mbg.generated
     */
    @Update({
        "update alert",
        "set exchange = #{exchange,jdbcType=VARCHAR},",
          "symbol = #{symbol,jdbcType=VARCHAR},",
          "type = #{type,jdbcType=VARCHAR},",
          "status = #{status,jdbcType=CHAR},",
          "price = #{price,jdbcType=DOUBLE},",
          "period = #{period,jdbcType=VARCHAR},",
          "send = #{send,jdbcType=CHAR},",
          "count = #{count,jdbcType=INTEGER},",
          "time = #{time,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(Alert record);
}