package wang.raye.springboot.model;

import java.util.ArrayList;
import java.util.List;

public class PairsNewCriteria {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table pairs_new
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table pairs_new
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table pairs_new
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pairs_new
     *
     * @mbg.generated
     */
    public PairsNewCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pairs_new
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pairs_new
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pairs_new
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pairs_new
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pairs_new
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pairs_new
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pairs_new
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
     * This method corresponds to the database table pairs_new
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
     * This method corresponds to the database table pairs_new
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pairs_new
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
     * This class corresponds to the database table pairs_new
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

        public Criteria andMarketIsNull() {
            addCriterion("market is null");
            return (Criteria) this;
        }

        public Criteria andMarketIsNotNull() {
            addCriterion("market is not null");
            return (Criteria) this;
        }

        public Criteria andMarketEqualTo(String value) {
            addCriterion("market =", value, "market");
            return (Criteria) this;
        }

        public Criteria andMarketNotEqualTo(String value) {
            addCriterion("market <>", value, "market");
            return (Criteria) this;
        }

        public Criteria andMarketGreaterThan(String value) {
            addCriterion("market >", value, "market");
            return (Criteria) this;
        }

        public Criteria andMarketGreaterThanOrEqualTo(String value) {
            addCriterion("market >=", value, "market");
            return (Criteria) this;
        }

        public Criteria andMarketLessThan(String value) {
            addCriterion("market <", value, "market");
            return (Criteria) this;
        }

        public Criteria andMarketLessThanOrEqualTo(String value) {
            addCriterion("market <=", value, "market");
            return (Criteria) this;
        }

        public Criteria andMarketLike(String value) {
            addCriterion("market like", value, "market");
            return (Criteria) this;
        }

        public Criteria andMarketNotLike(String value) {
            addCriterion("market not like", value, "market");
            return (Criteria) this;
        }

        public Criteria andMarketIn(List<String> values) {
            addCriterion("market in", values, "market");
            return (Criteria) this;
        }

        public Criteria andMarketNotIn(List<String> values) {
            addCriterion("market not in", values, "market");
            return (Criteria) this;
        }

        public Criteria andMarketBetween(String value1, String value2) {
            addCriterion("market between", value1, value2, "market");
            return (Criteria) this;
        }

        public Criteria andMarketNotBetween(String value1, String value2) {
            addCriterion("market not between", value1, value2, "market");
            return (Criteria) this;
        }

        public Criteria andSymbolIsNull() {
            addCriterion("symbol is null");
            return (Criteria) this;
        }

        public Criteria andSymbolIsNotNull() {
            addCriterion("symbol is not null");
            return (Criteria) this;
        }

