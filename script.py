import pandas as pd
import matplotlib.pyplot as plt

# Leggi i dati dal file CSV
df = pd.read_csv('job_data.csv')
# Ordina il DataFrame in base alla colonna 'Time'
df = df.sort_values(by='Time')

# Crea un grafico a linee
plt.plot(df['Time'], df['TotalJobs'], linestyle='-', color='blue', label='Total Jobs')

# Aggiungi etichette agli assi e un titolo al grafico
plt.xlabel('Time')
plt.ylabel('Total Jobs')
plt.title('Line Chart of Total Jobs Over Time')

# Aggiungi una legenda
plt.legend()

# Mostra il grafico
plt.show()