package wang.raye.springboot.model;

import java.util.ArrayList;
import java.util.List;

public class PairsCriteria {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table pairs
     *
     * @mbg.generated
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table pairs
     *
     * @mbg.generated
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table pairs
     *
     * @mbg.generated
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pairs
     *
     * @mbg.generated
     */
    public PairsCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pairs
     *
     * @mbg.generated
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pairs
     *
     * @mbg.generated
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pairs
     *
     * @mbg.generated
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pairs
     *
     * @mbg.generated
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pairs
     *
     * @mbg.generated
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pairs
     *
     * @mbg.generated
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pairs
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
     * This method corresponds to the database table pairs
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
     * This method corresponds to the database table pairs
     *
     * @mbg.generated
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pairs
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
     * This class corresponds to the database table pairs
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

        public Criteria andMarketAliasIsNull() {
            addCriterion("market_alias is null");
            return (Criteria) this;
        }

        public Criteria andMarketAliasIsNotNull() {
            addCriterion("market_alias is not null");
            return (Criteria) this;
        }

        public Criteria andMarketAliasEqualTo(String value) {
            addCriterion("market_alias =", value, "marketAlias");
            return (Criteria) this;
        }

        public Criteria andMarketAliasNotEqualTo(String value) {
            addCriterion("market_alias <>", value, "marketAlias");
            return (Criteria) this;
        }

        public Criteria andMarketAliasGreaterThan(String value) {
            addCriterion("market_alias >", value, "marketAlias");
            return (Criteria) this;
        }

        public Criteria andMarketAliasGreaterThanOrEqualTo(String value) {
            addCriterion("market_alias >=", value, "marketAlias");
            return (Criteria) this;
        }

        public Criteria andMarketAliasLessThan(String value) {
            addCriterion("market_alias <", value, "marketAlias");
            return (Criteria) this;
        }

        public Criteria andMarketAliasLessThanOrEqualTo(String value) {
            addCriterion("market_alias <=", value, "marketAlias");
            return (Criteria) this;
        }

        public Criteria andMarketAliasLike(String value) {
            addCriterion("market_alias like", value, "marketAlias");
            return (Criteria) this;
        }

        public Criteria andMarketAliasNotLike(String value) {
            addCriterion("market_alias not like", value, "marketAlias");
            return (Criteria) this;
        }

        public Criteria andMarketAliasIn(List<String> values) {
            addCriterion("market_alias in", values, "marketAlias");
            return (Criteria) this;
        }

        public Criteria andMarketAliasNotIn(List<String> values) {
            addCriterion("market_alias not in", values, "marketAlias");
            return (Criteria) this;
        }

        public Criteria andMarketAliasBetween(String value1, String value2) {
            addCriterion("market_alias between", value1, value2, "marketAlias");
            return (Criteria) this;
        }

