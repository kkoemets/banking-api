package com.kkoemets.domain.mybatis.typehandlers;

import com.kkoemets.domain.id.CustomerId;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerIdTypeHandler extends BaseTypeHandler<CustomerId> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, CustomerId parameter, JdbcType jdbcType) throws SQLException {
        ps.setLong(i, parameter.getValue());
    }

    @Override
    public CustomerId getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Long value = rs.getLong(columnName);
        return new CustomerId(value);
    }

    @Override
    public CustomerId getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Long value = rs.getLong(columnIndex);
        return new CustomerId(value);
    }

    @Override
    public CustomerId getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Long value = cs.getLong(columnIndex);
        return new CustomerId(value);
    }

}
