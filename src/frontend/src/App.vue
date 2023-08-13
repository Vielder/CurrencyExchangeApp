<template>
  <div>
    <header>
      <h1>Currency Exchange App</h1>
    </header>
    <div class="container">
      <ExchangeRates />
      <CurrencyCalculator />
    </div>
  </div>
</template>

<script>
import ExchangeRates from './components/ExchangeRates.vue';
import CurrencyCalculator from './components/CurrencyCalculator.vue';

export default {
  components: {
    ExchangeRates,
    CurrencyCalculator
  }
}
</script>

<style src="./css/styles.css">
</style>