        public Criteria andMarketAliasNotBetween(String value1, String value2) {
            addCriterion("market_alias not between", value1, value2, "marketAlias");
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

        public Criteria andSymbolAliasIsNull() {
            addCriterion("symbol_alias is null");
            return (Criteria) this;
        }

        public Criteria andSymbolAliasIsNotNull() {
            addCriterion("symbol_alias is not null");
            return (Criteria) this;
        }

        public Criteria andSymbolAliasEqualTo(String value) {
            addCriterion("symbol_alias =", value, "symbolAlias");
            return (Criteria) this;
        }

        public Criteria andSymbolAliasNotEqualTo(String value) {
            addCriterion("symbol_alias <>", value, "symbolAlias");
            return (Criteria) this;
        }

        public Criteria andSymbolAliasGreaterThan(String value) {
            addCriterion("symbol_alias >", value, "symbolAlias");
            return (Criteria) this;
        }

        public Criteria andSymbolAliasGreaterThanOrEqualTo(String value) {
            addCriterion("symbol_alias >=", value, "symbolAlias");
            return (Criteria) this;
        }

        public Criteria andSymbolAliasLessThan(String value) {
            addCriterion("symbol_alias <", value, "symbolAlias");
            return (Criteria) this;
        }

        public Criteria andSymbolAliasLessThanOrEqualTo(String value) {
            addCriterion("symbol_alias <=", value, "symbolAlias");
            return (Criteria) this;
        }

        public Criteria andSymbolAliasLike(String value) {
            addCriterion("symbol_alias like", value, "symbolAlias");
            return (Criteria) this;
        }

        public Criteria andSymbolAliasNotLike(String value) {
            addCriterion("symbol_alias not like", value, "symbolAlias");
            return (Criteria) this;
        }

        public Criteria andSymbolAliasIn(List<String> values) {
            addCriterion("symbol_alias in", values, "symbolAlias");
            return (Criteria) this;
        }

        public Criteria andSymbolAliasNotIn(List<String> values) {
            addCriterion("symbol_alias not in", values, "symbolAlias");
            return (Criteria) this;
        }

        public Criteria andSymbolAliasBetween(String value1, String value2) {
            addCriterion("symbol_alias between", value1, value2, "symbolAlias");
            return (Criteria) this;
        }

        public Criteria andSymbolAliasNotBetween(String value1, String value2) {
            addCriterion("symbol_alias not between", value1, value2, "symbolAlias");
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

        public Criteria andHasKilneIsNull() {
            addCriterion("has_kilne is null");
            return (Criteria) this;
        }

        public Criteria andHasKilneIsNotNull() {
            addCriterion("has_kilne is not null");
            return (Criteria) this;
        }

        public Criteria andHasKilneEqualTo(String value) {
            addCriterion("has_kilne =", value, "hasKilne");
            return (Criteria) this;
        }

        public Criteria andHasKilneNotEqualTo(String value) {
            addCriterion("has_kilne <>", value, "hasKilne");
            return (Criteria) this;
        }

        public Criteria andHasKilneGreaterThan(String value) {
            addCriterion("has_kilne >", value, "hasKilne");
            return (Criteria) this;
        }

        public Criteria andHasKilneGreaterThanOrEqualTo(String value) {
            addCriterion("has_kilne >=", value, "hasKilne");
            return (Criteria) this;
        }

        public Criteria andHasKilneLessThan(String value) {
            addCriterion("has_kilne <", value, "hasKilne");
            return (Criteria) this;
        }

        public Criteria andHasKilneLessThanOrEqualTo(String value) {
            addCriterion("has_kilne <=", value, "hasKilne");
            return (Criteria) this;
        }

        public Criteria andHasKilneLike(String value) {
            addCriterion("has_kilne like", value, "hasKilne");
            return (Criteria) this;
        }

        public Criteria andHasKilneNotLike(String value) {
            addCriterion("has_kilne not like", value, "hasKilne");
            return (Criteria) this;
        }

        public Criteria andHasKilneIn(List<String> values) {
            addCriterion("has_kilne in", values, "hasKilne");
            return (Criteria) this;
        }

        public Criteria andHasKilneNotIn(List<String> values) {
            addCriterion("has_kilne not in", values, "hasKilne");
            return (Criteria) this;
        }

        public Criteria andHasKilneBetween(String value1, String value2) {
            addCriterion("has_kilne between", value1, value2, "hasKilne");
            return (Criteria) this;
        }

        public Criteria andHasKilneNotBetween(String value1, String value2) {
            addCriterion("has_kilne not between", value1, value2, "hasKilne");
            return (Criteria) this;
        }

        public Criteria andListatIsNull() {
            addCriterion("listAt is null");
            return (Criteria) this;
        }

        public Criteria andListatIsNotNull() {
            addCriterion("listAt is not null");
            return (Criteria) this;
        }

        public Criteria andListatEqualTo(String value) {
            addCriterion("listAt =", value, "listat");
            return (Criteria) this;
        }

        public Criteria andListatNotEqualTo(String value) {
            addCriterion("listAt <>", value, "listat");
            return (Criteria) this;
        }

        public Criteria andListatGreaterThan(String value) {
            addCriterion("listAt >", value, "listat");
            return (Criteria) this;
        }

        public Criteria andListatGreaterThanOrEqualTo(String value) {
            addCriterion("listAt >=", value, "listat");
            return (Criteria) this;
        }

        public Criteria andListatLessThan(String value) {
            addCriterion("listAt <", value, "listat");
            return (Criteria) this;
        }

        public Criteria andListatLessThanOrEqualTo(String value) {
            addCriterion("listAt <=", value, "listat");
            return (Criteria) this;
        }

        public Criteria andListatLike(String value) {
            addCriterion("listAt like", value, "listat");
            return (Criteria) this;
        }

        public Criteria andListatNotLike(String value) {
            addCriterion("listAt not like", value, "listat");
            return (Criteria) this;
        }

        public Criteria andListatIn(List<String> values) {
            addCriterion("listAt in", values, "listat");
            return (Criteria) this;
        }

        public Criteria andListatNotIn(List<String> values) {
            addCriterion("listAt not in", values, "listat");
            return (Criteria) this;
        }

        public Criteria andListatBetween(String value1, String value2) {
            addCriterion("listAt between", value1, value2, "listat");
            return (Criteria) this;
        }

        public Criteria andListatNotBetween(String value1, String value2) {
            addCriterion("listAt not between", value1, value2, "listat");
            return (Criteria) this;
        }

        public Criteria andTradedatIsNull() {
            addCriterion("tradedAt is null");
            return (Criteria) this;
        }

        public Criteria andTradedatIsNotNull() {
            addCriterion("tradedAt is not null");
            return (Criteria) this;
        }

        public Criteria andTradedatEqualTo(String value) {
            addCriterion("tradedAt =", value, "tradedat");
            return (Criteria) this;
        }

        public Criteria andTradedatNotEqualTo(String value) {
            addCriterion("tradedAt <>", value, "tradedat");
            return (Criteria) this;
        }

        public Criteria andTradedatGreaterThan(String value) {
            addCriterion("tradedAt >", value, "tradedat");
            return (Criteria) this;
        }

        public Criteria andTradedatGreaterThanOrEqualTo(String value) {
            addCriterion("tradedAt >=", value, "tradedat");
            return (Criteria) this;
        }

        public Criteria andTradedatLessThan(String value) {
            addCriterion("tradedAt <", value, "tradedat");
            return (Criteria) this;
        }

        public Criteria andTradedatLessThanOrEqualTo(String value) {
            addCriterion("tradedAt <=", value, "tradedat");
            return (Criteria) this;
        }

        public Criteria andTradedatLike(String value) {
            addCriterion("tradedAt like", value, "tradedat");
            return (Criteria) this;
        }

        public Criteria andTradedatNotLike(String value) {
            addCriterion("tradedAt not like", value, "tradedat");
            return (Criteria) this;
        }

        public Criteria andTradedatIn(List<String> values) {
            addCriterion("tradedAt in", values, "tradedat");
            return (Criteria) this;
        }

        public Criteria andTradedatNotIn(List<String> values) {
            addCriterion("tradedAt not in", values, "tradedat");
            return (Criteria) this;
        }

        public Criteria andTradedatBetween(String value1, String value2) {
            addCriterion("tradedAt between", value1, value2, "tradedat");
            return (Criteria) this;
        }

        public Criteria andTradedatNotBetween(String value1, String value2) {
            addCriterion("tradedAt not between", value1, value2, "tradedat");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table pairs
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
     * This class corresponds to the database table pairs
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