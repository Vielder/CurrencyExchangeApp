document.addEventListener('DOMContentLoaded', function() {
    const today = new Date().toISOString().split('T')[0];
    let currencies = [];

    const updateButton = document.getElementById("update-button");
    const exchangeDropdown = document.getElementById('currency-dropdown');
    const calculatorFromDropdown = document.getElementById("fromCurrency");
    const calculatorToDropdown = document.getElementById("toCurrency");
    const exchangeRateHeading = document.getElementById("exchange-rate-header");
    const exchangeRateValue = document.getElementById("exchange-rate");
    const dateFromInput = document.getElementById('dateFrom');
    const dateToInput = document.getElementById('dateTo');
    const chartError = document.getElementById('chart-error');
    let exchangeRateData = document.getElementById('exchange-rate-chart').getContext('2d');
    const convertedAmount = document.getElementById('convertedAmount');
    const exchangeRate = document.getElementById('exchangeRate');
    const form = document.querySelector('.calculator form');

    dateToInput.max = today;
    dateFromInput.max = today;

    form.addEventListener('submit', function(event) {
        event.preventDefault();
        const amount = parseFloat(document.getElementById('amount').value);
        const fromCurrency = document.getElementById('fromCurrency').value;
        const toCurrency = document.getElementById('toCurrency').value;

        calculateExchangeRate(amount, fromCurrency, toCurrency);
    });

    function calculateExchangeRate(amount, fromCurrency, toCurrency) {
        fetch(`exchange-rates/calculate?amount=${amount}&currencyFrom=${fromCurrency}&currencyTo=${toCurrency}`)
            .then(response => response.json())
            .then(data => {
                convertedAmount.textContent = `Converted Amount: ${data.amount.toFixed(2)}`;
                exchangeRate.textContent = `Exchange Rate: ${data.rate.toFixed(4)}`;
            })
            .catch(error => {
                console.error('Error calculating exchange rate:', error);
                convertedAmount.textContent = 'Converted Amount: N/A (Error)';
                exchangeRate.textContent = 'Exchange Rate: N/A';
            });
    }

    function updateChart(selectedCurrency, dateFrom, dateTo) {
        fetch(`exchange-rates/history?currency=${selectedCurrency}&dateFrom=${dateFrom}&dateTo=${dateTo}`)
            .then(response => response.json())
            .then(data => {
                exchangeRateData = data.map(rate => ({
                    date: rate.date,
                    rate: rate.amount2
                }));

                console.log(exchangeRateData.length)
                if(exchangeRateData.length === 0){
                    chartError.textContent = "No data found!"
                } else  {
                    chart.data.labels = exchangeRateData.map(rate => rate.date);
                    chart.data.datasets[0].data = exchangeRateData.map(rate => rate.rate);

                    chart.update();
                }


            })
            .catch(error => {
                console.error('Error fetching exchange rate history:', error);
            });
    }

    function updateFromDropdowns(selectedCurrency) {
        exchangeRateHeading.textContent = `EUR to ${selectedCurrency}`;

        fetch(`exchange-rates/today-rate?currency=${selectedCurrency}`)
            .then(response => response.json())
            .then(data => {
                const exchangeRate = data.amount2;
                exchangeRateValue.textContent = `Exchange Rate: ${exchangeRate}`;
            })
            .catch(error => {
                console.error('Error fetching exchange rate:', error);
                exchangeRateValue.textContent = 'Exchange Rate: N/A (Error)';
            });

        const oneWeekAgo = new Date();
        oneWeekAgo.setDate(oneWeekAgo.getDate() - 7);
        const formattedOneWeekAgo = oneWeekAgo.toISOString().split('T')[0];
        if (dateFromInput.value === "") {
            dateFrom = formattedOneWeekAgo;
            dateFromInput.value = formattedOneWeekAgo;
        }

        if (dateToInput.value === "") {
            dateTo = today;
            dateToInput.value = today;
        }

        updateChart(selectedCurrency, dateFromInput.value, dateToInput.value);
    }

    function updateDropdownData(element){
        if (currencies.length > 0 && element.options.length === 1){
            while (element.firstChild) {
                element.removeChild(element.firstChild);
            }

            // Добавляем опции в дропдаун
            currencies.forEach(currency => {
                const option = document.createElement('option');
                option.value = currency.code;
                option.text = currency.code + " - " + currency.name;
                element.appendChild(option);
            });
        } else if (currencies.length === 0){
            // Получаем данные с API
            console.log("Getting data from api.");
            fetch('exchange-rates/currencies')
                .then(response => response.json())
                .then(data => {
                    currencies = data.map(currencyData => ({
                        code: currencyData.currency,
                        name: currencyData.nameEN
                    }));

                    // Очищаем дропдаун перед добавлением новых опций
                    while (element.firstChild) {
                        element.removeChild(element.firstChild);
                    }

                    // Добавляем опции в дропдаун
                    currencies.forEach(currency => {
                        const option = document.createElement('option');
                        option.value = currency.code;
                        option.text = currency.code + " - " + currency.name;
                        element.appendChild(option);
                    });
                })
                .catch(error => {
                    console.error('Ошибка при получении данных:', error);
                });
        }
    }

    updateButton.addEventListener('click', function(){
        const selectedCurrency = exchangeDropdown.value;
        dateFrom = dateFromInput.value;
        dateTo = dateToInput.value;

        // Вычитаем одну неделю (7 дней) из текущей даты
        let oneWeekAgo = new Date();
        oneWeekAgo.setDate(oneWeekAgo.getDate() - 7);
        oneWeekAgo = oneWeekAgo.toISOString().split('T')[0];
        if (dateFromInput.value === ""){
            dateFrom = oneWeekAgo;
            dateFromInput.value = oneWeekAgo;
        }

        if (dateToInput.value === ""){
            dateTo = today;
            dateToInput.value = today;
        }

        fetch(`exchange-rates/history?currency=${selectedCurrency}&dateFrom=${dateFrom}&dateTo=${dateTo}`)
            .then(response => response.json())
            .then(data => {
                exchangeRateData = data.map(rate => ({
                    date: rate.date,
                    rate: rate.amount2
                }));

                chart.data.labels = exchangeRateData.map(rate => rate.date);
                chart.data.datasets[0].data = exchangeRateData.map(rate => rate.rate);

                chart.update();
            })
            .catch(error => {
                console.error('Error fetching exchange rate history:', error);
            });
    })

    exchangeDropdown.addEventListener('change', function() {
        const selectedCurrency = exchangeDropdown.value;
        updateFromDropdowns(selectedCurrency);
    });

    exchangeDropdown.addEventListener('focus', function() {
        updateDropdownData(exchangeDropdown);
    })

    calculatorFromDropdown.addEventListener('click', function() {
        updateDropdownData(calculatorFromDropdown);
    });

    calculatorToDropdown.addEventListener('click', function() {
        updateDropdownData(calculatorToDropdown);
    });

    // Создание графика

    const chart = new Chart(exchangeRateData, {
        type: 'line',
        data: {
            labels: [], // Даты будут форматироваться автоматически
            datasets: [{
                label: 'Exchange Rate',
                data: [], // Ваши данные об обменном курсе
                borderColor: 'blue',
                fill: false
            }]
        },
        options: {
            maintainAspectRatio: false, // Это позволит графику растягиваться по размерам контейнера
            scales: {
                xAxis: {
                    type: 'time',
                    time: {
                        unit: 'day',
                    },
                },
                y: {
                    beginAtZero: true
                }
            }
        }
    });
});