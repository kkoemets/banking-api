package com.kkoemets.domain.mybatis.typehandlers;

import com.kkoemets.domain.status.AccountStatus;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountStatusTypeHandler extends BaseTypeHandler<AccountStatus> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, AccountStatus parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getCode());
    }

    @Override
    public AccountStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        return value == null ? null : AccountStatus.create(value);
    }

    @Override
    public AccountStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        return value == null ? null : AccountStatus.create(value);
    }

    @Override
    public AccountStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        return value == null ? null : AccountStatus.create(value);
    }

}
