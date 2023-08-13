<template>
  <div>
    <h2>Central Bank Exchange Rates</h2>
    <div class="exchange-rates">
      <div class="currency-card">
        <select v-model="selectedCurrency" @change="updateDropdownData" required>
          <option v-for="currency in currencies" :key="currency.code" :value="currency.code">{{ currency.code }} - {{ currency.name }}</option>
        </select>
        <h3 id="exchange-rate-header">{{ exchangeRateHeading }}</h3>
        <p id="exchange-rate">{{ exchangeRateValue }}</p>
      </div>
    </div>
  </div>
  <div>
    <h2>Exchange Rate History</h2>
    <div class="date-picker">
      <label for="dateFrom">From:</label>
      <input type="date" id="dateFrom" v-model="dateFrom" required :max="today">
    </div>
    <div class="date-picker">
      <label for="dateTo">To:</label>
      <input type="date" id="dateTo" v-model="dateTo" required :max="today">
    </div>
    <button id="update-button" @click="updateData">Update</button>
    <p v-if="tableError">{{ tableError }}</p>
    <div class="history-table">
      <table>
        <thead>
        <tr>
          <th>Date</th>
          <th>Exchange Rate</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="entry in exchangeRateData" :key="entry.date" class="table-row">
          <td>{{ entry.date }}</td>
          <td>{{ entry.rate }}</td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>

export default {
  data() {
    return {
      selectedCurrency: '',
      exchangeRateHeading: '',
      exchangeRateValue: '',
      currencies: [],
      today: new Date().toISOString().split('T')[0],
      dateFrom: '',
      dateTo: '',
      tableError: '',
      exchangeRateData: [],
    };
  },
  mounted() {
    this.updateDropdownData();
  },
  methods: {

    updateData() {
      this.updateTable(this.selectedCurrency, this.dateFrom, this.dateTo);
    },

    updateTable: async function(selectedCurrency, dateFrom, dateTo) {
      if(dateFrom !== undefined && dateTo !== undefined && selectedCurrency !== undefined) {
        try {
          const response = await fetch(`http://localhost:8080/exchange-rates/history?currency=${selectedCurrency}&dateFrom=${dateFrom}&dateTo=${dateTo}`);
          const data = await response.json();
          this.exchangeRateData = data.map(rate => ({
            date: rate.date,
            rate: rate.amount2
          }));
          if (this.exchangeRateData.length === 0) {
            this.tableError = "No data found!";
          } else {
            this.tableError = "";
          }
        } catch (error) {
          console.error('Error fetching exchange rate history:', error);
          this.tableError = "An error occurred while fetching data.";
        }
      }
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
      } else {
        this.updateFromDropdowns();
      }
    },
    updateFromDropdowns() {
      this.exchangeRateHeading = `EUR to ${this.selectedCurrency}`;

      fetch(`http://localhost:8080/exchange-rates/today-rate?currency=${this.selectedCurrency}`)
          .then(response => response.json())
          .then(data => {
            this.exchangeRateValue = `Exchange Rate: ${data.amount2}`;
          })
          .catch(error => {
            console.error('Error fetching exchange rate:', error);
            this.exchangeRateValue = 'Exchange Rate: N/A (Error)';
          });

      const oneWeekAgo = new Date();
      oneWeekAgo.setDate(oneWeekAgo.getDate() - 7);
      const formattedOneWeekAgo = oneWeekAgo.toISOString().split('T')[0];
      if (!this.dateFromInput) {
        this.dateFromInput = formattedOneWeekAgo;
      }

      if (!this.dateToInput) {
        this.dateToInput = this.today;
      }
      this.updateTable(this.selectedCurrency, this.dateFromInput, this.dateToInput);
    }
  },
};
</script>

<style scoped>

.history-table {
  margin-top: 20px;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 5px;
  box-shadow: 0px 0px 5px rgba(0, 0, 0, 0.2);
}

.history-table table {
  width: 100%;
  border-collapse: collapse;
  border: 1px solid #ddd;
}

.history-table th,
.history-table td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: left;
}

.history-table .table-row:hover {
  background-color: #f0f0f0;
}

</style>
