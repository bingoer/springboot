package wang.raye.springboot.model;

import java.util.Date;

public class Analysis {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column analysis.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column analysis.exchange
     *
     * @mbg.generated
     */
    private String exchange;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column analysis.symbol
     *
     * @mbg.generated
     */
    private String symbol;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column analysis.status
     *
     * @mbg.generated
     */
    private String status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column analysis.price
     *
     * @mbg.generated
     */
    private Double price;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column analysis.period
     *
     * @mbg.generated
     */
    private String period;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column analysis.kline_time
     *
     * @mbg.generated
     */
    private String klineTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column analysis.create_time
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column analysis.id
     *
     * @return the value of analysis.id
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column analysis.id
     *
     * @param id the value for analysis.id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column analysis.exchange
     *
     * @return the value of analysis.exchange
     *
     * @mbg.generated
     */
    public String getExchange() {
        return exchange;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column analysis.exchange
     *
     * @param exchange the value for analysis.exchange
     *
     * @mbg.generated
     */
    public void setExchange(String exchange) {
        this.exchange = exchange == null ? null : exchange.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column analysis.symbol
     *
     * @return the value of analysis.symbol
     *
     * @mbg.generated
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column analysis.symbol
     *
     * @param symbol the value for analysis.symbol
     *
     * @mbg.generated
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol == null ? null : symbol.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column analysis.status
     *
     * @return the value of analysis.status
     *
     * @mbg.generated
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column analysis.status
     *
     * @param status the value for analysis.status
     *
     * @mbg.generated
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column analysis.price
     *
     * @return the value of analysis.price
     *
     * @mbg.generated
     */
    public Double getPrice() {
        return price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column analysis.price
     *
     * @param price the value for analysis.price
     *
     * @mbg.generated
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column analysis.period
     *
     * @return the value of analysis.period
     *
     * @mbg.generated
     */
    public String getPeriod() {
        return period;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column analysis.period
     *
     * @param period the value for analysis.period
     *
     * @mbg.generated
     */
    public void setPeriod(String period) {
        this.period = period == null ? null : period.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column analysis.kline_time
     *
     * @return the value of analysis.kline_time
     *
     * @mbg.generated
     */
    public String getKlineTime() {
        return klineTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column analysis.kline_time
     *
     * @param klineTime the value for analysis.kline_time
     *
     * @mbg.generated
     */
    public void setKlineTime(String klineTime) {
        this.klineTime = klineTime == null ? null : klineTime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column analysis.create_time
     *
     * @return the value of analysis.create_time
     *
     * @mbg.generated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column analysis.create_time
     *
     * @param createTime the value for analysis.create_time
     *
     * @mbg.generated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}