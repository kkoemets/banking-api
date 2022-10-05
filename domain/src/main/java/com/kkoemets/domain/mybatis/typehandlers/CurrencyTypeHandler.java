package com.kkoemets.domain.mybatis.typehandlers;

import com.kkoemets.domain.codes.Currency;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CurrencyTypeHandler extends BaseTypeHandler<Currency> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Currency parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getValue());
    }

    @Override
    public Currency getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        return Currency.create(value);
    }

    @Override
    public Currency getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        return Currency.create(value);
    }

    @Override
    public Currency getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        return Currency.create(value);
    }

}
