package com.kkoemets.domain.mybatis.typehandlers;

import com.kkoemets.domain.id.TransactionId;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionIdTypeHandler extends BaseTypeHandler<TransactionId> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, TransactionId parameter, JdbcType jdbcType) throws SQLException {
        ps.setLong(i, parameter.getValue());
    }

    @Override
    public TransactionId getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Long value = rs.getLong(columnName);
        return new TransactionId(value);
    }

    @Override
    public TransactionId getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Long value = rs.getLong(columnIndex);
        return new TransactionId(value);
    }

    @Override
    public TransactionId getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Long value = cs.getLong(columnIndex);
        return new TransactionId(value);
    }

}
