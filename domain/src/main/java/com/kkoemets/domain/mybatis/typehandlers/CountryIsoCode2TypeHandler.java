package com.kkoemets.domain.mybatis.typehandlers;

import com.kkoemets.domain.codes.CountryIsoCode2;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryIsoCode2TypeHandler extends BaseTypeHandler<CountryIsoCode2> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, CountryIsoCode2 parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getValue());
    }

    @Override
    public CountryIsoCode2 getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        return value == null ? null : CountryIsoCode2.create(value);
    }

    @Override
    public CountryIsoCode2 getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        return value == null ? null : CountryIsoCode2.create(value);
    }

    @Override
    public CountryIsoCode2 getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        return value == null ? null : CountryIsoCode2.create(value);
    }

}
