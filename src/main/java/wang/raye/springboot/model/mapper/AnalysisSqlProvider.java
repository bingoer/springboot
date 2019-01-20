package wang.raye.springboot.model.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;
import wang.raye.springboot.model.Analysis;
import wang.raye.springboot.model.AnalysisCriteria.Criteria;
import wang.raye.springboot.model.AnalysisCriteria.Criterion;
import wang.raye.springboot.model.AnalysisCriteria;

public class AnalysisSqlProvider {

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table analysis
     *
     * @mbg.generated
     */
    public String countByExample(AnalysisCriteria example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("analysis");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table analysis
     *
     * @mbg.generated
     */
    public String deleteByExample(AnalysisCriteria example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("analysis");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table analysis
     *
     * @mbg.generated
     */
    public String insertSelective(Analysis record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("analysis");
        
        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=INTEGER}");
        }
        
        if (record.getExchange() != null) {
            sql.VALUES("exchange", "#{exchange,jdbcType=VARCHAR}");
        }
        
        if (record.getSymbol() != null) {
            sql.VALUES("symbol", "#{symbol,jdbcType=VARCHAR}");
        }
        
        if (record.getStatus() != null) {
            sql.VALUES("status", "#{status,jdbcType=VARCHAR}");
        }
        
        if (record.getPrice() != null) {
            sql.VALUES("price", "#{price,jdbcType=DOUBLE}");
        }
        
        if (record.getPeriod() != null) {
            sql.VALUES("period", "#{period,jdbcType=VARCHAR}");
        }
        
        if (record.getKlineTime() != null) {
            sql.VALUES("kline_time", "#{klineTime,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateTime() != null) {
            sql.VALUES("create_time", "#{createTime,jdbcType=TIMESTAMP}");
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table analysis
     *
     * @mbg.generated
     */
    public String selectByExample(AnalysisCriteria example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("id");
        } else {
            sql.SELECT("id");
        }
        sql.SELECT("exchange");
        sql.SELECT("symbol");
        sql.SELECT("status");
        sql.SELECT("price");
        sql.SELECT("period");
        sql.SELECT("kline_time");
        sql.SELECT("create_time");
        sql.FROM("analysis");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table analysis
     *
     * @mbg.generated
     */
    public String updateByExampleSelective(Map<String, Object> parameter) {
        Analysis record = (Analysis) parameter.get("record");
        AnalysisCriteria example = (AnalysisCriteria) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("analysis");
        
        if (record.getId() != null) {
            sql.SET("id = #{record.id,jdbcType=INTEGER}");
        }
        
        if (record.getExchange() != null) {
            sql.SET("exchange = #{record.exchange,jdbcType=VARCHAR}");
        }
        
        if (record.getSymbol() != null) {
            sql.SET("symbol = #{record.symbol,jdbcType=VARCHAR}");
        }
        
        if (record.getStatus() != null) {
            sql.SET("status = #{record.status,jdbcType=VARCHAR}");
        }
        
        if (record.getPrice() != null) {
            sql.SET("price = #{record.price,jdbcType=DOUBLE}");
        }
        
        if (record.getPeriod() != null) {
            sql.SET("period = #{record.period,jdbcType=VARCHAR}");
        }
        
        if (record.getKlineTime() != null) {
            sql.SET("kline_time = #{record.klineTime,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateTime() != null) {
            sql.SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        }
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table analysis
     *
     * @mbg.generated
     */
    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("analysis");
        
        sql.SET("id = #{record.id,jdbcType=INTEGER}");
        sql.SET("exchange = #{record.exchange,jdbcType=VARCHAR}");
        sql.SET("symbol = #{record.symbol,jdbcType=VARCHAR}");
        sql.SET("status = #{record.status,jdbcType=VARCHAR}");
        sql.SET("price = #{record.price,jdbcType=DOUBLE}");
        sql.SET("period = #{record.period,jdbcType=VARCHAR}");
        sql.SET("kline_time = #{record.klineTime,jdbcType=VARCHAR}");
        sql.SET("create_time = #{record.createTime,jdbcType=TIMESTAMP}");
        
        AnalysisCriteria example = (AnalysisCriteria) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table analysis
     *
     * @mbg.generated
     */
    public String updateByPrimaryKeySelective(Analysis record) {
        SQL sql = new SQL();
        sql.UPDATE("analysis");
        
        if (record.getExchange() != null) {
            sql.SET("exchange = #{exchange,jdbcType=VARCHAR}");
        }
        
        if (record.getSymbol() != null) {
            sql.SET("symbol = #{symbol,jdbcType=VARCHAR}");
        }
        
        if (record.getStatus() != null) {
            sql.SET("status = #{status,jdbcType=VARCHAR}");
        }
        
        if (record.getPrice() != null) {
            sql.SET("price = #{price,jdbcType=DOUBLE}");
        }
        
        if (record.getPeriod() != null) {
            sql.SET("period = #{period,jdbcType=VARCHAR}");
        }
        
        if (record.getKlineTime() != null) {
            sql.SET("kline_time = #{klineTime,jdbcType=VARCHAR}");
        }
        
        if (record.getCreateTime() != null) {
            sql.SET("create_time = #{createTime,jdbcType=TIMESTAMP}");
        }
        
        sql.WHERE("id = #{id,jdbcType=INTEGER}");
        
        return sql.toString();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table analysis
     *
     * @mbg.generated
     */
    protected void applyWhere(SQL sql, AnalysisCriteria example, boolean includeExamplePhrase) {
        if (example == null) {
            return;
        }
        
        String parmPhrase1;
        String parmPhrase1_th;
        String parmPhrase2;
        String parmPhrase2_th;
        String parmPhrase3;
        String parmPhrase3_th;
        if (includeExamplePhrase) {
            parmPhrase1 = "%s #{example.oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{example.oredCriteria[%d].allCriteria[%d].value} and #{example.oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{example.oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{example.oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{example.oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        } else {
            parmPhrase1 = "%s #{oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{oredCriteria[%d].allCriteria[%d].value} and #{oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        }
        
        StringBuilder sb = new StringBuilder();
        List<Criteria> oredCriteria = example.getOredCriteria();
        boolean firstCriteria = true;
        for (int i = 0; i < oredCriteria.size(); i++) {
            Criteria criteria = oredCriteria.get(i);
            if (criteria.isValid()) {
                if (firstCriteria) {
                    firstCriteria = false;
                } else {
                    sb.append(" or ");
                }
                
                sb.append('(');
                List<Criterion> criterions = criteria.getAllCriteria();
                boolean firstCriterion = true;
                for (int j = 0; j < criterions.size(); j++) {
                    Criterion criterion = criterions.get(j);
                    if (firstCriterion) {
                        firstCriterion = false;
                    } else {
                        sb.append(" and ");
                    }
                    
                    if (criterion.isNoValue()) {
                        sb.append(criterion.getCondition());
                    } else if (criterion.isSingleValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase1, criterion.getCondition(), i, j));
                        } else {
                            sb.append(String.format(parmPhrase1_th, criterion.getCondition(), i, j,criterion.getTypeHandler()));
                        }
                    } else if (criterion.isBetweenValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase2, criterion.getCondition(), i, j, i, j));
                        } else {
                            sb.append(String.format(parmPhrase2_th, criterion.getCondition(), i, j, criterion.getTypeHandler(), i, j, criterion.getTypeHandler()));
                        }
                    } else if (criterion.isListValue()) {
                        sb.append(criterion.getCondition());
                        sb.append(" (");
                        List<?> listItems = (List<?>) criterion.getValue();
                        boolean comma = false;
                        for (int k = 0; k < listItems.size(); k++) {
                            if (comma) {
                                sb.append(", ");
                            } else {
                                comma = true;
                            }
                            if (criterion.getTypeHandler() == null) {
                                sb.append(String.format(parmPhrase3, i, j, k));
                            } else {
                                sb.append(String.format(parmPhrase3_th, i, j, k, criterion.getTypeHandler()));
                            }
                        }
                        sb.append(')');
                    }
                }
                sb.append(')');
            }
        }
        
        if (sb.length() > 0) {
            sql.WHERE(sb.toString());
        }
    }
}