package com.github.gervaisb.boatrental.billing.domain;

import java.math.BigDecimal;
import java.time.Duration;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Blaise Gervais
 */
public class ChainedStrategyTest {

    private static final BigDecimal VALUE_FROM_FIRST = BigDecimal.ONE;
    private static final BigDecimal VALUE_FROM_SECOND = BigDecimal.valueOf(2);
    private static final BigDecimal VALUE_FROM_THIRD = BigDecimal.valueOf(3);
    private static final BigDecimal INITIAL_VALUE = BigDecimal.ZERO;
    private BillingStrategy first;
    private BillingStrategy second;
    private BillingStrategy third;

    @Before
    public void setup() {
        first = mock(BillingStrategy.class);
        when(first.apply(any(), any())).thenReturn(VALUE_FROM_FIRST);

        second = mock(BillingStrategy.class);
        when(second.apply(any(), any())).thenReturn(VALUE_FROM_SECOND);

        third = mock(BillingStrategy.class);
        when(third.apply(any(), any())).thenReturn(VALUE_FROM_THIRD);
    }

    @Test
    public void should_apply_first_then_second() {
        ChainedStrategy subject = new ChainedStrategy(first, second);

        BigDecimal result = subject.apply(INITIAL_VALUE, Duration.ofHours(0));

        verify(first).apply(eq(BigDecimal.ZERO), any());
        verify(second).apply(eq(VALUE_FROM_FIRST), any());
        assertThat(result)
                .isEqualTo(VALUE_FROM_SECOND);
    }

    @Test
    public void then_should_add_the_next_strategy_at_the_end_of_the_chain() {
        ChainedStrategy subject = new ChainedStrategy(first, second).then(third);

        BigDecimal result = subject.apply(INITIAL_VALUE, Duration.ofHours(0));

        verify(first).apply(eq(INITIAL_VALUE), any());
        verify(second).apply(eq(VALUE_FROM_FIRST), any());
        verify(third).apply(eq(VALUE_FROM_SECOND), any());
        assertThat(result)
                .isEqualTo(VALUE_FROM_THIRD);
    }
}
