package com.redelastic.stocktrader.portfolio.api;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.redelastic.stocktrader.TransferId;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", defaultImpl = Void.class)
@JsonSubTypes({
        @JsonSubTypes.Type(FundsTransfer.FundsDeposited.class),
        @JsonSubTypes.Type(FundsTransfer.FundsWithdrawn.class)
})
public abstract class FundsTransfer {

    private FundsTransfer() {}

    @Value
    @EqualsAndHashCode(callSuper = false)
    @Builder
    public static class FundsDeposited extends FundsTransfer {
        @NonNull TransferId transferId;
        @NonNull BigDecimal funds;

        @Override
        public <T> T visit(Visitor<T> visitor) { return visitor.visit(this); }
    }

    @Value
    @EqualsAndHashCode(callSuper = false)
    @Builder
    public static class FundsWithdrawn extends FundsTransfer {
        @NonNull TransferId transferId;
        @NonNull BigDecimal funds;

        @Override
        public <T> T visit(Visitor<T> visitor) { return visitor.visit(this); }
    }

    @Value
    @EqualsAndHashCode(callSuper = false)
    @Builder
    public static class Refund extends FundsTransfer {
        @NonNull TransferId transferId;
        @NonNull BigDecimal funds;

        @Override
        public <T> T visit(Visitor<T> visitor) { return visitor.visit(this); }
    }

    public interface Visitor<T> {
        T visit(FundsDeposited fundsDeposited);
        T visit(FundsWithdrawn fundsWithdrawn);
        T visit(Refund refund);
    }

    public abstract <T> T visit(Visitor<T> visitor);

}