        public Criteria andSymbolEqualTo(String value) {
            addCriterion("symbol =", value, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolNotEqualTo(String value) {
            addCriterion("symbol <>", value, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolGreaterThan(String value) {
            addCriterion("symbol >", value, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolGreaterThanOrEqualTo(String value) {
            addCriterion("symbol >=", value, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolLessThan(String value) {
            addCriterion("symbol <", value, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolLessThanOrEqualTo(String value) {
            addCriterion("symbol <=", value, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolLike(String value) {
            addCriterion("symbol like", value, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolNotLike(String value) {
            addCriterion("symbol not like", value, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolIn(List<String> values) {
            addCriterion("symbol in", values, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolNotIn(List<String> values) {
            addCriterion("symbol not in", values, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolBetween(String value1, String value2) {
            addCriterion("symbol between", value1, value2, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolNotBetween(String value1, String value2) {
            addCriterion("symbol not between", value1, value2, "symbol");
            return (Criteria) this;
        }

        public Criteria andSymbolIdIsNull() {
            addCriterion("symbol_id is null");
            return (Criteria) this;
        }

        public Criteria andSymbolIdIsNotNull() {
            addCriterion("symbol_id is not null");
            return (Criteria) this;
        }

        public Criteria andSymbolIdEqualTo(String value) {
            addCriterion("symbol_id =", value, "symbolId");
            return (Criteria) this;
        }

        public Criteria andSymbolIdNotEqualTo(String value) {
            addCriterion("symbol_id <>", value, "symbolId");
            return (Criteria) this;
        }

        public Criteria andSymbolIdGreaterThan(String value) {
            addCriterion("symbol_id >", value, "symbolId");
            return (Criteria) this;
        }

        public Criteria andSymbolIdGreaterThanOrEqualTo(String value) {
            addCriterion("symbol_id >=", value, "symbolId");
            return (Criteria) this;
        }

        public Criteria andSymbolIdLessThan(String value) {
            addCriterion("symbol_id <", value, "symbolId");
            return (Criteria) this;
        }

        public Criteria andSymbolIdLessThanOrEqualTo(String value) {
            addCriterion("symbol_id <=", value, "symbolId");
            return (Criteria) this;
        }

        public Criteria andSymbolIdLike(String value) {
            addCriterion("symbol_id like", value, "symbolId");
            return (Criteria) this;
        }

        public Criteria andSymbolIdNotLike(String value) {
            addCriterion("symbol_id not like", value, "symbolId");
            return (Criteria) this;
        }

        public Criteria andSymbolIdIn(List<String> values) {
            addCriterion("symbol_id in", values, "symbolId");
            return (Criteria) this;
        }

        public Criteria andSymbolIdNotIn(List<String> values) {
            addCriterion("symbol_id not in", values, "symbolId");
            return (Criteria) this;
        }

        public Criteria andSymbolIdBetween(String value1, String value2) {
            addCriterion("symbol_id between", value1, value2, "symbolId");
            return (Criteria) this;
        }

        public Criteria andSymbolIdNotBetween(String value1, String value2) {
            addCriterion("symbol_id not between", value1, value2, "symbolId");
            return (Criteria) this;
        }

        public Criteria andCurrencyIsNull() {
            addCriterion("currency is null");
            return (Criteria) this;
        }

        public Criteria andCurrencyIsNotNull() {
            addCriterion("currency is not null");
            return (Criteria) this;
        }

        public Criteria andCurrencyEqualTo(String value) {
            addCriterion("currency =", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyNotEqualTo(String value) {
            addCriterion("currency <>", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyGreaterThan(String value) {
            addCriterion("currency >", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyGreaterThanOrEqualTo(String value) {
            addCriterion("currency >=", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyLessThan(String value) {
            addCriterion("currency <", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyLessThanOrEqualTo(String value) {
            addCriterion("currency <=", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyLike(String value) {
            addCriterion("currency like", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyNotLike(String value) {
            addCriterion("currency not like", value, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyIn(List<String> values) {
            addCriterion("currency in", values, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyNotIn(List<String> values) {
            addCriterion("currency not in", values, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyBetween(String value1, String value2) {
            addCriterion("currency between", value1, value2, "currency");
            return (Criteria) this;
        }

        public Criteria andCurrencyNotBetween(String value1, String value2) {
            addCriterion("currency not between", value1, value2, "currency");
            return (Criteria) this;
        }

        public Criteria andCoinInfoIsNull() {
            addCriterion("coin_info is null");
            return (Criteria) this;
        }

        public Criteria andCoinInfoIsNotNull() {
            addCriterion("coin_info is not null");
            return (Criteria) this;
        }

        public Criteria andCoinInfoEqualTo(String value) {
            addCriterion("coin_info =", value, "coinInfo");
            return (Criteria) this;
        }

        public Criteria andCoinInfoNotEqualTo(String value) {
            addCriterion("coin_info <>", value, "coinInfo");
            return (Criteria) this;
        }

        public Criteria andCoinInfoGreaterThan(String value) {
            addCriterion("coin_info >", value, "coinInfo");
            return (Criteria) this;
        }

        public Criteria andCoinInfoGreaterThanOrEqualTo(String value) {
            addCriterion("coin_info >=", value, "coinInfo");
            return (Criteria) this;
        }

        public Criteria andCoinInfoLessThan(String value) {
            addCriterion("coin_info <", value, "coinInfo");
            return (Criteria) this;
        }

        public Criteria andCoinInfoLessThanOrEqualTo(String value) {
            addCriterion("coin_info <=", value, "coinInfo");
            return (Criteria) this;
        }

        public Criteria andCoinInfoLike(String value) {
            addCriterion("coin_info like", value, "coinInfo");
            return (Criteria) this;
        }

        public Criteria andCoinInfoNotLike(String value) {
            addCriterion("coin_info not like", value, "coinInfo");
            return (Criteria) this;
        }

        public Criteria andCoinInfoIn(List<String> values) {
            addCriterion("coin_info in", values, "coinInfo");
            return (Criteria) this;
        }

        public Criteria andCoinInfoNotIn(List<String> values) {
            addCriterion("coin_info not in", values, "coinInfo");
            return (Criteria) this;
        }

        public Criteria andCoinInfoBetween(String value1, String value2) {
            addCriterion("coin_info between", value1, value2, "coinInfo");
            return (Criteria) this;
        }

        public Criteria andCoinInfoNotBetween(String value1, String value2) {
            addCriterion("coin_info not between", value1, value2, "coinInfo");
            return (Criteria) this;
        }

        public Criteria andCoinUrlIsNull() {
            addCriterion("coin_url is null");
            return (Criteria) this;
        }

        public Criteria andCoinUrlIsNotNull() {
            addCriterion("coin_url is not null");
            return (Criteria) this;
        }

        public Criteria andCoinUrlEqualTo(String value) {
            addCriterion("coin_url =", value, "coinUrl");
            return (Criteria) this;
        }

        public Criteria andCoinUrlNotEqualTo(String value) {
            addCriterion("coin_url <>", value, "coinUrl");
            return (Criteria) this;
        }

        public Criteria andCoinUrlGreaterThan(String value) {
            addCriterion("coin_url >", value, "coinUrl");
            return (Criteria) this;
        }

        public Criteria andCoinUrlGreaterThanOrEqualTo(String value) {
            addCriterion("coin_url >=", value, "coinUrl");
            return (Criteria) this;
        }

        public Criteria andCoinUrlLessThan(String value) {
            addCriterion("coin_url <", value, "coinUrl");
            return (Criteria) this;
        }

        public Criteria andCoinUrlLessThanOrEqualTo(String value) {
            addCriterion("coin_url <=", value, "coinUrl");
            return (Criteria) this;
        }

        public Criteria andCoinUrlLike(String value) {
            addCriterion("coin_url like", value, "coinUrl");
            return (Criteria) this;
        }

        public Criteria andCoinUrlNotLike(String value) {
            addCriterion("coin_url not like", value, "coinUrl");
            return (Criteria) this;
        }

        public Criteria andCoinUrlIn(List<String> values) {
            addCriterion("coin_url in", values, "coinUrl");
            return (Criteria) this;
        }

        public Criteria andCoinUrlNotIn(List<String> values) {
            addCriterion("coin_url not in", values, "coinUrl");
            return (Criteria) this;
        }

        public Criteria andCoinUrlBetween(String value1, String value2) {
            addCriterion("coin_url between", value1, value2, "coinUrl");
            return (Criteria) this;
        }

        public Criteria andCoinUrlNotBetween(String value1, String value2) {
            addCriterion("coin_url not between", value1, value2, "coinUrl");
            return (Criteria) this;
        }

        public Criteria andChargeAtIsNull() {
            addCriterion("charge_at is null");
            return (Criteria) this;
        }

        public Criteria andChargeAtIsNotNull() {
            addCriterion("charge_at is not null");
            return (Criteria) this;
        }

        public Criteria andChargeAtEqualTo(String value) {
            addCriterion("charge_at =", value, "chargeAt");
            return (Criteria) this;
        }

        public Criteria andChargeAtNotEqualTo(String value) {
            addCriterion("charge_at <>", value, "chargeAt");
            return (Criteria) this;
        }

        public Criteria andChargeAtGreaterThan(String value) {
            addCriterion("charge_at >", value, "chargeAt");
            return (Criteria) this;
        }

        public Criteria andChargeAtGreaterThanOrEqualTo(String value) {
            addCriterion("charge_at >=", value, "chargeAt");
            return (Criteria) this;
        }

        public Criteria andChargeAtLessThan(String value) {
            addCriterion("charge_at <", value, "chargeAt");
            return (Criteria) this;
        }

        public Criteria andChargeAtLessThanOrEqualTo(String value) {
            addCriterion("charge_at <=", value, "chargeAt");
            return (Criteria) this;
        }

        public Criteria andChargeAtLike(String value) {
            addCriterion("charge_at like", value, "chargeAt");
            return (Criteria) this;
        }

        public Criteria andChargeAtNotLike(String value) {
            addCriterion("charge_at not like", value, "chargeAt");
            return (Criteria) this;
        }

        public Criteria andChargeAtIn(List<String> values) {
            addCriterion("charge_at in", values, "chargeAt");
            return (Criteria) this;
        }

        public Criteria andChargeAtNotIn(List<String> values) {
            addCriterion("charge_at not in", values, "chargeAt");
            return (Criteria) this;
        }

        public Criteria andChargeAtBetween(String value1, String value2) {
            addCriterion("charge_at between", value1, value2, "chargeAt");
            return (Criteria) this;
        }

        public Criteria andChargeAtNotBetween(String value1, String value2) {
            addCriterion("charge_at not between", value1, value2, "chargeAt");
            return (Criteria) this;
        }

        public Criteria andWithdrawAtIsNull() {
            addCriterion("withdraw_at is null");
            return (Criteria) this;
        }

        public Criteria andWithdrawAtIsNotNull() {
            addCriterion("withdraw_at is not null");
            return (Criteria) this;
        }

        public Criteria andWithdrawAtEqualTo(String value) {
            addCriterion("withdraw_at =", value, "withdrawAt");
            return (Criteria) this;
        }

        public Criteria andWithdrawAtNotEqualTo(String value) {
            addCriterion("withdraw_at <>", value, "withdrawAt");
            return (Criteria) this;
        }

        public Criteria andWithdrawAtGreaterThan(String value) {
            addCriterion("withdraw_at >", value, "withdrawAt");
            return (Criteria) this;
        }

        public Criteria andWithdrawAtGreaterThanOrEqualTo(String value) {
            addCriterion("withdraw_at >=", value, "withdrawAt");
            return (Criteria) this;
        }

        public Criteria andWithdrawAtLessThan(String value) {
            addCriterion("withdraw_at <", value, "withdrawAt");
            return (Criteria) this;
        }

        public Criteria andWithdrawAtLessThanOrEqualTo(String value) {
            addCriterion("withdraw_at <=", value, "withdrawAt");
            return (Criteria) this;
        }

        public Criteria andWithdrawAtLike(String value) {
            addCriterion("withdraw_at like", value, "withdrawAt");
            return (Criteria) this;
        }

        public Criteria andWithdrawAtNotLike(String value) {
            addCriterion("withdraw_at not like", value, "withdrawAt");
            return (Criteria) this;
        }

        public Criteria andWithdrawAtIn(List<String> values) {
            addCriterion("withdraw_at in", values, "withdrawAt");
            return (Criteria) this;
        }

        public Criteria andWithdrawAtNotIn(List<String> values) {
            addCriterion("withdraw_at not in", values, "withdrawAt");
            return (Criteria) this;
        }

        public Criteria andWithdrawAtBetween(String value1, String value2) {
            addCriterion("withdraw_at between", value1, value2, "withdrawAt");
            return (Criteria) this;
        }

        public Criteria andWithdrawAtNotBetween(String value1, String value2) {
            addCriterion("withdraw_at not between", value1, value2, "withdrawAt");
            return (Criteria) this;
        }

        public Criteria andEndAtIsNull() {
            addCriterion("end_at is null");
            return (Criteria) this;
        }

        public Criteria andEndAtIsNotNull() {
            addCriterion("end_at is not null");
            return (Criteria) this;
        }

        public Criteria andEndAtEqualTo(String value) {
            addCriterion("end_at =", value, "endAt");
            return (Criteria) this;
        }

        public Criteria andEndAtNotEqualTo(String value) {
            addCriterion("end_at <>", value, "endAt");
            return (Criteria) this;
        }

        public Criteria andEndAtGreaterThan(String value) {
            addCriterion("end_at >", value, "endAt");
            return (Criteria) this;
        }

        public Criteria andEndAtGreaterThanOrEqualTo(String value) {
            addCriterion("end_at >=", value, "endAt");
            return (Criteria) this;
        }

        public Criteria andEndAtLessThan(String value) {
            addCriterion("end_at <", value, "endAt");
            return (Criteria) this;
        }

        public Criteria andEndAtLessThanOrEqualTo(String value) {
            addCriterion("end_at <=", value, "endAt");
            return (Criteria) this;
        }

        public Criteria andEndAtLike(String value) {
            addCriterion("end_at like", value, "endAt");
            return (Criteria) this;
        }

        public Criteria andEndAtNotLike(String value) {
            addCriterion("end_at not like", value, "endAt");
            return (Criteria) this;
        }

        public Criteria andEndAtIn(List<String> values) {
            addCriterion("end_at in", values, "endAt");
            return (Criteria) this;
        }

        public Criteria andEndAtNotIn(List<String> values) {
            addCriterion("end_at not in", values, "endAt");
            return (Criteria) this;
        }

        public Criteria andEndAtBetween(String value1, String value2) {
            addCriterion("end_at between", value1, value2, "endAt");
            return (Criteria) this;
        }

        public Criteria andEndAtNotBetween(String value1, String value2) {
            addCriterion("end_at not between", value1, value2, "endAt");
            return (Criteria) this;
        }

        public Criteria andTradeAtIsNull() {
            addCriterion("trade_at is null");
            return (Criteria) this;
        }

        public Criteria andTradeAtIsNotNull() {
            addCriterion("trade_at is not null");
            return (Criteria) this;
        }

        public Criteria andTradeAtEqualTo(String value) {
            addCriterion("trade_at =", value, "tradeAt");
            return (Criteria) this;
        }

        public Criteria andTradeAtNotEqualTo(String value) {
            addCriterion("trade_at <>", value, "tradeAt");
            return (Criteria) this;
        }

        public Criteria andTradeAtGreaterThan(String value) {
            addCriterion("trade_at >", value, "tradeAt");
            return (Criteria) this;
        }

        public Criteria andTradeAtGreaterThanOrEqualTo(String value) {
            addCriterion("trade_at >=", value, "tradeAt");
            return (Criteria) this;
        }

        public Criteria andTradeAtLessThan(String value) {
            addCriterion("trade_at <", value, "tradeAt");
            return (Criteria) this;
        }

        public Criteria andTradeAtLessThanOrEqualTo(String value) {
            addCriterion("trade_at <=", value, "tradeAt");
            return (Criteria) this;
        }

        public Criteria andTradeAtLike(String value) {
            addCriterion("trade_at like", value, "tradeAt");
            return (Criteria) this;
        }

        public Criteria andTradeAtNotLike(String value) {
            addCriterion("trade_at not like", value, "tradeAt");
            return (Criteria) this;
        }

        public Criteria andTradeAtIn(List<String> values) {
            addCriterion("trade_at in", values, "tradeAt");
            return (Criteria) this;
        }

        public Criteria andTradeAtNotIn(List<String> values) {
            addCriterion("trade_at not in", values, "tradeAt");
            return (Criteria) this;
        }

        public Criteria andTradeAtBetween(String value1, String value2) {
            addCriterion("trade_at between", value1, value2, "tradeAt");
            return (Criteria) this;
        }

        public Criteria andTradeAtNotBetween(String value1, String value2) {
            addCriterion("trade_at not between", value1, value2, "tradeAt");
            return (Criteria) this;
        }

        public Criteria andPriceIsNull() {
            addCriterion("price is null");
            return (Criteria) this;
        }

        public Criteria andPriceIsNotNull() {
            addCriterion("price is not null");
            return (Criteria) this;
        }

        public Criteria andPriceEqualTo(String value) {
            addCriterion("price =", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotEqualTo(String value) {
            addCriterion("price <>", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThan(String value) {
            addCriterion("price >", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThanOrEqualTo(String value) {
            addCriterion("price >=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThan(String value) {
            addCriterion("price <", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThanOrEqualTo(String value) {
            addCriterion("price <=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLike(String value) {
            addCriterion("price like", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotLike(String value) {
            addCriterion("price not like", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceIn(List<String> values) {
            addCriterion("price in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotIn(List<String> values) {
            addCriterion("price not in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceBetween(String value1, String value2) {
            addCriterion("price between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotBetween(String value1, String value2) {
            addCriterion("price not between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andChange1dIsNull() {
            addCriterion("change1d is null");
            return (Criteria) this;
        }

        public Criteria andChange1dIsNotNull() {
            addCriterion("change1d is not null");
            return (Criteria) this;
        }

        public Criteria andChange1dEqualTo(String value) {
            addCriterion("change1d =", value, "change1d");
            return (Criteria) this;
        }

        public Criteria andChange1dNotEqualTo(String value) {
            addCriterion("change1d <>", value, "change1d");
            return (Criteria) this;
        }

        public Criteria andChange1dGreaterThan(String value) {
            addCriterion("change1d >", value, "change1d");
            return (Criteria) this;
        }

        public Criteria andChange1dGreaterThanOrEqualTo(String value) {
            addCriterion("change1d >=", value, "change1d");
            return (Criteria) this;
        }

        public Criteria andChange1dLessThan(String value) {
            addCriterion("change1d <", value, "change1d");
            return (Criteria) this;
        }

        public Criteria andChange1dLessThanOrEqualTo(String value) {
            addCriterion("change1d <=", value, "change1d");
            return (Criteria) this;
        }

        public Criteria andChange1dLike(String value) {
            addCriterion("change1d like", value, "change1d");
            return (Criteria) this;
        }

        public Criteria andChange1dNotLike(String value) {
            addCriterion("change1d not like", value, "change1d");
            return (Criteria) this;
        }

        public Criteria andChange1dIn(List<String> values) {
            addCriterion("change1d in", values, "change1d");
            return (Criteria) this;
        }

        public Criteria andChange1dNotIn(List<String> values) {
            addCriterion("change1d not in", values, "change1d");
            return (Criteria) this;
        }

        public Criteria andChange1dBetween(String value1, String value2) {
            addCriterion("change1d between", value1, value2, "change1d");
            return (Criteria) this;
        }

        public Criteria andChange1dNotBetween(String value1, String value2) {
            addCriterion("change1d not between", value1, value2, "change1d");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table pairs_new
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
     * This class corresponds to the database table pairs_new
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