#  https://www.money.pl/pieniadze/nbparch/srednie/?symbol=USD&from=2023-02-14&to=2023-03-14
import numpy as np
import pandas as pd
from functions import *
import plotly.graph_objects as go
from plotly.subplots import make_subplots

data = pd.read_csv('usdData2018-2023.csv')

print(f"Wybierz badany okres z przedziału: {data['Data'][data.index[-1]]} - {data['Data'][0]}")

money = (int)(input("Podaj kwote do zainwestowania: "))
start_date = input("Wpisz poczatkowa date, musi byc w takim foriacie jak powyzej\n")
end_date = input("Wpisz koncowa date, musi byc w takim foriacie jak powyzej\n")

data = data[(data['Data'] > start_date) & (data['Data'] < end_date)]

lastIndex = data.index[-1]
firstIndex = data.index[0]

data['EMA12'] = data['Kurs'].apply(lambda x: calculateMacdForOneParameter(data['Kurs'], data['Kurs'].tolist().index(x) + firstIndex, 12, lastIndex))
data['EMA26'] = data['Kurs'].apply(lambda x: calculateMacdForOneParameter(data['Kurs'], data['Kurs'].tolist().index(x) + firstIndex, 26, lastIndex))
data['MACD'] = np.subtract(data['EMA12'], data['EMA26'])
data['SIGNAL'] = data['MACD'].apply(lambda x: calculateMacdForOneParameter(data['MACD'], data['MACD'].tolist().index(x) + firstIndex, 26, lastIndex))
data['BUY'] = data['MACD'].apply(lambda x: isBuyCall(data['MACD'], data['SIGNAL'],data['MACD'].tolist().index(x) + firstIndex, lastIndex, firstIndex))
data['SELL'] = data['MACD'].apply(lambda x: isSellCall(data['MACD'], data['SIGNAL'],data['MACD'].tolist().index(x) + firstIndex, lastIndex, firstIndex))


# Wykresy


fig = make_subplots(rows=2, cols=1)
# dolar
fig.add_trace(go.Scatter(x=data['Data'], y=data['Kurs'], name='Kurs USD/PLN'), row=1, col=1)

#MACD i SIGNAL
fig.add_trace(go.Scatter(x=data['Data'], y=data['MACD'], name='MACD'), row=2, col=1)
fig.add_trace(go.Scatter(x=data['Data'], y=data['SIGNAL'], name='SIGNAL'), row=2, col=1)

# buy, sell
buy_data = data[data['BUY'] == 'BUY']
fig.add_trace(go.Scatter(x=buy_data['Data'], y=buy_data['Kurs'], name='Kupno', mode='markers', marker=dict(color='green', size=8)), row=1, col=1)
sell_data = data[data['SELL'] == 'SELL']
fig.add_trace(go.Scatter(x=sell_data['Data'], y=sell_data['Kurs'], name='Sprzedaz', mode='markers', marker=dict(color='red', size=8)), row=1, col=1)

fig.add_trace(go.Scatter(x=buy_data['Data'], y=buy_data['MACD'], name='Kupno', mode='markers', marker=dict(color='green', size=8)), row=2, col=1)
fig.add_trace(go.Scatter(x=sell_data['Data'], y=sell_data['MACD'], name='Kupno', mode='markers', marker=dict(color='red', size=8)), row=2, col=1)

fig.update_layout(title=f'USD/PLN {start_date} - {end_date}',xaxis_title='Data',  yaxis_title='Kurs',showlegend=True)
fig.show()


print(data)
calculateProfit(data, money, lastIndex, firstIndex)
