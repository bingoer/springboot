package wang.raye.springboot.model;

import java.util.ArrayList;
import java.util.List;

public class AlertSettingCriteria {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table alert_setting
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table alert_setting
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table alert_setting
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table alert_setting
     *
     * @mbg.generated
     */
    public AlertSettingCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table alert_setting
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table alert_setting
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table alert_setting
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table alert_setting
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table alert_setting
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table alert_setting
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table alert_setting
     *
     * @mbg.generated
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table alert_setting
     *
     * @mbg.generated
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table alert_setting
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table alert_setting
     *
     * @mbg.generated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table alert_setting
     *
     * @mbg.generated
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andPeriodIsNull() {
            addCriterion("period is null");
            return (Criteria) this;
        }

        public Criteria andPeriodIsNotNull() {
            addCriterion("period is not null");
            return (Criteria) this;
        }

        public Criteria andPeriodEqualTo(String value) {
            addCriterion("period =", value, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodNotEqualTo(String value) {
            addCriterion("period <>", value, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodGreaterThan(String value) {
            addCriterion("period >", value, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodGreaterThanOrEqualTo(String value) {
            addCriterion("period >=", value, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodLessThan(String value) {
            addCriterion("period <", value, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodLessThanOrEqualTo(String value) {
            addCriterion("period <=", value, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodLike(String value) {
            addCriterion("period like", value, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodNotLike(String value) {
            addCriterion("period not like", value, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodIn(List<String> values) {
            addCriterion("period in", values, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodNotIn(List<String> values) {
            addCriterion("period not in", values, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodBetween(String value1, String value2) {
            addCriterion("period between", value1, value2, "period");
            return (Criteria) this;
        }

        public Criteria andPeriodNotBetween(String value1, String value2) {
            addCriterion("period not between", value1, value2, "period");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(String value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(String value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(String value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(String value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(String value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(String value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLike(String value) {
            addCriterion("type like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotLike(String value) {
            addCriterion("type not like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<String> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<String> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(String value1, String value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(String value1, String value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(String value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("status like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("status not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andRatioIsNull() {
            addCriterion("ratio is null");
            return (Criteria) this;
        }

        public Criteria andRatioIsNotNull() {
            addCriterion("ratio is not null");
            return (Criteria) this;
        }

        public Criteria andRatioEqualTo(Double value) {
            addCriterion("ratio =", value, "ratio");
            return (Criteria) this;
        }

        public Criteria andRatioNotEqualTo(Double value) {
            addCriterion("ratio <>", value, "ratio");
            return (Criteria) this;
        }

        public Criteria andRatioGreaterThan(Double value) {
            addCriterion("ratio >", value, "ratio");
            return (Criteria) this;
        }

        public Criteria andRatioGreaterThanOrEqualTo(Double value) {
            addCriterion("ratio >=", value, "ratio");
            return (Criteria) this;
        }

        public Criteria andRatioLessThan(Double value) {
            addCriterion("ratio <", value, "ratio");
            return (Criteria) this;
        }

        public Criteria andRatioLessThanOrEqualTo(Double value) {
            addCriterion("ratio <=", value, "ratio");
            return (Criteria) this;
        }

        public Criteria andRatioIn(List<Double> values) {
            addCriterion("ratio in", values, "ratio");
            return (Criteria) this;
        }

        public Criteria andRatioNotIn(List<Double> values) {
            addCriterion("ratio not in", values, "ratio");
            return (Criteria) this;
        }

        public Criteria andRatioBetween(Double value1, Double value2) {
            addCriterion("ratio between", value1, value2, "ratio");
            return (Criteria) this;
        }

        public Criteria andRatioNotBetween(Double value1, Double value2) {
            addCriterion("ratio not between", value1, value2, "ratio");
            return (Criteria) this;
        }

        public Criteria andRatio2IsNull() {
            addCriterion("ratio2 is null");
            return (Criteria) this;
        }

        public Criteria andRatio2IsNotNull() {
            addCriterion("ratio2 is not null");
            return (Criteria) this;
        }

        public Criteria andRatio2EqualTo(Double value) {
            addCriterion("ratio2 =", value, "ratio2");
            return (Criteria) this;
        }

        public Criteria andRatio2NotEqualTo(Double value) {
            addCriterion("ratio2 <>", value, "ratio2");
            return (Criteria) this;
        }

        public Criteria andRatio2GreaterThan(Double value) {
            addCriterion("ratio2 >", value, "ratio2");
            return (Criteria) this;
        }

        public Criteria andRatio2GreaterThanOrEqualTo(Double value) {
            addCriterion("ratio2 >=", value, "ratio2");
            return (Criteria) this;
        }

        public Criteria andRatio2LessThan(Double value) {
            addCriterion("ratio2 <", value, "ratio2");
            return (Criteria) this;
        }

        public Criteria andRatio2LessThanOrEqualTo(Double value) {
            addCriterion("ratio2 <=", value, "ratio2");
            return (Criteria) this;
        }

        public Criteria andRatio2In(List<Double> values) {
            addCriterion("ratio2 in", values, "ratio2");
            return (Criteria) this;
        }

        public Criteria andRatio2NotIn(List<Double> values) {
            addCriterion("ratio2 not in", values, "ratio2");
            return (Criteria) this;
        }

        public Criteria andRatio2Between(Double value1, Double value2) {
            addCriterion("ratio2 between", value1, value2, "ratio2");
            return (Criteria) this;
        }

        public Criteria andRatio2NotBetween(Double value1, Double value2) {
            addCriterion("ratio2 not between", value1, value2, "ratio2");
            return (Criteria) this;
        }

        public Criteria andRatio3IsNull() {
            addCriterion("ratio3 is null");
            return (Criteria) this;
        }

        public Criteria andRatio3IsNotNull() {
            addCriterion("ratio3 is not null");
            return (Criteria) this;
        }

        public Criteria andRatio3EqualTo(Double value) {
            addCriterion("ratio3 =", value, "ratio3");
            return (Criteria) this;
        }

        public Criteria andRatio3NotEqualTo(Double value) {
            addCriterion("ratio3 <>", value, "ratio3");
            return (Criteria) this;
        }

        public Criteria andRatio3GreaterThan(Double value) {
            addCriterion("ratio3 >", value, "ratio3");
            return (Criteria) this;
        }

        public Criteria andRatio3GreaterThanOrEqualTo(Double value) {
            addCriterion("ratio3 >=", value, "ratio3");
            return (Criteria) this;
        }

        public Criteria andRatio3LessThan(Double value) {
            addCriterion("ratio3 <", value, "ratio3");
            return (Criteria) this;
        }

        public Criteria andRatio3LessThanOrEqualTo(Double value) {
            addCriterion("ratio3 <=", value, "ratio3");
            return (Criteria) this;
        }

        public Criteria andRatio3In(List<Double> values) {
            addCriterion("ratio3 in", values, "ratio3");
            return (Criteria) this;
        }

        public Criteria andRatio3NotIn(List<Double> values) {
            addCriterion("ratio3 not in", values, "ratio3");
            return (Criteria) this;
        }

        public Criteria andRatio3Between(Double value1, Double value2) {
            addCriterion("ratio3 between", value1, value2, "ratio3");
            return (Criteria) this;
        }

        public Criteria andRatio3NotBetween(Double value1, Double value2) {
            addCriterion("ratio3 not between", value1, value2, "ratio3");
            return (Criteria) this;
        }

        public Criteria andRatio4IsNull() {
            addCriterion("ratio4 is null");
            return (Criteria) this;
        }

        public Criteria andRatio4IsNotNull() {
            addCriterion("ratio4 is not null");
            return (Criteria) this;
        }

        public Criteria andRatio4EqualTo(Double value) {
            addCriterion("ratio4 =", value, "ratio4");
            return (Criteria) this;
        }

        public Criteria andRatio4NotEqualTo(Double value) {
            addCriterion("ratio4 <>", value, "ratio4");
            return (Criteria) this;
        }

        public Criteria andRatio4GreaterThan(Double value) {
            addCriterion("ratio4 >", value, "ratio4");
            return (Criteria) this;
        }

        public Criteria andRatio4GreaterThanOrEqualTo(Double value) {
            addCriterion("ratio4 >=", value, "ratio4");
            return (Criteria) this;
        }

        public Criteria andRatio4LessThan(Double value) {
            addCriterion("ratio4 <", value, "ratio4");
            return (Criteria) this;
        }

        public Criteria andRatio4LessThanOrEqualTo(Double value) {
            addCriterion("ratio4 <=", value, "ratio4");
            return (Criteria) this;
        }

        public Criteria andRatio4In(List<Double> values) {
            addCriterion("ratio4 in", values, "ratio4");
            return (Criteria) this;
        }

        public Criteria andRatio4NotIn(List<Double> values) {
            addCriterion("ratio4 not in", values, "ratio4");
            return (Criteria) this;
        }

        public Criteria andRatio4Between(Double value1, Double value2) {
            addCriterion("ratio4 between", value1, value2, "ratio4");
            return (Criteria) this;
        }

        public Criteria andRatio4NotBetween(Double value1, Double value2) {
            addCriterion("ratio4 not between", value1, value2, "ratio4");
            return (Criteria) this;
        }

        public Criteria andOpenflgIsNull() {
            addCriterion("openflg is null");
            return (Criteria) this;
        }

        public Criteria andOpenflgIsNotNull() {
            addCriterion("openflg is not null");
            return (Criteria) this;
        }

        public Criteria andOpenflgEqualTo(String value) {
            addCriterion("openflg =", value, "openflg");
            return (Criteria) this;
        }

        public Criteria andOpenflgNotEqualTo(String value) {
            addCriterion("openflg <>", value, "openflg");
            return (Criteria) this;
        }

        public Criteria andOpenflgGreaterThan(String value) {
            addCriterion("openflg >", value, "openflg");
            return (Criteria) this;
        }

        public Criteria andOpenflgGreaterThanOrEqualTo(String value) {
            addCriterion("openflg >=", value, "openflg");
            return (Criteria) this;
        }

        public Criteria andOpenflgLessThan(String value) {
            addCriterion("openflg <", value, "openflg");
            return (Criteria) this;
        }

        public Criteria andOpenflgLessThanOrEqualTo(String value) {
            addCriterion("openflg <=", value, "openflg");
            return (Criteria) this;
        }

        public Criteria andOpenflgLike(String value) {
            addCriterion("openflg like", value, "openflg");
            return (Criteria) this;
        }

        public Criteria andOpenflgNotLike(String value) {
            addCriterion("openflg not like", value, "openflg");
            return (Criteria) this;
        }

        public Criteria andOpenflgIn(List<String> values) {
            addCriterion("openflg in", values, "openflg");
            return (Criteria) this;
        }

        public Criteria andOpenflgNotIn(List<String> values) {
            addCriterion("openflg not in", values, "openflg");
            return (Criteria) this;
        }

        public Criteria andOpenflgBetween(String value1, String value2) {
            addCriterion("openflg between", value1, value2, "openflg");
            return (Criteria) this;
        }

        public Criteria andOpenflgNotBetween(String value1, String value2) {
            addCriterion("openflg not between", value1, value2, "openflg");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table alert_setting
     *
     * @mbg.generated do_not_delete_during_merge
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table alert_setting
     *
     * @mbg.generated
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}