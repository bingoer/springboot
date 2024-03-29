package wang.raye.springboot.model;

import java.util.Date;

public class AnalysisHistory {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column analysis_history.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column analysis_history.exchange
     *
     * @mbg.generated
     */
    private String exchange;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column analysis_history.symbol
     *
     * @mbg.generated
     */
    private String symbol;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column analysis_history.status
     *
     * @mbg.generated
     */
    private String status;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column analysis_history.price
     *
     * @mbg.generated
     */
    private Double price;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column analysis_history.period
     *
     * @mbg.generated
     */
    private String period;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column analysis_history.kline_time
     *
     * @mbg.generated
     */
    private String klineTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column analysis_history.create_time
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column analysis_history.id
     *
     * @return the value of analysis_history.id
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column analysis_history.id
     *
     * @param id the value for analysis_history.id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column analysis_history.exchange
     *
     * @return the value of analysis_history.exchange
     *
     * @mbg.generated
     */
    public String getExchange() {
        return exchange;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column analysis_history.exchange
     *
     * @param exchange the value for analysis_history.exchange
     *
     * @mbg.generated
     */
    public void setExchange(String exchange) {
        this.exchange = exchange == null ? null : exchange.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column analysis_history.symbol
     *
     * @return the value of analysis_history.symbol
     *
     * @mbg.generated
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column analysis_history.symbol
     *
     * @param symbol the value for analysis_history.symbol
     *
     * @mbg.generated
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol == null ? null : symbol.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column analysis_history.status
     *
     * @return the value of analysis_history.status
     *
     * @mbg.generated
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column analysis_history.status
     *
     * @param status the value for analysis_history.status
     *
     * @mbg.generated
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column analysis_history.price
     *
     * @return the value of analysis_history.price
     *
     * @mbg.generated
     */
    public Double getPrice() {
        return price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column analysis_history.price
     *
     * @param price the value for analysis_history.price
     *
     * @mbg.generated
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column analysis_history.period
     *
     * @return the value of analysis_history.period
     *
     * @mbg.generated
     */
    public String getPeriod() {
        return period;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column analysis_history.period
     *
     * @param period the value for analysis_history.period
     *
     * @mbg.generated
     */
    public void setPeriod(String period) {
        this.period = period == null ? null : period.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column analysis_history.kline_time
     *
     * @return the value of analysis_history.kline_time
     *
     * @mbg.generated
     */
    public String getKlineTime() {
        return klineTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column analysis_history.kline_time
     *
     * @param klineTime the value for analysis_history.kline_time
     *
     * @mbg.generated
     */
    public void setKlineTime(String klineTime) {
        this.klineTime = klineTime == null ? null : klineTime.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column analysis_history.create_time
     *
     * @return the value of analysis_history.create_time
     *
     * @mbg.generated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column analysis_history.create_time
     *
     * @param createTime the value for analysis_history.create_time
     *
     * @mbg.generated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}