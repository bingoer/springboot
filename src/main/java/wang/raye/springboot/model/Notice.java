package wang.raye.springboot.model;

public class Notice {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column notice.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column notice.notice_id
     *
     * @mbg.generated
     */
    private String noticeId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column notice.lang
     *
     * @mbg.generated
     */
    private String lang;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column notice.zh_name
     *
     * @mbg.generated
     */
    private String zhName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column notice.from_market
     *
     * @mbg.generated
     */
    private String fromMarket;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column notice.timestamp
     *
     * @mbg.generated
     */
    private String timestamp;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column notice.title
     *
     * @mbg.generated
     */
    private String title;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column notice.id
     *
     * @return the value of notice.id
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column notice.id
     *
     * @param id the value for notice.id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column notice.notice_id
     *
     * @return the value of notice.notice_id
     *
     * @mbg.generated
     */
    public String getNoticeId() {
        return noticeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column notice.notice_id
     *
     * @param noticeId the value for notice.notice_id
     *
     * @mbg.generated
     */
    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId == null ? null : noticeId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column notice.lang
     *
     * @return the value of notice.lang
     *
     * @mbg.generated
     */
    public String getLang() {
        return lang;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column notice.lang
     *
     * @param lang the value for notice.lang
     *
     * @mbg.generated
     */
    public void setLang(String lang) {
        this.lang = lang == null ? null : lang.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column notice.zh_name
     *
     * @return the value of notice.zh_name
     *
     * @mbg.generated
     */
    public String getZhName() {
        return zhName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column notice.zh_name
     *
     * @param zhName the value for notice.zh_name
     *
     * @mbg.generated
     */
    public void setZhName(String zhName) {
        this.zhName = zhName == null ? null : zhName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column notice.from_market
     *
     * @return the value of notice.from_market
     *
     * @mbg.generated
     */
    public String getFromMarket() {
        return fromMarket;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column notice.from_market
     *
     * @param fromMarket the value for notice.from_market
     *
     * @mbg.generated
     */
    public void setFromMarket(String fromMarket) {
        this.fromMarket = fromMarket == null ? null : fromMarket.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column notice.timestamp
     *
     * @return the value of notice.timestamp
     *
     * @mbg.generated
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column notice.timestamp
     *
     * @param timestamp the value for notice.timestamp
     *
     * @mbg.generated
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp == null ? null : timestamp.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column notice.title
     *
     * @return the value of notice.title
     *
     * @mbg.generated
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column notice.title
     *
     * @param title the value for notice.title
     *
     * @mbg.generated
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }
}