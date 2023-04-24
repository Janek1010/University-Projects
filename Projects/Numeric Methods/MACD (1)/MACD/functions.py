def calculateMacdForOneParameter(dataToCalc, index, ema_lvl, topIndex):
  alfa = 2/(ema_lvl+1)
  valueOfMeter = 0
  valueOfDenominator = 0

  # czyli index 0 = najmlodsza data
  for x in range(ema_lvl):
      if index+x > topIndex:
          break
      valueOfMeter += (1- alfa)**x * dataToCalc[index+x]

  # Down
  for x in range(ema_lvl):
      if index + x > topIndex:
          break
      valueOfDenominator += (1- alfa)**x


  return (valueOfMeter/valueOfDenominator).__round__(4)

def isBuyCall(MACD, SIGNAL,index, lastIndex, firstIndex):
    if index + 1 > lastIndex:
        return 0
    if index - 1 < firstIndex:
        return 0
    if MACD[index] > SIGNAL[index]:
        if MACD[index+1] < SIGNAL[index+1]:
            return "BUY"
    return 0

def isSellCall(MACD, SIGNAL,index, lastIndex, firstIndex):
    if index + 1 > lastIndex:
        return 0
    if index - 1 < firstIndex:
        return 0
    if MACD[index] < SIGNAL[index]:
        if MACD[index+1] > SIGNAL[index+1]:
            return "SELL"
    return 0

def calculateProfit(data, investment, lastIndex, firstIndex):

    state = 'SELL'
    currentMoney = investment
    amountofDollars = 0
    ostatniaCenaDolara = 3
    for i in range(lastIndex, firstIndex, -1):
        if (data['BUY'][i] == 'BUY') & (state != 'BUY'):
            state = 'BUY'
            amountofDollars += currentMoney / (float)(data['Kurs'][i]) # np kupuje dolary za 3.7 za 100 zl to 370zl
            print(f"Kupuje {amountofDollars.__round__(4)} dolarow za {currentMoney.__round__(4)} przy cenie {(float)(data['Kurs'][i])}")
            currentMoney = 0;
            ostatniaCenaDolara = (float)(data['Kurs'][i])
        elif (data['SELL'][i] == 'SELL') & (state != 'SELL'):
            state = 'SELL'
            currentMoney = amountofDollars * (float)(data['Kurs'][i])
            print(f"Sprzedaje {amountofDollars.__round__(4)} dolarow za {currentMoney.__round__(4)} przy cenie {(float)(data['Kurs'][i])}")
            amountofDollars = 0;
    print("\n")
    if  state == 'SELL':
        print(f"Inwestycja o poczatkowej wartosci {investment} wynosi teraz {currentMoney.__round__(4)}")
        print(f"a wiec profit to: {currentMoney.__round__(4) - investment}")
        return
    print(f"Nie sprzedales ostanich dolarow, ale przelcizajac po ostatniej cenie dolara wartosc wynosi: {(amountofDollars * ostatniaCenaDolara).__round__(4)}")
    print(f"a wiec profit to: {(amountofDollars * ostatniaCenaDolara).__round__(4) - investment}")