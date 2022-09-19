package com.kkoemets.domain.id;

public abstract class Id<A extends Id<A>> {
    private Long value;

    public Id(Long value) {
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

}
