package com.kkoemets.domain.id;

import java.util.Objects;

public abstract class Id<A extends Id<A>> {
    private final Long value;

    public Id(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

    @Override
    public String toString() {
        return Objects.toString(value, "null");
    }

}
