<template>
  <div>
    <h2>Currency Calculator</h2>
    <div class="calculator">
      <div>
        <div class="input-group">
          <label for="amount">Amount:</label>
          <input v-model="amount" type="number" id="amount" name="amount" step="0.01" required>
        </div>
        <div class="input-group">
          <label for="fromCurrency">From Currency:</label>
          <select v-model="fromCurrency" @change="updateDropdownData" id="fromCurrency" name="fromCurrency" required>
            <option v-for="currency in currencies" :key="currency.code" :value="currency.code">{{ currency.code }} - {{ currency.name }}</option>
          </select>
        </div>
        <div class="input-group">
          <label for="toCurrency">To Currency:</label>
          <select v-model="toCurrency" @change="updateDropdownData" id="toCurrency" name="toCurrency" required>
            <option v-for="currency in currencies" :key="currency.code" :value="currency.code">{{ currency.code }} - {{ currency.name }}</option>
          </select>
        </div>
        <button @click="submitCalculateExchangeRate">Calculate</button>
      </div>
      <div class="result">
        <p id="convertedAmount">Converted Amount: {{ convertedAmount }}</p>
        <p id="exchangeRate">Exchange Rate: {{ calculatedExchangeRate }}</p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      amount: 0,
      fromCurrency: '',
      toCurrency: '',
      currencies: [],
      convertedAmount: '',
      calculatedExchangeRate: ''
    };
  },
  mounted() {
    this.updateDropdownData();
  },
  methods: {
    submitCalculateExchangeRate() {
      this.calculateExchangeRate(this.amount, this.fromCurrency, this.toCurrency);
    },

    updateDropdownData() {
      if (this.currencies.length === 0) {
        fetch('http://localhost:8080/exchange-rates/currencies')
            .then(response => response.json())
            .then(data => {
              this.currencies = data.map(currencyData => ({
                code: currencyData.currency,
                name: currencyData.nameEN
              }));
            })
            .catch(error => {
              console.error('Error fetching currency data:', error);
            });
      }
    },

    calculateExchangeRate(amount, fromCurrency, toCurrency) {
      fetch(`http://localhost:8080/exchange-rates/calculate?amount=${amount}&currencyFrom=${fromCurrency}&currencyTo=${toCurrency}`)
          .then(response => response.json())
          .then(data => {
            this.convertedAmount = `Converted Amount: ${data.amount.toFixed(2)}`;
            this.calculatedExchangeRate = `Exchange Rate: ${data.rate.toFixed(4)}`;
          })
          .catch(error => {
            console.error('Error calculating exchange rate:', error);
            this.convertedAmount = 'Converted Amount: N/A (Error)';
            this.calculatedExchangeRate = 'Exchange Rate: N/A';
          });
    }
  }
};
</script>

<style scoped>

</style>
