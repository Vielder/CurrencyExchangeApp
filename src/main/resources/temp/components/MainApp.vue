<template>
  <div>
    <header>
      <h1>Currency Exchange App</h1>
    </header>
    <div class="container">
      <ExchangeRates />
      <ExchangeRateHistory />
      <CurrencyCalculator />
    </div>
  </div>
</template>

<script>
import ExchangeRates from '../../../../frontend/src/components/ExchangeRates.vue';
import ExchangeRateHistory from '../../../../frontend/src/components/ExchangeRateHistory.vue';
import ChartComponent from '../../../../frontend/src/components/ChartComponent.vue';
import CurrencyCalculator from '../../../../frontend/src/components/CurrencyCalculator.vue';

export default {
  components: {
    ExchangeRates,
    ExchangeRateHistory,
    ChartComponent,
    CurrencyCalculator
  }
}
</script>

<style scoped>
/* Your component-specific CSS styles here */
</style>
