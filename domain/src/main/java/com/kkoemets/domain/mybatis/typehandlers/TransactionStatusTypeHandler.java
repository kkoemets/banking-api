package com.kkoemets.domain.mybatis.typehandlers;

import com.kkoemets.domain.codes.TransactionStatus;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionStatusTypeHandler extends BaseTypeHandler<TransactionStatus> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, TransactionStatus parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getCode());
    }

    @Override
    public TransactionStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        return value == null ? null : TransactionStatus.create(value);
    }

    @Override
    public TransactionStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        return value == null ? null : TransactionStatus.create(value);
    }

    @Override
    public TransactionStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        return value == null ? null : TransactionStatus.create(value);
    }

}
