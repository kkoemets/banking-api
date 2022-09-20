package com.kkoemets.domain.mybatis.typehandlers;

import com.kkoemets.domain.id.AccountId;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountIdTypeHandler extends BaseTypeHandler<AccountId> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, AccountId parameter, JdbcType jdbcType) throws SQLException {
        ps.setLong(i, parameter.getValue());
    }

    @Override
    public AccountId getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Long value = rs.getLong(columnName);
        return new AccountId(value);
    }

    @Override
    public AccountId getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Long value = rs.getLong(columnIndex);
        return new AccountId(value);
    }

    @Override
    public AccountId getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Long value = cs.getLong(columnIndex);
        return new AccountId(value);
    }

}
